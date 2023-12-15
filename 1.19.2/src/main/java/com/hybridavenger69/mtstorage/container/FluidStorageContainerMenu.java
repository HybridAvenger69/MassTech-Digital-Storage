package com.hybridavenger69.mtstorage.container;

import com.hybridavenger69.mtstorage.MSContainerMenus;
import com.hybridavenger69.mtstorage.container.slot.filter.FluidFilterSlot;
import com.hybridavenger69.mtstorage.blockentity.FluidStorageBlockEntity;
import net.minecraft.world.entity.player.Player;

public class FluidStorageContainerMenu extends BaseContainerMenu {
    public FluidStorageContainerMenu(FluidStorageBlockEntity fluidStorage, Player player, int windowId) {
        super(MSContainerMenus.FLUID_STORAGE_BLOCK.get(), fluidStorage, player, windowId);

        for (int i = 0; i < 9; ++i) {
            addSlot(new FluidFilterSlot(fluidStorage.getNode().getFilters(), i, 8 + (18 * i), 20));
        }

        addPlayerInventory(8, 141);

        transferManager.addFluidFilterTransfer(player.getInventory(), fluidStorage.getNode().getFilters());
    }
}
