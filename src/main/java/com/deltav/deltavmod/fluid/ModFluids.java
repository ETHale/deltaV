package com.deltav.deltavmod.fluid;

import java.util.function.Supplier;

import com.deltav.deltavmod.DeltaV;
import com.deltav.deltavmod.block.ModBlocks;
import com.deltav.deltavmod.item.ModItems;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
        DeferredRegister.create(Registries.FLUID, DeltaV.MODID);

    public static final Supplier<FlowingFluid> OIL_SOURCE =
        FLUIDS.register("oil",
            registryName -> new BaseFlowingFluid.Source(ModFluids.OIL_PROPERTIES));

    public static final Supplier<FlowingFluid> OIL_FLOWING =
        FLUIDS.register("oil_flowing",
            registryName -> new BaseFlowingFluid.Flowing(ModFluids.OIL_PROPERTIES));

    public static BaseFlowingFluid.Properties OIL_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.OIL_FLUID_TYPE,
            () -> OIL_SOURCE.get(),
            () -> OIL_FLOWING.get()
        )
        .slopeFindDistance(2).levelDecreasePerBlock(2)
        .bucket(() -> ModItems.OIL_BUCKET.get())
        .block(() -> ModBlocks.OIL_FLUID.get());

    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
