package com.deltav.deltavmod.data;

import java.util.concurrent.CompletableFuture;

import com.deltav.deltavmod.DeltaV;
import com.deltav.deltavmod.block.ModBlocks;
import com.deltav.deltavmod.item.ModItems;

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
            .add(ModItems.RUBBERWOOD_LOG_ITEM.getKey())
            .add(ModItems.RUBBERWOOD_WOOD_ITEM.getKey())
            .add(ModItems.STRIPPED_RUBBERWOOD_LOG_ITEM.getKey())
            .add(ModItems.STRIPPED_RUBBERWOOD_WOOD_ITEM.getKey());
        this.tag(ItemTags.PLANKS).add(ModItems.RUBBERWOOD_PLANKS_ITEM.getKey());
        this.tag(ItemTags.WOODEN_SLABS).add(ModItems.RUBBERWOOD_SLAB_ITEM.getKey());
        this.tag(ItemTags.WOODEN_STAIRS).add(ModItems.RUBBERWOOD_STAIRS_ITEM.getKey());
        this.tag(ItemTags.WOODEN_FENCES).add(ModItems.RUBBERWOOD_FENCE_ITEM.getKey());
        this.tag(ItemTags.FENCE_GATES).add(ModItems.RUBBERWOOD_FENCE_GATE_ITEM.getKey());
        this.tag(ItemTags.WOODEN_DOORS).add(ModItems.RUBBERWOOD_DOOR_ITEM.getKey());
        this.tag(ItemTags.WOODEN_TRAPDOORS).add(ModItems.RUBBERWOOD_TRAPDOOR_ITEM.getKey());
        this.tag(ItemTags.WOODEN_PRESSURE_PLATES).add(ModItems.RUBBERWOOD_PRESSURE_PLATE_ITEM.getKey());
        this.tag(ItemTags.WOODEN_BUTTONS).add(ModItems.RUBBERWOOD_BUTTON_ITEM.getKey());
    }
}
