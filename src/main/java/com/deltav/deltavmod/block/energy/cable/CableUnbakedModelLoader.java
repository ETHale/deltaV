package com.deltav.deltavmod.block.energy.cable;


import javax.annotation.Nullable;

import com.deltav.deltavmod.DeltaV;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.TextureSlots;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelDebugName;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.QuadCollection;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.model.AbstractUnbakedModel;
import net.neoforged.neoforge.client.model.ExtendedUnbakedGeometry;
import net.neoforged.neoforge.client.model.StandardModelParameters;
import net.neoforged.neoforge.client.model.UnbakedModelLoader;
import net.neoforged.neoforge.client.model.pipeline.QuadBakingVertexConsumer;

/*
 * Heavily modified from the credit material for newer neoforge versions
 * Programatically makes a model for the cable so has to make quads 
 * 
 * credit to: https://www.mcjty.eu/docs/1.20.4_neo/ep5
 */
public final class CableUnbakedModelLoader implements UnbakedModelLoader<CableUnbakedModelLoader.CableUnbakedModel>, ResourceManagerReloadListener {
    public static final CableUnbakedModelLoader INSTANCE = new CableUnbakedModelLoader();
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(DeltaV.MODID, "cableloader");
    private CableUnbakedModelLoader() {}

    @Override
    public void onResourceManagerReload(ResourceManager manager) { /* clear caches if any */ }

    @Override
    public CableUnbakedModel read(JsonObject jsonObject, JsonDeserializationContext deserializationContext) throws JsonParseException {
        boolean facade = jsonObject.has("facade") && jsonObject.get("facade").getAsBoolean();
        StandardModelParameters params = StandardModelParameters.parse(jsonObject, deserializationContext); // common params
        CableUnbakedGeometry geometry = new CableUnbakedGeometry(facade);
        return new CableUnbakedModel(params, geometry);
    }

    // UnbakedModel
    public class CableUnbakedModel extends AbstractUnbakedModel {
        private final CableUnbakedGeometry geometry;
        public CableUnbakedModel(StandardModelParameters params, CableUnbakedGeometry geometry) {
            super(params);
            this.geometry = geometry;
        }
        @Override
        public CableUnbakedGeometry geometry() { return geometry; }
    }

    // Geometry -> previously returned BakedModel, now returns QuadCollection
    public class CableUnbakedGeometry implements ExtendedUnbakedGeometry {
        private final boolean facade;

        public CableUnbakedGeometry(boolean facade) { this.facade = facade; }

        @Override
        public QuadCollection bake(TextureSlots textureSlots,
                                ModelBaker baker,
                                ModelState state,
                                ModelDebugName debugName,
                                ContextMap additionalProperties) {
            // get sprites from the provided TextureSlots (adapt to your API)

            TextureAtlas atlas = new TextureAtlas(ResourceLocation.fromNamespaceAndPath(DeltaV.MODID, "null"));
            TextureAtlasSprite spriteConnector = atlas.getSprite(ResourceLocation.fromNamespaceAndPath(DeltaV.MODID, "null")); //getSprite(textureSlots, "block/cable/connector");
            TextureAtlasSprite spriteNormalCable = null; //getSprite(textureSlots, "block/cable/normal");
            TextureAtlasSprite spriteNoneCable = null; //getSprite(textureSlots, "block/cable/none");
            TextureAtlasSprite spriteEndCable = null; //getSprite(textureSlots, "block/cable/end");
            TextureAtlasSprite spriteCornerCable = null; //getSprite(textureSlots, "block/cable/corner");
            TextureAtlasSprite spriteThreeCable = null; //getSprite(textureSlots, "block/cable/three");
            TextureAtlasSprite spriteCrossCable = null; //getSprite(textureSlots, "block/cable/cross");
            TextureAtlasSprite spriteSide = null; //getSprite(textureSlots, "block/cable/side");

            QuadCollection.Builder builder = new QuadCollection.Builder();

            ConnectorType north = ConnectorType.NONE;//additionalProperties.getOrDefault(CableBlock.NORTH, ConnectorType.NONE)
            ConnectorType south = ConnectorType.NONE;
            ConnectorType west = ConnectorType.NONE;
            ConnectorType east = ConnectorType.NONE;
            ConnectorType up = ConnectorType.NONE;
            ConnectorType down = ConnectorType.NONE;

            double o = .4;  // cable thickness
            double p = .1;
            double q = .2;

            builder.addUnculledFace(makeBakedQuad(
                        v(1 - o, 1, o), v(1 - o, 1, 1 - o), v(1 - o, 1 - o, 1 - o), v(1 - o, 1 - o, o),
                        spriteNormalCable, /* rotation */ 0, /* cullFace */ null));
                builder.addUnculledFace(makeBakedQuad(
                        v(o, 1, 1 - o), v(o, 1, o), v(o, 1 - o, o), v(o, 1 - o, 1 - o),
                        spriteNormalCable, 0, null));
                builder.addUnculledFace(makeBakedQuad(
                        v(o, 1, o), v(1 - o, 1, o), v(1 - o, 1 - o, o), v(o, 1 - o, o),
                        spriteNormalCable, 0, null));
                builder.addUnculledFace(makeBakedQuad(
                        v(o, 1 - o, 1 - o), v(1 - o, 1 - o, 1 - o), v(1 - o, 1, 1 - o), v(o, 1, 1 - o),
                        spriteNormalCable, 0, null));

                        /*
            // Example: adapt one branch (UP) from your original getQuads
            if (up == ConnectorType.CABLE) {
                // replace quads.add(quad(..., spriteCable)) with builder.addUnculledFace(...)
                builder.addUnculledFace(makeBakedQuad(
                        v(1 - o, 1, o), v(1 - o, 1, 1 - o), v(1 - o, 1 - o, 1 - o), v(1 - o, 1 - o, o),
                        spriteNormalCable,  0,  null));
                builder.addUnculledFace(makeBakedQuad(
                        v(o, 1, 1 - o), v(o, 1, o), v(o, 1 - o, o), v(o, 1 - o, 1 - o),
                        spriteNormalCable, 0, null));
                builder.addUnculledFace(makeBakedQuad(
                        v(o, 1, o), v(1 - o, 1, o), v(1 - o, 1 - o, o), v(o, 1 - o, o),
                        spriteNormalCable, 0, null));
                builder.addUnculledFace(makeBakedQuad(
                        v(o, 1 - o, 1 - o), v(1 - o, 1 - o, 1 - o), v(1 - o, 1, 1 - o), v(o, 1, 1 - o),
                        spriteNormalCable, 0, null));
            } else if (up == ConnectorType.BLOCK) {
                // ... translate remaining branches same way, using builder.addUnculledFace or
                // builder.addCulledFace(Direction.UP, theQuad) when appropriate ...
            } else {
                QuadSetting pattern = CablePatterns.findPattern(west, south, east, north);
                builder.addUnculledFace(makeBakedQuad(
                        v(o, 1 - o, 1 - o), v(1 - o, 1 - o, 1 - o), v(1 - o, 1 - o, o), v(o, 1 - o, o),
                        spriteGetter.apply(pattern.sprite()), pattern.rotation(), null));
            }
             */

            // --- repeat the same conversion for all sides (down, east, west, north, south) ---
            // (copy the rest of your if/else blocks replacing quads.add(...) with builder.addUnculledFace
            // or addCulledFace(direction, ...) as required)

            // If facade rendering is needed at model bake time you must bake the facade's quads too.
            // Otherwise leave dynamic facade rendering to the block entity renderer or use ResolvedModel to inline it.

            return builder.build();
        }

