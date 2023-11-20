package com.hybridavenger69.mtstorage.screen;

import com.hybridavenger69.hybridlib.HybridIDS;
import com.mojang.blaze3d.vertex.PoseStack;
import com.hybridavenger69.mtstorage.MS;
import com.hybridavenger69.mtstorage.container.DiskManipulatorContainerMenu;
import com.hybridavenger69.mtstorage.screen.widget.sidebutton.*;
import com.hybridavenger69.mtstorage.blockentity.DiskManipulatorBlockEntity;
import com.hybridavenger69.mtstorage.blockentity.NetworkNodeBlockEntity;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class DiskManipulatorScreen extends BaseScreen<DiskManipulatorContainerMenu> {
    public DiskManipulatorScreen(DiskManipulatorContainerMenu containerMenu, Inventory playerInventory, Component title) {
        super(containerMenu, 211, 211, playerInventory, title);
    }

    @Override
    public void onPostInit(int x, int y) {
        addSideButton(new RedstoneModeSideButton(this, NetworkNodeBlockEntity.REDSTONE_MODE));
        addSideButton(new IoModeSideButton(this));
        addSideButton(new TypeSideButton(this, DiskManipulatorBlockEntity.TYPE));
        addSideButton(new WhitelistBlacklistSideButton(this, DiskManipulatorBlockEntity.WHITELIST_BLACKLIST));
        addSideButton(new ExactModeSideButton(this, DiskManipulatorBlockEntity.COMPARE));
    }

    @Override
    public void tick(int x, int y) {
        // NO OP
    }

    @Override
    public void renderBackground(PoseStack poseStack, int x, int y, int mouseX, int mouseY) {
        bindTexture(HybridIDS.MTStorage_MODID, "gui/disk_manipulator.png");

        blit(poseStack, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void renderForeground(PoseStack poseStack, int mouseX, int mouseY) {
        renderString(poseStack, 7, 7, title.getString());
        renderString(poseStack, 7, 117, I18n.get("container.inventory"));
        renderString(poseStack, 43, 45, I18n.get("gui.mtstorage.disk_manipulator.in"));
        renderString(poseStack, 115, 45, I18n.get("gui.mtstorage.disk_manipulator.out"));
    }
}
