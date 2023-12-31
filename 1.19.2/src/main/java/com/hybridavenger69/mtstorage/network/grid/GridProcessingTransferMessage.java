package com.hybridavenger69.mtstorage.network.grid;

import com.hybridavenger69.mtstorage.api.network.grid.GridType;
import com.hybridavenger69.mtstorage.api.network.grid.IGrid;
import com.hybridavenger69.mtstorage.apiimpl.network.node.GridNetworkNode;
import com.hybridavenger69.mtstorage.container.GridContainerMenu;
import com.hybridavenger69.mtstorage.inventory.fluid.FluidInventory;
import com.hybridavenger69.mtstorage.inventory.item.BaseItemHandler;
import com.hybridavenger69.mtstorage.util.StackUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class GridProcessingTransferMessage {
    private final Collection<ItemStack> inputs;
    private final Collection<ItemStack> outputs;
    private final Collection<FluidStack> fluidInputs;
    private final Collection<FluidStack> fluidOutputs;

    public GridProcessingTransferMessage(Collection<ItemStack> inputs, Collection<ItemStack> outputs, Collection<FluidStack> fluidInputs, Collection<FluidStack> fluidOutputs) {
        this.inputs = inputs;
        this.outputs = outputs;
        this.fluidInputs = fluidInputs;
        this.fluidOutputs = fluidOutputs;
    }

    public static GridProcessingTransferMessage decode(FriendlyByteBuf buf) {
        int size = buf.readInt();

        List<ItemStack> inputs = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            inputs.add(StackUtils.readItemStack(buf));
        }

        size = buf.readInt();

        List<ItemStack> outputs = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            outputs.add(StackUtils.readItemStack(buf));
        }

        size = buf.readInt();

        List<FluidStack> fluidInputs = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            fluidInputs.add(FluidStack.readFromPacket(buf));
        }

        size = buf.readInt();

        List<FluidStack> fluidOutputs = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            fluidOutputs.add(FluidStack.readFromPacket(buf));
        }

        return new GridProcessingTransferMessage(inputs, outputs, fluidInputs, fluidOutputs);
    }

    public static void encode(GridProcessingTransferMessage message, FriendlyByteBuf buf) {
        buf.writeInt(message.inputs.size());

        for (ItemStack stack : message.inputs) {
            StackUtils.writeItemStack(buf, stack);
        }

        buf.writeInt(message.outputs.size());

        for (ItemStack stack : message.outputs) {
            StackUtils.writeItemStack(buf, stack);
        }

        buf.writeInt(message.fluidInputs.size());

        for (FluidStack stack : message.fluidInputs) {
            stack.writeToPacket(buf);
        }

        buf.writeInt(message.fluidOutputs.size());

        for (FluidStack stack : message.fluidOutputs) {
            stack.writeToPacket(buf);
        }
    }

    public static void handle(GridProcessingTransferMessage message, Supplier<NetworkEvent.Context> ctx) {
        Player player = ctx.get().getSender();

        if (player != null) {
            ctx.get().enqueueWork(() -> {
                if (player.containerMenu instanceof GridContainerMenu) {
                    IGrid grid = ((GridContainerMenu) player.containerMenu).getGrid();

                    if (grid.getGridType() == GridType.PATTERN) {
                        BaseItemHandler handler = ((GridNetworkNode) grid).getProcessingMatrix();
                        FluidInventory handlerFluid = ((GridNetworkNode) grid).getProcessingMatrixFluids();

                        clearInputsAndOutputs(handler);
                        clearInputsAndOutputs(handlerFluid);

                        setInputs(handler, message.inputs, handlerFluid, message.fluidInputs);
                        setOutputs(handler, message.outputs, handlerFluid, message.fluidOutputs);


                        ((GridNetworkNode) grid).setProcessingPattern(true);
                        ((GridNetworkNode) grid).markDirty();
                    }
                }
            });
        }

        ctx.get().setPacketHandled(true);
    }

    private static void clearInputsAndOutputs(BaseItemHandler handler) {
        for (int i = 0; i < handler.getSlots(); ++i) {
            handler.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    private static void clearInputsAndOutputs(FluidInventory handler) {
        for (int i = 0; i < handler.getSlots(); ++i) {
            handler.setFluid(i, FluidStack.EMPTY);
        }
    }

    private static void setInputs(BaseItemHandler handler, Collection<ItemStack> stacks, FluidInventory fluidHandler, Collection<FluidStack> fluidStacks) {
        setSlots(handler, stacks, fluidHandler, fluidStacks, 0, handler.getSlots() / 2);
    }

    private static void setOutputs(BaseItemHandler handler, Collection<ItemStack> stacks, FluidInventory fluidHandler, Collection<FluidStack> fluidStacks) {
        setSlots(handler, stacks, fluidHandler, fluidStacks, handler.getSlots() / 2, handler.getSlots());
    }

    private static void setSlots(BaseItemHandler handler, Collection<ItemStack> stacks, FluidInventory fluidHandler, Collection<FluidStack> fluidStacks, int begin, int end) {
        for (ItemStack stack : stacks) {
            handler.setStackInSlot(begin, stack);

            begin++;

            if (begin >= end) {
                break;
            }
        }
        for (FluidStack stack : fluidStacks) {

            fluidHandler.setFluid(begin, stack.copy());

            begin++;

            if (begin >= end) {
                break;
            }
        }
    }
}
