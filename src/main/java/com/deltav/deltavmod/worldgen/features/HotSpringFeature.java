package com.deltav.deltavmod.worldgen.features;

import java.util.HashSet;
import java.util.Set;

import com.deltav.deltavmod.DeltaV;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
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
        
        //check origin is not in liquid and give up if it is
        BlockState originState = worldgenlevel.getBlockState(origin);
        if (originState.getFluidState().isSource()) {
            return false;
        }
        // if its air, move down until we hit ground or 15 blocks
        if (originState.isAir()) {
            int airCount = 0;
            int airLimit = 15;
            BlockPos checkPos = origin.below();
            while (worldgenlevel.getBlockState(checkPos).isAir() && airCount < airLimit) {
                origin = checkPos;
                checkPos = checkPos.below();
                airCount++;
            }
            // if we hit air 15 times, give up
            if (airCount >= airLimit) {
                return false;
            }
        }
        
        // Create a sunken ground area
        int radius = conf.radius().sample(randomsource);
        int depth =  conf.depth().sample(randomsource);
        Set<BlockPos>[] areas = createSunkenGround(worldgenlevel, origin, radius, depth, conf, randomsource);
        Set<BlockPos> poolArea = areas[0];
        Set<BlockPos> edgeArea = areas[1];
        Set<BlockPos> surfaceArea = areas[2];
        
        // Waterlog the area
        waterlogArea(worldgenlevel, poolArea, conf);
        
        // Place edge blocks
        placeEdgeBlocks(worldgenlevel, edgeArea, conf, randomsource);
        
        // Place surface blocks
        placeSurfaceBlocks(worldgenlevel, surfaceArea, conf, randomsource);
        
        // Place a geyser
        placeGeyser(worldgenlevel, surfaceArea, conf);
        
        DeltaV.LOGGER.debug("Placed Hot Spring Feature: {}", context.origin());
        return true;
    }

    private Set<BlockPos>[] createSunkenGround(WorldGenLevel level, BlockPos origin, int radius, int depth, HotSpringFeatureConfiguration conf, RandomSource rand) {
        Set<BlockPos> poolArea = new HashSet<>();
        Set<BlockPos> notWaterLoggedPoolArea = new HashSet<>();
        Set<BlockPos> edgeArea = new HashSet<>();
        Set<BlockPos> surfaceArea = new HashSet<>();
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        int edgeWidth = conf.edgeWidth().sample(rand);
        int contentsDepth = conf.contentsDepth().sample(rand);
        for (int y = 0; y < depth; y++) {
            // Adjust noise to create jagged edges
            int newRadius = (int)(radius - (conf.rateOfChange().sample(rand) * y));
            
            //move centre around to add more noise 
            int originNoise = conf.originNoise().sample(rand);
            BlockPos noisyOrigin = origin.offset(rand.nextInt(originNoise) - (originNoise / 2), 0, rand.nextInt(originNoise) - (originNoise / 2));
            
            int areaSize = (int) newRadius + (int) edgeWidth;
            
            for (BlockPos pos : BlockPos.betweenClosed(origin.offset(-areaSize, noisyOrigin.getY(), -areaSize), origin.offset(areaSize, noisyOrigin.getY(), areaSize))) {
                int dx = pos.getX() - noisyOrigin.getX();
                int dz = pos.getZ() - noisyOrigin.getZ();
                float point = dx * dx + dz * dz;
                
                // Check if the block is within the pool area
                float noise = rand.nextFloat() * conf.noiseScale().sample(rand); 
                float threshold = (1.0f - noise) * newRadius * newRadius;
                if (point <= threshold) {
                    mutablePos.set(pos.getX(), noisyOrigin.getY() - y, pos.getZ());
                    if (canReplaceBlock(level.getBlockState(mutablePos), conf)) {
                        poolArea.add(mutablePos.immutable());
                        if (y < contentsDepth) {
                            notWaterLoggedPoolArea.add(mutablePos.immutable());
                            level.setBlock(mutablePos, Blocks.AIR.defaultBlockState(), 2);
                            this.markAboveForPostProcessing(level, pos);
                        }
                    }
                }
            }
        }

        // Determine edge area
        for (BlockPos pos : poolArea) {
            for (int dx = -edgeWidth; dx <= edgeWidth; dx++) {
                for (int dz = -edgeWidth; dz <= edgeWidth; dz++) {
                    for (int dy = 0; dy > -edgeWidth; dy--) {
                        mutablePos.set(pos.offset(dx, dy, dz));
                        if (!poolArea.contains(mutablePos) && canReplaceBlock(level.getBlockState(mutablePos), conf)
                            && !edgeArea.contains(mutablePos.immutable()) && level.getFluidState(mutablePos) != conf.contents()) {
                            edgeArea.add(mutablePos.immutable());
                        }
                    }
                }
            }
        }
        poolArea.removeAll(edgeArea);

        // loop through edgeArea to find those that touch the surface
        for (BlockPos pos : edgeArea) {
            BlockPos abovePos = pos.above();
            if (((poolArea.contains(abovePos) || level.getBlockState(abovePos).isAir()) && !edgeArea.contains(abovePos))) {
                surfaceArea.add(new BlockPos(pos));
            }
        }
        edgeArea.removeAll(surfaceArea);
        poolArea.removeAll(notWaterLoggedPoolArea);
        
        @SuppressWarnings("unchecked")
        Set<BlockPos>[] result = (Set<BlockPos>[]) new Set[]{poolArea, edgeArea, surfaceArea};
        return result;
    }

    private void waterlogArea(WorldGenLevel level, Set<BlockPos> poolArea, HotSpringFeatureConfiguration conf) {
        for (BlockPos pos : poolArea) {
            level.setBlock(pos, conf.contents().createLegacyBlock(), 2);
            this.markAboveForPostProcessing(level, pos);
        }
    }
    
    private void placeEdgeBlocks(WorldGenLevel level, Set<BlockPos> edgeArea, HotSpringFeatureConfiguration conf, RandomSource rand) {
        for (BlockPos pos : edgeArea) {
            level.setBlock(pos, conf.barrier().getState(rand, pos), 2);
            this.markAboveForPostProcessing(level, pos);
        }
    }
    
    private void placeSurfaceBlocks(WorldGenLevel level, Set<BlockPos> surfaceArea, HotSpringFeatureConfiguration conf, RandomSource rand) {
        for (BlockPos pos : surfaceArea) {
            level.setBlock(pos, conf.surface().getState(rand, pos), 2);
            this.markAboveForPostProcessing(level, pos);
        }
    }

    private void placeGeyser(WorldGenLevel level, Set<BlockPos> area, HotSpringFeatureConfiguration conf) {
        if (area.isEmpty()) return;

        // Select a random position from the filtered set
        BlockPos[] positions = area.toArray(new BlockPos[0]);
        BlockPos geyserPos = positions[level.getRandom().nextInt(positions.length)];
        level.setBlock(geyserPos, conf.geyser(), 2);
    }

    private boolean canReplaceBlock(BlockState state, HotSpringFeatureConfiguration conf) {
        return !state.is(BlockTags.FEATURES_CANNOT_REPLACE) && state != conf.geyser();
    }
}
