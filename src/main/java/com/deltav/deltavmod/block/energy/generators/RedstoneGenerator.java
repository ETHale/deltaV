package com.deltav.deltavmod.block.energy.generators;

import javax.annotation.Nullable;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.redstone.Orientation;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

// Thinking it would be cool if this generated power when its POWERED state changed
public class RedstoneGenerator extends HorizontalDirectionalBlock {
    public static final int REDSTONE_GENERATOR_CAPACITY = 5;
    public static final MapCodec<RedstoneGenerator> CODEC = simpleCodec(RedstoneGenerator::new);

    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public RedstoneGenerator(Properties properties) {
        super(properties);
        this.registerDefaultState(stateDefinition.any()
            .setValue(FACING, Direction.NORTH)
            .setValue(POWERED, false));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, POWERED);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        this.neighborChanged(state, level, pos, null, null, false);
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, @Nullable Orientation orientation, boolean bool) {
        if (!level.isClientSide) {
            boolean flag = state.getValue(POWERED);
            if (flag != level.hasNeighborSignal(pos)) {
                if (!flag) {
                    level.setBlock(pos, state.cycle(POWERED), 2);
                }
            }
            level.scheduleTick(pos, this, 4);
        } 
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(POWERED) && !level.hasNeighborSignal(pos)) {
            level.setBlock(pos, state.cycle(POWERED), 2);
        }

        Direction facing = state.getValue(FACING);
        BlockPos targetPos = pos.relative(facing);

        IEnergyStorage be = level.getCapability(Capabilities.EnergyStorage.BLOCK, targetPos, Direction.SOUTH);
        if (state.getValue(POWERED)) {
            if (be != null && be.canReceive()) {
                be.receiveEnergy(REDSTONE_GENERATOR_CAPACITY, false);
            }
            level.scheduleTick(pos, this, 20);
        }
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }
}
