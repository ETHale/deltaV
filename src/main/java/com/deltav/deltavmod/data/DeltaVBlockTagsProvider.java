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
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.ALLOY_FURNACE.get());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.STEEL_BLOCK.get());
        this.tag(BlockTags.NEEDS_STONE_TOOL).add(ModBlocks.STEEL_BLOCK.get());
        // ores
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .add(ModBlocks.ZINC_ORE.get())
            .add(ModBlocks.DEEPSLATE_ZINC_ORE.get())
            .add(ModBlocks.ZINC_BLOCK.get())
            .add(ModBlocks.RAW_ZINC_BLOCK.get())
            .add(ModBlocks.COBALT_ORE.get())
            .add(ModBlocks.DEEPSLATE_COBALT_ORE.get())
            .add(ModBlocks.COBALT_BLOCK.get())
            .add(ModBlocks.RAW_COBALT_BLOCK.get())
            .add(ModBlocks.PRISMIUM_BLOCK.get())
            .add(ModBlocks.CRUSHER.get())
            .add(ModBlocks.STEAM_GEYSER.get())
            ;
        this.tag(BlockTags.NEEDS_STONE_TOOL)
            .add(ModBlocks.ZINC_ORE.get())
            .add(ModBlocks.DEEPSLATE_ZINC_ORE.get())
            .add(ModBlocks.ZINC_BLOCK.get())
            .add(ModBlocks.RAW_ZINC_BLOCK.get())
            ;
        this.tag(BlockTags.NEEDS_IRON_TOOL)
            .add(ModBlocks.COBALT_ORE.get())
            .add(ModBlocks.DEEPSLATE_COBALT_ORE.get())
            .add(ModBlocks.COBALT_BLOCK.get())
            .add(ModBlocks.RAW_COBALT_BLOCK.get())
            .add(ModBlocks.CRUSHER.get())
            .add(ModBlocks.STEAM_GEYSER.get())
            ;
        this.tag(BlockTags.NEEDS_DIAMOND_TOOL)
            .add(ModBlocks.PRISMIUM_BLOCK.get())
            ;
        this.tag(BlockTags.OVERWORLD_CARVER_REPLACEABLES)
            .add(ModBlocks.ZINC_ORE.get())
            .add(ModBlocks.DEEPSLATE_ZINC_ORE.get())
            .add(ModBlocks.COBALT_ORE.get())
            .add(ModBlocks.DEEPSLATE_COBALT_ORE.get())
            ;

        // kimberlite
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .add(ModBlocks.KIMBERLITE.get())
            .add(ModBlocks.KIMBERLITE_BUTTON.get())
            .add(ModBlocks.KIMBERLITE_PRESSURE_PLATE.get()) 
            .add(ModBlocks.KIMBERLITE_SLAB.get())
            .add(ModBlocks.KIMBERLITE_STAIRS.get()) 
            .add(ModBlocks.KIMBERLITE_WALL.get())
            .add(ModBlocks.POLISHED_KIMBERLITE.get())
            .add(ModBlocks.POLISHED_KIMBERLITE_STAIRS.get())
            .add(ModBlocks.POLISHED_KIMBERLITE_SLAB.get())
            .add(ModBlocks.KIMBERLITE_COAL_ORE.get())
            .add(ModBlocks.KIMBERLITE_COPPER_ORE.get())
            .add(ModBlocks.KIMBERLITE_DIAMOND_ORE.get())
            .add(ModBlocks.KIMBERLITE_EMERALD_ORE.get())
            .add(ModBlocks.KIMBERLITE_GOLD_ORE.get())
            .add(ModBlocks.KIMBERLITE_IRON_ORE.get())
            .add(ModBlocks.KIMBERLITE_LAPIS_ORE.get())
            .add(ModBlocks.KIMBERLITE_REDSTONE_ORE.get())
            .add(ModBlocks.KIMBERLITE_ZINC_ORE.get())
            .add(ModBlocks.KIMBERLITE_COBALT_ORE.get())
            ;
        this.tag(BlockTags.OVERWORLD_CARVER_REPLACEABLES)
            .add(ModBlocks.KIMBERLITE.get())
            .add(ModBlocks.KIMBERLITE_COAL_ORE.get())
            .add(ModBlocks.KIMBERLITE_COPPER_ORE.get())
            .add(ModBlocks.KIMBERLITE_DIAMOND_ORE.get())
            .add(ModBlocks.KIMBERLITE_EMERALD_ORE.get())
            .add(ModBlocks.KIMBERLITE_GOLD_ORE.get())
            .add(ModBlocks.KIMBERLITE_IRON_ORE.get())
            .add(ModBlocks.KIMBERLITE_LAPIS_ORE.get())
            .add(ModBlocks.KIMBERLITE_REDSTONE_ORE.get())
            .add(ModBlocks.KIMBERLITE_ZINC_ORE.get())
            .add(ModBlocks.KIMBERLITE_COBALT_ORE.get())
        ;
        this.tag(BlockTags.NEEDS_STONE_TOOL)
            .add(ModBlocks.KIMBERLITE_COPPER_ORE.get())
            .add(ModBlocks.KIMBERLITE_IRON_ORE.get())
            .add(ModBlocks.KIMBERLITE_LAPIS_ORE.get())
            .add(ModBlocks.KIMBERLITE_ZINC_ORE.get())
            ;
        this.tag(BlockTags.NEEDS_IRON_TOOL)
            .add(ModBlocks.KIMBERLITE_DIAMOND_ORE.get())
            .add(ModBlocks.KIMBERLITE_EMERALD_ORE.get())
            .add(ModBlocks.KIMBERLITE_GOLD_ORE.get())
            .add(ModBlocks.KIMBERLITE_REDSTONE_ORE.get())
            .add(ModBlocks.KIMBERLITE_COBALT_ORE.get())
            ;
        
        this.tag(BlockTags.BASE_STONE_OVERWORLD).add(ModBlocks.MOLTEN_BEDROCK.get());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.REDSTONE_GENERATOR.get());
        this.tag(BlockTags.NEEDS_STONE_TOOL).add(ModBlocks.REDSTONE_GENERATOR.get());
        this.tag(BlockTags.NEEDS_STONE_TOOL).add(ModBlocks.BASIC_BATTERY.get());

        this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(ModBlocks.SILICA_SAND.get());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.SILICA_SANDSTONE.get())
                .add(ModBlocks.SILICA_SANDSTONE_SLAB.get())
                .add(ModBlocks.SILICA_SANDSTONE_STAIRS.get())
                .add(ModBlocks.SILICA_SANDSTONE_WALL.get())
                
                .add(ModBlocks.CHISELED_SILICA_SANDSTONE.get())
                .add(ModBlocks.CUT_SILICA_SANDSTONE.get())
                .add(ModBlocks.CUT_SILICA_SANDSTONE_SLAB.get())
                
                .add(ModBlocks.SMOOTH_SILICA_SANDSTONE.get())
                .add(ModBlocks.SMOOTH_SILICA_SANDSTONE_SLAB.get())
                .add(ModBlocks.SMOOTH_SILICA_SANDSTONE_STAIRS.get());
        this.tag(BlockTags.WALLS)
            .add(ModBlocks.KIMBERLITE_WALL.get())
            .add(ModBlocks.SILICA_SANDSTONE_WALL.get());

        // rubber wood
        this.tag(BlockTags.MINEABLE_WITH_AXE)
            .add(ModBlocks.RUBBERWOODLOG.get())
            .add(ModBlocks.RUBBERWOODWOOD.get())
            .add(ModBlocks.STRIPPED_RUBBERWOODLOG.get())
            .add(ModBlocks.STRIPPED_RUBBERWOODWOOD.get())
            .add(ModBlocks.RUBBERWOOD_PLANKS.get());
        this.tag(BlockTags.LOGS)
            .add(ModBlocks.RUBBERWOODLOG.get())
            .add(ModBlocks.RUBBERWOODWOOD.get())
            .add(ModBlocks.STRIPPED_RUBBERWOODLOG.get())
            .add(ModBlocks.STRIPPED_RUBBERWOODWOOD.get());
        this.tag(BlockTags.LOGS_THAT_BURN)
            .add(ModBlocks.RUBBERWOODLOG.get())
            .add(ModBlocks.RUBBERWOODWOOD.get())
            .add(ModBlocks.STRIPPED_RUBBERWOODLOG.get())
            .add(ModBlocks.STRIPPED_RUBBERWOODWOOD.get());
        this.tag(BlockTags.PLANKS).add(ModBlocks.RUBBERWOOD_PLANKS.get());
        this.tag(BlockTags.MINEABLE_WITH_HOE)
            .add(ModBlocks.RUBBERWOODLEAVES.get())
            .add(ModBlocks.RUBBERWOODSAPLING.get());
        this.tag(BlockTags.LEAVES).add(ModBlocks.RUBBERWOODLEAVES.get());
    }
    
}
