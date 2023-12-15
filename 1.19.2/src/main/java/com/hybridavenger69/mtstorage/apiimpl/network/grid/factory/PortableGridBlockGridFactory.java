package com.hybridavenger69.mtstorage.apiimpl.network.grid.factory;

import com.hybridavenger69.mtstorage.MS;
import com.hybridavenger69.mtstorage.api.network.grid.GridFactoryType;
import com.hybridavenger69.mtstorage.api.network.grid.IGrid;
import com.hybridavenger69.mtstorage.api.network.grid.IGridFactory;
import com.hybridavenger69.mtstorage.blockentity.grid.portable.PortableGridBlockEntity;
import com.hybridavenger69.mtstorage.inventory.player.PlayerSlot;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import javax.annotation.Nullable;

public class PortableGridBlockGridFactory implements IGridFactory {
    public static final ResourceLocation ID = new ResourceLocation(MS.ID, "portable_grid_block");

    @Override
    @Nullable
    public IGrid createFromStack(Player player, ItemStack stack, PlayerSlot slot) {
        return null;
    }

    @Override
    @Nullable
    public IGrid createFromBlock(Player player, BlockPos pos) {
        BlockEntity blockEntity = getRelevantBlockEntity(player.level, pos);

        if (blockEntity instanceof PortableGridBlockEntity) {
            return (PortableGridBlockEntity) blockEntity;
        }

        return null;
    }

    @Nullable
    @Override
    public BlockEntity getRelevantBlockEntity(Level level, BlockPos pos) {
        return level.getBlockEntity(pos);
    }

    @Override
    public GridFactoryType getType() {
        return GridFactoryType.BLOCK;
    }
}
