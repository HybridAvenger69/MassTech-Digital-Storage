package com.hybridavenger69.mtstorage.apiimpl.storage.cache.listener;

import com.hybridavenger69.mtstorage.MS;
import com.hybridavenger69.mtstorage.api.network.INetwork;
import com.hybridavenger69.mtstorage.api.network.security.Permission;
import com.hybridavenger69.mtstorage.api.storage.cache.IStorageCacheListener;
import com.hybridavenger69.mtstorage.api.util.StackListResult;
import com.hybridavenger69.mtstorage.network.grid.GridFluidDeltaMessage;
import com.hybridavenger69.mtstorage.network.grid.GridFluidUpdateMessage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class FluidGridStorageCacheListener implements IStorageCacheListener<FluidStack> {
    private final ServerPlayer player;
    private final INetwork network;

    public FluidGridStorageCacheListener(ServerPlayer player, INetwork network) {
        this.player = player;
        this.network = network;
    }

    @Override
    public void onAttached() {
        MS.NETWORK_HANDLER.sendTo(player, new GridFluidUpdateMessage(network, network.getSecurityManager().hasPermission(Permission.AUTOCRAFTING, player)));
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
    public void onChangedBulk(List<StackListResult<FluidStack>> deltas) {
        MS.NETWORK_HANDLER.sendTo(player, new GridFluidDeltaMessage(network, deltas));
    }
}
