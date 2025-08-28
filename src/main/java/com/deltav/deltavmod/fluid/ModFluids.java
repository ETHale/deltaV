package com.deltav.deltavmod.fluid;

import com.deltav.deltavmod.DeltaV;
import com.deltav.deltavmod.fluid.custom.KeroseneFluid;
import com.deltav.deltavmod.fluid.custom.NaphthaFluid;
import com.deltav.deltavmod.fluid.custom.OilFluid;
import com.deltav.deltavmod.fluid.custom.PetrolFluid;
import com.deltav.deltavmod.fluid.custom.ThermalWaterFluid;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Fluids for the mod.
 * 
 * @author Adam Crawley
 */
public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
        DeferredRegister.create(BuiltInRegistries.FLUID, DeltaV.MODID);

    public static final DeferredHolder<Fluid, FlowingFluid> OIL_SOURCE = FLUIDS.register(
        "oil", OilFluid.Source::new);

    public static final DeferredHolder<Fluid, FlowingFluid> OIL_FLOW = FLUIDS.register(
        "oil_flow", OilFluid.Flowing::new);

    public static final DeferredHolder<Fluid, FlowingFluid> PETROL_SOURCE = FLUIDS.register(
        "petrol", PetrolFluid.Source::new);

    public static final DeferredHolder<Fluid, FlowingFluid> PETROL_FLOW = FLUIDS.register(
        "petrol_flow", PetrolFluid.Flowing::new);

    public static final DeferredHolder<Fluid, FlowingFluid> NAPHTHA_SOURCE = FLUIDS.register(
        "naphtha", NaphthaFluid.Source::new);

    public static final DeferredHolder<Fluid, FlowingFluid> NAPHTHA_FLOW = FLUIDS.register(
        "naphtha_flow", NaphthaFluid.Flowing::new);

    public static final DeferredHolder<Fluid, FlowingFluid> KEROSENE_SOURCE = FLUIDS.register(
        "kerosene", KeroseneFluid.Source::new);

    public static final DeferredHolder<Fluid, FlowingFluid> KEROSENE_FLOW = FLUIDS.register(
        "kerosene_flow", KeroseneFluid.Flowing::new);
    
    public static final DeferredHolder<Fluid, FlowingFluid> THERMAL_WATER_SOURCE = FLUIDS.register(
        "thermal_water", ThermalWaterFluid.Source::new);

    public static final DeferredHolder<Fluid, FlowingFluid> THERMAL_WATER_FLOW = FLUIDS.register(
        "thermal_water_flow", ThermalWaterFluid.Flowing::new);

    /**
     * Register the fluids.
     * 
     * @param eventBus
     */
    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
