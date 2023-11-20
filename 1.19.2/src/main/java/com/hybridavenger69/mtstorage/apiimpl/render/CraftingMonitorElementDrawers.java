package com.hybridavenger69.mtstorage.apiimpl.render;

import com.hybridavenger69.hybridlib.HybridIDS;
import com.mojang.blaze3d.systems.RenderSystem;
import com.hybridavenger69.mtstorage.MS;
import com.hybridavenger69.mtstorage.api.render.IElementDrawer;
import com.hybridavenger69.mtstorage.container.CraftingMonitorContainerMenu;
import com.hybridavenger69.mtstorage.screen.BaseScreen;
import net.minecraft.client.gui.GuiComponent;

public class CraftingMonitorElementDrawers extends ElementDrawers<CraftingMonitorContainerMenu> {
    private final IElementDrawer<Integer> overlayDrawer;
    private final IElementDrawer<Void> errorDrawer;

    public CraftingMonitorElementDrawers(BaseScreen<CraftingMonitorContainerMenu> screen, int itemWidth, int itemHeight) {
        super(screen);

        this.overlayDrawer = (poseStack, x, y, color) -> {
            RenderSystem.setShaderColor(1, 1, 1, 1);
            GuiComponent.fill(poseStack, x, y, x + itemWidth, y + itemHeight, color);
        };

        this.errorDrawer = (poseStack, x, y, nothing) -> {
            RenderSystem.setShaderColor(1, 1, 1, 1);
            screen.bindTexture(HybridIDS.MTStorage_MODID, "gui/crafting_preview.png");
            screen.blit(poseStack, x + itemWidth - 12 - 2, y + itemHeight - 12 - 2, 0, 244, 12, 12);
        };
    }

    @Override
    public IElementDrawer<Integer> getOverlayDrawer() {
        return overlayDrawer;
    }

    @Override
    public IElementDrawer<Void> getErrorDrawer() {
        return errorDrawer;
    }
}
