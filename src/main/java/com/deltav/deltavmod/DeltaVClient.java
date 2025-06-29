package com.deltav.deltavmod;

import com.deltav.deltavmod.fluid.ModFluidTypes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForgeMod;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = DeltaV.MODID, dist = Dist.CLIENT)
public class DeltaVClient {
    public DeltaVClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    // @SubscribeEvent
    // static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
    //     event.registerFluidType(new IClientFluidTypeExtensions() {
    //         private static final ResourceLocation WATER_STILL = ResourceLocation.withDefaultNamespace("block/water_still");
    //         private static final ResourceLocation WATER_FLOW = ResourceLocation.withDefaultNamespace("block/water_flow");
    //         private static final ResourceLocation WATER_OVERLAY = ResourceLocation.withDefaultNamespace("block/water_overlay");

    //         @Override
    //         public ResourceLocation getStillTexture() {
    //             return WATER_STILL;
    //         }

    //         @Override
    //         public ResourceLocation getFlowingTexture() {
    //             return WATER_FLOW;
    //         }

    //         @Override
    //         public ResourceLocation getOverlayTexture() {
    //             return WATER_OVERLAY;
    //         }

    //         @Override
    //         public int getTintColor() {
    //             return 0xFF3F76E4;
    //         }

    //         @Override
    //         public int getTintColor(FluidState state, BlockAndTintGetter getter, BlockPos pos) {
    //             return BiomeColors.getAverageWaterColor(getter, pos) | 0xFF000000;
    //         }
    //     }, ModFluidTypes.OIL_FLUID_TYPE.get());
    // }
}