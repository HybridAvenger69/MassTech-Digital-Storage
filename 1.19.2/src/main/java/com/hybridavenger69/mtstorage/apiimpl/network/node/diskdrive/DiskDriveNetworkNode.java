package com.hybridavenger69.mtstorage.apiimpl.network.node.diskdrive;

import com.hybridavenger69.hybridlib.HybridIDS;
import com.hybridavenger69.mtstorage.MS;
import com.hybridavenger69.mtstorage.api.network.INetwork;
import com.hybridavenger69.mtstorage.api.storage.AccessType;
import com.hybridavenger69.mtstorage.api.storage.IStorage;
import com.hybridavenger69.mtstorage.api.storage.IStorageProvider;
import com.hybridavenger69.mtstorage.api.storage.cache.InvalidateCause;
import com.hybridavenger69.mtstorage.api.storage.disk.IStorageDisk;
import com.hybridavenger69.mtstorage.api.storage.disk.IStorageDiskContainerContext;
import com.hybridavenger69.mtstorage.api.util.IComparer;
import com.hybridavenger69.mtstorage.apiimpl.network.node.ConnectivityStateChangeCause;
import com.hybridavenger69.mtstorage.apiimpl.network.node.DiskState;
import com.hybridavenger69.mtstorage.apiimpl.network.node.NetworkNode;
import com.hybridavenger69.mtstorage.apiimpl.storage.cache.FluidStorageCache;
import com.hybridavenger69.mtstorage.apiimpl.storage.cache.ItemStorageCache;
import com.hybridavenger69.mtstorage.blockentity.DiskDriveBlockEntity;
import com.hybridavenger69.mtstorage.blockentity.config.*;
import com.hybridavenger69.mtstorage.inventory.fluid.FluidInventory;
import com.hybridavenger69.mtstorage.inventory.item.BaseItemHandler;
import com.hybridavenger69.mtstorage.inventory.item.validator.StorageDiskItemValidator;
import com.hybridavenger69.mtstorage.inventory.listener.NetworkNodeFluidInventoryListener;
import com.hybridavenger69.mtstorage.inventory.listener.NetworkNodeInventoryListener;
import com.hybridavenger69.mtstorage.util.AccessTypeUtils;
import com.hybridavenger69.mtstorage.util.StackUtils;
import com.hybridavenger69.mtstorage.util.LevelUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class DiskDriveNetworkNode extends NetworkNode implements IStorageProvider, IComparable, IWhitelistBlacklist, IPrioritizable, IType, IAccessType, IStorageDiskContainerContext {
    public static final ResourceLocation ID = new ResourceLocation(HybridIDS.MTStorage_MODID, "disk_drive");

    private static final String NBT_PRIORITY = "Priority";
    private static final String NBT_COMPARE = "Compare";
    private static final String NBT_MODE = "Mode";
    private static final String NBT_TYPE = "Type";
    private static final String NBT_FLUID_FILTERS = "FluidFilters";

    private static final int DISK_STATE_UPDATE_THROTTLE = 30;

    private static final Logger LOGGER = LogManager.getLogger(DiskDriveNetworkNode.class);
    private final BaseItemHandler itemFilters = new BaseItemHandler(9).addListener(new NetworkNodeInventoryListener(this));
    private final FluidInventory fluidFilters = new FluidInventory(9).addListener(new NetworkNodeFluidInventoryListener(this));
    private final IStorageDisk[] itemDisks = new IStorageDisk[8];
    private final IStorageDisk[] fluidDisks = new IStorageDisk[8];
    private final BaseItemHandler disks = new BaseItemHandler(8)
        .addValidator(new StorageDiskItemValidator())
        .addListener(new NetworkNodeInventoryListener(this))
        .addListener((handler, slot, reading) -> {
            if (!level.isClientSide) {
                StackUtils.createStorages(
                    (ServerLevel) level,
                    handler.getStackInSlot(slot),
                    slot,
                    itemDisks,
                    fluidDisks,
                    s -> new ItemDriveWrapperStorageDisk(DiskDriveNetworkNode.this, s),
                    s -> new FluidDriveWrapperStorageDisk(DiskDriveNetworkNode.this, s)
                );

                if (network != null) {
                    network.getItemStorageCache().invalidate(InvalidateCause.DISK_INVENTORY_CHANGED);
                    network.getFluidStorageCache().invalidate(InvalidateCause.DISK_INVENTORY_CHANGED);
                }

                if (!reading) {
                    LevelUtils.updateBlock(level, pos);
                }
            }
        });
    private int ticksSinceBlockUpdateRequested;
    private boolean blockUpdateRequested;
    private AccessType accessType = AccessType.INSERT_EXTRACT;
    private int priority = 0;
    private int compare = IComparer.COMPARE_NBT;
    private int mode = IWhitelistBlacklist.BLACKLIST;
    private int type = IType.ITEMS;

    public DiskDriveNetworkNode(Level level, BlockPos pos) {
        super(level, pos);
    }

    public IStorageDisk[] getItemDisks() {
        return itemDisks;
    }

    public IStorageDisk[] getFluidDisks() {
        return fluidDisks;
    }

    @Override
    public int getEnergyUsage() {
        int usage = MS.SERVER_CONFIG.getDiskDrive().getUsage();

        for (IStorage storage : itemDisks) {
            if (storage != null) {
                usage += MS.SERVER_CONFIG.getDiskDrive().getDiskUsage();
            }
        }
        for (IStorage storage : fluidDisks) {
            if (storage != null) {
                usage += MS.SERVER_CONFIG.getDiskDrive().getDiskUsage();
            }
        }

        return usage;
    }

    @Override
    public void update() {
        super.update();

        if (blockUpdateRequested) {
            ++ticksSinceBlockUpdateRequested;

            if (ticksSinceBlockUpdateRequested > DISK_STATE_UPDATE_THROTTLE) {
                LevelUtils.updateBlock(level, pos);

                this.blockUpdateRequested = false;
                this.ticksSinceBlockUpdateRequested = 0;
            }
        } else {
            this.ticksSinceBlockUpdateRequested = 0;
        }
    }

    void requestBlockUpdate() {
        this.blockUpdateRequested = true;
    }

    @Override
    public void onConnectedStateChange(INetwork network, boolean state, ConnectivityStateChangeCause cause) {
        super.onConnectedStateChange(network, state, cause);

        LOGGER.debug("Connectivity state of disk drive at {} changed to {} due to {}", pos, state, cause);

        network.getNodeGraph().runActionWhenPossible(ItemStorageCache.INVALIDATE_ACTION.apply(InvalidateCause.CONNECTED_STATE_CHANGED));
        network.getNodeGraph().runActionWhenPossible(FluidStorageCache.INVALIDATE_ACTION.apply(InvalidateCause.CONNECTED_STATE_CHANGED));

        LevelUtils.updateBlock(level, pos);
    }

    @Override
    public void addItemStorages(List<IStorage<ItemStack>> storages) {
        for (IStorage<ItemStack> storage : this.itemDisks) {
            if (storage != null) {
                storages.add(storage);
            }
        }
    }

    @Override
    public void addFluidStorages(List<IStorage<FluidStack>> storages) {
        for (IStorage<FluidStack> storage : this.fluidDisks) {
            if (storage != null) {
                storages.add(storage);
            }
        }
    }

    @Override
    public void read(CompoundTag tag) {
        super.read(tag);

        StackUtils.readItems(disks, 0, tag);
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public CompoundTag write(CompoundTag tag) {
        super.write(tag);

        StackUtils.writeItems(disks, 0, tag);

        return tag;
    }

    @Override
    public CompoundTag writeConfiguration(CompoundTag tag) {
        super.writeConfiguration(tag);

        StackUtils.writeItems(itemFilters, 1, tag);

        tag.put(NBT_FLUID_FILTERS, fluidFilters.writeToNbt());
        tag.putInt(NBT_PRIORITY, priority);
        tag.putInt(NBT_COMPARE, compare);
        tag.putInt(NBT_MODE, mode);
        tag.putInt(NBT_TYPE, type);

        AccessTypeUtils.writeAccessType(tag, accessType);

        return tag;
    }

    @Override
    public void readConfiguration(CompoundTag tag) {
        super.readConfiguration(tag);

        StackUtils.readItems(itemFilters, 1, tag);

        if (tag.contains(NBT_FLUID_FILTERS)) {
            fluidFilters.readFromNbt(tag.getCompound(NBT_FLUID_FILTERS));
        }

        if (tag.contains(NBT_PRIORITY)) {
            priority = tag.getInt(NBT_PRIORITY);
        }

        if (tag.contains(NBT_COMPARE)) {
            compare = tag.getInt(NBT_COMPARE);
        }

        if (tag.contains(NBT_MODE)) {
            mode = tag.getInt(NBT_MODE);
        }

        if (tag.contains(NBT_TYPE)) {
            type = tag.getInt(NBT_TYPE);
        }

        accessType = AccessTypeUtils.readAccessType(tag);
    }

    @Override
    public int getCompare() {
        return compare;
    }

    @Override
    public void setCompare(int compare) {
        this.compare = compare;

        markDirty();
    }

    @Override
    public int getWhitelistBlacklistMode() {
        return mode;
    }

    @Override
    public void setWhitelistBlacklistMode(int mode) {
        this.mode = mode;

        markDirty();
    }

    @Override
    public AccessType getAccessType() {
        return accessType;
    }

    @Override
    public void setAccessType(AccessType value) {
        this.accessType = value;

        if (network != null) {
            network.getFluidStorageCache().invalidate(InvalidateCause.DEVICE_CONFIGURATION_CHANGED);
            network.getItemStorageCache().invalidate(InvalidateCause.DEVICE_CONFIGURATION_CHANGED);
        }

        markDirty();
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public void setPriority(int priority) {
        this.priority = priority;

        markDirty();

        if (network != null) {
            network.getItemStorageCache().sort();
            network.getFluidStorageCache().sort();
        }
    }

    public DiskState[] getDiskState() {
        DiskState[] diskStates = new DiskState[8];

        for (int i = 0; i < 8; ++i) {
            DiskState state = DiskState.NONE;

            if (itemDisks[i] != null || fluidDisks[i] != null) {
                if (!canUpdate()) {
                    state = DiskState.DISCONNECTED;
                } else {
                    state = DiskState.get(
                        itemDisks[i] != null ? itemDisks[i].getStored() : fluidDisks[i].getStored(),
                        itemDisks[i] != null ? itemDisks[i].getCapacity() : fluidDisks[i].getCapacity()
                    );
                }
            }

            diskStates[i] = state;
        }

        return diskStates;
    }

    public IItemHandler getDisks() {
        return disks;
    }

    @Override
    public int getType() {
        return level.isClientSide ? DiskDriveBlockEntity.TYPE.getValue() : type;
    }

    @Override
    public void setType(int type) {
        this.type = type;

        markDirty();
    }

    @Override
    public IItemHandlerModifiable getItemFilters() {
        return itemFilters;
    }

    @Override
    public FluidInventory getFluidFilters() {
        return fluidFilters;
    }

    @Override
    public IItemHandler getDrops() {
        return getDisks();
    }
}
