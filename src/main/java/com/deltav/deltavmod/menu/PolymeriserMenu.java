package com.deltav.deltavmod.menu;

import java.util.List;

import com.deltav.deltavmod.block.entity.PolymeriserBlockEntity;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.SlotItemHandler;

/**
 * Menu for the polymeriser block. Handles data synchronization between client and server.
 * 
 * @author Adam Crawley
 */
public class PolymeriserMenu extends AbstractContainerMenu {
    private final PolymeriserBlockEntity be;
    private final ContainerData data;

    public PolymeriserMenu(int id, Inventory inv, FriendlyByteBuf buf) {
        this(id, inv, (PolymeriserBlockEntity) inv.player.level().getBlockEntity(buf.readBlockPos()),
            new SimpleContainerData(PolymeriserBlockEntity.INTERNAL_DATA_COUNT));
    }
    public PolymeriserMenu(int id, Inventory inv, PolymeriserBlockEntity be, ContainerData data) {
        super(ModMenus.POLYMERISER_MENU.get(), id);
        this.be = be;
        this.data = data;

        // player inv
        addStandardInventorySlots(inv, 8, 84);

        addSlot(new SlotItemHandler(be.inventory, 0, 22, 26)); // NAPHTHA_IN_SLOT
        addSlot(new SlotItemHandler(be.inventory, 1, 22, 44)); // WATER_IN_SLOT
        addSlot(new SlotItemHandler(be.inventory, 2, 97, 49)); // CATALYST_SLOT
        addSlot(new SlotItemHandler(be.inventory, 3, 126, 25)); // PLASTIC_OUT_SLOT

        addDataSlots(data); // Stores progress values
    }

    public int getScaledArrowProgress(int arrowLength) {
        int progress = this.data.get(4);
        int maxProgress = this.data.get(5);
        return maxProgress != 0 ? progress * arrowLength / maxProgress : 0;
    }

    /**
     * Gets the scaled tank height.
     *
     * @param tank         Tank index (naphtha = 0, water = 1)
     * @param maxBarHeight Maximum height in pixels of the tank bar
     * @return Scaled height of the tank
     */
    public int getScaledTankHeight(int tank, int maxBarHeight) {
        int fluidAmount = this.data.get(tank);
        int tankCapacity = this.data.get(2 + tank);
        return tankCapacity != 0 ? fluidAmount * maxBarHeight / tankCapacity : 0;
    }

    /**
     * Gets the tooltip for the specified tank.
     *
     * @param tank Tank index (naphtha = 0, water = 1)
     * @return Tooltip for the tank
     */
    public List<Component> getTankTooltip(int tank) {
        int fluidAmount = this.data.get(tank);
        int tankCapacity = this.data.get(2 + tank);
        return List.of(Component.literal(fluidAmount + " / " + tankCapacity + " mB"));
    }

    @Override
    public boolean stillValid(Player p) {
        return be.getLevel().getBlockEntity(be.getBlockPos()) == be;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }
}
