package com.hybridavenger69.mtstorage.network.grid;

import com.hybridavenger69.mtstorage.api.util.StackListResult;
import com.hybridavenger69.mtstorage.screen.BaseScreen;
import com.hybridavenger69.mtstorage.screen.grid.GridScreen;
import com.hybridavenger69.mtstorage.screen.grid.stack.IGridStack;
import com.hybridavenger69.mtstorage.blockentity.grid.portable.IPortableGrid;
import com.hybridavenger69.mtstorage.util.StackUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.network.NetworkEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public class PortableGridFluidDeltaMessage {
    private IPortableGrid portableGrid;
    private List<StackListResult<FluidStack>> deltas;

    private List<Pair<IGridStack, Integer>> clientDeltas;

    public PortableGridFluidDeltaMessage(IPortableGrid portableGrid, List<StackListResult<FluidStack>> deltas) {
        this.portableGrid = portableGrid;
        this.deltas = deltas;
    }

    public PortableGridFluidDeltaMessage(List<Pair<IGridStack, Integer>> clientDeltas) {
        this.clientDeltas = clientDeltas;
    }

    public static PortableGridFluidDeltaMessage decode(FriendlyByteBuf buf) {
        int size = buf.readInt();

        List<Pair<IGridStack, Integer>> clientDeltas = new LinkedList<>();

        for (int i = 0; i < size; ++i) {
            int delta = buf.readInt();

            clientDeltas.add(Pair.of(StackUtils.readFluidGridStack(buf), delta));
        }

        return new PortableGridFluidDeltaMessage(clientDeltas);
    }

    public static void encode(PortableGridFluidDeltaMessage message, FriendlyByteBuf buf) {
        buf.writeInt(message.deltas.size());

        for (StackListResult<FluidStack> delta : message.deltas) {
            buf.writeInt(delta.getChange());

            StackUtils.writeFluidGridStack(buf, delta.getStack(), delta.getId(), null, false, message.portableGrid.getFluidStorageTracker().get(delta.getStack()));
        }
    }

    public static void handle(PortableGridFluidDeltaMessage message, Supplier<NetworkEvent.Context> ctx) {
        BaseScreen.executeLater(GridScreen.class, grid -> {
            message.clientDeltas.forEach(p -> grid.getView().postChange(p.getLeft(), p.getRight()));
        });

        ctx.get().setPacketHandled(true);
    }
}
