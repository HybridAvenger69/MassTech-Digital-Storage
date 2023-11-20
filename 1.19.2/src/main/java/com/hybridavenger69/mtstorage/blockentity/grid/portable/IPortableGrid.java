package com.hybridavenger69.mtstorage.blockentity.grid.portable;

import com.hybridavenger69.mtstorage.api.storage.cache.IStorageCache;
import com.hybridavenger69.mtstorage.api.storage.disk.IStorageDisk;
import com.hybridavenger69.mtstorage.api.storage.tracker.IStorageTracker;
import com.hybridavenger69.mtstorage.inventory.item.BaseItemHandler;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nullable;

public interface IPortableGrid {
    @Nullable
    IStorageCache getCache();

    default IStorageCache<ItemStack> getItemCache() {
        return getCache();
    }

    default IStorageCache<FluidStack> getFluidCache() {
        return getCache();
    }

    @Nullable
    IStorageDisk getStorage();

    default IStorageDisk<ItemStack> getItemStorage() {
        return getStorage();
    }

    default IStorageDisk<FluidStack> getFluidStorage() {
        return getStorage();
    }

    void drainEnergy(int energy);

    int getEnergy();

    BaseItemHandler getDiskInventory();

    IItemHandlerModifiable getFilter();

    IStorageTracker<ItemStack> getItemStorageTracker();

    IStorageTracker<FluidStack> getFluidStorageTracker();

    boolean isGridActive();

    PortableGridDiskState getDiskState();
}
