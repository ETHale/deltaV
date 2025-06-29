package com.deltav.deltavmod.item;

import com.deltav.deltavmod.DeltaV;
import com.deltav.deltavmod.block.ModBlocks;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS =
        DeferredRegister.createItems(DeltaV.MODID);

    public static final DeferredItem<BlockItem> STEEL_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("steel_block", ModBlocks.STEEL_BLOCK);
    public static final DeferredItem<BlockItem> ALLOY_FURNACE_ITEM = ITEMS.registerSimpleBlockItem("alloy_furnace", ModBlocks.ALLOY_FURNACE);
    public static final DeferredItem<BlockItem> ZINC_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("zinc_block", ModBlocks.ZINC_BLOCK);
    public static final DeferredItem<BlockItem> ZINC_ORE_ITEM = ITEMS.registerSimpleBlockItem("zinc_ore", ModBlocks.ZINC_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_ZINC_ORE_ITEM = ITEMS.registerSimpleBlockItem("deepslate_zinc_ore", ModBlocks.DEEPSLATE_ZINC_ORE);    
    public static final DeferredItem<BlockItem> RAW_ZINC_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("raw_zinc_block", ModBlocks.RAW_ZINC_BLOCK);
    
    public static final DeferredItem<BlockItem> KIMBERLITE_ITEM = ITEMS.registerSimpleBlockItem("kimberlite", ModBlocks.KIMBERLITE);
    public static final DeferredItem<BlockItem> KIMBERLITE_STAIRS_ITEM = ITEMS.registerSimpleBlockItem("kimberlite_stairs", ModBlocks.KIMBERLITE_STAIRS);
    public static final DeferredItem<BlockItem> KIMBERLITE_SLAB_ITEM = ITEMS.registerSimpleBlockItem("kimberlite_slab", ModBlocks.KIMBERLITE_SLAB);
    public static final DeferredItem<BlockItem> KIMBERLITE_PRESSURE_PLATE_ITEM = ITEMS.registerSimpleBlockItem("kimberlite_pressure_plate", ModBlocks.KIMBERLITE_PRESSURE_PLATE);
    public static final DeferredItem<BlockItem> KIMBERLITE_BUTTON_ITEM = ITEMS.registerSimpleBlockItem("kimberlite_button", ModBlocks.KIMBERLITE_BUTTON);
    public static final DeferredItem<BlockItem> KIMBERLITE_WALL_ITEM = ITEMS.registerSimpleBlockItem("kimberlite_wall", ModBlocks.KIMBERLITE_WALL);
    public static final DeferredItem<BlockItem> POLISHED_KIMBERLITE_ITEM = ITEMS.registerSimpleBlockItem("polished_kimberlite", ModBlocks.POLISHED_KIMBERLITE);
    public static final DeferredItem<BlockItem> POLISHED_KIMBERLITE_STAIRS_ITEM = ITEMS.registerSimpleBlockItem("polished_kimberlite_stairs", ModBlocks.POLISHED_KIMBERLITE_STAIRS);
    public static final DeferredItem<BlockItem> POLISHED_KIMBERLITE_SLAB_ITEM = ITEMS.registerSimpleBlockItem("polished_kimberlite_slab", ModBlocks.POLISHED_KIMBERLITE_SLAB);

    public static final DeferredItem<Item> STEEL_INGOT = ITEMS.registerSimpleItem("steel_ingot");
    public static final DeferredItem<Item> ZINC_INGOT = ITEMS.registerSimpleItem("zinc_ingot");
    public static final DeferredItem<Item> RAW_ZINC = ITEMS.registerSimpleItem("raw_zinc");

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
