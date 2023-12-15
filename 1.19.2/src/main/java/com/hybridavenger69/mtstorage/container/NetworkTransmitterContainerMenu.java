package com.hybridavenger69.mtstorage.container;

import com.hybridavenger69.mtstorage.MSContainerMenus;
import com.hybridavenger69.mtstorage.blockentity.NetworkTransmitterBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.items.SlotItemHandler;

public class NetworkTransmitterContainerMenu extends BaseContainerMenu {
    public NetworkTransmitterContainerMenu(NetworkTransmitterBlockEntity networkTransmitter, Player player, int windowId) {
        super(MSContainerMenus.NETWORK_TRANSMITTER.get(), networkTransmitter, player, windowId);

        addSlot(new SlotItemHandler(networkTransmitter.getNode().getNetworkCard(), 0, 8, 20));

        addPlayerInventory(8, 55);

        transferManager.addBiTransfer(player.getInventory(), networkTransmitter.getNode().getNetworkCard());
    }
}
