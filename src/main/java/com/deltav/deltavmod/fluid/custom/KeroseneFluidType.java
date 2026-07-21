package com.deltav.deltavmod.fluid.custom;

import org.jetbrains.annotations.Nullable;
import org.joml.Vector4f;

import com.deltav.deltavmod.DeltaV;
import com.deltav.deltavmod.fluid.ModFluidTypes;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.environment.FogEnvironment;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.fluids.FluidType;

/**
 * Kerosene fluid type. Contains all kerosene specific fluid type properties.
 * Subcribes to event bus for client extension fluid type registration.
 * 
 * @author Adam Crawley
 */
@EventBusSubscriber(modid = DeltaV.MODID, value = Dist.CLIENT)
public class KeroseneFluidType extends FluidType {
    public static final Vector4f FOG_COLOR = new Vector4f(0f / 255f, 226f / 255f, 255f / 255f, 1f);

    public KeroseneFluidType() {
        super(Properties.create()
            .lightLevel(2)
            .density(800)
            .viscosity(1200)
            .canExtinguish(false)
            .temperature(300));
    }

    /**
     * Register the kerosene fluid type with {@code IClientFluidTypeExtensions}.
     *
     * @param event event from subscriber
     */
    @SubscribeEvent
    static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public void modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance,
                    float darkenWorldAmount, Vector4f fluidFogColor) {
                fluidFogColor.set(FOG_COLOR);
            }

            @Override
            public void modifyFogRender(Camera camera, @Nullable FogEnvironment environment,
                    float renderDistance, float partialTick, FogData fogData) {
                fogData.renderDistanceStart = 0f;
                fogData.renderDistanceEnd = 1f;
            }
        }, ModFluidTypes.KEROSENE_FLUID_TYPE.get());
    }
}
