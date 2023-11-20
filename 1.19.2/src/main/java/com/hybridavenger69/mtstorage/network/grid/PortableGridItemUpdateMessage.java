package com.hybridavenger69.mtstorage.network.grid;

import com.hybridavenger69.mtstorage.api.util.StackListEntry;
import com.hybridavenger69.mtstorage.screen.BaseScreen;
import com.hybridavenger69.mtstorage.screen.grid.GridScreen;
import com.hybridavenger69.mtstorage.screen.grid.stack.IGridStack;
import com.hybridavenger69.mtstorage.screen.grid.view.GridViewImpl;
import com.hybridavenger69.mtstorage.blockentity.grid.portable.IPortableGrid;
import com.hybridavenger69.mtstorage.util.StackUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class PortableGridItemUpdateMessage {
    private IPortableGrid portableGrid;

    private List<IGridStack> stacks = new ArrayList<>();

    public PortableGridItemUpdateMessage(List<IGridStack> stacks) {
        this.stacks = stacks;
    }

    public PortableGridItemUpdateMessage(IPortableGrid portableGrid) {
        this.portableGrid = portableGrid;
    }

    public static PortableGridItemUpdateMessage decode(FriendlyByteBuf buf) {
        int size = buf.readInt();

        List<IGridStack> stacks = new ArrayList<>();

        for (int i = 0; i < size; ++i) {
            stacks.add(StackUtils.readItemGridStack(buf));
        }

        return new PortableGridItemUpdateMessage(stacks);
    }

    public static void encode(PortableGridItemUpdateMessage message, FriendlyByteBuf buf) {
        int size = message.portableGrid.getItemCache().getList().getStacks().size();

        buf.writeInt(size);

        for (StackListEntry<ItemStack> stack : message.portableGrid.getItemCache().getList().getStacks()) {
            StackUtils.writeItemGridStack(buf, stack.getStack(), stack.getId(), null, false, message.portableGrid.getItemStorageTracker().get(stack.getStack()));
        }
    }

    public static void handle(PortableGridItemUpdateMessage message, Supplier<NetworkEvent.Context> ctx) {
        BaseScreen.executeLater(GridScreen.class, grid -> {
            grid.setView(new GridViewImpl(grid, GridScreen.getDefaultSorter(), GridScreen.getSorters()));
            grid.getView().setStacks(message.stacks);
            grid.getView().forceSort();
        });

        ctx.get().setPacketHandled(true);
    }
}
