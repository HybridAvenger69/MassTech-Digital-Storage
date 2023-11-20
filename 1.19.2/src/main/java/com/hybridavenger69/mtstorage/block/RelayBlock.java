package com.hybridavenger69.mtstorage.block;

import com.hybridavenger69.mtstorage.MSBlocks;
import com.hybridavenger69.mtstorage.container.RelayContainerMenu;
import com.hybridavenger69.mtstorage.container.factory.BlockEntityMenuProvider;
import com.hybridavenger69.mtstorage.blockentity.RelayBlockEntity;
import com.hybridavenger69.mtstorage.util.BlockUtils;
import com.hybridavenger69.mtstorage.util.NetworkUtils;
import net.minecraft.core.BlockPos;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

public class RelayBlock extends ColoredNetworkBlock {
    public RelayBlock() {
        super(BlockUtils.DEFAULT_ROCK_PROPERTIES);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new RelayBlockEntity(pos, state);
    }

    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        InteractionResult result = MSBlocks.RELAY.changeBlockColor(state, player.getItemInHand(hand), level, pos, player);
        if (result != InteractionResult.PASS) {
            return result;
        }

        if (!level.isClientSide) {
            return NetworkUtils.attemptModify(level, pos, player, () -> NetworkHooks.openScreen(
                (ServerPlayer) player,
                new BlockEntityMenuProvider<RelayBlockEntity>(
                    Component.translatable("gui.mtstorage.relay"),
                    (blockEntity, windowId, inventory, p) -> new RelayContainerMenu(blockEntity, player, windowId),
                    pos
                ),
                pos
            ));
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean hasConnectedState() {
        return true;
    }
}
