package com.hybridavenger69.mtstorage.apiimpl.network;

import com.hybridavenger69.mtstorage.api.network.node.INetworkNodeFactory;
import com.hybridavenger69.mtstorage.api.network.node.INetworkNodeRegistry;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class NetworkNodeRegistry implements INetworkNodeRegistry {
    private final Map<ResourceLocation, INetworkNodeFactory> factories = new HashMap<>();

    @Override
    public void add(ResourceLocation id, INetworkNodeFactory factory) {
        factories.put(id, factory);
    }

    @Override
    @Nullable
    public INetworkNodeFactory get(ResourceLocation id) {
        return factories.get(id);
    }
}
