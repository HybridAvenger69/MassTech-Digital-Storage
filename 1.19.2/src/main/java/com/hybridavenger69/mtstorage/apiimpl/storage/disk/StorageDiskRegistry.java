package com.hybridavenger69.mtstorage.apiimpl.storage.disk;

import com.hybridavenger69.mtstorage.api.storage.disk.IStorageDiskFactory;
import com.hybridavenger69.mtstorage.api.storage.disk.IStorageDiskRegistry;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class StorageDiskRegistry implements IStorageDiskRegistry {
    private final Map<ResourceLocation, IStorageDiskFactory> factories = new HashMap<>();

    @Override
    public void add(ResourceLocation id, IStorageDiskFactory factory) {
        factories.put(id, factory);
    }

    @Override
    @Nullable
    public IStorageDiskFactory get(ResourceLocation id) {
        return factories.get(id);
    }
}
