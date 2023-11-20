package com.hybridavenger69.mtstorage.inventory.listener;

public interface InventoryListener<T> {
    void onChanged(T handler, int slot, boolean reading);
}
