package com.hybridavenger69.mtstorage.block;

import com.hybridavenger69.mtstorage.MSBlocks;
import com.hybridavenger69.mtstorage.MSContainerMenus;
import com.hybridavenger69.mtstorage.api.network.security.Permission;
import com.hybridavenger69.mtstorage.container.factory.CraftingMonitorMenuProvider;
import com.hybridavenger69.mtstorage.blockentity.craftingmonitor.CraftingMonitorBlockEntity;
import com.hybridavenger69.mtstorage.util.BlockUtils;
import com.hybridavenger69.mtstorage.util.NetworkUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

public class CraftingMonitorBlock extends ColoredNetworkBlock {
    public CraftingMonitorBlock() {
        super(BlockUtils.DEFAULT_ROCK_PROPERTIES);
    }

    @Override
    public BlockDirection getDirection() {
        return BlockDirection.HORIZONTAL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CraftingMonitorBlockEntity(pos, state);
    }

    //@Override
    @SuppressWarnings("deprecation")
    //public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
    //  InteractionResult result = MSBlocks.CRAFTING_MONITOR.changeBlockColor(state, player.getItemInHand(hand), level, pos, player);
    //  if (result != InteractionResult.PASS) {
    //      return result;
    //  }
//
            //      if (!level.isClientSide) {
    //CraftingMonitorBlockEntity blockEntity = (CraftingMonitorBlockEntity) level.getBlockEntity(pos);

//            return NetworkUtils.attempt(level, pos, player, () -> NetworkHooks.openScreen(
    //              (ServerPlayer) player,
    //          new CraftingMonitorMenuProvider(MSContainerMenus.CRAFTING_MONITOR.get(), blockEntity.getNode(), blockEntity),
    //          pos
    //      ), Permission.MODIFY, Permission.AUTOCRAFTING);
    //  }

    //  return InteractionResult.SUCCESS;
    //}

    @Override
    public boolean hasConnectedState() {
        return true;
    }
}
