package com.deltav.deltavmod.particle;

import com.deltav.deltavmod.DeltaV;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.data.ParticleDescriptionProvider;

public class ModParticleDescriptionProvider extends ParticleDescriptionProvider {
    public ModParticleDescriptionProvider(PackOutput output) {
        super(output);
    }

    // see https://docs.neoforged.net/docs/resources/client/particles/
    @Override
    protected void addDescriptions() {
        spriteSet(ModParticlesTypes.STEAM.get(),
            Identifier.fromNamespaceAndPath(DeltaV.MODID, "steam"),
            6,
            false
        );
        spriteSet(ModParticlesTypes.LATEX_DRIP.get(), 
            Identifier.fromNamespaceAndPath("minecraft", "drip_fall")
        );
    }
}