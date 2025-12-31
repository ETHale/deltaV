package com.deltav.deltavmod.entity;

import java.util.function.Supplier;

import com.deltav.deltavmod.DeltaV;
import com.deltav.deltavmod.item.ModItems;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntityTypes {
    public static final DeferredRegister.Entities ENTITY_TYPES = DeferredRegister.createEntities(DeltaV.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<Boat>> RUBBERWOOD_BOAT = ENTITY_TYPES.register(
        "rubberwood_boat",
        () -> EntityType.Builder.of(
                boatFactory(() -> { return ModItems.RUBBERWOOD_BOAT_ITEM.get();}), 
                MobCategory.MISC
            )
            .noLootTable()
            .sized(1.375F, 0.5625F)
            .eyeHeight(0.5625F)
            .clientTrackingRange(10)
            .build(ResourceKey.create(
                Registries.ENTITY_TYPE,
                ResourceLocation.fromNamespaceAndPath(DeltaV.MODID, "rubberwood_boat")))
    );
    private static EntityType.EntityFactory<Boat> boatFactory(Supplier<Item> boatItemGetter) {
        return (p_375558_, p_375559_) -> new Boat(p_375558_, p_375559_, boatItemGetter);
    }
    public static final DeferredHolder<EntityType<?>, EntityType<ChestBoat>> RUBBERWOOD_CHEST_BOAT = ENTITY_TYPES.register(
        "rubberwood_chest_boat",
        () -> EntityType.Builder.of(
                chestBoatFactory(() -> { return ModItems.RUBBERWOOD_CHEST_BOAT_ITEM.get();}), 
                MobCategory.MISC
            )
            .noLootTable()
            .sized(1.375F, 0.5625F)
            .eyeHeight(0.5625F)
            .clientTrackingRange(10)
            .build(ResourceKey.create(
                Registries.ENTITY_TYPE,
                ResourceLocation.fromNamespaceAndPath(DeltaV.MODID, "rubberwood_chest_boat")))
    );
    private static EntityType.EntityFactory<ChestBoat> chestBoatFactory(Supplier<Item> boatItemGetter) {
        return (p_375555_, p_375556_) -> new ChestBoat(p_375555_, p_375556_, boatItemGetter);
    }


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
