package com.hybridavenger69.mtstorage.container.factory;

import com.hybridavenger69.mtstorage.api.network.grid.IGrid;
import com.hybridavenger69.mtstorage.apiimpl.API;
import com.hybridavenger69.mtstorage.container.GridContainerMenu;
import com.hybridavenger69.mtstorage.inventory.player.PlayerSlot;
import com.hybridavenger69.mtstorage.blockentity.BaseBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.IContainerFactory;
import org.apache.commons.lang3.tuple.Pair;

public class GridContainerFactory implements IContainerFactory<GridContainerMenu> {
    @Override
    public GridContainerMenu create(int windowId, Inventory inv, FriendlyByteBuf data) {
        ResourceLocation id = data.readResourceLocation();

        BlockPos pos = null;
        ItemStack stack = null;

        if (data.readBoolean()) {
            pos = data.readBlockPos();
        }

        if (data.readBoolean()) {
            stack = data.readItem();
        }

        PlayerSlot slot = new PlayerSlot(data);

        Pair<IGrid, BlockEntity> grid = API.instance().getGridManager().createGrid(id, inv.player, stack, pos, slot);

        return new GridContainerMenu(grid.getLeft(), grid.getRight() instanceof BaseBlockEntity ? (BaseBlockEntity) grid.getRight() : null, inv.player, windowId);
    }
}
