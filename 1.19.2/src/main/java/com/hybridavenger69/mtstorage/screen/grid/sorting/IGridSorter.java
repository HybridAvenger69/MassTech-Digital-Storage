package com.hybridavenger69.mtstorage.screen.grid.sorting;

import com.hybridavenger69.mtstorage.api.network.grid.IGrid;
import com.hybridavenger69.mtstorage.screen.grid.stack.IGridStack;

public interface IGridSorter {
    boolean isApplicable(IGrid grid);

    int compare(IGridStack left, IGridStack right, SortingDirection direction);
}
