package com.hybridavenger69.mtstorage.container;

import com.hybridavenger69.mtstorage.MSContainerMenus;
import com.hybridavenger69.mtstorage.blockentity.WirelessTransmitterBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.items.SlotItemHandler;

public class WirelessTransmitterContainerMenu extends BaseContainerMenu {
    public WirelessTransmitterContainerMenu(WirelessTransmitterBlockEntity wirelessTransmitter, Player player, int windowId) {
        super(MSContainerMenus.WIRELESS_TRANSMITTER.get(), wirelessTransmitter, player, windowId);

        for (int i = 0; i < 4; ++i) {
            addSlot(new SlotItemHandler(wirelessTransmitter.getNode().getUpgrades(), i, 187, 6 + (i * 18)));
        }

        addPlayerInventory(8, 55);

        transferManager.addBiTransfer(player.getInventory(), wirelessTransmitter.getNode().getUpgrades());
    }

    @Override
    public void updatePatternSlotPositions(int patternScrollOffset) {

    }
}
