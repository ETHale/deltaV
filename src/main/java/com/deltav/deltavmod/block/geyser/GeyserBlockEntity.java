package com.deltav.deltavmod.block.geyser;

import com.deltav.deltavmod.block.entity.ModBlockEntities;
import com.deltav.deltavmod.block.geyser.GeyserBlock.GeyserMode;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class GeyserBlockEntity extends BlockEntity {
    private int cooldown = 0;
    private int eruptionRemaining = 0;
    private GeyserMode activeMode = null;
    
    public GeyserBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GEYSER_BE.get(), pos, state); // adapt registration reference
        this.cooldown = 0;
    }

    public void serverTick(Level level) {
        if (level == null || level.isClientSide()) return;

        GeyserBlock block = (GeyserBlock) this.getBlockState().getBlock();

        if (eruptionRemaining > 0) {
            // currently erupting
            handleEruptionTick(block, (ServerLevel) level, worldPosition);
            eruptionRemaining--;
            if (eruptionRemaining <= 0) {
                activeMode = null;
                cooldown = block.getCooldownTicks();
            }
            setChanged();
            return;
        }

        // not erupting: decrement cooldown then try to start
        if (cooldown > 0) {
            cooldown--;
            setChanged();
            return;
        }

        // cooldown finished, try to start eruption using chance
        if (level.getRandom().nextDouble() <= block.getEruptionChance()) {
            startEruption((ServerLevel) level, worldPosition, block);
            setChanged();
        } else {
            // failed to trigger; set small cooldown to retry later to avoid continuous checks
            cooldown = Math.max(20, block.getCooldownTicks() / 10);
            setChanged();
        }
    }

    private void startEruption(ServerLevel server, BlockPos pos, GeyserBlock block) {
        // decide mode
        if (block.getDefaultMode() == GeyserMode.RANDOM) {
            activeMode = (server.getRandom().nextDouble() < block.getParticleModeChance())
                ? GeyserMode.GAS : GeyserMode.LIQUID;
        } else {
            activeMode = block.getDefaultMode();
        }

        eruptionRemaining = block.getEruptionDurationTicks();

        // if liquid mode, attempt to place fluid once at start
        if (activeMode == GeyserMode.LIQUID) {
            BlockPos target = pos.above();
            BlockState targetState = server.getBlockState(target);
            if (canPlaceFluidAt(targetState)) {
                server.setBlock(target, block.getFluidState(), 3);
            }
        }
    }

    private void handleEruptionTick(GeyserBlock block, ServerLevel server, BlockPos pos) {
        if (activeMode == GeyserMode.GAS) {
            // spawn particles server->clients
            double cx = pos.getX() + 0.5;
            double cy = pos.getY() + 1.0;
            double cz = pos.getZ() + 0.5;
            // spread and speed values are example
            server.sendParticles((ParticleOptions) block.getParticleType(),
                    cx, cy, cz,
                    block.getParticleCountPerTick(),
                    0.3, 0.6, 0.3,
                    0.02);
        } else if (activeMode == GeyserMode.LIQUID) {
            // optionally you can push fluid or spawn extra effects during eruption
            // left minimal: we already placed fluid at start
        }
    }

    private boolean canPlaceFluidAt(BlockState state) {
        return state.isAir() && state.getFluidState().isEmpty();
    }

}
