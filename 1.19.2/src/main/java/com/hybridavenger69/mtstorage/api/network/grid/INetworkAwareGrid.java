package com.hybridavenger69.mtstorage.api.network.grid;

import com.hybridavenger69.mtstorage.api.network.INetwork;

import javax.annotation.Nullable;

/**
 * A grid that knows about a network.
 */
public interface INetworkAwareGrid extends IGrid {
    /**
     * @return the network, or null if no network is available
     */
    @Nullable
    INetwork getNetwork();
}
