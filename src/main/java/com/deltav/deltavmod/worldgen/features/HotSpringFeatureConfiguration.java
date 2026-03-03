package com.deltav.deltavmod.worldgen.features;

import com.deltav.deltavmod.block.ModBlocks;
import com.deltav.deltavmod.fluid.ModFluids;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.util.random.WeightedList;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.ConstantInt;

public record HotSpringFeatureConfiguration(
    FluidState contents,
    BlockStateProvider barrier,     
    BlockStateProvider surface,     
    BlockState geyser,
    IntProvider depth,
    IntProvider contentsDepth,
    IntProvider radius,
    IntProvider edgeWidth,
    FloatProvider noiseScale,
    FloatProvider rateOfChange,
    IntProvider originNoise
) implements FeatureConfiguration {
    public static final Codec<HotSpringFeatureConfiguration> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        FluidState.CODEC.fieldOf("contents").orElse(ModFluids.THERMAL_WATER_SOURCE.get().defaultFluidState()).forGetter(HotSpringFeatureConfiguration::contents),
        BlockStateProvider.CODEC.fieldOf("barrier").orElse(BlockStateProvider.simple(ModBlocks.SILICA_SANDSTONE.get().defaultBlockState())).forGetter(HotSpringFeatureConfiguration::barrier),
        BlockStateProvider.CODEC.fieldOf("surface").orElse(BlockStateProvider.simple(ModBlocks.SILICA_SANDSTONE.get().defaultBlockState())).forGetter(HotSpringFeatureConfiguration::surface),
        BlockState.CODEC.fieldOf("geyser").orElse(Blocks.AIR.defaultBlockState()).forGetter(HotSpringFeatureConfiguration::geyser),
        IntProvider.CODEC.fieldOf("depth").orElse(ConstantInt.of(6)).forGetter(HotSpringFeatureConfiguration::depth),
        IntProvider.CODEC.fieldOf("contentsDepth").orElse(ConstantInt.of(0)).forGetter(HotSpringFeatureConfiguration::contentsDepth),
        IntProvider.CODEC.fieldOf("radius").orElse(ConstantInt.of(8)).forGetter(HotSpringFeatureConfiguration::radius),
        IntProvider.CODEC.fieldOf("edgeWidth").orElse(ConstantInt.of(2)).forGetter(HotSpringFeatureConfiguration::edgeWidth),
        FloatProvider.CODEC.fieldOf("noiseScale").orElse(ConstantFloat.of(0.4f)).forGetter(HotSpringFeatureConfiguration::noiseScale),
        FloatProvider.CODEC.fieldOf("rateOfChange").orElse(ConstantFloat.of(1.3f)).forGetter(HotSpringFeatureConfiguration::rateOfChange),
        IntProvider.CODEC.fieldOf("originNoise").orElse(ConstantInt.of(3)).forGetter(HotSpringFeatureConfiguration::originNoise)
    ).apply(inst, HotSpringFeatureConfiguration::new));

    public static final HotSpringFeatureConfiguration INSTANCE = new HotSpringFeatureConfiguration(
        ModFluids.THERMAL_WATER_SOURCE.get().defaultFluidState(),
        BlockStateProvider.simple(ModBlocks.SILICA_SANDSTONE.get().defaultBlockState()),
        new WeightedStateProvider(
            WeightedList.<BlockState>builder()
                .add(ModBlocks.SILICA_SANDSTONE.get().defaultBlockState(), 2)
                .add(ModBlocks.SILICA_SAND.get().defaultBlockState(), 3).build()
        ),
        ModBlocks.STEAM_GEYSER.get().defaultBlockState(),
        UniformInt.of(5,9),
        UniformInt.of(0,2),
        UniformInt.of(6,10),
        ConstantInt.of(2),
        ConstantFloat.of(0.4f),
        ConstantFloat.of(1.3f),
        ConstantInt.of(3)
    );
}
