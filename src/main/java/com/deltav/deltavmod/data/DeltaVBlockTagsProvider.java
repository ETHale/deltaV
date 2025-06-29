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

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.STEEL_BLOCK.get());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.ALLOY_FURNACE.get());
        this.tag(BlockTags.NEEDS_STONE_TOOL).add(ModBlocks.STEEL_BLOCK.get());

        // zinc blocks
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.ZINC_ORE.get());
        this.tag(BlockTags.NEEDS_STONE_TOOL).add(ModBlocks.ZINC_ORE.get());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.DEEPSLATE_ZINC_ORE.get());
        this.tag(BlockTags.NEEDS_STONE_TOOL).add(ModBlocks.DEEPSLATE_ZINC_ORE.get());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.ZINC_BLOCK.get());
        this.tag(BlockTags.NEEDS_STONE_TOOL).add(ModBlocks.ZINC_BLOCK.get());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.RAW_ZINC_BLOCK.get());
        this.tag(BlockTags.NEEDS_STONE_TOOL).add(ModBlocks.RAW_ZINC_BLOCK.get());
        this.tag(BlockTags.OVERWORLD_CARVER_REPLACEABLES).add(ModBlocks.ZINC_ORE.get());
        this.tag(BlockTags.OVERWORLD_CARVER_REPLACEABLES).add(ModBlocks.DEEPSLATE_ZINC_ORE.get());
        
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .add(ModBlocks.KIMBERLITE.get())
            .add(ModBlocks.KIMBERLITE_BUTTON.get())
            .add(ModBlocks.KIMBERLITE_PRESSURE_PLATE.get()) 
            .add(ModBlocks.KIMBERLITE_SLAB.get())
            .add(ModBlocks.KIMBERLITE_STAIRS.get()) 
            .add(ModBlocks.KIMBERLITE_WALL.get());
        this.tag(BlockTags.WALLS).add(ModBlocks.KIMBERLITE_WALL.get());

    }
    
}
