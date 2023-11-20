package com.hybridavenger69.mtstorage.blockentity;

import com.hybridavenger69.mtstorage.MSBlockEntities;
import com.hybridavenger69.mtstorage.api.storage.AccessType;
import com.hybridavenger69.mtstorage.apiimpl.network.node.storage.StorageNetworkNode;
import com.hybridavenger69.mtstorage.apiimpl.storage.ItemStorageType;
import com.hybridavenger69.mtstorage.blockentity.config.IAccessType;
import com.hybridavenger69.mtstorage.blockentity.config.IComparable;
import com.hybridavenger69.mtstorage.blockentity.config.IPrioritizable;
import com.hybridavenger69.mtstorage.blockentity.config.IWhitelistBlacklist;
import com.hybridavenger69.mtstorage.blockentity.data.BlockEntitySynchronizationParameter;
import com.hybridavenger69.mtstorage.blockentity.data.BlockEntitySynchronizationSpec;
import com.hybridavenger69.mtstorage.blockentity.data.RSSerializers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class StorageBlockEntity extends NetworkNodeBlockEntity<StorageNetworkNode> {
    public static final BlockEntitySynchronizationParameter<Integer, StorageBlockEntity> PRIORITY = IPrioritizable.createParameter();
    public static final BlockEntitySynchronizationParameter<Integer, StorageBlockEntity> COMPARE = IComparable.createParameter();
    public static final BlockEntitySynchronizationParameter<Integer, StorageBlockEntity> WHITELIST_BLACKLIST = IWhitelistBlacklist.createParameter();
    public static final BlockEntitySynchronizationParameter<AccessType, StorageBlockEntity> ACCESS_TYPE = IAccessType.createParameter();
    public static final BlockEntitySynchronizationParameter<Long, StorageBlockEntity> STORED = new BlockEntitySynchronizationParameter<>(RSSerializers.LONG_SERIALIZER, 0L, t -> t.getNode().getStorage() != null ? (long) t.getNode().getStorage().getStored() : 0);

    public static BlockEntitySynchronizationSpec SPEC = BlockEntitySynchronizationSpec.builder()
        .addWatchedParameter(REDSTONE_MODE)
        .addWatchedParameter(PRIORITY)
        .addWatchedParameter(COMPARE)
        .addWatchedParameter(WHITELIST_BLACKLIST)
        .addWatchedParameter(STORED)
        .addWatchedParameter(ACCESS_TYPE)
        .build();

    private final ItemStorageType type;

    public StorageBlockEntity(ItemStorageType type, BlockPos pos, BlockState state) {
        super(getType(type), pos, state, SPEC, StorageNetworkNode.class);
        this.type = type;
    }

    public static BlockEntityType<StorageBlockEntity> getType(ItemStorageType type) {
        return switch (type) {
            case ONE_K -> MSBlockEntities.ONE_K_STORAGE_BLOCK.get();
            case FOUR_K -> MSBlockEntities.FOUR_K_STORAGE_BLOCK.get();
            case SIXTEEN_K -> MSBlockEntities.SIXTEEN_K_STORAGE_BLOCK.get();
            case SIXTY_FOUR_K -> MSBlockEntities.SIXTY_FOUR_K_STORAGE_BLOCK.get();
            case ONE_TWENTY_EIGHT_K -> MSBlockEntities.ONE_TWENTY_EIGHT_K_STORAGE_BLOCK.get();
            case TWO_FIFTY_SIX_K -> MSBlockEntities.TWO_FIFTY_SIX_K_STORAGE_BLOCK.get();
            case FIVE_TWELVE_K -> MSBlockEntities.FIVE_TWELVE_K_STORAGE_BLOCK.get();
            case ONE_ZERO_TWENTY_FOUR_K -> MSBlockEntities.ONE_ZERO_TWENTY_FOUR_K_STORAGE_BLOCK.get();
            case TWO_ZERO_FOUR_EIGHT_K -> MSBlockEntities.TWO_ZERO_FOUR_EIGHT_K_STORAGE_BLOCK.get();
            case CREATIVE -> MSBlockEntities.CREATIVE_STORAGE_BLOCK.get();
        };
    }

    public ItemStorageType getItemStorageType() {
        return type;
    }

    @Override
    @Nonnull
    public StorageNetworkNode createNode(Level level, BlockPos pos) {
        return new StorageNetworkNode(level, pos, type);
    }
}
