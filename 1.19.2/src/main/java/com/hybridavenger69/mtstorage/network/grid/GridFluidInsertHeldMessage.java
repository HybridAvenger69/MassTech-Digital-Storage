package com.hybridavenger69.mtstorage.network.grid;

import com.hybridavenger69.mtstorage.api.network.grid.IGrid;
import com.hybridavenger69.mtstorage.container.GridContainerMenu;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class GridFluidInsertHeldMessage {
    public static void handle(Supplier<NetworkEvent.Context> ctx) {
        ServerPlayer player = ctx.get().getSender();

        if (player != null) {
            ctx.get().enqueueWork(() -> {
                AbstractContainerMenu container = player.containerMenu;

                if (container instanceof GridContainerMenu) {
                    IGrid grid = ((GridContainerMenu) container).getGrid();

                    if (grid.getFluidHandler() != null) {
                        grid.getFluidHandler().onInsertHeldContainer(player);
                    }
                }
            });
        }

        ctx.get().setPacketHandled(true);
    }
}
