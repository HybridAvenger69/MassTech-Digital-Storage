package com.hybridavenger69.mtstorage.apiimpl.network.node.cover;

import com.hybridavenger69.mtstorage.MSItems;
import net.minecraft.world.item.ItemStack;

public enum CoverType {
    NORMAL,
    HOLLOW;

    public ItemStack createStack() {
        return new ItemStack(this == NORMAL ? MSItems.COVER.get() : MSItems.HOLLOW_COVER.get());
    }
}
