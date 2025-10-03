package com.deltav.deltavmod.block.entity;

import javax.annotation.Nullable;

import com.deltav.deltavmod.block.energy.batteries.SmartEnergyStorage;
import com.deltav.deltavmod.menu.BasicBatteryMenu;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class BasicBatteryBlockEntity extends BlockEntity implements MenuProvider{
    private SmartEnergyStorage energyStorage;
    public static final int MAX_TRANSFER = 80;
    public BasicBatteryBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.BASIC_BATTERY_BE.get(), pos, blockState);
        Direction[] dirs = new Direction[1];
        dirs[0] = Direction.UP;
        this.energyStorage = new SmartEnergyStorage(1500, MAX_TRANSFER, MAX_TRANSFER, this::setChanged, dirs);        
    }

    public IEnergyStorage getEnergyStorage(@Nullable Direction side) {
        return energyStorage.getEnergyStorage(side);
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

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        BlockPos targetPos = blockPos.relative(Direction.UP);

        IEnergyStorage be = level.getCapability(Capabilities.EnergyStorage.BLOCK, targetPos, Direction.DOWN);
        if (be != null && be.canReceive() && this.energyStorage.canExtract()) {
            int extracted = this.energyStorage.extractEnergy(MAX_TRANSFER, true);
            int amount = be.receiveEnergy(extracted, false);
            this.energyStorage.extractEnergy(amount, false);
        }
        level.scheduleTick(blockPos, blockState.getBlock(), 20);
    }

}
