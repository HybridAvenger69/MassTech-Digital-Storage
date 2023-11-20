package com.hybridavenger69.mtstorage.screen;

import com.hybridavenger69.mtstorage.container.ExternalStorageContainerMenu;
import com.hybridavenger69.mtstorage.blockentity.ExternalStorageBlockEntity;
import com.hybridavenger69.mtstorage.blockentity.NetworkNodeBlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ExternalStorageScreen extends StorageScreen<ExternalStorageContainerMenu> {
    public ExternalStorageScreen(ExternalStorageContainerMenu containerMenu, Inventory inventory, Component title) {
        super(
            containerMenu,
            inventory,
            title,
            "gui/storage.png",
            new StorageScreenSynchronizationParameters(
                ExternalStorageBlockEntity.TYPE,
                NetworkNodeBlockEntity.REDSTONE_MODE,
                ExternalStorageBlockEntity.COMPARE,
                ExternalStorageBlockEntity.WHITELIST_BLACKLIST,
                ExternalStorageBlockEntity.PRIORITY,
                ExternalStorageBlockEntity.ACCESS_TYPE
            ),
            ExternalStorageBlockEntity.STORED::getValue,
            ExternalStorageBlockEntity.CAPACITY::getValue
        );
    }
}
