package com.deltav.deltavmod.item;

import com.deltav.deltavmod.DeltaV;
import com.deltav.deltavmod.block.ModBlocks;
import com.deltav.deltavmod.fluid.ModFluids;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(DeltaV.MODID);

    public static final DeferredItem<BlockItem> STEEL_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("steel_block", ModBlocks.STEEL_BLOCK);
    public static final DeferredItem<BlockItem> ALLOY_FURNACE_ITEM = ITEMS.registerSimpleBlockItem("alloy_furnace", ModBlocks.ALLOY_FURNACE);
    public static final DeferredItem<BlockItem> ZINC_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("zinc_block", ModBlocks.ZINC_BLOCK);
    public static final DeferredItem<BlockItem> ZINC_ORE_ITEM = ITEMS.registerSimpleBlockItem("zinc_ore", ModBlocks.ZINC_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_ZINC_ORE_ITEM = ITEMS.registerSimpleBlockItem("deepslate_zinc_ore", ModBlocks.DEEPSLATE_ZINC_ORE);    
    public static final DeferredItem<BlockItem> RAW_ZINC_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("raw_zinc_block", ModBlocks.RAW_ZINC_BLOCK);

    public static final DeferredItem<BlockItem> COBALT_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("cobalt_block", ModBlocks.COBALT_BLOCK);
    public static final DeferredItem<BlockItem> COBALT_ORE_ITEM = ITEMS.registerSimpleBlockItem("cobalt_ore", ModBlocks.COBALT_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_COBALT_ORE_ITEM = ITEMS.registerSimpleBlockItem("deepslate_cobalt_ore", ModBlocks.DEEPSLATE_COBALT_ORE);    
    public static final DeferredItem<BlockItem> RAW_COBALT_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("raw_cobalt_block", ModBlocks.RAW_COBALT_BLOCK);

    public static final DeferredItem<BlockItem> PRISMIUM_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("prismium_block", ModBlocks.PRISMIUM_BLOCK);
    
    public static final DeferredItem<BlockItem> KIMBERLITE_ITEM = ITEMS.registerSimpleBlockItem("kimberlite", ModBlocks.KIMBERLITE);
    public static final DeferredItem<BlockItem> KIMBERLITE_STAIRS_ITEM = ITEMS.registerSimpleBlockItem("kimberlite_stairs", ModBlocks.KIMBERLITE_STAIRS);
    public static final DeferredItem<BlockItem> KIMBERLITE_SLAB_ITEM = ITEMS.registerSimpleBlockItem("kimberlite_slab", ModBlocks.KIMBERLITE_SLAB);
    public static final DeferredItem<BlockItem> KIMBERLITE_PRESSURE_PLATE_ITEM = ITEMS.registerSimpleBlockItem("kimberlite_pressure_plate", ModBlocks.KIMBERLITE_PRESSURE_PLATE);
    public static final DeferredItem<BlockItem> KIMBERLITE_BUTTON_ITEM = ITEMS.registerSimpleBlockItem("kimberlite_button", ModBlocks.KIMBERLITE_BUTTON);
    public static final DeferredItem<BlockItem> KIMBERLITE_WALL_ITEM = ITEMS.registerSimpleBlockItem("kimberlite_wall", ModBlocks.KIMBERLITE_WALL);
    public static final DeferredItem<BlockItem> POLISHED_KIMBERLITE_ITEM = ITEMS.registerSimpleBlockItem("polished_kimberlite", ModBlocks.POLISHED_KIMBERLITE);
    public static final DeferredItem<BlockItem> POLISHED_KIMBERLITE_STAIRS_ITEM = ITEMS.registerSimpleBlockItem("polished_kimberlite_stairs", ModBlocks.POLISHED_KIMBERLITE_STAIRS);
    public static final DeferredItem<BlockItem> POLISHED_KIMBERLITE_SLAB_ITEM = ITEMS.registerSimpleBlockItem("polished_kimberlite_slab", ModBlocks.POLISHED_KIMBERLITE_SLAB);
    public static final DeferredItem<BlockItem> KIMBERLITE_COAL_ORE_ITEM = ITEMS.registerSimpleBlockItem("kimberlite_coal_ore", ModBlocks.KIMBERLITE_COAL_ORE);
    public static final DeferredItem<BlockItem> KIMBERLITE_COPPER_ORE_ITEM = ITEMS.registerSimpleBlockItem("kimberlite_copper_ore", ModBlocks.KIMBERLITE_COPPER_ORE);
    public static final DeferredItem<BlockItem> KIMBERLITE_DIAMOND_ORE_ITEM = ITEMS.registerSimpleBlockItem("kimberlite_diamond_ore", ModBlocks.KIMBERLITE_DIAMOND_ORE);
    public static final DeferredItem<BlockItem> KIMBERLITE_EMERALD_ORE_ITEM = ITEMS.registerSimpleBlockItem("kimberlite_emerald_ore", ModBlocks.KIMBERLITE_EMERALD_ORE);
    public static final DeferredItem<BlockItem> KIMBERLITE_GOLD_ORE_ITEM = ITEMS.registerSimpleBlockItem("kimberlite_gold_ore", ModBlocks.KIMBERLITE_GOLD_ORE);
    public static final DeferredItem<BlockItem> KIMBERLITE_LAPIS_ORE_ITEM = ITEMS.registerSimpleBlockItem("kimberlite_lapis_ore", ModBlocks.KIMBERLITE_LAPIS_ORE);
    public static final DeferredItem<BlockItem> KIMBERLITE_REDSTONE_ORE_ITEM = ITEMS.registerSimpleBlockItem("kimberlite_redstone_ore", ModBlocks.KIMBERLITE_REDSTONE_ORE);
    public static final DeferredItem<BlockItem> KIMBERLITE_ZINC_ORE_ITEM = ITEMS.registerSimpleBlockItem("kimberlite_zinc_ore", ModBlocks.KIMBERLITE_ZINC_ORE);
    public static final DeferredItem<BlockItem> KIMBERLITE_IRON_ORE_ITEM = ITEMS.registerSimpleBlockItem("kimberlite_iron_ore", ModBlocks.KIMBERLITE_IRON_ORE);
    public static final DeferredItem<BlockItem> KIMBERLITE_COBALT_ORE_ITEM = ITEMS.registerSimpleBlockItem("kimberlite_cobalt_ore", ModBlocks.KIMBERLITE_COBALT_ORE);
    public static final DeferredItem<BlockItem> SILICA_SAND_ITEM = ITEMS.registerSimpleBlockItem("silica_sand", ModBlocks.SILICA_SAND);
    public static final DeferredItem<BlockItem> SILICA_SANDSTONE_ITEM = ITEMS.registerSimpleBlockItem("silica_sandstone", ModBlocks.SILICA_SANDSTONE);
    public static final DeferredItem<BlockItem> SILICA_SANDSTONE_SLAB_ITEM = ITEMS.registerSimpleBlockItem("silica_sandstone_slab", ModBlocks.SILICA_SANDSTONE_SLAB);
    public static final DeferredItem<BlockItem> SILICA_SANDSTONE_STAIRS_ITEM = ITEMS.registerSimpleBlockItem("silica_sandstone_stairs", ModBlocks.SILICA_SANDSTONE_STAIRS);
    public static final DeferredItem<BlockItem> SILICA_SANDSTONE_WALL_ITEM = ITEMS.registerSimpleBlockItem("silica_sandstone_wall", ModBlocks.SILICA_SANDSTONE_WALL);
    public static final DeferredItem<BlockItem> CUT_SILICA_SANDSTONE_ITEM = ITEMS.registerSimpleBlockItem("cut_silica_sandstone", ModBlocks.CUT_SILICA_SANDSTONE);
    public static final DeferredItem<BlockItem> CUT_SILICA_SANDSTONE_SLAB_ITEM = ITEMS.registerSimpleBlockItem("cut_silica_sandstone_slab", ModBlocks.CUT_SILICA_SANDSTONE_SLAB);
    public static final DeferredItem<BlockItem> CHISELED_SILICA_SANDSTONE_ITEM = ITEMS.registerSimpleBlockItem("chiseled_silica_sandstone", ModBlocks.CHISELED_SILICA_SANDSTONE);
    public static final DeferredItem<BlockItem> SMOOTH_SILICA_SANDSTONE_ITEM = ITEMS.registerSimpleBlockItem("smooth_silica_sandstone", ModBlocks.SMOOTH_SILICA_SANDSTONE);
    public static final DeferredItem<BlockItem> SMOOTH_SILICA_SANDSTONE_STAIRS_ITEM = ITEMS.registerSimpleBlockItem("smooth_silica_sandstone_stairs", ModBlocks.SMOOTH_SILICA_SANDSTONE_STAIRS);
    public static final DeferredItem<BlockItem> SMOOTH_SILICA_SANDSTONE_SLAB_ITEM = ITEMS.registerSimpleBlockItem("smooth_silica_sandstone_slab", ModBlocks.SMOOTH_SILICA_SANDSTONE_SLAB);

    
    public static final DeferredItem<BlockItem> MOLTEN_BEDROCK_ITEM = ITEMS.registerSimpleBlockItem("molten_bedrock", ModBlocks.MOLTEN_BEDROCK);
    public static final DeferredItem<BlockItem> CRUSHER_ITEM = ITEMS.registerSimpleBlockItem("crusher", ModBlocks.CRUSHER);
    public static final DeferredItem<BlockItem> REDSTONE_GENERATOR_ITEM = ITEMS.registerSimpleBlockItem("redstone_generator", ModBlocks.REDSTONE_GENERATOR);
    public static final DeferredItem<BlockItem> BASIC_BATTERY_ITEM = ITEMS.registerSimpleBlockItem("basic_battery", ModBlocks.BASIC_BATTERY);
    public static final DeferredItem<BlockItem> STEAM_GEYSER_ITEM = ITEMS.registerSimpleBlockItem("steam_geyser", ModBlocks.STEAM_GEYSER);
    
    public static final DeferredItem<Item> STEEL_INGOT = ITEMS.registerSimpleItem("steel_ingot");
    public static final DeferredItem<Item> ZINC_INGOT = ITEMS.registerSimpleItem("zinc_ingot");
    public static final DeferredItem<Item> RAW_ZINC = ITEMS.registerSimpleItem("raw_zinc");
    public static final DeferredItem<Item> COBALT_INGOT = ITEMS.registerSimpleItem("cobalt_ingot");
    public static final DeferredItem<Item> RAW_COBALT = ITEMS.registerSimpleItem("raw_cobalt");
    public static final DeferredItem<Item> ZINC_BATTERY = ITEMS.registerSimpleItem("zinc_battery");
    public static final DeferredItem<Item> SILICA_DUST = ITEMS.registerSimpleItem("silica_dust");
    public static final DeferredItem<Item> SILICON = ITEMS.registerSimpleItem("silicon");

    public static final DeferredItem<Item> GLOOPY_RESIDUE = ITEMS.registerSimpleItem("gloopy_residue");

    public static final DeferredItem<BucketItem> OIL_BUCKET = ITEMS.register(
        "oil_bucket",
        registryName -> new BucketItem(
            ModFluids.OIL_SOURCE.value(),
            new Item.Properties()
                .setId(ResourceKey.create(Registries.ITEM, registryName))
                .craftRemainder(Items.BUCKET)
                .stacksTo(1)
    ));

    public static final DeferredItem<BucketItem> NAPHTHA_BUCKET = ITEMS.register(
        "naphtha_bucket",
        registryName -> new BucketItem(
            ModFluids.NAPHTHA_SOURCE.value(),
            new Item.Properties()
                .setId(ResourceKey.create(Registries.ITEM, registryName))
                .craftRemainder(Items.BUCKET)
                .stacksTo(1)
    ));

    public static final DeferredItem<BucketItem> PETROL_BUCKET = ITEMS.register(
        "petrol_bucket",
        registryName -> new BucketItem(
            ModFluids.PETROL_SOURCE.value(),
            new Item.Properties()
                .setId(ResourceKey.create(Registries.ITEM, registryName))
                .craftRemainder(Items.BUCKET)
                .stacksTo(1)
    ));

    public static final DeferredItem<BucketItem> KEROSENE_BUCKET = ITEMS.register(
        "kerosene_bucket",
        registryName -> new BucketItem(
            ModFluids.KEROSENE_SOURCE.value(),
            new Item.Properties()
                .setId(ResourceKey.create(Registries.ITEM, registryName))
                .craftRemainder(Items.BUCKET)
                .stacksTo(1)
    ));

    public static final DeferredItem<BucketItem> THERMAL_WATER_BUCKET = ITEMS.register(
        "thermal_water_bucket",
        registryName -> new BucketItem(
            ModFluids.THERMAL_WATER_SOURCE.value(),
            new Item.Properties()
                .setId(ResourceKey.create(Registries.ITEM, registryName))
                .craftRemainder(Items.BUCKET)
                .stacksTo(1)
    ));

    public static final DeferredItem<BlockItem> FRACTIONATOR = ITEMS.registerSimpleBlockItem("fractionator", ModBlocks.FRACTIONATOR);
    public static final DeferredItem<BarrelItem> BARREL = ITEMS.register(
        "barrel",
        registryName -> new BarrelItem(
            new Item.Properties()
                .setId(ResourceKey.create(Registries.ITEM, registryName))
        ));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
