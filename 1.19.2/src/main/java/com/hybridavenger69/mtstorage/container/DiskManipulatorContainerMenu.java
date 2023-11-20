package com.hybridavenger69.mtstorage.container;

import com.hybridavenger69.mtstorage.MSContainerMenus;
import com.hybridavenger69.mtstorage.container.slot.filter.FilterSlot;
import com.hybridavenger69.mtstorage.container.slot.filter.FluidFilterSlot;
import com.hybridavenger69.mtstorage.blockentity.DiskManipulatorBlockEntity;
import com.hybridavenger69.mtstorage.blockentity.config.IType;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.items.SlotItemHandler;

public class DiskManipulatorContainerMenu extends BaseContainerMenu {
    public DiskManipulatorContainerMenu(DiskManipulatorBlockEntity diskManipulator, Player player, int windowId) {
        super(MSContainerMenus.DISK_MANIPULATOR.get(), diskManipulator, player, windowId);

        for (int i = 0; i < 4; ++i) {
            addSlot(new SlotItemHandler(diskManipulator.getNode().getUpgrades(), i, 187, 6 + (i * 18)));
        }

        for (int i = 0; i < 3; ++i) {
            addSlot(new SlotItemHandler(diskManipulator.getNode().getInputDisks(), i, 44, 57 + (i * 18)));
        }

        for (int i = 0; i < 3; ++i) {
            addSlot(new SlotItemHandler(diskManipulator.getNode().getOutputDisks(), i, 116, 57 + (i * 18)));
        }

        for (int i = 0; i < 9; ++i) {
            addSlot(new FilterSlot(diskManipulator.getNode().getItemFilters(), i, 8 + (18 * i), 20).setEnableHandler(() -> diskManipulator.getNode().getType() == IType.ITEMS));
        }

        for (int i = 0; i < 9; ++i) {
            addSlot(new FluidFilterSlot(diskManipulator.getNode().getFluidFilters(), i, 8 + (18 * i), 20).setEnableHandler(() -> diskManipulator.getNode().getType() == IType.FLUIDS));
        }

        addPlayerInventory(8, 129);

        transferManager.addBiTransfer(player.getInventory(), diskManipulator.getNode().getUpgrades());
        transferManager.addBiTransfer(player.getInventory(), diskManipulator.getNode().getInputDisks());
        transferManager.addTransfer(diskManipulator.getNode().getOutputDisks(), player.getInventory());
        transferManager.addFilterTransfer(player.getInventory(), diskManipulator.getNode().getItemFilters(), diskManipulator.getNode().getFluidFilters(), diskManipulator.getNode()::getType);
    }

    @Override
    public void updatePatternSlotPositions(int patternScrollOffset) {

    }
}
