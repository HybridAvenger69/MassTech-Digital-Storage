package com.hybridavenger69.mtstorage.integration.inventorysorter;

import net.minecraftforge.fml.ModList;

public class InventorySorterIntegration {
    private static final String ID = "inventorysorter";

    private InventorySorterIntegration() {
    }

    public static boolean isLoaded() {
        return ModList.get().isLoaded(ID);
    }

    public static void register() {
        // Prevent items moving while scrolling through slots with Inventory Sorter in the Crafter Manager
        // InterModComms.sendTo("inventorysorter", "slotblacklist", () -> "com.hybridavenger69.mtstorage.container.slot.CrafterManagerSlot");
    }
}
