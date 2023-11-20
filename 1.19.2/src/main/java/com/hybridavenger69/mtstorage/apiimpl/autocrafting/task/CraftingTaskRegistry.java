package com.hybridavenger69.mtstorage.apiimpl.autocrafting.task;

import com.hybridavenger69.mtstorage.api.autocrafting.task.ICraftingTaskFactory;
import com.hybridavenger69.mtstorage.api.autocrafting.task.ICraftingTaskRegistry;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class CraftingTaskRegistry implements ICraftingTaskRegistry {
    private final Map<ResourceLocation, ICraftingTaskFactory> registry = new HashMap<>();

    @Override
    public void add(ResourceLocation id, ICraftingTaskFactory factory) {
        registry.put(id, factory);
    }

    @Override
    @Nullable
    public ICraftingTaskFactory get(ResourceLocation id) {
        return registry.get(id);
    }
}
