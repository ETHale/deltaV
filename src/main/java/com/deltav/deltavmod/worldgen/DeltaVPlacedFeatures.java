package com.deltav.deltavmod.worldgen;

import java.util.List;

import com.deltav.deltavmod.DeltaV;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

// general pipeline:
// configured feature -> placed feature -> Biome modifier
public class DeltaVPlacedFeatures {
    public static final ResourceKey<PlacedFeature> KIMBERLITE_CARROT = registerKey("kimberlite_carrot");
    public static final ResourceKey<PlacedFeature> HOT_SPRING = registerKey("hot_spring");
    
    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(
            context, 
            KIMBERLITE_CARROT, 
            configuredFeatures.getOrThrow(DeltaVConfiguredFeatures.KIMBERLITE_CARROT), 
            List.of(
                RarityFilter.onAverageOnceEvery(110), // control spawn rate
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                BiomeFilter.biome()
            )
        );

        register(
            context, 
            HOT_SPRING, 
            configuredFeatures.getOrThrow(DeltaVConfiguredFeatures.HOT_SPRING), 
            List.of(
                CountPlacement.of(1),
                InSquarePlacement.spread(),
                PlacementUtils.FULL_RANGE
            )
        );
    }
    
    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(DeltaV.MODID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
