package com.hybridavenger69.mtstorage.inventory.fluid;

import com.hybridavenger69.mtstorage.item.FilterItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class ConfiguredIconInFluidFilterItemHandler extends FluidInventory {
    public ConfiguredIconInFluidFilterItemHandler(ItemStack stack) {
        super(1, Integer.MAX_VALUE);

        this.addListener((handler, slot, reading) -> {
            if (!stack.hasTag()) {
                stack.setTag(new CompoundTag());
            }

            FilterItem.setFluidIcon(stack, getFluid(slot));
        });

        FluidStack icon = FilterItem.getFluidIcon(stack);
        if (!icon.isEmpty()) {
            setFluid(0, icon);
        }
    }
}
