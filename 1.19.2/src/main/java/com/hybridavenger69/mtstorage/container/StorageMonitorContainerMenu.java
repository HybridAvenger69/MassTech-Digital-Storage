package com.hybridavenger69.mtstorage.container;

import com.hybridavenger69.mtstorage.MSContainerMenus;
import com.hybridavenger69.mtstorage.container.slot.filter.FilterSlot;
import com.hybridavenger69.mtstorage.container.slot.filter.FluidFilterSlot;
import com.hybridavenger69.mtstorage.blockentity.StorageMonitorBlockEntity;
import com.hybridavenger69.mtstorage.blockentity.config.IType;
import net.minecraft.world.entity.player.Player;

public class StorageMonitorContainerMenu extends BaseContainerMenu {
    public StorageMonitorContainerMenu(StorageMonitorBlockEntity storageMonitor, Player player, int windowId) {
        super(MSContainerMenus.STORAGE_MONITOR.get(), storageMonitor, player, windowId);

        addSlot(new FilterSlot(storageMonitor.getNode().getItemFilters(), 0, 80, 20).setEnableHandler(() -> storageMonitor.getNode().getType() == IType.ITEMS));
        addSlot(new FluidFilterSlot(storageMonitor.getNode().getFluidFilters(), 0, 80, 20).setEnableHandler(() -> storageMonitor.getNode().getType() == IType.FLUIDS));

        addPlayerInventory(8, 55);

        transferManager.addFilterTransfer(player.getInventory(), storageMonitor.getNode().getItemFilters(), storageMonitor.getNode().getFluidFilters(), storageMonitor.getNode()::getType);
    }

    @Override
    public void updatePatternSlotPositions(int patternScrollOffset) {

    }
}
