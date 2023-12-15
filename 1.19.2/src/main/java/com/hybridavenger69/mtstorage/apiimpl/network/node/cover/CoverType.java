package com.hybridavenger69.mtstorage.apiimpl.network.node.cover;

import net.minecraft.world.item.ItemStack;

public enum CoverType {
    NORMAL,
    HOLLOW;

    public ItemStack createStack() {
        return new ItemStack(this == NORMAL ? com.hybridavenger69.mtstorage.MSItems.COVER.get() : com.hybridavenger69.mtstorage.MSItems.HOLLOW_COVER.get());
    }
}
