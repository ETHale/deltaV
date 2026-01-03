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
        Set<BlockPos> poolArea = createSunkenGround(worldgenlevel, origin, radius, depth, conf);

        // Waterlog the area
        waterlogArea(worldgenlevel, poolArea, conf);

        // Place a geyser
        placeGeyser(worldgenlevel, poolArea, conf);

        return true;
    }

    private Set<BlockPos> createSunkenGround(WorldGenLevel level, BlockPos origin, int radius, int depth, HotSpringFeatureConfiguration conf) {
        Set<BlockPos> poolArea = new HashSet<>();
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        for (BlockPos pos : BlockPos.betweenClosed(origin.offset(-radius, -radius, -radius), origin.offset(radius, radius, radius))) {
            int dx = pos.getX() - origin.getX();
            int dz = pos.getZ() - origin.getZ();
            if (dx * dx + dz * dz <= radius * radius) {
                for (int y = 0; y < depth; y++) {
                    mutablePos.set(pos.getX(), origin.getY() - y, pos.getZ());
                    if (canReplaceBlock(level.getBlockState(mutablePos))) {
                        level.setBlock(mutablePos, conf.barrier(), 2);
                        if (y == depth - 1) {
                            poolArea.add(mutablePos.immutable());
                        }
                    }
                }
            }
        }

        return poolArea;
    }

    private void waterlogArea(WorldGenLevel level, Set<BlockPos> poolArea, HotSpringFeatureConfiguration conf) {
        for (BlockPos pos : poolArea) {
            level.setBlock(pos, conf.contents().createLegacyBlock(), 2);
        }
    }

    private void placeGeyser(WorldGenLevel level, Set<BlockPos> poolArea, HotSpringFeatureConfiguration conf) {
        if (poolArea.isEmpty()) return;

        BlockPos[] positions = poolArea.toArray(new BlockPos[0]);
        BlockPos geyserPos = positions[level.getRandom().nextInt(positions.length)];
        level.setBlock(geyserPos.above(), conf.geyser(), 2);
    }

    private boolean canReplaceBlock(BlockState state) {
        return !state.is(BlockTags.FEATURES_CANNOT_REPLACE);
    }
}
