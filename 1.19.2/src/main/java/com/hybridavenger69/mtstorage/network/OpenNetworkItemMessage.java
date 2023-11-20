package com.hybridavenger69.mtstorage.network;

import com.hybridavenger69.mtstorage.apiimpl.API;
//import com.hybridavenger69.mtstorage.apiimpl.network.grid.factory.PortableGridGridFactory;
import com.hybridavenger69.mtstorage.apiimpl.network.grid.factory.PortableGridGridFactory;
import com.hybridavenger69.mtstorage.inventory.player.PlayerSlot;
import com.hybridavenger69.mtstorage.item.NetworkItem;
//import com.hybridavenger69.mtstorage.item.blockitem.PortableGridBlockItem;
import com.hybridavenger69.mtstorage.item.blockitem.PortableGridBlockItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenNetworkItemMessage {
    private final PlayerSlot slot;

    public OpenNetworkItemMessage(PlayerSlot slot) {
        this.slot = slot;
    }

    public static OpenNetworkItemMessage decode(FriendlyByteBuf buf) {
        return new OpenNetworkItemMessage(new PlayerSlot(buf));
    }

    public static void encode(OpenNetworkItemMessage message, FriendlyByteBuf buf) {
        message.slot.writePlayerSlot(buf);
    }

    public static void handle(OpenNetworkItemMessage message, Supplier<NetworkEvent.Context> ctx) {
        ServerPlayer player = ctx.get().getSender();

        if (player != null) {
            ctx.get().enqueueWork(() -> {
                ItemStack stack = message.slot.getStackFromSlot(player);

                if (stack == null) {
                    return;
                }

                if (stack.getItem() instanceof NetworkItem) {
                    ((NetworkItem) stack.getItem()).applyNetwork(player.getServer(), stack, n -> n.getNetworkItemManager().open(player, stack, message.slot), player::sendSystemMessage);
                } else if (stack.getItem() instanceof PortableGridBlockItem) {
                    API.instance().getGridManager().openGrid(PortableGridGridFactory.ID, player, stack, message.slot);
                }
            });
        }

        ctx.get().setPacketHandled(true);
    }
}


