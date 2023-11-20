package com.hybridavenger69.mtstorage.container;

import com.hybridavenger69.mtstorage.container.slot.filter.DisabledFluidFilterSlot;
import com.hybridavenger69.mtstorage.inventory.fluid.FluidInventory;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fluids.FluidStack;

public class FluidAmountContainerMenu extends BaseContainerMenu {
    public FluidAmountContainerMenu(Player player, FluidStack stack) {
        super(null, null, player, 0);

        FluidInventory inventory = new FluidInventory(1);

        inventory.setFluid(0, stack);

        addSlot(new DisabledFluidFilterSlot(inventory, 0, 89, 48, 0));
    }

    @Override
    public void updatePatternSlotPositions(int patternScrollOffset) {

    }
}
