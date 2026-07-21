package com.deltav.deltavmod.block.custom;
import javax.annotation.Nullable;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import com.deltav.deltavmod.DeltaV;
import com.deltav.deltavmod.block.ModBlocks;
import com.deltav.deltavmod.particle.ModParticlesTypes;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TreeTapperBlock extends HorizontalDirectionalBlock{
    public static final MapCodec<TreeTapperBlock> CODEC = simpleCodec(TreeTapperBlock::new);
    
    public static final BooleanProperty CONFIGURED = BooleanProperty.create("configured");

    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    private static final VoxelShape SHAPE_NORTH = Block.box(6.0, 2.0, 14.0, 10.0, 6.0, 16.0);
    private static final VoxelShape SHAPE_SOUTH = Block.box(6.0, 2.0, 0.0, 10.0, 6.0, 2.0);
    private static final VoxelShape SHAPE_EAST = Block.box(0.0, 2.0, 6.0, 2.0, 6.0, 10.0);
    private static final VoxelShape SHAPE_WEST = Block.box(14.0, 2.0, 6.0, 16.0, 6.0, 10.0);
    
    public TreeTapperBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(stateDefinition.any()
            .setValue(FACING, Direction.NORTH)
            .setValue(CONFIGURED, false));
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
        pBuilder.add(FACING, CONFIGURED);
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch(state.getValue(FACING)) {
            case SOUTH -> SHAPE_SOUTH;
            case EAST -> SHAPE_EAST;
            case WEST -> SHAPE_WEST;
            default -> SHAPE_NORTH;
        };
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        this.neighborChanged(state, level, pos, null, null, false);
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, @Nullable Orientation orientation, boolean bool) {
        if (!level.isClientSide()) {
            Direction facing = state.getValue(FACING).getOpposite();
            BlockPos adjacentPos = pos.relative(facing);
            BlockState adjacentState = level.getBlockState(adjacentPos);
            
            if (adjacentState.getBlock() == ModBlocks.STRIPPED_RUBBERWOOD_LOG.get()) {
                level.setBlock(pos, state.setValue(CONFIGURED, true), 2);
                level.scheduleTick(pos, this, 4);
            }
            else {
                level.setBlock(pos, state.setValue(CONFIGURED, false), 2);
            }
        }
    }

    // cauldron filling 
    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(CONFIGURED)) {
            BlockState below = level.getBlockState(pos.below());
            boolean cauldronPresent = 
                below.getBlock() == ModBlocks.LATEX_CAULDRON.get() ||
                below.getBlock() == Blocks.CAULDRON;

            if (cauldronPresent) {
                float chance = level.getRandom().nextFloat();
                if (chance < 0.01) {
                    try {
                        BlockState newState = ModBlocks.LATEX_CAULDRON.get().defaultBlockState();
                        if (below.getBlock() == Blocks.CAULDRON) {
                            newState = newState.setValue(LayeredCauldronBlock.LEVEL, 1);
                        } else {
                            int currentLevel = below.getValue(LayeredCauldronBlock.LEVEL);
                            newState = newState.setValue(LayeredCauldronBlock.LEVEL, Math.min(currentLevel + 1, 3));
                        }
                        level.setBlock(pos.below(), newState, UPDATE_ALL);
                    } catch (Exception e) {
                        DeltaV.LOGGER.error("Error processing latex drip", e);
                    }
                }
            }
            level.scheduleTick(pos, this, 4);
        }
    }

    // drip particle
    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(CONFIGURED)) {
            float x = pos.getX();
            float y = pos.getY() + 0.0625f;
            float z = pos.getZ();

            if (state.getValue(FACING) == Direction.NORTH) {
                x += 0.5f;
                z += 0.6875f;
            } else if (state.getValue(FACING) == Direction.SOUTH) {
                x += 0.5f;
                z += 0.3125;
            }
            else if (state.getValue(FACING) == Direction.WEST) {
                x += 0.6875f;
                z += 0.5f;
            }
            else if (state.getValue(FACING) == Direction.EAST) {
                x += 0.3125;
                z += 0.5f;
            }

            level.addParticle(
                ModParticlesTypes.LATEX_DRIP.get(),
                x,y,z,
                0, 
                0,
                0   
            );
        }
    }
}
