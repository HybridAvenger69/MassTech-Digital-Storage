package com.hybridavenger69.mtstorage.screen.widget.sidebutton;

import com.hybridavenger69.mtstorage.screen.CrafterManagerScreen;
import com.hybridavenger69.mtstorage.blockentity.CrafterManagerBlockEntity;
import com.hybridavenger69.mtstorage.blockentity.data.BlockEntitySynchronizationManager;

public class CrafterManagerSearchBoxModeSideButton extends SearchBoxModeSideButton {
    public CrafterManagerSearchBoxModeSideButton(CrafterManagerScreen screen) {
        super(screen);
    }

    @Override
    protected int getSearchBoxMode() {
        return ((CrafterManagerScreen) screen).getCrafterManager().getSearchBoxMode();
    }

    @Override
    protected void setSearchBoxMode(int mode) {
        BlockEntitySynchronizationManager.setParameter(CrafterManagerBlockEntity.SEARCH_BOX_MODE, mode);
    }
}
