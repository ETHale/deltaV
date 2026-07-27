package com.deltav.deltavmod.data;

import java.util.List;
import java.util.Set;

import com.deltav.deltavmod.DeltaV;
import com.deltav.deltavmod.block.ModBlocks;
import com.deltav.deltavmod.block.energy.cable.modelstate.CableBlockStateModel;
import com.deltav.deltavmod.block.entity.FractionatorBlockEntity;
import com.deltav.deltavmod.block.entity.ModBlockEntities;
import com.deltav.deltavmod.entity.ModEntityTypes;
import com.deltav.deltavmod.entity.ModModelLayerLocations;
import com.deltav.deltavmod.menu.ModMenus;
import com.deltav.deltavmod.particle.LatexDripParticleProvider;
import com.deltav.deltavmod.particle.ModParticleDescriptionProvider;
import com.deltav.deltavmod.particle.ModParticlesTypes;
import com.deltav.deltavmod.particle.SteamParticleProvider;
import com.deltav.deltavmod.screen.custom.CrusherScreen;
import com.deltav.deltavmod.sound.ModSoundDefinitionsProvider;
import com.deltav.deltavmod.item.ModItems;

import net.minecraft.client.color.block.BlockTintSources;
import net.minecraft.client.model.object.boat.BoatModel;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.entity.BlockEntityTypes;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterBlockStateModels;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.transfer.fluid.FluidStacksResourceHandler;

// event handler for data gen classes 
// MAKE SURE EVERYTHING IS STATIC
@EventBusSubscriber(modid = DeltaV.MODID, value = Dist.CLIENT)
public class DeltaVDataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        // tags
        event.createProvider(DeltaVBlockTagsProvider::new);
        event.createProvider(DeltaVItemTagsProvider::new);
        event.createProvider(DeltaVBiomeTagsProvider::new);
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
            Capabilities.Energy.BLOCK,
            ModBlockEntities.BASIC_BATTERY_BE.get(), 
            (be, side) -> be.getEnergyStorage(side)
        );
        event.registerBlockEntity(
            Capabilities.Energy.BLOCK,
            ModBlockEntities.COPPER_CABLE_BE.get(), 
            (be, side) -> be.getEnergyHandler()
        );
        event.registerBlockEntity(
            Capabilities.Energy.BLOCK,
            ModBlockEntities.INSULATED_COPPER_CABLE_BE.get(), 
            (be, side) -> be.getEnergyHandler()
        );

        // Register that the Fractionator block entity has a fluid handler capability.
        event.registerBlockEntity(
            Capabilities.Fluid.BLOCK,
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
            Capabilities.Fluid.ITEM,
            (stack, ctx) -> new FluidStacksResourceHandler(
                1,
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
        event.enqueueWork(() -> {
            //Sheets.addWoodType(RubberWoodBlocks.RUBBERWOOD_TYPE);
            DeltaVCauldronRegistry.bootStrap();
        });

        ((FlowerPotBlock)Blocks.FLOWER_POT).addPlant(ModBlocks.RUBBERWOOD_SAPLING.getId(), () -> ModBlocks.POTTED_RUBBERWOOD_SAPLING.get());
    }

    @SubscribeEvent
    public static void registerFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticlesTypes.STEAM.get(), SteamParticleProvider::new);
        event.registerSpriteSet(ModParticlesTypes.LATEX_DRIP.get(), LatexDripParticleProvider::new);
    }

    @SubscribeEvent 
    public static void registerDefinitions(RegisterBlockStateModels event) {
        event.registerModel(CableBlockStateModel.Unbaked.ID, CableBlockStateModel.Unbaked.CODEC);
    }

    @SubscribeEvent
    public static void onRegisterBlockColors(RegisterColorHandlersEvent.BlockTintSources event) {
        event.register(
            List.of(BlockTintSources.constant(-12031986)),
            ModBlocks.RUBBERWOOD_LEAVES.get()
        );
    }

    @SubscribeEvent
    public static void registerBlockEntityTypes(BlockEntityTypeAddBlocksEvent event) {
        event.modify(
            BlockEntityTypes.SIGN, 
            ModBlocks.RUBBERWOOD_SIGN.get(),
            ModBlocks.RUBBERWOOD_WALL_SIGN.get()
        );

        event.modify(
            BlockEntityTypes.HANGING_SIGN,
            ModBlocks.RUBBERWOOD_HANGING_SIGN.get(),
            ModBlocks.RUBBERWOOD_WALL_HANGING_SIGN.get()
        );
    }

    @SubscribeEvent
    public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        //event.registerBlockEntityRenderer(BlockEntityTypes.SIGN, abstr);
        event.registerEntityRenderer(ModEntityTypes.RUBBERWOOD_BOAT.get(), (context) -> new BoatRenderer(
                context, 
                ModModelLayerLocations.RUBBERWOOD_BOAT
            ));
        event.registerEntityRenderer(ModEntityTypes.RUBBERWOOD_CHEST_BOAT.get(), (context) -> new BoatRenderer(
                context, 
                ModModelLayerLocations.RUBBERWOOD_CHEST_BOAT
            ));
    }

    @SubscribeEvent
    public static void registerModelLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(
            ModModelLayerLocations.RUBBERWOOD_BOAT, 
            () -> BoatModel.createBoatModel()
        );
        event.registerLayerDefinition(
            ModModelLayerLocations.RUBBERWOOD_CHEST_BOAT, 
            () -> BoatModel.createChestBoatModel()
        );
    }

}