package com.hybridavenger69.mtstorage;

import com.hybridavenger69.mtstorage.apiimpl.API;
import com.hybridavenger69.mtstorage.config.ClientConfig;
import com.hybridavenger69.mtstorage.config.ServerConfig;
import com.hybridavenger69.mtstorage.datageneration.DataGenerators;
import com.hybridavenger69.mtstorage.integration.curios.CuriosIntegration;
import com.hybridavenger69.mtstorage.item.group.MainCreativeModeTab;
import com.hybridavenger69.mtstorage.network.NetworkHandler;
import com.hybridavenger69.mtstorage.setup.ClientSetup;
import com.hybridavenger69.mtstorage.setup.CommonSetup;
import com.hybridavenger69.mtstorage.setup.ServerSetup;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MS.ID)
public final class MS {
    public static final String ID = "mtstorage";
    public static final String NAME = "MassTech Digital Storage";

    public static final NetworkHandler NETWORK_HANDLER = new NetworkHandler();
    public static final CreativeModeTab CREATIVE_MODE_TAB = new MainCreativeModeTab();
    public static final ServerConfig SERVER_CONFIG = new ServerConfig();
    public static final ClientConfig CLIENT_CONFIG = new ClientConfig();

    public MS() {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::onClientSetup);
            FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::onModelBake);
            FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::onRegisterAdditionalModels);
            FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::onTextureStitch);
            FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::onRegisterModelGeometry);
            FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::onRegisterKeymappings);
            FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::onRegisterColorBindings);
            MinecraftForge.EVENT_BUS.addListener(ClientSetup::addReloadListener);
        });

        MinecraftForge.EVENT_BUS.register(new ServerSetup());

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER_CONFIG.getSpec());
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_CONFIG.getSpec());

        MSBlocks.register();
        MSItems.register();
        MSLootFunctions.register();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(CommonSetup::onCommonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(CommonSetup::onRegisterCapabilities);
        FMLJavaModLoadingContext.get().getModEventBus().register(new DataGenerators());
        FMLJavaModLoadingContext.get().getModEventBus().register(new CuriosIntegration());

        MSContainerMenus.REGISTRY.register(FMLJavaModLoadingContext.get().getModEventBus());
        MSBlockEntities.REGISTRY.register(FMLJavaModLoadingContext.get().getModEventBus());
        MSRecipeSerializers.REGISTRY.register(FMLJavaModLoadingContext.get().getModEventBus());

        API.deliver();
    }
}
