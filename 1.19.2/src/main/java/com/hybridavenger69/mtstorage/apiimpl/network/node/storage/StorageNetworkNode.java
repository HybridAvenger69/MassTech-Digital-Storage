package com.hybridavenger69.mtstorage.apiimpl.network.node.storage;

import com.hybridavenger69.mtstorage.MS;
import com.hybridavenger69.mtstorage.api.network.INetwork;
import com.hybridavenger69.mtstorage.api.storage.AccessType;
import com.hybridavenger69.mtstorage.api.storage.IStorage;
import com.hybridavenger69.mtstorage.api.storage.IStorageProvider;
import com.hybridavenger69.mtstorage.api.storage.cache.InvalidateCause;
import com.hybridavenger69.mtstorage.api.storage.disk.IStorageDisk;
import com.hybridavenger69.mtstorage.api.storage.disk.IStorageDiskContainerContext;
import com.hybridavenger69.mtstorage.api.util.IComparer;
import com.hybridavenger69.mtstorage.apiimpl.API;
import com.hybridavenger69.mtstorage.apiimpl.network.node.ConnectivityStateChangeCause;
import com.hybridavenger69.mtstorage.apiimpl.network.node.IStorageScreen;
import com.hybridavenger69.mtstorage.apiimpl.network.node.NetworkNode;
import com.hybridavenger69.mtstorage.apiimpl.storage.ItemStorageType;
import com.hybridavenger69.mtstorage.apiimpl.storage.cache.ItemStorageCache;
import com.hybridavenger69.mtstorage.blockentity.StorageBlockEntity;
import com.hybridavenger69.mtstorage.blockentity.config.IAccessType;
import com.hybridavenger69.mtstorage.blockentity.config.IComparable;
import com.hybridavenger69.mtstorage.blockentity.config.IPrioritizable;
import com.hybridavenger69.mtstorage.blockentity.config.IWhitelistBlacklist;
import com.hybridavenger69.mtstorage.inventory.item.BaseItemHandler;
import com.hybridavenger69.mtstorage.inventory.listener.NetworkNodeInventoryListener;
import com.hybridavenger69.mtstorage.util.AccessTypeUtils;
import com.hybridavenger69.mtstorage.util.StackUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class StorageNetworkNode extends NetworkNode implements IStorageScreen, IStorageProvider, IComparable, IWhitelistBlacklist, IPrioritizable, IAccessType, IStorageDiskContainerContext {
    public static final ResourceLocation ONE_K_STORAGE_BLOCK_ID = new ResourceLocation(MS.ID, "1k_storage_block");
    public static final ResourceLocation FOUR_K_STORAGE_BLOCK_ID = new ResourceLocation(MS.ID, "4k_storage_block");
    public static final ResourceLocation SIXTEEN_K_STORAGE_BLOCK_ID = new ResourceLocation(MS.ID, "16k_storage_block");
    public static final ResourceLocation SIXTY_FOUR_K_STORAGE_BLOCK_ID = new ResourceLocation(MS.ID, "64k_storage_block");
    public static final ResourceLocation ONE_TWENTY_EIGHT_K_STORAGE_BLOCK_ID = new ResourceLocation(MS.ID, "128k_storage_block");
    public static final ResourceLocation TWO_FIFTY_SIX_K_STORAGE_BLOCK_ID = new ResourceLocation(MS.ID, "256k_storage_block");
    public static final ResourceLocation FIVE_TWELVE_K_STORAGE_BLOCK_ID = new ResourceLocation(MS.ID, "512k_storage_block");
    public static final ResourceLocation ONE_ZERO_TWENTY_FOUR_K_STORAGE_BLOCK_ID = new ResourceLocation(MS.ID, "1024k_storage_block");
    public static final ResourceLocation TWO_ZERO_FOURTY_EIGHT_K_STORAGE_BLOCK_ID = new ResourceLocation(MS.ID, "2048k_storage_block");
    public static final ResourceLocation CREATIVE_STORAGE_BLOCK_ID = new ResourceLocation(MS.ID, "creative_storage_block");
    public static final String NBT_ID = "Id";
    private static final Logger LOGGER = LogManager.getLogger(StorageNetworkNode.class);
    private static final String NBT_PRIORITY = "Priority";
    private static final String NBT_COMPARE = "Compare";
    private static final String NBT_MODE = "Mode";
    private final BaseItemHandler filters = new BaseItemHandler(9).addListener(new NetworkNodeInventoryListener(this));

    private final ItemStorageType type;

    private AccessType accessType = AccessType.INSERT_EXTRACT;
    private int priority = 0;
    private int compare = IComparer.COMPARE_NBT;
    private int mode = IWhitelistBlacklist.BLACKLIST;

    private UUID storageId = UUID.randomUUID();
    private IStorageDisk<ItemStack> storage;

    public StorageNetworkNode(Level level, BlockPos pos, ItemStorageType type) {
        super(level, pos);

        this.type = type;
    }

    public static ResourceLocation getId(ItemStorageType type) {
        switch (type) {
            case ONE_K:
                return ONE_K_STORAGE_BLOCK_ID;
            case FOUR_K:
                return FOUR_K_STORAGE_BLOCK_ID;
            case SIXTEEN_K:
                return SIXTEEN_K_STORAGE_BLOCK_ID;
            case SIXTY_FOUR_K:
                return SIXTY_FOUR_K_STORAGE_BLOCK_ID;
            case ONE_TWENTY_EIGHT_K:
                return ONE_TWENTY_EIGHT_K_STORAGE_BLOCK_ID;
            case TWO_FIFTY_SIX_K:
                return TWO_FIFTY_SIX_K_STORAGE_BLOCK_ID;
            case FIVE_TWELVE_K:
                return FIVE_TWELVE_K_STORAGE_BLOCK_ID;
            case ONE_ZERO_TWENTY_FOUR_K:
                return  ONE_ZERO_TWENTY_FOUR_K_STORAGE_BLOCK_ID;
            case TWO_ZERO_FOUR_EIGHT_K:
                return TWO_ZERO_FOURTY_EIGHT_K_STORAGE_BLOCK_ID;
            case CREATIVE:
                return CREATIVE_STORAGE_BLOCK_ID;
            default:
                throw new IllegalArgumentException("Unknown storage type " + type);
        }
    }

    @Override
    public int getEnergyUsage() {
        switch (type) {
            case ONE_K:
                return MS.SERVER_CONFIG.getStorageBlock().getOneKUsage();
            case FOUR_K:
                return MS.SERVER_CONFIG.getStorageBlock().getFourKUsage();
            case SIXTEEN_K:
                return MS.SERVER_CONFIG.getStorageBlock().getSixteenKUsage();
            case SIXTY_FOUR_K:
                return MS.SERVER_CONFIG.getStorageBlock().getSixtyFourKUsage();
            case ONE_TWENTY_EIGHT_K:
                return MS.SERVER_CONFIG.getStorageBlock().getOneTwentyEightKUsage();
            case TWO_FIFTY_SIX_K:
                return MS.SERVER_CONFIG.getStorageBlock().getTwoFiftySixkusage();
            case FIVE_TWELVE_K:
                return  MS.SERVER_CONFIG.getStorageBlock().getFiveTwelvekUsage();
            case ONE_ZERO_TWENTY_FOUR_K:
                return  MS.SERVER_CONFIG.getStorageBlock().getOneZeroTwentyFourkUsage();
            case TWO_ZERO_FOUR_EIGHT_K:
                return MS.SERVER_CONFIG.getStorageBlock().getTwoZeroFourtyEightKUsage();
            case CREATIVE:
                return MS.SERVER_CONFIG.getStorageBlock().getCreativeUsage();

            default:
                return 0;
        }
    }

    @Override
    public void onConnectedStateChange(INetwork network, boolean state, ConnectivityStateChangeCause cause) {
        super.onConnectedStateChange(network, state, cause);

        LOGGER.debug("Connectivity state of item storage block at {} changed to {} due to {}", pos, state, cause);

        network.getNodeGraph().runActionWhenPossible(ItemStorageCache.INVALIDATE_ACTION.apply(InvalidateCause.CONNECTED_STATE_CHANGED));
    }

    @Override
    public void addItemStorages(List<IStorage<ItemStack>> storages) {
        if (storage == null) {
            loadStorage(null);
        }

        storages.add(storage);
    }

    @Override
    public void addFluidStorages(List<IStorage<FluidStack>> storages) {
        // NO OP
    }

    @Override
    public ResourceLocation getId() {
        return getId(type);
    }

    @Override
    public CompoundTag write(CompoundTag tag) {
        super.write(tag);

        tag.putUUID(NBT_ID, storageId);

        return tag;
    }

    @Override
    public void read(CompoundTag tag) {
        super.read(tag);

        if (tag.hasUUID(NBT_ID)) {
            storageId = tag.getUUID(NBT_ID);

            loadStorage(null);
        }
    }

    public void loadStorage(@Nullable Player owner) {
        IStorageDisk disk = API.instance().getStorageDiskManager((ServerLevel) level).get(storageId);

        if (disk == null) {
            disk = API.instance().createDefaultItemDisk((ServerLevel) level, type.getCapacity(), owner);

            API.instance().getStorageDiskManager((ServerLevel) level).set(storageId, disk);
            API.instance().getStorageDiskManager((ServerLevel) level).markForSaving();
        }

        this.storage = new ItemStorageWrapperStorageDisk(this, disk);
    }

    public UUID getStorageId() {
        return storageId;
    }

    public void setStorageId(UUID id) {
        this.storageId = id;

        markDirty();
    }

    public IStorageDisk<ItemStack> getStorage() {
        return storage;
    }

    @Override
    public CompoundTag writeConfiguration(CompoundTag tag) {
        super.writeConfiguration(tag);

        StackUtils.writeItems(filters, 0, tag);

        tag.putInt(NBT_PRIORITY, priority);
        tag.putInt(NBT_COMPARE, compare);
        tag.putInt(NBT_MODE, mode);

        AccessTypeUtils.writeAccessType(tag, accessType);

        return tag;
    }

    @Override
    public void readConfiguration(CompoundTag tag) {
        super.readConfiguration(tag);

        StackUtils.readItems(filters, 0, tag);

        if (tag.contains(NBT_PRIORITY)) {
            priority = tag.getInt(NBT_PRIORITY);
        }

        if (tag.contains(NBT_COMPARE)) {
            compare = tag.getInt(NBT_COMPARE);
        }

        if (tag.contains(NBT_MODE)) {
            mode = tag.getInt(NBT_MODE);
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

    public BaseItemHandler getFilters() {
        return filters;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.mtstorage." + type.getName() + "_storage_block");
    }

    @Override
    public long getStored() {
        return StorageBlockEntity.STORED.getValue();
    }

    @Override
    public long getCapacity() {
        return type.getCapacity();
    }

    @Override
    public AccessType getAccessType() {
        return accessType;
    }

    @Override
    public void setAccessType(AccessType value) {
        this.accessType = value;

        if (network != null) {
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
        }
    }
}
