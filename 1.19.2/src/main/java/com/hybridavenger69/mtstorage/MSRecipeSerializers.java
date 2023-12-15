package com.hybridavenger69.mtstorage;

import com.hybridavenger69.mtstorage.recipe.CoverRecipe;
import com.hybridavenger69.mtstorage.recipe.HollowCoverRecipe;
import com.hybridavenger69.mtstorage.recipe.UpgradeWithEnchantedBookRecipeSerializer;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;

public final class MSRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> REGISTRY = DeferredRegister.create(Registry.RECIPE_SERIALIZER_REGISTRY, MS.ID);

    static {
        REGISTRY.register("upgrade_with_enchanted_book", UpgradeWithEnchantedBookRecipeSerializer::new);
        REGISTRY.register("cover_recipe", () -> CoverRecipe.SERIALIZER);
        REGISTRY.register("hollow_cover_recipe", () -> HollowCoverRecipe.SERIALIZER);
    }

    private MSRecipeSerializers() {
    }
}
