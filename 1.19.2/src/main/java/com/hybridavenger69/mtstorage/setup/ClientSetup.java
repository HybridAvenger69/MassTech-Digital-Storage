package com.hybridavenger69.mtstorage.setup;

import com.hybridavenger69.mtstorage.*;
import com.hybridavenger69.mtstorage.apiimpl.API;
import com.hybridavenger69.mtstorage.apiimpl.network.node.cover.CoverType;
import com.hybridavenger69.mtstorage.container.CrafterContainerMenu;
import com.hybridavenger69.mtstorage.container.CrafterManagerContainerMenu;
import com.hybridavenger69.mtstorage.container.slot.CrafterManagerSlot;
import com.hybridavenger69.mtstorage.item.property.ControllerItemPropertyGetter;
import com.hybridavenger69.mtstorage.item.property.NetworkItemPropertyGetter;
import com.hybridavenger69.mtstorage.item.property.SecurityCardItemPropertyGetter;
import com.hybridavenger69.mtstorage.render.BakedModelOverrideRegistry;
import com.hybridavenger69.mtstorage.render.blockentity.StorageMonitorBlockEntityRenderer;
import com.hybridavenger69.mtstorage.render.color.PatternItemColor;
import com.hybridavenger69.mtstorage.render.model.DiskDriveGeometryLoader;
import com.hybridavenger69.mtstorage.render.model.DiskManipulatorGeometryLoader;
import com.hybridavenger69.mtstorage.render.model.PortableGridGeometryLoader;
import com.hybridavenger69.mtstorage.render.model.baked.CableCoverBakedModel;
import com.hybridavenger69.mtstorage.render.model.baked.CableCoverItemBakedModel;
import com.hybridavenger69.mtstorage.render.model.baked.PatternBakedModel;
import com.hybridavenger69.mtstorage.render.resourcepack.ResourcePackListener;
import com.hybridavenger69.mtstorage.screen.*;
import com.hybridavenger69.mtstorage.screen.factory.CrafterManagerScreenFactory;
import com.hybridavenger69.mtstorage.screen.factory.GridScreenFactory;
import com.hybridavenger69.mtstorage.util.ColorMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.Arrays;
import java.util.function.BiConsumer;

public final class ClientSetup {
    private static final ResourceLocation DISK_RESOURCE = new ResourceLocation(MS.ID + ":block/disks/disk");
    private static final ResourceLocation DISK_NEAR_CAPACITY_RESOURCE = new ResourceLocation(MS.ID + ":block/disks/disk_near_capacity");
    private static final ResourceLocation DISK_FULL_RESOURCE = new ResourceLocation(MS.ID + ":block/disks/disk_full");
    private static final ResourceLocation DISK_DISCONNECTED_RESOURCE = new ResourceLocation(MS.ID + ":block/disks/disk_disconnected");

    private static final ResourceLocation CONNECTED = new ResourceLocation("connected");

    private static final BakedModelOverrideRegistry BAKED_MODEL_OVERRIDE_REGISTRY = new BakedModelOverrideRegistry();

    private static ResourceLocation[] getMultipleColoredModels(DyeColor color, String... paths) {
        return Arrays.stream(paths).map(path -> getColoredModel(color, path)).toArray(ResourceLocation[]::new);
    }

    private static ResourceLocation getColoredModel(DyeColor color, String path) {
        return new ResourceLocation(MS.ID, path + color);
    }

    private static void forEachColorApply(String name, BiConsumer<ResourceLocation, DyeColor> consumer) {
        for (DyeColor color : DyeColor.values()) {
            String prefix = color == ColorMap.DEFAULT_COLOR ? "" : color + "_";
            consumer.accept(new ResourceLocation(MS.ID, prefix + name), color);
        }
    }

