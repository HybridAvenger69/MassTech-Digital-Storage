package com.hybridavenger69.mtstorage.container;

import com.hybridavenger69.mtstorage.MSContainerMenus;
import com.hybridavenger69.mtstorage.blockentity.RelayBlockEntity;
import net.minecraft.world.entity.player.Player;

public class RelayContainerMenu extends BaseContainerMenu {
    public RelayContainerMenu(RelayBlockEntity relay, Player player, int windowId) {
        super(MSContainerMenus.RELAY.get(), relay, player, windowId);

        addPlayerInventory(8, 50);
    }

    @Override
    public void updatePatternSlotPositions(int patternScrollOffset) {

    }
}
