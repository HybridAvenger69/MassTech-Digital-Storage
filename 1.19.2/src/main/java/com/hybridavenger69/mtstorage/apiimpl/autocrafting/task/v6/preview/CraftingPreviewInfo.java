package com.hybridavenger69.mtstorage.apiimpl.autocrafting.task.v6.preview;

import com.hybridavenger69.mtstorage.api.util.IStackList;
import com.hybridavenger69.mtstorage.apiimpl.API;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class CraftingPreviewInfo {
    private final IStackList<ItemStack> missing = API.instance().createItemStackList();
    private final IStackList<FluidStack> missingFluids = API.instance().createFluidStackList();

    private final IStackList<ItemStack> toTake = API.instance().createItemStackList();
    private final IStackList<FluidStack> toTakeFluids = API.instance().createFluidStackList();

    private final List<ItemStack> toCraft = new ArrayList<>();
    private final List<FluidStack> toCraftFluids = new ArrayList<>();

    public IStackList<ItemStack> getMissing() {
        return missing;
    }

    public IStackList<FluidStack> getMissingFluids() {
        return missingFluids;
    }

    public boolean hasMissing() {
        return !missing.isEmpty() || !missingFluids.isEmpty();
    }

    public IStackList<ItemStack> getToTake() {
        return toTake;
    }

    public IStackList<FluidStack> getToTakeFluids() {
        return toTakeFluids;
    }

    public List<ItemStack> getToCraft() {
        return toCraft;
    }

    public List<FluidStack> getToCraftFluids() {
        return toCraftFluids;
    }
}
