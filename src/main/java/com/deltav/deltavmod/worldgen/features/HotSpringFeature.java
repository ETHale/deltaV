package com.deltav.deltavmod.worldgen.features;

import com.deltav.deltavmod.DeltaV;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.tags.BlockTags;

import java.util.HashSet;
import java.util.Set;

public class HotSpringFeature extends Feature<HotSpringFeatureConfiguration> {
    public HotSpringFeature(Codec<HotSpringFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<HotSpringFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        HotSpringFeatureConfiguration conf = context.config();

        int originX = context.origin().getX();
        int originZ = context.origin().getZ();
        int chunkMinX = originX & ~15;
        int chunkMinZ = originZ & ~15;
        int chunkMaxX = chunkMinX + 15;
        int chunkMaxZ = chunkMinZ + 15;

        final int cellSize = Math.max(4, conf.cellSize());
        final int padding = Math.max(0, conf.searchPadding()); // MUST be >= max radius + safety
        final long worldSeed = level.getSeed();

        int searchMinX = chunkMinX - padding;
        int searchMaxX = chunkMaxX + padding;
        int searchMinZ = chunkMinZ - padding;
        int searchMaxZ = chunkMaxZ + padding;

        int cellMinX = floorDiv(searchMinX, cellSize);
        int cellMaxX = floorDiv(searchMaxX, cellSize);
        int cellMinZ = floorDiv(searchMinZ, cellSize);
        int cellMaxZ = floorDiv(searchMaxZ, cellSize);

        boolean placedAny = false;
        Set<BlockPos> carvedThisChunk = new HashSet<>();

        for (int cellX = cellMinX; cellX <= cellMaxX; cellX++) {
            for (int cellZ = cellMinZ; cellZ <= cellMaxZ; cellZ++) {
                long cellSeed = scrambleSeed(worldSeed, cellX, cellZ);
                RandomSource cellRand = RandomSource.create(cellSeed);

                // Sample maxPerCell ONCE for the cell (deterministic)
                int attempts = Math.max(0, conf.maxPerCell().sample(cellRand));

                for (int attemptIndex = 0; attemptIndex < attempts; attemptIndex++) {
                    // deterministic spawn decision for this attempt
                    if (cellRand.nextFloat() >= conf.spawnChance()) continue;

                    // deterministic candidate pos inside the cell
                    int localX = cellRand.nextInt(cellSize);
                    int localZ = cellRand.nextInt(cellSize);
                    int candidateX = cellX * cellSize + localX;
                    int candidateZ = cellZ * cellSize + localZ;

                    // pool-local deterministic seed (include attemptIndex so multiple pools in same cell differ deterministically)
                    long poolSeed = scrambleSeedExtra(worldSeed, candidateX, candidateZ, attemptIndex);
                    RandomSource poolRand = RandomSource.create(poolSeed);

                    // SAMPLE provider-backed values ONCE from poolRand
                    int radius = conf.size().sample(poolRand);
                    int rimDepth = conf.rimSize().sample(poolRand);

                    // SAMPLE deterministic centerY from poolRand (guaranteed identical across chunks)
                    int centerY = determineCenterYDeterministic(level, poolRand);
                    int surfaceY = level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, candidateX, candidateZ);
                    if (centerY > surfaceY) centerY = surfaceY;

                    // quick intersection test using the sampled radius
                    if (!sphereIntersectsChunk(candidateX, candidateZ, radius, chunkMinX, chunkMinZ, chunkMaxX, chunkMaxZ)) {
                        continue;
                    }

                    // carve only portion inside this chunk — pass poolRand, radius, rimDepth (poolRand will continue to be used for mask jitter)
                    boolean carved = tryCarvePartial(context,
                            new BlockPos(candidateX, centerY, candidateZ),
                            carvedThisChunk,
                            conf,
                            poolRand,
                            chunkMinX, chunkMinZ, chunkMaxX, chunkMaxZ,
                            radius, rimDepth);

                    if (carved) placedAny = true;
                }
            }
        }

