package com.deltav.deltavmod.worldgen.features;

import com.deltav.deltavmod.block.ModBlocks;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;

public record HotSpringFeatureConfiguration(
    IntProvider size,
    IntProvider rimSize,
    BlockState contents,
    BlockState rim,
    BlockState base,
    int cellSize,         
    float spawnChance,   
    IntProvider maxPerCell,
    int searchPadding,
    IntProvider yVariance      
) implements FeatureConfiguration {
    public static final Codec<HotSpringFeatureConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        IntProvider.CODEC.fieldOf("size").forGetter(HotSpringFeatureConfiguration::size),
        IntProvider.CODEC.fieldOf("rim_size").forGetter(HotSpringFeatureConfiguration::rimSize),
        BlockState.CODEC.fieldOf("contents").forGetter(HotSpringFeatureConfiguration::contents),
        BlockState.CODEC.fieldOf("rim").forGetter(HotSpringFeatureConfiguration::rim),
        BlockState.CODEC.fieldOf("base").forGetter(HotSpringFeatureConfiguration::base),
        Codec.INT.fieldOf("cell_size").orElse(16).forGetter(HotSpringFeatureConfiguration::cellSize),
        Codec.FLOAT.fieldOf("spawn_chance").orElse(0.25F).forGetter(HotSpringFeatureConfiguration::spawnChance),
        IntProvider.CODEC.fieldOf("max_per_cell").forGetter(HotSpringFeatureConfiguration::maxPerCell),
        Codec.INT.fieldOf("search_padding").orElse(24).forGetter(HotSpringFeatureConfiguration::searchPadding),
        IntProvider.CODEC.fieldOf("y_variance").forGetter(HotSpringFeatureConfiguration::yVariance)
    ).apply(instance, HotSpringFeatureConfiguration::new));

    public static final HotSpringFeatureConfiguration INSTANCE = new HotSpringFeatureConfiguration(
        UniformInt.of(3, 8),
        UniformInt.of(1,2),
        Blocks.WATER.defaultBlockState(),
        ModBlocks.SILICA_SAND.get().defaultBlockState(),
        ModBlocks.SILICA_SANDSTONE.get().defaultBlockState(),
        16,
        0.005F,
        UniformInt.of(3, 6),
        64,
        UniformInt.of(1,8)
    );
}
