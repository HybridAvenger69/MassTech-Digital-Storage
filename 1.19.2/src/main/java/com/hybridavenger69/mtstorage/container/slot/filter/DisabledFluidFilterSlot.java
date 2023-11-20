package com.hybridavenger69.mtstorage.container.slot.filter;

import com.hybridavenger69.mtstorage.inventory.fluid.FluidInventory;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class DisabledFluidFilterSlot extends FluidFilterSlot {
    public DisabledFluidFilterSlot(FluidInventory inventory, int inventoryIndex, int x, int y, int flags) {
        super(inventory, inventoryIndex, x, y, flags);
    }

    public DisabledFluidFilterSlot(FluidInventory inventory, int inventoryIndex, int x, int y) {
        super(inventory, inventoryIndex, x, y);
    }

    @Override
    public void onContainerClicked(@Nonnull ItemStack stack) {
        // NO OP
    }
}
