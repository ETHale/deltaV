package com.deltav.deltavmod.worldgen.features;

import com.deltav.deltavmod.DeltaV;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
public class DeltaVFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES =
        DeferredRegister.create(Registries.FEATURE, DeltaV.MODID);

    public static final DeferredHolder<Feature<?>,KimberliteCarrotFeature> KIMBERLITE_CARROT_FEATURE =
        FEATURES.register("kimberlite_carrot", () -> new KimberliteCarrotFeature(NoneFeatureConfiguration.CODEC));

    public static final DeferredHolder<Feature<?>, HotSpringFeature> HOT_SPRING = 
        FEATURES.register("hot_spring", () -> new HotSpringFeature(HotSpringFeatureConfiguration.CODEC));

    public static void register(IEventBus eventBus) {
        FEATURES.register(eventBus);
    }
}
