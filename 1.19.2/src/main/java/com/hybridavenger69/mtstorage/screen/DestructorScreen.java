package com.hybridavenger69.mtstorage.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.hybridavenger69.mtstorage.MS;
import com.hybridavenger69.mtstorage.container.DestructorContainerMenu;
import com.hybridavenger69.mtstorage.screen.widget.sidebutton.*;
import com.hybridavenger69.mtstorage.blockentity.DestructorBlockEntity;
import com.hybridavenger69.mtstorage.blockentity.NetworkNodeBlockEntity;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class DestructorScreen extends BaseScreen<DestructorContainerMenu> {
    public DestructorScreen(DestructorContainerMenu containerMenu, Inventory playerInventory, Component title) {
        super(containerMenu, 211, 137, playerInventory, title);
    }

    @Override
    public void onPostInit(int x, int y) {
        addSideButton(new RedstoneModeSideButton(this, NetworkNodeBlockEntity.REDSTONE_MODE));

        addSideButton(new TypeSideButton(this, DestructorBlockEntity.TYPE));

        addSideButton(new WhitelistBlacklistSideButton(this, DestructorBlockEntity.WHITELIST_BLACKLIST));

        addSideButton(new ExactModeSideButton(this, DestructorBlockEntity.COMPARE));

        addSideButton(new DestructorPickupSideButton(this));
    }

    @Override
    public void tick(int x, int y) {
        // NO OP
    }

    @Override
    public void renderBackground(PoseStack poseStack, int x, int y, int mouseX, int mouseY) {
        bindTexture(MS.ID, "gui/destructor.png");

        blit(poseStack, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void renderForeground(PoseStack poseStack, int mouseX, int mouseY) {
        renderString(poseStack, 7, 7, title.getString());
        renderString(poseStack, 7, 43, I18n.get("container.inventory"));
    }
}
