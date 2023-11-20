package com.hybridavenger69.mtstorage.inventory.item;

import com.hybridavenger69.mtstorage.item.FilterItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class ConfiguredIconInFilterItemHandler extends ItemStackHandler {
    private final ItemStack filterItem;

    public ConfiguredIconInFilterItemHandler(ItemStack filterItem) {
        super(1);

        this.filterItem = filterItem;

        setStackInSlot(0, FilterItem.getIcon(filterItem));
    }

    @Override
    protected void onContentsChanged(int slot) {
        super.onContentsChanged(slot);

        FilterItem.setIcon(filterItem, getStackInSlot(0));
    }
}
