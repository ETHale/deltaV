package com.deltav.deltavmod.block.energy.cable.modelstate;

import java.util.List;

import javax.annotation.Nullable;

import com.deltav.deltavmod.DeltaV;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.client.renderer.block.model.TextureSlots;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.AtlasIds;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.QuadCollection;
import net.minecraft.client.resources.model.ResolvableModel;
import net.minecraft.client.resources.model.ResolvedModel;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.model.pipeline.QuadBakingVertexConsumer;

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

    /*
     * Template for all the different parts that make up the cable model
     * These are combined based on the block state to make the final model
     * Needs the name of the block texture passed in to the constructor
     */
    public static class CableModelPartTemplate {
        public static final MapCodec<CableModelPartTemplate> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("texture").forGetter(template -> template.texture),
            Codec.DOUBLE.fieldOf("cable_thickness").orElse(.4).forGetter(template -> template.cableThickness),
            Codec.DOUBLE.fieldOf("connector_thickness").orElse(.1).forGetter(template -> template.connectorThickness),
            Codec.DOUBLE.fieldOf("connector_width").orElse(.3).forGetter(template -> template.connectorWidth)
        ).apply(instance, CableModelPartTemplate::new));

        public static final CableModelPartTemplate INSTANCE = new CableModelPartTemplate(ResourceLocation.fromNamespaceAndPath(DeltaV.MODID, "")); // TODO default texture?

        public final CableModelPart UP_CABLE;
        public final CableModelPart UP_BLOCK;
        public final CableModelPart UP_NOTHING;
        public final CableModelPart DOWN_CABLE;
        public final CableModelPart DOWN_BLOCK;
        public final CableModelPart DOWN_NOTHING;
        public final CableModelPart EAST_CABLE;
        public final CableModelPart EAST_BLOCK;
        public final CableModelPart EAST_NOTHING;
        public final CableModelPart WEST_CABLE;
        public final CableModelPart WEST_BLOCK;
        public final CableModelPart WEST_NOTHING;
        public final CableModelPart NORTH_CABLE;
        public final CableModelPart NORTH_BLOCK;
        public final CableModelPart NORTH_NOTHING;
        public final CableModelPart SOUTH_CABLE;
        public final CableModelPart SOUTH_BLOCK;
        public final CableModelPart SOUTH_NOTHING;
        public final CableModelPart FULL_BLOCK;

        public final ResourceLocation texture;
        public final double cableThickness;
        public final double connectorThickness;
        public final double connectorWidth;

        public CableModelPartTemplate(ResourceLocation texture) {
            this(texture, .4, .1, .3);
        }

        public CableModelPartTemplate(ResourceLocation texture, double cableThickness, double connectorThickness, double connectorWidth) {
            this.texture = texture;
            this.cableThickness = cableThickness;
            this.connectorThickness = connectorThickness;
            this.connectorWidth = connectorWidth;

            // Load the texture TODO - fix this
            TextureAtlasSprite spriteCable = null;
            TextureAtlasSprite spriteSide = null;
            TextureAtlasSprite spriteConnector = null;
            try {
                TextureAtlas atlas = net.minecraft.client.Minecraft.getInstance().getModelManager().getAtlas(Sheets.BLOCKS_MAPPER.sheet());
                DeltaV.LOGGER.debug("Loading cable texture: " + texture);
                spriteCable = atlas.getSprite(texture);
                spriteSide = atlas.getSprite(texture.withSuffix("_side"));
                spriteConnector = atlas.getSprite(texture.withSuffix("_connector"));
                DeltaV.LOGGER.debug("Spites loaded" + spriteCable + spriteCable + spriteConnector);
            } catch (Exception e) {
                DeltaV.LOGGER.error("Error loading cable texture: " + texture, e);
                spriteCable = null;
                spriteSide = null;
                spriteConnector = null;
            }
    
            double o = cableThickness;      // Thickness of the cable. .0 would be full block, .5 is infinitely thin.
            double p = connectorThickness;      // Thickness of the connector as it is put on the connecting block
            double q = connectorWidth;      // The wideness of the connector
    
            // UP
            QuadCollection.Builder up_cable = new QuadCollection.Builder();
            up_cable.addUnculledFace(quad(v(1 - o, 1, o), v(1 - o, 1, 1 - o), v(1 - o, 1 - o, 1 - o), v(1 - o, 1 - o, o), spriteCable));
            up_cable.addUnculledFace(quad(v(o, 1, 1 - o), v(o, 1, o), v(o, 1 - o, o), v(o, 1 - o, 1 - o), spriteCable));
            up_cable.addUnculledFace(quad(v(o, 1, o), v(1 - o, 1, o), v(1 - o, 1 - o, o), v(o, 1 - o, o), spriteCable));
            up_cable.addUnculledFace(quad(v(o, 1 - o, 1 - o), v(1 - o, 1 - o, 1 - o), v(1 - o, 1, 1 - o), v(o, 1, 1 - o), spriteCable));
            UP_CABLE = new CableModelPart(up_cable.build(), true, null);
    
            QuadCollection.Builder up_block = new QuadCollection.Builder();
            up_block.addUnculledFace(quad(v(1 - o, 1 - p, o), v(1 - o, 1 - p, 1 - o), v(1 - o, 1 - o, 1 - o), v(1 - o, 1 - o, o), spriteCable));
            up_block.addUnculledFace(quad(v(o, 1 - p, 1 - o), v(o, 1 - p, o), v(o, 1 - o, o), v(o, 1 - o, 1 - o), spriteCable));
            up_block.addUnculledFace(quad(v(o, 1 - p, o), v(1 - o, 1 - p, o), v(1 - o, 1 - o, o), v(o, 1 - o, o), spriteCable));
            up_block.addUnculledFace(quad(v(o, 1 - o, 1 - o), v(1 - o, 1 - o, 1 - o), v(1 - o, 1 - p, 1 - o), v(o, 1 - p, 1 - o), spriteCable));
    
            up_block.addUnculledFace(quad(v(1 - q, 1 - p, q), v(1 - q, 1, q), v(1 - q, 1, 1 - q), v(1 - q, 1 - p, 1 - q), spriteSide));
            up_block.addUnculledFace(quad(v(q, 1 - p, 1 - q), v(q, 1, 1 - q), v(q, 1, q), v(q, 1 - p, q), spriteSide));
            up_block.addUnculledFace(quad(v(q, 1, q), v(1 - q, 1, q), v(1 - q, 1 - p, q), v(q, 1 - p, q), spriteSide));
            up_block.addUnculledFace(quad(v(q, 1 - p, 1 - q), v(1 - q, 1 - p, 1 - q), v(1 - q, 1, 1 - q), v(q, 1, 1 - q), spriteSide));
    
            up_block.addUnculledFace(quad(v(q, 1 - p, q), v(1 - q, 1 - p, q), v(1 - q, 1 - p, 1 - q), v(q, 1 - p, 1 - q), spriteConnector));
            up_block.addUnculledFace(quad(v(q, 1, q), v(q, 1, 1 - q), v(1 - q, 1, 1 - q), v(1 - q, 1, q), spriteSide));
            UP_BLOCK = new CableModelPart(up_block.build(), true, null);
    
            QuadCollection.Builder up_nothing = new QuadCollection.Builder();
            up_nothing.addCulledFace(Direction.UP, quad(v(o, 1 - o, 1 - o), v(1 - o, 1 - o, 1 - o), v(1 - o, 1 - o, o), v(o, 1 - o, o), spriteCable));
            UP_NOTHING = new CableModelPart(up_nothing.build(), true, null);
    
    
            // DOWN
            QuadCollection.Builder down_cable = new QuadCollection.Builder();
            down_cable.addUnculledFace(quad(v(1 - o, o, o), v(1 - o, o, 1 - o), v(1 - o, 0, 1 - o), v(1 - o, 0, o), spriteCable));
            down_cable.addUnculledFace(quad(v(o, o, 1 - o), v(o, o, o), v(o, 0, o), v(o, 0, 1 - o), spriteCable));
            down_cable.addUnculledFace(quad(v(o, o, o), v(1 - o, o, o), v(1 - o, 0, o), v(o, 0, o), spriteCable));
            down_cable.addUnculledFace(quad(v(o, 0, 1 - o), v(1 - o, 0, 1 - o), v(1 - o, o, 1 - o), v(o, o, 1 - o), spriteCable));
            DOWN_CABLE = new CableModelPart(down_cable.build(), true, null);
    
            QuadCollection.Builder down_block = new QuadCollection.Builder();
            down_block.addUnculledFace(quad(v(1 - o, o, o), v(1 - o, o, 1 - o), v(1 - o, p, 1 - o), v(1 - o, p, o), spriteCable));
            down_block.addUnculledFace(quad(v(o, o, 1 - o), v(o, o, o), v(o, p, o), v(o, p, 1 - o), spriteCable));
            down_block.addUnculledFace(quad(v(o, o, o), v(1 - o, o, o), v(1 - o, p, o), v(o, p, o), spriteCable));
            down_block.addUnculledFace(quad(v(o, p, 1 - o), v(1 - o, p, 1 - o), v(1 - o, o, 1 - o), v(o, o, 1 - o), spriteCable));
    
            down_block.addUnculledFace(quad(v(1 - q, 0, q), v(1 - q, p, q), v(1 - q, p, 1 - q), v(1 - q, 0, 1 - q), spriteSide));
            down_block.addUnculledFace(quad(v(q, 0, 1 - q), v(q, p, 1 - q), v(q, p, q), v(q, 0, q), spriteSide));
            down_block.addUnculledFace(quad(v(q, p, q), v(1 - q, p, q), v(1 - q, 0, q), v(q, 0, q), spriteSide));
            down_block.addUnculledFace(quad(v(q, 0, 1 - q), v(1 - q, 0, 1 - q), v(1 - q, p, 1 - q), v(q, p, 1 - q), spriteSide));
    
            down_block.addUnculledFace(quad(v(q, p, 1 - q), v(1 - q, p, 1 - q), v(1 - q, p, q), v(q, p, q), spriteConnector));
            down_block.addUnculledFace(quad(v(q, 0, 1 - q), v(q, 0, q), v(1 - q, 0, q), v(1 - q, 0, 1 - q), spriteSide));
            DOWN_BLOCK = new CableModelPart(down_block.build(), true, null);
    
            QuadCollection.Builder down_nothing = new QuadCollection.Builder();
            down_nothing.addCulledFace(Direction.DOWN, quad(v(o, o, o), v(1 - o, o, o), v(1 - o, o, 1 - o), v(o, o, 1 - o), spriteCable));
            DOWN_NOTHING = new CableModelPart(down_nothing.build(), true, null);
    
    
            // EAST
            QuadCollection.Builder east_cable = new QuadCollection.Builder();
            east_cable.addUnculledFace(quad(v(1, 1 - o, 1 - o), v(1, 1 - o, o), v(1 - o, 1 - o, o), v(1 - o, 1 - o, 1 - o), spriteCable));
            east_cable.addUnculledFace(quad(v(1, o, o), v(1, o, 1 - o), v(1 - o, o, 1 - o), v(1 - o, o, o), spriteCable));
            east_cable.addUnculledFace(quad(v(1, 1 - o, o), v(1, o, o), v(1 - o, o, o), v(1 - o, 1 - o, o), spriteCable));
            east_cable.addUnculledFace(quad(v(1, o, 1 - o), v(1, 1 - o, 1 - o), v(1 - o, 1 - o, 1 - o), v(1 - o, o, 1 - o), spriteCable));
            EAST_CABLE = new CableModelPart(east_cable.build(), true, null);
    
            QuadCollection.Builder east_block = new QuadCollection.Builder();
            east_block.addUnculledFace(quad(v(1 - p, 1 - o, 1 - o), v(1 - p, 1 - o, o), v(1 - o, 1 - o, o), v(1 - o, 1 - o, 1 - o), spriteCable));
            east_block.addUnculledFace(quad(v(1 - p, o, o), v(1 - p, o, 1 - o), v(1 - o, o, 1 - o), v(1 - o, o, o), spriteCable));
            east_block.addUnculledFace(quad(v(1 - p, 1 - o, o), v(1 - p, o, o), v(1 - o, o, o), v(1 - o, 1 - o, o), spriteCable));
            east_block.addUnculledFace(quad(v(1 - p, o, 1 - o), v(1 - p, 1 - o, 1 - o), v(1 - o, 1 - o, 1 - o), v(1 - o, o, 1 - o), spriteCable));
    
            east_block.addUnculledFace(quad(v(1 - p, 1 - q, 1 - q), v(1, 1 - q, 1 - q), v(1, 1 - q, q), v(1 - p, 1 - q, q), spriteSide));
            east_block.addUnculledFace(quad(v(1 - p, q, q), v(1, q, q), v(1, q, 1 - q), v(1 - p, q, 1 - q), spriteSide));
            east_block.addUnculledFace(quad(v(1 - p, 1 - q, q), v(1, 1 - q, q), v(1, q, q), v(1 - p, q, q), spriteSide));
            east_block.addUnculledFace(quad(v(1 - p, q, 1 - q), v(1, q, 1 - q), v(1, 1 - q, 1 - q), v(1 - p, 1 - q, 1 - q), spriteSide));
    
            east_block.addUnculledFace(quad(v(1 - p, q, 1 - q), v(1 - p, 1 - q, 1 - q), v(1 - p, 1 - q, q), v(1 - p, q, q), spriteConnector));
            east_block.addUnculledFace(quad(v(1, q, 1 - q), v(1, q, q), v(1, 1 - q, q), v(1, 1 - q, 1 - q), spriteSide));
            EAST_BLOCK = new CableModelPart(east_block.build(), true, null);
    
            QuadCollection.Builder east_nothing = new QuadCollection.Builder();
            east_nothing.addCulledFace(Direction.EAST, quad(v(1 - o, o, o), v(1 - o, 1 - o, o), v(1 - o, 1 - o, 1 - o), v(1 - o, o, 1 - o), spriteCable));
            EAST_NOTHING = new CableModelPart(east_nothing.build(), true, null);
    
    
            // WEST
            QuadCollection.Builder west_cable = new QuadCollection.Builder();
            west_cable.addUnculledFace(quad(v(o, 1 - o, 1 - o), v(o, 1 - o, o), v(0, 1 - o, o), v(0, 1 - o, 1 - o), spriteCable));
            west_cable.addUnculledFace(quad(v(o, o, o), v(o, o, 1 - o), v(0, o, 1 - o), v(0, o, o), spriteCable));
            west_cable.addUnculledFace(quad(v(o, 1 - o, o), v(o, o, o), v(0, o, o), v(0, 1 - o, o), spriteCable));
            west_cable.addUnculledFace(quad(v(o, o, 1 - o), v(o, 1 - o, 1 - o), v(0, 1 - o, 1 - o), v(0, o, 1 - o), spriteCable));
            WEST_CABLE = new CableModelPart(west_cable.build(), true, null);
    
            QuadCollection.Builder west_block = new QuadCollection.Builder();
            west_block.addUnculledFace(quad(v(o, 1 - o, 1 - o), v(o, 1 - o, o), v(p, 1 - o, o), v(p, 1 - o, 1 - o), spriteCable));
            west_block.addUnculledFace(quad(v(o, o, o), v(o, o, 1 - o), v(p, o, 1 - o), v(p, o, o), spriteCable));
            west_block.addUnculledFace(quad(v(o, 1 - o, o), v(o, o, o), v(p, o, o), v(p, 1 - o, o), spriteCable));
            west_block.addUnculledFace(quad(v(o, o, 1 - o), v(o, 1 - o, 1 - o), v(p, 1 - o, 1 - o), v(p, o, 1 - o), spriteCable));
    
            west_block.addUnculledFace(quad(v(0, 1 - q, 1 - q), v(p, 1 - q, 1 - q), v(p, 1 - q, q), v(0, 1 - q, q), spriteSide));
            west_block.addUnculledFace(quad(v(0, q, q), v(p, q, q), v(p, q, 1 - q), v(0, q, 1 - q), spriteSide));
            west_block.addUnculledFace(quad(v(0, 1 - q, q), v(p, 1 - q, q), v(p, q, q), v(0, q, q), spriteSide));
            west_block.addUnculledFace(quad(v(0, q, 1 - q), v(p, q, 1 - q), v(p, 1 - q, 1 - q), v(0, 1 - q, 1 - q), spriteSide));
    
            west_block.addUnculledFace(quad(v(p, q, q), v(p, 1 - q, q), v(p, 1 - q, 1 - q), v(p, q, 1 - q), spriteConnector));
            west_block.addUnculledFace(quad(v(0, q, q), v(0, q, 1 - q), v(0, 1 - q, 1 - q), v(0, 1 - q, q), spriteSide));
            WEST_BLOCK = new CableModelPart(west_block.build(), true, null);
    
            QuadCollection.Builder west_nothing = new QuadCollection.Builder();
            west_nothing.addCulledFace(Direction.WEST, quad(v(o, o, 1 - o), v(o, 1 - o, 1 - o), v(o, 1 - o, o), v(o, o, o), spriteCable));
            WEST_NOTHING = new CableModelPart(west_nothing.build(), true, null);
    
    
            //NORTH
            QuadCollection.Builder north_cable = new QuadCollection.Builder();
            north_cable.addUnculledFace(quad(v(o, 1 - o, o), v(1 - o, 1 - o, o), v(1 - o, 1 - o, 0), v(o, 1 - o, 0), spriteCable));
            north_cable.addUnculledFace(quad(v(o, o, 0), v(1 - o, o, 0), v(1 - o, o, o), v(o, o, o), spriteCable));
            north_cable.addUnculledFace(quad(v(1 - o, o, 0), v(1 - o, 1 - o, 0), v(1 - o, 1 - o, o), v(1 - o, o, o), spriteCable));
            north_cable.addUnculledFace(quad(v(o, o, o), v(o, 1 - o, o), v(o, 1 - o, 0), v(o, o, 0), spriteCable));
            NORTH_CABLE = new CableModelPart(north_cable.build(), true, null);
    
            QuadCollection.Builder north_block = new QuadCollection.Builder();
            north_block.addUnculledFace(quad(v(o, 1 - o, o), v(1 - o, 1 - o, o), v(1 - o, 1 - o, p), v(o, 1 - o, p), spriteCable));
            north_block.addUnculledFace(quad(v(o, o, p), v(1 - o, o, p), v(1 - o, o, o), v(o, o, o), spriteCable));
            north_block.addUnculledFace(quad(v(1 - o, o, p), v(1 - o, 1 - o, p), v(1 - o, 1 - o, o), v(1 - o, o, o), spriteCable));
            north_block.addUnculledFace(quad(v(o, o, o), v(o, 1 - o, o), v(o, 1 - o, p), v(o, o, p), spriteCable));
    
            north_block.addUnculledFace(quad(v(q, 1 - q, p), v(1 - q, 1 - q, p), v(1 - q, 1 - q, 0), v(q, 1 - q, 0), spriteSide));
            north_block.addUnculledFace(quad(v(q, q, 0), v(1 - q, q, 0), v(1 - q, q, p), v(q, q, p), spriteSide));
            north_block.addUnculledFace(quad(v(1 - q, q, 0), v(1 - q, 1 - q, 0), v(1 - q, 1 - q, p), v(1 - q, q, p), spriteSide));
            north_block.addUnculledFace(quad(v(q, q, p), v(q, 1 - q, p), v(q, 1 - q, 0), v(q, q, 0), spriteSide));
    
            north_block.addUnculledFace(quad(v(q, q, p), v(1 - q, q, p), v(1 - q, 1 - q, p), v(q, 1 - q, p), spriteConnector));
            north_block.addUnculledFace(quad(v(q, q, 0), v(q, 1 - q, 0), v(1 - q, 1 - q, 0), v(1 - q, q, 0), spriteSide));
            NORTH_BLOCK = new CableModelPart(north_block.build(), true, null);
    
            QuadCollection.Builder north_nothing = new QuadCollection.Builder();
            north_nothing.addCulledFace(Direction.NORTH, quad(v(o, 1 - o, o), v(1 - o, 1 - o, o), v(1 - o, o, o), v(o, o, o), spriteCable));
            NORTH_NOTHING = new CableModelPart(north_nothing.build(), true, null);
    
    
            //SOUTH
            QuadCollection.Builder south_cable = new QuadCollection.Builder();
            south_cable.addUnculledFace(quad(v(o, 1 - o, 1), v(1 - o, 1 - o, 1), v(1 - o, 1 - o, 1 - o), v(o, 1 - o, 1 - o), spriteCable));
            south_cable.addUnculledFace(quad(v(o, o, 1 - o), v(1 - o, o, 1 - o), v(1 - o, o, 1), v(o, o, 1), spriteCable));
            south_cable.addUnculledFace(quad(v(1 - o, o, 1 - o), v(1 - o, 1 - o, 1 - o), v(1 - o, 1 - o, 1), v(1 - o, o, 1), spriteCable));
            south_cable.addUnculledFace(quad(v(o, o, 1), v(o, 1 - o, 1), v(o, 1 - o, 1 - o), v(o, o, 1 - o), spriteCable));
            SOUTH_CABLE = new CableModelPart(south_cable.build(), true, null);
    
            QuadCollection.Builder south_block = new QuadCollection.Builder();
            south_block.addUnculledFace(quad(v(o, 1 - o, 1 - p), v(1 - o, 1 - o, 1 - p), v(1 - o, 1 - o, 1 - o), v(o, 1 - o, 1 - o), spriteCable));
            south_block.addUnculledFace(quad(v(o, o, 1 - o), v(1 - o, o, 1 - o), v(1 - o, o, 1 - p), v(o, o, 1 - p), spriteCable));
            south_block.addUnculledFace(quad(v(1 - o, o, 1 - o), v(1 - o, 1 - o, 1 - o), v(1 - o, 1 - o, 1 - p), v(1 - o, o, 1 - p), spriteCable));
            south_block.addUnculledFace(quad(v(o, o, 1 - p), v(o, 1 - o, 1 - p), v(o, 1 - o, 1 - o), v(o, o, 1 - o), spriteCable));
    
            south_block.addUnculledFace(quad(v(q, 1 - q, 1), v(1 - q, 1 - q, 1), v(1 - q, 1 - q, 1 - p), v(q, 1 - q, 1 - p), spriteSide));
            south_block.addUnculledFace(quad(v(q, q, 1 - p), v(1 - q, q, 1 - p), v(1 - q, q, 1), v(q, q, 1), spriteSide));
            south_block.addUnculledFace(quad(v(1 - q, q, 1 - p), v(1 - q, 1 - q, 1 - p), v(1 - q, 1 - q, 1), v(1 - q, q, 1), spriteSide));
            south_block.addUnculledFace(quad(v(q, q, 1), v(q, 1 - q, 1), v(q, 1 - q, 1 - p), v(q, q, 1 - p), spriteSide));
    
            south_block.addUnculledFace(quad(v(q, 1 - q, 1 - p), v(1 - q, 1 - q, 1 - p), v(1 - q, q, 1 - p), v(q, q, 1 - p), spriteConnector));
            south_block.addUnculledFace(quad(v(q, 1 - q, 1), v(q, q, 1), v(1 - q, q, 1), v(1 - q, 1 - q, 1), spriteSide));
            SOUTH_BLOCK = new CableModelPart(south_block.build(), true, null);
    
            QuadCollection.Builder south_nothing = new QuadCollection.Builder();
            south_nothing.addCulledFace(Direction.SOUTH, quad(v(o, o, 1 - o), v(1 - o, o, 1 - o), v(1 - o, 1 - o, 1 - o), v(o, 1 - o, 1 - o), spriteCable));
            SOUTH_NOTHING = new CableModelPart(south_nothing.build(), true, null);
    
            QuadCollection.Builder full_block = new QuadCollection.Builder();
            full_block.addCulledFace(Direction.UP, quad(v(0, 1, 1), v(1, 1, 1), v(1, 1, 0), v(0, 1, 0), spriteSide));
            full_block.addCulledFace(Direction.DOWN, quad(v(0, 0, 0), v(1, 0, 0), v(1, 0, 1), v(0, 0, 1), spriteSide));
            full_block.addCulledFace(Direction.EAST, quad(v(1, 0, 0), v(1, 1, 0), v(1, 1, 1), v(1, 0, 1), spriteSide));
            full_block.addCulledFace(Direction.WEST, quad(v(0, 0, 1), v(0, 1, 1), v(0, 1, 0), v(0, 0, 0), spriteSide));
            full_block.addCulledFace(Direction.NORTH, quad(v(0, 1, 0), v(1, 1, 0), v(1, 0, 0), v(0, 0, 0), spriteSide));
            full_block.addCulledFace(Direction.SOUTH, quad(v(0, 0, 1), v(1, 0, 1), v(1, 1, 1), v(0, 1, 1), spriteSide));
            FULL_BLOCK = new CableModelPart(full_block.build(), true, null); 
        }
    }

    public static BakedQuad quad(Vec3 v1, Vec3 v2, Vec3 v3, Vec3 v4, TextureAtlasSprite sprite, int rotation) {
        return switch (rotation) {
            case 0 -> quad(v1, v2, v3, v4, sprite);
            case 1 -> quad(v2, v3, v4, v1, sprite);
            case 2 -> quad(v3, v4, v1, v2, sprite);
            case 3 -> quad(v4, v1, v2, v3, sprite);
            default -> quad(v1, v2, v3, v4, sprite);
        };
    }

    public static BakedQuad quad(Vec3 v1, Vec3 v2, Vec3 v3, Vec3 v4, TextureAtlasSprite sprite) {
        Vec3 normal = v3.subtract(v2).cross(v1.subtract(v2)).normalize();

        Direction face = Direction.getNearest((int)normal.x, (int)normal.y, (int)normal.z, Direction.UP);

        QuadBakingVertexConsumer builder = new QuadBakingVertexConsumer();
        builder.setSprite(sprite);
        builder.setDirection(face);
        putVertex(builder, normal, v1, face, sprite);
        putVertex(builder, normal, v2, face, sprite);
        putVertex(builder, normal, v3, face, sprite);
        putVertex(builder, normal, v4, face, sprite);
        return builder.bakeQuad();
    }

    private static void putVertex(VertexConsumer builder, Vec3 normal,
                                  Vec3 pos, Direction face, TextureAtlasSprite sprite) {
        float u, v;

        // Project onto the right plane depending on face
        switch (face) {
            case UP, DOWN -> { // X/Z plane
                u = (float) pos.x;
                v = (float) pos.z;
            }
            case NORTH, SOUTH -> { // X/Y plane
                u = (float) pos.x;
                v = (float) pos.y;
            }
            case EAST, WEST -> { // Z/Y plane
                u = (float) pos.z;
                v = (float) pos.y;
            }
            default -> {
                u = 0;
                v = 0;
            }
        }

        // Convert to atlas coordinates
        float iu = sprite.getU(u);
        float iv = sprite.getV(v);

        builder.addVertex((float) pos.x, (float) pos.y, (float) pos.z)
            .setUv(iu, iv)
            .setColor(1f, 1f, 1f, 1f)
            .setNormal((float) normal.x, (float) normal.y, (float) normal.z);
    }

    public static Vec3 v(double x, double y, double z) {
        return new Vec3(x, y, z);
    }
}
