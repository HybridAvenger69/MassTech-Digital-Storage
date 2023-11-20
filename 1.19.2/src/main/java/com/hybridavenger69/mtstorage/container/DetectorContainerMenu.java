package com.hybridavenger69.mtstorage.container;

import com.hybridavenger69.mtstorage.MSContainerMenus;
import com.hybridavenger69.mtstorage.container.slot.filter.FilterSlot;
import com.hybridavenger69.mtstorage.container.slot.filter.FluidFilterSlot;
import com.hybridavenger69.mtstorage.blockentity.DetectorBlockEntity;
import com.hybridavenger69.mtstorage.blockentity.config.IType;
import net.minecraft.world.entity.player.Player;

public class DetectorContainerMenu extends BaseContainerMenu {
    public DetectorContainerMenu(DetectorBlockEntity detector, Player player, int windowId) {
        super(MSContainerMenus.DETECTOR.get(), detector, player, windowId);

        addSlot(new FilterSlot(detector.getNode().getItemFilters(), 0, 107, 20).setEnableHandler(() -> detector.getNode().getType() == IType.ITEMS));
        addSlot(new FluidFilterSlot(detector.getNode().getFluidFilters(), 0, 107, 20).setEnableHandler(() -> detector.getNode().getType() == IType.FLUIDS));

        addPlayerInventory(8, 55);

        transferManager.addFilterTransfer(player.getInventory(), detector.getNode().getItemFilters(), detector.getNode().getFluidFilters(), detector.getNode()::getType);
    }

    @Override
    public void updatePatternSlotPositions(int patternScrollOffset) {

    }
}
