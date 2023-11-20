package com.hybridavenger69.mtstorage.container;

import com.hybridavenger69.mtstorage.MSContainerMenus;
import com.hybridavenger69.mtstorage.container.slot.filter.FilterSlot;
import com.hybridavenger69.mtstorage.container.slot.filter.FluidFilterSlot;
import com.hybridavenger69.mtstorage.blockentity.ConstructorBlockEntity;
import com.hybridavenger69.mtstorage.blockentity.config.IType;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.items.SlotItemHandler;

public class ConstructorContainerMenu extends BaseContainerMenu {
    public ConstructorContainerMenu(ConstructorBlockEntity constructor, Player player, int windowId) {
        super(MSContainerMenus.CONSTRUCTOR.get(), constructor, player, windowId);

        for (int i = 0; i < 4; ++i) {
            addSlot(new SlotItemHandler(constructor.getNode().getUpgrades(), i, 187, 6 + (i * 18)));
        }

        addSlot(new FilterSlot(constructor.getNode().getItemFilters(), 0, 80, 20).setEnableHandler(() -> constructor.getNode().getType() == IType.ITEMS));
        addSlot(new FluidFilterSlot(constructor.getNode().getFluidFilters(), 0, 80, 20, 0).setEnableHandler(() -> constructor.getNode().getType() == IType.FLUIDS));

        addPlayerInventory(8, 55);

        transferManager.addBiTransfer(player.getInventory(), constructor.getNode().getUpgrades());
        transferManager.addFilterTransfer(player.getInventory(), constructor.getNode().getItemFilters(), constructor.getNode().getFluidFilters(), constructor.getNode()::getType);
    }

    @Override
    public void updatePatternSlotPositions(int patternScrollOffset) {

    }
}
