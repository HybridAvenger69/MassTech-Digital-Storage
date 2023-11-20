package com.hybridavenger69.mtstorage.container.factory;

import com.hybridavenger69.mtstorage.container.CrafterManagerContainerMenu;
import com.hybridavenger69.mtstorage.screen.EmptyScreenInfoProvider;
import com.hybridavenger69.mtstorage.blockentity.CrafterManagerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.network.IContainerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

public class CrafterManagerContainerFactory implements IContainerFactory<CrafterManagerContainerMenu> {
    @Override
    public CrafterManagerContainerMenu create(int windowId, Inventory inv, FriendlyByteBuf buf) {
        Map<String, Integer> data = new LinkedHashMap<>();

        BlockPos pos = buf.readBlockPos();

        int size = buf.readInt();

        for (int i = 0; i < size; ++i) {
            data.put(buf.readComponent().getString(), buf.readInt());
        }

        CrafterManagerContainerMenu container = new CrafterManagerContainerMenu((CrafterManagerBlockEntity) inv.player.level.getBlockEntity(pos), inv.player, windowId);

        container.setScreenInfoProvider(new EmptyScreenInfoProvider());
        container.initSlots(data);

        return container;
    }
}
