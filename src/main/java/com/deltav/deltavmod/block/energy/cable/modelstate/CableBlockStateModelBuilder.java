package com.deltav.deltavmod.block.energy.cable.modelstate;

import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.block.model.VariantMutator;
import net.neoforged.neoforge.client.model.block.CustomUnbakedBlockStateModel;
import net.neoforged.neoforge.client.model.generators.blockstate.CustomBlockStateModelBuilder;
import net.neoforged.neoforge.client.model.generators.blockstate.UnbakedMutator;

public class CableBlockStateModelBuilder extends CustomBlockStateModelBuilder{
    private CableModelPart.Unbaked model;
    public CableBlockStateModelBuilder() {}

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
        if (this.model != null) {
            CableBlockStateModel.Unbaked stateUnbaked = new CableBlockStateModel.Unbaked(this.model);
            BlockStateModel.Unbaked mutated = unbakedMutator.apply(stateUnbaked);

            if (mutated instanceof CableBlockStateModel.Unbaked casted) {
                result.model = casted.model();
            } else {
                result.model = this.model;
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
        return new CableBlockStateModel.Unbaked(this.model);
    }

    public CableBlockStateModelBuilder part(CableModelPart.Unbaked model) {
        this.model = model;
        return this;
    }
}
