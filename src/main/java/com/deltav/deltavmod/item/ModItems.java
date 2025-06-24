package com.deltav.deltavmod.item;

import com.deltav.deltavmod.DeltaV;
import com.deltav.deltavmod.block.ModBlocks;

import net.minecraft.world.item.BlockItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS =
        DeferredRegister.createItems(DeltaV.MODID);

    public static final DeferredItem<BlockItem> STEEL_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("steel_block", ModBlocks.STEEL_BLOCK);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
