package com.deltav.deltavmod.block;


import com.deltav.deltavmod.DeltaV;
import com.deltav.deltavmod.block.custom.AlloyFurnaceBlock;
import com.deltav.deltavmod.block.custom.CrusherBlock;
import com.deltav.deltavmod.block.custom.MoltenBedrockBlock;

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
    public static final DeferredBlock<Block> POLISHED_KIMBERLITE = KimberliteBlocks.POLISHED_KIMBERLITE;
    public static final DeferredBlock<StairBlock> POLISHED_KIMBERLITE_STAIRS = KimberliteBlocks.POLISHED_KIMBERLITE_STAIRS;
    public static final DeferredBlock<SlabBlock> POLISHED_KIMBERLITE_SLAB = KimberliteBlocks.POLISHED_KIMBERLITE_SLAB;
    public static final DeferredBlock<Block> KIMBERLITE_COAL_ORE = KimberliteBlocks.KIMBERLITE_COAL_ORE;
    public static final DeferredBlock<Block> KIMBERLITE_COPPER_ORE = KimberliteBlocks.KIMBERLITE_COPPER_ORE;
    public static final DeferredBlock<Block> KIMBERLITE_DIAMOND_ORE = KimberliteBlocks.KIMBERLITE_DIAMOND_ORE;
    public static final DeferredBlock<Block> KIMBERLITE_EMERALD_ORE = KimberliteBlocks.KIMBERLITE_EMERALD_ORE;
    public static final DeferredBlock<Block> KIMBERLITE_GOLD_ORE = KimberliteBlocks.KIMBERLITE_GOLD_ORE;
    public static final DeferredBlock<Block> KIMBERLITE_LAPIS_ORE = KimberliteBlocks.KIMBERLITE_LAPIS_ORE;
    public static final DeferredBlock<Block> KIMBERLITE_REDSTONE_ORE = KimberliteBlocks.KIMBERLITE_REDSTONE_ORE;
    public static final DeferredBlock<Block> KIMBERLITE_ZINC_ORE = KimberliteBlocks.KIMBERLITE_ZINC_ORE;
    public static final DeferredBlock<Block> KIMBERLITE_IRON_ORE = KimberliteBlocks.KIMBERLITE_IRON_ORE;
    public static final DeferredBlock<Block> KIMBERLITE_COBALT_ORE = KimberliteBlocks.KIMBERLITE_COBALT_ORE;

    public static final DeferredBlock<Block> ZINC_BLOCK = BLOCKS.registerSimpleBlock(
        "zinc_block",
        BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, null))
            .mapColor(MapColor.METAL)
            .instrument(NoteBlockInstrument.BELL)
            .requiresCorrectToolForDrops()
            .strength(3.0F, 6.0F)
            .sound(SoundType.METAL)
    );

    public static final DeferredBlock<Block> ZINC_ORE = BLOCKS.registerSimpleBlock(
        "zinc_ore",
        BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, null))
            .mapColor(MapColor.STONE)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .requiresCorrectToolForDrops()
            .strength(3.0F, 3.0F)
    );

    public static final DeferredBlock<Block> DEEPSLATE_ZINC_ORE = BLOCKS.registerSimpleBlock(
        "deepslate_zinc_ore",
        BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, null))
            .mapColor(MapColor.STONE)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .requiresCorrectToolForDrops()
            .strength(3.0F, 3.0F)
    );

    public static final DeferredBlock<Block> RAW_ZINC_BLOCK = BLOCKS.registerSimpleBlock(
        "raw_zinc_block",
        BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, null))
            .mapColor(MapColor.METAL)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .requiresCorrectToolForDrops()
            .strength(5.0F, 6.0F)
    );

    public static final DeferredBlock<Block> COBALT_BLOCK = BLOCKS.registerSimpleBlock(
        "cobalt_block",
        BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, null))
            .mapColor(MapColor.METAL)
            .instrument(NoteBlockInstrument.BELL)
            .requiresCorrectToolForDrops()
            .strength(3.0F, 6.0F)
            .sound(SoundType.METAL)
    );

    public static final DeferredBlock<Block> COBALT_ORE = BLOCKS.registerSimpleBlock(
        "cobalt_ore",
        BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, null))
            .mapColor(MapColor.STONE)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .requiresCorrectToolForDrops()
            .strength(3.0F, 3.0F)
    );

    public static final DeferredBlock<Block> DEEPSLATE_COBALT_ORE = BLOCKS.registerSimpleBlock(
        "deepslate_cobalt_ore",
        BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, null))
            .mapColor(MapColor.STONE)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .requiresCorrectToolForDrops()
            .strength(3.0F, 3.0F)
    );

    public static final DeferredBlock<Block> RAW_COBALT_BLOCK = BLOCKS.registerSimpleBlock(
        "raw_cobalt_block",
        BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, null))
            .mapColor(MapColor.METAL)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .requiresCorrectToolForDrops()
            .strength(5.0F, 6.0F)
    );

    public static final DeferredBlock<MoltenBedrockBlock> MOLTEN_BEDROCK = BLOCKS.register(
        "molten_bedrock",
        registryName -> new MoltenBedrockBlock(
            BlockBehaviour.Properties.of()
                .setId(ResourceKey.create(Registries.BLOCK, registryName))
                .mapColor(MapColor.FIRE)
                .strength(-1.0F, 3600000.0F)
                .lightLevel(state -> 3)
                .sound(SoundType.STONE)
                .isValidSpawn((a, b, c, d) -> d.fireImmune())
                .hasPostProcess((a, b ,c) -> true)
                .emissiveRendering((a, b ,c) -> true)
            )
    );

    public static final DeferredBlock<Block> PRISMIUM_BLOCK = BLOCKS.registerSimpleBlock(
        "prismium_block",
        BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, null))
            .mapColor(MapColor.DIAMOND)
            .instrument(NoteBlockInstrument.CHIME)
            .requiresCorrectToolForDrops()
            .strength(5.0F, 6.0F)
            .sound(SoundType.AMETHYST)
    );

    public static final DeferredBlock<Block> CRUSHER = BLOCKS.register(
        "crusher",
        registryName -> new CrusherBlock(
            BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, registryName))
            .mapColor(MapColor.METAL)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .requiresCorrectToolForDrops()
            .strength(5.0F, 6.0F)
        )
    );

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
