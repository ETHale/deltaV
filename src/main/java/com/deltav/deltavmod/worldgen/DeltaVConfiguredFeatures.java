package com.deltav.deltavmod.worldgen;

import com.deltav.deltavmod.DeltaV;
import com.deltav.deltavmod.worldgen.features.DeltaVFeatures;
import com.deltav.deltavmod.worldgen.features.HotSpringFeatureConfiguration;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature ;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

// general pipeline:
// configured feature -> placed feature -> Biome modifier
public class DeltaVConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> KIMBERLITE_CARROT = registerKey("kimberlite_carrot");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HOT_SPRING = registerKey("hot_spring");

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
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(DeltaV.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
        ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
            context.register(key, new ConfiguredFeature<>(feature, configuration));
        }

}
