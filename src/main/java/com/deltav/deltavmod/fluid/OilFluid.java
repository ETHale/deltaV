package com.deltav.deltavmod.fluid;

import java.util.Optional;

import javax.annotation.Nullable;

import com.deltav.deltavmod.block.ModBlocks;
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
import net.minecraft.world.item.Items;
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
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.WaterFluid;
import net.neoforged.neoforge.fluids.FluidType;

public abstract class OilFluid extends FlowingFluid {
    @Override
    public Fluid getFlowing() {
        return ModFluids.OIL_FLOWING.get();
    }

    @Override
    public Fluid getSource() {
        return ModFluids.OIL_SOURCE.get();
    }

    @Override
    public Item getBucket() {
        return ModItems.OIL_BUCKET.get();
    }

	@Override
	public FluidType getFluidType() {
		return ModFluidTypes.OIL_FLUID_TYPE.get();
	}

    @Override
    public void animateTick(Level p_230606_, BlockPos p_230607_, FluidState p_230608_, RandomSource p_230609_) {
        if (!p_230608_.isSource() && !p_230608_.getValue(FALLING)) {
            if (p_230609_.nextInt(64) == 0) {
                p_230606_.playLocalSound(
                    p_230607_.getX() + 0.5,
                    p_230607_.getY() + 0.5,
                    p_230607_.getZ() + 0.5,
                    SoundEvents.WATER_AMBIENT,
                    SoundSource.AMBIENT,
                    p_230609_.nextFloat() * 0.25F + 0.75F,
                    p_230609_.nextFloat() + 0.5F,
                    false
                );
            }
        } else if (p_230609_.nextInt(10) == 0) {
            p_230606_.addParticle(
                ParticleTypes.UNDERWATER,
                p_230607_.getX() + p_230609_.nextDouble(),
                p_230607_.getY() + p_230609_.nextDouble(),
                p_230607_.getZ() + p_230609_.nextDouble(),
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

    @Override
    protected boolean canConvertToSource(ServerLevel p_376722_) {
        // return p_376722_.getGameRules().getBoolean(GameRules.RULE_WATER_SOURCE_CONVERSION);
		return false;
	}

    @Override
    protected void beforeDestroyingBlock(LevelAccessor level, BlockPos pos, BlockState state) {
        BlockEntity blockentity = state.hasBlockEntity() ? level.getBlockEntity(pos) : null;
        Block.dropResources(state, level, pos, blockentity);
    }

    // @Override
    // protected void entityInside(Level p_404956_, BlockPos p_405311_, Entity p_405780_, InsideBlockEffectApplier p_405240_) {
    //     p_405240_.apply(InsideBlockEffectType.EXTINGUISH);
    // }

    @Override
    public int getSlopeFindDistance(LevelReader level) {
        return 4;
    }

    @Override
    public BlockState createLegacyBlock(FluidState state) {
        return ModBlocks.OIL_FLUID.get().defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state));
    }

    @Override
    public boolean isSame(Fluid fluid) {
        return fluid == ModFluids.OIL_SOURCE.get() || fluid == ModFluids.OIL_FLOWING.get();
    }

    @Override
    public int getDropOff(LevelReader level) {
        return 1;
    }

    @Override
    public int getTickDelay(LevelReader p_76454_) {
        return 5;
    }

    @Override
    public boolean canBeReplacedWith(FluidState fluidState, BlockGetter blockReader, BlockPos pos, Fluid fluid, Direction direction) {
		return !this.isSame(fluid);
		// return direction == Direction.DOWN && !fluid.is(FluidTags.WATER);
    }

    @Override
    protected float getExplosionResistance() {
        return 100.0F;
    }

    @Override
    public Optional<SoundEvent> getPickupSound() {
        return Optional.of(SoundEvents.BUCKET_FILL);
    }

    public static class Flowing extends OilFluid {
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

    public static class Source extends OilFluid {
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