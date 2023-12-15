package com.hybridavenger69.mtstorage;

import com.hybridavenger69.mtstorage.api.network.NetworkType;
import com.hybridavenger69.mtstorage.api.network.grid.GridType;
import com.hybridavenger69.mtstorage.apiimpl.storage.FluidStorageType;
import com.hybridavenger69.mtstorage.apiimpl.storage.ItemStorageType;
import com.hybridavenger69.mtstorage.blockentity.*;
import com.hybridavenger69.mtstorage.blockentity.craftingmonitor.CraftingMonitorBlockEntity;
import com.hybridavenger69.mtstorage.blockentity.data.BlockEntitySynchronizationManager;
import com.hybridavenger69.mtstorage.blockentity.data.BlockEntitySynchronizationSpec;
import com.hybridavenger69.mtstorage.blockentity.grid.GridBlockEntity;
import com.hybridavenger69.mtstorage.blockentity.grid.portable.PortableGridBlockEntity;
import com.hybridavenger69.mtstorage.item.blockitem.PortableGridBlockItem;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class MSBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MS.ID);

    public static final RegistryObject<BlockEntityType<ControllerBlockEntity>> CONTROLLER =
        REGISTRY.register("controller", () -> registerSynchronizationParameters(ControllerBlockEntity.SPEC, BlockEntityType.Builder.of((pos, state) -> new ControllerBlockEntity(NetworkType.NORMAL, pos, state), MSBlocks.CONTROLLER.getBlocks()).build(null)));
    public static final RegistryObject<BlockEntityType<ControllerBlockEntity>> CREATIVE_CONTROLLER =
        REGISTRY.register("creative_controller", () -> registerSynchronizationParameters(ControllerBlockEntity.SPEC, BlockEntityType.Builder.of((pos, state) -> new ControllerBlockEntity(NetworkType.CREATIVE, pos, state), MSBlocks.CREATIVE_CONTROLLER.getBlocks()).build(null)));
    public static final RegistryObject<BlockEntityType<DetectorBlockEntity>> DETECTOR =
        REGISTRY.register("detector", () -> registerSynchronizationParameters(DetectorBlockEntity.SPEC, BlockEntityType.Builder.of(DetectorBlockEntity::new, MSBlocks.DETECTOR.getBlocks()).build(null)));
    public static final RegistryObject<BlockEntityType<DiskDriveBlockEntity>> DISK_DRIVE =
        REGISTRY.register("disk_drive", () -> registerSynchronizationParameters(DiskDriveBlockEntity.SPEC, BlockEntityType.Builder.of(DiskDriveBlockEntity::new, MSBlocks.DISK_DRIVE.get()).build(null)));
    public static final RegistryObject<BlockEntityType<ExporterBlockEntity>> EXPORTER =
        REGISTRY.register("exporter", () -> registerSynchronizationParameters(ExporterBlockEntity.SPEC, BlockEntityType.Builder.of(ExporterBlockEntity::new, MSBlocks.EXPORTER.get()).build(null)));
    public static final RegistryObject<BlockEntityType<ExternalStorageBlockEntity>> EXTERNAL_STORAGE =
        REGISTRY.register("external_storage", () -> registerSynchronizationParameters(ExternalStorageBlockEntity.SPEC, BlockEntityType.Builder.of(ExternalStorageBlockEntity::new, MSBlocks.EXTERNAL_STORAGE.get()).build(null)));
    public static final RegistryObject<BlockEntityType<GridBlockEntity>> GRID =
        REGISTRY.register("grid", () -> registerSynchronizationParameters(GridBlockEntity.SPEC, BlockEntityType.Builder.of((pos, state) -> new GridBlockEntity(GridType.NORMAL, pos, state), MSBlocks.GRID.getBlocks()).build(null)));
    public static final RegistryObject<BlockEntityType<GridBlockEntity>> CRAFTING_GRID =
        REGISTRY.register("crafting_grid", () -> registerSynchronizationParameters(GridBlockEntity.SPEC, BlockEntityType.Builder.of((pos, state) -> new GridBlockEntity(GridType.CRAFTING, pos, state), MSBlocks.CRAFTING_GRID.getBlocks()).build(null)));
    public static final RegistryObject<BlockEntityType<GridBlockEntity>> PATTERN_GRID =
        REGISTRY.register("pattern_grid", () -> registerSynchronizationParameters(GridBlockEntity.SPEC, BlockEntityType.Builder.of((pos, state) -> new GridBlockEntity(GridType.PATTERN, pos, state), MSBlocks.PATTERN_GRID.getBlocks()).build(null)));
    public static final RegistryObject<BlockEntityType<GridBlockEntity>> FLUID_GRID =
        REGISTRY.register("fluid_grid", () -> registerSynchronizationParameters(GridBlockEntity.SPEC, BlockEntityType.Builder.of((pos, state) -> new GridBlockEntity(GridType.FLUID, pos, state), MSBlocks.FLUID_GRID.getBlocks()).build(null)));
    public static final RegistryObject<BlockEntityType<ImporterBlockEntity>> IMPORTER =
        REGISTRY.register("importer", () -> registerSynchronizationParameters(ImporterBlockEntity.SPEC, BlockEntityType.Builder.of(ImporterBlockEntity::new, MSBlocks.IMPORTER.get()).build(null)));
    public static final RegistryObject<BlockEntityType<NetworkTransmitterBlockEntity>> NETWORK_TRANSMITTER =
        REGISTRY.register("network_transmitter", () -> registerSynchronizationParameters(NetworkTransmitterBlockEntity.SPEC, BlockEntityType.Builder.of(NetworkTransmitterBlockEntity::new, MSBlocks.NETWORK_TRANSMITTER.getBlocks()).build(null)));
    public static final RegistryObject<BlockEntityType<NetworkReceiverBlockEntity>> NETWORK_RECEIVER =
        REGISTRY.register("network_receiver", () -> registerSynchronizationParameters(NetworkReceiverBlockEntity.SPEC, BlockEntityType.Builder.of(NetworkReceiverBlockEntity::new, MSBlocks.NETWORK_RECEIVER.getBlocks()).build(null)));
    public static final RegistryObject<BlockEntityType<RelayBlockEntity>> RELAY =
        REGISTRY.register("relay", () -> registerSynchronizationParameters(RelayBlockEntity.SPEC, BlockEntityType.Builder.of(RelayBlockEntity::new, MSBlocks.RELAY.getBlocks()).build(null)));
    public static final RegistryObject<BlockEntityType<CableBlockEntity>> CABLE =
        REGISTRY.register("cable", () -> registerSynchronizationParameters(CableBlockEntity.SPEC, BlockEntityType.Builder.of(CableBlockEntity::new, MSBlocks.CABLE.get()).build(null)));
    public static final RegistryObject<BlockEntityType<StorageBlockEntity>> ONE_K_STORAGE_BLOCK =
        REGISTRY.register("1k_storage_block", () -> registerSynchronizationParameters(StorageBlockEntity.SPEC, BlockEntityType.Builder.of((pos, state) -> new StorageBlockEntity(ItemStorageType.ONE_K, pos, state), MSBlocks.STORAGE_BLOCKS.get(ItemStorageType.ONE_K).get()).build(null)));
    public static final RegistryObject<BlockEntityType<StorageBlockEntity>> FOUR_K_STORAGE_BLOCK =
        REGISTRY.register("4k_storage_block", () -> registerSynchronizationParameters(StorageBlockEntity.SPEC, BlockEntityType.Builder.of((pos, state) -> new StorageBlockEntity(ItemStorageType.FOUR_K, pos, state), MSBlocks.STORAGE_BLOCKS.get(ItemStorageType.FOUR_K).get()).build(null)));
    public static final RegistryObject<BlockEntityType<StorageBlockEntity>> SIXTEEN_K_STORAGE_BLOCK =
        REGISTRY.register("16k_storage_block", () -> registerSynchronizationParameters(StorageBlockEntity.SPEC, BlockEntityType.Builder.of((pos, state) -> new StorageBlockEntity(ItemStorageType.SIXTEEN_K, pos, state), MSBlocks.STORAGE_BLOCKS.get(ItemStorageType.SIXTEEN_K).get()).build(null)));
    public static final RegistryObject<BlockEntityType<StorageBlockEntity>> SIXTY_FOUR_K_STORAGE_BLOCK =
        REGISTRY.register("64k_storage_block", () -> registerSynchronizationParameters(StorageBlockEntity.SPEC, BlockEntityType.Builder.of((pos, state) -> new StorageBlockEntity(ItemStorageType.SIXTY_FOUR_K, pos, state), MSBlocks.STORAGE_BLOCKS.get(ItemStorageType.SIXTY_FOUR_K).get()).build(null)));
    public static final RegistryObject<BlockEntityType<StorageBlockEntity>> ONE_TWENTY_EIGHT_K_STORAGE_BLOCK =
            REGISTRY.register("128k_storage_block", () -> registerSynchronizationParameters(StorageBlockEntity.SPEC, BlockEntityType.Builder.of((pos, state) -> new StorageBlockEntity(ItemStorageType.ONE_TWENTY_EIGHT_K, pos, state), MSBlocks.STORAGE_BLOCKS.get(ItemStorageType.ONE_TWENTY_EIGHT_K).get()).build(null)));
    public static final RegistryObject<BlockEntityType<StorageBlockEntity>> TWO_FIFTY_SIX_K_STORAGE_BLOCK =
            REGISTRY.register("256k_storage_block", () -> registerSynchronizationParameters(StorageBlockEntity.SPEC, BlockEntityType.Builder.of((pos, state) -> new StorageBlockEntity(ItemStorageType.TWO_FIFTY_SIX_K, pos, state), MSBlocks.STORAGE_BLOCKS.get(ItemStorageType.TWO_FIFTY_SIX_K).get()).build(null)));
    public static final RegistryObject<BlockEntityType<StorageBlockEntity>> FIVE_TWELVE_K_STORAGE_BLOCK =
            REGISTRY.register("512k_storage_block", () -> registerSynchronizationParameters(StorageBlockEntity.SPEC, BlockEntityType.Builder.of((pos, state) -> new StorageBlockEntity(ItemStorageType.FIVE_TWELVE_K, pos, state), MSBlocks.STORAGE_BLOCKS.get(ItemStorageType.FIVE_TWELVE_K).get()).build(null)));
    public static final RegistryObject<BlockEntityType<StorageBlockEntity>> ONE_ZERO_TWENTY_FOUR_K_STORAGE_BLOCK =
            REGISTRY.register("1024k_storage_block", () -> registerSynchronizationParameters(StorageBlockEntity.SPEC, BlockEntityType.Builder.of((pos, state) -> new StorageBlockEntity(ItemStorageType.ONE_ZERO_TWENTY_FOUR_K, pos, state), MSBlocks.STORAGE_BLOCKS.get(ItemStorageType.ONE_ZERO_TWENTY_FOUR_K).get()).build(null)));
    public static final RegistryObject<BlockEntityType<StorageBlockEntity>> TWO_ZERO_FOUR_EIGHT_K_STORAGE_BLOCK =
            REGISTRY.register("2048k_storage_block", () -> registerSynchronizationParameters(StorageBlockEntity.SPEC, BlockEntityType.Builder.of((pos, state) -> new StorageBlockEntity(ItemStorageType.TWO_ZERO_FOUR_EIGHT_K, pos, state), MSBlocks.STORAGE_BLOCKS.get(ItemStorageType.TWO_ZERO_FOUR_EIGHT_K).get()).build(null)));





    public static final RegistryObject<BlockEntityType<StorageBlockEntity>> CREATIVE_STORAGE_BLOCK =
        REGISTRY.register("creative_storage_block", () -> registerSynchronizationParameters(StorageBlockEntity.SPEC, BlockEntityType.Builder.of((pos, state) -> new StorageBlockEntity(ItemStorageType.CREATIVE, pos, state), MSBlocks.STORAGE_BLOCKS.get(ItemStorageType.CREATIVE).get()).build(null)));
    public static final RegistryObject<BlockEntityType<FluidStorageBlockEntity>> SIXTY_FOUR_K_FLUID_STORAGE_BLOCK =
        REGISTRY.register("64k_fluid_storage_block", () -> registerSynchronizationParameters(FluidStorageBlockEntity.SPEC, BlockEntityType.Builder.of((pos, state) -> new FluidStorageBlockEntity(FluidStorageType.SIXTY_FOUR_K, pos, state), MSBlocks.FLUID_STORAGE_BLOCKS.get(FluidStorageType.SIXTY_FOUR_K).get()).build(null)));
    public static final RegistryObject<BlockEntityType<FluidStorageBlockEntity>> TWO_HUNDRED_FIFTY_SIX_K_FLUID_STORAGE_BLOCK =
        REGISTRY.register("256k_fluid_storage_block", () -> registerSynchronizationParameters(FluidStorageBlockEntity.SPEC, BlockEntityType.Builder.of((pos, state) -> new FluidStorageBlockEntity(FluidStorageType.TWO_HUNDRED_FIFTY_SIX_K, pos, state), MSBlocks.FLUID_STORAGE_BLOCKS.get(FluidStorageType.TWO_HUNDRED_FIFTY_SIX_K).get()).build(null)));
    public static final RegistryObject<BlockEntityType<FluidStorageBlockEntity>> THOUSAND_TWENTY_FOUR_K_FLUID_STORAGE_BLOCK =
        REGISTRY.register("1024k_fluid_storage_block", () -> registerSynchronizationParameters(FluidStorageBlockEntity.SPEC, BlockEntityType.Builder.of((pos, state) -> new FluidStorageBlockEntity(FluidStorageType.THOUSAND_TWENTY_FOUR_K, pos, state), MSBlocks.FLUID_STORAGE_BLOCKS.get(FluidStorageType.THOUSAND_TWENTY_FOUR_K).get()).build(null)));
    public static final RegistryObject<BlockEntityType<FluidStorageBlockEntity>> FOUR_THOUSAND_NINETY_SIX_K_FLUID_STORAGE_BLOCK =
        REGISTRY.register("4096k_fluid_storage_block", () -> registerSynchronizationParameters(FluidStorageBlockEntity.SPEC, BlockEntityType.Builder.of((pos, state) -> new FluidStorageBlockEntity(FluidStorageType.FOUR_THOUSAND_NINETY_SIX_K, pos, state), MSBlocks.FLUID_STORAGE_BLOCKS.get(FluidStorageType.FOUR_THOUSAND_NINETY_SIX_K).get()).build(null)));
    public static final RegistryObject<BlockEntityType<FluidStorageBlockEntity>> CREATIVE_FLUID_STORAGE_BLOCK =
        REGISTRY.register("creative_fluid_storage_block", () -> registerSynchronizationParameters(FluidStorageBlockEntity.SPEC, BlockEntityType.Builder.of((pos, state) -> new FluidStorageBlockEntity(FluidStorageType.CREATIVE, pos, state), MSBlocks.FLUID_STORAGE_BLOCKS.get(FluidStorageType.CREATIVE).get()).build(null)));
    public static final RegistryObject<BlockEntityType<SecurityManagerBlockEntity>> SECURITY_MANAGER =
        REGISTRY.register("security_manager", () -> registerSynchronizationParameters(SecurityManagerBlockEntity.SPEC, BlockEntityType.Builder.of(SecurityManagerBlockEntity::new, MSBlocks.SECURITY_MANAGER.getBlocks()).build(null)));
    public static final RegistryObject<BlockEntityType<InterfaceBlockEntity>> INTERFACE =
        REGISTRY.register("interface", () -> registerSynchronizationParameters(InterfaceBlockEntity.SPEC, BlockEntityType.Builder.of(InterfaceBlockEntity::new, MSBlocks.INTERFACE.get()).build(null)));
    public static final RegistryObject<BlockEntityType<FluidInterfaceBlockEntity>> FLUID_INTERFACE =
        REGISTRY.register("fluid_interface", () -> registerSynchronizationParameters(FluidInterfaceBlockEntity.SPEC, BlockEntityType.Builder.of(FluidInterfaceBlockEntity::new, MSBlocks.FLUID_INTERFACE.get()).build(null)));
    public static final RegistryObject<BlockEntityType<WirelessTransmitterBlockEntity>> WIRELESS_TRANSMITTER =
        REGISTRY.register("wireless_transmitter", () -> registerSynchronizationParameters(WirelessTransmitterBlockEntity.SPEC, BlockEntityType.Builder.of(WirelessTransmitterBlockEntity::new, MSBlocks.WIRELESS_TRANSMITTER.getBlocks()).build(null)));
    public static final RegistryObject<BlockEntityType<StorageMonitorBlockEntity>> STORAGE_MONITOR =
        REGISTRY.register("storage_monitor", () -> registerSynchronizationParameters(StorageMonitorBlockEntity.SPEC, BlockEntityType.Builder.of(StorageMonitorBlockEntity::new, MSBlocks.STORAGE_MONITOR.get()).build(null)));
    public static final RegistryObject<BlockEntityType<ConstructorBlockEntity>> CONSTRUCTOR =
        REGISTRY.register("constructor", () -> registerSynchronizationParameters(ConstructorBlockEntity.SPEC, BlockEntityType.Builder.of(ConstructorBlockEntity::new, MSBlocks.CONSTRUCTOR.get()).build(null)));
    public static final RegistryObject<BlockEntityType<DestructorBlockEntity>> DESTRUCTOR =
        REGISTRY.register("destructor", () -> registerSynchronizationParameters(DestructorBlockEntity.SPEC, BlockEntityType.Builder.of(DestructorBlockEntity::new, MSBlocks.DESTRUCTOR.get()).build(null)));
    public static final RegistryObject<BlockEntityType<DiskManipulatorBlockEntity>> DISK_MANIPULATOR =
        REGISTRY.register("disk_manipulator", () -> registerSynchronizationParameters(DiskManipulatorBlockEntity.SPEC, BlockEntityType.Builder.of(DiskManipulatorBlockEntity::new, MSBlocks.DISK_MANIPULATOR.getBlocks()).build(null)));
    public static final RegistryObject<BlockEntityType<PortableGridBlockEntity>> PORTABLE_GRID =
        REGISTRY.register("portable_grid", () -> registerSynchronizationParameters(PortableGridBlockEntity.SPEC, BlockEntityType.Builder.of((pos, state) -> new PortableGridBlockEntity(PortableGridBlockItem.Type.NORMAL, pos, state), MSBlocks.PORTABLE_GRID.get()).build(null)));
    public static final RegistryObject<BlockEntityType<PortableGridBlockEntity>> CREATIVE_PORTABLE_GRID =
        REGISTRY.register("creative_portable_grid", () -> registerSynchronizationParameters(PortableGridBlockEntity.SPEC, BlockEntityType.Builder.of((pos, state) -> new PortableGridBlockEntity(PortableGridBlockItem.Type.CREATIVE, pos, state), MSBlocks.CREATIVE_PORTABLE_GRID.get()).build(null)));
    public static final RegistryObject<BlockEntityType<CrafterBlockEntity>> CRAFTER =
        REGISTRY.register("crafter", () -> registerSynchronizationParameters(CrafterBlockEntity.SPEC, BlockEntityType.Builder.of(CrafterBlockEntity::new, MSBlocks.CRAFTER.getBlocks()).build(null)));
    public static final RegistryObject<BlockEntityType<CrafterManagerBlockEntity>> CRAFTER_MANAGER =
        REGISTRY.register("crafter_manager", () -> registerSynchronizationParameters(CrafterManagerBlockEntity.SPEC, BlockEntityType.Builder.of(CrafterManagerBlockEntity::new, MSBlocks.CRAFTER_MANAGER.getBlocks()).build(null)));
    public static final RegistryObject<BlockEntityType<CraftingMonitorBlockEntity>> CRAFTING_MONITOR =
        REGISTRY.register("crafting_monitor", () -> registerSynchronizationParameters(CraftingMonitorBlockEntity.SPEC, BlockEntityType.Builder.of(CraftingMonitorBlockEntity::new, MSBlocks.CRAFTING_MONITOR.getBlocks()).build(null)));

    private static <T extends BlockEntity> BlockEntityType<T> registerSynchronizationParameters(BlockEntitySynchronizationSpec spec, BlockEntityType<T> t) {
        spec.getParameters().forEach(BlockEntitySynchronizationManager::registerParameter);
        return t;
    }


}
