package com.deltav.deltavmod.data;

import java.util.List;
import java.util.Set;

import com.deltav.deltavmod.DeltaV;
import com.deltav.deltavmod.block.ModBlocks;
import com.deltav.deltavmod.block.energy.cable.modelstate.CableBlockStateModel;
import com.deltav.deltavmod.block.entity.FractionatorBlockEntity;
import com.deltav.deltavmod.block.entity.ModBlockEntities;
import com.deltav.deltavmod.menu.ModMenus;
import com.deltav.deltavmod.particle.ModParticleDescriptionProvider;
import com.deltav.deltavmod.particle.ModParticles;
import com.deltav.deltavmod.particle.SteamParticleProvider;
import com.deltav.deltavmod.screen.custom.CrusherScreen;
import com.deltav.deltavmod.sound.ModSoundDefinitionsProvider;
import com.deltav.deltavmod.fluid.ModFluids;
import com.deltav.deltavmod.item.ModItems;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.RegisterBlockStateModels;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStack;

// event handler for data gen classes 
// MAKE SURE EVERYTHING IS STATIC
@EventBusSubscriber(modid = DeltaV.MODID, value = Dist.CLIENT)
public class DeltaVDataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        // tags
        event.createProvider(DeltaVBlockTagsProvider::new);
        event.createProvider(DeltaVItemTagsProvider::new);
        event.createProvider(DeltaVModelProvider::new);
        event.createProvider(ModParticleDescriptionProvider::new);
        event.createProvider(ModSoundDefinitionsProvider::new);
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
            ModBlockEntities.BASIC_BATTERY_BE.get(), 
            (be, side) -> be.getEnergyStorage(side)
        );
        event.registerBlockEntity(
            Capabilities.EnergyStorage.BLOCK,
            ModBlockEntities.COPPER_CABLE_BE.get(), 
            (be, side) -> be.getEnergyHandler()
        );

        // Register that the Fractionator block entity has a fluid handler capability.
        event.registerBlockEntity(
            Capabilities.FluidHandler.BLOCK,
            ModBlockEntities.FRACTIONATOR_BE.get(),
            (be, side) -> {
                if (be instanceof FractionatorBlockEntity fbe) {
                    return fbe.getFluidHandler(side);
                }
                return null;
            }
        );

        /**
         * Register that the Barrel item has a fluid handler capability. The fluid name is
         * provided by ModDataComponents.GENERIC_FLUID.
         */
        event.registerItem(
            Capabilities.FluidHandler.ITEM,
            (stack, ctx) -> new FluidHandlerItemStack(
                ModDataComponents.GENERIC_FLUID,
                stack,
                4000
            ), ModItems.BARREL.get()
        );
    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        // SFI: This could be moved into CrusherScreen class
        event.register(ModMenus.CRUSHER_MENU.get(), CrusherScreen::new);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // item models
        ItemBlockRenderTypes.setRenderLayer(ModFluids.OIL_FLOW.get(), ChunkSectionLayer.TRANSLUCENT);
        ItemBlockRenderTypes.setRenderLayer(ModFluids.OIL_SOURCE.get(), ChunkSectionLayer.TRANSLUCENT);
        ItemBlockRenderTypes.setRenderLayer(ModFluids.THERMAL_WATER_FLOW.get(), ChunkSectionLayer.TRANSLUCENT);
        ItemBlockRenderTypes.setRenderLayer(ModFluids.THERMAL_WATER_SOURCE.get(), ChunkSectionLayer.TRANSLUCENT);

        // isn't deprecated because it will be removed but because they want jsons instead
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.RUBBER_SAPLING.get(), ChunkSectionLayer.CUTOUT);
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_RUBBER_SAPLING.get(), ChunkSectionLayer.CUTOUT);
        ((FlowerPotBlock)Blocks.FLOWER_POT).addPlant(ModBlocks.RUBBER_SAPLING.getId(), () -> ModBlocks.POTTED_RUBBER_SAPLING.get());
    }

    @SubscribeEvent
    public static void registerFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.STEAM.get(), SteamParticleProvider::new);
    }

    @SubscribeEvent
    public static void registerDefinitions(RegisterBlockStateModels event) {
        event.registerModel(CableBlockStateModel.Unbaked.ID, CableBlockStateModel.Unbaked.CODEC);
    }

    @SubscribeEvent
    public static void onRegisterBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register(
            (state, world, pos, tintIndex) -> tintIndex == 0 ? -12031986 : 0xFFFFFF,
            ModBlocks.RUBBER_LEAVES.get()
        );
    }
}
