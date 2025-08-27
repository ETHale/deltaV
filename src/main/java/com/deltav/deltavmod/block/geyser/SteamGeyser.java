package com.deltav.deltavmod.block.geyser;

import com.deltav.deltavmod.particle.ModParticles;

import net.minecraft.world.level.block.Blocks;

public class SteamGeyser extends GeyserBlock{
    public SteamGeyser(Properties properties) {
        super(
            properties,
            1200,
            90,
            0.2,
            GeyserMode.GAS,
            1,
            Blocks.WATER.defaultBlockState(),
            ModParticles.STEAM.get(),
            30
        );
    }
}
