package com.hybridavenger69.mtstorage.apiimpl.network.node.diskdrive;

import com.hybridavenger69.mtstorage.api.storage.AccessType;
import com.hybridavenger69.mtstorage.api.storage.disk.IStorageDisk;
import com.hybridavenger69.mtstorage.api.storage.disk.IStorageDiskContainerContext;
import com.hybridavenger69.mtstorage.api.storage.disk.IStorageDiskListener;
import com.hybridavenger69.mtstorage.api.util.Action;
import com.hybridavenger69.mtstorage.apiimpl.network.node.DiskState;
import com.hybridavenger69.mtstorage.blockentity.config.IWhitelistBlacklist;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.UUID;

public class ItemDriveWrapperStorageDisk implements IStorageDisk<ItemStack> {
    private final DiskDriveNetworkNode diskDrive;
    private final IStorageDisk<ItemStack> parent;
    private DiskState lastState;

    public ItemDriveWrapperStorageDisk(DiskDriveNetworkNode diskDrive, IStorageDisk<ItemStack> parent) {
        this.diskDrive = diskDrive;
        this.parent = parent;
        this.setSettings(
            () -> {
                DiskState currentState = DiskState.get(getStored(), getCapacity());

                if (this.lastState != currentState) {
                    this.lastState = currentState;

                    diskDrive.requestBlockUpdate();
                }
            },
            diskDrive
        );
        this.lastState = DiskState.get(getStored(), getCapacity());
    }

    @Override
    public int getPriority() {
        return diskDrive.getPriority();
    }

    @Override
    public AccessType getAccessType() {
        return parent.getAccessType();
    }

    @Override
    public Collection<ItemStack> getStacks() {
        return parent.getStacks();
    }

    @Override
    @Nonnull
    public ItemStack insert(@Nonnull ItemStack stack, int size, Action action) {
        if (!IWhitelistBlacklist.acceptsItem(diskDrive.getItemFilters(), diskDrive.getWhitelistBlacklistMode(), diskDrive.getCompare(), stack)) {
            return ItemHandlerHelper.copyStackWithSize(stack, size);
        }

        return parent.insert(stack, size, action);
    }

    @Override
    @Nonnull
    public ItemStack extract(@Nonnull ItemStack stack, int size, int flags, Action action) {
        return parent.extract(stack, size, flags, action);
    }

    @Override
    public int getStored() {
        return parent.getStored();
    }

    @Override
    public int getCacheDelta(int storedPreInsertion, int size, @Nullable ItemStack remainder) {
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