        return placedAny;
    }

    private boolean tryCarvePartial(FeaturePlaceContext<HotSpringFeatureConfiguration> context,
                                    BlockPos offset,
                                    Set<BlockPos> carved,
                                    HotSpringFeatureConfiguration conf,
                                    RandomSource rand,
                                    int chunkMinX, int chunkMinZ, int chunkMaxX, int chunkMaxZ,
                                    int radius, int rimDepth) {
        WorldGenLevel level = context.level();

        BlockState contents = conf.contents();
        BlockState rim = conf.rim();
        BlockState base = conf.base();

        BlockPos start = offset.below(radius + 1);

        int diam = radius * 2 + 1;
        int halfY = Math.max(1, diam / 2);
        boolean[][][] mask = new boolean[diam][halfY][diam];

        double rx = radius + rand.nextDouble() * 0.5;
        double ry = (radius / 2.0) + rand.nextDouble() * 0.5;
        double rz = radius + rand.nextDouble() * 0.5;

        for (int x = 0; x < diam; x++) {
            for (int y = 0; y < halfY; y++) {
                for (int z = 0; z < diam; z++) {
                    double dx = (x - radius) / rx;
                    double dy = (y - ry) / ry;
                    double dz = (z - radius) / rz;
                    double d = dx * dx + dy * dy + dz * dz;
                    if (d < 1.0 || (d < 1.1 && rand.nextDouble() < 0.3)) {
                        mask[x][y][z] = true;
                    }
                }
            }
        }

        // For each mask position inside this chunk, record whether it's carveable or blocked.
        final int CARVABLE = 1;
        final int BLOCKED_BY_FLUID = 2;
        final int BLOCKED_BY_NONREPLACEABLE = 3;
        int[][][] posState = new int[diam][halfY][diam];
        boolean anyMaskInsideChunk = false;

        for (int x = 0; x < diam; x++) {
            for (int y = 0; y < halfY; y++) {
                for (int z = 0; z < diam; z++) {
                    if (!mask[x][y][z]) continue;
                    BlockPos pos = start.offset(x, y, z);
                    if (!isInsideChunk(pos.getX(), pos.getZ(), chunkMinX, chunkMinZ, chunkMaxX, chunkMaxZ)) continue;
                    anyMaskInsideChunk = true;
                    BlockState state = level.getBlockState(pos);
                    if (!state.getFluidState().isEmpty() && !state.isAir()) {
                        posState[x][y][z] = BLOCKED_BY_FLUID;
                    } else if (!isCarverReplaceable(state, contents)) {
                        posState[x][y][z] = BLOCKED_BY_NONREPLACEABLE;
                    } else {
                        posState[x][y][z] = CARVABLE;
                    }
                }
            }
        }

        // If there's no mask inside this chunk, nothing to do here
        if (!anyMaskInsideChunk) return false;

        // Apply carving
        int carvedCount = 0;
        boolean placingFluid = !contents.getFluidState().isEmpty();
        Fluid targetFluid = placingFluid ? contents.getFluidState().getType() : null;

        for (int x = 0; x < diam; x++) {
            for (int y = 0; y < halfY; y++) {
                for (int z = 0; z < diam; z++) {
                    if (!mask[x][y][z]) continue;
                    if (posState[x][y][z] != CARVABLE) continue; // skip blocked positions
                    BlockPos pos = start.offset(x, y, z);

                    if (carved.contains(pos)) continue;

                    // Defensive: if there's another fluid here that's NOT the target, skip.
                    var existingFluidState = level.getFluidState(pos);
                    if (!existingFluidState.isEmpty() && (targetFluid == null || existingFluidState.getType() != targetFluid)) {
                        continue;
                    }

                    boolean aboveWater = y >= ry;

                    // If we're about to place a fluid (e.g. water/lava/other), only allow it to start if:
                    //  - there's solid support below (not air), OR
                    //  - at least one horizontal neighbor already has the same fluid in-world, OR
                    //  - at least one horizontal neighbor already equals the contents BlockState (covers newly placed fluid this pass).
                    if (!aboveWater && placingFluid) {
                        BlockPos below = pos.below();
                        boolean hasSupport = !level.getBlockState(below).isAir();

                        boolean neighborHasSameFluid = false;
                        for (Direction d : new Direction[] { Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST }) {
                            BlockPos npos = pos.relative(d);
                            // existing fluid in world of same type
                            var nFluid = level.getFluidState(npos);
                            if (!nFluid.isEmpty() && nFluid.getType() == targetFluid) { neighborHasSameFluid = true; break; }
                            // or blockstate equals the contents (covers newly placed fluid or identical block)
                            if (level.getBlockState(npos).equals(contents)) { neighborHasSameFluid = true; break; }
                        }

                        if (!hasSupport && !neighborHasSameFluid) {
                            // Don't start a fluid pocket in mid-air; skip this cell.
                            continue;
                        }
                    }

                    // Place either air (upper region) or the configured contents (lower region).
                    if (aboveWater) {
                        this.setBlock(level, pos, Blocks.AIR.defaultBlockState());
                        level.scheduleTick(pos, Blocks.AIR, 0);
                        markAboveForPostProcessing(level, pos);
                    } else {
                        // Place contents. If it's a fluid that already exists in-world here, this is a no-op, but safe.
                        this.setBlock(level, pos, contents);
                        carved.add(pos); // record placement so neighbors can see it
                    }

                    carvedCount++;
                }
            }
        }

        int rimPlaced = 0;
        for (int x = 0; x < diam; x++) {
            for (int y = 0; y < halfY; y++) {
                for (int z = 0; z < diam; z++) {
                    if (mask[x][y][z]) continue; // only consider outside-mask positions for rims

                    // Check only horizontal neighbors for mask adjacency (prevents vertical-side rims)
                    boolean neighborMask = false;
                    for (Direction d : Direction.values()) {
                        int nx = x + d.getStepX();
                        int ny = y + d.getStepY();
                        int nz = z + d.getStepZ();
                        if (nx >= 0 && nx < diam && ny >= 0 && ny < halfY && nz >= 0 && nz < diam) {
                            if (mask[nx][ny][nz]) { neighborMask = true; break; }
                        }
                    }
                    if (!neighborMask) continue;

                    BlockPos pos = start.offset(x, y, z);
                    if (!isInsideChunk(pos.getX(), pos.getZ(), chunkMinX, chunkMinZ, chunkMaxX, chunkMaxZ)) continue;
                    if (carved.contains(pos)) continue;

                    BlockState current = level.getBlockState(pos);

                    if (level.getFluidState(pos).is(Fluids.WATER) || level.getFluidState(pos).is(Fluids.LAVA)) continue;

                    if (isCarverReplaceable(current, rim) && isNotRoof(pos, level)) {
                        this.setBlock(level, pos, y < (halfY) - rimDepth ? base : rim);
                        markAboveForPostProcessing(level, pos);
                        carved.add(pos);
                        rimPlaced++;
                    }
                }
            }
        }

        // If nothing was placed (no carveable and no rim placed), return false. Otherwise true.
        return (carvedCount + rimPlaced) > 0;
    }

    // Deterministic centerY using pool-local RNG (so same across chunks)
    private int determineCenterYDeterministic(WorldGenLevel level, RandomSource poolRand) {
        int min = Math.max(level.getMinY() + 8, 48);
        int max = Math.min(level.getHeight() - 16, 140);
        int range = Math.max(1, max - min + 1);
        // poolRand.nextInt(range) is deterministic for the pool (poolRand seeded deterministically)
        int offset = poolRand.nextInt(range);
        return min + offset;
    }

    private static boolean isInsideChunk(int x, int z, int chunkMinX, int chunkMinZ, int chunkMaxX, int chunkMaxZ) {
        return x >= chunkMinX && x <= chunkMaxX && z >= chunkMinZ && z <= chunkMaxZ;
    }

    private static boolean sphereIntersectsChunk(int cx, int cz, int radius, int chunkMinX, int chunkMinZ, int chunkMaxX, int chunkMaxZ) {
        int closestX = Math.max(chunkMinX, Math.min(cx, chunkMaxX));
        int closestZ = Math.max(chunkMinZ, Math.min(cz, chunkMaxZ));
        long dx = cx - closestX;
        long dz = cz - closestZ;
        long distSq = dx * dx + dz * dz;
        // increase padding to reduce false-negatives from ellipsoid jitter
        long padding = radius + 4L;
        long threshold = padding * padding;
        return distSq <= threshold;
    }

    private static int floorDiv(int a, int b) {
        int res = a / b;
        if ((a ^ b) < 0 && a % b != 0) res--;
        return res;
    }

    private static long scrambleSeed(long worldSeed, int a, int b) {
        long l = worldSeed;
        l ^= (long) a * 341873128712L + (long) b * 132897987541L;
        l = l * 6364136223846793005L + 1442695040888963407L;
        return l;
    }

    // Slightly different scramble for pool with attemptIndex
    private static long scrambleSeedExtra(long worldSeed, int x, int z, int attempt) {
        long l = worldSeed;
        l ^= (long) x * 0x9E3779B97F4A7C15L;
        l ^= (long) z * 0xC2B2AE3D27D4EB4FL;
        l ^= (long) attempt * 0xBF58476D1CE4E5B9L;
        l = l * 6364136223846793005L + 1442695040888963407L;
        return l;
    }

    private boolean isCarverReplaceable(BlockState state, BlockState contents) {
        if (state.is(contents.getBlock())) return true;
        if (state.is(BlockTags.FEATURES_CANNOT_REPLACE)) return false;
        return state.is(BlockTags.OVERWORLD_CARVER_REPLACEABLES);
    }

    private boolean isNotRoof(BlockPos pos, WorldGenLevel level) {
        if (level.canSeeSky(pos)) {
            if (level.getBlockState(pos.below()).is(Blocks.AIR)) return false;
        }
        return true;
    }
}
