package com.deltav.deltavmod.block.energy.cable;

import com.google.gson.JsonObject;

import net.neoforged.neoforge.client.model.generators.template.CustomLoaderBuilder;

public class CableLoaderBuilder extends CustomLoaderBuilder{
    public CableLoaderBuilder() {
        super(
            CableUnbakedModelLoader.ID,
            false
        );
    }

    @Override
    protected CustomLoaderBuilder copyInternal() {
        CableLoaderBuilder builder = new CableLoaderBuilder();
        // builder.<field> = this.<field>;
        return builder;
    }
    
    // Serialize the model to JSON.
    @Override
    public JsonObject toJson(JsonObject json) {
        return super.toJson(json);
    }
}
