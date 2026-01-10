package com.deltav.deltavmod.worldgen.features;

import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableFloat;

import com.deltav.deltavmod.block.ModBlocks;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
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
        int initialRadius = random.nextIntBetweenInclusive(1, 6);
        float radiusIncrement = 0.1f + random.nextFloat() * 0.015f; // How quickly radius grows
        MutableFloat health = new MutableFloat(80 + random.nextFloat() * 300f); // how quickly it starts to fizzle out
        MutableBoolean reachedSurface = new MutableBoolean(false);

        int heightLimit = level.getMaxY(); // Prevents infinite loop
        int maxSteps = heightLimit - y;

        BlockPos.MutableBlockPos center = new BlockPos.MutableBlockPos();
        float dx = 0f, dz = 0f; // Directional drift for natural wandering

        for (int step = 0; step < maxSteps && !reachedSurface.booleanValue() && health.getValue() > 0; y++, step++) {
            dx += (random.nextFloat() - 0.5f) * 0.8f;
            dz += (random.nextFloat() - 0.5f) * 0.8f;
            
            int centerX = origin.getX() + Math.round(dx);
            int centerZ = origin.getZ() + Math.round(dz);
            float currentRadius = initialRadius + step * radiusIncrement;
            int r = (int) Math.ceil(currentRadius);

            center.set(centerX, y, centerZ);

            health.setValue(health.getValue() - random.nextFloat() * 5F);
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
                        }
                        float edgeBias = (float)(Math.sqrt(dist2) / currentRadius);
                        placeBlock(context, level, pos, state, reachedSurface.booleanValue(), health.getValue(), edgeBias, random);
                        this.markAboveForPostProcessing((WorldGenLevel) level, pos);
                    }
                }
            );
        }
        return true;
    }

    private void placeBlock(
            FeaturePlaceContext<NoneFeatureConfiguration> context, 
            LevelAccessor level, 
            BlockPos pos, 
            BlockState state, 
            boolean reachedSurface, 
            float health, 
            float edgeBias, 
            RandomSource random
        ) {
        // we also don't want to replace grass blocks on the surface
        // and don't change liquids
        if (reachedSurface && (state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.MYCELIUM))
            || state.is(Blocks.LAVA) || state.is(Blocks.WATER)
        )
            return;

        // ORES
        if (state.is(Blocks.COAL_ORE) || state.is(Blocks.DEEPSLATE_COAL_ORE))
            setBlock(level, pos, ModBlocks.KIMBERLITE_COAL_ORE.get().defaultBlockState());
        
        else if (state.is(Blocks.COPPER_ORE) || state.is(Blocks.DEEPSLATE_COPPER_ORE))
            setBlock(level, pos, ModBlocks.KIMBERLITE_COPPER_ORE.get().defaultBlockState());

        else if (state.is(Blocks.DIAMOND_ORE) || state.is(Blocks.DEEPSLATE_DIAMOND_ORE))
            setBlock(level, pos, ModBlocks.KIMBERLITE_DIAMOND_ORE.get().defaultBlockState());

        else if (state.is(Blocks.EMERALD_ORE) || state.is(Blocks.DEEPSLATE_EMERALD_ORE))
            setBlock(level, pos, ModBlocks.KIMBERLITE_EMERALD_ORE.get().defaultBlockState());

        else if (state.is(Blocks.GOLD_ORE) || state.is(Blocks.DEEPSLATE_GOLD_ORE))
            setBlock(level, pos, ModBlocks.KIMBERLITE_GOLD_ORE.get().defaultBlockState());

        else if (state.is(Blocks.IRON_ORE) || state.is(Blocks.DEEPSLATE_IRON_ORE))
            setBlock(level, pos, ModBlocks.KIMBERLITE_IRON_ORE.get().defaultBlockState());

        else if (state.is(Blocks.LAPIS_ORE) || state.is(Blocks.DEEPSLATE_LAPIS_ORE))
            setBlock(level, pos, ModBlocks.KIMBERLITE_LAPIS_ORE.get().defaultBlockState());

        else if (state.is(Blocks.REDSTONE_ORE) || state.is(Blocks.DEEPSLATE_REDSTONE_ORE))
            setBlock(level, pos, ModBlocks.KIMBERLITE_REDSTONE_ORE.get().defaultBlockState());

        else if (state.is(ModBlocks.ZINC_ORE.get()) || state.is(ModBlocks.DEEPSLATE_ZINC_ORE.get()))
            setBlock(level, pos, ModBlocks.KIMBERLITE_ZINC_ORE.get().defaultBlockState());

        else if (state.is(Blocks.BEDROCK)) {
            float blockSelection = random.nextFloat();
            if (blockSelection < 0.05f)
                setBlock(level, pos, ModBlocks.MOLTEN_BEDROCK.get().defaultBlockState());
        }
        
        // we need to determine what blocks to place and where
        // as we run low of health and near the top we want to size fizzleing out - but more likely to fizzle out in the center
        //    (makes a crater)
        else if (state.is(BlockTags.OVERWORLD_CARVER_REPLACEABLES)) {
            if (health < 15F) {
                // Blocks near the center (edgeBias close to 0) are more likely to fizzle out
                float craterChance = Mth.clamp((1.0f - edgeBias) * (1.0f - (health / 15f)), 0f, 1f); // High near center, low near edge
                if (random.nextFloat() < craterChance) {
                    return;
                }
            }

            int worldMinY = context.chunkGenerator().getMinY();
            int worldMaxY = level.getMaxY();
            float depth = (float)(pos.getY() - worldMinY) / (worldMaxY - worldMinY); // 0.0 = bottom, 1.0 = top
            float blockSelection = random.nextFloat();
            BlockState filler = ModBlocks.KIMBERLITE.get().defaultBlockState();
            if (depth < 0.02f) { // can only get diamond really near the bottom - not entirely accurate but game design
                if (blockSelection < 0.005f) {
                    setBlock(level, pos, Blocks.MAGMA_BLOCK.defaultBlockState());
                } else if (blockSelection < 0.01f) {
                    setBlock(level, pos, Blocks.OBSIDIAN.defaultBlockState());
                } else if (blockSelection < 0.015f) {
                    setBlock(level, pos, ModBlocks.KIMBERLITE_DIAMOND_ORE.get().defaultBlockState());
                } else {
                    setBlock(level, pos, filler);
                }
            } else if (depth < 0.1f) {
                if (blockSelection < 0.002f) {
                    setBlock(level, pos, Blocks.MAGMA_BLOCK.defaultBlockState());
                } else if (blockSelection < 0.007f) {
                    setBlock(level, pos, Blocks.OBSIDIAN.defaultBlockState());
                } else {
                    setBlock(level, pos, filler);
                }
            } else {
                // Upper layers — more coal
                if (blockSelection < 0.0015f) {
                    setBlock(level, pos, ModBlocks.KIMBERLITE_COAL_ORE.get().defaultBlockState());
                } else {
                    setBlock(level, pos, filler);
                }
            }
        }
        return;
    }
}
