package com.hybridavenger69.mtstorage.integration.jei;

import com.hybridavenger69.mtstorage.MSItems;
import com.hybridavenger69.mtstorage.apiimpl.network.node.cover.CoverManager;
import com.hybridavenger69.mtstorage.item.CoverItem;
import com.hybridavenger69.mtstorage.recipe.CoverRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.ingredient.ICraftingGridHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICraftingCategoryExtension;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CoverCraftingCategoryExtension implements ICraftingCategoryExtension {


    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ICraftingGridHelper craftingGridHelper, IFocusGroup focuses) {
        List<ItemStack> input = new ArrayList<>();
        List<ItemStack> output = new ArrayList<>();
        for (Block block : ForgeRegistries.BLOCKS.getValues()) {
            Item item = Item.BY_BLOCK.get(block);
            if (item == null || item == Items.AIR) {
                continue;
            }
            NonNullList<ItemStack> subBlocks = NonNullList.create();
            block.fillItemCategory(CreativeModeTab.TAB_SEARCH, subBlocks);
            for (ItemStack subBlock : subBlocks) {
                if (CoverManager.isValidCover(subBlock)) {
                    input.add(subBlock);
                    ItemStack stack = new ItemStack(MSItems.COVER.get());
                    CoverItem.setItem(stack, subBlock);
                    output.add(stack);
                }
            }
        }

        ITag<Item> nuggetTag = ForgeRegistries.ITEMS.tags().getTag(Tags.Items.NUGGETS_IRON);
        List<ItemStack> nuggets = nuggetTag.stream().map(ItemStack::new).toList();
        List<List<ItemStack>> inputs = new ArrayList<>(Collections.nCopies(9, new ArrayList<>()));
        inputs.set(3, nuggets);
        inputs.set(4, input);
        List<IRecipeSlotBuilder> inputSlots = craftingGridHelper.createAndSetInputs(builder, inputs, 3, 3);
        IRecipeSlotBuilder outputSlot = craftingGridHelper.createAndSetOutputs(builder, output);

        builder.createFocusLink(inputSlots.get(4), outputSlot);
    }

    @Override
    public int getWidth() {
        return 3;
    }

    @Override
    public int getHeight() {
        return 3;
    }

    @Nullable
    @Override
    public ResourceLocation getRegistryName() {
        return ForgeRegistries.RECIPE_SERIALIZERS.getKey(CoverRecipe.SERIALIZER);
    }
}
