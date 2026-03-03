package com.deltav.deltavmod.worldgen;

import java.util.List;

import com.deltav.deltavmod.DeltaV;
import com.deltav.deltavmod.block.ModBlocks;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.NoiseBasedCountPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

// general pipeline:
// configured feature -> placed feature -> Biome modifier
public class DeltaVPlacedFeatures {
    public static final ResourceKey<PlacedFeature> KIMBERLITE_CARROT = registerKey("kimberlite_carrot");
    public static final ResourceKey<PlacedFeature> HOT_SPRING = registerKey("hot_spring");
    public static final ResourceKey<PlacedFeature> RUBBERWOOD_TREE = registerKey("rubberwood_tree");
    
    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(
            context, 
            KIMBERLITE_CARROT, 
            configuredFeatures.getOrThrow(DeltaVConfiguredFeatures.KIMBERLITE_CARROT), 
            List.of(
                RarityFilter.onAverageOnceEvery(110),
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
                RarityFilter.onAverageOnceEvery(170),
                NoiseBasedCountPlacement.of(4, 60.0, 0.2),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(40), VerticalAnchor.belowTop(100)),
                BiomeFilter.biome()
            )
        );

        register(
            context, 
            RUBBERWOOD_TREE,
            configuredFeatures.getOrThrow(DeltaVConfiguredFeatures.RUBBERWOODTREE), 
            List.of(
                RarityFilter.onAverageOnceEvery(100),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                PlacementUtils.filteredByBlockSurvival(ModBlocks.RUBBERWOOD_SAPLING.get()),
                BiomeFilter.biome()
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
