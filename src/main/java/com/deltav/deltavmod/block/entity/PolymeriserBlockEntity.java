package com.deltav.deltavmod.block.entity;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.deltav.deltavmod.block.custom.PolymeriserBlock;
import com.deltav.deltavmod.fluid.ModFluids;
import com.deltav.deltavmod.item.BarrelItem;
import com.deltav.deltavmod.item.ModItems;
import com.deltav.deltavmod.menu.PolymeriserMenu;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.capabilities.Capabilities.FluidHandler;
import net.neoforged.neoforge.common.util.ValueIOSerializable;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.items.ItemStackHandler;

/**
 * Polymeriser block entity. Holds the inventory and fluid tank for the polymerisation process.
 * Handles crafting (i.e. converting naphtha + water into plastic pellets)
 * 
 * @author Adam Crawley
 */
public class PolymeriserBlockEntity extends BlockEntity implements MenuProvider {
    // Named Binary Tags for internal progress data
    private static final String PROGRESS_NBT = "polymeriser.progress";
    private static final String MAX_PROGRESS_NBT = "polymeriser.max_progress";

    private static final int NAPHTHA_TANK_CAPACITY = 5_000; // 5 buckets
    private static final int WATER_TANK_CAPACITY = 5_000; // 5 buckets

    private static final int NAPHTHA_DRAIN = 100; // mB/tick
    private static final int WATER_DRAIN   = 100; // mB/tick

    private static final int NAPHTHA_IN_SLOT = 0;
    private static final int WATER_IN_SLOT = 1;
    private static final int CATALYST_SLOT = 2;
    private static final int PLASTIC_OUT_SLOT = 3;

    private static final FluidStack NAPHTHA_DRAIN_STACK = new FluidStack(ModFluids.NAPHTHA_SOURCE.get(), NAPHTHA_DRAIN);
    private static final FluidStack WATER_DRAIN_STACK = new FluidStack(Fluids.WATER, WATER_DRAIN);

    private static final List<Item> VALID_CATALYSTS = List.of(
        ModItems.GOLD_DUST.get()
        // ModItems.COPPER_DUST.get(),
        // ModItems.TITANIUM_DUST.get()
    );

    // Plastic creation progress rate max. 1 progress is applied every tick up to
    // MAX_PROGRESS, then loops back to 0
    private static final int MAX_PROGRESS = 50;

    // Public since menu may need to create a Polymeriser block entity to open the menu
    // Number of items within synchronised ContainerData
    public static final int INTERNAL_DATA_COUNT = 6;

    // Holds all items within the polymeriser
    public final ItemStackHandler inventory = new ItemStackHandler(4) {
        @Override
        protected int getStackLimit(int slot, ItemStack stack) {
            return 1; // In every slot only 1 item can be stored
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide) {
                // Sync the inventory with the client
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    // Internal progress state
    private int progress = 0;
    private int maxProgress = MAX_PROGRESS;

    // Container to synchronise data between client and server
    private ContainerData data;
    private final PolymeriserTank tank = new PolymeriserTank(NAPHTHA_TANK_CAPACITY, WATER_TANK_CAPACITY);

    /**
     * Constructor for PolymeriserBlockEntity
     *
     * @param pos   position the block is placed
     * @param state state of the block
     */
    public PolymeriserBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.POLYMERISER_BE.get(), pos, state);
        data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> tank.getFluidInTank(0).getAmount();
                    case 1 -> tank.getFluidInTank(1).getAmount();
                    case 2 -> tank.getTankCapacity(0);
                    case 3 -> tank.getTankCapacity(1);
                    case 4 -> progress;
                    case 5 -> maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> tank.setFluid(new FluidStack(ModFluids.NAPHTHA_SOURCE.get(), value));
                    case 1 -> tank.setFluid(new FluidStack(Fluids.WATER, value));
                    case 2 -> tank.setTankCapacity(0, value);
                    case 3 -> tank.setTankCapacity(1, value);
                    case 4 -> progress = value;
                    case 5 -> maxProgress = value;
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
        return new PolymeriserMenu(id, inv, this, data);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("gui.deltav.polymeriser");
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
        // Internal state
        super.saveAdditional(out);
        inventory.serialize(out);
        tank.serialize(out);

        // Progress
        out.putInt(PROGRESS_NBT, progress);
        out.putInt(MAX_PROGRESS_NBT, maxProgress);
    }

