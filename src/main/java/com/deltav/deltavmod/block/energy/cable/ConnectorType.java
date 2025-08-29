package com.deltav.deltavmod.block.energy.cable;

import javax.annotation.Nonnull;

import net.minecraft.util.StringRepresentable;

/*
 * Used to determine the connection type of a cable end
 * 
 * credit to: https://www.mcjty.eu/docs/1.20.4_neo/ep5
 */
public enum ConnectorType implements StringRepresentable {
    NONE,
    CABLE,
    BLOCK;

    public static final ConnectorType[] VALUES = values();

    @Override
    @Nonnull
    public String getSerializedName() {
        return name().toLowerCase();
    }
    
}
