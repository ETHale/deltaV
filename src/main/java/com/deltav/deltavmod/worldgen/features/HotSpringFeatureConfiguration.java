package com.deltav.deltavmod.worldgen.features;

import java.util.Optional;

import com.deltav.deltavmod.block.ModBlocks;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record HotSpringFeatureConfiguration(
    BlockState contents,
    BlockState rim,
    BlockState base,
    IntProvider size,
    IntProvider rimSize,
    IntProvider poolCount
) implements FeatureConfiguration {
    public static final Codec<HotSpringFeatureConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        BlockState.CODEC.fieldOf("contents").forGetter(HotSpringFeatureConfiguration::contents),
        BlockState.CODEC.fieldOf("rim").forGetter(HotSpringFeatureConfiguration::rim),
        BlockState.CODEC.fieldOf("base").forGetter(HotSpringFeatureConfiguration::base),
        IntProvider.CODEC.fieldOf("size").forGetter(HotSpringFeatureConfiguration::size),
        IntProvider.CODEC.fieldOf("rim_size").forGetter(HotSpringFeatureConfiguration::rimSize),
        IntProvider.CODEC.fieldOf("pool_count").forGetter(HotSpringFeatureConfiguration::poolCount)
    ).apply(instance, HotSpringFeatureConfiguration::new));

    public static final HotSpringFeatureConfiguration INSTANCE = new HotSpringFeatureConfiguration(
        Blocks.WATER.defaultBlockState(),
        ModBlocks.SILICA_SAND.get().defaultBlockState(),
        ModBlocks.SILICA_SANDSTONE.get().defaultBlockState(),
        UniformInt.of(1, 8),
        ConstantInt.of(1),
        UniformInt.of(4,10)
    );
}
