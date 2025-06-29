package com.deltav.deltavmod.data;

import java.util.Set;

import com.deltav.deltavmod.block.ModBlocks;
import com.deltav.deltavmod.item.ModItems;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

public class DeltaVBlockLootProvider extends BlockLootSubProvider{
    public DeltaVBlockLootProvider(HolderLookup.Provider lookupProvider) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, lookupProvider);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries()
            .stream()
            .map(e -> (Block) e.value())
            .toList();
    }

    // add loot tables to blocks here
    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.KIMBERLITE.get());
        this.dropSelf(ModBlocks.STEEL_BLOCK.get());
        this.dropSelf(ModBlocks.ALLOY_FURNACE.get());
        this.dropSelf(ModBlocks.KIMBERLITE_STAIRS.get());
        this.add(ModBlocks.KIMBERLITE_SLAB.get(),
            block -> createSlabItemTable(ModBlocks.KIMBERLITE_SLAB.get()));
        this.dropSelf(ModBlocks.KIMBERLITE_PRESSURE_PLATE.get());
        this.dropSelf(ModBlocks.KIMBERLITE_BUTTON.get());
        this.dropSelf(ModBlocks.KIMBERLITE_WALL.get());
        this.dropOther(ModBlocks.ZINC_ORE.get(), ModItems.RAW_ZINC.get());
        this.dropOther(ModBlocks.DEEPSLATE_ZINC_ORE.get(), ModItems.RAW_ZINC.get());
        this.dropSelf(ModBlocks.ZINC_BLOCK.get());
        this.dropSelf(ModBlocks.RAW_ZINC_BLOCK.get());
        
        
    }
}
