package com.hybridavenger69.mtstorage.block;

import com.hybridavenger69.mtstorage.MSBlocks;
import com.hybridavenger69.mtstorage.api.network.security.Permission;
import com.hybridavenger69.mtstorage.container.SecurityManagerContainerMenu;
import com.hybridavenger69.mtstorage.container.factory.BlockEntityMenuProvider;
import com.hybridavenger69.mtstorage.blockentity.SecurityManagerBlockEntity;
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

public class SecurityManagerBlock extends ColoredNetworkBlock {
    public SecurityManagerBlock() {
        super(BlockUtils.DEFAULT_ROCK_PROPERTIES);
    }

    @Override
    public BlockDirection getDirection() {
        return BlockDirection.HORIZONTAL;
    }

    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        InteractionResult result = MSBlocks.SECURITY_MANAGER.changeBlockColor(state, player.getItemInHand(hand), level, pos, player);
        if (result != InteractionResult.PASS) {
            return result;
        }

        if (!level.isClientSide) {
            Runnable action = () -> NetworkHooks.openScreen(
                (ServerPlayer) player,
                new BlockEntityMenuProvider<SecurityManagerBlockEntity>(
                    Component.translatable("gui.mtstorage.security_manager"),
                    (blockEntity, windowId, inventory, p) -> new SecurityManagerContainerMenu(blockEntity, player, windowId),
                    pos
                ),
                pos
            );

            if (player.getGameProfile().getId().equals(((SecurityManagerBlockEntity) level.getBlockEntity(pos)).getNode().getOwner())) {
                action.run();
            } else {
                return NetworkUtils.attempt(level, pos, player, action, Permission.MODIFY, Permission.SECURITY);
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SecurityManagerBlockEntity(pos, state);
    }

    @Override
    public boolean hasConnectedState() {
        return true;
    }
}
