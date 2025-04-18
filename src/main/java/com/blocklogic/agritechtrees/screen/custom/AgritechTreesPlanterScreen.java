package com.blocklogic.agritechtrees.screen.custom;

import com.blocklogic.agritechtrees.AgritechTrees;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class AgritechTreesPlanterScreen extends AbstractContainerScreen<AgritechTreesPlanterMenu> {

    public static final ResourceLocation AGRITECH_PLANTER_GUI_TEXTURE = ResourceLocation.fromNamespaceAndPath(AgritechTrees.MODID, "textures/gui/agritech_planter_gui.png");

    public AgritechTreesPlanterScreen(AgritechTreesPlanterMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F,1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, AGRITECH_PLANTER_GUI_TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) /2;

        guiGraphics.blit(AGRITECH_PLANTER_GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
