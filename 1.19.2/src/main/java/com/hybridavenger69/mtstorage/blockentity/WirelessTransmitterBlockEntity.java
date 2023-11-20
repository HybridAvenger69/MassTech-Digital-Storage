package com.hybridavenger69.mtstorage.blockentity;

import com.hybridavenger69.mtstorage.MSBlockEntities;
import com.hybridavenger69.mtstorage.apiimpl.network.node.WirelessTransmitterNetworkNode;
import com.hybridavenger69.mtstorage.blockentity.data.BlockEntitySynchronizationParameter;
import com.hybridavenger69.mtstorage.blockentity.data.BlockEntitySynchronizationSpec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class WirelessTransmitterBlockEntity extends NetworkNodeBlockEntity<WirelessTransmitterNetworkNode> {
    public static final BlockEntitySynchronizationParameter<Integer, WirelessTransmitterBlockEntity> RANGE = new BlockEntitySynchronizationParameter<>(EntityDataSerializers.INT, 0, t -> t.getNode().getRange());

    public static BlockEntitySynchronizationSpec SPEC = BlockEntitySynchronizationSpec.builder()
        .addWatchedParameter(REDSTONE_MODE)
        .addWatchedParameter(RANGE)
        .build();

    public WirelessTransmitterBlockEntity(BlockPos pos, BlockState state) {
        super(MSBlockEntities.WIRELESS_TRANSMITTER.get(), pos, state, SPEC, WirelessTransmitterNetworkNode.class);
    }

    @Override
    @Nonnull
    public WirelessTransmitterNetworkNode createNode(Level level, BlockPos pos) {
        return new WirelessTransmitterNetworkNode(level, pos);
    }
}
