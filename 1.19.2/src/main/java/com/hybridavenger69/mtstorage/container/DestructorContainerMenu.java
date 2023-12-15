package com.hybridavenger69.mtstorage.container;

import com.hybridavenger69.mtstorage.MSContainerMenus;
import com.hybridavenger69.mtstorage.container.slot.filter.FilterSlot;
import com.hybridavenger69.mtstorage.container.slot.filter.FluidFilterSlot;
import com.hybridavenger69.mtstorage.blockentity.DestructorBlockEntity;
import com.hybridavenger69.mtstorage.blockentity.config.IType;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.items.SlotItemHandler;

public class DestructorContainerMenu extends BaseContainerMenu {
    public DestructorContainerMenu(DestructorBlockEntity destructor, Player player, int windowId) {
        super(MSContainerMenus.DESTRUCTOR.get(), destructor, player, windowId);

        for (int i = 0; i < 4; ++i) {
            addSlot(new SlotItemHandler(destructor.getNode().getUpgrades(), i, 187, 6 + (i * 18)));
        }

        for (int i = 0; i < 9; ++i) {
            addSlot(new FilterSlot(destructor.getNode().getItemFilters(), i, 8 + (18 * i), 20).setEnableHandler(() -> destructor.getNode().getType() == IType.ITEMS));
        }

        for (int i = 0; i < 9; ++i) {
            addSlot(new FluidFilterSlot(destructor.getNode().getFluidFilters(), i, 8 + (18 * i), 20).setEnableHandler(() -> destructor.getNode().getType() == IType.FLUIDS));
        }

        addPlayerInventory(8, 55);

        transferManager.addBiTransfer(player.getInventory(), destructor.getNode().getUpgrades());
        transferManager.addFilterTransfer(player.getInventory(), destructor.getNode().getItemFilters(), destructor.getNode().getFluidFilters(), destructor.getNode()::getType);
    }
}
