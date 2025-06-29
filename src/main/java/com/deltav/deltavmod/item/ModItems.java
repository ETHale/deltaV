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
    public static final DeferredRegister.Items ITEMS =
        DeferredRegister.createItems(DeltaV.MODID);

    public static final DeferredItem<BlockItem> STEEL_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("steel_block", ModBlocks.STEEL_BLOCK);
    public static final DeferredItem<BlockItem> ALLOY_FURNACE_ITEM = ITEMS.registerSimpleBlockItem("alloy_furnace", ModBlocks.ALLOY_FURNACE);
    
    public static final DeferredItem<BlockItem> KIMBERLITE_ITEM = ITEMS.registerSimpleBlockItem("kimberlite", ModBlocks.KIMBERLITE);
    public static final DeferredItem<BlockItem> KIMBERLITE_STAIRS_ITEM = ITEMS.registerSimpleBlockItem("kimberlite_stairs", ModBlocks.KIMBERLITE_STAIRS);
    public static final DeferredItem<BlockItem> KIMBERLITE_SLAB_ITEM = ITEMS.registerSimpleBlockItem("kimberlite_slab", ModBlocks.KIMBERLITE_SLAB);
    public static final DeferredItem<BlockItem> KIMBERLITE_PRESSURE_PLATE_ITEM = ITEMS.registerSimpleBlockItem("kimberlite_pressure_plate", ModBlocks.KIMBERLITE_PRESSURE_PLATE);
    public static final DeferredItem<BlockItem> KIMBERLITE_BUTTON_ITEM = ITEMS.registerSimpleBlockItem("kimberlite_button", ModBlocks.KIMBERLITE_BUTTON);
    public static final DeferredItem<BlockItem> KIMBERLITE_WALL_ITEM = ITEMS.registerSimpleBlockItem("kimberlite_wall", ModBlocks.KIMBERLITE_WALL);

    public static final DeferredItem<Item> STEEL_INGOT = ITEMS.registerSimpleItem("steel_ingot");


    public static final DeferredItem<BucketItem> OIL_BUCKET = ITEMS.register(
        "oil_bucket",
        registryName -> new BucketItem(
            ModFluids.OIL_SOURCE.value(),
            new Item.Properties()
                .setId(ResourceKey.create(Registries.ITEM, registryName))
                .craftRemainder(Items.BUCKET)
                .stacksTo(1)
    ));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
