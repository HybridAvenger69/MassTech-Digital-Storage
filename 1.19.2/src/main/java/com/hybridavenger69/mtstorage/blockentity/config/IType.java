package com.hybridavenger69.mtstorage.blockentity.config;

import com.hybridavenger69.mtstorage.api.network.node.INetworkNodeProxy;
import com.hybridavenger69.mtstorage.inventory.fluid.FluidInventory;
import com.hybridavenger69.mtstorage.blockentity.data.BlockEntitySynchronizationParameter;
import com.hybridavenger69.mtstorage.blockentity.data.BlockEntitySynchronizationClientListener;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nullable;

public interface IType {
    int ITEMS = 0;
    int FLUIDS = 1;

    static <T extends BlockEntity & INetworkNodeProxy<?>> BlockEntitySynchronizationParameter<Integer, T> createParameter(@Nullable BlockEntitySynchronizationClientListener<Integer> clientListener) {
        return new BlockEntitySynchronizationParameter<>(EntityDataSerializers.INT, ITEMS, t -> ((IType) t.getNode()).getType(), (t, v) -> {
            if (v == IType.ITEMS || v == IType.FLUIDS) {
                ((IType) t.getNode()).setType(v);
            }
        }, clientListener);
    }

    static <T extends BlockEntity & INetworkNodeProxy<?>> BlockEntitySynchronizationParameter<Integer, T> createParameter() {
        return createParameter(null);
    }

    int getType();

    void setType(int type);

    IItemHandlerModifiable getItemFilters();

    FluidInventory getFluidFilters();
}
