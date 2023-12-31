package com.hybridavenger69.mtstorage.screen.grid.sorting;

import com.hybridavenger69.mtstorage.api.network.grid.IGrid;
import com.hybridavenger69.mtstorage.screen.grid.stack.IGridStack;

public class InventoryTweaksGridSorter implements IGridSorter {
    @Override
    public boolean isApplicable(IGrid grid) {
        return false;
    }

    @Override
    public int compare(IGridStack left, IGridStack right, SortingDirection direction) {
        return 0;
    }

/*    private InvTweaksAPI api = null;

    public GridSorterInventoryTweaks() {
        try {
            api = (InvTweaksAPI) Class.forName("invtweaks.forge.InvTweaksMod", true, Loader.instance().getModClassLoader()).getField("instance").get(null);
        } catch (Exception e) {
            // NO OP
        }
    }

    @Override
    public boolean isApplicable(IGrid grid) {
        return grid.getSortingType() == IGrid.SORTING_TYPE_INVENTORYTWEAKS;
    }

    @Override
    public int compare(IGridStack left, IGridStack right, GridSorterDirection sortingDirection) {
        if (api != null && left instanceof GridStackItem && right instanceof GridStackItem) {
            if (sortingDirection == GridSorterDirection.DESCENDING) {
                return api.compareItems(((GridStackItem) left).getStack(), ((GridStackItem) right).getStack());
            } else if (sortingDirection == GridSorterDirection.ASCENDING) {
                return api.compareItems(((GridStackItem) right).getStack(), ((GridStackItem) left).getStack());
            }
        }

        return 0;
    }*/
}