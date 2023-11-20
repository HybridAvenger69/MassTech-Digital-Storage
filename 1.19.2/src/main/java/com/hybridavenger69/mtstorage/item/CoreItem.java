package com.hybridavenger69.mtstorage.item;

import com.hybridavenger69.mtstorage.MS;
import net.minecraft.world.item.Item;

public class CoreItem extends Item {
    public CoreItem() {
        super(new Item.Properties().tab(MS.CREATIVE_MODE_TAB));
    }

    public enum Type {
        CONSTRUCTION,
        DESTRUCTION
    }
}
