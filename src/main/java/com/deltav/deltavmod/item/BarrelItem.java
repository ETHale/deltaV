package com.deltav.deltavmod.item;

import java.util.function.Consumer;

import com.deltav.deltavmod.data.ModDataComponents;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.neoforge.capabilities.Capabilities.FluidHandler;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;

/**
 * Item representing a barrel for storing fluids.
 * 
 * @author Adam Crawley
 */
public class BarrelItem extends Item {
    public BarrelItem(Properties properties) {
        super(properties
            .stacksTo(1)
            .component(ModDataComponents.GENERIC_FLUID, SimpleFluidContent.EMPTY));
    }

    @Override
    @SuppressWarnings("deprecation") // SFI: Remove this and use non-deprecated function
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay tooltipDisplay,
            Consumer<Component> tooltipAdder, TooltipFlag flag) {
        IFluidHandlerItem fluidHandler = FluidHandler.ITEM.getCapability(stack, null);
        if (fluidHandler != null) {
            tooltipAdder.accept(Component.literal(fluidHandler.getFluidInTank(0).getAmount() + " / " + fluidHandler.getTankCapacity(0) + " - " + fluidHandler.getFluidInTank(0).getFluid().toString()));
        }

        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
    }
}