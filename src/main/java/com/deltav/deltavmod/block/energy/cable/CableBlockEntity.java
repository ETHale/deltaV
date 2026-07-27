package com.deltav.deltavmod.block.energy.cable;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.model.data.ModelData;
import net.neoforged.neoforge.model.data.ModelProperty;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.energy.EnergyHandlerUtil;
import net.neoforged.neoforge.transfer.energy.SimpleEnergyHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;

/*
 * Base cable block entity
 * 
 * credit to: https://www.mcjty.eu/docs/1.20.4_neo/ep5
 */
public abstract class CableBlockEntity extends BlockEntity {

    public static final String ENERGY_TAG = "Energy";

    protected final int maxTransfer;
    protected final int capacity;

    private final SimpleEnergyHandler energy;

    public static final ModelProperty<BlockState> FACADEID = new ModelProperty<>();
    public static final ModelProperty<ConnectorType> MODEL_NORTH = new ModelProperty<>();
    public static final ModelProperty<ConnectorType> MODEL_SOUTH = new ModelProperty<>();
    public static final ModelProperty<ConnectorType> MODEL_WEST  = new ModelProperty<>();
    public static final ModelProperty<ConnectorType> MODEL_EAST  = new ModelProperty<>();
    public static final ModelProperty<ConnectorType> MODEL_UP    = new ModelProperty<>();
    public static final ModelProperty<ConnectorType> MODEL_DOWN  = new ModelProperty<>();

    public CableBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int capacity, int maxTransfer) {
        super(type, pos, state);
        this.maxTransfer = maxTransfer;
        this.capacity = capacity;

        this.energy = new SimpleEnergyHandler(capacity, maxTransfer);
    }

    // Cached outputs
    private Map<BlockPos, Direction> outputs = null;

    // Traverse cable network and cache outputs 
    private void checkOutputs() {
        if (outputs == null) {
            outputs = new HashMap<>();
            traverse(worldPosition, cable -> {
                // Check for all energy receivers around this position (ignore cables)
                for (Direction direction : Direction.values()) {
                    BlockPos p = cable.getBlockPos().relative(direction);
                    BlockEntity te = level.getBlockEntity(p);
                    if (te != null && !(this.isSameCable(te))) {
                        Direction dir = direction.getOpposite();
                        EnergyHandler handler = level.getCapability(Capabilities.Energy.BLOCK, p, dir);
                        if (handler != null) {
                            if (!EnergyHandlerUtil.isFull(handler)) {
                                outputs.put(p, dir);
                            }
                        }
                    }
                }
            });
        }
    }

    private<T extends BlockEntity> boolean isSameCable(T blockEntity) {
        if (!(blockEntity instanceof CableBlockEntity)) {
            return false;
        }
        if (blockEntity.getBlockState().getBlock() == this.getBlockState().getBlock()) {
            return true;
        }
        return false;
    } 

    /**
     * Flags this block entity to update itself.
     * Will update the block model and wipe the known outputs of the cable
     */
    public void markDirty() {
        traverse(worldPosition, cable -> cable.outputs = null);
        requestModelDataUpdate();
    }

    // This is a generic function that will traverse all cables connected to this cable
    // and call the given consumer for each cable.
    private void traverse(BlockPos pos, Consumer<CableBlockEntity> consumer) {
        Set<BlockPos> traversed = new HashSet<>();
        traversed.add(pos);
        consumer.accept(this);
        traverse(pos, traversed, consumer);
    }

    // Continues the traversal of the cables with a set of known traversed cables
    private void traverse(BlockPos pos, Set<BlockPos> traversed, Consumer<CableBlockEntity> consumer) {
        for (Direction direction : Direction.values()) {
            BlockPos p = pos.relative(direction);
            if (!traversed.contains(p)) {
                traversed.add(p);
                if (level.getBlockEntity(p) instanceof CableBlockEntity cable) {
                    consumer.accept(cable);
                    cable.traverse(p, traversed, consumer);
                }
            }
        }
    }

    /*
     * Tick handler
     * If there is stored energy we check all possible outputs and distribute energy
     * modified from mcjty code to try to more fairly distribute the energy
     */
    public void tickServer() {
        if (energy.getAmountAsInt() <= 0) {
            return;
        }

        checkOutputs();
        if (outputs.isEmpty()) {
            return;
        }

        List<Map.Entry<EnergyHandler, Integer>> availableOutputs = new ArrayList<>();

        // Discover outputs and simulate how much each can receive
        for (Map.Entry<BlockPos, Direction> entry : outputs.entrySet()) {
            BlockPos pos = entry.getKey();
            Direction side = entry.getValue();

            if (pos.equals(getBlockPos())) {
                continue;
            }

            EnergyHandler handler = level.getCapability(Capabilities.Energy.BLOCK, pos, side);
            if (handler == null) {
                continue;
            }

            try (Transaction tx = Transaction.open(null)) {
                int accepted = handler.insert(maxTransfer, tx);

                // Don't commit -> simulation only
                if (accepted > 0) {
                    availableOutputs.add(new AbstractMap.SimpleEntry<>(handler, accepted));
                }
            }
        }

        if (availableOutputs.isEmpty()) {
            return;
        }

        availableOutputs.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        int remaining = energy.getAmountAsInt();
        int remainingOutputs = availableOutputs.size();

        for (Map.Entry<EnergyHandler, Integer> entry : availableOutputs) {
            if (remaining <= 0 || remainingOutputs <= 0) {
                break;
            }

            EnergyHandler handler = entry.getKey();
            int want = Math.min(entry.getValue(), remaining / remainingOutputs);

            if (want <= 0) {
                remainingOutputs--;
                continue;
            }

            try (Transaction tx = Transaction.open(null)) {
                // Destination accepts energy
                int inserted = handler.insert(want, tx);

                if (inserted <= 0) {
                    remainingOutputs--;
                    continue;
                }

                // Source extracts exactly that amount
                int extracted = energy.extract(inserted, tx);

                if (extracted == inserted) {
                    tx.commit();
                    remaining -= inserted;
                }

                remainingOutputs--;
            }
        }
    }

    @Override
    protected void saveAdditional(ValueOutput out) {
        super.saveAdditional(out);
        energy.serialize(out);
    }

    @Override
    public void loadAdditional(ValueInput in) {
        super.loadAdditional(in);
        energy.deserialize(in);
    }

    public EnergyHandler getEnergyHandler() {
        return this.energy;
    }

    @Override
    public ModelData getModelData() {
        return ModelData.builder()
            //.with(FACADEID, false) - ignore for now
            .with(MODEL_NORTH, this.getBlockState().getValueOrElse(CableBlock.NORTH, ConnectorType.NONE))
            .with(MODEL_SOUTH, this.getBlockState().getValueOrElse(CableBlock.SOUTH, ConnectorType.NONE))
            .with(MODEL_WEST, this.getBlockState().getValueOrElse(CableBlock.WEST, ConnectorType.NONE))
            .with(MODEL_EAST, this.getBlockState().getValueOrElse(CableBlock.EAST, ConnectorType.NONE))
            .with(MODEL_UP, this.getBlockState().getValueOrElse(CableBlock.UP, ConnectorType.NONE))
            .with(MODEL_DOWN, this.getBlockState().getValueOrElse(CableBlock.DOWN, ConnectorType.NONE))
            .build();
    }
}
