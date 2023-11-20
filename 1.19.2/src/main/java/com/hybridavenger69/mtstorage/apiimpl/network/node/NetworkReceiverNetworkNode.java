package com.hybridavenger69.mtstorage.apiimpl.network.node;

import com.hybridavenger69.hybridlib.HybridIDS;
import com.hybridavenger69.mtstorage.MS;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class NetworkReceiverNetworkNode extends NetworkNode {
    public static final ResourceLocation ID = new ResourceLocation(HybridIDS.MTStorage_MODID, "network_receiver");

    public NetworkReceiverNetworkNode(Level level, BlockPos pos) {
        super(level, pos);
    }

    @Override
    public int getEnergyUsage() {
        return MS.SERVER_CONFIG.getNetworkReceiver().getUsage();
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }
}
