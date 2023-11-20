package com.hybridavenger69.mtstorage.apiimpl.autocrafting.task.v6;

import com.hybridavenger69.hybridlib.HybridIDS;
import com.hybridavenger69.mtstorage.MS;
import com.hybridavenger69.mtstorage.api.autocrafting.ICraftingPattern;
import com.hybridavenger69.mtstorage.api.autocrafting.task.*;
import com.hybridavenger69.mtstorage.api.network.INetwork;
import com.hybridavenger69.mtstorage.apiimpl.autocrafting.task.v6.calculator.CraftingCalculator;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

public class CraftingTaskFactory implements ICraftingTaskFactory {
    public static final ResourceLocation ID = new ResourceLocation(HybridIDS.MTStorage_MODID, "v6");

    @Override
    public ICalculationResult create(INetwork network, ICraftingRequestInfo requested, int quantity, ICraftingPattern pattern) {
        CraftingCalculator calculator = new CraftingCalculator(network, requested, quantity, pattern);
        return calculator.calculate();
    }

    @Override
    public ICraftingTask createFromNbt(INetwork network, CompoundTag tag) throws CraftingTaskReadException {
        return new CraftingTask(network, tag);
    }
}