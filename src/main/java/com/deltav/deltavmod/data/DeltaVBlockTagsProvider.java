package com.deltav.deltavmod.data;

import java.util.concurrent.CompletableFuture;

import com.deltav.deltavmod.DeltaV;
import com.deltav.deltavmod.block.ModBlocks;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;

// adds tags to all our custom blocks
// tags seem to be used to determine traits like - needs stone or higher pickaxe to mine
public class DeltaVBlockTagsProvider extends BlockTagsProvider {
    public DeltaVBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, DeltaV.MODID);
    }

    // add tags to blocks in this function 
    @Override
    protected void addTags(HolderLookup.Provider lookupProvider) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.ALLOY_FURNACE.getKey());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.STEEL_BLOCK.getKey());
        this.tag(BlockTags.NEEDS_STONE_TOOL).add(ModBlocks.STEEL_BLOCK.getKey());
        // ores
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .add(ModBlocks.ZINC_ORE.getKey())
            .add(ModBlocks.DEEPSLATE_ZINC_ORE.getKey())
            .add(ModBlocks.ZINC_BLOCK.getKey())
            .add(ModBlocks.RAW_ZINC_BLOCK.getKey())
            .add(ModBlocks.COBALT_ORE.getKey())
            .add(ModBlocks.DEEPSLATE_COBALT_ORE.getKey())
            .add(ModBlocks.COBALT_BLOCK.getKey())
            .add(ModBlocks.RAW_COBALT_BLOCK.getKey())
            .add(ModBlocks.PRISMIUM_BLOCK.getKey())
            .add(ModBlocks.CRUSHER.getKey())
            .add(ModBlocks.STEAM_GEYSER.getKey())
            ;
        this.tag(BlockTags.NEEDS_STONE_TOOL)
            .add(ModBlocks.ZINC_ORE.getKey())
            .add(ModBlocks.DEEPSLATE_ZINC_ORE.getKey())
            .add(ModBlocks.ZINC_BLOCK.getKey())
            .add(ModBlocks.RAW_ZINC_BLOCK.getKey())
            ;
        this.tag(BlockTags.NEEDS_IRON_TOOL)
            .add(ModBlocks.COBALT_ORE.getKey())
            .add(ModBlocks.DEEPSLATE_COBALT_ORE.getKey())
            .add(ModBlocks.COBALT_BLOCK.getKey())
            .add(ModBlocks.RAW_COBALT_BLOCK.getKey())
            .add(ModBlocks.CRUSHER.getKey())
            .add(ModBlocks.STEAM_GEYSER.getKey())
            ;
        this.tag(BlockTags.NEEDS_DIAMOND_TOOL)
            .add(ModBlocks.PRISMIUM_BLOCK.getKey())
            ;
        this.tag(BlockTags.OVERWORLD_CARVER_REPLACEABLES)
            .add(ModBlocks.ZINC_ORE.getKey())
            .add(ModBlocks.DEEPSLATE_ZINC_ORE.getKey())
            .add(ModBlocks.COBALT_ORE.getKey())
            .add(ModBlocks.DEEPSLATE_COBALT_ORE.getKey())
            ;

        // kimberlite
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .add(ModBlocks.KIMBERLITE.getKey())
            .add(ModBlocks.KIMBERLITE_BUTTON.getKey())
            .add(ModBlocks.KIMBERLITE_PRESSURE_PLATE.getKey()) 
            .add(ModBlocks.KIMBERLITE_SLAB.getKey())
            .add(ModBlocks.KIMBERLITE_STAIRS.getKey()) 
            .add(ModBlocks.KIMBERLITE_WALL.getKey())
            .add(ModBlocks.POLISHED_KIMBERLITE.getKey())
            .add(ModBlocks.POLISHED_KIMBERLITE_STAIRS.getKey())
            .add(ModBlocks.POLISHED_KIMBERLITE_SLAB.getKey())
            .add(ModBlocks.KIMBERLITE_COAL_ORE.getKey())
            .add(ModBlocks.KIMBERLITE_COPPER_ORE.getKey())
            .add(ModBlocks.KIMBERLITE_DIAMOND_ORE.getKey())
            .add(ModBlocks.KIMBERLITE_EMERALD_ORE.getKey())
            .add(ModBlocks.KIMBERLITE_GOLD_ORE.getKey())
            .add(ModBlocks.KIMBERLITE_IRON_ORE.getKey())
            .add(ModBlocks.KIMBERLITE_LAPIS_ORE.getKey())
            .add(ModBlocks.KIMBERLITE_REDSTONE_ORE.getKey())
            .add(ModBlocks.KIMBERLITE_ZINC_ORE.getKey())
            .add(ModBlocks.KIMBERLITE_COBALT_ORE.getKey())
            ;
        this.tag(BlockTags.OVERWORLD_CARVER_REPLACEABLES)
            .add(ModBlocks.KIMBERLITE.getKey())
            .add(ModBlocks.KIMBERLITE_COAL_ORE.getKey())
            .add(ModBlocks.KIMBERLITE_COPPER_ORE.getKey())
            .add(ModBlocks.KIMBERLITE_DIAMOND_ORE.getKey())
            .add(ModBlocks.KIMBERLITE_EMERALD_ORE.getKey())
            .add(ModBlocks.KIMBERLITE_GOLD_ORE.getKey())
            .add(ModBlocks.KIMBERLITE_IRON_ORE.getKey())
            .add(ModBlocks.KIMBERLITE_LAPIS_ORE.getKey())
            .add(ModBlocks.KIMBERLITE_REDSTONE_ORE.getKey())
            .add(ModBlocks.KIMBERLITE_ZINC_ORE.getKey())
            .add(ModBlocks.KIMBERLITE_COBALT_ORE.getKey())
        ;
        this.tag(BlockTags.NEEDS_STONE_TOOL)
            .add(ModBlocks.KIMBERLITE_COPPER_ORE.getKey())
            .add(ModBlocks.KIMBERLITE_IRON_ORE.getKey())
            .add(ModBlocks.KIMBERLITE_LAPIS_ORE.getKey())
            .add(ModBlocks.KIMBERLITE_ZINC_ORE.getKey())
            ;
        this.tag(BlockTags.NEEDS_IRON_TOOL)
            .add(ModBlocks.KIMBERLITE_DIAMOND_ORE.getKey())
            .add(ModBlocks.KIMBERLITE_EMERALD_ORE.getKey())
            .add(ModBlocks.KIMBERLITE_GOLD_ORE.getKey())
            .add(ModBlocks.KIMBERLITE_REDSTONE_ORE.getKey())
            .add(ModBlocks.KIMBERLITE_COBALT_ORE.getKey())
            ;
        
        this.tag(BlockTags.BASE_STONE_OVERWORLD).add(ModBlocks.MOLTEN_BEDROCK.getKey());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.REDSTONE_GENERATOR.getKey());
        this.tag(BlockTags.NEEDS_STONE_TOOL).add(ModBlocks.REDSTONE_GENERATOR.getKey());
        this.tag(BlockTags.NEEDS_STONE_TOOL).add(ModBlocks.BASIC_BATTERY.getKey());

        this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(ModBlocks.SILICA_SAND.getKey());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.SILICA_SANDSTONE.getKey())
                .add(ModBlocks.SILICA_SANDSTONE_SLAB.getKey())
                .add(ModBlocks.SILICA_SANDSTONE_STAIRS.getKey())
                .add(ModBlocks.SILICA_SANDSTONE_WALL.getKey())
                
                .add(ModBlocks.CHISELED_SILICA_SANDSTONE.getKey())
                .add(ModBlocks.CUT_SILICA_SANDSTONE.getKey())
                .add(ModBlocks.CUT_SILICA_SANDSTONE_SLAB.getKey())
                
                .add(ModBlocks.SMOOTH_SILICA_SANDSTONE.getKey())
                .add(ModBlocks.SMOOTH_SILICA_SANDSTONE_SLAB.getKey())
                .add(ModBlocks.SMOOTH_SILICA_SANDSTONE_STAIRS.getKey());
        this.tag(BlockTags.WALLS)
            .add(ModBlocks.KIMBERLITE_WALL.getKey())
            .add(ModBlocks.SILICA_SANDSTONE_WALL.getKey());

        // rubber wood
        this.tag(BlockTags.MINEABLE_WITH_AXE)
            .add(ModBlocks.RUBBERWOOD_LOG.getKey())
            .add(ModBlocks.RUBBERWOOD_WOOD.getKey())
            .add(ModBlocks.STRIPPED_RUBBERWOOD_LOG.getKey())
            .add(ModBlocks.STRIPPED_RUBBERWOOD_WOOD.getKey())
            .add(ModBlocks.RUBBERWOOD_PLANKS.getKey());
        this.tag(BlockTags.LOGS)
            .add(ModBlocks.RUBBERWOOD_LOG.getKey())
            .add(ModBlocks.RUBBERWOOD_WOOD.getKey())
            .add(ModBlocks.STRIPPED_RUBBERWOOD_LOG.getKey())
            .add(ModBlocks.STRIPPED_RUBBERWOOD_WOOD.getKey());
        this.tag(BlockTags.OVERWORLD_NATURAL_LOGS)
            .add(ModBlocks.RUBBERWOOD_LOG.getKey())
            .add(ModBlocks.RUBBERWOOD_WOOD.getKey())
            .add(ModBlocks.STRIPPED_RUBBERWOOD_LOG.getKey())
            .add(ModBlocks.STRIPPED_RUBBERWOOD_WOOD.getKey());
        this.tag(BlockTags.PLANKS).add(ModBlocks.RUBBERWOOD_PLANKS.getKey());
        this.tag(BlockTags.MINEABLE_WITH_HOE)
            .add(ModBlocks.RUBBERWOOD_LEAVES.getKey())
            .add(ModBlocks.RUBBERWOOD_SAPLING.getKey());
        this.tag(BlockTags.LEAVES).add(ModBlocks.RUBBERWOOD_LEAVES.getKey());
        this.tag(BlockTags.WOODEN_SLABS).add(ModBlocks.RUBBERWOOD_SLAB.getKey());
        this.tag(BlockTags.SLABS).add(ModBlocks.RUBBERWOOD_SLAB.getKey());
        this.tag(BlockTags.WOODEN_STAIRS).add(ModBlocks.RUBBERWOOD_STAIRS.getKey());
        this.tag(BlockTags.STAIRS).add(ModBlocks.RUBBERWOOD_STAIRS.getKey());
        this.tag(BlockTags.WOODEN_FENCES).add(ModBlocks.RUBBERWOOD_FENCE.getKey());
        this.tag(BlockTags.FENCE_GATES).add(ModBlocks.RUBBERWOOD_FENCE_GATE.getKey());
        this.tag(BlockTags.WOODEN_DOORS).add(ModBlocks.RUBBERWOOD_DOOR.getKey());
        this.tag(BlockTags.DOORS).add(ModBlocks.RUBBERWOOD_DOOR.getKey());
        this.tag(BlockTags.WOODEN_TRAPDOORS).add(ModBlocks.RUBBERWOOD_TRAPDOOR.getKey());
        this.tag(BlockTags.TRAPDOORS).add(ModBlocks.RUBBERWOOD_TRAPDOOR.getKey());
        this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(ModBlocks.RUBBERWOOD_PRESSURE_PLATE.getKey());
        this.tag(BlockTags.PRESSURE_PLATES).add(ModBlocks.RUBBERWOOD_PRESSURE_PLATE.getKey());
        this.tag(BlockTags.WOODEN_BUTTONS).add(ModBlocks.RUBBERWOOD_BUTTON.getKey());
        this.tag(BlockTags.BUTTONS).add(ModBlocks.RUBBERWOOD_BUTTON.getKey());

        // cauldrons
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .add(ModBlocks.LATEX_CAULDRON.getKey());
    }

}