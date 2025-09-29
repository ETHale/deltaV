package com.deltav.deltavmod.block.energy.cable.cables;

import javax.annotation.Nullable;

import com.deltav.deltavmod.block.energy.cable.CableBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

// TODO battery needs to output up
// TODO crafting reciepe
// annoyingly only needed because of the block entity function
public class CopperCableBlock extends CableBlock{
    public CopperCableBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CopperCableBlockEntity(pos, state);
    }
}
