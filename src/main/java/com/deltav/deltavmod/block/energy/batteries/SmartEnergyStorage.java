package com.deltav.deltavmod.block.energy.batteries;

import net.minecraft.util.Mth;
import net.neoforged.neoforge.energy.EnergyStorage;


// custom class to make some stuff easier
public class SmartEnergyStorage extends EnergyStorage {
    private final Runnable changeListener;

    public SmartEnergyStorage(int capacity, int maxTransfer, int maxExtract, Runnable changeListener) {
        super(capacity, maxTransfer, maxExtract);
        this.changeListener = changeListener;
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
        changeListener.run(); // Call this if you want it to notify the BE
    }
}
