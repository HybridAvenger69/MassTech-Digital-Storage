package com.hybridavenger69.mtstorage.network;

import com.hybridavenger69.mtstorage.container.slot.filter.FluidFilterSlot;
import com.hybridavenger69.mtstorage.screen.BaseScreen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FluidFilterSlotUpdateMessage {
    private final int containerSlot;
    private final FluidStack stack;

    public FluidFilterSlotUpdateMessage(int containerSlot, FluidStack stack) {
        this.containerSlot = containerSlot;
        this.stack = stack;
    }

    public static void encode(FluidFilterSlotUpdateMessage message, FriendlyByteBuf buf) {
        buf.writeInt(message.containerSlot);
        message.stack.writeToPacket(buf);
    }

    public static FluidFilterSlotUpdateMessage decode(FriendlyByteBuf buf) {
        return new FluidFilterSlotUpdateMessage(buf.readInt(), FluidStack.readFromPacket(buf));
    }

    public static void handle(FluidFilterSlotUpdateMessage message, Supplier<NetworkEvent.Context> ctx) {
        BaseScreen.executeLater(gui -> {
            if (message.containerSlot >= 0 && message.containerSlot < gui.getMenu().slots.size()) {
                Slot slot = gui.getMenu().getSlot(message.containerSlot);

                if (slot instanceof FluidFilterSlot) {
                    ((FluidFilterSlot) slot).getFluidInventory().setFluid(slot.getSlotIndex(), message.stack);
                }
            }
        });

        ctx.get().setPacketHandled(true);
    }
}
