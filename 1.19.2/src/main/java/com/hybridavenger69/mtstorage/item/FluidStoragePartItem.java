package com.hybridavenger69.mtstorage.item;

import com.hybridavenger69.mtstorage.MS;
import com.hybridavenger69.mtstorage.apiimpl.storage.FluidStorageType;
import net.minecraft.world.item.Item;

public class FluidStoragePartItem extends Item {
    public FluidStoragePartItem() {
        super(new Item.Properties().tab(MS.CREATIVE_MODE_TAB));
    }

    public static FluidStoragePartItem getByType(FluidStorageType type) {
        return com.hybridavenger69.mtstorage.MSItems.FLUID_STORAGE_PARTS.get(type).get();
    }
}
