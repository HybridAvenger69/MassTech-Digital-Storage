package com.hybridavenger69.mtstorage.apiimpl.storage.cache.listener;

import com.hybridavenger69.mtstorage.MS;
import com.hybridavenger69.mtstorage.api.storage.cache.IStorageCacheListener;
import com.hybridavenger69.mtstorage.api.util.StackListResult;
import com.hybridavenger69.mtstorage.network.grid.PortableGridItemDeltaMessage;
import com.hybridavenger69.mtstorage.network.grid.PortableGridItemUpdateMessage;
import com.hybridavenger69.mtstorage.blockentity.grid.portable.IPortableGrid;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PortableItemGridStorageCacheListener implements IStorageCacheListener<ItemStack> {
    private final IPortableGrid portableGrid;
    private final ServerPlayer player;

    public PortableItemGridStorageCacheListener(IPortableGrid portableGrid, ServerPlayer player) {
        this.portableGrid = portableGrid;
        this.player = player;
    }

    @Override
    public void onAttached() {
        MS.NETWORK_HANDLER.sendTo(player, new PortableGridItemUpdateMessage(portableGrid));
    }

    @Override
    public void onInvalidated() {
        // NO OP
    }

    @Override
    public void onChanged(StackListResult<ItemStack> delta) {
        List<StackListResult<ItemStack>> deltas = new ArrayList<>();

        deltas.add(delta);

        onChangedBulk(deltas);
    }

    @Override
    public void onChangedBulk(List<StackListResult<ItemStack>> storageCacheDeltas) {
        MS.NETWORK_HANDLER.sendTo(player, new PortableGridItemDeltaMessage(portableGrid, storageCacheDeltas));
    }
}
