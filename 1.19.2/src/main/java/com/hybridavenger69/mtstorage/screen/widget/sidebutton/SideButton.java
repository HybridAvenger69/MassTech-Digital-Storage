package com.hybridavenger69.mtstorage.screen.widget.sidebutton;

import com.hybridavenger69.hybridlib.HybridIDS;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.hybridavenger69.mtstorage.MS;
import com.hybridavenger69.mtstorage.screen.BaseScreen;
import com.hybridavenger69.mtstorage.util.RenderUtils;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import org.lwjgl.opengl.GL11;

public abstract class SideButton extends Button {
    private static final Button.OnPress NO_ACTION = btn -> {
    };

    private static final int WIDTH = 18;
    private static final int HEIGHT = 18;

    protected final BaseScreen<?> screen;

    protected SideButton(BaseScreen<?> screen) {
        super(-1, -1, WIDTH, HEIGHT, Component.empty(), NO_ACTION);

        this.screen = screen;
    }

    public void renderTooltip(PoseStack poseStack, int mouseX, int mouseY) {
        boolean isFocused = isFocused();
        if (isHovered || isFocused) {
            int x = isHovered ? mouseX : (this.x - screen.getGuiLeft()) + width;
            int y = isHovered ? mouseY : (this.y - screen.getGuiTop()) + (height / 2);
            screen.renderTooltip(poseStack, x, y, getTooltip());
        }
    }

    @Override
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        isHovered = RenderUtils.inBounds(x, y, width, height, mouseX, mouseY);

        screen.bindTexture(HybridIDS.MTStorage_MODID, "icons.png");
        screen.blit(poseStack, x, y, 238, isHovered ? 35 : 16, WIDTH, HEIGHT);

        renderButtonIcon(poseStack, x + 1, y + 1);

        if (isHoveredOrFocused()) {
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 0.5f);
            screen.blit(poseStack, x, y, 238, 54, WIDTH, HEIGHT);
            RenderSystem.disableBlend();
        }
    }

    public int getHeight() {
        return height;
    }

    protected abstract void renderButtonIcon(PoseStack poseStack, int x, int y);

    protected abstract String getTooltip();
}
