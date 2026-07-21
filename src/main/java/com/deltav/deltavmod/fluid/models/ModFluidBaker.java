package com.deltav.deltavmod.fluid.models;

import com.deltav.deltavmod.DeltaV;
import com.deltav.deltavmod.fluid.ModFluids;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterFluidModelsEvent;

@EventBusSubscriber(modid = DeltaV.MODID, value = Dist.CLIENT)
public class ModFluidBaker {
    @SubscribeEvent
    public static void registerFluidModels(RegisterFluidModelsEvent event) {
        event.register(ModFluidModels.KEROSENE_MODEL, ModFluids.KEROSENE_SOURCE.get(), ModFluids.KEROSENE_FLOW.get());
        event.register(ModFluidModels.NAPTHA_MODEL, ModFluids.NAPHTHA_SOURCE.get(), ModFluids.NAPHTHA_FLOW.get());
        event.register(ModFluidModels.PETROL_MODEL, ModFluids.PETROL_SOURCE.get(), ModFluids.PETROL_FLOW.get());
        event.register(ModFluidModels.OIL_MODEL, ModFluids.OIL_SOURCE.get(), ModFluids.OIL_FLOW.get());
        event.register(ModFluidModels.LATEX_MODEL, ModFluids.LATEX_SOURCE.get(), ModFluids.LATEX_FLOW.get());
        event.register(ModFluidModels.THERMAL_WATER_MODEL, ModFluids.THERMAL_WATER_SOURCE.get(), ModFluids.THERMAL_WATER_FLOW.get());
    }
}
