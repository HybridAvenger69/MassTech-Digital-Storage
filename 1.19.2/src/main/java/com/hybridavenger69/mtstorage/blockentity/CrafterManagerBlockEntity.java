package com.hybridavenger69.mtstorage.blockentity;

import com.hybridavenger69.mtstorage.MSBlockEntities;
import com.hybridavenger69.mtstorage.api.network.grid.IGrid;
import com.hybridavenger69.mtstorage.apiimpl.network.node.CrafterManagerNetworkNode;
import com.hybridavenger69.mtstorage.blockentity.data.BlockEntitySynchronizationParameter;
import com.hybridavenger69.mtstorage.blockentity.data.BlockEntitySynchronizationSpec;
import com.hybridavenger69.mtstorage.screen.BaseScreen;
import com.hybridavenger69.mtstorage.screen.CrafterManagerScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class CrafterManagerBlockEntity extends NetworkNodeBlockEntity<CrafterManagerNetworkNode> {
    public static final BlockEntitySynchronizationParameter<Integer, CrafterManagerBlockEntity> SEARCH_BOX_MODE = new BlockEntitySynchronizationParameter<>(EntityDataSerializers.INT, 0, t -> t.getNode().getSearchBoxMode(), (t, v) -> {
        if (IGrid.isValidSearchBoxMode(v)) {
            t.getNode().setSearchBoxMode(v);
            t.getNode().markDirty();
        }
    }, (initial, p) -> BaseScreen.executeLater(CrafterManagerScreen.class, crafterManager -> crafterManager.getSearchField().setMode(p)));
    public static final BlockEntitySynchronizationParameter<Integer, CrafterManagerBlockEntity> SIZE = new BlockEntitySynchronizationParameter<>(EntityDataSerializers.INT, IGrid.SIZE_STRETCH, t -> t.getNode().getSize(), (t, v) -> {
        if (IGrid.isValidSize(v)) {
            t.getNode().setSize(v);
            t.getNode().markDirty();
        }
    }, (initial, p) -> BaseScreen.executeLater(CrafterManagerScreen.class, BaseScreen::init));

    public static BlockEntitySynchronizationSpec SPEC = BlockEntitySynchronizationSpec.builder()
        .addWatchedParameter(REDSTONE_MODE)
        .addWatchedParameter(SIZE)
        .addWatchedParameter(SEARCH_BOX_MODE)
        .build();

    public CrafterManagerBlockEntity(BlockPos pos, BlockState state) {
        super(MSBlockEntities.CRAFTER_MANAGER.get(), pos, state, SPEC, CrafterManagerNetworkNode.class);
    }

    @Override
    public CrafterManagerNetworkNode createNode(Level level, BlockPos pos) {
        return new CrafterManagerNetworkNode(level, pos);
    }
}
