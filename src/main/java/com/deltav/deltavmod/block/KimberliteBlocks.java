package com.deltav.deltavmod.block;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;

import static com.deltav.deltavmod.block.ModBlocks.BLOCKS;

public class KimberliteBlocks {
    
    public static final DeferredBlock<Block> KIMBERLITE = BLOCKS.registerSimpleBlock(
        "kimberlite", 
        BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, null))
            .sound(SoundType.STONE)
            .requiresCorrectToolForDrops()
            .strength(1.4f, 5.0f)
            .mapColor(MapColor.COLOR_BLACK)
    );

    public static final DeferredBlock<StairBlock> KIMBERLITE_STAIRS = BLOCKS.register(
        "kimberlite_stairs",
        registryName -> new StairBlock(ModBlocks.KIMBERLITE.get().defaultBlockState(), 
            BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, registryName))
            .sound(SoundType.STONE)
            .requiresCorrectToolForDrops()
            .strength(1.4f, 5.0f)
            .mapColor(MapColor.COLOR_BLACK))
    );

    public static final DeferredBlock<SlabBlock> KIMBERLITE_SLAB = BLOCKS.register(
        "kimberlite_slab",
        registryName -> new SlabBlock(BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, registryName))
            .sound(SoundType.STONE)
            .requiresCorrectToolForDrops()
            .strength(1.4f, 5.0f)
            .mapColor(MapColor.COLOR_BLACK))
    );

    public static final DeferredBlock<PressurePlateBlock> KIMBERLITE_PRESSURE_PLATE = BLOCKS.register(
        "kimberlite_pressure_plate",
        registryName -> new PressurePlateBlock(BlockSetType.STONE,
            BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, registryName))
            .sound(SoundType.STONE)
            .requiresCorrectToolForDrops()
            .strength(1.4f, 5.0f)
            .mapColor(MapColor.COLOR_BLACK))
    );

    public static final DeferredBlock<ButtonBlock> KIMBERLITE_BUTTON = BLOCKS.register(
        "kimberlite_button",
        registryName -> new ButtonBlock(BlockSetType.STONE, 25,
            BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, registryName))
            .sound(SoundType.STONE)
            .requiresCorrectToolForDrops()
            .strength(1.4f, 5.0f)
            .mapColor(MapColor.COLOR_BLACK)
            .noCollission())
    );

    public static final DeferredBlock<WallBlock> KIMBERLITE_WALL = BLOCKS.register(
        "kimberlite_wall",
        registryName -> new WallBlock(BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, registryName))
            .sound(SoundType.STONE)
            .requiresCorrectToolForDrops()
            .strength(1.4f, 5.0f)
            .mapColor(MapColor.COLOR_BLACK))
    );
}
