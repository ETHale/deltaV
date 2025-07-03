package com.deltav.deltavmod.block.custom;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.state.BlockState;

public class MoltenBedrockBlock extends Block{
    private static final int BUBBLE_COLUMN_CHECK_DELAY = 20;

    public MoltenBedrockBlock(Properties properties) {
        super(properties);
    }

    // set on fire and hurt when stepping on it
    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (!entity.fireImmune() && !entity.isCrouching() && entity instanceof LivingEntity) {
            entity.igniteForSeconds(3.0f);
            entity.hurt(level.damageSources().hotFloor(), 2.0F); // I know its depreciated but its what magma does so... 
        }
        super.stepOn(level, pos, state, entity);
    }

    @Override
    protected BlockState updateShape(
        BlockState state,
        LevelReader levelReader,
        ScheduledTickAccess tickAccess,
        BlockPos pos,
        Direction dir,
        BlockPos otherPos,
        BlockState otherState,
        RandomSource random
    ) {
        if (dir == Direction.UP && otherState.is(Blocks.WATER)) {
            tickAccess.scheduleTick(pos, this, 20);
        }

        return super.updateShape(state, levelReader, tickAccess, pos, dir, otherPos, otherState, random);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BubbleColumnBlock.updateColumn(level, pos.above(), state);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        level.scheduleTick(pos, this, 20);
    }
}
