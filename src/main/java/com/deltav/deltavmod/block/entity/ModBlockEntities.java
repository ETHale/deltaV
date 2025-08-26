package com.deltav.deltavmod.block.entity;

import java.util.function.Supplier;

import com.deltav.deltavmod.DeltaV;
import com.deltav.deltavmod.block.ModBlocks;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
        DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, DeltaV.MODID);

    public static final Supplier<BlockEntityType<AlloyFurnaceBlockEntity>> ALLOY_FURNACE_BE =
        BLOCK_ENTITIES.register(
            "alloy_furnace_be",
            () -> new BlockEntityType<>(
                AlloyFurnaceBlockEntity::new,
                false,
                ModBlocks.ALLOY_FURNACE.get()));
    
    public static final Supplier<BlockEntityType<CrusherBlockEntity>> CRUSHER_BE = 
        BLOCK_ENTITIES.register(
            "crusher_be",
            () -> new BlockEntityType<>(
                CrusherBlockEntity::new,
                false,
                ModBlocks.CRUSHER.get()));
    

    public static final Supplier<BlockEntityType<BasicBatteryBlockEntity>> BASIC_BATTERY_BE =
        BLOCK_ENTITIES.register(
            "basic_battery_be",
            () -> new BlockEntityType<>(
                BasicBatteryBlockEntity::new,
                false,
                ModBlocks.BASIC_BATTERY.get()));

    public static final Supplier<BlockEntityType<FractionatorBlockEntity>> FRACTIONATOR_BE =
        BLOCK_ENTITIES.register(
            "fractionator_be",
            () -> new BlockEntityType<>(
                FractionatorBlockEntity::new,
                false,
                ModBlocks.FRACTIONATOR.get()));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
