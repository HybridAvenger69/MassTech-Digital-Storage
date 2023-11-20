package com.hybridavenger69.mtstorage.apiimpl.storage.cache.listener;

import com.hybridavenger69.mtstorage.MS;
import com.hybridavenger69.mtstorage.api.storage.cache.IStorageCacheListener;
import com.hybridavenger69.mtstorage.api.util.StackListResult;
import com.hybridavenger69.mtstorage.network.grid.PortableGridFluidDeltaMessage;
import com.hybridavenger69.mtstorage.network.grid.PortableGridFluidUpdateMessage;
import com.hybridavenger69.mtstorage.blockentity.grid.portable.IPortableGrid;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class PortableFluidGridStorageCacheListener implements IStorageCacheListener<FluidStack> {
    private final IPortableGrid portableGrid;
    private final ServerPlayer player;

    public PortableFluidGridStorageCacheListener(IPortableGrid portableGrid, ServerPlayer player) {
        this.portableGrid = portableGrid;
        this.player = player;
    }

    @Override
    public void onAttached() {
        MS.NETWORK_HANDLER.sendTo(player, new PortableGridFluidUpdateMessage(portableGrid));
    }

    @Override
    public void onInvalidated() {
        // NO OP
    }

    @Override
    public void onChanged(StackListResult<FluidStack> delta) {
        List<StackListResult<FluidStack>> deltas = new ArrayList<>();

        deltas.add(delta);

        onChangedBulk(deltas);
    }

    @Override
    public void onChangedBulk(List<StackListResult<FluidStack>> storageCacheDeltas) {
        MS.NETWORK_HANDLER.sendTo(player, new PortableGridFluidDeltaMessage(portableGrid, storageCacheDeltas));
    }
}
