package com.deltav.deltavmod.worldgen.features;

import com.deltav.deltavmod.block.ModBlocks;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.util.valueproviders.UniformInt;

public record HotSpringFeatureConfiguration(
    double noiseScale,         // 3D noise scale (smaller = larger blobs)
    int octaves,               // noise octaves for mask
    float threshold,           // noise threshold to decide mask membership (0..1)
    IntProvider maxDepth,      // max carve depth
    BlockState contents,       // what fills carved area (water, block, etc.)
    BlockState rim,            // rim block
    BlockState base,           // base (lower band) block
    int rimThickness,          // how many horizontal layers of rim
    int searchPadding,         // how far (blocks) around chunk to sample
    double exponent,           // bias exponent (>=1) to make peaks rarer / sharper
    double plateauThreshold,   // biased value above this will plateau toward 1
    double detailAmp           // amplitude of high-frequency detail blended into base
) implements FeatureConfiguration {
    public static final Codec<HotSpringFeatureConfiguration> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        Codec.DOUBLE.fieldOf("noise_scale").orElse(0.06).forGetter(HotSpringFeatureConfiguration::noiseScale),
        Codec.INT.fieldOf("octaves").orElse(2).forGetter(HotSpringFeatureConfiguration::octaves),
        Codec.FLOAT.fieldOf("threshold").orElse(0.92F).forGetter(HotSpringFeatureConfiguration::threshold),
        IntProvider.CODEC.fieldOf("max_depth").forGetter(HotSpringFeatureConfiguration::maxDepth),
        BlockState.CODEC.fieldOf("contents").orElse(Blocks.WATER.defaultBlockState()).forGetter(HotSpringFeatureConfiguration::contents),
        BlockState.CODEC.fieldOf("rim").orElse(ModBlocks.SILICA_SAND.get().defaultBlockState()).forGetter(HotSpringFeatureConfiguration::rim),
        BlockState.CODEC.fieldOf("base").orElse(ModBlocks.SILICA_SANDSTONE.get().defaultBlockState()).forGetter(HotSpringFeatureConfiguration::base),
        Codec.INT.fieldOf("rim_thickness").orElse(1).forGetter(HotSpringFeatureConfiguration::rimThickness),
        Codec.INT.fieldOf("search_padding").orElse(64).forGetter(HotSpringFeatureConfiguration::searchPadding),
        Codec.DOUBLE.fieldOf("exponent").orElse(3.8).forGetter(HotSpringFeatureConfiguration::exponent),
        Codec.DOUBLE.fieldOf("plateau_threshold").orElse(0.72).forGetter(HotSpringFeatureConfiguration::plateauThreshold),
        Codec.DOUBLE.fieldOf("detail_amp").orElse(0.22).forGetter(HotSpringFeatureConfiguration::detailAmp)
    ).apply(inst, HotSpringFeatureConfiguration::new));

    public static final HotSpringFeatureConfiguration INSTANCE = new HotSpringFeatureConfiguration(
        0.01,                    // noiseScale
        1,                       // octaves
        0.78f,                   // threshold
        UniformInt.of(1, 3),     // maxDepth
        Blocks.WATER.defaultBlockState(),
        ModBlocks.SILICA_SAND.get().defaultBlockState(),
        ModBlocks.SILICA_SANDSTONE.get().defaultBlockState(),
        1,                       // rimThickness
        64,                      // searchPadding
        2.5,                     // exponent
        0.7,                    // plateauThreshold
        0.6                     // detailAmp
    );

    // convenience: sample deterministic "group" Y from a cell RNG (keeps clusters vertically stable)
    public static int sampleGroupY(net.minecraft.world.level.WorldGenLevel level, RandomSource rand) {
        int min = Math.max(level.getMinY() + 8, 24);
        int max = Math.min(level.getHeight() - 16, 140);
        int range = Math.max(1, max - min + 1);
        return min + rand.nextInt(range);
    }
}
