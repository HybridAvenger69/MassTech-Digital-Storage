package com.hybridavenger69.mtstorage.screen.widget.sidebutton;

import com.mojang.blaze3d.vertex.PoseStack;
import com.hybridavenger69.mtstorage.api.network.grid.GridType;
import com.hybridavenger69.mtstorage.api.network.grid.IGrid;
import com.hybridavenger69.mtstorage.container.GridContainerMenu;
import com.hybridavenger69.mtstorage.integration.inventorytweaks.InventoryTweaksIntegration;
import com.hybridavenger69.mtstorage.screen.BaseScreen;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;

public class GridSortingTypeSideButton extends SideButton {
    private final IGrid grid;

    public GridSortingTypeSideButton(BaseScreen<GridContainerMenu> screen, IGrid grid) {
        super(screen);

        this.grid = grid;
    }

    @Override
    protected String getTooltip() {
        return I18n.get("sidebutton.mtstorage.grid.sorting.type") + "\n" + ChatFormatting.GRAY + I18n.get("sidebutton.mtstorage.grid.sorting.type." + grid.getSortingType());
    }

    @Override
    protected void renderButtonIcon(PoseStack poseStack, int x, int y) {
        if (grid.getSortingType() == IGrid.SORTING_TYPE_LAST_MODIFIED) {
            screen.blit(poseStack, x, y, 48, 48, 16, 16);
        } else {
            screen.blit(poseStack, x, y, grid.getSortingType() * 16, 32, 16, 16);
        }
    }

    @Override
    public void onPress() {
        int type = grid.getSortingType();

        if (type == IGrid.SORTING_TYPE_QUANTITY) {
            type = IGrid.SORTING_TYPE_NAME;
        } else if (type == IGrid.SORTING_TYPE_NAME) {
            if (grid.getGridType() == GridType.FLUID) {
                type = IGrid.SORTING_TYPE_LAST_MODIFIED;
            } else {
                type = IGrid.SORTING_TYPE_ID;
            }
        } else if (type == IGrid.SORTING_TYPE_ID) {
            type = IGrid.SORTING_TYPE_LAST_MODIFIED;
        } else if (type == IGrid.SORTING_TYPE_LAST_MODIFIED) {
            if (grid.getGridType() == GridType.FLUID || !InventoryTweaksIntegration.isLoaded()) {
                type = IGrid.SORTING_TYPE_QUANTITY;
            } else {
                type = IGrid.SORTING_TYPE_INVENTORYTWEAKS;
            }
        } else if (type == IGrid.SORTING_TYPE_INVENTORYTWEAKS) {
            type = IGrid.SORTING_TYPE_QUANTITY;
        }

        grid.onSortingTypeChanged(type);
    }
}
