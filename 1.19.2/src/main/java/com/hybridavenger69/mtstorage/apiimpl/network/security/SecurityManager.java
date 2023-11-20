package com.hybridavenger69.mtstorage.apiimpl.network.security;

import com.hybridavenger69.mtstorage.api.network.INetwork;
import com.hybridavenger69.mtstorage.api.network.INetworkNodeGraphEntry;
import com.hybridavenger69.mtstorage.api.network.security.ISecurityCard;
import com.hybridavenger69.mtstorage.api.network.security.ISecurityCardContainer;
import com.hybridavenger69.mtstorage.api.network.security.ISecurityManager;
import com.hybridavenger69.mtstorage.api.network.security.Permission;
import net.minecraft.server.players.ServerOpList;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SecurityManager implements ISecurityManager {
    private final INetwork network;
    private final Map<UUID, ISecurityCard> cards = new HashMap<>();
    private ISecurityCard globalCard;

    public SecurityManager(INetwork network) {
        this.network = network;
    }

    @Override
    public boolean hasPermission(Permission permission, Player player) {
        ServerOpList ops = player.getServer().getPlayerList().getOps();

        if (ops.get(player.getGameProfile()) != null) {
            return true;
        }

        UUID uuid = player.getGameProfile().getId();

        if (!cards.containsKey(uuid)) {
            if (globalCard != null) {
                return globalCard.hasPermission(permission);
            }

            return true;
        }

        return cards.get(uuid).hasPermission(permission);
    }

    @Override
    public void invalidate() {
        this.cards.clear();
        this.globalCard = null;

        for (INetworkNodeGraphEntry entry : network.getNodeGraph().all()) {
            if (entry.getNode() instanceof ISecurityCardContainer && entry.getNode().isActive()) {
                ISecurityCardContainer container = (ISecurityCardContainer) entry.getNode();

                for (ISecurityCard card : container.getCards()) {
                    if (card.getOwner() == null) {
                        throw new IllegalStateException("Owner in #getCards() cannot be null!");
                    }

                    this.cards.put(card.getOwner(), card);
                }

                if (container.getGlobalCard() != null) {
                    this.globalCard = container.getGlobalCard();
                }
            }
        }
    }
}
