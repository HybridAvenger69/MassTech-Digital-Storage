package com.hybridavenger69.mtstorage.setup;

import com.hybridavenger69.mtstorage.MS;
import com.hybridavenger69.mtstorage.api.network.grid.GridType;
import com.hybridavenger69.mtstorage.api.network.node.INetworkNode;
import com.hybridavenger69.mtstorage.api.network.node.INetworkNodeProxy;
import com.hybridavenger69.mtstorage.api.storage.StorageType;
import com.hybridavenger69.mtstorage.apiimpl.API;
import com.hybridavenger69.mtstorage.apiimpl.autocrafting.craftingmonitor.ErrorCraftingMonitorElement;
import com.hybridavenger69.mtstorage.apiimpl.autocrafting.craftingmonitor.FluidCraftingMonitorElement;
import com.hybridavenger69.mtstorage.apiimpl.autocrafting.craftingmonitor.ItemCraftingMonitorElement;
import com.hybridavenger69.mtstorage.apiimpl.autocrafting.preview.ErrorCraftingPreviewElement;
import com.hybridavenger69.mtstorage.apiimpl.autocrafting.preview.FluidCraftingPreviewElement;
import com.hybridavenger69.mtstorage.apiimpl.autocrafting.preview.ItemCraftingPreviewElement;
import com.hybridavenger69.mtstorage.apiimpl.autocrafting.task.v6.CraftingTaskFactory;
import com.hybridavenger69.mtstorage.apiimpl.network.NetworkListener;
import com.hybridavenger69.mtstorage.apiimpl.network.NetworkNodeListener;
import com.hybridavenger69.mtstorage.apiimpl.network.grid.factory.*;
import com.hybridavenger69.mtstorage.apiimpl.network.node.*;
import com.hybridavenger69.mtstorage.apiimpl.network.node.diskdrive.DiskDriveNetworkNode;
import com.hybridavenger69.mtstorage.apiimpl.network.node.diskmanipulator.DiskManipulatorNetworkNode;
import com.hybridavenger69.mtstorage.apiimpl.network.node.storage.FluidStorageNetworkNode;
import com.hybridavenger69.mtstorage.apiimpl.network.node.storage.StorageNetworkNode;
import com.hybridavenger69.mtstorage.apiimpl.storage.FluidStorageType;
import com.hybridavenger69.mtstorage.apiimpl.storage.ItemStorageType;
import com.hybridavenger69.mtstorage.apiimpl.storage.disk.factory.FluidStorageDiskFactory;
import com.hybridavenger69.mtstorage.apiimpl.storage.disk.factory.ItemStorageDiskFactory;
import com.hybridavenger69.mtstorage.apiimpl.storage.externalstorage.FluidExternalStorageProvider;
import com.hybridavenger69.mtstorage.apiimpl.storage.externalstorage.ItemExternalStorageProvider;
import com.hybridavenger69.mtstorage.block.BlockListener;
import com.hybridavenger69.mtstorage.integration.craftingtweaks.CraftingTweaksIntegration;
import com.hybridavenger69.mtstorage.integration.inventorysorter.InventorySorterIntegration;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public final class CommonSetup {
    private CommonSetup() {
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent e) {
        MS.NETWORK_HANDLER.register();

        MinecraftForge.EVENT_BUS.register(new NetworkNodeListener());
        MinecraftForge.EVENT_BUS.register(new NetworkListener());
        MinecraftForge.EVENT_BUS.register(new BlockListener());

        API.instance().getStorageDiskRegistry().add(ItemStorageDiskFactory.ID, new ItemStorageDiskFactory());
        API.instance().getStorageDiskRegistry().add(FluidStorageDiskFactory.ID, new FluidStorageDiskFactory());

        API.instance().getNetworkNodeRegistry().add(DiskDriveNetworkNode.ID, (tag, world, pos) -> readAndReturn(tag, new DiskDriveNetworkNode(world, pos)));
        API.instance().getNetworkNodeRegistry().add(CableNetworkNode.ID, (tag, world, pos) -> readAndReturn(tag, new CableNetworkNode(world, pos)));
        API.instance().getNetworkNodeRegistry().add(GridNetworkNode.ID, (tag, world, pos) -> readAndReturn(tag, new GridNetworkNode(world, pos, GridType.NORMAL)));
        API.instance().getNetworkNodeRegistry().add(GridNetworkNode.CRAFTING_ID, (tag, world, pos) -> readAndReturn(tag, new GridNetworkNode(world, pos, GridType.CRAFTING)));
        API.instance().getNetworkNodeRegistry().add(GridNetworkNode.PATTERN_ID, (tag, world, pos) -> readAndReturn(tag, new GridNetworkNode(world, pos, GridType.PATTERN)));
        API.instance().getNetworkNodeRegistry().add(GridNetworkNode.FLUID_ID, (tag, world, pos) -> readAndReturn(tag, new GridNetworkNode(world, pos, GridType.FLUID)));

        API.instance().getNetworkNodeRegistry().add(StorageNetworkNode.ONE_K_STORAGE_BLOCK_ID, (tag, world, pos) -> readAndReturn(tag, new StorageNetworkNode(world, pos, ItemStorageType.ONE_K)));
        API.instance().getNetworkNodeRegistry().add(StorageNetworkNode.FOUR_K_STORAGE_BLOCK_ID, (tag, world, pos) -> readAndReturn(tag, new StorageNetworkNode(world, pos, ItemStorageType.FOUR_K)));
        API.instance().getNetworkNodeRegistry().add(StorageNetworkNode.SIXTEEN_K_STORAGE_BLOCK_ID, (tag, world, pos) -> readAndReturn(tag, new StorageNetworkNode(world, pos, ItemStorageType.SIXTEEN_K)));
        API.instance().getNetworkNodeRegistry().add(StorageNetworkNode.SIXTY_FOUR_K_STORAGE_BLOCK_ID, (tag, world, pos) -> readAndReturn(tag, new StorageNetworkNode(world, pos, ItemStorageType.SIXTY_FOUR_K)));
        API.instance().getNetworkNodeRegistry().add(StorageNetworkNode.ONE_TWENTY_EIGHT_K_STORAGE_BLOCK_ID, (tag, world, pos) -> readAndReturn(tag, new StorageNetworkNode(world, pos, ItemStorageType.ONE_TWENTY_EIGHT_K)));
        API.instance().getNetworkNodeRegistry().add(StorageNetworkNode.TWO_FIFTY_SIX_K_STORAGE_BLOCK_ID, (tag, world, pos) -> readAndReturn(tag, new StorageNetworkNode(world, pos, ItemStorageType.TWO_FIFTY_SIX_K)));
        API.instance().getNetworkNodeRegistry().add(StorageNetworkNode.FIVE_TWELVE_K_STORAGE_BLOCK_ID, (tag, world, pos) -> readAndReturn(tag, new StorageNetworkNode(world, pos, ItemStorageType.FIVE_TWELVE_K)));
        API.instance().getNetworkNodeRegistry().add(StorageNetworkNode.ONE_ZERO_TWENTY_FOUR_K_STORAGE_BLOCK_ID, (tag, world, pos) -> readAndReturn(tag, new StorageNetworkNode(world, pos, ItemStorageType.ONE_ZERO_TWENTY_FOUR_K)));
        API.instance().getNetworkNodeRegistry().add(StorageNetworkNode.TWO_ZERO_FOURTY_EIGHT_K_STORAGE_BLOCK_ID, (tag, world, pos) -> readAndReturn(tag, new StorageNetworkNode(world, pos, ItemStorageType.TWO_ZERO_FOUR_EIGHT_K)));



        API.instance().getNetworkNodeRegistry().add(StorageNetworkNode.CREATIVE_STORAGE_BLOCK_ID, (tag, world, pos) -> readAndReturn(tag, new StorageNetworkNode(world, pos, ItemStorageType.CREATIVE)));

        API.instance().getNetworkNodeRegistry().add(FluidStorageNetworkNode.SIXTY_FOUR_K_FLUID_STORAGE_BLOCK_ID, (tag, world, pos) -> readAndReturn(tag, new FluidStorageNetworkNode(world, pos, FluidStorageType.SIXTY_FOUR_K)));
        API.instance().getNetworkNodeRegistry().add(FluidStorageNetworkNode.TWO_HUNDRED_FIFTY_SIX_K_FLUID_STORAGE_BLOCK_ID, (tag, world, pos) -> readAndReturn(tag, new FluidStorageNetworkNode(world, pos, FluidStorageType.TWO_HUNDRED_FIFTY_SIX_K)));
        API.instance().getNetworkNodeRegistry().add(FluidStorageNetworkNode.THOUSAND_TWENTY_FOUR_K_FLUID_STORAGE_BLOCK_ID, (tag, world, pos) -> readAndReturn(tag, new FluidStorageNetworkNode(world, pos, FluidStorageType.THOUSAND_TWENTY_FOUR_K)));
        API.instance().getNetworkNodeRegistry().add(FluidStorageNetworkNode.FOUR_THOUSAND_NINETY_SIX_K_FLUID_STORAGE_BLOCK_ID, (tag, world, pos) -> readAndReturn(tag, new FluidStorageNetworkNode(world, pos, FluidStorageType.FOUR_THOUSAND_NINETY_SIX_K)));
        API.instance().getNetworkNodeRegistry().add(FluidStorageNetworkNode.CREATIVE_FLUID_STORAGE_BLOCK_ID, (tag, world, pos) -> readAndReturn(tag, new FluidStorageNetworkNode(world, pos, FluidStorageType.CREATIVE)));

        API.instance().getNetworkNodeRegistry().add(ExternalStorageNetworkNode.ID, (tag, world, pos) -> readAndReturn(tag, new ExternalStorageNetworkNode(world, pos)));
        API.instance().getNetworkNodeRegistry().add(ImporterNetworkNode.ID, (tag, world, pos) -> readAndReturn(tag, new ImporterNetworkNode(world, pos)));
        API.instance().getNetworkNodeRegistry().add(ExporterNetworkNode.ID, (tag, world, pos) -> readAndReturn(tag, new ExporterNetworkNode(world, pos)));
        API.instance().getNetworkNodeRegistry().add(NetworkReceiverNetworkNode.ID, (tag, world, pos) -> readAndReturn(tag, new NetworkReceiverNetworkNode(world, pos)));
        API.instance().getNetworkNodeRegistry().add(NetworkTransmitterNetworkNode.ID, (tag, world, pos) -> readAndReturn(tag, new NetworkTransmitterNetworkNode(world, pos)));
        API.instance().getNetworkNodeRegistry().add(RelayNetworkNode.ID, (tag, world, pos) -> readAndReturn(tag, new RelayNetworkNode(world, pos)));
        API.instance().getNetworkNodeRegistry().add(DetectorNetworkNode.ID, (tag, world, pos) -> readAndReturn(tag, new DetectorNetworkNode(world, pos)));
        API.instance().getNetworkNodeRegistry().add(SecurityManagerNetworkNode.ID, (tag, world, pos) -> readAndReturn(tag, new SecurityManagerNetworkNode(world, pos)));
        API.instance().getNetworkNodeRegistry().add(InterfaceNetworkNode.ID, (tag, world, pos) -> readAndReturn(tag, new InterfaceNetworkNode(world, pos)));
        API.instance().getNetworkNodeRegistry().add(FluidInterfaceNetworkNode.ID, (tag, world, pos) -> readAndReturn(tag, new FluidInterfaceNetworkNode(world, pos)));
        API.instance().getNetworkNodeRegistry().add(WirelessTransmitterNetworkNode.ID, (tag, world, pos) -> readAndReturn(tag, new WirelessTransmitterNetworkNode(world, pos)));
        API.instance().getNetworkNodeRegistry().add(StorageMonitorNetworkNode.ID, (tag, world, pos) -> readAndReturn(tag, new StorageMonitorNetworkNode(world, pos)));
        API.instance().getNetworkNodeRegistry().add(ConstructorNetworkNode.ID, (tag, world, pos) -> readAndReturn(tag, new ConstructorNetworkNode(world, pos)));
        API.instance().getNetworkNodeRegistry().add(DestructorNetworkNode.ID, (tag, world, pos) -> readAndReturn(tag, new DestructorNetworkNode(world, pos)));
        API.instance().getNetworkNodeRegistry().add(DiskManipulatorNetworkNode.ID, (tag, world, pos) -> readAndReturn(tag, new DiskManipulatorNetworkNode(world, pos)));
        API.instance().getNetworkNodeRegistry().add(CrafterNetworkNode.ID, (tag, world, pos) -> readAndReturn(tag, new CrafterNetworkNode(world, pos)));
        API.instance().getNetworkNodeRegistry().add(CrafterManagerNetworkNode.ID, (tag, world, pos) -> readAndReturn(tag, new CrafterManagerNetworkNode(world, pos)));
        API.instance().getNetworkNodeRegistry().add(CraftingMonitorNetworkNode.ID, (tag, world, pos) -> readAndReturn(tag, new CraftingMonitorNetworkNode(world, pos)));

        API.instance().getGridManager().add(GridBlockGridFactory.ID, new GridBlockGridFactory());
        API.instance().getGridManager().add(WirelessGridGridFactory.ID, new WirelessGridGridFactory());
        API.instance().getGridManager().add(WirelessFluidGridGridFactory.ID, new WirelessFluidGridGridFactory());
        API.instance().getGridManager().add(PortableGridGridFactory.ID, new PortableGridGridFactory());
        API.instance().getGridManager().add(PortableGridBlockGridFactory.ID, new PortableGridBlockGridFactory());

        API.instance().addExternalStorageProvider(StorageType.ITEM, new ItemExternalStorageProvider());
        API.instance().addExternalStorageProvider(StorageType.FLUID, new FluidExternalStorageProvider());

        API.instance().getCraftingPreviewElementRegistry().add(ItemCraftingPreviewElement.ID, ItemCraftingPreviewElement::read);
        API.instance().getCraftingPreviewElementRegistry().add(FluidCraftingPreviewElement.ID, FluidCraftingPreviewElement::read);
        API.instance().getCraftingPreviewElementRegistry().add(ErrorCraftingPreviewElement.ID, ErrorCraftingPreviewElement::read);

        API.instance().getCraftingMonitorElementRegistry().add(ItemCraftingMonitorElement.ID, ItemCraftingMonitorElement::read);
        API.instance().getCraftingMonitorElementRegistry().add(FluidCraftingMonitorElement.ID, FluidCraftingMonitorElement::read);
        API.instance().getCraftingMonitorElementRegistry().add(ErrorCraftingMonitorElement.ID, ErrorCraftingMonitorElement::read);

        API.instance().getCraftingTaskRegistry().add(CraftingTaskFactory.ID, new CraftingTaskFactory());

        if (CraftingTweaksIntegration.isLoaded()) {
            CraftingTweaksIntegration.register();
        }

        if (InventorySorterIntegration.isLoaded()) {
            InventorySorterIntegration.register();
        }
    }

    private static INetworkNode readAndReturn(CompoundTag tag, NetworkNode node) {
        node.read(tag);

        return node;
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent e) {
        e.register(INetworkNodeProxy.class);
    }
}