    @Override
    protected void loadAdditional(ValueInput in) {
        // Internal state
        super.loadAdditional(in);
        inventory.deserialize(in);
        tank.deserialize(in);

        // Progress
        progress = in.getIntOr(PROGRESS_NBT, 0);
        maxProgress = in.getIntOr(MAX_PROGRESS_NBT, maxProgress);
    }

    public IFluidHandler getFluidHandler(@Nullable Direction side) {
        return tank;
    }

    /**
     * Runs on every tick, handles conversion from naphtha buckets/barrel and water
     * to internal tank, and internal tank to plastic barrels if applicable.
     *
     * @note invoked from the overriden block getTicket function in {@link PolymeriserBlock}
     */
    public void tick() {
        if (level == null || level.isClientSide) return;
        progress++;

        drainToTankFromInput();

        if (hasRecipe()) {

            if (progress >= maxProgress) {
                craft();
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    /*
     * Attempts to drain fluid from the input tank into the internal tank.
     */
    private void drainToTankFromInput() {
        // Drain naphtha from bucket / barrel into internal tank
        ItemStack naphthaInput = inventory.getStackInSlot(NAPHTHA_IN_SLOT);
        if (naphthaInput.getItem() == ModItems.NAPHTHA_BUCKET.get()) {
            IFluidHandlerItem itemHandler = FluidHandler.ITEM.getCapability(naphthaInput, null);
            if (itemHandler != null) {
                // Drain the entire bucket (1000 mB)
                FluidStack drained = itemHandler.drain(1000, FluidAction.EXECUTE);
                if (drained != null && !drained.isEmpty()) {
                    int filled = tank.fill(drained, FluidAction.EXECUTE);
                    if (filled > 0) {
                        ItemStack result = itemHandler.getContainer();
                        inventory.setStackInSlot(NAPHTHA_IN_SLOT, result);
                        setChanged();
                    }
                }
            }
        } else if (naphthaInput.getItem() == ModItems.BARREL.get()) {
            IFluidHandlerItem itemHandler = FluidHandler.ITEM.getCapability(naphthaInput, null);
            if (itemHandler != null) {
                FluidStack drained = itemHandler.drain(BarrelItem.CAPACITY, FluidAction.EXECUTE);
                if (drained != null) {
                    int filled = tank.fill(drained, FluidAction.EXECUTE);
                    if (filled > 0) {
                        ItemStack result = itemHandler.getContainer();
                        inventory.setStackInSlot(NAPHTHA_IN_SLOT, result);
                        setChanged();
                    }
                }
            }
        }

        // Drain water from bucket into internal tank
        ItemStack waterInput = inventory.getStackInSlot(WATER_IN_SLOT);
        if (waterInput.getItem() == Items.WATER_BUCKET) {
            IFluidHandlerItem itemHandler = FluidHandler.ITEM.getCapability(waterInput, null);
            if (itemHandler != null) {
                // Drain the entire bucket (1000 mB)
                FluidStack drained = itemHandler.drain(1000, FluidAction.EXECUTE);
                if (drained != null && !drained.isEmpty()) {
                    int filled = tank.fill(drained, FluidAction.EXECUTE);
                    if (filled > 0) {
                        ItemStack result = itemHandler.getContainer();
                        inventory.setStackInSlot(WATER_IN_SLOT, result);
                        setChanged();
                    }
                }
            }
        }
    }

    /**
     * Checks if the current inventory setup has a valid setup for
     * polymerisation processing.
     * 
     * @return true if a valid recipe is present, false otherwise.
     */
    private boolean hasRecipe() {
        // Check if internal tanks can be drained
        boolean tankHasSufficientFluids = (tank.getFluidInTank(0).getAmount() >= NAPHTHA_DRAIN_STACK.getAmount())
            && (tank.getFluidInTank(1).getAmount() >= WATER_DRAIN_STACK.getAmount());
        if (!tankHasSufficientFluids) return false;

        // Check suitable catalyst given
        ItemStack catalyst = inventory.getStackInSlot(CATALYST_SLOT);
        if (catalyst.isEmpty() || (!VALID_CATALYSTS.contains(catalyst.getItem()))) return false;

        // If the output is not plastic pellets, we can't process into output
        ItemStack output = inventory.getStackInSlot(PLASTIC_OUT_SLOT);
        return (output.isEmpty() || output.getItem() == ModItems.PLASTIC_PELLETS.get());
    }

    /**
     * Processes conversion from internal tanks to plastic pellets.
     * 
     * @note Assumes that hasRecipe() was already called and returned true, i.e.
     *       input tank has enough fluids, catalyst given, output has space
     */
    private void craft() {
        // Drain from internal tank
        tank.drain(NAPHTHA_DRAIN_STACK, FluidAction.EXECUTE);
        tank.drain(WATER_DRAIN_STACK, FluidAction.EXECUTE);

        // Put plastic pellets into plastic output slot
        ItemStack output = inventory.getStackInSlot(PLASTIC_OUT_SLOT);
        if (output.isEmpty()) {
            inventory.setStackInSlot(PLASTIC_OUT_SLOT, new ItemStack(ModItems.PLASTIC_PELLETS.get(), 1));
        } else if (output.getItem() == ModItems.PLASTIC_PELLETS.get() && output.getCount() < output.getMaxStackSize()) {
            output.grow(1);
            inventory.setStackInSlot(PLASTIC_OUT_SLOT, output);
        }

        setChanged(); // TODO: Is this required in this function?
    }

    /**
     * Resets the crafting progress.
     */
    private void resetProgress() {
        progress = 0;
        maxProgress = MAX_PROGRESS;
    }

    // TODO: Are getUpdateTag and getUpdatePacket necessary?

    /**
     * Custom fluid tank implementation for the polymeriser. Originally from
     * import net.neoforged.neoforge.fluids.capability.templates.FluidTank, but
     * with FluidTank interface removed (since it only allows 1 tank).
     * Modified to have two tanks (one for Naphtha and the other for Water)
     */
    private class PolymeriserTank implements IFluidHandler, ValueIOSerializable {
        protected FluidStack naphta;
        protected FluidStack water;
        protected int naphtaCapacity;
        protected int waterCapacity;

        public PolymeriserTank(int naphtaCapacity, int waterCapacity) {
            this.naphtaCapacity = naphtaCapacity;
            this.waterCapacity = waterCapacity;

            this.naphta = new FluidStack(ModFluids.NAPHTHA_SOURCE.get(), 0);
            this.water = new FluidStack(Fluids.WATER, 0);
        }

        public void deserialize(ValueInput input) {
            this.naphta = (FluidStack)input.read("Naphta", FluidStack.CODEC).orElse(FluidStack.EMPTY);
            this.water = (FluidStack)input.read("Water", FluidStack.CODEC).orElse(FluidStack.EMPTY);
        }

        public void serialize(ValueOutput output) {
            if (!this.naphta.isEmpty()) {
                output.store("Naphta", FluidStack.CODEC, this.naphta);
            }

            if (!this.water.isEmpty()) {
                output.store("Water", FluidStack.CODEC, this.water);
            }

        }

        public int getTanks() {
            return 2;
        }

        public FluidStack getFluidInTank(int tank) {
            if (tank == 0) {
                return this.naphta;
            } else if (tank == 1) {
                return this.water;
            }
            return FluidStack.EMPTY;
        }

        public int getTankCapacity(int tank) {
            if (tank == 0) {
                return this.naphtaCapacity;
            } else if (tank == 1) {
                return this.waterCapacity;
            }
            return 0;
        }

        public void setTankCapacity(int tank, int capacity){
            if (tank == 0) {
                this.naphtaCapacity = capacity;
                this.naphta.setAmount(Math.min(this.naphta.getAmount(), capacity));
            } else if (tank == 1) {
                this.waterCapacity = capacity;
                this.water.setAmount(Math.min(this.water.getAmount(), capacity));
            }
            this.onContentsChanged();
        }

        public boolean isFluidValid(int tank, FluidStack stack) {
            return FluidStack.isSameFluidSameComponents(getFluidInTank(tank), stack);
        }

        public int fill(FluidStack resource, IFluidHandler.FluidAction action) {
            boolean isNaptha = FluidStack.isSameFluidSameComponents(resource, this.naphta);
            boolean isWater = FluidStack.isSameFluidSameComponents(resource, this.water);
            if (!resource.isEmpty() && (isNaptha || isWater)) {

                FluidStack toFill = isNaptha ? this.naphta : this.water;
                int capacity = isNaptha ? this.naphtaCapacity : this.waterCapacity;

                if (action.simulate()) {
                    if (toFill.isEmpty()) {
                        return Math.min(capacity, resource.getAmount());
                    } else {
                        return Math.min(capacity - toFill.getAmount(), resource.getAmount());
                    }
                } else if (toFill.isEmpty()) {
                    toFill = resource.copyWithAmount(Math.min(capacity, resource.getAmount()));
                    this.onContentsChanged();
                    return toFill.getAmount();
                } else if (!FluidStack.isSameFluidSameComponents(toFill, resource)) {
                    return 0;
                } else {
                    int filled = capacity - toFill.getAmount();
                    if (resource.getAmount() < filled) {
                        toFill.grow(resource.getAmount());
                        filled = resource.getAmount();
                    } else {
                        toFill.setAmount(capacity);
                    }

                    if (filled > 0) {
                        this.onContentsChanged();
                    }

                    return filled;
                }
            } else {
                return 0;
            }
        }

        public FluidStack drain(FluidStack resource, IFluidHandler.FluidAction action) {
            FluidStack stack = FluidStack.EMPTY; // return value

            if (FluidStack.isSameFluidSameComponents(resource, this.naphta)) {
                // Draining from naphtha
                int drained = resource.getAmount();
                if (this.naphta.getAmount() < drained) {
                    drained = this.naphta.getAmount();
                }

                stack = this.naphta.copyWithAmount(drained);
                if (action.execute() && drained > 0) {
                    this.naphta.shrink(drained);
                    this.onContentsChanged();
                }
            } else if (FluidStack.isSameFluidSameComponents(resource, this.water)) {
                // Draining from water
                int drained = resource.getAmount();
                if (this.water.getAmount() < drained) {
                    drained = this.water.getAmount();
                }

                stack = this.water.copyWithAmount(drained);
                if (action.execute() && drained > 0) {
                    this.water.shrink(drained);
                    this.onContentsChanged();
                }
            }
            return stack;
        }


        public FluidStack drain(int maxDrain, IFluidHandler.FluidAction action) {
            throw new UnsupportedOperationException("drain");
        }

        protected void onContentsChanged() {
            setChanged();
        }

        public int getSpace(int tank) {
            if (tank == 0) {
                return Math.max(0, this.naphtaCapacity - this.naphta.getAmount());
            } else if (tank == 1) {
                return Math.max(0, this.waterCapacity - this.water.getAmount());
            } else {
                return 0;
            }
        }

        public void setFluid(FluidStack fluid) {
            if (FluidStack.isSameFluidSameComponents(fluid, this.naphta)) {
                this.naphta = fluid;
            } else if (FluidStack.isSameFluidSameComponents(fluid, this.water)) {
                this.water = fluid;
            }
        }
    }
}
