package com.hybridavenger69.mtstorage.item.blockitem;

import com.hybridavenger69.mtstorage.block.BaseBlock;
import com.hybridavenger69.mtstorage.block.BlockDirection;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;

public class BaseBlockItem extends BlockItem {
    private final BaseBlock block;

    public BaseBlockItem(BaseBlock block, Properties builder) {
        super(block, builder);

        this.block = block;
    }

    @Override
    protected boolean placeBlock(BlockPlaceContext context, BlockState state) {
        boolean result = super.placeBlock(context, state);

        if (result && block.getDirection() != BlockDirection.NONE) {
            context.getLevel().setBlockAndUpdate(context.getClickedPos(), state.setValue(block.getDirection().getProperty(), block.getDirection().getFrom(
                context.getClickedFace(),
                context.getClickedPos(),
                context.getPlayer()
            )));
        }

        return result;
    }
}
