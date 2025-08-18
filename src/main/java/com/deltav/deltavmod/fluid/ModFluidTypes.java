package com.deltav.deltavmod.fluid;

import com.deltav.deltavmod.DeltaV;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModFluidTypes {
    public static final ResourceLocation OIL_STILL_RL = ResourceLocation.fromNamespaceAndPath(DeltaV.MODID, "textures/block/oil");
    public static final ResourceLocation OIL_FLOWING_RL = ResourceLocation.fromNamespaceAndPath(DeltaV.MODID, "textures/block/oil_flow");
    public static final ResourceLocation OIL_OVERLAY_RL = ResourceLocation.fromNamespaceAndPath(DeltaV.MODID, "textures/block/oil_overlay");

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, DeltaV.MODID);

    public static final DeferredHolder<FluidType, FluidType> OIL_FLUID_TYPE = FLUID_TYPES.register("oil",
        () -> new FluidType(FluidType.Properties.create()
            .lightLevel(2)
            .density(15)
            .viscosity(5)
            .canExtinguish(false)
            .temperature(0)));

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }

}