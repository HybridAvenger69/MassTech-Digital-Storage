package com.hybridavenger69.mtstorage.block;

import com.hybridavenger69.mtstorage.MSBlocks;
import com.hybridavenger69.mtstorage.blockentity.NetworkReceiverBlockEntity;
import com.hybridavenger69.mtstorage.util.BlockUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class NetworkReceiverBlock extends ColoredNetworkBlock {
    public NetworkReceiverBlock() {
        super(BlockUtils.DEFAULT_ROCK_PROPERTIES);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new NetworkReceiverBlockEntity(pos, state);
    }

    @Override
    public boolean hasConnectedState() {
        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        return MSBlocks.NETWORK_RECEIVER.changeBlockColor(state, player.getItemInHand(hand), level, pos, player);
    }
}
