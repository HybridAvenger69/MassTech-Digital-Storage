package com.hybridavenger69.mtstorage.screen;

import com.hybridavenger69.mtstorage.screen.widget.sidebutton.CrafterModeSideButton;
import com.hybridavenger69.mtstorage.blockentity.data.BlockEntitySynchronizationClientListener;

public class CrafterBlockEntitySynchronizationClientListener implements BlockEntitySynchronizationClientListener<Boolean> {
    @Override
    public void onChanged(boolean initial, Boolean hasRoot) {
        if (Boolean.FALSE.equals(hasRoot)) {
            BaseScreen.executeLater(CrafterScreen.class, gui -> gui.addSideButton(new CrafterModeSideButton(gui)));
        }
    }
}
