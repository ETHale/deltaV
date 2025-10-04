package com.deltav.deltavmod.block.family;

import static com.deltav.deltavmod.block.ModBlocks.BLOCKS;
import static com.deltav.deltavmod.block.ModBlocks.STRIPPED_RUBBER_LOG;

import com.deltav.deltavmod.block.custom.FlammableRotatedPillarBlock;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;

public class RubberWoodBlocks {
    public static final DeferredBlock<Block> RUBBER_LOG = BLOCKS.register(
        "rubber_log", 
        registryName -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, registryName))
            .mapColor(MapColor.WOOD)
            .instrument(NoteBlockInstrument.BASS)
            .strength(2.0F)
            .sound(SoundType.WOOD)
            .ignitedByLava()
        )
    );

    public static final DeferredBlock<Block> RUBBER_WOOD = BLOCKS.register(
        "rubber_wood", 
        registryName -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, registryName))
            .mapColor(MapColor.WOOD)
            .instrument(NoteBlockInstrument.BASS)
            .strength(2.0F)
            .sound(SoundType.WOOD)
            .ignitedByLava()
        )
    );

    public static final DeferredBlock<Block> STRIPPED_RUBBER_LOG = BLOCKS.register(
        "stripped_rubber_log", 
        registryName -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, registryName))
            .mapColor(MapColor.WOOD)
            .instrument(NoteBlockInstrument.BASS)
            .strength(2.0F)
            .sound(SoundType.WOOD)
            .ignitedByLava()
        )
    );

    public static final DeferredBlock<Block> STRIPPED_RUBBER_WOOD = BLOCKS.register(
        "stripped_rubber_wood", 
        registryName -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, registryName))
            .mapColor(MapColor.WOOD)
            .instrument(NoteBlockInstrument.BASS)
            .strength(2.0F)
            .sound(SoundType.WOOD)
            .ignitedByLava()
        )
    );
}
