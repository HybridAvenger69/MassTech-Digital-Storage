package com.hybridavenger69.mtstorage.apiimpl.network.grid.factory;

import com.hybridavenger69.mtstorage.MS;
import com.hybridavenger69.mtstorage.api.network.grid.GridFactoryType;
import com.hybridavenger69.mtstorage.api.network.grid.IGrid;
import com.hybridavenger69.mtstorage.api.network.grid.IGridFactory;
import com.hybridavenger69.mtstorage.blockentity.grid.WirelessGrid;
import com.hybridavenger69.mtstorage.inventory.player.PlayerSlot;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import javax.annotation.Nullable;

public class WirelessGridGridFactory implements IGridFactory {
    public static final ResourceLocation ID = new ResourceLocation(MS.ID, "wireless_grid");

    @Nullable
    @Override
    public IGrid createFromStack(Player player, ItemStack stack, PlayerSlot slot) {
        return new WirelessGrid(stack, player.getServer(), slot);
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
