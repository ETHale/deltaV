package com.deltav.deltavmod.worldgen.features;

import com.deltav.deltavmod.block.ModBlocks;
import com.deltav.deltavmod.fluid.ModFluids;
import com.mojang.datafixers.types.templates.List;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.block.Blocks;

public record HotSpringFeatureConfiguration(
    FluidState contents,
    BlockStateProvider barrier,     
    BlockState geyser,
    int depth,
    int radius,
    int edgeWidth,
    float noiseScale,
    float rateOfChange,
    int originNoise
) implements FeatureConfiguration {
    public static final Codec<HotSpringFeatureConfiguration> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        FluidState.CODEC.fieldOf("contents").orElse(ModFluids.THERMAL_WATER_SOURCE.get().defaultFluidState()).forGetter(HotSpringFeatureConfiguration::contents),
        BlockStateProvider.CODEC.fieldOf("barrier").orElse(BlockStateProvider.simple(ModBlocks.SILICA_SANDSTONE.get().defaultBlockState())).forGetter(HotSpringFeatureConfiguration::barrier),
        BlockState.CODEC.fieldOf("geyser").orElse(Blocks.AIR.defaultBlockState()).forGetter(HotSpringFeatureConfiguration::geyser),
        Codec.intRange(0, 255).fieldOf("depth").orElse(6).forGetter(HotSpringFeatureConfiguration::depth),
        Codec.intRange(0, 255).fieldOf("radius").orElse(8).forGetter(HotSpringFeatureConfiguration::radius),
        Codec.intRange(0, 255).fieldOf("edgeWidth").orElse(2).forGetter(HotSpringFeatureConfiguration::edgeWidth),
        Codec.floatRange(0, 1).fieldOf("noiseScale").orElse(0.4f).forGetter(HotSpringFeatureConfiguration::noiseScale),
        Codec.floatRange(0, 255).fieldOf("rateOfChange").orElse(1.3f).forGetter(HotSpringFeatureConfiguration::rateOfChange),
        Codec.intRange(0, 255).fieldOf("originNoise").orElse(3).forGetter(HotSpringFeatureConfiguration::originNoise)
    ).apply(inst, HotSpringFeatureConfiguration::new));

    public static final HotSpringFeatureConfiguration INSTANCE = new HotSpringFeatureConfiguration(
        ModFluids.THERMAL_WATER_SOURCE.get().defaultFluidState(),
        new WeightedStateProvider(
            WeightedList.<BlockState>builder()
                .add(ModBlocks.SILICA_SANDSTONE.get().defaultBlockState(), 5)
                .add(ModBlocks.SILICA_SAND.get().defaultBlockState(), 1).build()
        ),
        ModBlocks.STEAM_GEYSER.get().defaultBlockState(),
        6,
        8,
        2,
        0.4f,
        1.3f,
        3
    );
}
