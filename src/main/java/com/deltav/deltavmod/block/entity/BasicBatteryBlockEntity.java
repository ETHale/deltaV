package com.deltav.deltavmod.block.entity;

import javax.annotation.Nullable;

import com.deltav.deltavmod.DeltaV;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class BasicBatteryBlockEntity extends BlockEntity implements IEnergyStorage{
    private EnergyStorage energyStorage;
    public BasicBatteryBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.BASIC_BATTERY_BE.get(), pos, blockState);
        this.energyStorage = new EnergyStorage(1500, 80, 80);        
    }

    @Override
    public int receiveEnergy(int toReceive, boolean simulate) {
        DeltaV.LOGGER.debug(String.valueOf(this.getEnergyStored()));
        return this.energyStorage.receiveEnergy(toReceive, simulate);
    }
    @Override
    public int extractEnergy(int toExtract, boolean simulate) {
        return this.energyStorage.extractEnergy(toExtract, simulate);
    }
    @Override
    public int getEnergyStored() {
        return this.energyStorage.getEnergyStored();
    }
    @Override
    public int getMaxEnergyStored() {
        return this.energyStorage.getMaxEnergyStored();
    }
    @Override
    public boolean canExtract() {
        return this.energyStorage.canExtract();
    }
    @Override
    public boolean canReceive() {
        return this.energyStorage.canReceive();
    }

    public IEnergyStorage getEnergyStorage(@Nullable Direction side) {
        return energyStorage;
    }
}
