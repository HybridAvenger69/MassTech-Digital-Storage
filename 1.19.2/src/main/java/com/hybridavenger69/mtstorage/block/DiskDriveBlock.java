package com.hybridavenger69.mtstorage.block;

import com.hybridavenger69.mtstorage.container.DiskDriveContainerMenu;
import com.hybridavenger69.mtstorage.container.factory.BlockEntityMenuProvider;
import com.hybridavenger69.mtstorage.blockentity.DiskDriveBlockEntity;
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

public class DiskDriveBlock extends NetworkNodeBlock {
    public DiskDriveBlock() {
        super(BlockUtils.DEFAULT_ROCK_PROPERTIES);
    }

    @Override
    public BlockDirection getDirection() {
        return BlockDirection.HORIZONTAL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DiskDriveBlockEntity(pos, state);
    }

    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult rayTraceResult) {
        if (!level.isClientSide) {
            return NetworkUtils.attemptModify(level, pos, player, () -> NetworkHooks.openScreen(
                (ServerPlayer) player,
                new BlockEntityMenuProvider<DiskDriveBlockEntity>(
                    Component.translatable("gui.mtstorage.disk_drive"),
                    (blockEntity, windowId, inventory, p) -> new DiskDriveContainerMenu(blockEntity, p, windowId),
                    pos
                ),
                pos
            ));
        }

        return InteractionResult.SUCCESS;
    }
}
