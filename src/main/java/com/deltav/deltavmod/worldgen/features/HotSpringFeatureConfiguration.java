package com.deltav.deltavmod.worldgen.features;

import com.deltav.deltavmod.block.ModBlocks;
import com.deltav.deltavmod.fluid.ModFluids;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.block.Blocks;

public record HotSpringFeatureConfiguration(
    FluidState contents,
    BlockState barrier,     
    BlockState rim,
    BlockState geyser,
    int depth,
    int radius
) implements FeatureConfiguration {
    public static final Codec<HotSpringFeatureConfiguration> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        FluidState.CODEC.fieldOf("contents").orElse(ModFluids.THERMAL_WATER_SOURCE.get().defaultFluidState()).forGetter(HotSpringFeatureConfiguration::contents),
        BlockState.CODEC.fieldOf("rim").orElse(ModBlocks.SILICA_SAND.get().defaultBlockState()).forGetter(HotSpringFeatureConfiguration::rim),
        BlockState.CODEC.fieldOf("barrier").orElse(ModBlocks.SILICA_SANDSTONE.get().defaultBlockState()).forGetter(HotSpringFeatureConfiguration::barrier),
        BlockState.CODEC.fieldOf("geyser").orElse(Blocks.AIR.defaultBlockState()).forGetter(HotSpringFeatureConfiguration::geyser),
        Codec.intRange(0, 255).fieldOf("depth").orElse(6).forGetter(HotSpringFeatureConfiguration::depth),
        Codec.intRange(0, 255).fieldOf("radius").orElse(8).forGetter(HotSpringFeatureConfiguration::radius)
    ).apply(inst, HotSpringFeatureConfiguration::new));

    public static final HotSpringFeatureConfiguration INSTANCE = new HotSpringFeatureConfiguration(
        ModFluids.THERMAL_WATER_SOURCE.get().defaultFluidState(),
        ModBlocks.SILICA_SAND.get().defaultBlockState(),
        ModBlocks.SILICA_SANDSTONE.get().defaultBlockState(),
        ModBlocks.STEAM_GEYSER.get().defaultBlockState(),
        6,
        8
    );
}
