package com.deltav.deltavmod.fluid.custom;

import org.jetbrains.annotations.Nullable;
import org.joml.Vector4f;

import com.deltav.deltavmod.DeltaV;
import com.deltav.deltavmod.fluid.ModFluidTypes;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.environment.FogEnvironment;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;

@EventBusSubscriber(modid = DeltaV.MODID, value = Dist.CLIENT)
public class LatexFluidType extends FluidType {
    private static final ResourceLocation STILL_TEXTURE = ResourceLocation.fromNamespaceAndPath(DeltaV.MODID, "block/latex");
    private static final ResourceLocation FLOWING_TEXTURE = ResourceLocation.fromNamespaceAndPath(DeltaV.MODID, "block/latex_flow");
    private static final ResourceLocation OVERLAY_TEXTURE = ResourceLocation.fromNamespaceAndPath(DeltaV.MODID, "block/latex_overlay");
    private static final int TINT_COLOR = 0xFFFCFFE8;
    private static final Vector4f FOG_COLOR = new Vector4f(252f / 255f, 255f / 255f, 232f / 255f, 0.8f);

    public LatexFluidType() {
        super(Properties.create()
            .lightLevel(2)
            .canExtinguish(false)
            .density(1100)
            .viscosity(120)
            .temperature(600)
            .canConvertToSource(false)
            .supportsBoating(true)
            .canSwim(true)
            .canDrown(true)
            .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
            .sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH));
    }

    /**
     * Register the fluid type with {@code IClientFluidTypeExtensions}.
     *
     * @param event event from subscriber
     */
    @SubscribeEvent
    static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public ResourceLocation getStillTexture() {
                return STILL_TEXTURE;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return FLOWING_TEXTURE;
            }

            @Override
            public ResourceLocation getOverlayTexture() {
                return OVERLAY_TEXTURE;
            }

            @Override
            public int getTintColor() {
                return TINT_COLOR;
            }

            @Override
            public int getTintColor(FluidState state, BlockAndTintGetter getter, BlockPos pos) {
                return getTintColor();
            }

            @Override
            public Vector4f modifyFogColor(Camera camera, float partialTick, ClientLevel level,
                    int renderDistance, float darkenWorldAmount, Vector4f fluidFogColor) {
                return FOG_COLOR;
            }

            @Override
            public void modifyFogRender(Camera camera, @Nullable FogEnvironment environment,
                    float renderDistance, float partialTick, FogData fogData) {
                fogData.renderDistanceStart = 0f;
                fogData.renderDistanceEnd = 1f;
            }
        }, ModFluidTypes.LATEX_FLUID_TYPE.get());
    }
}
