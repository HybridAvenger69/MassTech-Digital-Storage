package com.hybridavenger69.mtstorage.apiimpl.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.hybridavenger69.mtstorage.api.render.IElementDrawer;
import com.hybridavenger69.mtstorage.screen.grid.CraftingPreviewScreen;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class CraftingPreviewElementDrawers extends ElementDrawers<AbstractContainerMenu> {
    private final IElementDrawer<Integer> overlayDrawer = (poseStack, x, y, color) -> {
        RenderSystem.setShaderColor(1, 1, 1, 1);
        GuiComponent.fill(poseStack, x, y, x + 73, y + 29, color);
    };

    public CraftingPreviewElementDrawers(CraftingPreviewScreen screen) {
        super(screen);
    }

    @Override
    public IElementDrawer<Integer> getOverlayDrawer() {
        return overlayDrawer;
    }
}
