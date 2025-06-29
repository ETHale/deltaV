package com.deltav.deltavmod.block;


import com.deltav.deltavmod.DeltaV;
import com.deltav.deltavmod.block.custom.AlloyFurnaceBlock;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {

    public static final DeferredRegister.Blocks BLOCKS =
        DeferredRegister.createBlocks(DeltaV.MODID);

    public static final DeferredBlock<Block> STEEL_BLOCK = BLOCKS.register(
        "steel_block",
        registryName -> new Block(BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, registryName))
            .mapColor(MapColor.METAL)
            .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
            .requiresCorrectToolForDrops()
            .strength(5.0f, 6.0f)
            .sound(SoundType.IRON))
    );

    public static final DeferredBlock<AlloyFurnaceBlock> ALLOY_FURNACE = BLOCKS.register(
        "alloy_furnace",
        registryName -> new AlloyFurnaceBlock(BlockBehaviour.Properties.of()
        .setId(ResourceKey.create(Registries.BLOCK, registryName))
        .mapColor(MapColor.STONE)
        .instrument(NoteBlockInstrument.BASEDRUM)
        .requiresCorrectToolForDrops()
        .strength(3.5f))
    );
    
    // Kimberlite blocks have been moved to their own file to reduce file length
    // but have still been added like so for convience of referencing
    public static final DeferredBlock<Block> KIMBERLITE = KimberliteBlocks.KIMBERLITE;
    public static final DeferredBlock<StairBlock> KIMBERLITE_STAIRS = KimberliteBlocks.KIMBERLITE_STAIRS;
    public static final DeferredBlock<SlabBlock> KIMBERLITE_SLAB = KimberliteBlocks.KIMBERLITE_SLAB;
    public static final DeferredBlock<PressurePlateBlock> KIMBERLITE_PRESSURE_PLATE = KimberliteBlocks.KIMBERLITE_PRESSURE_PLATE;
    public static final DeferredBlock<ButtonBlock> KIMBERLITE_BUTTON = KimberliteBlocks.KIMBERLITE_BUTTON;
    public static final DeferredBlock<WallBlock> KIMBERLITE_WALL = KimberliteBlocks.KIMBERLITE_WALL;

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
