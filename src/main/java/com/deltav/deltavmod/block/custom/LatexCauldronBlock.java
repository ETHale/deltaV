package com.deltav.deltavmod.block.custom;

import com.deltav.deltavmod.data.DeltaVCauldronRegistry;
import com.deltav.deltavmod.item.ModItems;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.redstone.Orientation;

public class LatexCauldronBlock extends LayeredCauldronBlock {

    public LatexCauldronBlock(BlockBehaviour.Properties properties) {
        super(Biome.Precipitation.RAIN, DeltaVCauldronRegistry.LATEX_INTERACTIONS, properties);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        if (state.getValue(LEVEL) == 3 && this.isLitCampfireBelow(level, pos)) {
            level.scheduleTick(pos, this, 20);
        }
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, @Nullable Orientation orientation, boolean movedByPiston) {
        super.neighborChanged(state, level, pos, block, orientation, movedByPiston);
        if (state.getValue(LEVEL) == 3 && this.isLitCampfireBelow(level, pos)) {
            level.scheduleTick(pos, this, 20);
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(LEVEL) != 3 || !this.isLitCampfireBelow(level, pos)) {
            return;
        }
        float chance = random.nextFloat();

        if (chance <= 0.5) {
            level.playSound(null, pos, SoundEvents.BUBBLE_COLUMN_UPWARDS_AMBIENT, SoundSource.BLOCKS, 0.8F, 1.0F);
            Block.popResource(level, pos, new ItemStack(ModItems.COAGULATED_LATEX.get(), 3));
            level.setBlockAndUpdate(pos, Blocks.CAULDRON.defaultBlockState());
        }
        else {
            level.playSound(null, pos, SoundEvents.BUBBLE_COLUMN_BUBBLE_POP, SoundSource.BLOCKS, 0.8F, 1.0F);
            level.scheduleTick(pos, this, 20);
        }
    }

    private boolean isLitCampfireBelow(Level level, BlockPos pos) {
        return CampfireBlock.isSmokeyPos(level, pos);
    }
}
