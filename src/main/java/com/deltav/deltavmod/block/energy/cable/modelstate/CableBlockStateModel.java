package com.deltav.deltavmod.block.energy.cable.modelstate;

import java.util.List;
import java.util.Objects;

import com.deltav.deltavmod.DeltaV;
import com.deltav.deltavmod.block.energy.cable.CableBlockEntity;
import com.deltav.deltavmod.block.energy.cable.ConnectorType;
import com.deltav.deltavmod.block.energy.cable.modelstate.CableModelPart.CableModelPartTemplate;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ResolvableModel;
import net.minecraft.client.resources.model.geometry.BakedQuad.MaterialFlags;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.DynamicBlockStateModel;
import net.neoforged.neoforge.client.model.block.CustomUnbakedBlockStateModel;
import net.neoforged.neoforge.model.data.ModelData;

public final class CableBlockStateModel implements DynamicBlockStateModel {
    private final Identifier texture;
    private CableModelPartTemplate template = null;

    public CableBlockStateModel(CableModelPartTemplate template, Identifier texture) {
        this.template = template;
        this.texture = texture;
    }

    public Identifier texture() {
        return texture;
    }

    @Override
    @MaterialFlags
    public int materialFlags() {
        return 0;
    }

    @Override
    public Material.Baked particleMaterial() {
        return template.spriteCable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CableBlockStateModel)) return false;
        CableBlockStateModel that = (CableBlockStateModel) o;
        return Objects.equals(texture, that.texture) && Objects.equals(texture, that.texture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(texture);
    }

    @Override
    public String toString() {
        return "CableBlockStateModel[texture=" + texture + "]";
    }

    // Method responsible for collecting the parts to be rendered. Parameters in this method are:
    // - The getter for the blocks and tints, usually the level.
    // - The position of the block to render.
    // - The state of the block.
    // - A random instance.
    // - This list of model parts to be rendered. Add your model parts here.
    @Override
    public void collectParts(BlockAndTintGetter level, BlockPos pos, BlockState state, RandomSource random, List<BlockStateModelPart> parts) {
        // If you want the block rendered to be dependent on the block entity (e.g., your block entity implements `BlockEntity#getModelData`)
        // You can call `BlockAndLightGetter#getModelData` with the block position
        // You can read the property using `get` with the `ModelProperty` key
        // Remember that your block entity should call `BlockEntity#requestModelDataUpdate` to sync the model data to the client

        ModelData data = level.getModelData(pos);
        if (data == null) {
            parts.add(template.FULL_BLOCK);
            return;
        }
        ConnectorType north = data.get(CableBlockEntity.MODEL_NORTH);
        ConnectorType south = data.get(CableBlockEntity.MODEL_SOUTH);
        ConnectorType down = data.get(CableBlockEntity.MODEL_DOWN);
        ConnectorType up = data.get(CableBlockEntity.MODEL_UP);
        ConnectorType east = data.get(CableBlockEntity.MODEL_EAST);
        ConnectorType west = data.get(CableBlockEntity.MODEL_WEST);

        if (north != null)
            switch (north) {
                case CABLE -> parts.add(template.NORTH_CABLE);
                case BLOCK -> parts.add(template.NORTH_BLOCK);
                default -> parts.add(template.NORTH_NOTHING);
            }
        else {
            parts.add(template.NORTH_NOTHING);
        }

        if (south != null)
            switch (south) {
                case CABLE -> parts.add(template.SOUTH_CABLE);
                case BLOCK -> parts.add(template.SOUTH_BLOCK);
                default -> parts.add(template.SOUTH_NOTHING);
            }
        else {
            parts.add(template.SOUTH_NOTHING);
        }

        if (up != null) 
            switch (up) {
                case CABLE -> parts.add(template.UP_CABLE);
                case BLOCK -> parts.add(template.UP_BLOCK);
                default -> parts.add(template.UP_NOTHING);
            }
        else {
            parts.add(template.UP_NOTHING);
        }

        if (down != null)
            switch (down) {
                case CABLE -> parts.add(template.DOWN_CABLE);
                case BLOCK -> parts.add(template.DOWN_BLOCK);
                default -> parts.add(template.DOWN_NOTHING);
            }

        else {
            parts.add(template.DOWN_NOTHING);
        }
        if (east != null)
            switch (east) {
                case CABLE -> parts.add(template.EAST_CABLE);
                case BLOCK -> parts.add(template.EAST_BLOCK);
                default -> parts.add(template.EAST_NOTHING);
            }

        else {
            parts.add(template.EAST_NOTHING);
        }
        if (west != null)
            switch (west) {
                case CABLE -> parts.add(template.WEST_CABLE);
                case BLOCK -> parts.add(template.WEST_BLOCK);
                default -> parts.add(template.WEST_NOTHING);
            } 
        else {
            parts.add(template.WEST_NOTHING);
        }   
    }

    // The unbaked model that is read from the block state json
    public record Unbaked(Identifier texture) implements CustomUnbakedBlockStateModel {

        // The codec to register
        public static final MapCodec<CableBlockStateModel.Unbaked> CODEC =
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                Identifier.CODEC.fieldOf("texture").forGetter(Unbaked::texture)
            ).apply(instance, Unbaked::new));
        public static final Identifier ID = Identifier.fromNamespaceAndPath(DeltaV.MODID, "cable_model_loader");

        @Override
        public void resolveDependencies(ResolvableModel.Resolver resolver) {
        }

        @Override
        public BlockStateModel bake(ModelBaker baker) {
            // Bake the model parts and pass into the block state model
            return new CableBlockStateModel(new CableModelPartTemplate(baker, texture), texture);
        }

        @Override
        public MapCodec<? extends CustomUnbakedBlockStateModel> codec() {
            return CODEC;
        }
    }
}
