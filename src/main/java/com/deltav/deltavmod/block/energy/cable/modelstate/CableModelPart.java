package com.deltav.deltavmod.block.energy.cable.modelstate;

import java.util.List;

import javax.annotation.Nullable;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.client.renderer.block.model.TextureSlots;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.QuadCollection;
import net.minecraft.client.resources.model.ResolvableModel;
import net.minecraft.client.resources.model.ResolvedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

/*
 * Based on https://docs.neoforged.net/docs/resources/client/models/modelloaders#creating-custom-block-state-model-loaders
 */
public record CableModelPart(QuadCollection quads, boolean useAmbientOcclusion, TextureAtlasSprite particleIcon) implements BlockModelPart {

    @Override
    public List<BakedQuad> getQuads(@Nullable Direction direction) {
        return this.quads.getQuads(direction);
    }

    // The unbaked model that is read from the block state json
    public record Unbaked(ResourceLocation modelLocation, CableModelState modelState) implements BlockModelPart.Unbaked {

        // Used for the unbaked block state model
        public static final MapCodec<CableModelPart.Unbaked> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                ResourceLocation.CODEC.fieldOf("model").forGetter(CableModelPart.Unbaked::modelLocation),
                CableModelState.CODEC.fieldOf("state").forGetter(CableModelPart.Unbaked::modelState)
            ).apply(instance, CableModelPart.Unbaked::new)
        );

        @Override
        public void resolveDependencies(ResolvableModel.Resolver resolver) {
            // Mark any models used by the model part
            resolver.markDependency(this.modelLocation);
        }

        @Override
        public CableModelPart bake(ModelBaker baker) {
            // Get the model to bake
            ResolvedModel resolvedModel = baker.getModel(this.modelLocation);

            // Get the necessary settings for the model part
            TextureSlots slots = resolvedModel.getTopTextureSlots();
            boolean ao = resolvedModel.getTopAmbientOcclusion();
            TextureAtlasSprite particle = resolvedModel.resolveParticleSprite(slots, baker);
            QuadCollection quads = resolvedModel.bakeTopGeometry(slots, baker, this.modelState);
            
            // Return the baked part
            return new CableModelPart(quads, ao, particle);
        }
    }
}
