package com.deltav.deltavmod.menu;

import java.util.List;

import com.deltav.deltavmod.block.entity.FractionatorBlockEntity;

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
 * Menu for the fractionator block. Handles data synchronization between client and server.
 * 
 * @author Adam Crawley
 */
public class FractionatorMenu extends AbstractContainerMenu {
    private final FractionatorBlockEntity be;
    private final ContainerData data;

    public FractionatorMenu(int id, Inventory inv, FriendlyByteBuf buf) {
        this(id, inv, (FractionatorBlockEntity) inv.player.level().getBlockEntity(buf.readBlockPos()), 
            new SimpleContainerData(FractionatorBlockEntity.INTERNAL_DATA_COUNT));
    }
    public FractionatorMenu(int id, Inventory inv, FractionatorBlockEntity be, ContainerData data) {
        super(ModMenus.FRACTIONATOR_MENU.get(), id);
        this.be = be;
        this.data = data;

        // player inv
        addStandardInventorySlots(inv, 8, 84);

        addSlot(new SlotItemHandler(be.inventory, 0, 22, 36)); // INPUT_SLOT
        addSlot(new SlotItemHandler(be.inventory, 1, 139, 36)); // MIDDLE_OUTPUT_SLOT
        addSlot(new SlotItemHandler(be.inventory, 2, 139, 18)); // TOP_OUTPUT_SLOT
        addSlot(new SlotItemHandler(be.inventory, 3, 139, 54)); // BOTTOM_OUTPUT_SLOT

        addDataSlots(data); // Stores progress values
    }

    public int getScaledArrowProgress(int arrowLength) {
        int progress = this.data.get(2);
        int maxProgress = this.data.get(3);
        return maxProgress != 0 ? progress * arrowLength / maxProgress : 0;
    }

    public int getScaledOilTankHeight(int maxBarHeight) {
        int fluidAmount = this.data.get(0);
        int tankCapacity = this.data.get(1);
        return tankCapacity != 0 ? fluidAmount * maxBarHeight / tankCapacity : 0;
    }

    public List<Component> getOilTankTooltip() {
        int fluidAmount = this.data.get(0);
        int tankCapacity = this.data.get(1);
        return List.of(Component.literal(fluidAmount + " / " + tankCapacity));
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
