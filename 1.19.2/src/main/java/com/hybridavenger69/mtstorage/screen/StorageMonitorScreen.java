package com.hybridavenger69.mtstorage.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.hybridavenger69.mtstorage.MS;
import com.hybridavenger69.mtstorage.container.StorageMonitorContainerMenu;
import com.hybridavenger69.mtstorage.screen.widget.sidebutton.ExactModeSideButton;
import com.hybridavenger69.mtstorage.screen.widget.sidebutton.TypeSideButton;
import com.hybridavenger69.mtstorage.blockentity.StorageMonitorBlockEntity;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class StorageMonitorScreen extends BaseScreen<StorageMonitorContainerMenu> {
    public StorageMonitorScreen(StorageMonitorContainerMenu containerMenu, Inventory inventory, Component title) {
        super(containerMenu, 211, 137, inventory, title);
    }

    @Override
    public void onPostInit(int x, int y) {
        addSideButton(new TypeSideButton(this, StorageMonitorBlockEntity.TYPE));
        addSideButton(new ExactModeSideButton(this, StorageMonitorBlockEntity.COMPARE));
    }

    @Override
    public void tick(int x, int y) {
        // NO OP
    }

    @Override
    public void renderBackground(PoseStack poseStack, int x, int y, int mouseX, int mouseY) {
        bindTexture(MS.ID, "gui/storage_monitor.png");

        blit(poseStack, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void renderForeground(PoseStack poseStack, int mouseX, int mouseY) {
        renderString(poseStack, 7, 7, title.getString());
        renderString(poseStack, 7, 43, I18n.get("container.inventory"));
    }
}
