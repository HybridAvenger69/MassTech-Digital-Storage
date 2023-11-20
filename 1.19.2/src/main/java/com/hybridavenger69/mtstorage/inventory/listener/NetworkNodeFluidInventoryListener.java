package com.hybridavenger69.mtstorage.inventory.listener;

import com.hybridavenger69.mtstorage.api.network.node.INetworkNode;
import com.hybridavenger69.mtstorage.inventory.fluid.FluidInventory;

public class NetworkNodeFluidInventoryListener implements InventoryListener<FluidInventory> {
    private final INetworkNode node;

    public NetworkNodeFluidInventoryListener(INetworkNode node) {
        this.node = node;
    }

    @Override
    public void onChanged(FluidInventory handler, int slot, boolean reading) {
        if (!reading) {
            node.markDirty();
        }
    }
}
