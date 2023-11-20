package com.hybridavenger69.mtstorage.apiimpl.network.node.diskmanipulator;

import com.hybridavenger69.mtstorage.api.storage.AccessType;
import com.hybridavenger69.mtstorage.api.storage.disk.IStorageDisk;
import com.hybridavenger69.mtstorage.api.storage.disk.IStorageDiskContainerContext;
import com.hybridavenger69.mtstorage.api.storage.disk.IStorageDiskListener;
import com.hybridavenger69.mtstorage.api.util.Action;
import com.hybridavenger69.mtstorage.apiimpl.network.node.DiskState;
import com.hybridavenger69.mtstorage.blockentity.config.IWhitelistBlacklist;
import com.hybridavenger69.mtstorage.util.StackUtils;
import com.hybridavenger69.mtstorage.util.LevelUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.UUID;

public class StorageDiskFluidManipulatorWrapper implements IStorageDisk<FluidStack> {
    private final DiskManipulatorNetworkNode diskManipulator;
    private final IStorageDisk<FluidStack> parent;
    private DiskState lastState;

    public StorageDiskFluidManipulatorWrapper(DiskManipulatorNetworkNode diskManipulator, IStorageDisk<FluidStack> parent) {
        this.diskManipulator = diskManipulator;
        this.parent = parent;
        this.setSettings(
            () -> {
                DiskState currentState = DiskState.get(getStored(), getCapacity());

                if (lastState != currentState) {
                    lastState = currentState;

                    LevelUtils.updateBlock(diskManipulator.getLevel(), diskManipulator.getPos());
                }
            },
            diskManipulator
        );
        this.lastState = DiskState.get(getStored(), getCapacity());
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

    @Override
    public Collection<FluidStack> getStacks() {
        return parent.getStacks();
    }

    @Override
    @Nonnull
    public FluidStack insert(@Nonnull FluidStack stack, int size, Action action) {
        if (stack.isEmpty()) {
            return stack;
        }

        if (!IWhitelistBlacklist.acceptsFluid(diskManipulator.getFluidFilters(), diskManipulator.getWhitelistBlacklistMode(), diskManipulator.getCompare(), stack)) {
            return StackUtils.copy(stack, size);
        }

        return parent.insert(stack, size, action);
    }

    @Override
    @Nonnull
    public FluidStack extract(@Nonnull FluidStack stack, int size, int flags, Action action) {
        if (!IWhitelistBlacklist.acceptsFluid(diskManipulator.getFluidFilters(), diskManipulator.getWhitelistBlacklistMode(), diskManipulator.getCompare(), stack)) {
            return FluidStack.EMPTY;
        }

        return parent.extract(stack, size, flags, action);
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
}
