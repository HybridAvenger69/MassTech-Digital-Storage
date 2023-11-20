package com.hybridavenger69.mtstorage.container.transfer;

import net.minecraft.world.item.ItemStack;

interface IInventoryWrapper {
    InsertionResult insert(ItemStack stack);
}
