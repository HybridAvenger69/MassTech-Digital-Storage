package com.hybridavenger69.mtstorage.screen;

import com.hybridavenger69.mtstorage.container.StorageContainerMenu;
import com.hybridavenger69.mtstorage.blockentity.NetworkNodeBlockEntity;
import com.hybridavenger69.mtstorage.blockentity.StorageBlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class StorageBlockScreen extends StorageScreen<StorageContainerMenu> {
    public StorageBlockScreen(StorageContainerMenu containerMenu, Inventory inventory, Component title) {
        super(
            containerMenu,
            inventory,
            title,
            "gui/storage.png",
            new StorageScreenSynchronizationParameters(
                null,
                NetworkNodeBlockEntity.REDSTONE_MODE,
                StorageBlockEntity.COMPARE,
                StorageBlockEntity.WHITELIST_BLACKLIST,
                StorageBlockEntity.PRIORITY,
                StorageBlockEntity.ACCESS_TYPE
            ),
            StorageBlockEntity.STORED::getValue,
            () -> (long) ((StorageBlockEntity) containerMenu.getBlockEntity()).getItemStorageType().getCapacity()
        );
    }
}
