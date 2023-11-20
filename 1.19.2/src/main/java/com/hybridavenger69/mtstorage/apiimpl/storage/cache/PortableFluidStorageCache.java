package com.hybridavenger69.mtstorage.apiimpl.storage.cache;

import com.hybridavenger69.mtstorage.api.storage.IStorage;
import com.hybridavenger69.mtstorage.api.storage.cache.IStorageCache;
import com.hybridavenger69.mtstorage.api.storage.cache.IStorageCacheListener;
import com.hybridavenger69.mtstorage.api.storage.cache.InvalidateCause;
import com.hybridavenger69.mtstorage.api.util.IStackList;
import com.hybridavenger69.mtstorage.api.util.StackListResult;
import com.hybridavenger69.mtstorage.apiimpl.API;
import com.hybridavenger69.mtstorage.blockentity.grid.portable.IPortableGrid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PortableFluidStorageCache implements IStorageCache<FluidStack> {
    private final IPortableGrid portableGrid;
    private final IStackList<FluidStack> list = API.instance().createFluidStackList();
    private final List<IStorageCacheListener<FluidStack>> listeners = new LinkedList<>();

    public PortableFluidStorageCache(IPortableGrid portableGrid) {
        this.portableGrid = portableGrid;
    }

    @Override
    public void invalidate(InvalidateCause cause) {
        list.clear();

        if (portableGrid.getFluidStorage() != null) {
            portableGrid.getFluidStorage().getStacks().forEach(list::add);
        }

        listeners.forEach(IStorageCacheListener::onInvalidated);
    }

    @Override
    public void add(@Nonnull FluidStack stack, int size, boolean rebuilding, boolean batched) {
        StackListResult<FluidStack> result = list.add(stack, size);

        if (!rebuilding) {
            listeners.forEach(l -> l.onChanged(result));
        }
    }

    @Override
    public void remove(@Nonnull FluidStack stack, int size, boolean batched) {
        StackListResult<FluidStack> result = list.remove(stack, size);

        if (result != null) {
            listeners.forEach(l -> l.onChanged(result));
        }
    }

    @Override
    public void flush() {
        throw new UnsupportedOperationException("Cannot flush portable grid storage cache");
    }

    @Override
    public void addListener(IStorageCacheListener<FluidStack> listener) {
        listeners.add(listener);

        listener.onAttached();
    }

    @Override
    public void removeListener(IStorageCacheListener<FluidStack> listener) {
        listeners.remove(listener);
    }

    @Override
    public void reAttachListeners() {
        listeners.forEach(IStorageCacheListener::onAttached);
    }

    @Override
    public void sort() {
        // NO OP
    }

    @Override
    public IStackList<FluidStack> getList() {
        return list;
    }

    @Override
    public IStackList<FluidStack> getCraftablesList() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<IStorage<FluidStack>> getStorages() {
        return Collections.emptyList();
    }
}
