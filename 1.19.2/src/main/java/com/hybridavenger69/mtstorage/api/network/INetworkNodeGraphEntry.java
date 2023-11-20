package com.hybridavenger69.mtstorage.api.network;

import com.hybridavenger69.mtstorage.api.network.node.INetworkNode;

/**
 * An entry in the network graph.
 * Implementors MUST implement equals and hashCode.
 */
public interface INetworkNodeGraphEntry {
    INetworkNode getNode();
}
