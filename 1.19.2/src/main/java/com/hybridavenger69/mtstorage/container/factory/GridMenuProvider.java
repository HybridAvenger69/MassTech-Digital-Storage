package com.hybridavenger69.mtstorage.container.factory;

import com.hybridavenger69.mtstorage.api.network.grid.IGrid;
import com.hybridavenger69.mtstorage.container.GridContainerMenu;
import com.hybridavenger69.mtstorage.screen.EmptyScreenInfoProvider;
import com.hybridavenger69.mtstorage.blockentity.BaseBlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;

import javax.annotation.Nullable;

public class GridMenuProvider implements MenuProvider {
    private final IGrid grid;
    private final BlockEntity blockEntity;

    public GridMenuProvider(IGrid grid, BlockEntity blockEntity) {
        this.grid = grid;
        this.blockEntity = blockEntity;
    }

    @Override
    public Component getDisplayName() {
        return grid.getTitle();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory inv, Player player) {
        GridContainerMenu c = new GridContainerMenu(grid, blockEntity instanceof BaseBlockEntity ? (BaseBlockEntity) blockEntity : null, player, windowId);

        c.setScreenInfoProvider(new EmptyScreenInfoProvider());
        c.initSlots();

        return c;
    }
}
