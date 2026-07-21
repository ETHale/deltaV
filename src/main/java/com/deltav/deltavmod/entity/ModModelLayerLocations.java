package com.deltav.deltavmod.entity;

import com.deltav.deltavmod.DeltaV;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.Identifier;

public class ModModelLayerLocations {
    public static final ModelLayerLocation RUBBERWOOD_BOAT = new ModelLayerLocation( 
        Identifier.fromNamespaceAndPath(DeltaV.MODID, "boat/rubberwood"), 
        "main");
    public static final ModelLayerLocation RUBBERWOOD_CHEST_BOAT = new ModelLayerLocation( 
        Identifier.fromNamespaceAndPath(DeltaV.MODID, "chest_boat/rubberwood"), 
        "main");
}
