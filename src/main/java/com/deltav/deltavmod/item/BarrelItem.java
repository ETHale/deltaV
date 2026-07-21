package com.deltav.deltavmod.item;

import java.util.function.Consumer;

import com.deltav.deltavmod.data.ModDataComponents;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.fluid.FluidResource;

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
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay tooltipDisplay,
            Consumer<Component> tooltipAdder, TooltipFlag flag) {
        ResourceHandler<FluidResource> fluidHandler = stack.getCapability(Capabilities.Fluid.ITEM, null);
        if (fluidHandler != null) {
            if (!fluidHandler.getResource(0).isEmpty()) {
                String amount = fluidHandler.getAmountAsInt(0) + " / " + fluidHandler.getCapacityAsInt(0, null) + "mB";
                String fluidName = fluidHandler.getResource(0).getFluidType().getDescriptionId();
                tooltipAdder.accept(Component.translatable(fluidName));
                tooltipAdder.accept(Component.literal(amount));
            }
        }

        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
    }
}