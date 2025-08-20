package com.deltav.deltavmod.block.family;
import static com.deltav.deltavmod.block.ModBlocks.BLOCKS;

import com.deltav.deltavmod.block.ModBlocks;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SandBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;

public class SilicaBlocks {
    
    public static final DeferredBlock<Block> SILICA_SAND = BLOCKS.register(
        "silica_sand",
        registryName -> new SandBlock(new ColorRGBA(11513096),
        BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, registryName))
            .mapColor(MapColor.QUARTZ)
            .instrument(NoteBlockInstrument.SNARE)
            .strength(0.5F)
            .sound(SoundType.SAND))
    );

    public static final DeferredBlock<Block> SILICA_SANDSTONE = BLOCKS.registerSimpleBlock(
        "silica_sandstone",
        BlockBehaviour.Properties.of()
            .mapColor(MapColor.QUARTZ)
            .sound(SoundType.STONE)
            .requiresCorrectToolForDrops()
            .strength(0.8F)
    );

    // Derivatives

    public static final DeferredBlock<WallBlock> SILICA_SANDSTONE_WALL = BLOCKS.register(
        "silica_sandstone_wall",
        registryName -> new WallBlock(BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, registryName))
            .sound(SoundType.STONE)
            .requiresCorrectToolForDrops()
            .strength(0.8f)
            .mapColor(MapColor.QUARTZ))
    );

    public static final DeferredBlock<StairBlock> SILICA_SANDSTONE_STAIRS = BLOCKS.register(
        "silica_sandstone_stairs",
        registryName -> new StairBlock(ModBlocks.SILICA_SANDSTONE.get().defaultBlockState(),
            BlockBehaviour.Properties.of()
                .setId(ResourceKey.create(Registries.BLOCK, registryName))
                .sound(SoundType.STONE)
                .requiresCorrectToolForDrops()
                .strength(0.8f)
                .mapColor(MapColor.SAND))
    );

    public static final DeferredBlock<SlabBlock> SILICA_SANDSTONE_SLAB = BLOCKS.register(
        "silica_sandstone_slab",
        registryName -> new SlabBlock(BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, registryName))
            .sound(SoundType.STONE)
            .requiresCorrectToolForDrops()
            .strength(0.8f)
            .mapColor(MapColor.SAND))
    );

    // Chiseled variant
    public static final DeferredBlock<Block> CHISELED_SILICA_SANDSTONE = BLOCKS.register(
        "chiseled_silica_sandstone",
        registryName -> new Block(BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, registryName))
            .sound(SoundType.STONE)
            .requiresCorrectToolForDrops()
            .strength(0.8f)
            .mapColor(MapColor.SAND))
    );

    // Smooth variant
    public static final DeferredBlock<Block> SMOOTH_SILICA_SANDSTONE = BLOCKS.register(
        "smooth_silica_sandstone",
        registryName -> new Block(BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, registryName))
            .sound(SoundType.STONE)
            .requiresCorrectToolForDrops()
            .strength(1.2f)
            .mapColor(MapColor.SAND))
    );

    public static final DeferredBlock<StairBlock> SMOOTH_SILICA_SANDSTONE_STAIRS = BLOCKS.register(
        "smooth_silica_sandstone_stairs",
        registryName -> new StairBlock(ModBlocks.SMOOTH_SILICA_SANDSTONE.get().defaultBlockState(),
            BlockBehaviour.Properties.of()
                .setId(ResourceKey.create(Registries.BLOCK, registryName))
                .sound(SoundType.STONE)
                .requiresCorrectToolForDrops()
                .strength(1.2f)
                .mapColor(MapColor.SAND))
    );

    public static final DeferredBlock<SlabBlock> SMOOTH_SILICA_SANDSTONE_SLAB = BLOCKS.register(
        "smooth_silica_sandstone_slab",
        registryName -> new SlabBlock(BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, registryName))
            .sound(SoundType.STONE)
            .requiresCorrectToolForDrops()
            .strength(1.2f)
            .mapColor(MapColor.SAND))
    );

    public static final DeferredBlock<Block> CUT_SILICA_SANDSTONE = BLOCKS.register(
        "cut_silica_sandstone",
        registryName -> new Block(BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, registryName))
            .sound(SoundType.STONE)
            .requiresCorrectToolForDrops()
            .strength(0.8f)
            .mapColor(MapColor.SAND))
    );

    public static final DeferredBlock<SlabBlock> CUT_SILICA_SANDSTONE_SLAB = BLOCKS.register(
        "cut_silica_sandstone_slab",
        registryName -> new SlabBlock(BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, registryName))
            .sound(SoundType.STONE)
            .requiresCorrectToolForDrops()
            .strength(0.8f)
            .mapColor(MapColor.SAND))
);
}
