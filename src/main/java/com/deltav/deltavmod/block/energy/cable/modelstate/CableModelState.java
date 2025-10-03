package com.deltav.deltavmod.block.energy.cable.modelstate;

import org.joml.Matrix4fc;

import com.mojang.math.Transformation;
import com.mojang.serialization.Codec;

import net.minecraft.client.resources.model.ModelState;
import net.minecraft.core.Direction;

/*
 * Based on https://docs.neoforged.net/docs/resources/client/models/modelloaders#creating-custom-block-state-model-loaders
 */
public class CableModelState implements ModelState{
    public static final Codec<CableModelState> CODEC = Codec.unit(new CableModelState());

    public CableModelState() {}

    // Returns the model rotation to apply to the baking vertices
    @Override
    public Transformation transformation() {
        return Transformation.identity();
    }

    @Override
    public Matrix4fc faceTransformation(Direction direction) {
        return NO_TRANSFORM;
    }

    // Returns the inverse of faceTransformation that is applied to a given face on the model
    // This is passed to the FaceBakery
    @Override
    public Matrix4fc inverseFaceTransformation(Direction direction) {
        return NO_TRANSFORM;
    }
    
}
