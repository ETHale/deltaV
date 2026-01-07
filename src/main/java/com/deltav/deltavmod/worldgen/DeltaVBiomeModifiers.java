package com.deltav.deltavmod.worldgen;

import com.deltav.deltavmod.DeltaV;
import com.deltav.deltavmod.data.DeltaVBiomeTagsProvider;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.common.world.BiomeModifiers.AddFeaturesBiomeModifier;

// general pipeline:
// configured feature -> configured feature -> placed feature -> Biome modifier
public class DeltaVBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_KIMBERLITE_CARROT = registerKey("add_kimberlite_carrot");
    public static final ResourceKey<BiomeModifier> ADD_HOT_SPRING = registerKey("add_hot_spring");
    public static final ResourceKey<BiomeModifier> ADD_RUBBERWOOD_TREE = registerKey("add_rubberwood_tree");
    
    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var PlacedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(
            ADD_KIMBERLITE_CARROT, 
            new AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(PlacedFeatures.getOrThrow(DeltaVPlacedFeatures.KIMBERLITE_CARROT)),
                Decoration.UNDERGROUND_DECORATION
            )
        );
        context.register(
            ADD_HOT_SPRING, 
            new AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(PlacedFeatures.getOrThrow(DeltaVPlacedFeatures.HOT_SPRING)),
                Decoration.LAKES
            )
        );
        context.register(
            ADD_RUBBERWOOD_TREE, 
            new AddFeaturesBiomeModifier(
                biomes.getOrThrow(DeltaVBiomeTagsProvider.HAS_RUBBERWOOD_TREES),
                HolderSet.direct(PlacedFeatures.getOrThrow(DeltaVPlacedFeatures.RUBBERWOOD_TREE)),
                Decoration.VEGETAL_DECORATION
            )
        );
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(DeltaV.MODID, name));
    }

}
