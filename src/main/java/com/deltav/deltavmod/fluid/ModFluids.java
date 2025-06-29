package com.deltav.deltavmod.fluid;

import com.deltav.deltavmod.DeltaV;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
        DeferredRegister.create(BuiltInRegistries.FLUID, DeltaV.MODID);

    public static final DeferredHolder<Fluid, FlowingFluid> OIL_SOURCE =
        FLUIDS.register("oil", () -> new OilFluid.Source());

    public static final DeferredHolder<Fluid, FlowingFluid> OIL_FLOWING =
        FLUIDS.register("oil_flow", () -> new OilFluid.Flowing());

    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
