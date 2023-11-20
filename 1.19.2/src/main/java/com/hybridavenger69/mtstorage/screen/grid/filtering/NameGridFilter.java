package com.hybridavenger69.mtstorage.screen.grid.filtering;

import com.hybridavenger69.mtstorage.screen.grid.stack.IGridStack;

import java.util.function.Predicate;

public class NameGridFilter implements Predicate<IGridStack> {
    private final String name;

    public NameGridFilter(String name) {
        this.name = name.toLowerCase();
    }

    @Override
    public boolean test(IGridStack stack) {
        return stack.getName().toLowerCase().contains(name);
    }
}
