package com.hybridavenger69.mtstorage.apiimpl.storage.disk;

import com.hybridavenger69.mtstorage.api.storage.AccessType;
import com.hybridavenger69.mtstorage.api.storage.disk.IStorageDisk;
import com.hybridavenger69.mtstorage.api.storage.disk.IStorageDiskContainerContext;
import com.hybridavenger69.mtstorage.api.storage.disk.IStorageDiskListener;
import com.hybridavenger69.mtstorage.api.util.Action;
import com.hybridavenger69.mtstorage.blockentity.grid.portable.IPortableGrid;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.UUID;

public class PortableFluidStorageDisk implements IStorageDisk<FluidStack> {
    private final IStorageDisk<FluidStack> parent;
    private final IPortableGrid portableGrid;

    public PortableFluidStorageDisk(IStorageDisk<FluidStack> parent, IPortableGrid portableGrid) {
        this.parent = parent;
        this.portableGrid = portableGrid;
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
    public Collection<FluidStack> getStacks() {
        return parent.getStacks();
    }

    @Override
    @Nonnull
    public FluidStack insert(@Nonnull FluidStack stack, int size, Action action) {
        int storedPre = parent.getStored();

        FluidStack remainder = parent.insert(stack, size, action);

        if (action == Action.PERFORM) {
            int inserted = parent.getCacheDelta(storedPre, size, remainder);

            if (inserted > 0) {
                portableGrid.getFluidCache().add(stack, inserted, false, false);
            }
        }

        return remainder;
    }

    @Override
    @Nonnull
    public FluidStack extract(@Nonnull FluidStack stack, int size, int flags, Action action) {
        FluidStack extracted = parent.extract(stack, size, flags, action);

        if (action == Action.PERFORM && !extracted.isEmpty()) {
            portableGrid.getFluidCache().remove(extracted, extracted.getAmount(), false);
        }

        return extracted;
    }

    @Override
    public int getStored() {
        return parent.getStored();
    }

    @Override
    public int getPriority() {
        return parent.getPriority();
    }

    @Override
    public AccessType getAccessType() {
        return parent.getAccessType();
    }

    @Override
    public int getCacheDelta(int storedPreInsertion, int size, @Nullable FluidStack remainder) {
        return parent.getCacheDelta(storedPreInsertion, size, remainder);
    }

    @Override
    public ResourceLocation getFactoryId() {
        return parent.getFactoryId();
    }
}