    private ClientSetup() {
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent e) {
        MinecraftForge.EVENT_BUS.register(new KeyInputListener());

        registerBakedModelOverrides();
        registerPatternRenderHandlers();

        // MenuScreens isn't thread safe
        e.enqueueWork(() -> {
            MenuScreens.register(MSContainerMenus.FILTER.get(), FilterScreen::new);
            MenuScreens.register(MSContainerMenus.CONTROLLER.get(), ControllerScreen::new);
            MenuScreens.register(MSContainerMenus.DISK_DRIVE.get(), DiskDriveScreen::new);
            MenuScreens.register(MSContainerMenus.GRID.get(), new GridScreenFactory());
            MenuScreens.register(MSContainerMenus.STORAGE_BLOCK.get(), StorageBlockScreen::new);
            MenuScreens.register(MSContainerMenus.FLUID_STORAGE_BLOCK.get(), FluidStorageBlockScreen::new);
            MenuScreens.register(MSContainerMenus.EXTERNAL_STORAGE.get(), ExternalStorageScreen::new);
            MenuScreens.register(MSContainerMenus.IMPORTER.get(), ImporterScreen::new);
            MenuScreens.register(MSContainerMenus.EXPORTER.get(), ExporterScreen::new);
            MenuScreens.register(MSContainerMenus.NETWORK_TRANSMITTER.get(), NetworkTransmitterScreen::new);
            MenuScreens.register(MSContainerMenus.RELAY.get(), RelayScreen::new);
            MenuScreens.register(MSContainerMenus.DETECTOR.get(), DetectorScreen::new);
            MenuScreens.register(MSContainerMenus.SECURITY_MANAGER.get(), SecurityManagerScreen::new);
            MenuScreens.register(MSContainerMenus.INTERFACE.get(), InterfaceScreen::new);
            MenuScreens.register(MSContainerMenus.FLUID_INTERFACE.get(), FluidInterfaceScreen::new);
            MenuScreens.register(MSContainerMenus.WIRELESS_TRANSMITTER.get(), WirelessTransmitterScreen::new);
            MenuScreens.register(MSContainerMenus.STORAGE_MONITOR.get(), StorageMonitorScreen::new);
            MenuScreens.register(MSContainerMenus.CONSTRUCTOR.get(), ConstructorScreen::new);
            MenuScreens.register(MSContainerMenus.DESTRUCTOR.get(), DestructorScreen::new);
            MenuScreens.register(MSContainerMenus.DISK_MANIPULATOR.get(), DiskManipulatorScreen::new);
            MenuScreens.register(MSContainerMenus.CRAFTER.get(), CrafterScreen::new);
            MenuScreens.register(MSContainerMenus.CRAFTER_MANAGER.get(), new CrafterManagerScreenFactory());
            MenuScreens.register(MSContainerMenus.CRAFTING_MONITOR.get(), CraftingMonitorScreen::new);
            MenuScreens.register(MSContainerMenus.WIRELESS_CRAFTING_MONITOR.get(), CraftingMonitorScreen::new);
        });

        BlockEntityRenderers.register(MSBlockEntities.STORAGE_MONITOR.get(), ctx -> new StorageMonitorBlockEntityRenderer());

        // ItemProperties isn't thread safe
        e.enqueueWork(() -> {
            ItemProperties.register(MSItems.SECURITY_CARD.get(), new ResourceLocation("active"), new SecurityCardItemPropertyGetter());

            MSItems.CONTROLLER.values().forEach(controller -> ItemProperties.register(controller.get(), new ResourceLocation("energy_type"), new ControllerItemPropertyGetter()));
            MSItems.CREATIVE_CONTROLLER.values().forEach(controller -> ItemProperties.register(controller.get(), new ResourceLocation("energy_type"), new ControllerItemPropertyGetter()));

            ItemProperties.register(MSItems.WIRELESS_CRAFTING_MONITOR.get(), CONNECTED, new NetworkItemPropertyGetter());
            ItemProperties.register(MSItems.CREATIVE_WIRELESS_CRAFTING_MONITOR.get(), CONNECTED, new NetworkItemPropertyGetter());

            ItemProperties.register(MSItems.WIRELESS_GRID.get(), CONNECTED, new NetworkItemPropertyGetter());
            ItemProperties.register(MSItems.CREATIVE_WIRELESS_GRID.get(), CONNECTED, new NetworkItemPropertyGetter());

            ItemProperties.register(MSItems.WIRELESS_FLUID_GRID.get(), CONNECTED, new NetworkItemPropertyGetter());
            ItemProperties.register(MSItems.CREATIVE_WIRELESS_FLUID_GRID.get(), CONNECTED, new NetworkItemPropertyGetter());
        });
    }

    private static void registerPatternRenderHandlers() {
        API.instance().addPatternRenderHandler(pattern -> Screen.hasShiftDown());
        API.instance().addPatternRenderHandler(pattern -> {
            AbstractContainerMenu container = Minecraft.getInstance().player.containerMenu;

            if (container instanceof CrafterManagerContainerMenu) {
                for (Slot slot : container.slots) {
                    if (slot instanceof CrafterManagerSlot && slot.getItem() == pattern) {
                        return true;
                    }
                }
            }

            return false;
        });
        API.instance().addPatternRenderHandler(pattern -> {
            AbstractContainerMenu container = Minecraft.getInstance().player.containerMenu;

            if (container instanceof CrafterContainerMenu) {
                for (int i = 0; i < 9; ++i) {
                    if (container.getSlot(i).getItem() == pattern) {
                        return true;
                    }
                }
            }

            return false;
        });
    }

