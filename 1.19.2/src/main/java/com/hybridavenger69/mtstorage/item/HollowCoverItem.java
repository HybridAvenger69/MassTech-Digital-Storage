package com.hybridavenger69.mtstorage.item;

import com.hybridavenger69.mtstorage.apiimpl.network.node.cover.Cover;
import com.hybridavenger69.mtstorage.apiimpl.network.node.cover.CoverType;
import net.minecraft.world.item.ItemStack;

public class HollowCoverItem extends CoverItem {

    public HollowCoverItem() {
        super();
    }

    @Override
    protected Cover createCover(ItemStack stack) {
        return new Cover(stack, CoverType.HOLLOW);
    }
}
