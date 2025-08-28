package com.deltav.deltavmod.block.geyser;

import com.deltav.deltavmod.block.ModBlocks;
import com.deltav.deltavmod.particle.ModParticles;


public class SteamGeyser extends GeyserBlock{
    public SteamGeyser(Properties properties) {
        super(
            properties,
            1200,
            90,
            0.2,
            GeyserMode.GAS,
            1,
            ModBlocks.THERMAL_WATER_FLUID.get().defaultBlockState(),
            ModParticles.STEAM.get(),
            30,
            1.5f,
            4,
            1.2f,
            0.5f,
            7.0f
        );
    }
}
