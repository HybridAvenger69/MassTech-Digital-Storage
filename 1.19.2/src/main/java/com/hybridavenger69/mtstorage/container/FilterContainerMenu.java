package com.hybridavenger69.mtstorage.container;

import com.hybridavenger69.mtstorage.MSContainerMenus;
import com.hybridavenger69.mtstorage.container.slot.filter.FilterSlot;
import com.hybridavenger69.mtstorage.container.slot.filter.FluidFilterSlot;
import com.hybridavenger69.mtstorage.inventory.fluid.ConfiguredFluidsInFilterItemHandler;
import com.hybridavenger69.mtstorage.inventory.fluid.ConfiguredIconInFluidFilterItemHandler;
import com.hybridavenger69.mtstorage.inventory.fluid.FluidInventory;
import com.hybridavenger69.mtstorage.inventory.item.ConfiguredIconInFilterItemHandler;
import com.hybridavenger69.mtstorage.inventory.item.ConfiguredItemsInFilterItemHandler;
import com.hybridavenger69.mtstorage.item.FilterItem;
import com.hybridavenger69.mtstorage.blockentity.config.IType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class FilterContainerMenu extends BaseContainerMenu {
    private final ItemStack filterItem;

    public FilterContainerMenu(Player player, ItemStack filterItem, int windowId) {
        super(MSContainerMenus.FILTER.get(), null, player, windowId);

        this.filterItem = filterItem;

        int y = 20;
        int x = 8;

        ConfiguredItemsInFilterItemHandler filter = new ConfiguredItemsInFilterItemHandler(filterItem);
        FluidInventory fluidFilter = new ConfiguredFluidsInFilterItemHandler(filterItem);

        for (int i = 0; i < 27; ++i) {
            addSlot(new FilterSlot(filter, i, x, y).setEnableHandler(() -> FilterItem.getType(filterItem) == IType.ITEMS));
            addSlot(new FluidFilterSlot(fluidFilter, i, x, y).setEnableHandler(() -> FilterItem.getType(filterItem) == IType.FLUIDS));

            if ((i + 1) % 9 == 0) {
                x = 8;
                y += 18;
            } else {
                x += 18;
            }
        }

        addSlot(new FilterSlot(new ConfiguredIconInFilterItemHandler(filterItem), 0, 8, 117).setEnableHandler(() -> FilterItem.getType(filterItem) == IType.ITEMS));
        addSlot(new FluidFilterSlot(new ConfiguredIconInFluidFilterItemHandler(filterItem), 0, 8, 117).setEnableHandler(() -> FilterItem.getType(filterItem) == IType.FLUIDS));

        addPlayerInventory(8, 149);

        transferManager.addFilterTransfer(player.getInventory(), filter, fluidFilter, () -> FilterItem.getType(filterItem));
    }

    public ItemStack getFilterItem() {
        return filterItem;
    }

    @Override
    protected int getDisabledSlotNumber() {
        return getPlayer().getInventory().selected;
    }

    @Override
    public void updatePatternSlotPositions(int patternScrollOffset) {

    }
}
