package com.hybridavenger69.mtstorage.network.craftingmonitor;

import com.hybridavenger69.mtstorage.api.autocrafting.craftingmonitor.ICraftingMonitorElement;
import com.hybridavenger69.mtstorage.api.autocrafting.task.CraftingTaskReadException;
import com.hybridavenger69.mtstorage.api.autocrafting.task.ICraftingRequestInfo;
import com.hybridavenger69.mtstorage.api.autocrafting.task.ICraftingTask;
import com.hybridavenger69.mtstorage.api.network.grid.IGridTab;
import com.hybridavenger69.mtstorage.apiimpl.API;
import com.hybridavenger69.mtstorage.network.ClientProxy;
import com.hybridavenger69.mtstorage.screen.CraftingMonitorScreen;
import com.hybridavenger69.mtstorage.blockentity.craftingmonitor.ICraftingMonitor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

public class CraftingMonitorUpdateMessage {
    private static final Logger LOGGER = LogManager.getLogger(CraftingMonitorUpdateMessage.class);

    private ICraftingMonitor craftingMonitor;

    private List<IGridTab> tasks = new ArrayList<>();

    public CraftingMonitorUpdateMessage(ICraftingMonitor craftingMonitor) {
        this.craftingMonitor = craftingMonitor;
    }

    public CraftingMonitorUpdateMessage(List<IGridTab> tasks) {
        this.tasks = tasks;
    }

    public static CraftingMonitorUpdateMessage decode(FriendlyByteBuf buf) {
        int size = buf.readInt();

        List<IGridTab> tasks = new ArrayList<>();

        for (int i = 0; i < size; ++i) {
            UUID id = buf.readUUID();

            ICraftingRequestInfo requested = null;
            try {
                requested = API.instance().createCraftingRequestInfo(buf.readNbt());
            } catch (CraftingTaskReadException e) {
                LOGGER.error("Could not create crafting request info", e);
            }

            int qty = buf.readInt();
            long executionStarted = buf.readLong();
            int percentage = buf.readInt();

            List<ICraftingMonitorElement> elements = new ArrayList<>();

            int elementCount = buf.readInt();

            for (int j = 0; j < elementCount; ++j) {
                Function<FriendlyByteBuf, ICraftingMonitorElement> factory = API.instance().getCraftingMonitorElementRegistry().get(buf.readResourceLocation());

                if (factory != null) {
                    elements.add(factory.apply(buf));
                }
            }

            tasks.add(new CraftingMonitorScreen.Task(id, requested, qty, executionStarted, percentage, elements));
        }

        return new CraftingMonitorUpdateMessage(tasks);
    }

    public static void encode(CraftingMonitorUpdateMessage message, FriendlyByteBuf buf) {
        buf.writeInt(message.craftingMonitor.getTasks().size());

        for (ICraftingTask task : message.craftingMonitor.getTasks()) {
            buf.writeUUID(task.getId());
            buf.writeNbt(task.getRequested().writeToNbt());
            buf.writeInt(task.getQuantity());
            buf.writeLong(task.getStartTime());
            buf.writeInt(task.getCompletionPercentage());

            List<ICraftingMonitorElement> elements = task.getCraftingMonitorElements();

            buf.writeInt(elements.size());

            for (ICraftingMonitorElement element : elements) {
                buf.writeResourceLocation(element.getId());

                element.write(buf);
            }
        }
    }

    public static void handle(CraftingMonitorUpdateMessage message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> ClientProxy.onReceivedCraftingMonitorUpdateMessage(message));
        ctx.get().setPacketHandled(true);
    }

    public List<IGridTab> getTasks() {
        return tasks;
    }
}
