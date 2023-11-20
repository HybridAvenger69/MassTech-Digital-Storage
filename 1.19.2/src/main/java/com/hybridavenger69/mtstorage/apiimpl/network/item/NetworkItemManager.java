package com.hybridavenger69.mtstorage.apiimpl.network.item;

import com.hybridavenger69.mtstorage.api.network.INetwork;
import com.hybridavenger69.mtstorage.api.network.INetworkNodeGraphEntry;
import com.hybridavenger69.mtstorage.api.network.IWirelessTransmitter;
import com.hybridavenger69.mtstorage.api.network.item.INetworkItem;
import com.hybridavenger69.mtstorage.api.network.item.INetworkItemManager;
import com.hybridavenger69.mtstorage.api.network.item.INetworkItemProvider;
import com.hybridavenger69.mtstorage.api.network.node.INetworkNode;
import com.hybridavenger69.mtstorage.inventory.player.PlayerSlot;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NetworkItemManager implements INetworkItemManager {
    private final INetwork network;
    private final Map<Player, INetworkItem> items = new ConcurrentHashMap<>();

    public NetworkItemManager(INetwork network) {
        this.network = network;
    }

    @Override
    public void open(Player player, ItemStack stack, PlayerSlot slot) {
        boolean inRange = false;

        for (INetworkNodeGraphEntry entry : network.getNodeGraph().all()) {
            INetworkNode node = entry.getNode();

            if (node instanceof IWirelessTransmitter &&
                network.canRun() &&
                node.isActive() &&
                ((IWirelessTransmitter) node).getDimension() == player.getCommandSenderWorld().dimension()) {
                IWirelessTransmitter transmitter = (IWirelessTransmitter) node;

                Vec3 pos = player.position();

                double distance = Math.sqrt(Math.pow(transmitter.getOrigin().getX() - pos.x(), 2) + Math.pow(transmitter.getOrigin().getY() - pos.y(), 2) + Math.pow(transmitter.getOrigin().getZ() - pos.z(), 2));

                if (distance < transmitter.getRange()) {
                    inRange = true;

                    break;
                }
            }
        }

        if (!inRange) {
            player.sendSystemMessage(Component.translatable("misc.mtstorage.network_item.out_of_range"));

            return;
        }

        INetworkItem item = ((INetworkItemProvider) stack.getItem()).provide(this, player, stack, slot);

        if (item.onOpen(network)) {
            items.put(player, item);
        }
    }

    @Override
    public void close(Player player) {
        items.remove(player);
    }

    @Override
    public INetworkItem getItem(Player player) {
        return items.get(player);
    }

    @Override
    public void drainEnergy(Player player, int energy) {
        INetworkItem item = getItem(player);

        if (item != null) {
            item.drainEnergy(energy);
        }
    }
}
