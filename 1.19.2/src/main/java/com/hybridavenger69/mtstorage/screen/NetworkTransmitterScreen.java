package com.hybridavenger69.mtstorage.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.hybridavenger69.mtstorage.MS;
import com.hybridavenger69.mtstorage.container.NetworkTransmitterContainerMenu;
import com.hybridavenger69.mtstorage.screen.widget.sidebutton.RedstoneModeSideButton;
import com.hybridavenger69.mtstorage.blockentity.NetworkNodeBlockEntity;
import com.hybridavenger69.mtstorage.blockentity.NetworkTransmitterBlockEntity;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.Optional;

public class NetworkTransmitterScreen extends BaseScreen<NetworkTransmitterContainerMenu> {
    public NetworkTransmitterScreen(NetworkTransmitterContainerMenu containerMenu, Inventory inventory, Component title) {
        super(containerMenu, 176, 137, inventory, title);
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
        bindTexture(MS.ID, "gui/network_transmitter.png");

        blit(poseStack, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void renderForeground(PoseStack poseStack, int mouseX, int mouseY) {
        renderString(poseStack, 7, 7, title.getString());

        String text;

        Optional<ResourceLocation> receiverDim = NetworkTransmitterBlockEntity.RECEIVER_DIMENSION.getValue();
        int distance = NetworkTransmitterBlockEntity.DISTANCE.getValue();

        if (!receiverDim.isPresent()) {
            text = I18n.get("gui.mtstorage.network_transmitter.missing_card");
        } else if (distance != -1) {
            text = I18n.get("gui.mtstorage.network_transmitter.distance", distance);
        } else {
            text = receiverDim.get().toString();
        }

        renderString(poseStack, 51, 24, text);
        renderString(poseStack, 7, 42, I18n.get("container.inventory"));
    }
}
