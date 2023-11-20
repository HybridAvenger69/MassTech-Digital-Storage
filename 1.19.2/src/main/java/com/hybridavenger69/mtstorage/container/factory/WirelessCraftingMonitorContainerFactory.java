package com.hybridavenger69.mtstorage.container.factory;

import com.hybridavenger69.mtstorage.MSContainerMenus;
import com.hybridavenger69.mtstorage.container.CraftingMonitorContainerMenu;
import com.hybridavenger69.mtstorage.inventory.player.PlayerSlot;
import com.hybridavenger69.mtstorage.blockentity.craftingmonitor.WirelessCraftingMonitor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.IContainerFactory;

public class WirelessCraftingMonitorContainerFactory implements IContainerFactory<CraftingMonitorContainerMenu> {
    @Override
    public CraftingMonitorContainerMenu create(int windowId, Inventory inv, FriendlyByteBuf data) {

        PlayerSlot slot = new PlayerSlot(data);

        ItemStack stack = slot.getStackFromSlot(inv.player);

        WirelessCraftingMonitor wirelessCraftingMonitor = new WirelessCraftingMonitor(stack, null, slot);

        return new CraftingMonitorContainerMenu(MSContainerMenus.WIRELESS_CRAFTING_MONITOR.get(), wirelessCraftingMonitor, null, inv.player, windowId);
    }
}
