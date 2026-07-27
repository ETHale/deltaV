package com.deltav.deltavmod.block.entity;

import java.util.Map;

import javax.annotation.Nullable;

import com.deltav.deltavmod.block.custom.FractionatorBlock;
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
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.ResourceHandlerUtil;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.fluid.FluidStacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;

/**
 * Fractionator block entity. Holds the inventory and fluid tank for the fractionation process.
 * Handles crafting (i.e. converting internal tank contents into barrels)
 * 
 * @author Adam Crawley
 */
public class FractionatorBlockEntity extends BlockEntity implements MenuProvider {
    // Named Binary Tags for internal progress data
    private static final String ARROW_PROGRESS_NBT = "fractionator.arrow_progress";
    private static final String MAX_ARROW_PROGRESS_NBT = "fractionator.max_arrow_progress";
    private static final String RESIDUE_PROGRESS_NBT = "fractionator.residue_progress";
    private static final String MAX_RESIDUE_PROGRESS_NBT = "fractionator.max_residue_progress";

    public static final int MAX_TANK_CAPACITY = 10_000; // 10 buckets

    public static final int OIL_DRAIN     = 10; // mB/tick
    public static final int PETROL_GAIN   = 5;  // mB/tick
    public static final int NAPHTHA_GAIN  = 1;  // mB/tick
    public static final int KEROSENE_GAIN = 3;  // mB/tick

    public static final int INPUT_SLOT = 0;
    public static final int MIDDLE_OUTPUT_SLOT = 1;
    public static final int TOP_OUTPUT_SLOT = 2;
    public static final int BOTTOM_OUTPUT_SLOT = 3;
    public static final int RESIDUE_SLOT = 4;

    public static final FluidStack OIL_DRAIN_STACK = new FluidStack(ModFluids.OIL_SOURCE.get(), OIL_DRAIN);
    public static final Map<Integer, FluidStack> OUTPUTS = Map.of(
        TOP_OUTPUT_SLOT, new FluidStack(ModFluids.NAPHTHA_SOURCE.get(), NAPHTHA_GAIN),
        MIDDLE_OUTPUT_SLOT, new FluidStack(ModFluids.PETROL_SOURCE.get(), PETROL_GAIN),
        BOTTOM_OUTPUT_SLOT, new FluidStack(ModFluids.KEROSENE_SOURCE.get(), KEROSENE_GAIN)
    );

    // Arrow progress rate max. 1 progress is applied every tick up to ARROW_MAX_PROGRESS, then loops back to 0
    // This setting is PURELY GUI, and does NOT change the rate at which oil is processed
    private static final int ARROW_MAX_PROGRESS = 25;

    // Amount of ticks that pass before creating residue from fractionating process
    private static final int RESIDUE_MAX_PROGRESS = 50;

    // Public since menu may need to create a Fractionator block entity to open the menu
    // Number of items within synchronised ContainerData
    public static final int INTERNAL_DATA_COUNT = 4;

