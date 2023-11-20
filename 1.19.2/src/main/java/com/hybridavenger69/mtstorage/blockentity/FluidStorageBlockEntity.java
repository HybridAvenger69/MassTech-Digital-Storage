package com.hybridavenger69.mtstorage.blockentity;

import com.hybridavenger69.mtstorage.MSBlockEntities;
import com.hybridavenger69.mtstorage.api.storage.AccessType;
import com.hybridavenger69.mtstorage.apiimpl.network.node.storage.FluidStorageNetworkNode;
import com.hybridavenger69.mtstorage.apiimpl.storage.FluidStorageType;
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

public class FluidStorageBlockEntity extends NetworkNodeBlockEntity<FluidStorageNetworkNode> {
    public static final BlockEntitySynchronizationParameter<Integer, FluidStorageBlockEntity> PRIORITY = IPrioritizable.createParameter();
    public static final BlockEntitySynchronizationParameter<Integer, FluidStorageBlockEntity> COMPARE = IComparable.createParameter();
    public static final BlockEntitySynchronizationParameter<Integer, FluidStorageBlockEntity> WHITELIST_BLACKLIST = IWhitelistBlacklist.createParameter();
    public static final BlockEntitySynchronizationParameter<AccessType, FluidStorageBlockEntity> ACCESS_TYPE = IAccessType.createParameter();
    public static final BlockEntitySynchronizationParameter<Long, FluidStorageBlockEntity> STORED = new BlockEntitySynchronizationParameter<>(RSSerializers.LONG_SERIALIZER, 0L, t -> t.getNode().getStorage() != null ? (long) t.getNode().getStorage().getStored() : 0);

    public static BlockEntitySynchronizationSpec SPEC = BlockEntitySynchronizationSpec.builder()
        .addWatchedParameter(REDSTONE_MODE)
        .addWatchedParameter(PRIORITY)
        .addWatchedParameter(COMPARE)
        .addWatchedParameter(WHITELIST_BLACKLIST)
        .addWatchedParameter(STORED)
        .addWatchedParameter(ACCESS_TYPE)
        .build();

    private final FluidStorageType type;

    public FluidStorageBlockEntity(FluidStorageType type, BlockPos pos, BlockState state) {
        super(getType(type), pos, state, SPEC, FluidStorageNetworkNode.class);
        this.type = type;
    }

    public static BlockEntityType<FluidStorageBlockEntity> getType(FluidStorageType type) {
        return switch (type) {
            case SIXTY_FOUR_K -> MSBlockEntities.SIXTY_FOUR_K_FLUID_STORAGE_BLOCK.get();
            case TWO_HUNDRED_FIFTY_SIX_K -> MSBlockEntities.TWO_HUNDRED_FIFTY_SIX_K_FLUID_STORAGE_BLOCK.get();
            case THOUSAND_TWENTY_FOUR_K -> MSBlockEntities.THOUSAND_TWENTY_FOUR_K_FLUID_STORAGE_BLOCK.get();
            case FOUR_THOUSAND_NINETY_SIX_K -> MSBlockEntities.FOUR_THOUSAND_NINETY_SIX_K_FLUID_STORAGE_BLOCK.get();
            case CREATIVE -> MSBlockEntities.CREATIVE_FLUID_STORAGE_BLOCK.get();
        };
    }

    public FluidStorageType getFluidStorageType() {
        return type;
    }

    @Override
    @Nonnull
    public FluidStorageNetworkNode createNode(Level level, BlockPos pos) {
        return new FluidStorageNetworkNode(level, pos, type);
    }
}

