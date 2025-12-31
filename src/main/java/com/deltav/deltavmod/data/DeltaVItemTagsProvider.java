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
            .add(ModBlocks.RUBBERWOODLOG.get().asItem())
            .add(ModBlocks.RUBBERWOODWOOD.get().asItem())
            .add(ModBlocks.STRIPPED_RUBBERWOODLOG.get().asItem())
            .add(ModBlocks.STRIPPED_RUBBERWOODWOOD.get().asItem());
        this.tag(ItemTags.PLANKS).add(ModBlocks.RUBBERWOOD_PLANKS.get().asItem());
    }
}