    // Holds all items within the fractionator
    public final ItemStacksResourceHandler inventory = new ItemStacksResourceHandler(5) {
        @Override
        protected int getCapacity(int slot, ItemResource stack) {
            return 1; // In every slot only 1 item can be stored
        }

        @Override
        protected void onContentsChanged(int slot, ItemStack prev) {
            setChanged();
            if (!level.isClientSide()) {
                // Sync the inventory with the client
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    // Internal progress state
    private int arrowProgress = 0;
    private int arrowMaxProgress = ARROW_MAX_PROGRESS;
    private int residueProgress = 0;
    private int residueMaxProgress = RESIDUE_MAX_PROGRESS;

    // Container to synchronise data between client and server
    private ContainerData data;
    private final FluidStacksResourceHandler tank = new FluidStacksResourceHandler(1, MAX_TANK_CAPACITY) {
        @Override
        protected void onContentsChanged(int index, FluidStack stack) {
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
                    case 0 -> tank.getAmountAsInt(0);
                    case 1 -> tank.getCapacityAsInt(0, null);
                    case 2 -> arrowProgress;
                    case 3 -> arrowMaxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> tank.set(0, FluidResource.of(new FluidStack(ModFluids.OIL_SOURCE.get(), value)), value);
                    case 1 -> {}
                    case 2 -> arrowProgress = value;
                    case 3 -> arrowMaxProgress = value;
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
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getResource(i).toStack();
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

        // Arrow progress
        out.putInt(ARROW_PROGRESS_NBT, arrowProgress);
        out.putInt(MAX_ARROW_PROGRESS_NBT, arrowMaxProgress);

        // Residue progress
        out.putInt(RESIDUE_PROGRESS_NBT, residueProgress);
        out.putInt(MAX_RESIDUE_PROGRESS_NBT, residueMaxProgress);
    }

    @Override
    protected void loadAdditional(ValueInput in) {
        // Internal state
        super.loadAdditional(in);
        inventory.deserialize(in);
        tank.deserialize(in);

        // Arrow progress
        arrowProgress = in.getIntOr(ARROW_PROGRESS_NBT, 0);
        arrowMaxProgress = in.getIntOr(MAX_ARROW_PROGRESS_NBT, arrowMaxProgress);

        // Residue progress
        residueProgress = in.getIntOr(RESIDUE_PROGRESS_NBT, 0);
        residueMaxProgress = in.getIntOr(MAX_RESIDUE_PROGRESS_NBT, residueMaxProgress);
    }

    public FluidStacksResourceHandler getFluidHandler(@Nullable Direction side) {
        return tank;
    }

    /**
     * Runs on every tick, handles conversion from oil buckets to internal tank,
     * and internal tank to output barrels if applicable.
     * 
     * @note invoked from the overriden block getTicket function in {@link FractionatorBlock}
     */
    public void tick() {
        if (level == null || level.isClientSide()) return;
        arrowProgress++;
        residueProgress++;

        drainToTankFromInput();

        if (hasRecipe()) {
            // Add arrow progress
            if (arrowProgress >= arrowMaxProgress) {
                resetArrowProgress();
            }

            if (residueProgress >= residueMaxProgress) {
                createResidue();
                resetResidueProgress();
            }

            processOutputs();
        } else {
            resetArrowProgress();
        }
    }

    /*
     * Attempts to drain fluid from the input tank into the internal tank.
     */
    private void drainToTankFromInput() {
        ItemStack input = inventory.getResource(INPUT_SLOT).toStack();
        if (input.getItem() == ModItems.OIL_BUCKET.get()) {
            ResourceHandler<FluidResource> itemHandler = input.getCapability(Capabilities.Fluid.ITEM, null);
            if (itemHandler != null) {
                ResourceHandlerUtil.move(itemHandler, tank, (f) -> f.is(ModFluids.OIL_SOURCE), 1000, null);
            }
        }
    }

    /**
     * Checks if the current inventory setup has a valid setup for
     * oil processing.
     * 
     * @return true if a valid recipe is present, false otherwise.
     */
    private boolean hasRecipe() {
        // Check if internal tank can be drained
        boolean tankHasSufficientOil = (tank.getAmountAsInt(0) >= OIL_DRAIN_STACK.getAmount())
            && (tank.getResource(0).getFluid() == OIL_DRAIN_STACK.getFluid());
        if (!tankHasSufficientOil) return false;

        // Check that residue slot is not full and contains Gloopy Residue
        ItemStack residue = inventory.getResource(RESIDUE_SLOT).toStack();
        if (!residue.isEmpty()) {
            if (residue.getItem() != ModItems.GLOOPY_RESIDUE.get()) return false;
            if (residue.getCount() >= residue.getMaxStackSize()) return false;
        }

        // Check every output slot for valid barrels
        for (int SLOT : OUTPUTS.keySet()) {
            ItemStack output = inventory.getResource(SLOT).toStack();

            // If the output is not a barrel, we can't process into output
            if (output.getItem() != ModItems.BARREL.get()) return false;

            // Double-check barrel has fluid handler
            ResourceHandler<FluidResource> barrelHandler = output.getCapability(Capabilities.Fluid.ITEM, null);
            if (barrelHandler == null) return false;

            // Check if barrel can accept more (simulate)
            try (Transaction tr = Transaction.openRoot()) {
                // Convert FluidStack to FluidResource and request insertion of that amount
                FluidStack outStack = OUTPUTS.get(SLOT);
                int canAccept = barrelHandler.insert(FluidResource.of(outStack), outStack.getAmount(), tr);
                if (canAccept <= 0) return false;
            }
        }
        return true;
    }

    /**
     * Processes conversion from internal oil tank to output barrels.
     * 
     * @note Assumes that hasRecipe() was already called and returned true, i.e.
     *       input tank has enough oil, all barrels are valid and can accept more
     */
    private void processOutputs() {
        try (Transaction tr = Transaction.openRoot()) {
            // Drain from internal tank
            tank.extract(FluidResource.of(OIL_DRAIN_STACK), OIL_DRAIN, tr);
    
            // Iterate gain into each output barrel
            for (int SLOT : OUTPUTS.keySet()) {
                ItemStack output = inventory.getResource(SLOT).toStack();
                // Top up the existing barrel in-place
                ResourceHandler<FluidResource> existingHandler = output.getCapability(Capabilities.Fluid.ITEM, null);
                existingHandler.insert(FluidResource.of(OUTPUTS.get(SLOT)), SLOT, tr);
                setChanged(); // TODO: Is this required in processOil?
            }
            tr.commit();
        }
    }

    private void createResidue() {
        ItemStack residue = inventory.getResource(RESIDUE_SLOT).toStack();
        if (residue.isEmpty()) {
            residue = new ItemStack(ModItems.GLOOPY_RESIDUE.get());
        } else {
            residue.grow(1);
        }
        inventory.set(RESIDUE_SLOT, ItemResource.of(residue), residue.count());
    }

    /**
     * Resets the progress of the arrow crafting operation.
     */
    private void resetArrowProgress() {
        arrowProgress = 0;
        arrowMaxProgress = ARROW_MAX_PROGRESS;
    }

    /**
     * Resets the progress of the residue crafting operation.
     */
    private void resetResidueProgress() {
        residueProgress = 0;
        residueMaxProgress = RESIDUE_MAX_PROGRESS;
    }

    // TODO: Are getUpdateTag and getUpdatePacket necessary?
}
