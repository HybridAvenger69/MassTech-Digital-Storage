package com.hybridavenger69.mtstorage.api.storage.disk;

/**
 * Listens to storage disk changes.
 */
public interface IStorageDiskListener {
    /**
     * Called when any change happens.
     */
    void onChanged();
}
