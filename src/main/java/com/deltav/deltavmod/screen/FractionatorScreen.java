package com.deltav.deltavmod.screen;

import java.util.Optional;

import com.deltav.deltavmod.DeltaV;
import com.deltav.deltavmod.menu.FractionatorMenu;
import com.deltav.deltavmod.menu.ModMenus;

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

/**
 * Fractionator GUI
 * 
 * @author Adam Crawley
 */
@EventBusSubscriber(modid = DeltaV.MODID, value = Dist.CLIENT)
public class FractionatorScreen extends AbstractContainerScreen<FractionatorMenu> {
    private static final ResourceLocation BG = ResourceLocation.fromNamespaceAndPath(DeltaV.MODID, "textures/gui/fractionator.png");
    private static final ResourceLocation ARROW = ResourceLocation.fromNamespaceAndPath(DeltaV.MODID, "textures/gui/arrow_progress.png");

    // Tank coordinates
    private static final int TOP_PADDING = 18;
    private static final int LEFT_PADDING = 47;
    private static final int MAX_BAR_HEIGHT = 50;
    private static final int BAR_WIDTH = 25;

    public FractionatorScreen(FractionatorMenu menu, Inventory inv, Component title) {
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

        // Render crafting arrow progress
        final int arrowProgress = menu.getScaledArrowProgress(24);
        if (arrowProgress > 0) {
            guiGraphics.blit(
                RenderPipelines.GUI_TEXTURED, // This first parameter isn't actually required - include for completeness
                ARROW,
                this.leftPos + 103, this.topPos + 18,
                0, 0,
                arrowProgress, 52,
                24, 52
            );
        }
        

        // Render oil tank
        final int tankFillHeight = menu.getScaledOilTankHeight(MAX_BAR_HEIGHT);

        guiGraphics.fillGradient(leftPos + LEFT_PADDING, topPos + TOP_PADDING + (MAX_BAR_HEIGHT - tankFillHeight),
               leftPos + LEFT_PADDING + BAR_WIDTH, topPos + TOP_PADDING + MAX_BAR_HEIGHT, 0xFF010A1C, 0xFF051536);

        // Output slot labels
        guiGraphics.drawString(this.font, "N", this.leftPos + 158, this.topPos + 23, -12566464, false);
        guiGraphics.drawString(this.font, "P", this.leftPos + 158, this.topPos + 41, -12566464, false);
        guiGraphics.drawString(this.font, "K", this.leftPos + 158, this.topPos + 59, -12566464, false);
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

        // Render additional tank tooltip if hovered over
        boolean isHoveringOverTank = isHovering(LEFT_PADDING, TOP_PADDING, BAR_WIDTH, MAX_BAR_HEIGHT, mouseX, mouseY);
        if (isHoveringOverTank) {
            g.setTooltipForNextFrame(
                this.font,
                menu.getOilTankTooltip(),
                Optional.empty(),
                ItemStack.EMPTY,
                mouseX,
                mouseY
            );
        }
    }

    /**
     * Register the fractionator screen when client is registering screens.
     * @param event
     */
    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenus.FRACTIONATOR_MENU.get(), FractionatorScreen::new);
    }
}
