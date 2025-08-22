package com.deltav.deltavmod.fluid;

import com.deltav.deltavmod.DeltaV;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
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

    // Define fluids used in fractionator 
    public static final DeferredHolder<FluidType, FluidType> PETROL_FLUID_TYPE = FLUID_TYPES.register(
        "petrol",
        () -> new FluidType(FluidType.Properties.create()
            .density(750)
            .viscosity(1000)
            .temperature(350)
            .canPushEntity(false)));

    public static final DeferredHolder<FluidType, FluidType> NAPHTHA_FLUID_TYPE = FLUID_TYPES.register(
        "naphta",
        () -> new FluidType(FluidType.Properties.create()
            .density(780)
            .viscosity(1100)
            .temperature(320)
            .canPushEntity(false)));

    public static final DeferredHolder<FluidType, FluidType> KEROSENE_FLUID_TYPE = FLUID_TYPES.register(
        "kerosene",
        () -> new FluidType(FluidType.Properties.create()
            .density(800)
            .viscosity(1200)
            .temperature(300)
            .canPushEntity(false)));

    /**
     * Register the fluid types.
     * 
     * @param eventBus
     */
    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }

}