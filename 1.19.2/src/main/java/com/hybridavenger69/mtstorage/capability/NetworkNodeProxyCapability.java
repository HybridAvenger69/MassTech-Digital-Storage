package com.hybridavenger69.mtstorage.capability;

import com.hybridavenger69.mtstorage.api.network.node.INetworkNodeProxy;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class NetworkNodeProxyCapability {
    public static Capability<INetworkNodeProxy> NETWORK_NODE_PROXY_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    private NetworkNodeProxyCapability() {
    }
}
