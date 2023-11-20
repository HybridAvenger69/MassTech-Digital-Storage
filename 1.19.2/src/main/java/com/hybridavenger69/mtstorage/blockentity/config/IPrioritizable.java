package com.hybridavenger69.mtstorage.blockentity.config;

import com.hybridavenger69.mtstorage.api.network.node.INetworkNodeProxy;
import com.hybridavenger69.mtstorage.blockentity.data.BlockEntitySynchronizationParameter;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface IPrioritizable {
    static <T extends BlockEntity & INetworkNodeProxy<?>> BlockEntitySynchronizationParameter<Integer, T> createParameter() {
        return new BlockEntitySynchronizationParameter<>(EntityDataSerializers.INT, 0, t -> ((IPrioritizable) t.getNode()).getPriority(), (t, v) -> ((IPrioritizable) t.getNode()).setPriority(v));
    }

    int getPriority();

    void setPriority(int priority);
}
