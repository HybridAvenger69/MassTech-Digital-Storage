package com.hybridavenger69.mtstorage.apiimpl.network.node.cover;

import net.minecraft.world.item.ItemStack;

public class Cover {

    private ItemStack stack;
    private CoverType type;

    public Cover(ItemStack stack, CoverType type) {
        this.stack = stack;
        this.type = type;
    }

    public ItemStack getStack() {
        return stack;
    }

    public CoverType getType() {
        return type;
    }
}