    // TODO: we have probably too much emissivity (when disconnected)

    private static void registerBakedModelOverrides() {
        BAKED_MODEL_OVERRIDE_REGISTRY.add(new ResourceLocation(MS.ID, "cable"), (base, registry) -> new CableCoverBakedModel(base));
        BAKED_MODEL_OVERRIDE_REGISTRY.add(new ResourceLocation(MS.ID, "exporter"), (base, registry) -> new CableCoverBakedModel(base));
        BAKED_MODEL_OVERRIDE_REGISTRY.add(new ResourceLocation(MS.ID, "importer"), (base, registry) -> new CableCoverBakedModel(base));
        BAKED_MODEL_OVERRIDE_REGISTRY.add(new ResourceLocation(MS.ID, "external_storage"), (base, registry) -> new CableCoverBakedModel(base));
        BAKED_MODEL_OVERRIDE_REGISTRY.add(new ResourceLocation(MS.ID, "cover"), (base, registry) -> new CableCoverItemBakedModel(ItemStack.EMPTY, CoverType.NORMAL));
        BAKED_MODEL_OVERRIDE_REGISTRY.add(new ResourceLocation(MS.ID, "hollow_cover"), (base, registry) -> new CableCoverItemBakedModel(ItemStack.EMPTY, CoverType.HOLLOW));
        BAKED_MODEL_OVERRIDE_REGISTRY.add(new ResourceLocation(MS.ID, "pattern"), (base, registry) -> new PatternBakedModel(base));
    }

    @SubscribeEvent
    public static void onRegisterColorBindings(RegisterColorHandlersEvent.Item e) {
        e.register(new PatternItemColor(), MSItems.PATTERN.get());
    }

    @SubscribeEvent
    public static void onRegisterKeymappings(RegisterKeyMappingsEvent e) {
        e.register(MSKeyBindings.FOCUS_SEARCH_BAR);
        e.register(MSKeyBindings.CLEAR_GRID_CRAFTING_MATRIX);
        e.register(MSKeyBindings.OPEN_WIRELESS_GRID);
        e.register(MSKeyBindings.OPEN_WIRELESS_FLUID_GRID);
        e.register(MSKeyBindings.OPEN_WIRELESS_CRAFTING_MONITOR);
        e.register(MSKeyBindings.OPEN_PORTABLE_GRID);
    }

    @SubscribeEvent
    public static void addReloadListener(AddReloadListenerEvent event) {
        event.addListener(new ResourcePackListener());
    }

    @SubscribeEvent
    public static void onRegisterAdditionalModels(ModelEvent.RegisterAdditional e) {
        e.register(DISK_RESOURCE);
        e.register(DISK_NEAR_CAPACITY_RESOURCE);
        e.register(DISK_FULL_RESOURCE);
        e.register(DISK_DISCONNECTED_RESOURCE);

        e.register(new ResourceLocation(MS.ID + ":block/disk_manipulator/disconnected"));

        for (DyeColor color : DyeColor.values()) {
            e.register(new ResourceLocation(MS.ID + ":block/disk_manipulator/" + color));
        }

    }

    @SubscribeEvent
    public static void onModelBake(ModelEvent.BakingCompleted e) {
        for (ResourceLocation id : e.getModels().keySet()) {
            BakedModelOverrideRegistry.BakedModelOverrideFactory factory = BAKED_MODEL_OVERRIDE_REGISTRY.get(new ResourceLocation(id.getNamespace(), id.getPath()));

            if (factory != null) {
                e.getModels().put(id, factory.create(e.getModels().get(id), e.getModels()));
            }
        }
    }

    @SubscribeEvent
    public static void onTextureStitch(TextureStitchEvent.Pre event) {
        if (event.getAtlas().location().equals(InventoryMenu.BLOCK_ATLAS)) {
            event.addSprite(new ResourceLocation(MS.ID, "block/cable_part_border"));
        }
    }

    @SubscribeEvent
    public static void onRegisterModelGeometry(final ModelEvent.RegisterGeometryLoaders e) {
        e.register("disk_drive", new DiskDriveGeometryLoader());
        e.register("disk_manipulator", new DiskManipulatorGeometryLoader());
        e.register("portable_grid", new PortableGridGeometryLoader());
    }
}
