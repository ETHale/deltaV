package com.deltav.deltavmod.block.energy.cable;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/*
 * Used to calculate the possible shapes of cables 
 * 
 * credit to: https://www.mcjty.eu/docs/1.20.4_neo/ep5
 */
public class CableShapeCache {
    private static ConcurrentMap<Integer, VoxelShape> shapeCache = new ConcurrentHashMap<Integer, VoxelShape>();

    private static final VoxelShape SHAPE_CABLE_NORTH = Shapes.box(.4, .4, 0, .6, .6, .4);
    private static final VoxelShape SHAPE_CABLE_SOUTH = Shapes.box(.4, .4, .6, .6, .6, 1);
    private static final VoxelShape SHAPE_CABLE_WEST = Shapes.box(0, .4, .4, .4, .6, .6);
    private static final VoxelShape SHAPE_CABLE_EAST = Shapes.box(.6, .4, .4, 1, .6, .6);
    private static final VoxelShape SHAPE_CABLE_UP = Shapes.box(.4, .6, .4, .6, 1, .6);
    private static final VoxelShape SHAPE_CABLE_DOWN = Shapes.box(.4, 0, .4, .6, .4, .6);

    private static final VoxelShape SHAPE_BLOCK_NORTH = Shapes.box(.3, .3, 0, .7, .7, .1);
    private static final VoxelShape SHAPE_BLOCK_SOUTH = Shapes.box(.3, .3, .9, .7, .7, 1);
    private static final VoxelShape SHAPE_BLOCK_WEST = Shapes.box(0, .3, .3, .1, .7, .7);
    private static final VoxelShape SHAPE_BLOCK_EAST = Shapes.box(.9, .3, .3, 1, .7, .7);
    private static final VoxelShape SHAPE_BLOCK_UP = Shapes.box(.3, .9, .3, .7, 1, .7);
    private static final VoxelShape SHAPE_BLOCK_DOWN = Shapes.box(.3, 0, .3, .7, .1, .7);

    private static int calculateShapeIndex(ConnectorType north, ConnectorType south, ConnectorType west, ConnectorType east, ConnectorType up, ConnectorType down) {
        int l = ConnectorType.values().length;
        return ((((south.ordinal() * l + north.ordinal()) * l + west.ordinal()) * l + east.ordinal()) * l + up.ordinal()) * l + down.ordinal();
    }

    public static VoxelShape getCachedCableShape(ConnectorType north, ConnectorType south, ConnectorType west, ConnectorType east, ConnectorType up, ConnectorType down) {
        int index = calculateShapeIndex(north, south, west, east, up, down);
        return shapeCache.computeIfAbsent(index, i -> makeShape(north, south, west, east, up, down));
    }

    private static VoxelShape makeShape(ConnectorType north, ConnectorType south, ConnectorType west, ConnectorType east, ConnectorType up, ConnectorType down) {
        VoxelShape shape = Shapes.box(.4, .4, .4, .6, .6, .6);
        shape = combineShape(shape, north, SHAPE_CABLE_NORTH, SHAPE_BLOCK_NORTH);
        shape = combineShape(shape, south, SHAPE_CABLE_SOUTH, SHAPE_BLOCK_SOUTH);
        shape = combineShape(shape, west, SHAPE_CABLE_WEST, SHAPE_BLOCK_WEST);
        shape = combineShape(shape, east, SHAPE_CABLE_EAST, SHAPE_BLOCK_EAST);
        shape = combineShape(shape, up, SHAPE_CABLE_UP, SHAPE_BLOCK_UP);
        shape = combineShape(shape, down, SHAPE_CABLE_DOWN, SHAPE_BLOCK_DOWN);
        return shape;
    }
    
    private static VoxelShape combineShape(VoxelShape shape, ConnectorType connectorType, VoxelShape cableShape, VoxelShape blockShape) {
        if (connectorType == ConnectorType.CABLE) {
            return Shapes.join(shape, cableShape, BooleanOp.OR);
        } else if (connectorType == ConnectorType.BLOCK) {
            return Shapes.join(shape, Shapes.join(blockShape, cableShape, BooleanOp.OR), BooleanOp.OR);
        } else {
            return shape;
        }
    }
}
