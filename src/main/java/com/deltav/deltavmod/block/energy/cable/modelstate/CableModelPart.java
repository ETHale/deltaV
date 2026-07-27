package com.deltav.deltavmod.block.energy.cable.modelstate;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;


import org.joml.Vector3f;

import com.deltav.deltavmod.DeltaV;
import com.mojang.math.Quadrant;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.block.dispatch.ModelState;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.geometry.BakedQuad.MaterialFlags;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelDebugName;
import net.minecraft.client.resources.model.ResolvableModel;
import net.minecraft.client.resources.model.ResolvedModel;
import net.minecraft.client.resources.model.cuboid.CuboidFace;
import net.minecraft.client.resources.model.cuboid.CuboidModelElement;
import net.minecraft.client.resources.model.cuboid.FaceBakery;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.client.resources.model.sprite.TextureSlots;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.client.resources.model.sprite.MaterialBaker;
import net.minecraft.resources.Identifier;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;

/*
 * Based on https://docs.neoforged.net/docs/resources/client/models/modelloaders#creating-custom-block-state-model-loaders
 */
public record CableModelPart(QuadCollection quads, boolean useAmbientOcclusion, Material.Baked material) implements BlockStateModelPart {    
    @Override
    @MaterialFlags 
    public int materialFlags() {
        return 0;
    }

    @Override
    public Material.Baked particleMaterial() {
        return this.material;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable Direction direction) {
        return this.quads.getQuads(direction);
    }

    public static final Identifier MODEL_LOCATION = Identifier.fromNamespaceAndPath(DeltaV.MODID, "cable_model");

    // The unbaked model that is read from the block state json
    public record Unbaked(Identifier modelLocation, CableModelState modelState) implements BlockStateModelPart.Unbaked {
        
        // Used for the unbaked block state model
        public static final MapCodec<CableModelPart.Unbaked> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                Identifier.CODEC.fieldOf("model").forGetter(CableModelPart.Unbaked::modelLocation),
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
            Material.Baked particleMaterial = resolvedModel.resolveParticleMaterial(slots, baker);
            QuadCollection quads = resolvedModel.bakeTopGeometry(slots, baker, (ModelState)this.modelState);
            
