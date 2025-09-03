package com.deltav.deltavmod.block.energy.cable.modelstate;

import java.util.List;

import com.deltav.deltavmod.DeltaV;
import com.mojang.serialization.MapCodec;

import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ResolvableModel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.DynamicBlockStateModel;
import net.neoforged.neoforge.client.model.block.CustomUnbakedBlockStateModel;
import net.neoforged.neoforge.model.data.ModelData;

public record CableBlockStateModel(CableModelPart model) implements DynamicBlockStateModel {
    @Override
    public TextureAtlasSprite particleIcon() {
        return this.model.particleIcon();
    }

    @Override
    public Object createGeometryKey(BlockAndTintGetter level, BlockPos pos, BlockState state, RandomSource random) {
        return this;
    }

    // Method responsible for collecting the parts to be rendered. Parameters in this method are:
    // - The getter for the blocks and tints, usually the level.
    // - The position of the block to render.
    // - The state of the block.
    // - A random instance.
    // - This list of model parts to be rendered. Add your model parts here.
    @Override
    public void collectParts(BlockAndTintGetter level, BlockPos pos, BlockState state, RandomSource random, List<BlockModelPart> parts) {
        // If you want the block rendered to be dependent on the block entity (e.g., your block entity implements `BlockEntity#getModelData`)
        // You can call `BlockAndTintGetter#getModelData` with the block position
        // You can read the property using `get` with the `ModelProperty` key
        // Remember that your block entity should call `BlockEntity#requestModelDataUpdate` to sync the model data to the client
        ModelData data = level.getModelData(pos);

        // Add the model to be rendered
        parts.add(this.model);
    }

    // The unbaked model that is read from the block state json
    public record Unbaked(CableModelPart.Unbaked model) implements CustomUnbakedBlockStateModel {

        // The codec to register
        public static final MapCodec<CableBlockStateModel.Unbaked> CODEC = CableModelPart.Unbaked.CODEC.xmap(
            CableBlockStateModel.Unbaked::new, CableBlockStateModel.Unbaked::model
        );
        public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(DeltaV.MODID, "cable_model_loader");

        @Override
        public void resolveDependencies(ResolvableModel.Resolver resolver) {
            // Mark any models used by the state model
            this.model.resolveDependencies(resolver);
        }

        @Override
        public BlockStateModel bake(ModelBaker baker) {
            // Bake the model parts and pass into the block state model
            return new CableBlockStateModel(this.model.bake(baker));
        }

        @Override
        public MapCodec<? extends CustomUnbakedBlockStateModel> codec() {
            return CODEC;
        }
    }
}
