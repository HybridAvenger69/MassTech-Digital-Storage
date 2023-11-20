package com.hybridavenger69.mtstorage.apiimpl.network;

import com.hybridavenger69.mtstorage.api.network.INetwork;
import com.hybridavenger69.mtstorage.api.network.node.INetworkNode;
import com.hybridavenger69.mtstorage.apiimpl.API;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class NetworkListener {
    @SubscribeEvent
    public void onLevelTick(TickEvent.LevelTickEvent e) {
        if (!e.level.isClientSide() && e.phase == TickEvent.Phase.END) {
            e.level.getProfiler().push("network ticking");

            for (INetwork network : API.instance().getNetworkManager((ServerLevel) e.level).all()) {
                network.update();
            }

            e.level.getProfiler().pop();

            e.level.getProfiler().push("network node ticking");

            for (INetworkNode node : API.instance().getNetworkNodeManager((ServerLevel) e.level).all()) {
                node.update();
            }

            e.level.getProfiler().pop();
        }
    }
}
