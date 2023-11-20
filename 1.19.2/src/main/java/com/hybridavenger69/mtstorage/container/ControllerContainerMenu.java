package com.hybridavenger69.mtstorage.container;

import com.hybridavenger69.mtstorage.MSContainerMenus;
import com.hybridavenger69.mtstorage.blockentity.ControllerBlockEntity;
import net.minecraft.world.entity.player.Player;

public class ControllerContainerMenu extends BaseContainerMenu {
    public ControllerContainerMenu(ControllerBlockEntity controller, Player player, int windowId) {
        super(MSContainerMenus.CONTROLLER.get(), controller, player, windowId);

        addPlayerInventory(8, 99);
    }

    @Override
    public void updatePatternSlotPositions(int patternScrollOffset) {

    }
}
