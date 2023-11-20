package com.hybridavenger69.mtstorage.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.hybridavenger69.mtstorage.container.DiskDriveContainerMenu;
import com.hybridavenger69.mtstorage.blockentity.DiskDriveBlockEntity;
import com.hybridavenger69.mtstorage.blockentity.NetworkNodeBlockEntity;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class DiskDriveScreen extends StorageScreen<DiskDriveContainerMenu> {
    public DiskDriveScreen(DiskDriveContainerMenu containerMenu, Inventory inventory, Component title) {
        super(
            containerMenu,
            inventory,
            title,
            "gui/disk_drive.png",
            new StorageScreenSynchronizationParameters(
                DiskDriveBlockEntity.TYPE,
                NetworkNodeBlockEntity.REDSTONE_MODE,
                DiskDriveBlockEntity.COMPARE,
                DiskDriveBlockEntity.WHITELIST_BLACKLIST,
                DiskDriveBlockEntity.PRIORITY,
                DiskDriveBlockEntity.ACCESS_TYPE
            ),
            DiskDriveBlockEntity.STORED::getValue,
            DiskDriveBlockEntity.CAPACITY::getValue
        );
    }

    @Override
    public void renderForeground(PoseStack poseStack, int mouseX, int mouseY) {
        renderString(poseStack, 79, 42, I18n.get("gui.mtstorage.disk_drive.disks"));

        super.renderForeground(poseStack, mouseX, mouseY);
    }
}
