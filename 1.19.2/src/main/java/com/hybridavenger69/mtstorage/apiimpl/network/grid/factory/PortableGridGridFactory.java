package com.hybridavenger69.mtstorage.apiimpl.network.grid.factory;

import com.hybridavenger69.hybridlib.HybridIDS;
import com.hybridavenger69.mtstorage.MS;
import com.hybridavenger69.mtstorage.api.network.grid.GridFactoryType;
import com.hybridavenger69.mtstorage.api.network.grid.IGrid;
import com.hybridavenger69.mtstorage.api.network.grid.IGridFactory;
import com.hybridavenger69.mtstorage.blockentity.grid.portable.PortableGrid;
import com.hybridavenger69.mtstorage.inventory.player.PlayerSlot;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import javax.annotation.Nullable;

public class PortableGridGridFactory implements IGridFactory {
    public static final ResourceLocation ID = new ResourceLocation(HybridIDS.MTStorage_MODID, "portable_grid_item");

    @Nullable
    @Override
    public IGrid createFromStack(Player player, ItemStack stack, PlayerSlot slot) {
        PortableGrid portableGrid = new PortableGrid(player, stack, slot);

        portableGrid.onOpen();

        return portableGrid;
    }

    @Nullable
    @Override
    public IGrid createFromBlock(Player player, BlockPos pos) {
        return null;
    }

    @Nullable
    @Override
    public BlockEntity getRelevantBlockEntity(Level level, BlockPos pos) {
        return null;
    }

    @Override
    public GridFactoryType getType() {
        return GridFactoryType.STACK;
    }
}


