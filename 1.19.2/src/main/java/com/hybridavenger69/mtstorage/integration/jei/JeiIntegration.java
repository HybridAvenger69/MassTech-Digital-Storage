package com.hybridavenger69.mtstorage.integration.jei;

import net.minecraftforge.fml.ModList;

public final class JeiIntegration {
    private JeiIntegration() {
    }

    public static boolean isLoaded() {
        return ModList.get().isLoaded("jei");
    }
}

