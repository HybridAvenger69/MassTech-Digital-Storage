package com.hybridavenger69.mtstorage.item.group;

import com.hybridavenger69.hybridlib.HybridIDS;
import com.hybridavenger69.mtstorage.MS;
import com.hybridavenger69.mtstorage.MSBlocks;
import com.hybridavenger69.mtstorage.util.ColorMap;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class MainCreativeModeTab extends CreativeModeTab {
    public MainCreativeModeTab() {
        super(HybridIDS.MTStorage_MODID);
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(MSBlocks.CREATIVE_CONTROLLER.get(ColorMap.DEFAULT_COLOR).get());
    }
}
