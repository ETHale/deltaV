package com.deltav.deltavmod.fluid;

import static com.deltav.deltavmod.block.ModBlocks.BLOCKS;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;

/**
 * Registration for fluid blocks
 * 
 * @author Adam Crawley
 */
public class ModFluidBlocks {

    private static final BlockBehaviour.Properties FLUID_BLOCK_PROPERTIES =
            BlockBehaviour.Properties.of()
                .noCollission()
                .strength(100.0F)
                .noLootTable()
                .liquid()
                .replaceable()
                .pushReaction(PushReaction.DESTROY)
                .sound(SoundType.EMPTY);

    public static final DeferredBlock<Block> OIL_FLUID = BLOCKS.register(
        "oil",
        registryName -> new LiquidBlock(
            ModFluids.OIL_SOURCE.value(),
            FLUID_BLOCK_PROPERTIES
                .setId(ResourceKey.create(Registries.BLOCK, registryName))
                .mapColor(MapColor.COLOR_BLACK)
        )
    );

    public static final DeferredBlock<Block> NAPHTHA_FLUID = BLOCKS.register(
        "naphtha",
        registryName -> new LiquidBlock(
            ModFluids.NAPHTHA_SOURCE.value(),
            FLUID_BLOCK_PROPERTIES
                .setId(ResourceKey.create(Registries.BLOCK, registryName))
                .mapColor(MapColor.SAND)
        )
    );

    public static final DeferredBlock<Block> PETROL_FLUID = BLOCKS.register(
        "petrol",
        registryName -> new LiquidBlock(
            ModFluids.PETROL_SOURCE.value(),
            FLUID_BLOCK_PROPERTIES
                .setId(ResourceKey.create(Registries.BLOCK, registryName))
                .mapColor(MapColor.COLOR_ORANGE)
        )
    );

    public static final DeferredBlock<Block> KEROSENE_FLUID = BLOCKS.register(
        "kerosene", 
        registryName -> new LiquidBlock(
            ModFluids.KEROSENE_SOURCE.value(),
            FLUID_BLOCK_PROPERTIES
                .setId(ResourceKey.create(Registries.BLOCK, registryName))
                .mapColor(MapColor.COLOR_LIGHT_BLUE)
        )
    );
}
