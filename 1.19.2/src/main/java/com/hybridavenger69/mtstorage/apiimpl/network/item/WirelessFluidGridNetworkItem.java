package com.hybridavenger69.mtstorage.apiimpl.network.item;

import com.hybridavenger69.mtstorage.MS;
import com.hybridavenger69.mtstorage.api.network.INetwork;
import com.hybridavenger69.mtstorage.api.network.item.INetworkItem;
import com.hybridavenger69.mtstorage.api.network.item.INetworkItemManager;
import com.hybridavenger69.mtstorage.api.network.security.Permission;
import com.hybridavenger69.mtstorage.apiimpl.API;
import com.hybridavenger69.mtstorage.apiimpl.network.grid.factory.WirelessFluidGridGridFactory;
import com.hybridavenger69.mtstorage.inventory.player.PlayerSlot;
import com.hybridavenger69.mtstorage.item.WirelessFluidGridItem;
import com.hybridavenger69.mtstorage.util.LevelUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;

public class WirelessFluidGridNetworkItem implements INetworkItem {
    private final INetworkItemManager handler;
    private final Player player;
    private final ItemStack stack;
    private final PlayerSlot slot;

    public WirelessFluidGridNetworkItem(INetworkItemManager handler, Player player, ItemStack stack, PlayerSlot slot) {
        this.handler = handler;
        this.player = player;
        this.stack = stack;
        this.slot = slot;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean onOpen(INetwork network) {
        IEnergyStorage energy = stack.getCapability(ForgeCapabilities.ENERGY, null).orElse(null);

        if (MS.SERVER_CONFIG.getWirelessFluidGrid().getUseEnergy() &&
            ((WirelessFluidGridItem) stack.getItem()).getType() != WirelessFluidGridItem.Type.CREATIVE &&
            energy != null &&
            energy.getEnergyStored() <= MS.SERVER_CONFIG.getWirelessFluidGrid().getOpenUsage()) {
            sendOutOfEnergyMessage();

            return false;
        }

        if (!network.getSecurityManager().hasPermission(Permission.MODIFY, player)) {
            LevelUtils.sendNoPermissionMessage(player);

            return false;
        }

        API.instance().getGridManager().openGrid(WirelessFluidGridGridFactory.ID, (ServerPlayer) player, stack, slot);

        drainEnergy(MS.SERVER_CONFIG.getWirelessFluidGrid().getOpenUsage());

        return true;
    }

    @Override
    public void drainEnergy(int energy) {
        if (MS.SERVER_CONFIG.getWirelessFluidGrid().getUseEnergy() && ((WirelessFluidGridItem) stack.getItem()).getType() != WirelessFluidGridItem.Type.CREATIVE) {
            stack.getCapability(ForgeCapabilities.ENERGY).ifPresent(energyStorage -> {
                energyStorage.extractEnergy(energy, false);

                if (energyStorage.getEnergyStored() <= 0) {
                    handler.close(player);

                    player.closeContainer();

                    sendOutOfEnergyMessage();
                }
            });
        }
    }

    private void sendOutOfEnergyMessage() {
        player.sendSystemMessage(Component.translatable("misc.mtstorage.network_item.out_of_energy", Component.translatable(stack.getItem().getDescriptionId())));
    }
}