package com.deltav.deltavmod.worldgen.features;

import org.apache.commons.lang3.mutable.MutableBoolean;

import com.deltav.deltavmod.DeltaV;
import com.deltav.deltavmod.block.ModBlocks;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class KimberliteCarrotFeature extends Feature<NoneFeatureConfiguration>{
    public KimberliteCarrotFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        BlockPos origin = context.origin();
        RandomSource random = context.random();
        LevelAccessor level = context.level();

        int y = context.chunkGenerator().getMinY();
        DeltaV.LOGGER.debug("Y: %d", y);
        int initialRadius = random.nextIntBetweenInclusive(1, 6);
        float radiusIncrement = 0.1f + random.nextFloat() * 0.015f; // How quickly radius grows
        float health = 80 + random.nextFloat() * 300f; // how quickly it starts to fizzle out
        MutableBoolean reachedSurface = new MutableBoolean(false);

        int heightLimit = level.getMaxY(); // Prevents infinite loop
        int maxSteps = heightLimit - y;

        BlockPos.MutableBlockPos center = new BlockPos.MutableBlockPos();
        float dx = 0f, dz = 0f; // Directional drift for natural wandering

        for (int step = 0; step < maxSteps && !reachedSurface.booleanValue() && health > 0; y++, step++) {
            dx += (random.nextFloat() - 0.5f) * 0.8f;
            dz += (random.nextFloat() - 0.5f) * 0.8f;
            
            int centerX = origin.getX() + Math.round(dx);
            int centerZ = origin.getZ() + Math.round(dz);
            float currentRadius = initialRadius + step * radiusIncrement;
            int r = (int) Math.ceil(currentRadius);

            center.set(centerX, y, centerZ);

            BlockPos.betweenClosedStream(center.offset(-r, 0, -r), center.offset(r, 0, r))
                .forEach(pos -> {
                    if (reachedSurface.booleanValue()) return;

                    double innerDx = pos.getX() - center.getX();
                    double innerDz = pos.getZ() - center.getZ();
                    double dist2 = innerDx * innerDx + innerDz * innerDz;

                    // Edge roughness using random threshold
                    float noise = random.nextFloat() * 0.4f; // tweak for more/less jagged edges
                    float threshold = (1.0f - noise) * currentRadius * currentRadius;

                    if (dist2 <= threshold) {
                        BlockState state = level.getBlockState(pos);
                        if (level.canSeeSky(pos.above())) {
                            reachedSurface.setTrue();
                        } else {
                            placeBlock(level, pos, state);
                        }
                    }
                }
            );
            health = health - random.nextFloat() * 5F;
        }
        return true;
    }

    private void placeBlock(LevelAccessor level, BlockPos pos, BlockState state) {
        if (state.is(BlockTags.OVERWORLD_CARVER_REPLACEABLES))
            setBlock(level, pos, ModBlocks.KIMBERLITE_BUTTON.get().defaultBlockState());
        // TODO ore versions
        // TODO Molten bedrock
        // TODO vary what is placed + increase diamonds + magma at the bottom 
        // TODO have very few placed on the top height level / maybe a crater - health dependant
    }
}
