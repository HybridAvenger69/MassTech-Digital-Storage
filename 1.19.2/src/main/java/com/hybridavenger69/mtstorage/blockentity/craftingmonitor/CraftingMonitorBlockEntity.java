package com.hybridavenger69.mtstorage.blockentity.craftingmonitor;

import com.hybridavenger69.mtstorage.MSBlockEntities;
import com.hybridavenger69.mtstorage.apiimpl.network.node.CraftingMonitorNetworkNode;
import com.hybridavenger69.mtstorage.blockentity.NetworkNodeBlockEntity;
import com.hybridavenger69.mtstorage.blockentity.data.BlockEntitySynchronizationParameter;
import com.hybridavenger69.mtstorage.blockentity.data.BlockEntitySynchronizationSpec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.UUID;

public class CraftingMonitorBlockEntity extends NetworkNodeBlockEntity<CraftingMonitorNetworkNode> {
    public static final BlockEntitySynchronizationParameter<Optional<UUID>, CraftingMonitorBlockEntity> TAB_SELECTED = new BlockEntitySynchronizationParameter<>(EntityDataSerializers.OPTIONAL_UUID, Optional.empty(), t -> t.getNode().getTabSelected(), (t, v) -> {
        if (v.isPresent() && t.getNode().getTabSelected().isPresent() && v.get().equals(t.getNode().getTabSelected().get())) {
            t.getNode().setTabSelected(Optional.empty());
        } else {
            t.getNode().setTabSelected(v);
        }

        t.getNode().markDirty();
    });

    public static final BlockEntitySynchronizationParameter<Integer, CraftingMonitorBlockEntity> TAB_PAGE = new BlockEntitySynchronizationParameter<>(EntityDataSerializers.INT, 0, t -> t.getNode().getTabPage(), (t, v) -> {
        if (v >= 0) {
            t.getNode().setTabPage(v);
            t.getNode().markDirty();
        }
    });

    public static BlockEntitySynchronizationSpec SPEC = BlockEntitySynchronizationSpec.builder()
        .addWatchedParameter(REDSTONE_MODE)
        .addWatchedParameter(TAB_SELECTED)
        .addWatchedParameter(TAB_PAGE)
        .build();

    public CraftingMonitorBlockEntity(BlockPos pos, BlockState state) {
        super(MSBlockEntities.CRAFTING_MONITOR.get(), pos, state, SPEC, CraftingMonitorNetworkNode.class);
    }

    @Override
    @Nonnull
    public CraftingMonitorNetworkNode createNode(Level level, BlockPos pos) {
        return new CraftingMonitorNetworkNode(level, pos);
    }
}
