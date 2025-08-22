package com.deltav.deltavmod.fluid;

import com.deltav.deltavmod.DeltaV;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
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

    public static final DeferredHolder<Fluid, FlowingFluid> OIL_FLOWING = FLUIDS.register(
        "oil_flow", OilFluid.Flowing::new);

    // Petrol fluid properties and registration
    private static final DeferredHolder<Fluid, FlowingFluid> DEF_PETROL_SOURCE = DeferredHolder.create(Registries.FLUID, ResourceLocation.withDefaultNamespace("petrol"));
    private static final DeferredHolder<Fluid, FlowingFluid> DEF_PETROL_FLOW = DeferredHolder.create(Registries.FLUID, ResourceLocation.withDefaultNamespace("petrol_flow"));

    private static final BaseFlowingFluid.Properties petrolProperties = new BaseFlowingFluid.Properties(
        () -> ModFluidTypes.PETROL_FLUID_TYPE.value(),
        () -> DEF_PETROL_SOURCE.value(),
        () -> DEF_PETROL_FLOW.value()
    );

    public static final DeferredHolder<Fluid, FlowingFluid> PETROL_SOURCE = FLUIDS.register(
        "petrol", () -> new BaseFlowingFluid.Source(petrolProperties));
    public static final DeferredHolder<Fluid, FlowingFluid> PETROL_FLOW = FLUIDS.register(
        "petrol_flow", () -> new BaseFlowingFluid.Flowing(petrolProperties));

    // Naptha fluid properties and registration
    private static final DeferredHolder<Fluid, FlowingFluid> DEF_NAPHTHA_SOURCE = DeferredHolder.create(Registries.FLUID, ResourceLocation.withDefaultNamespace("naphta"));
    private static final DeferredHolder<Fluid, FlowingFluid> DEF_NAPHTHA_FLOW = DeferredHolder.create(Registries.FLUID, ResourceLocation.withDefaultNamespace("naphta_flow"));

    private static final BaseFlowingFluid.Properties naphtaProperties = new BaseFlowingFluid.Properties(
        () -> ModFluidTypes.NAPHTHA_FLUID_TYPE.value(),
        () -> DEF_NAPHTHA_SOURCE.value(),
        () -> DEF_NAPHTHA_FLOW.value()
    );

    public static final DeferredHolder<Fluid, FlowingFluid> NAPHTHA_SOURCE = FLUIDS.register(
        "naphta", () -> new BaseFlowingFluid.Source(naphtaProperties));
    public static final DeferredHolder<Fluid, FlowingFluid> NAPHTHA_FLOW = FLUIDS.register(
        "naphta_flow", () -> new BaseFlowingFluid.Flowing(naphtaProperties));

    // Kerosene fluid properties and registration
    private static final DeferredHolder<Fluid, FlowingFluid> DEF_KEROSENE_SOURCE = DeferredHolder.create(Registries.FLUID, ResourceLocation.withDefaultNamespace("kerosene"));
    private static final DeferredHolder<Fluid, FlowingFluid> DEF_KEROSENE_FLOW = DeferredHolder.create(Registries.FLUID, ResourceLocation.withDefaultNamespace("kerosene_flow"));

    private static final BaseFlowingFluid.Properties properties = new BaseFlowingFluid.Properties(
        () -> ModFluidTypes.KEROSENE_FLUID_TYPE.value(),
        () -> DEF_KEROSENE_SOURCE.value(),
        () -> DEF_KEROSENE_FLOW.value()
    );

    public static final DeferredHolder<Fluid, FlowingFluid> KEROSENE_SOURCE = FLUIDS.register(
        "kerosene", () -> new BaseFlowingFluid.Source(properties));
    public static final DeferredHolder<Fluid, FlowingFluid> KEROSENE_FLOW = FLUIDS.register(
        "kerosene_flow", () -> new BaseFlowingFluid.Flowing(properties));

    /**
     * Register the fluids.
     * 
     * @param eventBus
     */
    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
