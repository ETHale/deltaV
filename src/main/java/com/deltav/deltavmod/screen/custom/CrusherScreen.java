package com.deltav.deltavmod.screen.custom;

import com.deltav.deltavmod.DeltaV;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

public class CrusherScreen extends AbstractContainerScreen<CrusherMenu>{
    private static final ResourceLocation GUI_TEXTURE =
        ResourceLocation.fromNamespaceAndPath(DeltaV.MODID, "textures/gui/crusher/crusher_gui.png");
    private static final ResourceLocation ARROW_TEXTURE =
        ResourceLocation.fromNamespaceAndPath(DeltaV.MODID, "arrow_progress");


    public CrusherScreen(CrusherMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {

        int x = (width - imageWidth)/2;
        int y = (height - imageHeight)/2;

        guiGraphics.blit(
            RenderPipelines.GUI_TEXTURED,
            GUI_TEXTURE,
            this.leftPos, this.topPos,
            0, 0,
            this.imageWidth, this.imageHeight,
            256, 256
        );

        renderProgressArrow(guiGraphics, this.leftPos, this.topPos);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            //int k = 14;
            //int l = Mth.ceil(menu.getScaledArrowProgress()*13.0F) +1;
            //guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, ARROW_TEXTURE, k, k, 0, k-l, x+56, y+36+k-l, k, l);

            //guiGraphics.blitSprite(ARROW_TEXTURE, x + 73, y + 35, 0, 0, menu.getScaledArrowProgress(), 16, 24, 16);

            int j = Mth.ceil(menu.getScaledArrowProgress());
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, ARROW_TEXTURE, 24, 16, 0, 0, x+73, y+34, j, 16);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }


}
