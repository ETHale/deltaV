package com.deltav.deltavmod.block.energy.cable.cables;

import com.deltav.deltavmod.block.energy.cable.CableBlockEntity;
import com.deltav.deltavmod.block.entity.ModBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class CopperCableBlockEntity extends CableBlockEntity{
    public CopperCableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.COPPER_CABLE_BE.get(), pos, state, 100, 20);
    }
}
