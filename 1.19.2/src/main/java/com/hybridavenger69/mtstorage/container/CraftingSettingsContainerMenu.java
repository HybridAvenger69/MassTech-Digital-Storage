package com.hybridavenger69.mtstorage.container;

import com.hybridavenger69.mtstorage.container.slot.DisabledSlot;
import com.hybridavenger69.mtstorage.container.slot.filter.DisabledFluidFilterSlot;
import com.hybridavenger69.mtstorage.inventory.fluid.FluidInventory;
import com.hybridavenger69.mtstorage.screen.grid.stack.FluidGridStack;
import com.hybridavenger69.mtstorage.screen.grid.stack.IGridStack;
import com.hybridavenger69.mtstorage.screen.grid.stack.ItemGridStack;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

public abstract class CraftingSettingsContainerMenu extends BaseContainerMenu {
    public CraftingSettingsContainerMenu(Player player, IGridStack stack) {
        super(null, null, player, 0);

        if (stack instanceof FluidGridStack) {
            FluidInventory inventory = new FluidInventory(1);

            inventory.setFluid(0, ((FluidGridStack) stack).getStack());

            addSlot(new DisabledFluidFilterSlot(inventory, 0, 89, 48));
        } else if (stack instanceof ItemGridStack) {
            ItemStackHandler handler = new ItemStackHandler(1);

            handler.setStackInSlot(0, ItemHandlerHelper.copyStackWithSize(((ItemGridStack) stack).getStack(), 1));

            addSlot(new DisabledSlot(handler, 0, 89, 48));
        }
    }
}
