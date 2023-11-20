package com.hybridavenger69.mtstorage.container.factory;

import com.hybridavenger69.mtstorage.MSContainerMenus;
import com.hybridavenger69.mtstorage.container.CraftingMonitorContainerMenu;
import com.hybridavenger69.mtstorage.blockentity.craftingmonitor.CraftingMonitorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.network.IContainerFactory;

public class CraftingMonitorContainerFactory implements IContainerFactory<CraftingMonitorContainerMenu> {
    @Override
    public CraftingMonitorContainerMenu create(int windowId, Inventory inv, FriendlyByteBuf data) {
        BlockPos pos = data.readBlockPos();

        CraftingMonitorBlockEntity blockEntity = (CraftingMonitorBlockEntity) inv.player.level.getBlockEntity(pos);

        return new CraftingMonitorContainerMenu(MSContainerMenus.CRAFTING_MONITOR.get(), blockEntity.getNode(), blockEntity, inv.player, windowId);
    }
}
