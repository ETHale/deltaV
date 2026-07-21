package com.deltav.deltavmod.block.energy.cable.modelstate;

import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.VariantMutator;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.model.block.CustomUnbakedBlockStateModel;
import net.neoforged.neoforge.client.model.generators.blockstate.CustomBlockStateModelBuilder;
import net.neoforged.neoforge.client.model.generators.blockstate.UnbakedMutator;

public class CableBlockStateModelBuilder extends CustomBlockStateModelBuilder{
    private CableModelPart.Unbaked model;
    private Identifier texture;
    public CableBlockStateModelBuilder() {}

    public void setTexture(Identifier texture) {
        this.texture = texture;
    }

    @Override
    public CableBlockStateModelBuilder with(VariantMutator variantMutator) {
        // If you want to apply any mutators that assumes your unbaked model part is a `Variant`
        // If not, this should do nothing
        return this;
    }

    // This is for generalized unbaked blockstate models
    @Override
    public CableBlockStateModelBuilder with(UnbakedMutator unbakedMutator) {
        var result = new CableBlockStateModelBuilder();

        if (this.texture != null) {
            BlockStateModel.Unbaked mutated =
                    unbakedMutator.apply(new CableBlockStateModel.Unbaked(this.texture));

            if (mutated instanceof CableBlockStateModel.Unbaked casted) {
                result.texture = casted.texture();
            } else {
                result.texture = this.texture;
            }
        }

        return result;
    }

    // Converts the builder to its unbaked variant to encode
    @Override
    public CustomUnbakedBlockStateModel toUnbaked() {
        if (this.model == null) {
            throw new IllegalStateException("CableBlockStateModelBuilder: no model part present; ensure the builder was populated");
        }
        return new CableBlockStateModel.Unbaked(texture);
    }

    public CableBlockStateModelBuilder part(CableModelPart.Unbaked model) {
        this.model = model;
        return this;
    }
}
