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
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.model.data.ModelData;
import net.neoforged.neoforge.model.data.ModelProperty;

/*
 * Base cable block entity
 * 
 * credit to: https://www.mcjty.eu/docs/1.20.4_neo/ep5
 */
public abstract class CableBlockEntity extends BlockEntity {

    public static final String ENERGY_TAG = "Energy";

    protected final int maxTransfer;
    protected final int capacity;

    private final EnergyStorage energy;

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

        this.energy = new EnergyStorage(maxTransfer, capacity) {
            @Override
            public int extractEnergy(int toExtract, boolean simulate) {
                int energyExtracted = Math.min(this.energy, Math.min(this.maxExtract, toExtract));
                if (!simulate)
                    this.energy -= energyExtracted;
                return energyExtracted;
            }

            @Override
            public int receiveEnergy(int maxReceive, boolean simulate) {
                if (!simulate)
                    setChanged();
                return super.receiveEnergy(maxReceive, simulate);
            }

            @Override
            public boolean canExtract() {
                return false;
            }

            @Override
            public boolean canReceive() {
                return true;
            }
        };
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
                    if (te != null && !(te instanceof CableBlockEntity)) {
                        Direction dir = direction.getOpposite();
                        IEnergyStorage handler = level.getCapability(Capabilities.EnergyStorage.BLOCK, p, dir);
                        if (handler != null) {
                            if (handler.canReceive()) {
                                outputs.put(p, dir);
                            }
                        }
                    }
                }
            });
        }
    }

    public void markDirty() {
        traverse(worldPosition, cable -> cable.outputs = null);
    }

    // This is a generic function that will traverse all cables connected to this cable
    // and call the given consumer for each cable.
    private void traverse(BlockPos pos, Consumer<CableBlockEntity> consumer) {
        Set<BlockPos> traversed = new HashSet<>();
        traversed.add(pos);
        consumer.accept(this);
        traverse(pos, traversed, consumer);
    }

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
        if (energy.getEnergyStored() > 0) {
            // Only do something if we have energy
            checkOutputs();
            if (!outputs.isEmpty()) {
                // calculate avaliable outputs
                // and get avaliable handlers - ordered by energy able to be recieved
                List<Map.Entry<IEnergyStorage, Integer>> outputsAvaliable = new ArrayList<>();
                for (Map.Entry<BlockPos, Direction> entry : outputs.entrySet()) {
                    BlockPos p = entry.getKey();
                    Direction dir = entry.getValue();

                    if (p.equals(this.getBlockPos())) continue;

                    IEnergyStorage handler = level.getCapability(Capabilities.EnergyStorage.BLOCK, p, dir);
                    
                    if (handler.canExtract()) continue; // up to producers to give us energy

                    if (handler != null) {
                        if (handler.canReceive()) {
                            int received = handler.receiveEnergy(this.maxTransfer, true);
                            if (received > 0)
                                outputsAvaliable.add(new AbstractMap.SimpleEntry<>(handler, received));
                        }
                    }
                }

                if (outputsAvaliable.isEmpty()) return;

                outputsAvaliable.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
                // go through each handler giving it as much as possible
                // this should account for when blocks don't take in the maximum amount
                int remaining = energy.getEnergyStored();
                int size = outputsAvaliable.size();
                for (Map.Entry<IEnergyStorage,Integer> e : outputsAvaliable) {
                    if (remaining <= 0) break;
                    IEnergyStorage handler = e.getKey();
                    int want = Math.min(e.getValue(), (int)(remaining / size));
                    int sent = handler.receiveEnergy(want, false);
                    if (sent > 0) {
                        energy.extractEnergy(sent, false);
                        remaining -= sent;
                        size -= 1;
                    }
                }
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

    public IEnergyStorage getEnergyHandler() {
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
