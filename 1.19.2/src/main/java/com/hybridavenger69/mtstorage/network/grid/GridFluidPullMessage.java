package com.hybridavenger69.mtstorage.network.grid;

import com.hybridavenger69.mtstorage.api.network.grid.IGrid;
import com.hybridavenger69.mtstorage.container.GridContainerMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class GridFluidPullMessage {
    private final UUID id;
    private final boolean shift;

    public GridFluidPullMessage(UUID id, boolean shift) {
        this.id = id;
        this.shift = shift;
    }

    public static GridFluidPullMessage decode(FriendlyByteBuf buf) {
        return new GridFluidPullMessage(buf.readUUID(), buf.readBoolean());
    }

    public static void encode(GridFluidPullMessage message, FriendlyByteBuf buf) {
        buf.writeUUID(message.id);
        buf.writeBoolean(message.shift);
    }

    public static void handle(GridFluidPullMessage message, Supplier<NetworkEvent.Context> ctx) {
        ServerPlayer player = ctx.get().getSender();

        if (player != null) {
            ctx.get().enqueueWork(() -> {
                AbstractContainerMenu container = player.containerMenu;

                if (container instanceof GridContainerMenu) {
                    IGrid grid = ((GridContainerMenu) container).getGrid();

                    if (grid.getFluidHandler() != null) {
                        grid.getFluidHandler().onExtract(player, message.id, message.shift);
                    }
                }
            });
        }

        ctx.get().setPacketHandled(true);
    }
}
