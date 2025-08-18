package com.deltav.deltavmod.worldgen.features;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.deltav.deltavmod.DeltaV;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

/**
 * HotSpringFeature — water always starts one block lower than rim (guaranteed top air).
 */
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

        final long worldSeed = level.getSeed();
        final int padding = Math.max(0, conf.searchPadding());

        int searchMinX = chunkMinX - padding;
        int searchMaxX = chunkMaxX + padding;
        int searchMinZ = chunkMinZ - padding;
        int searchMaxZ = chunkMaxZ + padding;

        int minX = searchMinX;
        int maxX = searchMaxX;
        int minZ = searchMinZ;
        int maxZ = searchMaxZ;

        int sx = maxX - minX + 1;
        int sz = maxZ - minZ + 1;

        // defensive limit
        if (sx > 2048 || sz > 2048) return false;

        boolean[][] mask = new boolean[sx][sz];
        int[][] topY = new int[sx][sz];

        double scale = conf.noiseScale();
        int octaves = Math.max(1, conf.octaves());
        float threshold = conf.threshold();

        boolean foundAny = false;

        // Build mask and determine per-column target Y (prefer underground cavity floor)
        double debug_avg = 0;
        for (int ix = 0; ix < sx; ix++) {
            int wx = minX + ix;
            for (int iz = 0; iz < sz; iz++) {
                int wz = minZ + iz;

                int surfaceY = level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, wx, wz);

                // Search downward for a cavity air cell that is NOT sky-exposed; choose the floor (block below the air)
                int scanDepth = 48;
                int cavityAirY = Integer.MIN_VALUE;
                for (int d = 0; d < scanDepth; d++) {
                    int y = surfaceY - d;
                    if (y <= level.getMinY() + 2) break;
                    BlockPos p = new BlockPos(wx, y, wz);
                    if (level.getBlockState(p).isAir() && !level.canSeeSky(p)) {
                        cavityAirY = y;
                        break;
                    }
                }

                int useY;
                if (cavityAirY != Integer.MIN_VALUE) {
                    // choose floor of cavity (block below the air)
                    useY = Math.max(level.getMinY() + 2, cavityAirY - 1);
                } else {
                    useY = surfaceY;
                }

                BlockPos testPos = new BlockPos(wx, useY, wz);
                BlockState at = level.getBlockState(testPos);
                FluidState fs = level.getFluidState(testPos);

                // skip columns where chosen top is fluid/bedrock/forbidden
                if (!fs.isEmpty() || at.is(Blocks.BEDROCK) || at.is(BlockTags.FEATURES_CANNOT_REPLACE)) {
                    mask[ix][iz] = false;
                    topY[ix][iz] = useY;
                    continue;
                }

                topY[ix][iz] = useY;

                double n = peakPlateauNoise(worldSeed, wx * scale, useY * scale, wz * scale, octaves, conf.exponent(), conf.plateauThreshold(), conf.detailAmp());
                double norm = (n + 1.0) * 0.5;
                debug_avg += norm;
                if (norm >= threshold) {
                    mask[ix][iz] = true;
                    foundAny = true;
                } else {
                    mask[ix][iz] = false;
                }
            }
        }
        debug_avg = debug_avg/(sx * sz);
        DeltaV.LOGGER.debug("value: {} / {}", debug_avg, threshold);

        if (!foundAny) return false;

        // Flood-fill blobs
        boolean[][] visited = new boolean[sx][sz];
        List<int[]> blobs = new ArrayList<>();
        for (int ix = 0; ix < sx; ix++) {
            for (int iz = 0; iz < sz; iz++) {
                if (visited[ix][iz] || !mask[ix][iz]) continue;
                int minbx = ix, maxbx = ix, minbz = iz, maxbz = iz;
                List<int[]> q = new ArrayList<>();
                q.add(new int[]{ix, iz});
                visited[ix][iz] = true;
                int qi = 0;
                while (qi < q.size()) {
                    int[] cur = q.get(qi++);
                    int cx = cur[0], cz = cur[1];
                    minbx = Math.min(minbx, cx); maxbx = Math.max(maxbx, cx);
                    minbz = Math.min(minbz, cz); maxbz = Math.max(maxbz, cz);
                    for (Direction d : new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST}) {
                        int nx = cx + d.getStepX();
                        int nz = cz + d.getStepZ();
                        if (nx >= 0 && nx < sx && nz >= 0 && nz < sz && !visited[nx][nz] && mask[nx][nz]) {
                            visited[nx][nz] = true;
                            q.add(new int[]{nx, nz});
                        }
                    }
                }
                blobs.add(new int[]{minbx, minbz, maxbx, maxbz});
            }
        }

        boolean placedAny = false;

        for (int[] b : blobs) {
            int minbx = b[0], minbz = b[1], maxbx = b[2], maxbz = b[3];

            int expand = 2;
            int bx0 = Math.max(0, minbx - expand);
            int bz0 = Math.max(0, minbz - expand);
            int bx1 = Math.min(sx - 1, maxbx + expand);
            int bz1 = Math.min(sz - 1, maxbz + expand);

            // carveDepth grid sized to bounding box (store EFFECTIVE depth = sampled + 1)
            int cxw = bx1 - bx0 + 1;
            int czw = bz1 - bz0 + 1;
            if (cxw <= 0 || czw <= 0) continue;
            int[][] carveDepth = new int[cxw][czw];
            int maxCarveDepth = 0;

            for (int ix = bx0; ix <= bx1; ix++) {
                int wx = minX + ix;
                for (int iz = bz0; iz <= bz1; iz++) {
                    int wz = minZ + iz;
                    if (!mask[ix][iz]) { carveDepth[ix - bx0][iz - bz0] = 0; continue; }
                    int ty = topY[ix][iz];

                    double dn = peakPlateauNoise(worldSeed ^ 0x9E3779B97F4A7C15L, wx * scale * 1.7, ty * scale * 0.6, wz * scale * 1.7, Math.max(1, octaves - 1), conf.exponent(), conf.plateauThreshold(), conf.detailAmp());
                    double nd = (dn + 1.0) * 0.5;

                    long seed = computeSeed(worldSeed, wx, wz);
                    RandomSource r = RandomSource.create(seed);
                    int configMax = conf.maxDepth().sample(r); // original maxDepth

                    int sampled = Math.max(1, (int) Math.round(nd * (double) configMax));
                    sampled = Math.min(sampled, configMax);

                    // Effective depth = sampled + 1 so water starts one block lower than rim/top.
                    // Cap to configMax + 1 to avoid runaway.
                    int effective = Math.min(configMax + 1, sampled + 1);

                    carveDepth[ix - bx0][iz - bz0] = effective;
                    maxCarveDepth = Math.max(maxCarveDepth, effective);
                }
            }

            if (maxCarveDepth <= 0) continue;

            // Build carvedRanges for the bbox (world coords -> [top,bottom]) using EFFECTIVE depth
            Map<Long, int[]> carvedRanges = new HashMap<>();
            for (int ix = bx0; ix <= bx1; ix++) {
                int wx = minX + ix;
                for (int iz = bz0; iz <= bz1; iz++) {
                    int wz = minZ + iz;
                    int eff = carveDepth[ix - bx0][iz - bz0];
                    if (eff <= 0) continue;
                    int top = topY[ix][iz];                // original top (rim top)
                    int bottom = top - eff + 1;            // bottom of carved column (effective)
                    carvedRanges.put(pack(wx, wz), new int[]{top, bottom});
                }
            }

            // CARVE interior bottom-up (write only inside this chunk)
            // Contents should always start one block lower than the rim: contents cover bottom..(top-1)
            Set<BlockPos> placedContent = new HashSet<>();
            for (int ix = bx0; ix <= bx1; ix++) {
                int wx = minX + ix;
                for (int iz = bz0; iz <= bz1; iz++) {
                    int wz = minZ + iz;
                    int eff = carveDepth[ix - bx0][iz - bz0];
                    if (eff <= 0) continue;
                    int top = topY[ix][iz];
                    int bottom = top - eff + 1;

                    int contentsTop = top - 1; // ALWAYS one below rim/top

                    // ensure range is valid (should be because eff >= 2)
                    if (contentsTop < bottom) contentsTop = bottom;

                    for (int y = bottom; y <= contentsTop; y++) {
                        BlockPos pos = new BlockPos(wx, y, wz);
                        if (!isInsideChunk(pos.getX(), pos.getZ(), chunkMinX, chunkMinZ, chunkMaxX, chunkMaxZ)) continue;
                        if (pos.getY() <= level.getMinY()) continue;

                        BlockState cur = level.getBlockState(pos);
                        FluidState fcur = level.getFluidState(pos);

                        if (cur.is(Blocks.BEDROCK) || cur.is(BlockTags.FEATURES_CANNOT_REPLACE)) break;

                        Fluid targetFluid = conf.contents().getFluidState().getType();
                        if (!fcur.isEmpty() && (targetFluid == null || fcur.getType() != targetFluid)) break;

                        if (!isCarverReplaceable(cur, conf.contents())) continue;

                        // place contents bottom-up
                        this.setBlock(level, pos, conf.contents());
                        placedContent.add(pos);
                        markAboveForPostProcessing(level, pos);
                        DeltaV.LOGGER.debug("Check here mate: {} {} {}", pos.getX(), pos.getY(), pos.getZ());
                        placedAny = true;
                    }

                    // Make sure rim/top block (the original top) is air so user sees a lip above the contents.
                    BlockPos topPos = new BlockPos(wx, top, wz);
                    if (isInsideChunk(topPos.getX(), topPos.getZ(), chunkMinX, chunkMinZ, chunkMaxX, chunkMaxZ)) {
                        if (topPos.getY() > level.getMinY()) {
                            FluidState topFluid = level.getFluidState(topPos);
                            BlockState topCur = level.getBlockState(topPos);
                            if (topFluid.isEmpty() &&
                                !topCur.is(Blocks.BEDROCK) &&
                                !topCur.is(BlockTags.FEATURES_CANNOT_REPLACE) &&
                                (topCur.isAir() || isCarverReplaceable(topCur, Blocks.AIR.defaultBlockState()))) {
                                this.setBlock(level, topPos, Blocks.AIR.defaultBlockState());
                                level.scheduleTick(topPos, Blocks.AIR, 0);
                                markAboveForPostProcessing(level, topPos);
                                placedAny = true;
                            }
                        }
                    }
                }
            }

            // Compute rim candidates — per carved Y level, neighbor positions not carved at that y get rim/base candidates
            List<RimOp> rimOps = new ArrayList<>();
            for (Map.Entry<Long, int[]> e : carvedRanges.entrySet()) {
                long key = e.getKey();
                int[] range = e.getValue();
                int wx = unpackX(key);
                int wz = unpackZ(key);
                int top = range[0];
                int bottom = range[1];

                for (int y = bottom; y <= top; y++) {
                    for (Direction d : new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST}) {
                        int nx = wx + d.getStepX();
                        int nz = wz + d.getStepZ();

                        boolean neighborHasAtY = false;
                        int[] nr = carvedRanges.get(pack(nx, nz));
                        if (nr != null) {
                            if (y >= nr[1] && y <= nr[0]) neighborHasAtY = true;
                        } else {
                            // fallback — derive from mask + carveDepth if available
                            int localX = nx - minX;
                            int localZ = nz - minZ;
                            if (localX >= 0 && localX < sx && localZ >= 0 && localZ < sz) {
                                int nTop = topY[localX][localZ];
                                int nxCarveIdx = localX - bx0;
                                int nzCarveIdx = localZ - bz0;
                                int nDepth = 0;
                                if (nxCarveIdx >= 0 && nxCarveIdx < carveDepth.length && nzCarveIdx >= 0 && nzCarveIdx < carveDepth[0].length) {
                                    nDepth = carveDepth[nxCarveIdx][nzCarveIdx];
                                }
                                if (nDepth > 0) {
                                    int nBottom = nTop - nDepth + 1;
                                    if (y >= nBottom && y <= nTop) neighborHasAtY = true;
                                }
                            }
                        }

                        if (neighborHasAtY) continue;

                        BlockPos rimPos = new BlockPos(nx, y, nz);
                        // skip if rim position has fluid / bedrock / forbidden
                        if (!level.getFluidState(rimPos).isEmpty()) continue;
                        BlockState cur = level.getBlockState(rimPos);
                        if (!cur.isAir() && !isCarverReplaceable(cur, conf.rim())) continue;

                        rimOps.add(new RimOp(rimPos, true));
                    }
                }
            }
            for (int cx = bx0; cx < bx1; cx++) {
                for (int cz = bz0; cz < bz1; cz++) {
                    int depth = carveDepth[cx - bx0][cz - bz0];
                    if (depth <= 0) continue;

                    int top = topY[cx][cz];
                    int bottom = top - depth + 1;
                    BlockPos floorPos = new BlockPos(minX + cx, bottom - 1, minZ + cz);

                    BlockState cur = level.getBlockState(floorPos);
                    if (cur.isAir() || isCarverReplaceable(cur, conf.base())) {
                        rimOps.add(new RimOp(floorPos, true)); // mark as base
                    }
                }
            }
            
            // Deduplicate rimOps using safe string keys
            Map<String, Boolean> rimMap = new HashMap<>();
            for (RimOp op : rimOps) {
                String k = keyFor(op.pos);
                Boolean prev = rimMap.get(k);
                if (prev == null) rimMap.put(k, op.isBase);
                else if (prev && !op.isBase) rimMap.put(k, false);
            }

            // Convert rimMap to list and sort by y asc
            List<RimEntry> rimList = new ArrayList<>();
            for (Map.Entry<String, Boolean> en : rimMap.entrySet()) {
                BlockPos p = posFromKey(en.getKey());
                rimList.add(new RimEntry(p, en.getValue()));
            }
            rimList.sort(Comparator.comparingInt(a -> a.pos.getY()));

            // Place rim bottom-up; use placedRimPositions to allow higher placements to rely on lower ones as support.
            Set<String> placedRimPositions = new HashSet<>();
            final int SUPPORT_SEARCH_LIMIT = 2;

            for (RimEntry re : rimList) {
                BlockPos pos = re.pos;
                if (!isInsideChunk(pos.getX(), pos.getZ(), chunkMinX, chunkMinZ, chunkMaxX, chunkMaxZ)) continue;

                // skip if there is an existing fluid at this pos (don't place rim over ocean)
                //if (!level.getFluidState(pos).isEmpty()) continue;

                BlockState cur = level.getBlockState(pos);
                BlockState toPlace = re.isBase ? conf.base() : conf.rim();

                if (!cur.isAir() && !isCarverReplaceable(cur, toPlace)) continue;
                if (!isNotRoof(pos, level)) continue;

                // check support chain and optionally place base blocks under air to create support (but avoid building into fluids)
                BlockPos scan = pos.below();
                int steps = 0;
                boolean hitFluid = false;
                boolean foundSolidSupport = false;
                List<BlockPos> toFillBelow = new ArrayList<>();

                while (steps < SUPPORT_SEARCH_LIMIT && scan.getY() > level.getMinY()) {
                    BlockState sCur = level.getBlockState(scan);
                    FluidState sFluid = level.getFluidState(scan);

                    if (!sFluid.isEmpty()) { hitFluid = true; break; }

                    if (!sCur.isAir()) {
                        foundSolidSupport = true;
                        break;
                    } else {
                        toFillBelow.add(scan);
                    }

                    scan = scan.below();
                    steps++;
                }

                if (hitFluid) continue;
                if (!foundSolidSupport && steps >= SUPPORT_SEARCH_LIMIT) continue;

                // Place required support base blocks bottom-up (but only inside this chunk)
                for (int i = toFillBelow.size() - 1; i >= 0; i--) {
                    BlockPos bp = toFillBelow.get(i);
                    if (!isInsideChunk(bp.getX(), bp.getZ(), chunkMinX, chunkMinZ, chunkMaxX, chunkMaxZ)) {
                        continue; // don't write outside this chunk
                    }
                    if (!level.getFluidState(bp).isEmpty()) { hitFluid = true; break; }
                    BlockState curB = level.getBlockState(bp);
                    if (curB.is(BlockTags.FEATURES_CANNOT_REPLACE) || curB.is(Blocks.BEDROCK)) { hitFluid = true; break; }
                    if (curB.isAir() || isCarverReplaceable(curB, conf.base())) {
                        this.setBlock(level, bp, conf.base());
                        markAboveForPostProcessing(level, bp);
                        placedAny = true;
                        placedRimPositions.add(keyFor(bp));
                    }
                }
                if (hitFluid) continue;

                // Finally place the rim/base at pos
                if (pos.getY() <= level.getMinY()) continue;
                if (!level.getFluidState(pos).isEmpty() && !placedContent.contains(pos)) continue;
                BlockState currentNow = level.getBlockState(pos);
                if (!currentNow.isAir() && !isCarverReplaceable(currentNow, toPlace)) continue;

                if (!re.isBase) {
                    BlockPos above = pos.above();
                    Boolean check = placedContent.contains(above);
                    toPlace = check ? conf.base() : conf.rim();
                    this.setBlock(level, pos, toPlace);
                    placedRimPositions.add(keyFor(pos));
                    pos = pos.above();
                    while (placedContent.contains(pos)) {
                        above = pos.above();
                        check = placedContent.contains(above);
                        toPlace = check ? conf.base() : conf.rim();
                        this.setBlock(level, pos, toPlace);
                        placedRimPositions.add(keyFor(pos));
                        pos = above;
                    }
                    markAboveForPostProcessing(level, pos);
                }
                else {
                    this.setBlock(level, pos, toPlace);
                    markAboveForPostProcessing(level, pos);
                    placedRimPositions.add(keyFor(pos));
                }
                placedAny = true;
            }
        } // end blobs

        return placedAny;
    } // end place()

    // ---------------- small helper types ----------------
    private static class RimOp {
        final BlockPos pos;
        final boolean isBase;
        RimOp(BlockPos p, boolean b) { pos = p; isBase = b; }
    }
    private static class RimEntry {
        final BlockPos pos;
        final boolean isBase;
        RimEntry(BlockPos p, boolean b) { pos = p; isBase = b; }
    }

    // ---------------- noise / utilities ----------------

    // --- peak-plateau noise that returns [0..1] ---
    private static double peakPlateauNoise(long seed, double x, double y, double z, int octaves, double exponent, double plateauThreshold, double detailAmp) {
        // Parameters you can tweak:
        final double plateauBlend = 0.9;    // how strongly to snap to plateau (0..1)
        final double detailScale = 3.5;     // how much smaller the detail frequency is relative to base

        // base (low-frequency) noise in [-1..1]
        double baseRaw = valueNoise3D_raw(seed, x, y, z, Math.max(1, Math.min(octaves, 2)));
        double base01 = (baseRaw + 1.0) * 0.5; // -> [0..1]

        // bias toward extremes: raises abs to exponent while preserving sign via base01 being 0..1
        double biased = Math.pow(base01, exponent); // small values shrink, large get closer to 1

        // plateau shaping: smoothly push biased values above threshold toward 1
        double plateauFactor = smoothStep(plateauThreshold, 1.0, biased);
        double baseVal = lerp(biased, 1.0, Math.pow(plateauFactor, plateauBlend)); // biased -> more plateau near top

        // high-frequency detail, sampled at higher frequency, [-1..1] -> [0..1]
        double detailRaw = valueNoise3D_raw(seed ^ 0x9E3779B97F4A7C15L, x * detailScale, y * detailScale, z * detailScale, Math.max(1, octaves));
        double detail01 = (detailRaw + 1.0) * 0.5;

        // Blend detail in proportionally outside plateaus (so plateau centers stay flat)
        double finalVal = baseVal * (1.0 - detailAmp) + detail01 * detailAmp * (1.0 - baseVal);

        // clamp
        if (finalVal < 0.0) finalVal = 0.0;
        if (finalVal > 1.0) finalVal = 1.0;
        return finalVal;
    }

    // --- keep a raw bipolar value-noise ([-1..1]) used by the peakPlateau code ---
    // This is similar to your old valueNoise3D but returns [-1..1].
    private static double valueNoise3D_raw(long seed, double x, double y, double z, int octaves) {
        double total = 0.0;
        double amp = 1.0;
        double freq = 1.0;
        double maxAmp = 0.0;

        for (int o = 0; o < Math.max(1, octaves); o++) {
            total += valueNoiseSingle_raw(seed + o * 0x9E3779B97F4A7C15L, x * freq, y * freq, z * freq) * amp;
            maxAmp += amp;
            amp *= 0.5;
            freq *= 2.0;
        }

        // Normalize back into [-1..1]
        return total / maxAmp;
    }

    /** single octave value-noise in [-1..1] (trilinear interp of corner values in [-1..1]) */
    private static double valueNoiseSingle_raw(long seed, double x, double y, double z) {
        int x0 = fastFloor(x), y0 = fastFloor(y), z0 = fastFloor(z);
        double xf = x - x0, yf = y - y0, zf = z - z0;
        double u = fade(xf), v = fade(yf), w = fade(zf);

        double c000 = randomCorner(seed, x0, y0, z0);
        double c100 = randomCorner(seed, x0 + 1, y0, z0);
        double c010 = randomCorner(seed, x0, y0 + 1, z0);
        double c110 = randomCorner(seed, x0 + 1, y0 + 1, z0);
        double c001 = randomCorner(seed, x0, y0, z0 + 1);
        double c101 = randomCorner(seed, x0 + 1, y0, z0 + 1);
        double c011 = randomCorner(seed, x0, y0 + 1, z0 + 1);
        double c111 = randomCorner(seed, x0 + 1, y0 + 1, z0 + 1);

        double x00 = lerp(c000, c100, u);
        double x10 = lerp(c010, c110, u);
        double x01 = lerp(c001, c101, u);
        double x11 = lerp(c011, c111, u);

        double y0v = lerp(x00, x10, v);
        double y1v = lerp(x01, x11, v);

        return lerp(y0v, y1v, w);
    }

    /** corner pseudorandom value in [-1..1] */
    private static double randomCorner(long seed, int xi, int yi, int zi) {
        long h = mix(seed, xi, yi, zi);
        RandomSource r = RandomSource.create(h);
        return r.nextDouble() * 2.0 - 1.0;
    }

    // ---------------- small helpers ----------------
    private static int fastFloor(double v) { int i = (int) v; return (v < i) ? i - 1 : i; }
    private static double fade(double t) { return t * t * (3.0 - 2.0 * t); }
    private static double lerp(double a, double b, double t) { return a + (b - a) * t; }
    private static long mix(long seed, int xi, int yi, int zi) {
        long h = seed;
        h ^= (long) xi * 0x9E3779B97F4A7C15L;
        h ^= (long) yi * 0xC2B2AE3D27D4EB4FL;
        h ^= (long) zi * 0x165667B19E3779F9L;
        h = (h ^ (h >>> 30)) * 0xBF58476D1CE4E5B9L;
        h = (h ^ (h >>> 27)) * 0x94D049BB133111EBL;
        return h ^ (h >>> 31);
    }

    /** smoothstep( edge0, edge1, x ) clamped */
    private static double smoothStep(double edge0, double edge1, double x) {
        if (x <= edge0) return 0.0;
        if (x >= edge1) return 1.0;
        double t = (x - edge0) / (edge1 - edge0);
        return t * t * (3.0 - 2.0 * t);
    }

    private static long computeSeed(long worldSeed, int x, int z) {
        long l = worldSeed;
        l ^= (long) x * 341873128712L + (long) z * 132897987541L;
        l = l * 6364136223846793005L + 1442695040888963407L;
        return l;
    }

    // pack/unpack for (x,z) pair (safe for negatives)
    private static long pack(int x, int z) {
        return (((long) x & 0xFFFFFFFFL) << 32) | ((long) z & 0xFFFFFFFFL);
    }
    private static int unpackX(long p) { return (int) (p >>> 32); }
    private static int unpackZ(long p) { return (int) p; }

    // simple string key helpers for xyz dedupe (negatives handled naturally)
    private static String keyFor(BlockPos p) { return p.getX() + "," + p.getY() + "," + p.getZ(); }
    private static BlockPos posFromKey(String k) {
        String[] s = k.split(",");
        int x = Integer.parseInt(s[0]);
        int y = Integer.parseInt(s[1]);
        int z = Integer.parseInt(s[2]);
        return new BlockPos(x, y, z);
    }

    private static boolean isInsideChunk(int x, int z, int chunkMinX, int chunkMinZ, int chunkMaxX, int chunkMaxZ) {
        return x >= chunkMinX && x <= chunkMaxX && z >= chunkMinZ && z <= chunkMaxZ;
    }

    // broadened replaceable logic — tweak if you want more/less aggressive conversion
    private boolean isCarverReplaceable(BlockState state, BlockState replacement) {
        if (state.is(replacement.getBlock())) return true;
        if (state.is(Blocks.ICE) || state.is(Blocks.PACKED_ICE) || state.is(Blocks.BLUE_ICE) || state.is(Blocks.FROSTED_ICE)
                || state.is(Blocks.SNOW_BLOCK)) return false;
        if (state.is(BlockTags.FEATURES_CANNOT_REPLACE)) return false;
        if (state.is(BlockTags.OVERWORLD_CARVER_REPLACEABLES)) return true;
        if (state.is(BlockTags.BASE_STONE_OVERWORLD)) return true;
        if (state.is(Blocks.DIRT) || state.is(Blocks.GRASS_BLOCK)
                || state.is(Blocks.GRAVEL) || state.is(Blocks.SAND) || state.is(Blocks.CLAY)) return true;
        return false;
    }

    private boolean isNotRoof(BlockPos pos, WorldGenLevel level) {
        if (level.canSeeSky(pos)) {
            if (level.getBlockState(pos.below()).is(Blocks.AIR)) return false;
        }
        return true;
    }
}
