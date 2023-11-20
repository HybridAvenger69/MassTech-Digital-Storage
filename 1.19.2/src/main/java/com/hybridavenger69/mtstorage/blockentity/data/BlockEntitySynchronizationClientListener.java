package com.hybridavenger69.mtstorage.blockentity.data;

public interface BlockEntitySynchronizationClientListener<T> {
    void onChanged(boolean initial, T value);
}
