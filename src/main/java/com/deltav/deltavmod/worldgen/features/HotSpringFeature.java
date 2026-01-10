package com.deltav.deltavmod.worldgen.features;

import java.util.HashSet;
import java.util.Set;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.LakeFeature;

/**
 * HotSpringFeature — water always starts one block lower than rim (guaranteed top air).
 */
public class HotSpringFeature extends Feature<HotSpringFeatureConfiguration> {
    public HotSpringFeature(Codec<HotSpringFeatureConfiguration> codec) {
        super(codec);
    }

    /**
     * Places the hot spring feature in the world. Based from `place` in {@link LakeFeature}
     */
    @Override
    public boolean place(FeaturePlaceContext<HotSpringFeatureConfiguration> context) {
        WorldGenLevel worldgenlevel = context.level();
        RandomSource randomsource = context.random();
        HotSpringFeatureConfiguration conf = context.config();
        BlockPos origin = context.origin();

        // Create a sunken ground area
        int radius = conf.radius();
        int depth =  conf.depth();
        Set<BlockPos>[] areas = createSunkenGround(worldgenlevel, origin, radius, depth, conf, randomsource);
        Set<BlockPos> poolArea = areas[0];
        Set<BlockPos> edgeArea = areas[1];

        // Waterlog the area
        waterlogArea(worldgenlevel, poolArea, conf);

        // Place a geyser
        placeGeyser(worldgenlevel, edgeArea, conf);

        return true;
    }

    private Set<BlockPos>[] createSunkenGround(WorldGenLevel level, BlockPos origin, int radius, int depth, HotSpringFeatureConfiguration conf, RandomSource rand) {
        Set<BlockPos> poolArea = new HashSet<>();
        Set<BlockPos> edgeArea = new HashSet<>();
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        int edgeWidth = conf.edgeWidth();
        for (int y = 0; y < depth; y++) {
            // Adjust noise to create jagged edges
            int newRadius = (int)(radius - (conf.rateOfChange() * y));
            
            //move centre around to add more noise 
            int originNoise = conf.originNoise();
            BlockPos noisyOrigin = origin.offset(rand.nextInt(originNoise) - (originNoise / 2), 0, rand.nextInt(originNoise) - (originNoise / 2));
            
            int areaSize = (int) newRadius + (int) edgeWidth;
            
            for (BlockPos pos : BlockPos.betweenClosed(origin.offset(-areaSize, noisyOrigin.getY(), -areaSize), origin.offset(areaSize, noisyOrigin.getY(), areaSize))) {
                int dx = pos.getX() - noisyOrigin.getX();
                int dz = pos.getZ() - noisyOrigin.getZ();
                float point = dx * dx + dz * dz;
                
                // Check if the block is within the pool area
                float noise = rand.nextFloat() * conf.noiseScale(); 
                float threshold = (1.0f - noise) * newRadius * newRadius;
                if (point <= threshold) {
                    mutablePos.set(pos.getX(), noisyOrigin.getY() - y, pos.getZ());
                    if (canReplaceBlock(level.getBlockState(mutablePos))) {
                        poolArea.add(mutablePos.immutable());
                    }
                }
            }
        }

        // Determine edge area
        for (BlockPos pos : poolArea) {
            for (int dx = -edgeWidth; dx <= edgeWidth; dx++) {
                for (int dz = -edgeWidth; dz <= edgeWidth; dz++) {
                    for (int dy = 0; dy > -2; dy--) {
                        mutablePos.set(pos.offset(dx, dy, dz));
                        if (!poolArea.contains(mutablePos) && canReplaceBlock(level.getBlockState(mutablePos)) 
                            && !edgeArea.contains(mutablePos) && level.getFluidState(mutablePos) != conf.contents()) {
                            level.setBlock(mutablePos, conf.barrier().getState(rand, mutablePos), 2);
                            this.markAboveForPostProcessing(level, pos);
                            edgeArea.add(mutablePos);
                        }
                    }
                }
            }
        }

        @SuppressWarnings("unchecked")
        Set<BlockPos>[] result = (Set<BlockPos>[]) new Set[]{poolArea, edgeArea};
        return result;
    }

    private void waterlogArea(WorldGenLevel level, Set<BlockPos> poolArea, HotSpringFeatureConfiguration conf) {
        for (BlockPos pos : poolArea) {
            level.setBlock(pos, conf.contents().createLegacyBlock(), 2);
            this.markAboveForPostProcessing(level, pos);
        }
    }

    private void placeGeyser(WorldGenLevel level, Set<BlockPos> area, HotSpringFeatureConfiguration conf) {
        if (area.isEmpty()) return;

        // Filter positions to include only those visible to the sky
        Set<BlockPos> skyVisiblePositions = new HashSet<>();
        for (BlockPos pos : area) {
            if (level.canSeeSky(pos)) {
                skyVisiblePositions.add(pos);
            }
        }

        if (skyVisiblePositions.isEmpty()) return;

        // Select a random position from the filtered set
        BlockPos[] positions = skyVisiblePositions.toArray(new BlockPos[0]);
        BlockPos geyserPos = positions[level.getRandom().nextInt(positions.length)];
        level.setBlock(geyserPos, conf.geyser(), 2);
    }

    private boolean canReplaceBlock(BlockState state) {
        return !state.is(BlockTags.FEATURES_CANNOT_REPLACE);
    }
}
