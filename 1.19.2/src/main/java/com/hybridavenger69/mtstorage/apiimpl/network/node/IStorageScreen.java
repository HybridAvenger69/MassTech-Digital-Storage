package com.hybridavenger69.mtstorage.apiimpl.network.node;

import net.minecraft.network.chat.Component;

public interface IStorageScreen {
    Component getTitle();

    long getStored();

    long getCapacity();
}
