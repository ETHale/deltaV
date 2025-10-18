package com.deltav.deltavmod.data;

import java.util.Set;

import com.deltav.deltavmod.block.ModBlocks;
import com.deltav.deltavmod.item.ModItems;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.common.Mod;

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
        this.dropSelf(ModBlocks.CRUSHER.get());
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
        this.dropSelf(ModBlocks.REDSTONE_GENERATOR.get());
        this.dropSelf(ModBlocks.BASIC_BATTERY.get());
        this.dropSelf(ModBlocks.SILICA_SAND.get());
        this.dropSelf(ModBlocks.SILICA_SANDSTONE.get());
        this.dropSelf(ModBlocks.CHISELED_SILICA_SANDSTONE.get());
        this.dropSelf(ModBlocks.CUT_SILICA_SANDSTONE.get());
        this.dropSelf(ModBlocks.SMOOTH_SILICA_SANDSTONE.get());
        this.add(ModBlocks.SILICA_SANDSTONE_SLAB.get(),
            block -> createSlabItemTable(ModBlocks.SILICA_SANDSTONE_SLAB.get()));
        this.add(ModBlocks.CUT_SILICA_SANDSTONE_SLAB.get(),
            block -> createSlabItemTable(ModBlocks.CUT_SILICA_SANDSTONE_SLAB.get()));
        this.add(ModBlocks.SMOOTH_SILICA_SANDSTONE_SLAB.get(),
            block -> createSlabItemTable(ModBlocks.SMOOTH_SILICA_SANDSTONE_SLAB.get()));
        this.dropSelf(ModBlocks.SILICA_SANDSTONE_STAIRS.get());
        this.dropSelf(ModBlocks.SILICA_SANDSTONE_WALL.get());
        this.dropSelf(ModBlocks.SMOOTH_SILICA_SANDSTONE_STAIRS.get());
        this.dropSelf(ModBlocks.FRACTIONATOR.get());
        this.dropWhenSilkTouch(ModBlocks.STEAM_GEYSER.get());
        this.dropSelf(ModBlocks.COPPER_CABLE.get());
        this.dropSelf(ModBlocks.RUBBER_LOG.get());
        this.dropSelf(ModBlocks.RUBBER_WOOD.get());
        this.dropSelf(ModBlocks.STRIPPED_RUBBER_LOG.get());
        this.dropSelf(ModBlocks.STRIPPED_RUBBER_WOOD.get());
        this.dropSelf(ModBlocks.RUBBERWOOD_PLANKS.get());
        this.dropSelf(ModBlocks.RUBBER_SAPLING.get());
        this.add(ModBlocks.RUBBER_LEAVES.get(), 
            block -> createLeavesDrops(block, ModBlocks.RUBBER_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        this.dropPottedContents(ModBlocks.POTTED_RUBBER_SAPLING.get());
        this.add(ModBlocks.RUBBERWOOD_SLAB.get(),
            block -> createSlabItemTable(ModBlocks.RUBBERWOOD_SLAB.get()));
        this.dropSelf(ModBlocks.RUBBERWOOD_STAIRS.get());
        this.dropSelf(ModBlocks.RUBBERWOOD_PRESSURE_PLATE.get());
        this.dropSelf(ModBlocks.RUBBERWOOD_BUTTON.get());
        this.dropSelf(ModBlocks.RUBBERWOOD_FENCE.get());
        this.dropSelf(ModBlocks.RUBBERWOOD_FENCE_GATE.get());
        this.dropSelf(ModBlocks.RUBBERWOOD_TRAPDOOR.get());
        this.add(ModBlocks.RUBBERWOOD_DOOR.get(),
            block -> createDoorTable(ModBlocks.RUBBERWOOD_DOOR.get()));
        this.dropSelf(ModBlocks.RUBBERWOOD_SIGN.get());
        this.dropSelf(ModBlocks.RUBBERWOOD_HANGING_SIGN.get());
    }
}
