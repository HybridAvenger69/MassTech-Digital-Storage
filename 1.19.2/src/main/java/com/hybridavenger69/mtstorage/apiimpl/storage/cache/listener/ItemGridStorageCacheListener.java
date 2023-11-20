package com.hybridavenger69.mtstorage.apiimpl.storage.cache.listener;

import com.hybridavenger69.mtstorage.MS;
import com.hybridavenger69.mtstorage.api.network.INetwork;
import com.hybridavenger69.mtstorage.api.network.security.Permission;
import com.hybridavenger69.mtstorage.api.storage.cache.IStorageCacheListener;
import com.hybridavenger69.mtstorage.api.util.StackListResult;
import com.hybridavenger69.mtstorage.network.grid.GridItemDeltaMessage;
import com.hybridavenger69.mtstorage.network.grid.GridItemUpdateMessage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemGridStorageCacheListener implements IStorageCacheListener<ItemStack> {
    private final ServerPlayer player;
    private final INetwork network;

    public ItemGridStorageCacheListener(ServerPlayer player, INetwork network) {
        this.player = player;
        this.network = network;
    }

    @Override
    public void onAttached() {
        MS.NETWORK_HANDLER.sendTo(player, new GridItemUpdateMessage(network, network.getSecurityManager().hasPermission(Permission.AUTOCRAFTING, player)));
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
    public void onChangedBulk(List<StackListResult<ItemStack>> deltas) {
        MS.NETWORK_HANDLER.sendTo(player, new GridItemDeltaMessage(network, deltas));
    }
}
