package com.deltav.deltavmod.block.energy.cable.cables;

import javax.annotation.Nullable;

import com.deltav.deltavmod.block.energy.cable.CableBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

// annoyingly only needed because of the block entity function
public class InsulatedCopperCableBlock extends CableBlock{
    public InsulatedCopperCableBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new InsulatedCopperCableBlockEntity(pos, state);
    }
}
