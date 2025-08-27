package com.deltav.deltavmod.worldgen.features;

import com.deltav.deltavmod.block.ModBlocks;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.block.Blocks;

public record HotSpringFeatureConfiguration(
    BlockState contents,
    BlockState barrier,     
    BlockState rim,
    int searchHeight, 
    BlockState geyser
) implements FeatureConfiguration {
    public static final Codec<HotSpringFeatureConfiguration> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        BlockState.CODEC.fieldOf("contents").orElse(Blocks.WATER.defaultBlockState()).forGetter(HotSpringFeatureConfiguration::contents),
        BlockState.CODEC.fieldOf("rim").orElse(ModBlocks.SILICA_SAND.get().defaultBlockState()).forGetter(HotSpringFeatureConfiguration::rim),
        BlockState.CODEC.fieldOf("barrier").orElse(ModBlocks.SILICA_SANDSTONE.get().defaultBlockState()).forGetter(HotSpringFeatureConfiguration::barrier),
        Codec.INT.fieldOf("searchHeight").orElse(32).forGetter(HotSpringFeatureConfiguration::searchHeight),
        BlockState.CODEC.fieldOf("geyser").orElse(Blocks.AIR.defaultBlockState()).forGetter(HotSpringFeatureConfiguration::geyser)
    ).apply(inst, HotSpringFeatureConfiguration::new));

    public static final HotSpringFeatureConfiguration INSTANCE = new HotSpringFeatureConfiguration(
        Blocks.WATER.defaultBlockState(),
        ModBlocks.SILICA_SAND.get().defaultBlockState(),
        ModBlocks.SILICA_SANDSTONE.get().defaultBlockState(),
        32,
        ModBlocks.STEAM_GEYSER.get().defaultBlockState()
    );
}
