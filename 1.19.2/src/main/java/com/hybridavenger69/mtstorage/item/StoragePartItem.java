package com.hybridavenger69.mtstorage.item;

import com.hybridavenger69.mtstorage.MS;
import com.hybridavenger69.mtstorage.MSItems;
import com.hybridavenger69.mtstorage.apiimpl.storage.ItemStorageType;
import net.minecraft.world.item.Item;

public class StoragePartItem extends Item {
    public StoragePartItem() {
        super(new Item.Properties().tab(MS.CREATIVE_MODE_TAB));
    }

    public static StoragePartItem getByType(ItemStorageType type) {
        return MSItems.ITEM_STORAGE_PARTS.get(type).get();
    }
}
