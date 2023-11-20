package com.hybridavenger69.mtstorage.setup;

import com.hybridavenger69.hybridlib.HybridIDS;
import com.hybridavenger69.mtstorage.MS;
import com.hybridavenger69.mtstorage.command.disk.CreateDiskCommand;
import com.hybridavenger69.mtstorage.command.disk.ListDiskCommand;
import com.hybridavenger69.mtstorage.command.network.GetNetworkCommand;
import com.hybridavenger69.mtstorage.command.network.ListNetworkCommand;
import com.hybridavenger69.mtstorage.command.pattern.PatternDumpCommand;
import net.minecraft.commands.Commands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ServerSetup {
    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent e) {
        e.getDispatcher().register(Commands.literal(HybridIDS.MTStorage_MODID)
            .then(Commands.literal("pattern")
                .then(PatternDumpCommand.register()))
            .then(Commands.literal("disk")
                .then(CreateDiskCommand.register())
                .then(ListDiskCommand.register()))
            .then(Commands.literal("network")
                .then(GetNetworkCommand.register())
                .then(ListNetworkCommand.register())));
    }
}
