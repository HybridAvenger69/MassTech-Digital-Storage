package com.hybridavenger69.mtstorage.network;

import com.hybridavenger69.mtstorage.api.network.grid.IGrid;
import com.hybridavenger69.mtstorage.apiimpl.network.node.GridNetworkNode;
import com.hybridavenger69.mtstorage.container.GridContainerMenu;
import com.hybridavenger69.mtstorage.container.slot.filter.FilterSlot;
import com.hybridavenger69.mtstorage.container.slot.legacy.LegacyFilterSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class SetFilterSlotMessage {
    private final int containerSlot;
    private final ItemStack stack;

    public SetFilterSlotMessage(int containerSlot, ItemStack stack) {
        this.containerSlot = containerSlot;
        this.stack = stack;
    }

    public static SetFilterSlotMessage decode(FriendlyByteBuf buf) {
        return new SetFilterSlotMessage(buf.readInt(), buf.readItem());
    }

    public static void encode(SetFilterSlotMessage message, FriendlyByteBuf buf) {
        buf.writeInt(message.containerSlot);
        buf.writeItem(message.stack);
    }

    public static void handle(SetFilterSlotMessage message, Supplier<NetworkEvent.Context> ctx) {
        if (!message.stack.isEmpty() && message.stack.getCount() <= message.stack.getMaxStackSize()) {
            Player player = ctx.get().getSender();

            if (player != null) {
                ctx.get().enqueueWork(() -> {
                    AbstractContainerMenu container = player.containerMenu;

                    if (container != null && message.containerSlot >= 0 && message.containerSlot < container.slots.size()) {
                        handle(message, container);
                    }
                });
            }
        }

        ctx.get().setPacketHandled(true);
    }

    private static void handle(SetFilterSlotMessage message, AbstractContainerMenu container) {
        Slot slot = container.getSlot(message.containerSlot);

        if (slot instanceof FilterSlot || slot instanceof LegacyFilterSlot) {
            Runnable postAction = () -> {
            };

            // Prevent the grid crafting matrix inventory listener from resetting the list.
            if (container instanceof GridContainerMenu) {
                IGrid grid = ((GridContainerMenu) container).getGrid();
                //exclude output slots
                if (grid instanceof GridNetworkNode && slot.getSlotIndex() < ((GridNetworkNode) grid).getAllowedTagList().getAllowedItemTags().size()) {
                    Set<ResourceLocation> list = new HashSet<>(((GridNetworkNode) grid).getAllowedTagList().getAllowedItemTags().get(slot.getSlotIndex()));

                    postAction = () -> {
                        ((GridNetworkNode) grid).getAllowedTagList().setAllowedItemTags(slot.getSlotIndex(), list);
                        ((GridNetworkNode) grid).markDirty();
                    };
                }
            }

            slot.set(message.stack);
            postAction.run();
        }
    }
}
