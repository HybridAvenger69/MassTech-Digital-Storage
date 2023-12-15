package com.hybridavenger69.mtstorage.integration.jei;

import com.hybridavenger69.mtstorage.MS;
import com.hybridavenger69.mtstorage.recipe.CoverRecipe;
import com.hybridavenger69.mtstorage.recipe.HollowCoverRecipe;
import com.hybridavenger69.mtstorage.screen.BaseScreen;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import mezz.jei.api.registration.IVanillaCategoryExtensionRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class RSJeiPlugin implements IModPlugin {
    private static final ResourceLocation ID = new ResourceLocation(MS.ID, "plugin");

    private static IJeiRuntime runtime;

    public static IJeiRuntime getRuntime() {
        return runtime;
    }

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addUniversalRecipeTransferHandler(GridRecipeTransferHandler.INSTANCE);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addGenericGuiContainerHandler(BaseScreen.class, new GuiContainerHandler());
        registration.addGhostIngredientHandler(BaseScreen.class, new GhostIngredientHandler());
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime runtime) {
        RSJeiPlugin.runtime = runtime;
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.useNbtForSubtypes(com.hybridavenger69.mtstorage.MSItems.COVER.get(), com.hybridavenger69.mtstorage.MSItems.HOLLOW_COVER.get());
    }

    @Override
    public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration registration) {
        registration.getCraftingCategory().addCategoryExtension(CoverRecipe.class, (cover) -> new CoverCraftingCategoryExtension());
        registration.getCraftingCategory().addCategoryExtension(HollowCoverRecipe.class, (cover) -> new HollowCoverCraftingCategoryExtension());
    }
}
