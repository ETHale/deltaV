package com.deltav.deltavmod.fluid;

import com.deltav.deltavmod.DeltaV;
import com.deltav.deltavmod.fluid.custom.KeroseneFluidType;
import com.deltav.deltavmod.fluid.custom.NaphthaFluidType;
import com.deltav.deltavmod.fluid.custom.OilFluidType;
import com.deltav.deltavmod.fluid.custom.PetrolFluidType;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

/**
 * Fluid types for the mod.
 * 
 * @author Adam Crawley
 */
public class ModFluidTypes {
    public static final DeferredRegister<FluidType> FLUID_TYPES =
        DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, DeltaV.MODID);

    public static final DeferredHolder<FluidType, FluidType> OIL_FLUID_TYPE = FLUID_TYPES.register(
        "oil", OilFluidType::new);

    public static final DeferredHolder<FluidType, FluidType> PETROL_FLUID_TYPE = FLUID_TYPES.register(
        "petrol", PetrolFluidType::new);

    public static final DeferredHolder<FluidType, FluidType> NAPHTHA_FLUID_TYPE = FLUID_TYPES.register(
        "naphtha", NaphthaFluidType::new);

    public static final DeferredHolder<FluidType, FluidType> KEROSENE_FLUID_TYPE = FLUID_TYPES.register(
        "kerosene", KeroseneFluidType::new);

    /**
     * Register the fluid types.
     * 
     * @param eventBus
     */
    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }

}