package com.deltav.deltavmod.data;

import java.util.concurrent.CompletableFuture;

import com.deltav.deltavmod.DeltaV;
import com.deltav.deltavmod.block.ModBlocks;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.neoforged.neoforge.common.data.ItemTagsProvider;

public class DeltaVItemTagsProvider extends ItemTagsProvider{
    public DeltaVItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, DeltaV.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookupProvider) {
        this.tag(ItemTags.LOGS_THAT_BURN)
            .add(ModBlocks.RUBBERWOOD_LOG.get().asItem())
            .add(ModBlocks.RUBBERWOOD_WOOD.get().asItem())
            .add(ModBlocks.STRIPPED_RUBBERWOOD_LOG.get().asItem())
            .add(ModBlocks.STRIPPED_RUBBERWOOD_WOOD.get().asItem());
        this.tag(ItemTags.PLANKS).add(ModBlocks.RUBBERWOOD_PLANKS.get().asItem());
        this.tag(ItemTags.WOODEN_SLABS).add(ModBlocks.RUBBERWOOD_SLAB.get().asItem());
        this.tag(ItemTags.SLABS).add(ModBlocks.RUBBERWOOD_SLAB.get().asItem());
        this.tag(ItemTags.WOODEN_STAIRS).add(ModBlocks.RUBBERWOOD_STAIRS.get().asItem());
        this.tag(ItemTags.STAIRS).add(ModBlocks.RUBBERWOOD_STAIRS.get().asItem());
        this.tag(ItemTags.WOODEN_FENCES).add(ModBlocks.RUBBERWOOD_FENCE.get().asItem());
        this.tag(ItemTags.FENCE_GATES).add(ModBlocks.RUBBERWOOD_FENCE_GATE.get().asItem());
        this.tag(ItemTags.WOODEN_DOORS).add(ModBlocks.RUBBERWOOD_DOOR.get().asItem());
        this.tag(ItemTags.DOORS).add(ModBlocks.RUBBERWOOD_DOOR.get().asItem());
        this.tag(ItemTags.WOODEN_TRAPDOORS).add(ModBlocks.RUBBERWOOD_TRAPDOOR.get().asItem());
        this.tag(ItemTags.TRAPDOORS).add(ModBlocks.RUBBERWOOD_TRAPDOOR.get().asItem());
        this.tag(ItemTags.WOODEN_PRESSURE_PLATES).add(ModBlocks.RUBBERWOOD_PRESSURE_PLATE.get().asItem());
        this.tag(ItemTags.WOODEN_BUTTONS).add(ModBlocks.RUBBERWOOD_BUTTON.get().asItem());
        this.tag(ItemTags.BUTTONS).add(ModBlocks.RUBBERWOOD_BUTTON.get().asItem());
    }
}
