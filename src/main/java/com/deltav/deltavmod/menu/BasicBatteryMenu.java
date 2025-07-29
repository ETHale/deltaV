package com.deltav.deltavmod.menu;

import com.deltav.deltavmod.block.entity.BasicBatteryBlockEntity;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.item.ItemStack;

public class BasicBatteryMenu extends AbstractContainerMenu {
    private final BasicBatteryBlockEntity be;

    // Synced to client via DataSlots
    private int energyStored = 0;
    private int capacity = 0;

    public BasicBatteryMenu(int id, Inventory inv, FriendlyByteBuf buf) {
        this(id, inv, (BasicBatteryBlockEntity) inv.player.level().getBlockEntity(buf.readBlockPos()));
    }
    public BasicBatteryMenu(int id, Inventory inv, BasicBatteryBlockEntity be) {
        super(ModMenus.BASIC_BATTERY_MENU.get(), id);
        this.be = be;

        // player inv
        addStandardInventorySlots(inv, 8, 84);

        addDataSlot(new DataSlot() {
            @Override public int get(){ return be.getEnergyStorage(null).getEnergyStored(); }
            @Override public void set(int v){ energyStored = v; }
        });
        addDataSlot(new DataSlot() {
            @Override public int get(){ return be.getEnergyStorage(null).getMaxEnergyStored(); }
            @Override public void set(int v){ capacity = v; }
        });
    }

    public int getEnergyStored() {
        return energyStored;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public boolean stillValid(Player p) {
        return be.getLevel().getBlockEntity(be.getBlockPos()) == be;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    public BasicBatteryBlockEntity getBlockEntity() { return be; }
}
