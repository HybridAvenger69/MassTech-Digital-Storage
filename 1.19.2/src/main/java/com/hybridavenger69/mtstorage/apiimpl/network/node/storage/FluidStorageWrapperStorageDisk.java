package com.hybridavenger69.mtstorage.apiimpl.network.node.storage;

import com.hybridavenger69.mtstorage.api.storage.AccessType;
import com.hybridavenger69.mtstorage.api.storage.disk.IStorageDisk;
import com.hybridavenger69.mtstorage.api.storage.disk.IStorageDiskContainerContext;
import com.hybridavenger69.mtstorage.api.storage.disk.IStorageDiskListener;
import com.hybridavenger69.mtstorage.api.util.Action;
import com.hybridavenger69.mtstorage.blockentity.config.IWhitelistBlacklist;
import com.hybridavenger69.mtstorage.util.StackUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.UUID;

public class FluidStorageWrapperStorageDisk implements IStorageDisk<FluidStack> {
    private final FluidStorageNetworkNode storage;
    private final IStorageDisk<FluidStack> parent;

    public FluidStorageWrapperStorageDisk(FluidStorageNetworkNode storage, IStorageDisk<FluidStack> parent) {
        this.storage = storage;
        this.parent = parent;
        this.setSettings(null, storage);
    }

    @Override
    public int getPriority() {
        return storage.getPriority();
    }

    @Override
    public AccessType getAccessType() {
        return parent.getAccessType();
    }

    @Override
    public Collection<FluidStack> getStacks() {
        return parent.getStacks();
    }

    @Override
    @Nonnull
    public FluidStack insert(@Nonnull FluidStack stack, int size, Action action) {
        if (!IWhitelistBlacklist.acceptsFluid(storage.getFilters(), storage.getWhitelistBlacklistMode(), storage.getCompare(), stack)) {
            return StackUtils.copy(stack, size);
        }

        return parent.insert(stack, size, action);
    }

    @Override
    @Nonnull
    public FluidStack extract(@Nonnull FluidStack stack, int size, int flags, Action action) {
        return parent.extract(stack, size, flags, action);
    }

    @Override
    public int getStored() {
        return parent.getStored();
    }

    @Override
    public int getCacheDelta(int storedPreInsertion, int size, @Nullable FluidStack remainder) {
        return parent.getCacheDelta(storedPreInsertion, size, remainder);
    }

    @Override
    public int getCapacity() {
        return parent.getCapacity();
    }

    @Nullable
    @Override
    public UUID getOwner() {
        return parent.getOwner();
    }

    @Override
    public void setSettings(@Nullable IStorageDiskListener listener, IStorageDiskContainerContext context) {
        parent.setSettings(listener, context);
    }

    @Override
    public CompoundTag writeToNbt() {
        return parent.writeToNbt();
    }

    @Override
    public ResourceLocation getFactoryId() {
        return parent.getFactoryId();
    }
}
