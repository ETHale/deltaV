package com.deltav.deltavmod.worldgen.tree;

import java.util.Optional;

import com.deltav.deltavmod.DeltaV;
import com.deltav.deltavmod.worldgen.DeltaVConfiguredFeatures;

import net.minecraft.world.level.block.grower.TreeGrower;

public class DeltaVTreeGrowers {
    public static final TreeGrower RUBBER_TREE = new TreeGrower(
        DeltaV.MODID + ":rubber_tree",
        Optional.empty(),
        Optional.of(DeltaVConfiguredFeatures.RUBBER_TREE),
        Optional.empty()
    );
}
