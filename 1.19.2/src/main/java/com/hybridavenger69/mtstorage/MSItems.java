package com.hybridavenger69.mtstorage;

import com.hybridavenger69.mtstorage.apiimpl.storage.FluidStorageType;
import com.hybridavenger69.mtstorage.apiimpl.storage.ItemStorageType;
import com.hybridavenger69.mtstorage.block.BaseBlock;
import com.hybridavenger69.mtstorage.item.*;
import com.hybridavenger69.mtstorage.item.blockitem.*;
import com.hybridavenger69.mtstorage.util.BlockUtils;
import com.hybridavenger69.mtstorage.util.ColorMap;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;

public final class MSItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MS.ID);

    public static final RegistryObject<QuartzEnrichedTechItem> QUARTZ_ENRICHED_TECH;
    public static final RegistryObject<SiliconItem> SILICON;
    public static final RegistryObject<ProcessorBindingItem> PROCESSOR_BINDING;
    public static final RegistryObject<WrenchItem> WRENCH;
    public static final RegistryObject<PatternItem> PATTERN;
    public static final RegistryObject<FilterItem> FILTER;
    public static final RegistryObject<StorageHousingItem> STORAGE_HOUSING;
    public static final RegistryObject<NetworkCardItem> NETWORK_CARD;
    public static final RegistryObject<SecurityCardItem> SECURITY_CARD;
    public static final RegistryObject<CoreItem> CONSTRUCTION_CORE;
    public static final RegistryObject<CoreItem> DESTRUCTION_CORE;
    public static final RegistryObject<WirelessGridItem> WIRELESS_GRID;
    public static final RegistryObject<WirelessGridItem> CREATIVE_WIRELESS_GRID;
    public static final RegistryObject<WirelessFluidGridItem> WIRELESS_FLUID_GRID;
    public static final RegistryObject<WirelessFluidGridItem> CREATIVE_WIRELESS_FLUID_GRID;
    public static final RegistryObject<PortableGridBlockItem> PORTABLE_GRID;
    public static final RegistryObject<PortableGridBlockItem> CREATIVE_PORTABLE_GRID;
    public static final RegistryObject<WirelessCraftingMonitorItem> WIRELESS_CRAFTING_MONITOR;
    public static final RegistryObject<WirelessCraftingMonitorItem> CREATIVE_WIRELESS_CRAFTING_MONITOR;
    public static final RegistryObject<BlockItem> MACHINE_CASING;
    public static final RegistryObject<CoverItem> COVER;
    public static final RegistryObject<CoverItem> HOLLOW_COVER;

    public static final Map<ProcessorItem.Type, RegistryObject<ProcessorItem>> PROCESSORS = new EnumMap<>(ProcessorItem.Type.class);

    public static final Map<ItemStorageType, RegistryObject<StoragePartItem>> ITEM_STORAGE_PARTS = new EnumMap<>(ItemStorageType.class);
    public static final Map<ItemStorageType, RegistryObject<StorageDiskItem>> ITEM_STORAGE_DISKS = new EnumMap<>(ItemStorageType.class);
    public static final Map<ItemStorageType, RegistryObject<StorageBlockItem>> STORAGE_BLOCKS = new EnumMap<>(ItemStorageType.class);

    public static final Map<FluidStorageType, RegistryObject<FluidStoragePartItem>> FLUID_STORAGE_PARTS = new EnumMap<>(FluidStorageType.class);
    public static final Map<FluidStorageType, RegistryObject<FluidStorageDiskItem>> FLUID_STORAGE_DISKS = new EnumMap<>(FluidStorageType.class);
    public static final Map<FluidStorageType, RegistryObject<FluidStorageBlockItem>> FLUID_STORAGE_BLOCKS = new EnumMap<>(FluidStorageType.class);

    public static final Map<UpgradeItem.Type, RegistryObject<UpgradeItem>> UPGRADE_ITEMS = new EnumMap<>(UpgradeItem.Type.class);

    public static final Map<TagKey<Item>, ColorMap<BlockItem>> COLORED_ITEM_TAGS = new HashMap<>();

    private static final List<Runnable> LATE_REGISTRATION = new ArrayList<>();

    public static final ColorMap<BlockItem> CRAFTER = new ColorMap<>(ITEMS, LATE_REGISTRATION);
    public static final ColorMap<BlockItem> RELAY = new ColorMap<>(ITEMS, LATE_REGISTRATION);
    public static final ColorMap<BlockItem> NETWORK_TRANSMITTER = new ColorMap<>(ITEMS, LATE_REGISTRATION);
    public static final ColorMap<BlockItem> NETWORK_RECEIVER = new ColorMap<>(ITEMS, LATE_REGISTRATION);
    public static final ColorMap<BlockItem> CONTROLLER = new ColorMap<>(ITEMS, LATE_REGISTRATION);
    public static final ColorMap<BlockItem> CREATIVE_CONTROLLER = new ColorMap<>(ITEMS, LATE_REGISTRATION);
    public static final ColorMap<BlockItem> GRID = new ColorMap<>(ITEMS, LATE_REGISTRATION);
    public static final ColorMap<BlockItem> CRAFTING_GRID = new ColorMap<>(ITEMS, LATE_REGISTRATION);
    public static final ColorMap<BlockItem> PATTERN_GRID = new ColorMap<>(ITEMS, LATE_REGISTRATION);
    public static final ColorMap<BlockItem> FLUID_GRID = new ColorMap<>(ITEMS, LATE_REGISTRATION);
    public static final ColorMap<BlockItem> SECURITY_MANAGER = new ColorMap<>(ITEMS, LATE_REGISTRATION);
    public static final ColorMap<BlockItem> WIRELESS_TRANSMITTER = new ColorMap<>(ITEMS, LATE_REGISTRATION);
    public static final ColorMap<BlockItem> DISK_MANIPULATOR = new ColorMap<>(ITEMS, LATE_REGISTRATION);
    public static final ColorMap<BlockItem> CRAFTER_MANAGER = new ColorMap<>(ITEMS, LATE_REGISTRATION);
    public static final ColorMap<BlockItem> CRAFTING_MONITOR = new ColorMap<>(ITEMS, LATE_REGISTRATION);
    public static final ColorMap<BlockItem> DETECTOR = new ColorMap<>(ITEMS, LATE_REGISTRATION);

    static {
        CONSTRUCTION_CORE = ITEMS.register("construction_core", CoreItem::new);
        DESTRUCTION_CORE = ITEMS.register("destruction_core", CoreItem::new);
        QUARTZ_ENRICHED_TECH = ITEMS.register("quartz_enriched_tech", QuartzEnrichedTechItem::new);
        PROCESSOR_BINDING = ITEMS.register("processor_binding", ProcessorBindingItem::new);

        for (ProcessorItem.Type type : ProcessorItem.Type.values()) {
            PROCESSORS.put(type, ITEMS.register(type.getName() + "_processor", ProcessorItem::new));
        }

        SILICON = ITEMS.register("silicon", SiliconItem::new);
        SECURITY_CARD = ITEMS.register("security_card", SecurityCardItem::new);
        NETWORK_CARD = ITEMS.register("network_card", NetworkCardItem::new);

        for (ItemStorageType type : ItemStorageType.values()) {
            if (type != ItemStorageType.CREATIVE) {
                ITEM_STORAGE_PARTS.put(type, ITEMS.register(type.getName() + "_storage_part", StoragePartItem::new));
            }

            ITEM_STORAGE_DISKS.put(type, ITEMS.register(type.getName() + "_storage_disk", () -> new StorageDiskItem(type)));
        }

        for (FluidStorageType type : FluidStorageType.values()) {
            if (type != FluidStorageType.CREATIVE) {
                FLUID_STORAGE_PARTS.put(type, ITEMS.register(type.getName() + "_fluid_storage_part", FluidStoragePartItem::new));
            }

            FLUID_STORAGE_DISKS.put(type, ITEMS.register(type.getName() + "_fluid_storage_disk", () -> new FluidStorageDiskItem(type)));
        }

        STORAGE_HOUSING = ITEMS.register("storage_housing", StorageHousingItem::new);

        for (UpgradeItem.Type type : UpgradeItem.Type.values()) {
            UPGRADE_ITEMS.put(type, ITEMS.register(type == UpgradeItem.Type.NORMAL ? "upgrade" : type.getName() + "_upgrade", () -> new UpgradeItem(type)));
        }

        WRENCH = ITEMS.register("wrench", WrenchItem::new);
        PATTERN = ITEMS.register("pattern", PatternItem::new);
        FILTER = ITEMS.register("filter", FilterItem::new);
        PORTABLE_GRID = ITEMS.register("portable_grid", () -> new PortableGridBlockItem(PortableGridBlockItem.Type.NORMAL));
        CREATIVE_PORTABLE_GRID = ITEMS.register("creative_portable_grid", () -> new PortableGridBlockItem(PortableGridBlockItem.Type.CREATIVE));

        registerBlockItemFor(MSBlocks.QUARTZ_ENRICHED_TECH);
        MACHINE_CASING = registerBlockItemFor(MSBlocks.MACHINE_CASING);
        COVER = ITEMS.register("cover", CoverItem::new);
        HOLLOW_COVER = ITEMS.register("hollow_cover", HollowCoverItem::new);
        registerBlockItemFor(MSBlocks.CABLE);
        registerBlockItemFor(MSBlocks.DISK_DRIVE);

        for (ItemStorageType type : ItemStorageType.values()) {
            STORAGE_BLOCKS.put(type, ITEMS.register(MSBlocks.STORAGE_BLOCKS.get(type).getId().getPath(), () -> new StorageBlockItem(MSBlocks.STORAGE_BLOCKS.get(type).get())));
        }

        for (FluidStorageType type : FluidStorageType.values()) {
            FLUID_STORAGE_BLOCKS.put(type, ITEMS.register(MSBlocks.FLUID_STORAGE_BLOCKS.get(type).getId().getPath(), () -> new FluidStorageBlockItem(MSBlocks.FLUID_STORAGE_BLOCKS.get(type).get())));
        }

        registerBlockItemFor(MSBlocks.EXTERNAL_STORAGE);
        registerBlockItemFor(MSBlocks.IMPORTER);
        registerBlockItemFor(MSBlocks.EXPORTER);
        registerBlockItemFor(MSBlocks.INTERFACE);
        registerBlockItemFor(MSBlocks.FLUID_INTERFACE);
        registerBlockItemFor(MSBlocks.STORAGE_MONITOR);
        registerBlockItemFor(MSBlocks.CONSTRUCTOR);
        registerBlockItemFor(MSBlocks.DESTRUCTOR);

        CONTROLLER.put(ColorMap.DEFAULT_COLOR, ITEMS.register(
            MSBlocks.CONTROLLER.get(ColorMap.DEFAULT_COLOR).getId().getPath(),
            () -> new ControllerBlockItem(
                MSBlocks.CONTROLLER.get(ColorMap.DEFAULT_COLOR).get(),
                ColorMap.DEFAULT_COLOR,
                BlockUtils.getBlockTranslation(MSBlocks.CONTROLLER.get(ColorMap.DEFAULT_COLOR).get())
            )
        ));
        CREATIVE_CONTROLLER.put(ColorMap.DEFAULT_COLOR, ITEMS.register(
            MSBlocks.CREATIVE_CONTROLLER.get(ColorMap.DEFAULT_COLOR).getId().getPath(),
            () -> new ControllerBlockItem(
                MSBlocks.CREATIVE_CONTROLLER.get(ColorMap.DEFAULT_COLOR).get(),
                ColorMap.DEFAULT_COLOR,
                BlockUtils.getBlockTranslation(MSBlocks.CREATIVE_CONTROLLER.get(ColorMap.DEFAULT_COLOR).get())
            )
        ));

        COLORED_ITEM_TAGS.put(ItemTags.create(new ResourceLocation(MS.ID, CONTROLLER.get(ColorMap.DEFAULT_COLOR).getId().getPath())), CONTROLLER);

        LATE_REGISTRATION.add(() -> {
            MSBlocks.CONTROLLER.forEach((color, block) -> {
                if (color != ColorMap.DEFAULT_COLOR) {
                    CONTROLLER.put(color, ITEMS.register(MSBlocks.CONTROLLER.get(color).getId().getPath(), () -> new ControllerBlockItem(MSBlocks.CONTROLLER.get(color).get(), color, Component.translatable(MSBlocks.CONTROLLER.get(ColorMap.DEFAULT_COLOR).get().getDescriptionId()))));
                }
            });

            MSBlocks.CREATIVE_CONTROLLER.forEach((color, block) -> {
                if (color != ColorMap.DEFAULT_COLOR) {
                    CREATIVE_CONTROLLER.put(color, ITEMS.register(MSBlocks.CREATIVE_CONTROLLER.get(color).getId().getPath(), () -> new ControllerBlockItem(MSBlocks.CREATIVE_CONTROLLER.get(color).get(), color, Component.translatable(MSBlocks.CREATIVE_CONTROLLER.get(ColorMap.DEFAULT_COLOR).get().getDescriptionId()))));
                }
            });
        });

        GRID.registerItemsFromBlocks(MSBlocks.GRID);
        CRAFTING_GRID.registerItemsFromBlocks(MSBlocks.CRAFTING_GRID);
        PATTERN_GRID.registerItemsFromBlocks(MSBlocks.PATTERN_GRID);
        FLUID_GRID.registerItemsFromBlocks(MSBlocks.FLUID_GRID);
        NETWORK_RECEIVER.registerItemsFromBlocks(MSBlocks.NETWORK_RECEIVER);
        NETWORK_TRANSMITTER.registerItemsFromBlocks(MSBlocks.NETWORK_TRANSMITTER);
        RELAY.registerItemsFromBlocks(MSBlocks.RELAY);
        DETECTOR.registerItemsFromBlocks(MSBlocks.DETECTOR);
        SECURITY_MANAGER.registerItemsFromBlocks(MSBlocks.SECURITY_MANAGER);
        WIRELESS_TRANSMITTER.registerItemsFromBlocks(MSBlocks.WIRELESS_TRANSMITTER);
        DISK_MANIPULATOR.registerItemsFromBlocks(MSBlocks.DISK_MANIPULATOR);
        CRAFTER.registerItemsFromBlocks(MSBlocks.CRAFTER);
        CRAFTER_MANAGER.registerItemsFromBlocks(MSBlocks.CRAFTER_MANAGER);
        CRAFTING_MONITOR.registerItemsFromBlocks(MSBlocks.CRAFTING_MONITOR);

        WIRELESS_GRID = ITEMS.register("wireless_grid", () -> new WirelessGridItem(WirelessGridItem.Type.NORMAL));
        CREATIVE_WIRELESS_GRID = ITEMS.register("creative_wireless_grid", () -> new WirelessGridItem(WirelessGridItem.Type.CREATIVE));
        WIRELESS_FLUID_GRID = ITEMS.register("wireless_fluid_grid", () -> new WirelessFluidGridItem(WirelessFluidGridItem.Type.NORMAL));
        CREATIVE_WIRELESS_FLUID_GRID = ITEMS.register("creative_wireless_fluid_grid", () -> new WirelessFluidGridItem(WirelessFluidGridItem.Type.CREATIVE));
        WIRELESS_CRAFTING_MONITOR = ITEMS.register("wireless_crafting_monitor", () -> new WirelessCraftingMonitorItem(WirelessCraftingMonitorItem.Type.NORMAL));
        CREATIVE_WIRELESS_CRAFTING_MONITOR = ITEMS.register("creative_wireless_crafting_monitor", () -> new WirelessCraftingMonitorItem(WirelessCraftingMonitorItem.Type.CREATIVE));

        LATE_REGISTRATION.forEach(Runnable::run);
    }

    private MSItems() {
    }

    private static <T extends BaseBlock> RegistryObject<BlockItem> registerBlockItemFor(RegistryObject<T> block) {
        return ITEMS.register(block.getId().getPath(), () -> new BaseBlockItem(block.get(), new Item.Properties().tab(MS.CREATIVE_MODE_TAB)));
    }

    public static void register() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
