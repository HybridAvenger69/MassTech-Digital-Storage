package com.hybridavenger69.mtstorage.apiimpl.autocrafting;

import com.hybridavenger69.mtstorage.api.autocrafting.ICraftingPatternContainer;
import net.minecraft.world.item.ItemStack;

public class CraftingPatternContext {
    private final ICraftingPatternContainer container;
    private final ItemStack stack;

    public CraftingPatternContext(ICraftingPatternContainer container, ItemStack stack) {
        this.container = container;
        this.stack = stack;
    }

    public ICraftingPatternContainer getContainer() {
        return container;
    }

    public ItemStack getStack() {
        return stack;
    }
}
