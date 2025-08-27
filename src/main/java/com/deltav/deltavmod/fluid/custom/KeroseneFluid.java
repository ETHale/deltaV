package com.deltav.deltavmod.fluid.custom;

import java.util.Optional;

import javax.annotation.Nullable;

import com.deltav.deltavmod.block.ModBlocks;
import com.deltav.deltavmod.fluid.ModFluidTypes;
import com.deltav.deltavmod.fluid.ModFluids;
import com.deltav.deltavmod.item.ModItems;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.fluids.FluidType;

/**
 * The Kerosene fluid class. Includes nested sub-classes for both
 * source and flowing variants.
 * 
 * @author Adam Crawley
 */
public abstract class KeroseneFluid extends FlowingFluid {
    @Override
    public Fluid getFlowing() {
        return ModFluids.KEROSENE_FLOW.get();
    }

    @Override
    public Fluid getSource() {
        return ModFluids.KEROSENE_SOURCE.get();
    }

    @Override
    public Item getBucket() {
        return ModItems.KEROSENE_BUCKET.get();
    }

	@Override
	public FluidType getFluidType() {
		return ModFluidTypes.KEROSENE_FLUID_TYPE.get();
	}

    /**
     * Animate the fluid's behavior.
     * This method is called to handle animations for the fluid,
     * such as sound effects and particle effects.
     *
     * @param level  The level in which the fluid is located.
     * @param pos    The position of the fluid.
     * @param state  The current state of the fluid.
     * @param random The random source for generating animations.
     */
    @Override
    public void animateTick(Level level, BlockPos pos, FluidState state, RandomSource random) {
        if (!state.isSource() && !state.getValue(FALLING)) {
            if (random.nextInt(64) == 0) {
                level.playLocalSound(
                    pos.getX() + 0.5,
                    pos.getY() + 0.5,
                    pos.getZ() + 0.5,
                    SoundEvents.WATER_AMBIENT,
                    SoundSource.AMBIENT,
                    random.nextFloat() * 0.25F + 0.75F,
                    random.nextFloat() + 0.5F,
                    false
                );
            }
        } else if (random.nextInt(10) == 0) {
            // SFI: Could change from blue water particle to custom particle
            level.addParticle(
                ParticleTypes.UNDERWATER,
                pos.getX() + random.nextDouble(),
                pos.getY() + random.nextDouble(),
                pos.getZ() + random.nextDouble(),
                0.0,
                0.0,
                0.0
            );
        }
    }

    @Nullable
    @Override
    public ParticleOptions getDripParticle() {
        return ParticleTypes.DRIPPING_WATER;
    }

    /**
     * Returns whether the fluid can create a source.
     *
     * @param level The level in which the fluid is located.
     * @return true if the fluid can create a source, false otherwise
     */
    @Override
    protected boolean canConvertToSource(ServerLevel level) {
		return false;
	}

    @Override
    protected void beforeDestroyingBlock(LevelAccessor level, BlockPos pos, BlockState state) {
        BlockEntity blockentity = state.hasBlockEntity() ? level.getBlockEntity(pos) : null;
        Block.dropResources(state, level, pos, blockentity);
    }

    @Override
    public int getSlopeFindDistance(LevelReader level) {
        return 4;
    }

    @Override
    public BlockState createLegacyBlock(FluidState state) {
        return ModBlocks.KEROSENE_FLUID.get().defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state));
    }

    @Override
    public boolean isSame(Fluid fluid) {
        return fluid == ModFluids.KEROSENE_SOURCE.get() || fluid == ModFluids.KEROSENE_FLOW.get();
    }

    @Override
    public int getDropOff(LevelReader level) {
        return 1;
    }

    @Override
    public int getTickDelay(LevelReader level) {
        return 5;
    }

    /**
     * Whether a fluid can be replaced with another fluid.
     * 
     * @param fluidState  The current state of the fluid.
     * @param blockReader The block reader for the level.
     * @param pos         The position of the fluid.
     * @param fluid       The fluid to replace with.
     * @param direction   The direction of the replacement.
     */
    @Override
    public boolean canBeReplacedWith(FluidState fluidState, BlockGetter blockReader, BlockPos pos, Fluid fluid, Direction direction) {
		return false;
    }

    @Override
    protected float getExplosionResistance() {
        return 100.0F;
    }

    @Override
    public Optional<SoundEvent> getPickupSound() {
        return Optional.of(SoundEvents.BUCKET_FILL);
    }

    public static class Flowing extends KeroseneFluid {
        @Override
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getAmount(FluidState state) {
            return state.getValue(LEVEL);
        }

        @Override
        public boolean isSource(FluidState state) {
            return false;
        }
    }

    public static class Source extends KeroseneFluid {
        @Override
        public int getAmount(FluidState state) {
            return 8;
        }

        @Override
        public boolean isSource(FluidState state) {
            return true;
        }
    }
}