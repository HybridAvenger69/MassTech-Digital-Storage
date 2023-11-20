package com.hybridavenger69.mtstorage.apiimpl.network.grid.handler;

import com.hybridavenger69.mtstorage.MS;
import com.hybridavenger69.mtstorage.api.autocrafting.task.CalculationResultType;
import com.hybridavenger69.mtstorage.api.autocrafting.task.ICalculationResult;
import com.hybridavenger69.mtstorage.api.network.INetwork;
import com.hybridavenger69.mtstorage.api.network.grid.handler.IFluidGridHandler;
import com.hybridavenger69.mtstorage.api.network.security.Permission;
import com.hybridavenger69.mtstorage.api.util.Action;
import com.hybridavenger69.mtstorage.apiimpl.autocrafting.preview.ErrorCraftingPreviewElement;
import com.hybridavenger69.mtstorage.network.grid.GridCraftingPreviewResponseMessage;
import com.hybridavenger69.mtstorage.network.grid.GridCraftingStartResponseMessage;
import com.hybridavenger69.mtstorage.util.NetworkUtils;
import com.hybridavenger69.mtstorage.util.StackUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.UUID;

public class FluidGridHandler implements IFluidGridHandler {
    private final INetwork network;

    public FluidGridHandler(INetwork network) {
        this.network = network;
    }

    @Override
    public void onExtract(ServerPlayer player, UUID id, boolean shift) {
        FluidStack stack = network.getFluidStorageCache().getList().get(id);

        if (stack == null || stack.getAmount() < FluidType.BUCKET_VOLUME || !network.getSecurityManager().hasPermission(Permission.EXTRACT, player) || !network.canRun()) {
            return;
        }

        NetworkUtils.extractBucketFromPlayerInventoryOrNetwork(player, network, bucket -> bucket.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM, null).ifPresent(fluidHandler -> {
            network.getFluidStorageTracker().changed(player, stack.copy());

            FluidStack extracted = network.extractFluid(stack, FluidType.BUCKET_VOLUME, Action.PERFORM);

            fluidHandler.fill(extracted, IFluidHandler.FluidAction.EXECUTE);

            if (shift) {
                if (!player.getInventory().add(fluidHandler.getContainer().copy())) {
                    Containers.dropItemStack(player.getCommandSenderWorld(), player.getX(), player.getY(), player.getZ(), fluidHandler.getContainer());
                }
            } else {
                player.containerMenu.setCarried(fluidHandler.getContainer());
            }

            network.getNetworkItemManager().drainEnergy(player, MS.SERVER_CONFIG.getWirelessFluidGrid().getExtractUsage());
        }));
    }

    @Override
    @Nonnull
    public ItemStack onInsert(ServerPlayer player, ItemStack container) {
        if (!network.getSecurityManager().hasPermission(Permission.INSERT, player) || !network.canRun()) {
            return container;
        }

        Pair<ItemStack, FluidStack> result = StackUtils.getFluid(container, true);

        if (!result.getValue().isEmpty() && network.insertFluid(result.getValue(), result.getValue().getAmount(), Action.SIMULATE).isEmpty()) {
            network.getFluidStorageTracker().changed(player, result.getValue().copy());

            result = StackUtils.getFluid(container, false);

            network.insertFluid(result.getValue(), result.getValue().getAmount(), Action.PERFORM);

            network.getNetworkItemManager().drainEnergy(player, MS.SERVER_CONFIG.getWirelessFluidGrid().getInsertUsage());

            return result.getLeft();
        }

        return container;
    }

    @Override
    public void onInsertHeldContainer(ServerPlayer player) {
        player.containerMenu.setCarried(onInsert(player, player.containerMenu.getCarried()));
    }

    @Override
    public void onCraftingPreviewRequested(ServerPlayer player, UUID id, int quantity, boolean noPreview) {
        if (!network.getSecurityManager().hasPermission(Permission.AUTOCRAFTING, player)) {
            return;
        }

        FluidStack stack = network.getFluidStorageCache().getCraftablesList().get(id);

        if (stack != null) {
            ICalculationResult result = network.getCraftingManager().create(stack, quantity);
            if (result == null) {
                return;
            }

            if (!result.isOk() && result.getType() != CalculationResultType.MISSING) {
                MS.NETWORK_HANDLER.sendTo(
                    player,
                    new GridCraftingPreviewResponseMessage(
                        Collections.singletonList(new ErrorCraftingPreviewElement(result.getType(), result.getRecursedPattern() == null ? ItemStack.EMPTY : result.getRecursedPattern().getStack())),
                        id,
                        quantity,
                        true
                    )
                );
            } else if (result.isOk() && noPreview) {
                network.getCraftingManager().start(result.getTask());

                MS.NETWORK_HANDLER.sendTo(player, new GridCraftingStartResponseMessage());
            } else {
                MS.NETWORK_HANDLER.sendTo(
                    player,
                    new GridCraftingPreviewResponseMessage(
                        result.getPreviewElements(),
                        id,
                        quantity,
                        true
                    )
                );
            }
        }
    }

    @Override
    public void onCraftingRequested(ServerPlayer player, UUID id, int quantity) {
        if (quantity <= 0 || !network.getSecurityManager().hasPermission(Permission.AUTOCRAFTING, player)) {
            return;
        }

        FluidStack stack = network.getFluidStorageCache().getCraftablesList().get(id);

        if (stack != null) {
            ICalculationResult result = network.getCraftingManager().create(stack, quantity);
            if (result.isOk()) {
                network.getCraftingManager().start(result.getTask());
            }
        }
    }
}
