package com.hybridavenger69.mtstorage.screen.widget.sidebutton;

import com.hybridavenger69.mtstorage.screen.grid.GridScreen;

public class GridSearchBoxModeSideButton extends SearchBoxModeSideButton {
    public GridSearchBoxModeSideButton(GridScreen screen) {
        super(screen);
    }

    @Override
    protected int getSearchBoxMode() {
        return ((GridScreen) screen).getGrid().getSearchBoxMode();
    }

    @Override
    protected void setSearchBoxMode(int mode) {
        ((GridScreen) screen).getGrid().onSearchBoxModeChanged(mode);
        ((GridScreen) screen).getSearchField().setMode(mode);
    }
}
