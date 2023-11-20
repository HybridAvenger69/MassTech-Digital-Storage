package com.hybridavenger69.mtstorage.container;

import com.hybridavenger69.mtstorage.MSContainerMenus;
import com.hybridavenger69.mtstorage.blockentity.CrafterBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.items.SlotItemHandler;

public class CrafterContainerMenu extends BaseContainerMenu {
    public CrafterContainerMenu(CrafterBlockEntity crafter, Player player, int windowId) {
        super(MSContainerMenus.CRAFTER.get(), crafter, player, windowId);

        for (int i = 0; i < 9; ++i) {
            addSlot(new SlotItemHandler(crafter.getNode().getPatternInventory(), i, 8 + (18 * i), 20));
        }

        for (int i = 0; i < 4; ++i) {
            addSlot(new SlotItemHandler(crafter.getNode().getUpgrades(), i, 187, 6 + (i * 18)));
        }

        addPlayerInventory(8, 55);

        transferManager.addBiTransfer(player.getInventory(), crafter.getNode().getUpgrades());
        transferManager.addBiTransfer(player.getInventory(), crafter.getNode().getPatternInventory());
    }

    @Override
    public void updatePatternSlotPositions(int patternScrollOffset) {

    }
}
