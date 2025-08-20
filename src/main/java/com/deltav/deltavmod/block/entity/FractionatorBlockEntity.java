package com.deltav.deltavmod.block.entity;

import javax.annotation.Nullable;

import com.deltav.deltavmod.fluid.ModFluids;
import com.deltav.deltavmod.item.ModItems;
import com.deltav.deltavmod.menu.FractionatorMenu;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.capabilities.Capabilities.FluidHandler;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.ItemStackHandler;

/**
 * Fractionator block entity. Holds the inventory and fluid tank for the fractionation process.
 * Handles crafting (i.e. converting internal tank contents into barrels)
 * 
 * @author Adam Crawley
 */
public class FractionatorBlockEntity extends BlockEntity implements MenuProvider {
    private static final String PROGRESS_NBT = "fractionator.progress";
    private static final String MAX_PROGRESS_NBT = "fractionator.max_progress";
    
    private static final int MAX_TANK_CAPACITY = 10_000; // 10 buckets
    private static final int MAX_PROGRESS = 5;
    private static final int DRAIN = 50; // Drains 50 mB per 5 ticks

    public static final int INTERNAL_DATA_COUNT = 4;

    // Holds all items within the fractionator
    public final ItemStackHandler inventory = new ItemStackHandler(2) {
        @Override
        protected int getStackLimit(int slot, ItemStack stack) {
            return 1; // In every slot only 1 item can be stored
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide) {
                // Sync the inventory with the client
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3); // TODO: Is this necessary?
            }
        }
    };

    // Internal state
    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;
    private int progress = 0;
    private int maxProgress = MAX_PROGRESS; // TODO: This variable could most likely be removed
    private ContainerData data; // Synchronization data
    private final FluidTank tank = new FluidTank(MAX_TANK_CAPACITY) {
        @Override
        protected void onContentsChanged() {
            setChanged();
        }
    };

    /**
     * Constructor for FractionatorBlockEntity
     *
     * @param pos   position the block is placed
     * @param state state of the block
     */
    public FractionatorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FRACTIONATOR_BE.get(), pos, state);
        data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> progress;
                    case 1 -> maxProgress;
                    case 2 -> tank.getFluidAmount();
                    case 3 -> tank.getCapacity();
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> progress = value;
                    case 1 -> maxProgress = value;
                    case 2 -> tank.setFluid(new FluidStack(ModFluids.OIL_SOURCE.get(), value));
                    case 3 -> tank.setCapacity(value);
                }
            }

            @Override
            public int getCount() {
                return INTERNAL_DATA_COUNT;
            }
        };
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        return new FractionatorMenu(id, inv, this, data);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("gui.deltav.fractionator");
    }

    /**
     * Handle block removal, drop all items from internal inventory
     *
     * @param pos   position the block is placed
     * @param state state of the block
     */
    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        super.preRemoveSideEffects(pos, state);

        // Drop items from internal ItemHandler
        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (!stack.isEmpty()) {
                Containers.dropItemStack(this.level, pos.getX(), pos.getY(), pos.getZ(), stack);
            }
        }
    }

    @Override
    protected void saveAdditional(ValueOutput out) {
        super.saveAdditional(out);
        inventory.serialize(out);
        out.putInt(PROGRESS_NBT, progress);
        out.putInt(MAX_PROGRESS_NBT, maxProgress);

        // Save tank
        tank.serialize(out);
    }

    @Override
    protected void loadAdditional(ValueInput in) {
        super.loadAdditional(in);
        inventory.deserialize(in);
        progress = in.getIntOr(PROGRESS_NBT, 0);
        maxProgress = in.getIntOr(MAX_PROGRESS_NBT, maxProgress);

        // Load tank
        tank.deserialize(in);
    }

    public IFluidHandler getFluidHandler(@Nullable Direction side) {
        return tank;
    }

    /**
     * Runs on every tick, handles conversion from oil buckets to internal tank,
     * and internal tank to output barrels if applicable.
     * 
     * @note invoked from the overriden block getTicket function in {@link FractionatorBlock}
     */
    public void tick() {
        if (level == null || level.isClientSide) return;

        attemptDrainInInput();

        if (hasRecipe()) {
            progress++;
            setChanged();

            if (progress >= maxProgress) {
                craftItem();
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    /*
     * Attempts to drain fluid from the input tank into the internal tank.
     */
    private void attemptDrainInInput() {
        ItemStack input = inventory.getStackInSlot(INPUT_SLOT);
        if (input.getItem() == ModItems.OIL_BUCKET.get()) {
            IFluidHandlerItem itemHandler = FluidHandler.ITEM.getCapability(input, null);
            if (itemHandler != null) {
                // Drain the entire bucket (1000 mB)
                FluidStack drained = itemHandler.drain(1000, FluidAction.EXECUTE);
                if (drained != null && !drained.isEmpty()) {
                    int filled = tank.fill(drained, FluidAction.EXECUTE);
                    if (filled > 0) {
                        ItemStack result = itemHandler.getContainer();
                        inventory.setStackInSlot(INPUT_SLOT, result);
                        setChanged();
                    }
                }
            }
        }
    }

    /**
     * Checks if the current inventory setup has a valid recipe,
     * i.e. oil is being converted / fractionated.
     * 
     * @return true if a valid recipe is present, false otherwise.
     */
    private boolean hasRecipe() {
        ItemStack output = inventory.getStackInSlot(OUTPUT_SLOT);

        boolean tankHasSufficientOil = tank.getFluidAmount() >= DRAIN && tank.getFluid().getFluid() == ModFluids.OIL_SOURCE.get();
        if (!tankHasSufficientOil) return false;

        if (output.getItem() != ModItems.BARREL.get()) return false;

        // If it's a barrel, check if it can accept more (simulate)
        IFluidHandlerItem barrelHandler = output.getCapability(FluidHandler.ITEM);
        if (barrelHandler == null) return false;
        // Try to simulate adding up to 1k mB from the tank -- if any would fit, recipe is valid
        int amountAvailable = Math.min(DRAIN, tank.getFluidAmount());
        FluidStack simulate = new FluidStack(tank.getFluid().getFluid(), amountAvailable);
        int canAccept = barrelHandler.fill(simulate, FluidAction.SIMULATE);
        return canAccept > 0;
    }

    /**
     * Crafts the output items from the internal tank fluid.
     */
    private void craftItem() {
        // We assume hasRecipe() was already called and returned true (so tank has oil)
        ItemStack output = inventory.getStackInSlot(OUTPUT_SLOT);

        // Determine how much we can try to move (max DRAIN per operation)
        int amountAvailable = Math.min(DRAIN, tank.getFluidAmount());
        FluidStack toAttempt = new FluidStack(tank.getFluid().getFluid(), amountAvailable);

        if (output.getItem() == ModItems.BARREL.get()) {
            // Top up the existing barrel in-place
            IFluidHandlerItem existingHandler = output.getCapability(FluidHandler.ITEM);
            if (existingHandler != null) {
                int canFill = existingHandler.fill(toAttempt, FluidAction.SIMULATE);
                if (canFill > 0) {
                    FluidStack drained = tank.drain(canFill, FluidAction.EXECUTE);
                    existingHandler.fill(drained, FluidAction.EXECUTE);
                    setChanged(); // TODO: Is this required in craftItem?
                }
            }
        }
    }

    /**
     * Resets the progress of the crafting operation.
     */
    private void resetProgress() {
        progress = 0;
        maxProgress = MAX_PROGRESS;
        setChanged();
    }

    // TODO: Are getUpdateTag and getUpdatePacket necessary?
}
