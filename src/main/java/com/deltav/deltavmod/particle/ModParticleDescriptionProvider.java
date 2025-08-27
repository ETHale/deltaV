package com.deltav.deltavmod.particle;

import com.deltav.deltavmod.DeltaV;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.data.ParticleDescriptionProvider;

public class ModParticleDescriptionProvider extends ParticleDescriptionProvider {
    public ModParticleDescriptionProvider(PackOutput output) {
        super(output);
    }

    // see https://docs.neoforged.net/docs/resources/client/particles/
    @Override
    protected void addDescriptions() {
        spriteSet(ModParticles.STEAM.get(),
            ResourceLocation.fromNamespaceAndPath(DeltaV.MODID, "steam"),
            6,
            false
        );
    }
}