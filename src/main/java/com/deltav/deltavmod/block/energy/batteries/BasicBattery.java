package com.deltav.deltavmod.block.energy.batteries;

import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class BasicBattery extends Block implements IEnergyStorage{
    private EnergyStorage energyStorage;

    public BasicBattery(Properties properties, int capacity, int maxReceive, int maxExtract) {
        super(properties);
        this.energyStorage = new EnergyStorage(capacity, maxReceive, maxExtract);
    }

    @Override
    public int receiveEnergy(int toReceive, boolean simulate) {
        return energyStorage.receiveEnergy(toReceive, simulate);
    }
    @Override
    public int extractEnergy(int toExtract, boolean simulate) {
        return energyStorage.extractEnergy(toExtract, simulate);
    }
    @Override
    public int getEnergyStored() {
        return energyStorage.getEnergyStored();
    }
    @Override
    public int getMaxEnergyStored() {
        return energyStorage.getMaxEnergyStored();
    }
    @Override
    public boolean canExtract() {
        return energyStorage.canExtract();
    }
    @Override
    public boolean canReceive() {
        return energyStorage.canReceive();
    }
    
}
