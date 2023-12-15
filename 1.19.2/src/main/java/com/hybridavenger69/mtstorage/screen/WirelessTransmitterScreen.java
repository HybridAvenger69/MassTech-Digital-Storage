package com.hybridavenger69.mtstorage.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.hybridavenger69.mtstorage.MS;
import com.hybridavenger69.mtstorage.container.WirelessTransmitterContainerMenu;
import com.hybridavenger69.mtstorage.screen.widget.sidebutton.RedstoneModeSideButton;
import com.hybridavenger69.mtstorage.blockentity.NetworkNodeBlockEntity;
import com.hybridavenger69.mtstorage.blockentity.WirelessTransmitterBlockEntity;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class WirelessTransmitterScreen extends BaseScreen<WirelessTransmitterContainerMenu> {
    public WirelessTransmitterScreen(WirelessTransmitterContainerMenu containerMenu, Inventory inventory, Component title) {
        super(containerMenu, 211, 137, inventory, title);
    }

    @Override
    public void onPostInit(int x, int y) {
        addSideButton(new RedstoneModeSideButton(this, NetworkNodeBlockEntity.REDSTONE_MODE));
    }

    @Override
    public void tick(int x, int y) {
        // NO OP
    }

    @Override
    public void renderBackground(PoseStack poseStack, int x, int y, int mouseX, int mouseY) {
        bindTexture(MS.ID, "gui/wireless_transmitter.png");

        blit(poseStack, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void renderForeground(PoseStack poseStack, int mouseX, int mouseY) {
        renderString(poseStack, 7, 7, title.getString());
        renderString(poseStack, 28, 25, I18n.get("gui.mtstorage.wireless_transmitter.distance", WirelessTransmitterBlockEntity.RANGE.getValue()));
        renderString(poseStack, 7, 43, I18n.get("container.inventory"));
    }
}
