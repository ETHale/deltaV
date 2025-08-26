package com.deltav.deltavmod.data;

import com.deltav.deltavmod.DeltaV;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Data components for the mod. See https://docs.neoforged.net/docs/1.21.5/items/datacomponents for more details.
 * 
 * @author Adam Crawley
 */
public class ModDataComponents {
    private static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.create(
        Registries.DATA_COMPONENT_TYPE, DeltaV.MODID
    );

    /**
     * Generic fluid data component. This handles storing a data component on an ItemStack that have fluid content.
     * It creates a data component for appropriate items called "fluid".
     */
    public static DeferredHolder<DataComponentType<?>, DataComponentType<SimpleFluidContent>> GENERIC_FLUID = DATA_COMPONENTS.register(
        "fluid", () -> DataComponentType.<SimpleFluidContent>builder()
            .persistent(SimpleFluidContent.CODEC)
            .networkSynchronized(SimpleFluidContent.STREAM_CODEC)
            .build()
	);

    /**
     * Register the data components.
     * 
     * @param eventBus
     */
    public static void register(IEventBus eventBus) {
        DATA_COMPONENTS.register(eventBus);
    }
}
