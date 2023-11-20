package com.hybridavenger69.mtstorage.inventory.listener;

import com.hybridavenger69.mtstorage.api.network.node.INetworkNode;
import com.hybridavenger69.mtstorage.inventory.item.BaseItemHandler;

public class NetworkNodeInventoryListener implements InventoryListener<BaseItemHandler> {
    private final INetworkNode node;

    public NetworkNodeInventoryListener(INetworkNode node) {
        this.node = node;
    }

    @Override
    public void onChanged(BaseItemHandler handler, int slot, boolean reading) {
        if (!reading) {
            node.markDirty();
        }
    }
}
