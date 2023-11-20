package com.hybridavenger69.mtstorage.blockentity;

import com.hybridavenger69.mtstorage.MSBlockEntities;
import com.hybridavenger69.mtstorage.apiimpl.network.node.SecurityManagerNetworkNode;
import com.hybridavenger69.mtstorage.blockentity.data.BlockEntitySynchronizationSpec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class SecurityManagerBlockEntity extends NetworkNodeBlockEntity<SecurityManagerNetworkNode> {
    public static BlockEntitySynchronizationSpec SPEC = BlockEntitySynchronizationSpec.builder()
        .addWatchedParameter(REDSTONE_MODE)
        .build();

    public SecurityManagerBlockEntity(BlockPos pos, BlockState state) {
        super(MSBlockEntities.SECURITY_MANAGER.get(), pos, state, SPEC, SecurityManagerNetworkNode.class);
    }

    @Override
    @Nonnull
    public SecurityManagerNetworkNode createNode(Level level, BlockPos pos) {
        return new SecurityManagerNetworkNode(level, pos);
    }
}
