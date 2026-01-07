package com.deltav.deltavmod.worldgen;

import com.deltav.deltavmod.DeltaV;
import com.deltav.deltavmod.block.ModBlocks;
import com.deltav.deltavmod.worldgen.features.DeltaVFeatures;
import com.deltav.deltavmod.worldgen.features.HotSpringFeatureConfiguration;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature ;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.RandomSpreadFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

// general pipeline:
// configured feature -> placed feature -> Biome modifier
public class DeltaVConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> KIMBERLITE_CARROT = registerKey("kimberlite_carrot");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HOT_SPRING = registerKey("hot_spring");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RUBBERWOODTREE = registerKey("rubberwood_tree");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        register(
            context,
            KIMBERLITE_CARROT,
            DeltaVFeatures.KIMBERLITE_CARROT_FEATURE.get(),
            NoneFeatureConfiguration.INSTANCE
        );
        register(
            context,
            HOT_SPRING,
            DeltaVFeatures.HOT_SPRING.get(),
            HotSpringFeatureConfiguration.INSTANCE
        );
        register(
            context,
            RUBBERWOODTREE,
            Feature.TREE,
            new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.RUBBERWOOD_LOG.get()),
                new StraightTrunkPlacer(4, 1, 3),
                BlockStateProvider.simple(ModBlocks.RUBBERWOOD_LEAVES.get()), 
                new RandomSpreadFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(5), 125), 
                new TwoLayersFeatureSize(1, 0, 2)).build()
        );
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(DeltaV.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
        ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
            context.register(key, new ConfiguredFeature<>(feature, configuration));
        }

}
