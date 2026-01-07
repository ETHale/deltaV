package com.deltav.deltavmod.block.custom;

import org.jetbrains.annotations.Nullable;

import com.deltav.deltavmod.block.ModBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbility;

/**
 * A rotated pillar block that is flammable and can be stripped with an axe
 */
public class FlammableRotatedPillarBlock extends RotatedPillarBlock {

    public FlammableRotatedPillarBlock(Properties properties) {
        super(properties);
    }
    
    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }

    /**
     * Called by the game when an item is used on the block
     * Will strip the log
     */
    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility itemAbility,
            boolean simulate) {

        // if we add more logs would be worth switching to a map
        if (context.getItemInHand().getItem() instanceof AxeItem) {
            if (state.is(ModBlocks.RUBBERWOOD_LOG.get())) {
                return ModBlocks.STRIPPED_RUBBERWOOD_LOG.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }
            else if (state.is(ModBlocks.RUBBERWOOD_WOOD.get())) {
                return ModBlocks.STRIPPED_RUBBERWOOD_WOOD.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }
        }
                
        return super.getToolModifiedState(state, context, itemAbility, simulate);
    }
}
