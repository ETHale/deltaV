package com.deltav.deltavmod.block;

import com.deltav.deltavmod.DeltaV;
import com.deltav.deltavmod.block.custom.AlloyFurnaceBlock;
import com.deltav.deltavmod.block.custom.CrusherBlock;
import com.deltav.deltavmod.block.custom.FractionatorBlock;
import com.deltav.deltavmod.block.custom.MoltenBedrockBlock;
import com.deltav.deltavmod.block.energy.batteries.BasicBattery;
import com.deltav.deltavmod.block.energy.cable.cables.CopperCableBlock;
import com.deltav.deltavmod.block.energy.cable.cables.InsulatedCopperCableBlock;
import com.deltav.deltavmod.block.energy.generators.RedstoneGenerator;
import com.deltav.deltavmod.block.family.KimberliteBlocks;
import com.deltav.deltavmod.block.family.RubberWoodBlocks;
import com.deltav.deltavmod.block.family.SilicaBlocks;
import com.deltav.deltavmod.block.geyser.SteamGeyser;
import com.deltav.deltavmod.fluid.ModFluidBlocks;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {

    /**
     * Mod block register, this variable can be referenced elsewhere
     * (i.e. not in ModBlocks)
     */
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

    public static final DeferredBlock<RedstoneGenerator> REDSTONE_GENERATOR =  BLOCKS.register(
        "redstone_generator", 
        registryName -> new RedstoneGenerator(
            BlockBehaviour.Properties.of()
                .setId(ResourceKey.create(Registries.BLOCK, registryName))
                .mapColor(MapColor.METAL)
                .strength(4.0F, 6.0F)
                .sound(SoundType.IRON)
                .noOcclusion()
        )
    );

    public static final DeferredBlock<BasicBattery> BASIC_BATTERY = BLOCKS.register(
        "basic_battery", 
        registryName -> new BasicBattery(
            BlockBehaviour.Properties.of()
                .setId(ResourceKey.create(Registries.BLOCK, registryName))
                .mapColor(MapColor.METAL)
                .strength(3.0F, 6.0F)
                .sound(SoundType.IRON)
                .noOcclusion())
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

    // Below blocks have been moved to separate files to reduce file length and
    // improve modularity, they are referenced here for convenience of referencing
    //#region Referenced blocks

    // Fluid blocks
    public static final DeferredBlock<Block> OIL_FLUID = ModFluidBlocks.OIL_FLUID;
    public static final DeferredBlock<Block> NAPHTHA_FLUID = ModFluidBlocks.NAPHTHA_FLUID;
    public static final DeferredBlock<Block> PETROL_FLUID = ModFluidBlocks.PETROL_FLUID;
    public static final DeferredBlock<Block> KEROSENE_FLUID = ModFluidBlocks.KEROSENE_FLUID;
    public static final DeferredBlock<Block> THERMAL_WATER_FLUID = ModFluidBlocks.THERMAL_WATER_FLUID;

    // Silica blocks
    public static final DeferredBlock<Block> SILICA_SAND = SilicaBlocks.SILICA_SAND;
    public static final DeferredBlock<Block> SILICA_SANDSTONE = SilicaBlocks.SILICA_SANDSTONE;
    public static final DeferredBlock<WallBlock> SILICA_SANDSTONE_WALL = SilicaBlocks.SILICA_SANDSTONE_WALL;
    public static final DeferredBlock<StairBlock> SILICA_SANDSTONE_STAIRS = SilicaBlocks.SILICA_SANDSTONE_STAIRS;
    public static final DeferredBlock<SlabBlock> SILICA_SANDSTONE_SLAB = SilicaBlocks.SILICA_SANDSTONE_SLAB;
    public static final DeferredBlock<Block> CHISELED_SILICA_SANDSTONE = SilicaBlocks.CHISELED_SILICA_SANDSTONE;
    public static final DeferredBlock<Block> SMOOTH_SILICA_SANDSTONE = SilicaBlocks.SMOOTH_SILICA_SANDSTONE;
    public static final DeferredBlock<StairBlock> SMOOTH_SILICA_SANDSTONE_STAIRS = SilicaBlocks.SMOOTH_SILICA_SANDSTONE_STAIRS;
    public static final DeferredBlock<SlabBlock> SMOOTH_SILICA_SANDSTONE_SLAB = SilicaBlocks.SMOOTH_SILICA_SANDSTONE_SLAB;
    public static final DeferredBlock<Block> CUT_SILICA_SANDSTONE = SilicaBlocks.CUT_SILICA_SANDSTONE;
    public static final DeferredBlock<SlabBlock> CUT_SILICA_SANDSTONE_SLAB = SilicaBlocks.CUT_SILICA_SANDSTONE_SLAB;

    // Kimberlite blocks
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

    // Rubber wood
    public static final DeferredBlock<Block> RUBBERWOOD_LOG = RubberWoodBlocks.RUBBERWOOD_LOG;
    public static final DeferredBlock<Block> STRIPPED_RUBBERWOOD_LOG = RubberWoodBlocks.STRIPPED_RUBBERWOOD_LOG;
    public static final DeferredBlock<Block> RUBBERWOOD_WOOD = RubberWoodBlocks.RUBBERWOOD_WOOD;
    public static final DeferredBlock<Block> STRIPPED_RUBBERWOOD_WOOD = RubberWoodBlocks.STRIPPED_RUBBERWOOD_WOOD;
    public static final DeferredBlock<Block> RUBBERWOOD_LEAVES = RubberWoodBlocks.RUBBERWOOD_LEAVES;
    public static final DeferredBlock<Block> RUBBERWOOD_SAPLING = RubberWoodBlocks.RUBBERWOOD_SAPLING;
    public static final DeferredBlock<Block> POTTED_RUBBERWOOD_SAPLING = RubberWoodBlocks.POTTED_RUBBERWOOD_SAPLING;
    public static final DeferredBlock<Block> RUBBERWOOD_PLANKS = RubberWoodBlocks.RUBBERWOOD_PLANKS;
    public static final DeferredBlock<StairBlock> RUBBERWOOD_STAIRS = RubberWoodBlocks.RUBBERWOOD_STAIRS;
    public static final DeferredBlock<SlabBlock> RUBBERWOOD_SLAB = RubberWoodBlocks.RUBBERWOOD_SLAB;
    public static final DeferredBlock<PressurePlateBlock> RUBBERWOOD_PRESSURE_PLATE = RubberWoodBlocks.RUBBERWOOD_PRESSURE_PLATE;
    public static final DeferredBlock<ButtonBlock> RUBBERWOOD_BUTTON = RubberWoodBlocks.RUBBERWOOD_BUTTON;
    public static final DeferredBlock<FenceBlock> RUBBERWOOD_FENCE = RubberWoodBlocks.RUBBERWOOD_FENCE;
    public static final DeferredBlock<FenceGateBlock> RUBBERWOOD_FENCE_GATE = RubberWoodBlocks.RUBBERWOOD_FENCE_GATE;
    public static final DeferredBlock<DoorBlock> RUBBERWOOD_DOOR = RubberWoodBlocks.RUBBERWOOD_DOOR;
    public static final DeferredBlock<TrapDoorBlock> RUBBERWOOD_TRAPDOOR = RubberWoodBlocks.RUBBERWOOD_TRAPDOOR;
    public static final DeferredBlock<StandingSignBlock> RUBBERWOOD_SIGN = RubberWoodBlocks.RUBBERWOOD_SIGN;
    public static final DeferredBlock<WallSignBlock> RUBBERWOOD_WALL_SIGN = RubberWoodBlocks.RUBBERWOOD_WALL_SIGN;
    public static final DeferredBlock<CeilingHangingSignBlock> RUBBERWOOD_HANGING_SIGN = RubberWoodBlocks.RUBBERWOOD_HANGING_SIGN;
    public static final DeferredBlock<WallHangingSignBlock> RUBBERWOOD_WALL_HANGING_SIGN = RubberWoodBlocks.RUBBERWOOD_WALL_HANGING_SIGN;
    
    //#endregion
    //#region Block entities

    public static final DeferredBlock<AlloyFurnaceBlock> ALLOY_FURNACE = BLOCKS.register(
        "alloy_furnace",
        registryName -> new AlloyFurnaceBlock(BlockBehaviour.Properties.of()
        .setId(ResourceKey.create(Registries.BLOCK, registryName))
        .mapColor(MapColor.STONE)
        .instrument(NoteBlockInstrument.BASEDRUM)
        .requiresCorrectToolForDrops()
        .strength(3.5f))
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

    public static final DeferredBlock<FractionatorBlock> FRACTIONATOR = BLOCKS.register(
        "fractionator", 
        registryName -> new FractionatorBlock(
            BlockBehaviour.Properties.of()
                .setId(ResourceKey.create(Registries.BLOCK, registryName))
                .mapColor(MapColor.METAL)
                .strength(3.0F, 6.0F)
                .sound(SoundType.IRON)
                .noOcclusion())
    );

    public static final DeferredBlock<SteamGeyser> STEAM_GEYSER = BLOCKS.register(
        "steam_geyser", 
        registryName -> new SteamGeyser(
            BlockBehaviour.Properties.of()
                .setId(ResourceKey.create(Registries.BLOCK, registryName))
                .mapColor(MapColor.QUARTZ)
                .instrument(NoteBlockInstrument.SNARE)
                .strength(5.0f, 1.2F)
                .sound(SoundType.STONE))
    );

    public static final DeferredBlock<CopperCableBlock> COPPER_CABLE = BLOCKS.register(
        "copper_cable", 
        registryName -> new CopperCableBlock(
            BlockBehaviour.Properties.of()
                .setId(ResourceKey.create(Registries.BLOCK, registryName))
                .mapColor(MapColor.COLOR_ORANGE)
                .instrument(NoteBlockInstrument.BELL)
                .strength(1.0f)
                .sound(SoundType.METAL)
        ));

    public static final DeferredBlock<InsulatedCopperCableBlock> INSULATED_COPPER_CABLE = BLOCKS.register(
        "insulated_copper_cable", 
        registryName -> new InsulatedCopperCableBlock(
            BlockBehaviour.Properties.of()
                .setId(ResourceKey.create(Registries.BLOCK, registryName))
                .mapColor(MapColor.COLOR_BLACK)
                .instrument(NoteBlockInstrument.BELL)
                .strength(1.0f)
                .sound(SoundType.WOOL)
        ));

    //#endregion

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
