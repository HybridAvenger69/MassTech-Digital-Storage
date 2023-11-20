package com.hybridavenger69.mtstorage;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import org.lwjgl.glfw.GLFW;

public final class MSKeyBindings {
    public static final KeyMapping FOCUS_SEARCH_BAR = new KeyMapping(
        "key.mtstorage.focusSearchBar",
        KeyConflictContext.GUI,
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_TAB,
        MS.NAME
    );

    public static final KeyMapping CLEAR_GRID_CRAFTING_MATRIX = new KeyMapping(
        "key.mtstorage.clearGridCraftingMatrix",
        KeyConflictContext.GUI,
        KeyModifier.CONTROL,
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_X,
        MS.NAME
    );

    public static final KeyMapping OPEN_WIRELESS_GRID = new KeyMapping(
        "key.mtstorage.openWirelessGrid",
        KeyConflictContext.IN_GAME,
        InputConstants.UNKNOWN,
        MS.NAME
    );

    public static final KeyMapping OPEN_WIRELESS_FLUID_GRID = new KeyMapping(
        "key.mtstorage.openWirelessFluidGrid",
        KeyConflictContext.IN_GAME,
        InputConstants.UNKNOWN,
        MS.NAME
    );

    public static final KeyMapping OPEN_WIRELESS_CRAFTING_MONITOR = new KeyMapping(
        "key.mtstorage.openWirelessCraftingMonitor",
        KeyConflictContext.IN_GAME,
        InputConstants.UNKNOWN,
        MS.NAME
    );

    public static final KeyMapping OPEN_PORTABLE_GRID = new KeyMapping(
        "key.mtstorage.openPortableGrid",
        KeyConflictContext.IN_GAME,
        InputConstants.UNKNOWN,
        MS.NAME
    );

    private MSKeyBindings() {
    }
}
