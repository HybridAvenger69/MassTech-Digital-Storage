package com.hybridavenger69.mtstorage.container;

import com.hybridavenger69.mtstorage.MSContainerMenus;
import com.hybridavenger69.mtstorage.container.slot.filter.FilterSlot;
import com.hybridavenger69.mtstorage.container.slot.filter.FluidFilterSlot;
import com.hybridavenger69.mtstorage.blockentity.DiskDriveBlockEntity;
import com.hybridavenger69.mtstorage.blockentity.config.IType;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.items.SlotItemHandler;

public class DiskDriveContainerMenu extends BaseContainerMenu {
    public DiskDriveContainerMenu(DiskDriveBlockEntity diskDrive, Player player, int windowId) {
        super(MSContainerMenus.DISK_DRIVE.get(), diskDrive, player, windowId);

        int x = 80;
        int y = 54;

        for (int i = 0; i < 8; ++i) {
            addSlot(new SlotItemHandler(diskDrive.getNode().getDisks(), i, x + ((i % 2) * 18), y + Math.floorDiv(i, 2) * 18));
        }

        for (int i = 0; i < 9; ++i) {
            addSlot(new FilterSlot(diskDrive.getNode().getItemFilters(), i, 8 + (18 * i), 20).setEnableHandler(() -> diskDrive.getNode().getType() == IType.ITEMS));
        }

        for (int i = 0; i < 9; ++i) {
            addSlot(new FluidFilterSlot(diskDrive.getNode().getFluidFilters(), i, 8 + (18 * i), 20).setEnableHandler(() -> diskDrive.getNode().getType() == IType.FLUIDS));
        }

        addPlayerInventory(8, 141);

        transferManager.addBiTransfer(player.getInventory(), diskDrive.getNode().getDisks());
        transferManager.addFilterTransfer(player.getInventory(), diskDrive.getNode().getItemFilters(), diskDrive.getNode().getFluidFilters(), diskDrive.getNode()::getType);
    }

    @Override
    public void updatePatternSlotPositions(int patternScrollOffset) {

    }
}
