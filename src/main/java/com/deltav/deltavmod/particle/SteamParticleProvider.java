package com.deltav.deltavmod.particle;

import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;

@OnlyIn(Dist.CLIENT)
public class SteamParticleProvider implements ParticleProvider<SimpleParticleType> {
    private final SpriteSet sprite;

    public SteamParticleProvider(SpriteSet sprite) {
        this.sprite = sprite;
    }

    @Override
    public Particle createParticle(SimpleParticleType type, ClientLevel level,
                                   double x, double y, double z,
                                   double dx, double dy, double dz) {
        SteamParticle particle = new SteamParticle(level, x, y, z, dx, dy, dz, sprite);
        particle.setSpriteFromAge(this.sprite);
        return particle;
    }
}

