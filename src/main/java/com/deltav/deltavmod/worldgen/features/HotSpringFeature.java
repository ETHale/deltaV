package com.deltav.deltavmod.worldgen.features;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.deltav.deltavmod.block.ModBlocks;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class HotSpringFeature extends Feature<HotSpringFeatureConfiguration> {
    public HotSpringFeature(Codec<HotSpringFeatureConfiguration> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<HotSpringFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        BlockPos origin = context.origin();

        int clusterRadius = 6;
        int poolCount = context.config().poolCount().sample(random);
        boolean placedAny = false;

        List<BlockPos> clusterCenters = new ArrayList<>();
        int clusters = Math.max(1, (int) Math.sqrt(poolCount));
        for (int i = 0; i < clusters; i++) {
            int dx = random.nextInt(-clusterRadius, clusterRadius + 1);
            int dz = random.nextInt(-clusterRadius, clusterRadius + 1);
            clusterCenters.add(origin.offset(dx, 0, dz));
        }

        Set<BlockPos> carved = new HashSet<>();
        List<BlockPos> placedCenters = new ArrayList<>();

        for (int i = 0; i < poolCount; i++) {
            BlockPos base = clusterCenters.get(i % clusters);

            BlockPos bestCandidate = null;
            double bestScore = Double.NEGATIVE_INFINITY;

            // Try a few candidates and choose one furthest from existing pools
            for (int attempt = 0; attempt < 5; attempt++) {
                int dx = random.nextInt(-4, 5);
                int dz = random.nextInt(-4, 5);
                BlockPos candidate = base.offset(dx, 0, dz);

                double minDistSq = placedCenters.stream()
                    .mapToDouble(c -> c.distSqr(candidate))
                    .min()
                    .orElse(Double.MAX_VALUE);

                // Add a little randomness to avoid overly regular spacing
                double score = minDistSq + random.nextDouble() * 4;

                if (score > bestScore) {
                    bestScore = score;
                    bestCandidate = candidate;
                }
            }

            if (bestCandidate != null && tryPlaceSingle(context, bestCandidate, carved)) {
                placedAny = true;
                placedCenters.add(bestCandidate);

                // Add a 1-block buffer of Silica Sandstone around each carved pool
                for (BlockPos carvedPos : new ArrayList<>(carved)) {
                    for (Direction dir : Direction.values()) {
                        BlockPos bufferPos = carvedPos.relative(dir);
                        BlockState current = level.getBlockState(bufferPos);
                        if (!carved.contains(bufferPos) && !current.isAir() && !current.is(BlockTags.FEATURES_CANNOT_REPLACE) && !level.canSeeSky(bufferPos)) {
                            level.setBlock(bufferPos, ModBlocks.SILICA_SANDSTONE.get().defaultBlockState(), 2);
                        }
                    }
                }
            }
        }

        return placedAny;
    }

    private boolean tryPlaceSingle(FeaturePlaceContext<HotSpringFeatureConfiguration> context, BlockPos offset, Set<BlockPos> carved) {
        int seaLevel = context.level().getSeaLevel();
        if (context.level().getFluidState(offset).is(FluidTags.WATER)
            && offset.getY() >= seaLevel - 1) {
            return false;
        }

        // small local buffer based on size
        RandomSource rand = context.random();
        HotSpringFeatureConfiguration conf = context.config();
        int radius = conf.size().sample(rand);
        int rimDepth = conf.rimSize().sample(rand);
        BlockState contents = conf.contents();
        BlockState rim = conf.rim();
        BlockState base = conf.base();

        if (offset.getY() <= context.level().getMinY() + 4) return false;
        BlockPos start = offset.below(radius + 1);

        int diam = radius * 2 + 1;
        boolean[][][] mask = new boolean[diam][diam/2][diam];

        // carve single ellipsoid
        double rx = radius + rand.nextDouble() * 0.5;
        double ry = (radius/2.0) + rand.nextDouble() * 0.5;
        double rz = radius + rand.nextDouble() * 0.5;

        for (int x = 0; x < diam; x++) {
            for (int y = 0; y < diam/2; y++) {
                for (int z = 0; z < diam; z++) {
                    double dx = (x - radius) / rx;
                    double dy = (y - (ry)) / ry;
                    double dz = (z - radius) / rz;
                    double d = dx*dx + dy*dy + dz*dz;
                    if (d < 1.0 || (d < 1.1 && rand.nextDouble() < 0.3)) {
                        mask[x][y][z] = true;
                    }
                }
            }
        }

        // validate environment
        for (int x = 0; x < diam; x++) {
            for (int y = 0; y < diam/2; y++) {
                for (int z = 0; z < diam; z++) {
                    if (!mask[x][y][z]) continue;
                    BlockPos pos = start.offset(x, y, z);
                    BlockState state = context.level().getBlockState(pos);
                    if (y >= ry && state.liquid()) return false;
                    if (y < ry && !state.isSolid() && !state.equals(contents)) return false;
                }
            }
        }

        // apply carving
        for (int x = 0; x < diam; x++) {
            for (int y = 0; y < diam/2; y++) {
                for (int z = 0; z < diam; z++) {
                    if (!mask[x][y][z]) continue;
                    BlockPos pos = start.offset(x, y, z);
                    if (carved.contains(pos)) continue;
                    boolean aboveWater = y >= ry;
                    context.level().setBlock(pos, aboveWater ? Blocks.AIR.defaultBlockState() : contents, 2);
                    carved.add(pos);
                    if (aboveWater) {
                        context.level().scheduleTick(pos, Blocks.AIR, 0);
                        markAboveForPostProcessing(context.level(), pos);
                    }
                }
            }
        }

        // rim
        if (!rim.isAir()) {
            for (int x = 0; x < diam; x++) {
                for (int y = 0; y < diam/2; y++) {
                    for (int z = 0; z < diam; z++) {
                        if (mask[x][y][z]) continue;
                        boolean neighbor = false;
                        for (Direction d : Direction.values()) {
                            int nx = x + d.getStepX();
                            int ny = y + d.getStepY();
                            int nz = z + d.getStepZ();
                            if (nx>=0&&nx<diam && ny>=0&&ny<diam/2 && nz>=0&&nz<diam) {
                                if (mask[nx][ny][nz]) { neighbor = true; break; }
                            }
                        }
                        if (!neighbor) continue;
                        BlockPos pos = start.offset(x, y, z);
                        if (carved.contains(pos)) continue;
                        BlockState current = context.level().getBlockState(pos);
                        if (current.isSolid() && !current.is(BlockTags.LAVA_POOL_STONE_CANNOT_REPLACE)) {
                            context.level().setBlock(pos, y < rimDepth-1 ? base : rim, 2);
                            markAboveForPostProcessing(context.level(), pos);
                            carved.add(pos);
                        }
                    }
                }
            }
        }

        return true;
    }
}
