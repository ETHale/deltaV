package com.deltav.deltavmod.worldgen.tree;

import java.util.Optional;

import com.deltav.deltavmod.DeltaV;
import com.deltav.deltavmod.worldgen.DeltaVConfiguredFeatures;

import net.minecraft.world.level.block.grower.TreeGrower;

public class DeltaVTreeGrowers {
    public static final TreeGrower RUBBERWOODTREE = new TreeGrower(
        DeltaV.MODID + ":rubberwood_tree",
        Optional.empty(),
        Optional.of(DeltaVConfiguredFeatures.RUBBERWOODTREE),
        Optional.empty()
    );
}
