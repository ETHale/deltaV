package com.deltav.deltavmod.screen;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;

import com.deltav.deltavmod.DeltaV;
import com.deltav.deltavmod.menu.BasicBatteryMenu;
import com.deltav.deltavmod.menu.ModMenus;
import com.mojang.logging.LogUtils;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = DeltaV.MODID, value = Dist.CLIENT)
public class BasicBatteryScreen extends AbstractContainerScreen<BasicBatteryMenu>{
    private static final ResourceLocation BG = ResourceLocation.fromNamespaceAndPath(DeltaV.MODID, "textures/gui/basic_battery.png");
    
    private static final int MAX_BAR_HEIGHT = 57;
    private static final int LEFT_PADDING = 75;
    private static final int BAR_WIDTH = 25;
    private static final int TOP_PADDING = 17;
    
    public BasicBatteryScreen(BasicBatteryMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        guiGraphics.blit(
            RenderPipelines.GUI_TEXTURED,
            BG,
            this.leftPos, this.topPos,
            0, 0,
            this.imageWidth, this.imageHeight,
            256, 256
        );
        int e = menu.getEnergyStored();
        int cap = menu.getCapacity();

        Logger log = LogUtils.getLogger();
        log.debug(String.valueOf(e));
        log.debug(String.valueOf(cap));

        int barHeight = cap > 0 ? (int) ((e / (float) cap) * MAX_BAR_HEIGHT) : 0;
        guiGraphics.fillGradient(leftPos + LEFT_PADDING,topPos + TOP_PADDING + (MAX_BAR_HEIGHT - barHeight),
               leftPos + LEFT_PADDING + BAR_WIDTH, topPos + TOP_PADDING + MAX_BAR_HEIGHT, 0xFF941400, 0xFFBD2008);
    }

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(g, mouseX, mouseY, partialTicks);
        super.render(g, mouseX, mouseY, partialTicks);
        this.renderTooltip(g, mouseX, mouseY);
    }

    @Override
    public void renderTooltip(GuiGraphics g, int mouseX, int mouseY) {
        super.renderTooltip(g, mouseX, mouseY);

        String energyLabel = menu.getEnergyStored() + "/" + menu.getCapacity();

        // Render additional tank tooltip if hovered over
        boolean isHoveringOverTank = isHovering(LEFT_PADDING, TOP_PADDING, BAR_WIDTH, MAX_BAR_HEIGHT, mouseX, mouseY);
        if (isHoveringOverTank) {
            g.setTooltipForNextFrame(
                this.font,
                List.of(Component.literal(energyLabel)),
                Optional.empty(),
                ItemStack.EMPTY,
                mouseX,
                mouseY
            );
        }
    }

    /**
     * Register the basic battery screen when client is registering screens.
     * @param event
     */
    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenus.BASIC_BATTERY_MENU.get(), BasicBatteryScreen::new);
    }
}
