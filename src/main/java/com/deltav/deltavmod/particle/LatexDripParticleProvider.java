package com.deltav.deltavmod.particle;

import com.deltav.deltavmod.fluid.ModFluids;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.DripParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

public class LatexDripParticleProvider implements ParticleProvider<SimpleParticleType> {
    private final SpriteSet spriteSet;

    public LatexDripParticleProvider(SpriteSet spriteSet) {
        this.spriteSet = spriteSet;
    }

    @Override
    public Particle createParticle(
        SimpleParticleType type,
        ClientLevel level,
        double x,
        double y,
        double z,
        double xSpeed,
        double ySpeed,
        double zSpeed,
        RandomSource random
    ) {
        DripParticle dripparticle = new DripParticle(level, x, y, z, ModFluids.LATEX_FLOW.get(), this.spriteSet.first());
        dripparticle.setColor(252f / 255f, 255f / 255f, 232f / 255f);
        return dripparticle;
    }
}
