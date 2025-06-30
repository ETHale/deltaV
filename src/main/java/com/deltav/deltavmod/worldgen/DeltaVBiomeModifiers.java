package com.deltav.deltavmod.worldgen;

import com.deltav.deltavmod.DeltaV;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

// general pipeline:
// configured feature -> placed feature -> Biome modifier
public class DeltaVBiomeModifiers {
    
    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var PlacedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(DeltaV.MODID, name));
    }

}
