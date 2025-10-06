package com.deltav.deltavmod.block.family;

import static com.deltav.deltavmod.block.ModBlocks.BLOCKS;

import com.deltav.deltavmod.block.custom.FlammableRotatedPillarBlock;
import com.deltav.deltavmod.worldgen.tree.DeltaVTreeGrowers;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TintedParticleLeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
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

    public static final DeferredBlock<Block> RUBBER_PLANKS = BLOCKS.register(
        "rubber_planks", 
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

    public static final DeferredBlock<Block> RUBBER_LEAVES = BLOCKS.register(
        "rubber_leaves", 
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

    public static final DeferredBlock<Block> RUBBER_SAPLING = BLOCKS.register(
        "rubber_sapling", 
        registryName -> new SaplingBlock(
            DeltaVTreeGrowers.RUBBER_TREE,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)
                .setId(ResourceKey.create(Registries.BLOCK, registryName)
            )
    ));

    public static final DeferredBlock<Block> POTTED_RUBBER_SAPLING = BLOCKS.register(
        "potted_rubber_sapling", 
        registryName -> new FlowerPotBlock(
            () -> (FlowerPotBlock) Blocks.FLOWER_POT, 
            RUBBER_SAPLING,
            BlockBehaviour.Properties.of()
                .setId(ResourceKey.create(Registries.BLOCK, registryName))
                .noOcclusion()
                .instabreak()
                .pushReaction(PushReaction.DESTROY)
    ));
}
