package com.deltav.deltavmod.data;

import static com.deltav.deltavmod.block.entity.ModBlockEntities.BASIC_BATTERY_BE;

import java.util.List;
import java.util.Set;

import com.deltav.deltavmod.DeltaV;
import com.deltav.deltavmod.block.energy.generators.RedstoneGenerator;

import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;

// event handler for data gen classes 
// MAKE SURE EVERYTHING IS STATIC
@EventBusSubscriber(modid = DeltaV.MODID, value = Dist.CLIENT)
public class DeltaVDataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        // tags
        event.createProvider(DeltaVBlockTagsProvider::new);
        event.createProvider(DeltaVModelProvider::new);
    }


    @SubscribeEvent
    public static void onGatherData(GatherDataEvent.Client event) {
        // block loot tables
        event.createProvider((output, lookupProvider) -> new LootTableProvider(
            output, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(
                    DeltaVBlockLootProvider::new,
                    LootContextParamSets.BLOCK
                )
            ), lookupProvider));
        // datapack
        event.createDatapackRegistryObjects(DeltaVDatapackProvider.BUILDER);
    }

    @SubscribeEvent 
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
            Capabilities.EnergyStorage.BLOCK,
            BASIC_BATTERY_BE.get(), 
            (be, side) -> be.getEnergyStorage(side)
        );
    }
}
