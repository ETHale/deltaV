package com.deltav.deltavmod.block.energy.cable.cables;

import com.deltav.deltavmod.block.energy.cable.CableBlockEntity;
import com.deltav.deltavmod.block.entity.ModBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class InsulatedCopperCableBlockEntity extends CableBlockEntity{
    public InsulatedCopperCableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.INSULATED_COPPER_CABLE_BE.get(), pos, state, 300, 50);
    }
}
