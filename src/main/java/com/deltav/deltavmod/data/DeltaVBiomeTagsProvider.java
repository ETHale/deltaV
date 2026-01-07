package com.deltav.deltavmod.data;

import java.util.concurrent.CompletableFuture;

import com.deltav.deltavmod.DeltaV;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public class DeltaVBiomeTagsProvider extends BiomeTagsProvider {
    public static final TagKey<Biome> HAS_RUBBERWOOD_TREES = TagKey.create(
        net.minecraft.core.registries.Registries.BIOME,
        ResourceLocation.fromNamespaceAndPath(DeltaV.MODID, "has_rubberwood_trees")
    );

    public DeltaVBiomeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, DeltaV.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookupProvider) {
        this.tag(HAS_RUBBERWOOD_TREES)
            .add(Biomes.FOREST)
            .add(Biomes.BIRCH_FOREST)
            .add(Biomes.DARK_FOREST)
            .add(Biomes.JUNGLE)
            .add(Biomes.SPARSE_JUNGLE);
    }
    
}
