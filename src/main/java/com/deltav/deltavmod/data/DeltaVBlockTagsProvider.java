package com.deltav.deltavmod.data;

import java.util.concurrent.CompletableFuture;

import com.deltav.deltavmod.DeltaV;
import com.deltav.deltavmod.block.ModBlocks;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.minecraft.tags.BlockTags;

// adds tags to all our custom blocks
// tags seem to be used to determine traits like - needs stone or higher pickaxe to mine
public class DeltaVBlockTagsProvider extends BlockTagsProvider {
    public DeltaVBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, DeltaV.MODID);
    }

    // add tags to blocks in this function 
    @Override
    protected void addTags(HolderLookup.Provider lookupProvider) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.KIMBERLITE.get());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.STEEL_BLOCK.get());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.ALLOY_FURNACE.get());
        this.tag(BlockTags.INCORRECT_FOR_WOODEN_TOOL).add(ModBlocks.STEEL_BLOCK.get());
    }
    
}
