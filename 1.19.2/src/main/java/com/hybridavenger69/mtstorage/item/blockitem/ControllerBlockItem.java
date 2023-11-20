package com.hybridavenger69.mtstorage.item.blockitem;

import com.hybridavenger69.mtstorage.MS;
import com.hybridavenger69.mtstorage.api.network.NetworkType;
import com.hybridavenger69.mtstorage.block.ControllerBlock;
import com.hybridavenger69.mtstorage.util.ColorMap;
import net.minecraft.network.chat.Component;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ControllerBlockItem extends EnergyBlockItem {
    private final Component displayName;

    public ControllerBlockItem(ControllerBlock block, DyeColor color, Component displayName) {
        super(block, new Item.Properties().tab(MS.CREATIVE_MODE_TAB).stacksTo(1), block.getType() == NetworkType.CREATIVE, () -> MS.SERVER_CONFIG.getController().getCapacity());

        if (color != ColorMap.DEFAULT_COLOR) {
            this.displayName = Component.translatable("color.minecraft." + color.getName())
                .append(" ")
                .append(displayName);
        } else {
            this.displayName = displayName;
        }

    }

    @Override
    public Component getName(ItemStack stack) {
        return displayName;
    }
}
