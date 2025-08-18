package com.deltav.deltavmod.data;

import static com.deltav.deltavmod.block.entity.ModBlockEntities.BASIC_BATTERY_BE;

import java.util.List;
import java.util.Set;

import com.deltav.deltavmod.DeltaV;
import com.deltav.deltavmod.menu.ModMenus;
import com.deltav.deltavmod.screen.BasicBatteryScreen;
import com.deltav.deltavmod.fluid.ModFluidTypes;
import com.deltav.deltavmod.fluid.ModFluids;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
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

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenus.BASIC_BATTERY_MENU.get(), BasicBatteryScreen::new);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // item models
        ItemBlockRenderTypes.setRenderLayer(ModFluids.OIL_FLOWING.get(), ChunkSectionLayer.TRANSLUCENT);
        ItemBlockRenderTypes.setRenderLayer(ModFluids.OIL_SOURCE.get(), ChunkSectionLayer.TRANSLUCENT);
    }

    @SubscribeEvent
    static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerFluidType(new IClientFluidTypeExtensions() {
            private static final ResourceLocation OIL_STILL = ResourceLocation.fromNamespaceAndPath(DeltaV.MODID, "block/oil");
            private static final ResourceLocation OIL_FLOW = ResourceLocation.fromNamespaceAndPath(DeltaV.MODID, "block/oil_flow");
            private static final ResourceLocation OIL_OVERLAY = ResourceLocation.fromNamespaceAndPath(DeltaV.MODID, "block/oil_overlay");

            @Override
            public ResourceLocation getStillTexture() {
                return OIL_STILL;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return OIL_FLOW;
            }

            @Override
            public ResourceLocation getOverlayTexture() {
                return OIL_OVERLAY;
            }

            @Override
            public int getTintColor() {
                return 0xFF010A1C;
            }

            @Override
            public int getTintColor(FluidState state, BlockAndTintGetter getter, BlockPos pos) {
                // return BiomeColors.getAverageWaterColor(getter, pos) | 0xFF000000;
                return getTintColor();
            }
        }, ModFluidTypes.OIL_FLUID_TYPE.get());
    }
}
