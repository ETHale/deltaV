package com.deltav.deltavmod.block.energy.batteries;

import net.minecraft.core.Direction;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.energy.SimpleEnergyHandler;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;


// custom class to make some stuff easier
public class SmartEnergyStorage extends SimpleEnergyHandler {
    private final Direction[] outputSides;

    public SmartEnergyStorage(int capacity, int maxTransfer, int maxExtract, Direction[] outputSides) {
        super(capacity, maxTransfer, maxExtract);
        this.outputSides = outputSides;
    }

    /*
     * Returns an EnergyHandler with certain access depending on the side
     * Set side to null for full access.
     */
    public EnergyHandler getEnergyStorage(Direction side) {
        if (side == null) return this; // full access for side-less queries

        boolean extractAllowed = isOutputSide(side);
        boolean receiveAllowed = !extractAllowed;

        if (!extractAllowed && !receiveAllowed) return null;

        return new SideFilteredView(extractAllowed, receiveAllowed);
    }

    private boolean isOutputSide(Direction side) {
        for (Direction d : outputSides) if (d == side) return true;
        return false;
    }
    
    private final class SideFilteredView implements EnergyHandler {
        private final boolean allowExtract;
        private final boolean allowReceive;

        SideFilteredView(boolean allowExtract, boolean allowReceive) {
            this.allowExtract = allowExtract;
            this.allowReceive = allowReceive;
        }

        @Override
        public int insert(int maxReceive, TransactionContext tr) {
            return allowReceive ? SmartEnergyStorage.this.insert(maxReceive, tr) : 0;
        }

        @Override
        public int extract(int maxExtract, TransactionContext tr) {
            return allowExtract ? SmartEnergyStorage.this.extract(maxExtract, tr) : 0;
        }

        @Override
        public long getAmountAsLong() {
            return SmartEnergyStorage.this.getAmountAsLong();
        }

        @Override
        public long getCapacityAsLong() {
            return SmartEnergyStorage.this.getCapacityAsLong();
        }
    }
}
