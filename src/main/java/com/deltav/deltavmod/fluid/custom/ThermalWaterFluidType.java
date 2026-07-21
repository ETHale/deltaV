package com.deltav.deltavmod.fluid.custom;

import javax.annotation.Nullable;

import org.joml.Vector4f;

import com.deltav.deltavmod.DeltaV;
import com.deltav.deltavmod.fluid.ModFluidTypes;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.environment.FogEnvironment;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;

@EventBusSubscriber(modid = DeltaV.MODID, value = Dist.CLIENT)
public class ThermalWaterFluidType extends FluidType {
    private static final Vector4f FOG_COLOR = new Vector4f(79f / 255f, 185f / 255f, 234f / 255f, 0.8f);

    public ThermalWaterFluidType() {
        super(Properties.create()
            .fallDistanceModifier(0F)
            .canExtinguish(true)
            .canConvertToSource(false)
            .supportsBoating(true)
            .canSwim(true)
            .canDrown(true)
            .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
            .sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH)
            .canHydrate(true)
            .density(1000)
            .viscosity(100)
            .temperature(500));
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
