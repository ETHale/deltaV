package com.deltav.deltavmod.block.family;

import static com.deltav.deltavmod.block.ModBlocks.BLOCKS;

import com.deltav.deltavmod.DeltaV;
import com.deltav.deltavmod.block.ModBlocks;
import com.deltav.deltavmod.block.custom.FlammableRotatedPillarBlock;
import com.deltav.deltavmod.worldgen.tree.DeltaVTreeGrowers;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.TintedParticleLeavesBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;

public class RubberWoodBlocks {
    public static final String RUBBERWOOD_NAME = DeltaV.MODID + ":rubberwood";
    public static final BlockSetType RUBBERWOOD_SET = BlockSetType.register(new BlockSetType(RUBBERWOOD_NAME));
    public static final WoodType RUBBERWOOD_TYPE = WoodType.register(new WoodType(RUBBERWOOD_NAME, RUBBERWOOD_SET));

    public static final DeferredBlock<Block> RUBBERWOOD_LOG = BLOCKS.register(
        "rubberwood_log", 
        registryName -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, registryName))
            .mapColor(MapColor.WOOD)
            .instrument(NoteBlockInstrument.BASS)
            .strength(2.0F)
            .sound(SoundType.WOOD)
            .ignitedByLava()
        )
    );

    public static final DeferredBlock<Block> RUBBERWOOD_WOOD = BLOCKS.register(
        "rubberwood_wood", 
        registryName -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, registryName))
            .mapColor(MapColor.WOOD)
            .instrument(NoteBlockInstrument.BASS)
            .strength(2.0F)
            .sound(SoundType.WOOD)
            .ignitedByLava()
        )
    );

    public static final DeferredBlock<Block> STRIPPED_RUBBERWOOD_LOG = BLOCKS.register(
        "stripped_rubberwood_log", 
        registryName -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, registryName))
            .mapColor(MapColor.WOOD)
            .instrument(NoteBlockInstrument.BASS)
            .strength(2.0F)
            .sound(SoundType.WOOD)
            .ignitedByLava()
        )
    );

    public static final DeferredBlock<Block> STRIPPED_RUBBERWOOD_WOOD = BLOCKS.register(
        "stripped_rubberwood_wood", 
        registryName -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, registryName))
            .mapColor(MapColor.WOOD)
            .instrument(NoteBlockInstrument.BASS)
            .strength(2.0F)
            .sound(SoundType.WOOD)
            .ignitedByLava()
        )
    );

    public static final DeferredBlock<Block> RUBBERWOOD_LEAVES = BLOCKS.register(
        "rubberwood_leaves", 
        registryName -> new TintedParticleLeavesBlock(0.01F, BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, registryName))
            .mapColor(MapColor.PLANT)
            .strength(0.2F)
            .randomTicks()
            .sound(SoundType.GRASS)
            .noOcclusion()
            .isValidSpawn(Blocks::ocelotOrParrot)
            .isSuffocating((state, level, pos) -> false)
            .isViewBlocking((state, level, pos) -> false)
            .ignitedByLava()
            .pushReaction(PushReaction.DESTROY)
            .isRedstoneConductor((state, level, pos) -> false)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 60;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 30;
                }
            }
    );

    public static final DeferredBlock<Block> RUBBERWOOD_SAPLING = BLOCKS.register(
        "rubberwood_sapling", 
        registryName -> new SaplingBlock(
            DeltaVTreeGrowers.RUBBERWOODTREE,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)
                .setId(ResourceKey.create(Registries.BLOCK, registryName)
            )
    ));

    public static final DeferredBlock<Block> POTTED_RUBBERWOOD_SAPLING = BLOCKS.register(
        "potted_rubberwood_sapling", 
        registryName -> new FlowerPotBlock(
            () -> (FlowerPotBlock) Blocks.FLOWER_POT, 
            RUBBERWOOD_SAPLING,
            BlockBehaviour.Properties.of()
                .setId(ResourceKey.create(Registries.BLOCK, registryName))
                .noOcclusion()
                .instabreak()
                .pushReaction(PushReaction.DESTROY)
    ));

    public static final DeferredBlock<Block> RUBBERWOOD_PLANKS = BLOCKS.register(
        "rubberwood_planks", 
        registryName -> new Block(BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, registryName))
            .mapColor(MapColor.WOOD)
            .instrument(NoteBlockInstrument.BASS)
            .strength(2.0F)
            .sound(SoundType.WOOD)
            .ignitedByLava()) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }
            }
    );

    public static final DeferredBlock<StairBlock> RUBBERWOOD_STAIRS = BLOCKS.register(
        "rubberwood_stairs", 
        registryName -> new StairBlock(
            ModBlocks.RUBBERWOOD_PLANKS.get().defaultBlockState(), 
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS)
                .setId(ResourceKey.create(Registries.BLOCK, registryName)
            )) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }
            }
    );

    public static final DeferredBlock<SlabBlock> RUBBERWOOD_SLAB = BLOCKS.register(
        "rubberwood_slab", 
        registryName -> new SlabBlock(
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB)
                .setId(ResourceKey.create(Registries.BLOCK, registryName)
            )) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }
            }
    );

    public static final DeferredBlock<PressurePlateBlock> RUBBERWOOD_PRESSURE_PLATE = BLOCKS.register(
        "rubberwood_pressure_plate",
        registryName -> new PressurePlateBlock(RUBBERWOOD_SET,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE)
                .setId(ResourceKey.create(Registries.BLOCK, registryName)
            )) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }
            }
    );

    public static final DeferredBlock<ButtonBlock> RUBBERWOOD_BUTTON = BLOCKS.register(
        "rubberwood_button",
        registryName -> new ButtonBlock(RUBBERWOOD_SET, 15,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON)
                .setId(ResourceKey.create(Registries.BLOCK, registryName)
            )) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }
            }
    );

    public static final DeferredBlock<FenceBlock> RUBBERWOOD_FENCE = BLOCKS.register(
        "rubberwood_fence", 
        registryName -> new FenceBlock(
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE)
                .setId(ResourceKey.create(Registries.BLOCK, registryName)
            )) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }
            }
    );
    
    public static final DeferredBlock<FenceGateBlock> RUBBERWOOD_FENCE_GATE = BLOCKS.register(
        "rubberwood_fence_gate", 
        registryName -> new FenceGateBlock(RUBBERWOOD_TYPE, 
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE)
                .setId(ResourceKey.create(Registries.BLOCK, registryName)
            )) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }
            }
    );

    public static final DeferredBlock<DoorBlock> RUBBERWOOD_DOOR = BLOCKS.register(
        "rubberwood_door", 
        registryName -> new DoorBlock(RUBBERWOOD_SET, 
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR)
                .setId(ResourceKey.create(Registries.BLOCK, registryName)
            )) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }
            }
    );

    public static final DeferredBlock<TrapDoorBlock> RUBBERWOOD_TRAPDOOR = BLOCKS.register(
        "rubberwood_trapdoor", 
        registryName -> new TrapDoorBlock(RUBBERWOOD_SET, 
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR)
                .setId(ResourceKey.create(Registries.BLOCK, registryName)
            )) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }
            }
    );

    public static final DeferredBlock<StandingSignBlock> RUBBERWOOD_SIGN = BLOCKS.register(
        "rubberwood_sign", 
        registryName -> new StandingSignBlock(RUBBERWOOD_TYPE, 
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN)
                .setId(ResourceKey.create(Registries.BLOCK, registryName)
            )) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }
            }
    );

    public static final DeferredBlock<WallSignBlock> RUBBERWOOD_WALL_SIGN = BLOCKS.register(
        "rubberwood_wall_sign", 
        registryName -> new WallSignBlock(RUBBERWOOD_TYPE,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN)
                .setId(ResourceKey.create(Registries.BLOCK, registryName)
            )) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }
            }
    );

    public static final DeferredBlock<CeilingHangingSignBlock> RUBBERWOOD_HANGING_SIGN = BLOCKS.register(
        "rubberwood_hanging_sign", 
        registryName -> new CeilingHangingSignBlock(RUBBERWOOD_TYPE,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN)
                .setId(ResourceKey.create(Registries.BLOCK, registryName)
            )) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }
            }
    );

    public static final DeferredBlock<WallHangingSignBlock> RUBBERWOOD_WALL_HANGING_SIGN = BLOCKS.register(
        "rubberwood_wall_hanging_sign", 
        registryName -> new WallHangingSignBlock(RUBBERWOOD_TYPE,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN)
                .setId(ResourceKey.create(Registries.BLOCK, registryName)
            )) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }
            }
    );
}
