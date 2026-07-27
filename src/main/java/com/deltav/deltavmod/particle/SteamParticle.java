package com.deltav.deltavmod.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;

public class SteamParticle extends SingleQuadParticle {
    private final SpriteSet spriteSet;

    protected SteamParticle(ClientLevel world, double x, double y, double z,
                            double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z, vx, vy, vz, spriteSet.first());

        this.lifetime = 40 + this.random.nextInt(20);
        this.gravity = -0.01F; 

        
        this.xd = (this.random.nextGaussian() * 0.005);
        this.yd = 0.18 + (this.random.nextDouble() * 0.02); 
        this.zd = (this.random.nextGaussian() * 0.005);

        this.alpha = 0.9F;
        this.scale(1.5F + this.random.nextFloat() * 0.2F);
        this.spriteSet = spriteSet;
    }

    @Override
    public void tick() {
        this.setSpriteFromAge(spriteSet);
        super.tick();

        // after 70% of lifetime, start drifting outward
        if (this.age > (this.lifetime * 0.7)) {
            this.xd += (this.random.nextGaussian() * 0.02);
            this.zd += (this.random.nextGaussian() * 0.02);
        }
    }

    public SingleQuadParticle.Layer getLayer() {
      return Layer.TRANSLUCENT;
   }

    // @Override
    // public ParticleRenderType getRenderType() {
    //     return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    // }
}
