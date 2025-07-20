package com.deltav.deltavmod.block.energy.batteries;

import javax.annotation.Nullable;

import com.deltav.deltavmod.block.entity.BasicBatteryBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BasicBattery extends Block implements EntityBlock{

    public BasicBattery(Properties properties) {
        super(properties);
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BasicBatteryBlockEntity(pos, state);
    }
    
}
