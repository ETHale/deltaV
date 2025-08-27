package com.deltav.deltavmod.block.geyser;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.Blocks;

public class SteamGeyser extends GeyserBlock{
    public SteamGeyser(Properties properties) {
        super(
            properties,
            1200,
            100,
            0.2,
            GeyserMode.RANDOM,
            0.8,
            Blocks.WATER.defaultBlockState(),
            ParticleTypes.CLOUD,
            6
        );
    }
}
