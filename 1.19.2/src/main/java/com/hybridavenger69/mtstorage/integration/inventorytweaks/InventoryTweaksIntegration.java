package com.hybridavenger69.mtstorage.integration.inventorytweaks;

import net.minecraftforge.fml.ModList;

public class InventoryTweaksIntegration {
    private InventoryTweaksIntegration() {
    }

    public static boolean isLoaded() {
        return ModList.get().isLoaded("inventorytweaks");
    }
}
