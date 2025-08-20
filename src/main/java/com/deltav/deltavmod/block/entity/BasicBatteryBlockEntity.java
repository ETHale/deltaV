package com.deltav.deltavmod.block.entity;

import javax.annotation.Nullable;

import com.deltav.deltavmod.block.energy.batteries.SmartEnergyStorage;
import com.deltav.deltavmod.menu.BasicBatteryMenu;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class BasicBatteryBlockEntity extends BlockEntity implements MenuProvider{
    private SmartEnergyStorage energyStorage;
    public BasicBatteryBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.BASIC_BATTERY_BE.get(), pos, blockState);
        this.energyStorage = new SmartEnergyStorage(1500, 80, 80, this::setChanged);        
    }

    public IEnergyStorage getEnergyStorage(@Nullable Direction side) {
        return energyStorage;
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        return new BasicBatteryMenu(id, inv, this);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("gui.deltav.basic_battery");
    }

    @Override
    protected void saveAdditional(ValueOutput out) {
        super.saveAdditional(out);
        out.putInt("Energy", energyStorage.getEnergyStored());
    }

    @Override
    protected void loadAdditional(ValueInput in) {
        super.loadAdditional(in);
        energyStorage.setEnergy(in.getIntOr("Energy", 0));
    }

}
