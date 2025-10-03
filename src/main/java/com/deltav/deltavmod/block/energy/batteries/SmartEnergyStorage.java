package com.deltav.deltavmod.block.energy.batteries;

import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;


// custom class to make some stuff easier
public class SmartEnergyStorage extends EnergyStorage {
    private final Runnable changeListener;
    private final Direction[] outputSides;

    public SmartEnergyStorage(int capacity, int maxTransfer, int maxExtract, Runnable changeListener, Direction[] outputSides) {
        super(capacity, maxTransfer, maxExtract);
        this.changeListener = changeListener;
        this.outputSides = outputSides;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int received = super.receiveEnergy(maxReceive, simulate);
        if (received > 0 && !simulate) {
            changeListener.run();
        }
        return received;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int extracted = super.extractEnergy(maxExtract, simulate);
        if (extracted > 0 && !simulate) {
            changeListener.run();
        }
        return extracted;
    }

    public void setEnergy(int energy) {
        this.energy = Mth.clamp(energy, 0, getMaxEnergyStored());
        changeListener.run();
    }

    /*
     * Returns an IEnergyStorage with certain access depending on the side
     * Set side to null for full access.
     */
    public IEnergyStorage getEnergyStorage(Direction side) {
        if (side == null) return this; // full access for side-less queries

        boolean extractAllowed = isOutputSide(side);
        boolean receiveAllowed = !extractAllowed;

        if (!extractAllowed && !receiveAllowed) return null;

        return new SideFilteredView(extractAllowed, receiveAllowed);
    }

    private boolean isOutputSide(Direction side) {
        for (Direction d : outputSides) if (d == side) return true;
        return false;
    }
    
    private final class SideFilteredView implements IEnergyStorage {
        private final boolean allowExtract;
        private final boolean allowReceive;

        SideFilteredView(boolean allowExtract, boolean allowReceive) {
            this.allowExtract = allowExtract;
            this.allowReceive = allowReceive;
        }

        @Override
        public int receiveEnergy(int maxReceive, boolean simulate) {
            return allowReceive ? SmartEnergyStorage.this.receiveEnergy(maxReceive, simulate) : 0;
        }

        @Override
        public int extractEnergy(int maxExtract, boolean simulate) {
            return allowExtract ? SmartEnergyStorage.this.extractEnergy(maxExtract, simulate) : 0;
        }

        @Override
        public int getEnergyStored() {
            return SmartEnergyStorage.this.getEnergyStored();
        }

        @Override
        public int getMaxEnergyStored() {
            return SmartEnergyStorage.this.getMaxEnergyStored();
        }

        @Override
        public boolean canReceive() {
            return allowReceive;
        }

        @Override
        public boolean canExtract() {
            return allowExtract;
        }
    }
}
