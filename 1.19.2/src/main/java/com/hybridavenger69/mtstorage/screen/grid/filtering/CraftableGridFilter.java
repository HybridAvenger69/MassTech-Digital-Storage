package com.hybridavenger69.mtstorage.screen.grid.filtering;

import com.hybridavenger69.mtstorage.screen.grid.stack.IGridStack;

import java.util.function.Predicate;

public class CraftableGridFilter implements Predicate<IGridStack> {
    private final boolean craftable;

    public CraftableGridFilter(boolean craftable) {
        this.craftable = craftable;
    }

    @Override
    public boolean test(IGridStack stack) {
        if (craftable) {
            return stack.isCraftable();
        } else {
            return !stack.isCraftable() && stack.getOtherId() == null;
        }
    }
}
