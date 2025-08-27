package com.deltav.deltavmod.screen;

import java.util.Optional;

import com.deltav.deltavmod.DeltaV;
import com.deltav.deltavmod.menu.ModMenus;
import com.deltav.deltavmod.menu.PolymeriserMenu;

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
 * Polymeriser GUI
 * 
 * @author Adam Crawley
 */
@EventBusSubscriber(modid = DeltaV.MODID, value = Dist.CLIENT)
public class PolymeriserScreen extends AbstractContainerScreen<PolymeriserMenu> {
    private static final ResourceLocation BG = ResourceLocation.fromNamespaceAndPath(DeltaV.MODID, "textures/gui/polymeriser.png");
    private static final ResourceLocation ARROW = ResourceLocation.fromNamespaceAndPath(DeltaV.MODID, "textures/gui/sprites/arrow_progress.png");

    // Tank coordinates
    private static final int TOP_PADDING = 18;
    private static final int LEFT_PADDING = 47;
    private static final int MAX_BAR_HEIGHT = 50;
    private static final int BAR_WIDTH = 12;

    public PolymeriserScreen(PolymeriserMenu menu, Inventory inv, Component title) {
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
                this.leftPos + 94, this.topPos + 25,
                0, 0,
                arrowProgress, 16,
                24, 16
            );
        }
        

        // Render naphtha tank
        final int tankFillHeight = menu.getScaledTankHeight(0, MAX_BAR_HEIGHT);
        guiGraphics.fillGradient(leftPos + LEFT_PADDING, topPos + TOP_PADDING + (MAX_BAR_HEIGHT - tankFillHeight),
               leftPos + LEFT_PADDING + BAR_WIDTH, topPos + TOP_PADDING + MAX_BAR_HEIGHT, 0xFF010A1C, 0xFF051536);

        // Render water tank
        final int waterTankFillHeight = menu.getScaledTankHeight(1, MAX_BAR_HEIGHT);
        guiGraphics.fillGradient(leftPos + LEFT_PADDING + BAR_WIDTH + 2, topPos + TOP_PADDING + (MAX_BAR_HEIGHT - waterTankFillHeight),
               leftPos + LEFT_PADDING + BAR_WIDTH + 2 + BAR_WIDTH, topPos + TOP_PADDING + MAX_BAR_HEIGHT, 0xFF0581B3, 0xFF10ADEB);

        // Output slot labels
        guiGraphics.drawString(this.font, "N", this.leftPos + 13, this.topPos + 30, -12566464, false);
        guiGraphics.drawString(this.font, "W", this.leftPos + 13, this.topPos + 48, -12566464, false);
        guiGraphics.drawString(this.font, "C", this.leftPos + 102, this.topPos + 68, -12566464, false);
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

        // Render additional naphtha tank tooltip if hovered over
        boolean isHoveringOverNaphthaTank = isHovering(LEFT_PADDING, TOP_PADDING, BAR_WIDTH, MAX_BAR_HEIGHT, mouseX, mouseY);
        if (isHoveringOverNaphthaTank) {
            g.setTooltipForNextFrame(
                this.font,
                menu.getTankTooltip(0),
                Optional.empty(),
                ItemStack.EMPTY,
                mouseX,
                mouseY
            );
        }

        // Render additional water tank tooltip if hovered over
        boolean isHoveringOverWaterTank = isHovering(LEFT_PADDING + BAR_WIDTH + 2, TOP_PADDING, BAR_WIDTH, MAX_BAR_HEIGHT, mouseX, mouseY);
        if (isHoveringOverWaterTank) {
            g.setTooltipForNextFrame(
                this.font,
                menu.getTankTooltip(1),
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
        event.register(ModMenus.POLYMERISER_MENU.get(), PolymeriserScreen::new);
    }
}
