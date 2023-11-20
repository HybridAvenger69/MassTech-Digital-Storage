package com.hybridavenger69.mtstorage.network.grid;

import com.hybridavenger69.mtstorage.network.ClientProxy;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class GridCraftingStartResponseMessage {
    public static void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> ClientProxy.onReceivedCraftingStartResponseMessage());
        ctx.get().setPacketHandled(true);
    }
}
