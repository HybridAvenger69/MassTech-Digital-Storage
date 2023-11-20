package com.hybridavenger69.mtstorage.api.autocrafting.task;

import com.hybridavenger69.mtstorage.api.network.INetwork;

/**
 * Gets thrown from {@link ICraftingTaskFactory#createFromNbt(INetwork, net.minecraft.nbt.CompoundNBT)}.
 */
public class CraftingTaskReadException extends Exception {
    /**
     * @param message the message
     */
    public CraftingTaskReadException(String message) {
        super(message);
    }
}
