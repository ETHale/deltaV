package com.deltav.deltavmod.data;

import java.util.Set;

import com.deltav.deltavmod.block.ModBlocks;
import com.deltav.deltavmod.item.ModItems;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
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
        this.dropSelf(ModBlocks.POLISHED_KIMBERLITE.get());
        this.add(ModBlocks.POLISHED_KIMBERLITE_SLAB.get(),
            block -> createSlabItemTable(ModBlocks.POLISHED_KIMBERLITE_SLAB.get()));
        this.dropSelf(ModBlocks.POLISHED_KIMBERLITE_STAIRS.get());
        this.add(ModBlocks.ZINC_ORE.get(), this.createOreDrop(ModBlocks.ZINC_ORE.get(), ModItems.RAW_ZINC.get()));
        this.add(ModBlocks.DEEPSLATE_ZINC_ORE.get(), this.createOreDrop(ModBlocks.DEEPSLATE_ZINC_ORE.get(), ModItems.RAW_ZINC.get()));
        this.dropSelf(ModBlocks.ZINC_BLOCK.get());
        this.dropSelf(ModBlocks.RAW_ZINC_BLOCK.get());
        this.add(ModBlocks.COBALT_ORE.get(), this.createOreDrop(ModBlocks.COBALT_ORE.get(), ModItems.RAW_COBALT.get()));
        this.add(ModBlocks.DEEPSLATE_COBALT_ORE.get(), this.createOreDrop(ModBlocks.DEEPSLATE_COBALT_ORE.get(), ModItems.RAW_COBALT.get()));
        this.dropSelf(ModBlocks.COBALT_BLOCK.get());
        this.dropSelf(ModBlocks.RAW_COBALT_BLOCK.get());
        this.add(ModBlocks.KIMBERLITE_COAL_ORE.get(), this.createOreDrop(ModBlocks.KIMBERLITE_COAL_ORE.get(), Items.COAL));
        this.add(ModBlocks.KIMBERLITE_COPPER_ORE.get(), this.createCopperOreDrops(ModBlocks.KIMBERLITE_COPPER_ORE.get()));
        this.add(ModBlocks.KIMBERLITE_DIAMOND_ORE.get(), this.createOreDrop(ModBlocks.KIMBERLITE_DIAMOND_ORE.get(), Items.DIAMOND));
        this.add(ModBlocks.KIMBERLITE_EMERALD_ORE.get(), this.createOreDrop(ModBlocks.KIMBERLITE_EMERALD_ORE.get(), Items.EMERALD));
        this.add(ModBlocks.KIMBERLITE_GOLD_ORE.get(), this.createOreDrop(ModBlocks.KIMBERLITE_GOLD_ORE.get(), Items.RAW_GOLD));
        this.add(ModBlocks.KIMBERLITE_LAPIS_ORE.get(), this.createLapisOreDrops(ModBlocks.KIMBERLITE_LAPIS_ORE.get()));
        this.add(ModBlocks.KIMBERLITE_REDSTONE_ORE.get(), this.createRedstoneOreDrops(ModBlocks.KIMBERLITE_REDSTONE_ORE.get()));
        this.add(ModBlocks.KIMBERLITE_ZINC_ORE.get(), this.createOreDrop(ModBlocks.KIMBERLITE_ZINC_ORE.get(), ModItems.RAW_ZINC.get()));
        this.add(ModBlocks.KIMBERLITE_IRON_ORE.get(), this.createOreDrop(ModBlocks.KIMBERLITE_IRON_ORE.get(), Items.RAW_IRON));
        this.add(ModBlocks.KIMBERLITE_COBALT_ORE.get(), this.createOreDrop(ModBlocks.KIMBERLITE_COBALT_ORE.get(), ModItems.RAW_COBALT.get()));
        this.dropSelf(ModBlocks.MOLTEN_BEDROCK.get());
        this.dropSelf(ModBlocks.PRISMIUM_BLOCK.get());
    }
}
