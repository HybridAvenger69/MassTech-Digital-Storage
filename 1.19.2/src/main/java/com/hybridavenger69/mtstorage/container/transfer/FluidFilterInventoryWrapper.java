package com.hybridavenger69.mtstorage.container.transfer;

import com.hybridavenger69.mtstorage.api.util.IComparer;
import com.hybridavenger69.mtstorage.apiimpl.API;
import com.hybridavenger69.mtstorage.inventory.fluid.FluidInventory;
import com.hybridavenger69.mtstorage.util.StackUtils;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.FluidStack;

class FluidFilterInventoryWrapper implements IInventoryWrapper {
    private final FluidInventory filterInv;

    FluidFilterInventoryWrapper(FluidInventory filterInv) {
        this.filterInv = filterInv;
    }

    @Override
    public InsertionResult insert(ItemStack stack) {
        InsertionResult stop = new InsertionResult(InsertionResultType.STOP);

        FluidStack fluidInContainer = StackUtils.getFluid(stack, true).getValue();

        if (fluidInContainer.isEmpty()) {
            return stop;
        }

        for (FluidStack fluid : filterInv.getFluids()) {
            if (API.instance().getComparer().isEqual(fluidInContainer, fluid, IComparer.COMPARE_NBT)) {
                return stop;
            }
        }

        for (int i = 0; i < filterInv.getSlots(); ++i) {
            if (filterInv.getFluid(i).isEmpty()) {
                filterInv.setFluid(i, StackUtils.copy(fluidInContainer, FluidType.BUCKET_VOLUME));

                return stop;
            }
        }

        return stop;
    }
}
