package com.hybridavenger69.mtstorage.screen.widget.sidebutton;

import com.mojang.blaze3d.vertex.PoseStack;
import com.hybridavenger69.mtstorage.api.network.grid.IGrid;
import com.hybridavenger69.mtstorage.container.GridContainerMenu;
import com.hybridavenger69.mtstorage.screen.BaseScreen;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;

public class GridSortingDirectionSideButton extends SideButton {
    private final IGrid grid;

    public GridSortingDirectionSideButton(BaseScreen<GridContainerMenu> screen, IGrid grid) {
        super(screen);

        this.grid = grid;
    }

    @Override
    protected String getTooltip() {
        return I18n.get("sidebutton.mtstorage.grid.sorting.direction") + "\n" + ChatFormatting.GRAY + I18n.get("sidebutton.mtstorage.grid.sorting.direction." + grid.getSortingDirection());
    }

    @Override
    protected void renderButtonIcon(PoseStack poseStack, int x, int y) {
        screen.blit(poseStack, x, y, grid.getSortingDirection() * 16, 16, 16, 16);
    }

    @Override
    public void onPress() {
        int dir = grid.getSortingDirection();

        if (dir == IGrid.SORTING_DIRECTION_ASCENDING) {
            dir = IGrid.SORTING_DIRECTION_DESCENDING;
        } else if (dir == IGrid.SORTING_DIRECTION_DESCENDING) {
            dir = IGrid.SORTING_DIRECTION_ASCENDING;
        }

        grid.onSortingDirectionChanged(dir);
    }
}
