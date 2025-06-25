package com.deltav.deltavmod.data;

import java.util.List;
import java.util.Set;

import com.deltav.deltavmod.DeltaV;

import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

// event handler for data gen classes 
// MAKE SURE EVERYTHING IS STATIC
@EventBusSubscriber(modid = DeltaV.MODID, value = Dist.CLIENT)
public class DeltaVDataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        // tags
        event.createProvider(DeltaVBlockTagsProvider::new);
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
    }
}
