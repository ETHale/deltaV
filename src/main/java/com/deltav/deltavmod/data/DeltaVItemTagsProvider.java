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
            .add(ModBlocks.RUBBER_LOG.get().asItem())
            .add(ModBlocks.RUBBER_WOOD.get().asItem())
            .add(ModBlocks.STRIPPED_RUBBER_LOG.get().asItem())
            .add(ModBlocks.STRIPPED_RUBBER_WOOD.get().asItem());
        this.tag(ItemTags.PLANKS).add(ModBlocks.RUBBER_PLANKS.get().asItem());
    }
}
