package com.hybridavenger69.mtstorage;

import com.hybridavenger69.mtstorage.loottable.ControllerLootFunction;
import com.hybridavenger69.mtstorage.loottable.CrafterLootFunction;
import com.hybridavenger69.mtstorage.loottable.PortableGridBlockLootFunction;
import com.hybridavenger69.mtstorage.loottable.StorageBlockLootFunction;
import net.minecraft.core.Registry;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class MSLootFunctions {
    public static final RegistryObject<LootItemFunctionType> STORAGE_BLOCK;
    public static final RegistryObject<LootItemFunctionType> PORTABLE_GRID;
    public static final RegistryObject<LootItemFunctionType> CRAFTER;
    public static final RegistryObject<LootItemFunctionType> CONTROLLER;

    private static final DeferredRegister<LootItemFunctionType> LOOT_ITEM_FUNCTIONS = DeferredRegister.create(Registry.LOOT_FUNCTION_REGISTRY, MS.ID);

    static {
        STORAGE_BLOCK = LOOT_ITEM_FUNCTIONS.register("storage_block", () -> new LootItemFunctionType(new StorageBlockLootFunction.Serializer()));
        PORTABLE_GRID = LOOT_ITEM_FUNCTIONS.register("portable_grid", () -> new LootItemFunctionType(new PortableGridBlockLootFunction.Serializer()));
        CRAFTER = LOOT_ITEM_FUNCTIONS.register("crafter", () -> new LootItemFunctionType(new CrafterLootFunction.Serializer()));
        CONTROLLER = LOOT_ITEM_FUNCTIONS.register("controller", () -> new LootItemFunctionType(new ControllerLootFunction.Serializer()));
    }

    private MSLootFunctions() {
    }

    public static void register() {
        LOOT_ITEM_FUNCTIONS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
