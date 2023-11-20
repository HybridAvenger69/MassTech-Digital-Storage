package com.hybridavenger69.mtstorage.screen;

import com.hybridavenger69.hybridlib.HybridIDS;
import com.mojang.blaze3d.vertex.PoseStack;
import com.hybridavenger69.mtstorage.MS;
import com.hybridavenger69.mtstorage.container.InterfaceContainerMenu;
import com.hybridavenger69.mtstorage.screen.widget.sidebutton.ExactModeSideButton;
import com.hybridavenger69.mtstorage.screen.widget.sidebutton.RedstoneModeSideButton;
import com.hybridavenger69.mtstorage.blockentity.InterfaceBlockEntity;
import com.hybridavenger69.mtstorage.blockentity.NetworkNodeBlockEntity;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class InterfaceScreen extends BaseScreen<InterfaceContainerMenu> {
    public InterfaceScreen(InterfaceContainerMenu containerMenu, Inventory inventory, Component title) {
        super(containerMenu, 211, 217, inventory, title);
    }

    @Override
    public void onPostInit(int x, int y) {
        addSideButton(new RedstoneModeSideButton(this, NetworkNodeBlockEntity.REDSTONE_MODE));

        addSideButton(new ExactModeSideButton(this, InterfaceBlockEntity.COMPARE));
    }

    @Override
    public void tick(int x, int y) {
        // NO OP
    }

    @Override
    public void renderBackground(PoseStack poseStack, int x, int y, int mouseX, int mouseY) {
        bindTexture(HybridIDS.MTStorage_MODID, "gui/interface.png");

        blit(poseStack, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void renderForeground(PoseStack poseStack, int mouseX, int mouseY) {
        renderString(poseStack, 7, 7, I18n.get("gui.mtstorage.interface.import"));
        renderString(poseStack, 7, 42, I18n.get("gui.mtstorage.interface.export"));
        renderString(poseStack, 7, 122, I18n.get("container.inventory"));
    }
}
