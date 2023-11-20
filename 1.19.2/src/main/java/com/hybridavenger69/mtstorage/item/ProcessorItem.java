package com.hybridavenger69.mtstorage.item;

import com.hybridavenger69.mtstorage.MS;
import net.minecraft.world.item.Item;

public class ProcessorItem extends Item {
    public ProcessorItem() {
        super(new Item.Properties().tab(MS.CREATIVE_MODE_TAB));
    }

    public enum Type {
        RAW_BASIC("raw_basic"),
        RAW_IMPROVED("raw_improved"),
        RAW_ADVANCED("raw_advanced"),
        RAW_PROFICIENT("raw_proficient"),
        RAW_SUPERIOR("raw_superior"),
        BASIC("basic"),
        IMPROVED("improved"),
        ADVANCED("advanced"),
        PROFICIENT("proficient"),
        SUPERIOR("superior");

        final String name;

        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
