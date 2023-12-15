package com.hybridavenger69.mtstorage.datageneration;

import com.hybridavenger69.mtstorage.MS;
import com.hybridavenger69.mtstorage.item.ProcessorItem;
import com.hybridavenger69.mtstorage.util.ColorMap;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public class RecipeGenerator extends RecipeProvider {
    private static final String GRID_ID = MS.ID + ":grid";

    public RecipeGenerator(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> recipeAcceptor) {
        // Tag + Color -> Colored Block
        com.hybridavenger69.mtstorage.MSItems.COLORED_ITEM_TAGS.forEach((tag, map) -> map.forEach((color, item) -> ShapelessRecipeBuilder.shapeless(item.get())
            .requires(tag)
            .requires(color.getTag())
            .group(MS.ID)
            .unlockedBy("mtstorage:controller", InventoryChangeTrigger.TriggerInstance.hasItems(com.hybridavenger69.mtstorage.MSItems.CONTROLLER.get(ColorMap.DEFAULT_COLOR).get()))
            .save(recipeAcceptor, new ResourceLocation(MS.ID, "coloring_recipes/" + item.getId().getPath()))
        ));

        // Crafting Grid
        com.hybridavenger69.mtstorage.MSItems.CRAFTING_GRID.forEach((color, item) -> ShapelessRecipeBuilder.shapeless(item.get())
            .requires(com.hybridavenger69.mtstorage.MSItems.GRID.get(color).get())
            .requires(com.hybridavenger69.mtstorage.MSItems.PROCESSORS.get(ProcessorItem.Type.ADVANCED).get())
            .requires(ItemTags.create(new ResourceLocation(MS.ID, "crafting_tables")))
            .unlockedBy(GRID_ID, InventoryChangeTrigger.TriggerInstance.hasItems(com.hybridavenger69.mtstorage.MSItems.GRID.get(ColorMap.DEFAULT_COLOR).get()))
            .save(recipeAcceptor, new ResourceLocation(MS.ID, "crafting_grid/" + item.getId().getPath()))
        );

        // Fluid Grid
        com.hybridavenger69.mtstorage.MSItems.FLUID_GRID.forEach((color, item) -> ShapelessRecipeBuilder.shapeless(item.get())
            .requires(com.hybridavenger69.mtstorage.MSItems.GRID.get(color).get())
            .requires(com.hybridavenger69.mtstorage.MSItems.PROCESSORS.get(ProcessorItem.Type.ADVANCED).get())
            .requires(Items.BUCKET)
            .unlockedBy(GRID_ID, InventoryChangeTrigger.TriggerInstance.hasItems(com.hybridavenger69.mtstorage.MSItems.GRID.get(ColorMap.DEFAULT_COLOR).get()))
            .save(recipeAcceptor, new ResourceLocation(MS.ID, "fluid_grid/" + item.getId().getPath()))
        );

        // Pattern Grid
        com.hybridavenger69.mtstorage.MSItems.PATTERN_GRID.forEach((color, item) -> ShapelessRecipeBuilder.shapeless(item.get())
            .requires(com.hybridavenger69.mtstorage.MSItems.GRID.get(color).get())
            .requires(com.hybridavenger69.mtstorage.MSItems.PROCESSORS.get(ProcessorItem.Type.ADVANCED).get())
            .requires(com.hybridavenger69.mtstorage.MSItems.PATTERN.get())
            .unlockedBy(GRID_ID, InventoryChangeTrigger.TriggerInstance.hasItems(com.hybridavenger69.mtstorage.MSItems.GRID.get(ColorMap.DEFAULT_COLOR).get()))
            .save(recipeAcceptor, new ResourceLocation(MS.ID, "pattern_grid/" + item.getId().getPath()))
        );
    }
}