            // Return the baked part
            return new CableModelPart(quads, ao, particleMaterial);
        }
    }

    /*
     * Template for all the different parts that make up the cable model
     * These are combined based on the block state to make the final model
     * Needs the name of the block texture passed in to the constructor
     */
    public static class CableModelPartTemplate {

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

        public final Identifier texture;
        public Material.Baked spriteCable;
        public Material.Baked spriteSide;
        public Material.Baked spriteConnector;
        public final double cableThickness;
        public final double connectorThickness;
        public final double connectorWidth;

        public CableModelPartTemplate(ModelBaker baker, Identifier texture) {
            this(baker, texture, .4, .1, .3);
        }

        public CableModelPartTemplate(ModelBaker baker, Identifier texture, double cableThickness, double connectorThickness, double connectorWidth) {
            this.texture = texture;
            this.cableThickness = cableThickness;
            this.connectorThickness = connectorThickness;
            this.connectorWidth = connectorWidth;
            
            try {
                MaterialBaker materials = baker.materials();

                DeltaV.LOGGER.debug("Loading cable texture: " + texture);

                spriteCable = materials.get(new Material(texture, false), new CableModelDebugName(texture.toDebugFileName(), ""));
                spriteSide = materials.get(new Material(texture.withSuffix("_side"), false), new CableModelDebugName(texture.toDebugFileName(), "_side"));
                spriteConnector = materials.get(new Material(texture.withSuffix("_connector"), false), new CableModelDebugName(texture.toDebugFileName(), "_connector"));

                DeltaV.LOGGER.debug("Sprites loaded " + spriteCable + " " + spriteSide + " " + spriteConnector);
            } catch (Exception e) {
                DeltaV.LOGGER.error("Error loading cable texture: " + texture, e);
                spriteCable = null;
                spriteSide = null;
                spriteConnector = null;
            }

            Map<String, Material.Baked> textures = Map.of(
                "#cable", spriteCable,
                "#side", spriteSide,
                "#connector", spriteConnector
            );
    
            double o = cableThickness;      // Thickness of the cable. .0 would be full block, .5 is infinitely thin.
            double p = connectorThickness;      // Thickness of the connector as it is put on the connecting block
            double q = connectorWidth;      // The wideness of the connector

            List<CuboidModelElement> up_cable =  new ArrayList<>();
            addBox(up_cable, o, 1 - o, o, 1 - o, 1, 1 - o, "#cable");
            UP_CABLE = bakePart(baker, up_cable, textures);

            List<CuboidModelElement> up_block =  new ArrayList<>();
            addBox(up_block, o, 1 - o, o, 1 - o, 1 - p, 1 - o, "#cable");
            addBox(up_block, q, 1 - p, q, 1 - q, 1, 1 - q, "#side");
            addFace(up_block, Direction.DOWN, q, 1 - p, q, 1 - q, 1 - p, 1 - q, "#connector");
            UP_BLOCK = bakePart(baker, up_block, textures);

            List<CuboidModelElement> up_nothing =  new ArrayList<>();
            addFace(up_nothing, Direction.UP, o, 1 - o, o, 1 - o, 1 - o, 1 - o, "#cable");
            UP_NOTHING = bakePart(baker, up_nothing, textures);

            List<CuboidModelElement> down_cable =  new ArrayList<>();
            addBox(down_cable, o, 0, o, 1 - o, o, 1 - o, "#cable");
            DOWN_CABLE = bakePart(baker, down_cable, textures);

            List<CuboidModelElement> down_block =  new ArrayList<>();
            addBox(down_block, o, p, o, 1 - o, o, 1 - o, "#cable");
            addBox(down_block, q, 0, q, 1 - q, p, 1 - q, "#side");
            addFace(down_block, Direction.UP, q, p, q, 1 - q, p, 1 - q, "#connector");
            DOWN_BLOCK = bakePart(baker, down_block, textures);

            List<CuboidModelElement> down_nothing =  new ArrayList<>();
            addFace(down_nothing, Direction.DOWN, o, o, o, 1 - o, o, 1 - o, "#cable");
            DOWN_NOTHING = bakePart(baker, down_nothing, textures);

            List<CuboidModelElement> east_cable =  new ArrayList<>();
            addBox(east_cable, 1 - o, o, o, 1, 1 - o, 1 - o, "#cable");
            EAST_CABLE = bakePart(baker, east_cable, textures);

            List<CuboidModelElement> east_block =  new ArrayList<>();
            addBox(east_block, 1 - o, o, o, 1 - p, 1 - o, 1 - o, "#cable");
            addBox(east_block, 1 - p, q, q, 1, 1 - q, 1 - q, "#side");
            addFace(east_block, Direction.WEST, 1 - p, q, q, 1 - p, 1 - q, 1 - q, "#connector");
            EAST_BLOCK = bakePart(baker, east_block, textures);

            List<CuboidModelElement> east_nothing =  new ArrayList<>();
            addFace(east_nothing, Direction.EAST, 1 - o, o, o, 1 - o, 1 - o, 1 - o, "#cable");
            EAST_NOTHING = bakePart(baker, east_nothing, textures);

            List<CuboidModelElement> west_cable =  new ArrayList<>();
            addBox(west_cable, 0, o, o, o, 1 - o, 1 - o, "#cable");
            WEST_CABLE = bakePart(baker, west_cable, textures);

            List<CuboidModelElement> west_block =  new ArrayList<>();
            addBox(west_block, p, o, o, o, 1 - o, 1 - o, "#cable");
            addBox(west_block, 0, q, q, p, 1 - q, 1 - q, "#side");
            addFace(west_block, Direction.EAST, p, q, q, p, 1 - q, 1 - q, "#connector");
            WEST_BLOCK = bakePart(baker, west_block, textures);

            List<CuboidModelElement> west_nothing =  new ArrayList<>();
            addFace(west_nothing, Direction.WEST, o, o, o, o, 1 - o, 1 - o, "#cable");
            WEST_NOTHING = bakePart(baker, west_nothing, textures);

            List<CuboidModelElement> north_cable =  new ArrayList<>();
            addBox(north_cable, o, o, 0, 1 - o, 1 - o, o, "#cable");
            NORTH_CABLE = bakePart(baker, north_cable, textures);

            List<CuboidModelElement> north_block =  new ArrayList<>();
            addBox(north_block, o, o, p, 1 - o, 1 - o, o, "#cable");
            addBox(north_block, q, q, 0, 1 - q, 1 - q, p, "#side");
            addFace(north_block, Direction.SOUTH, q, q, p, 1 - q, 1 - q, p, "#connector");
            NORTH_BLOCK = bakePart(baker, north_block, textures);

            List<CuboidModelElement> north_nothing =  new ArrayList<>();
            addFace(north_nothing, Direction.NORTH, o, o, o, 1 - o, 1 - o, o, "#cable");
            NORTH_NOTHING = bakePart(baker, north_nothing, textures);

            List<CuboidModelElement> south_cable =  new ArrayList<>();
            addBox(south_cable, o, o, 1 - o, 1 - o, 1 - o, 1, "#cable");
            SOUTH_CABLE = bakePart(baker, south_cable, textures);

            List<CuboidModelElement> south_block =  new ArrayList<>();
            addBox(south_block, o, o, 1 - o, 1 - o, 1 - o, 1 - p, "#cable");
            addBox(south_block, q, q, 1 - p, 1 - q, 1 - q, 1, "#side");
            addFace(south_block, Direction.NORTH, q, q, 1 - p, 1 - q, 1 - q, 1 - p, "#connector");
            SOUTH_BLOCK = bakePart(baker, south_block, textures);

            List<CuboidModelElement> south_nothing =  new ArrayList<>();
            addFace(south_nothing, Direction.SOUTH, o, o, 1 - o, 1 - o, 1 - o, 1 - o, "#cable");
            SOUTH_NOTHING = bakePart(baker, south_nothing, textures);

            List<CuboidModelElement> full_block =  new ArrayList<>();
            addFace(full_block, Direction.UP, 0, 1, 0, 1, 1, 1, "#side");
            addFace(full_block, Direction.DOWN, 0, 0, 0, 1, 0, 1, "#side");
            addFace(full_block, Direction.EAST, 1, 0, 0, 1, 1, 1, "#side");
            addFace(full_block, Direction.WEST, 0, 0, 1, 0, 1, 0, "#side");
            addFace(full_block, Direction.NORTH, 0, 1, 0, 1, 0, 0, "#side");
            addFace(full_block, Direction.SOUTH, 0, 0, 1, 1, 1, 1, "#side");
            FULL_BLOCK = bakePart(baker, full_block, textures);
        }
    }

    private static void addBox(
            List<CuboidModelElement> elements,
            double x0, double y0, double z0,
            double x1, double y1, double z1,
            String texture,
            boolean cull)
    {
        Map<Direction, CuboidFace> faces = new EnumMap<>(Direction.class);

        for (Direction dir : Direction.values()) {
            faces.put(dir,
                new CuboidFace(
                    cull ? dir : null,
                    -1,
                    texture,
                    uvs(dir, x0, y0, z0, x1, y1, z1),
                    Quadrant.R0
                ));
        }

        elements.add(new CuboidModelElement(
            new Vector3f((float)x0 * 16, (float)y0 * 16, (float)z0 * 16),
            new Vector3f((float)x1 * 16, (float)y1 * 16, (float)z1 * 16),
            faces,
            null,
            true,
            0
        ));
    }

    private static void addBox(
            List<CuboidModelElement> elements,
            double x0, double y0, double z0,
            double x1, double y1, double z1,
            String texture)
    {
        addBox(elements, x0, y0, z0, x1, y1, z1, texture, true);
    }

    private static void addFace(
        List<CuboidModelElement> elements,
        Direction face,
        double x0, double y0, double z0,
        double x1, double y1, double z1,
        String texture)
    {
        elements.add(new CuboidModelElement(
            new Vector3f((float)(x0 * 16), (float)(y0 * 16), (float)(z0 * 16)),
            new Vector3f((float)(x1 * 16), (float)(y1 * 16), (float)(z1 * 16)),
            Map.of(
                face,
                new CuboidFace(
                    face,
                    -1,
                    texture,
                    uvs(face, x0, y0, z0, x1, y1, z1),
                    Quadrant.R0
                )
            ),
            null,
            true,
            0
        ));
    }

    public static Vec3 v(double x, double y, double z) {
        return new Vec3(x, y, z);
    }

    private static CuboidFace.UVs uvs(
            Direction face,
            double x0, double y0, double z0,
            double x1, double y1, double z1) {

        float minU, minV, maxU, maxV;

        switch (face) {
            case UP, DOWN -> {
                minU = (float) Math.min(x0, x1) * 16f;
                maxU = (float) Math.max(x0, x1) * 16f;
                minV = (float) Math.min(z0, z1) * 16f;
                maxV = (float) Math.max(z0, z1) * 16f;
            }

            case NORTH, SOUTH -> {
                minU = (float) Math.min(x0, x1) * 16f;
                maxU = (float) Math.max(x0, x1) * 16f;
                minV = (float) Math.min(y0, y1) * 16f;
                maxV = (float) Math.max(y0, y1) * 16f;
            }

            case EAST, WEST -> {
                minU = (float) Math.min(z0, z1) * 16f;
                maxU = (float) Math.max(z0, z1) * 16f;
                minV = (float) Math.min(y0, y1) * 16f;
                maxV = (float) Math.max(y0, y1) * 16f;
            }

            default -> throw new AssertionError(face);
        }

        return new CuboidFace.UVs(minU, minV, maxU, maxV);
    }

    private static CableModelPart bakePart(
            ModelBaker baker,
            List<CuboidModelElement> elements,
            Map<String, Material.Baked> textures) {

        QuadCollection.Builder builder = new QuadCollection.Builder();
        for (CuboidModelElement element : elements) {
            for (Direction dir : Direction.values()) {
                CuboidFace face = element.faces().get(dir);
                if (face == null) continue;

                Material.Baked material = textures.get(face.texture());
                if (material == null) {
                    DeltaV.LOGGER.error("No material bound for texture key: " + face.texture());
                    continue;
                }

                BakedQuad quad = FaceBakery.bakeQuad(
                    baker, element.from(), element.to(),
                    face, material, dir,
                    new CableModelState(), element.rotation(), true, 0
                );
                builder.addUnculledFace(quad);
            }
        }
        return new CableModelPart(builder.build(), true, textures.get("#cable"));
    }

    private static class CableModelDebugName implements ModelDebugName {
        private String part;
        private String filePath;

        CableModelDebugName(String filePath, String part) {
            this.filePath = filePath;
            this.part = part;
        }

        @Override
        public String debugName() {
            return String.format("%s%s", filePath, part);
        }
    }
}
