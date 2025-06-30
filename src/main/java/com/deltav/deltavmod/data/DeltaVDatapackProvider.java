package com.deltav.deltavmod.data;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import com.deltav.deltavmod.worldgen.DeltaVBiomeModifiers;
import com.deltav.deltavmod.worldgen.DeltaVConfiguredFeatures;
import com.deltav.deltavmod.worldgen.DeltaVPlacedFeatures;

import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class DeltaVDatapackProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
        //World gen stuff
        .add(Registries.CONFIGURED_FEATURE, DeltaVConfiguredFeatures::bootstrap)
        .add(Registries.PLACED_FEATURE, DeltaVPlacedFeatures::bootstrap)
        .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, DeltaVBiomeModifiers::bootstrap);
    
    public DeltaVDatapackProvider(PackOutput output, CompletableFuture<RegistrySetBuilder.PatchedRegistries> registries, Set<String> modIds) {
        super(output, registries, modIds);
    }
}
