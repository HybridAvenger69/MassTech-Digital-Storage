package com.hybridavenger69.mtstorage.blockentity.config;

import com.hybridavenger69.mtstorage.api.network.node.INetworkNodeProxy;
import com.hybridavenger69.mtstorage.api.storage.AccessType;
import com.hybridavenger69.mtstorage.blockentity.data.RSSerializers;
import com.hybridavenger69.mtstorage.blockentity.data.BlockEntitySynchronizationParameter;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface IAccessType {
    static <T extends BlockEntity & INetworkNodeProxy<?>> BlockEntitySynchronizationParameter<AccessType, T> createParameter() {
        return new BlockEntitySynchronizationParameter<>(RSSerializers.ACCESS_TYPE_SERIALIZER, AccessType.INSERT_EXTRACT, t -> ((IAccessType) t.getNode()).getAccessType(), (t, v) -> ((IAccessType) t.getNode()).setAccessType(v));
    }

    AccessType getAccessType();

    void setAccessType(AccessType accessType);
}