        private static Vec3 v(double x, double y, double z) {
            // return vertex coordinate array or small Vector3f depending on your makeBakedQuad signature
            return new Vec3((float)x, (float)y, (float)z);
        }

        /**
         * Build a BakedQuad from four vertices and a sprite.
         *
         * @param v0..v3 vertex triples (x,y,z)
         * @param sprite texture
         * @param rotation rotation in 90-degree steps (0..3) if you used it previously
         * @param cullFace the face to be culled, or null for unculled
         */
        private BakedQuad makeBakedQuad(Vec3 v1, Vec3 v2, Vec3 v3, Vec3 v4,
                                        TextureAtlasSprite sprite, int rotation, @Nullable Direction cullFace) {
            return switch (rotation) {
                case 0 -> quad(v1, v2, v3, v4, sprite);
                case 1 -> quad(v2, v3, v4, v1, sprite);
                case 2 -> quad(v3, v4, v1, v2, sprite);
                case 3 -> quad(v4, v1, v2, v3, sprite);
                default -> quad(v1, v2, v3, v4, sprite);
            };
        }

        private static BakedQuad quad(Vec3 v1, Vec3 v2, Vec3 v3, Vec3 v4, TextureAtlasSprite sprite) {
            Vec3 normal = v3.subtract(v2).cross(v1.subtract(v2)).normalize();

            BakedQuad[] quad = new BakedQuad[1];
            QuadBakingVertexConsumer builder = new QuadBakingVertexConsumer();
            builder.setSprite(sprite);
            builder.setDirection(Direction.getApproximateNearest((double)normal.x, (double)normal.y, (double)normal.z));
            putVertex(builder, normal, v1.x, v1.y, v1.z, 0, 0, sprite);
            putVertex(builder, normal, v2.x, v2.y, v2.z, 0, 1, sprite);
            putVertex(builder, normal, v3.x, v3.y, v3.z, 1, 1, sprite);
            putVertex(builder, normal, v4.x, v4.y, v4.z, 1, 0, sprite);
            return quad[0];
        }

        private static void putVertex(VertexConsumer builder, Position normal,
                                 double x, double y, double z, float u, float v,
                                 TextureAtlasSprite sprite) {
            float iu = sprite.getU(u);
            float iv = sprite.getV(v);
            builder.addVertex((float)x, (float)y, (float)z)
                    .setUv1((int)iu, (int)iv)
                    .setUv2(0, 0)
                    .setColor(1.0f, 1.0f, 1.0f, 1.0f)
                    .setNormal((float) normal.x(), (float) normal.y(), (float) normal.z());
        }
    }
}
