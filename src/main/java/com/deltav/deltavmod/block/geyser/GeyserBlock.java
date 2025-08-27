package com.deltav.deltavmod.block.geyser;

import javax.annotation.Nullable;

import com.deltav.deltavmod.block.entity.ModBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class GeyserBlock extends Block implements EntityBlock{
    public enum GeyserMode {
        LIQUID,
        GAS,
        RANDOM
    }

    protected final int cooldownTicks;
    protected final int eruptionDurationTicks;
    protected final double eruptionChance;         
    protected final GeyserMode defaultMode;
    protected final double particleModeChance;     
    protected final BlockState fluidState;         
    protected final SimpleParticleType particleType; 
    protected final int particleCountPerTick;      

    public GeyserBlock(Properties properties,
                       int cooldownTicks,
                       int eruptionDurationTicks,
                       double eruptionChance,
                       GeyserMode defaultMode,
                       double particleModeChance,
                       BlockState fluidState,
                       SimpleParticleType particleType,
                       int particleCountPerTick) {
        super(properties);
        this.cooldownTicks = cooldownTicks;
        this.eruptionDurationTicks = eruptionDurationTicks;
        this.eruptionChance = eruptionChance;
        this.defaultMode = defaultMode;
        this.particleModeChance = particleModeChance;
        this.fluidState = fluidState;
        this.particleType = particleType;
        this.particleCountPerTick = particleCountPerTick;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GeyserBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (level.isClientSide)
            return null;

        if (blockEntityType == ModBlockEntities.GEYSER_BE.get()) {
            return (lvl, pos, st, be) -> {
                if (be instanceof GeyserBlockEntity geyser) {
                    geyser.serverTick(lvl);
                }
            };
        }

        return null;
    }

    public int getCooldownTicks() { return cooldownTicks; }
    public int getEruptionDurationTicks() { return eruptionDurationTicks; }
    public double getEruptionChance() { return eruptionChance; }
    public GeyserMode getDefaultMode() { return defaultMode; }
    public double getParticleModeChance() { return particleModeChance; }
    public BlockState getFluidState() { return fluidState; }
    public net.minecraft.core.particles.SimpleParticleType getParticleType() { return particleType; }
    public int getParticleCountPerTick() { return particleCountPerTick; }
}
