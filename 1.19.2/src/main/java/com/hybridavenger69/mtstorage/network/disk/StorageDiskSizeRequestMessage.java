package com.hybridavenger69.mtstorage.network.disk;

import com.hybridavenger69.mtstorage.MS;
import com.hybridavenger69.mtstorage.api.storage.disk.IStorageDisk;
import com.hybridavenger69.mtstorage.apiimpl.API;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class StorageDiskSizeRequestMessage {
    private final UUID id;

    public StorageDiskSizeRequestMessage(UUID id) {
        this.id = id;
    }

    public static StorageDiskSizeRequestMessage decode(FriendlyByteBuf buf) {
        return new StorageDiskSizeRequestMessage(buf.readUUID());
    }

    public static void encode(StorageDiskSizeRequestMessage message, FriendlyByteBuf buf) {
        buf.writeUUID(message.id);
    }

    public static void handle(StorageDiskSizeRequestMessage message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            IStorageDisk disk = API.instance().getStorageDiskManager(ctx.get().getSender().getLevel()).get(message.id);

            if (disk != null) {
                MS.NETWORK_HANDLER.sendTo(ctx.get().getSender(), new StorageDiskSizeResponseMessage(message.id, disk.getStored(), disk.getCapacity()));
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
