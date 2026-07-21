package com.deltav.deltavmod.fluid.models;

import com.deltav.deltavmod.DeltaV;

import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.client.fluid.FluidTintSource;


public class ModFluidModels {
    public static final FluidModel.Unbaked KEROSENE_MODEL = new FluidModel.Unbaked(
        new Material(Identifier.fromNamespaceAndPath(DeltaV.MODID, "block/oil")), 
        new Material(Identifier.fromNamespaceAndPath(DeltaV.MODID, "block/oil_flow")), 
        new Material(Identifier.fromNamespaceAndPath(DeltaV.MODID, "block/oil_overlay")),
        (FluidTintSource)null
    );

    public static final FluidModel.Unbaked NAPTHA_MODEL = new FluidModel.Unbaked(
        new Material(Identifier.fromNamespaceAndPath(DeltaV.MODID, "block/oil")), 
        new Material(Identifier.fromNamespaceAndPath(DeltaV.MODID, "block/oil_flow")), 
        new Material(Identifier.fromNamespaceAndPath(DeltaV.MODID, "block/oil_overlay")),
        (FluidTintSource)null
    );

    public static final FluidModel.Unbaked PETROL_MODEL = new FluidModel.Unbaked(
        new Material(Identifier.fromNamespaceAndPath(DeltaV.MODID, "block/oil")), 
        new Material(Identifier.fromNamespaceAndPath(DeltaV.MODID, "block/oil_flow")), 
        new Material(Identifier.fromNamespaceAndPath(DeltaV.MODID, "block/oil_overlay")),
        (FluidTintSource)null
    );

    public static final FluidModel.Unbaked OIL_MODEL = new FluidModel.Unbaked(
        new Material(Identifier.fromNamespaceAndPath(DeltaV.MODID, "block/oil")), 
        new Material(Identifier.fromNamespaceAndPath(DeltaV.MODID, "block/oil_flow")), 
        new Material(Identifier.fromNamespaceAndPath(DeltaV.MODID, "block/oil_overlay")),
        (FluidTintSource)null
    );

    public static final FluidModel.Unbaked LATEX_MODEL = new FluidModel.Unbaked(
        new Material(Identifier.fromNamespaceAndPath(DeltaV.MODID, "block/latex")), 
        new Material(Identifier.fromNamespaceAndPath(DeltaV.MODID, "block/latex_flow")), 
        new Material(Identifier.fromNamespaceAndPath(DeltaV.MODID, "block/latex_overlay")),
        (FluidTintSource)null
    );

    public static final FluidModel.Unbaked THERMAL_WATER_MODEL = new FluidModel.Unbaked(
        new Material(Identifier.withDefaultNamespace("block/water_still")),
        new Material(Identifier.withDefaultNamespace("block/water_flow")),
        new Material(Identifier.withDefaultNamespace("block/water_overlay")), 
        new FluidTintSource() {
            @Override
            public int color(BlockState state) {
                return -1;
            }

            @Override
            public int color(FluidState state) {
                return 0xFF4FB9EA;
            }

            @Override
            public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
                return 0xFF4FB9EA;
            }

            @Override
            public int colorInWorld(FluidState fluidState, BlockState blockState, BlockAndTintGetter level, BlockPos pos) {
                return 0xFF4FB9EA;
            }
        }
    );
}
