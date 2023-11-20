package com.hybridavenger69.mtstorage.item.blockitem;

import com.hybridavenger69.mtstorage.MS;
import com.hybridavenger69.mtstorage.MSBlocks;
import com.hybridavenger69.mtstorage.MSItems;
import com.hybridavenger69.mtstorage.api.storage.disk.IStorageDisk;
import com.hybridavenger69.mtstorage.api.storage.disk.StorageDiskSyncData;
import com.hybridavenger69.mtstorage.apiimpl.API;
import com.hybridavenger69.mtstorage.apiimpl.network.node.storage.FluidStorageNetworkNode;
import com.hybridavenger69.mtstorage.apiimpl.storage.FluidStorageType;
import com.hybridavenger69.mtstorage.block.FluidStorageBlock;
import com.hybridavenger69.mtstorage.item.FluidStoragePartItem;
import com.hybridavenger69.mtstorage.item.ProcessorItem;
import com.hybridavenger69.mtstorage.render.Styles;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class FluidStorageBlockItem extends BaseBlockItem {
    private final FluidStorageType type;

    public FluidStorageBlockItem(FluidStorageBlock block) {
        super(block, new Item.Properties().tab(MS.CREATIVE_MODE_TAB));

        this.type = block.getType();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        if (isValid(stack)) {
            UUID id = getId(stack);

            API.instance().getStorageDiskSync().sendRequest(id);

            StorageDiskSyncData data = API.instance().getStorageDiskSync().getData(id);
            if (data != null) {
                if (data.getCapacity() == -1) {
                    tooltip.add(Component.translatable("misc.mtstorage.storage.stored", API.instance().getQuantityFormatter().format(data.getStored())).setStyle(Styles.GRAY));
                } else {
                    tooltip.add(Component.translatable("misc.mtstorage.storage.stored_capacity", API.instance().getQuantityFormatter().format(data.getStored()), API.instance().getQuantityFormatter().format(data.getCapacity())).setStyle(Styles.GRAY));
                }
            }

            if (flag.isAdvanced()) {
                tooltip.add(Component.literal(id.toString()).setStyle(Styles.GRAY));
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack storageStack = player.getItemInHand(hand);

        if (!level.isClientSide && player.isCrouching() && type != FluidStorageType.CREATIVE) {
            UUID diskId = null;
            IStorageDisk disk = null;

            if (isValid(storageStack)) {
                diskId = getId(storageStack);
                disk = API.instance().getStorageDiskManager((ServerLevel) level).get(diskId);
            }

            // Newly created fluid storages won't have a tag yet, so allow invalid disks as well.
            if (disk == null || disk.getStored() == 0) {
                ItemStack fluidStoragePart = new ItemStack(FluidStoragePartItem.getByType(type));

                if (!player.getInventory().add(fluidStoragePart.copy())) {
                    Containers.dropItemStack(level, player.getX(), player.getY(), player.getZ(), fluidStoragePart);
                }

                ItemStack processor = new ItemStack(MSItems.PROCESSORS.get(ProcessorItem.Type.BASIC).get());

                if (!player.getInventory().add(processor.copy())) {
                    Containers.dropItemStack(level, player.getX(), player.getY(), player.getZ(), processor);
                }

                ItemStack bucket = new ItemStack(Items.BUCKET);

                if (!player.getInventory().add(bucket.copy())) {
                    Containers.dropItemStack(level, player.getX(), player.getY(), player.getZ(), bucket);
                }

                if (disk != null) {
                    API.instance().getStorageDiskManager((ServerLevel) level).remove(diskId);
                    API.instance().getStorageDiskManager((ServerLevel) level).markForSaving();
                }

                return new InteractionResultHolder<>(InteractionResult.SUCCESS, new ItemStack(MSBlocks.MACHINE_CASING.get()));
            }
        }

        return new InteractionResultHolder<>(InteractionResult.PASS, storageStack);
    }

    @Override
    public int getEntityLifespan(ItemStack stack, Level level) {
        return Integer.MAX_VALUE;
    }

    private UUID getId(ItemStack disk) {
        return disk.getTag().getUUID(FluidStorageNetworkNode.NBT_ID);
    }

    private boolean isValid(ItemStack disk) {
        return disk.hasTag() && disk.getTag().hasUUID(FluidStorageNetworkNode.NBT_ID);
    }
}
