package com.deltav.deltavmod.screen;

import org.slf4j.Logger;

import com.deltav.deltavmod.DeltaV;
import com.deltav.deltavmod.menu.BasicBatteryMenu;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.logging.LogUtils;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BasicBatteryScreen extends AbstractContainerScreen<BasicBatteryMenu>{
    private static final ResourceLocation BG = ResourceLocation.fromNamespaceAndPath(DeltaV.MODID, "textures/gui/basic_battery.png");
    
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
        int e = menu.energyStored;
        int cap = menu.capacity;

        Logger log = LogUtils.getLogger();
        log.debug(String.valueOf(e));
        log.debug(String.valueOf(cap));

        int maxBarHeight = 57;
        int leftPadding = 75;
        int barWidth = 25;
        int topPadding = 17;

        int barHeight = cap > 0 ? (int) ((e / (float) cap) * maxBarHeight) : 0;
        guiGraphics.fill(leftPos + leftPadding, topPos + topPadding + maxBarHeight,
               leftPos + leftPadding + barWidth, topPos + topPadding + (maxBarHeight - barHeight), 0xFF00FF00);

        String energyLabel = e + " / " + cap;
        int textX = leftPos + leftPadding + barWidth + 10;
        int textY = topPos + topPadding + (maxBarHeight / 2); // Near bottom of bar
        guiGraphics.drawString(this.font, energyLabel, textX, textY, 0xFF00FF00, false);
    }

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(g, mouseX, mouseY, partialTicks);
        super.render(g, mouseX, mouseY, partialTicks);
        this.renderTooltip(g, mouseX, mouseY);
    }
}
