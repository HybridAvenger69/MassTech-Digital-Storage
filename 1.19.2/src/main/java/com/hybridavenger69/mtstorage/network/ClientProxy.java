package com.hybridavenger69.mtstorage.network;

import com.hybridavenger69.mtstorage.network.craftingmonitor.CraftingMonitorUpdateMessage;
import com.hybridavenger69.mtstorage.network.grid.GridCraftingPreviewResponseMessage;
import com.hybridavenger69.mtstorage.screen.BaseScreen;
import com.hybridavenger69.mtstorage.screen.CraftingMonitorScreen;
import com.hybridavenger69.mtstorage.screen.grid.CraftingPreviewScreen;
import com.hybridavenger69.mtstorage.screen.grid.CraftingSettingsScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;


public class ClientProxy {
    private ClientProxy() {
    }

    public static void onReceivedCraftingPreviewResponseMessage(GridCraftingPreviewResponseMessage message) {
        Screen parent = Minecraft.getInstance().screen;

        if (parent instanceof CraftingSettingsScreen) {
            parent = ((CraftingSettingsScreen) parent).getParent();
        }

        Minecraft.getInstance().setScreen(new CraftingPreviewScreen(
            parent,
            message.getElements(),
            message.getId(),
            message.getQuantity(),
            message.isFluids(),
            Component.translatable("gui.mtstorage.crafting_preview"),
            Minecraft.getInstance().player.getInventory()
        ));
    }

    public static void onReceivedCraftingStartResponseMessage() {
        Screen screen = Minecraft.getInstance().screen;

        if (screen instanceof CraftingSettingsScreen) {
            ((CraftingSettingsScreen) screen).close();
        }
    }

    public static void onReceivedCraftingMonitorUpdateMessage(CraftingMonitorUpdateMessage message) {
        BaseScreen.executeLater(CraftingMonitorScreen.class, craftingMonitor -> craftingMonitor.setTasks(message.getTasks()));
    }
}
