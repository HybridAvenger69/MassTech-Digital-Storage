package com.hybridavenger69.mtstorage.recipe;


import com.google.common.collect.Lists;
import com.hybridavenger69.mtstorage.item.CoverItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraft.world.level.Level;

import java.util.List;

public class HollowCoverRecipe extends CustomRecipe {
    public static RecipeSerializer<HollowCoverRecipe> SERIALIZER = new SimpleRecipeSerializer<>(HollowCoverRecipe::new);

    public HollowCoverRecipe(ResourceLocation id) {
        super(id);
    }

    public static boolean stackMatches(ItemStack first) {
        return first.getItem() == com.hybridavenger69.mtstorage.MSItems.COVER.get();
    }

    public static boolean matches(List<ItemStack> list) {
        return list.size() == 1;
    }

    public static ItemStack getResult(List<ItemStack> list) {
        if (list.size() == 1) {
            ItemStack first = list.get(0);
            return getResult(first);
        }
        return ItemStack.EMPTY;
    }

    public static ItemStack getResult(ItemStack first) {
        ItemStack stack = CoverItem.getItem(first);
        ItemStack result = new ItemStack(com.hybridavenger69.mtstorage.MSItems.HOLLOW_COVER.get());
        CoverItem.setItem(result, stack);
        return result;
    }

    @Override
    public boolean matches(CraftingContainer craftingContainer, Level level) {
        List<ItemStack> list = Lists.newArrayList();
        for (int i = 0; i < craftingContainer.getContainerSize(); ++i) {
            ItemStack itemstack = craftingContainer.getItem(i);
            if (!itemstack.isEmpty()) {
                list.add(itemstack);
                if (!stackMatches(itemstack)) {
                    return false;
                }
            }
        }
        return matches(list);
    }

    @Override
    public ItemStack assemble(CraftingContainer inv) {
        List<ItemStack> list = Lists.newArrayList();
        for (int i = 0; i < inv.getContainerSize(); ++i) {
            ItemStack itemstack = inv.getItem(i);
            if (!itemstack.isEmpty()) {
                list.add(itemstack);
                if (!stackMatches(itemstack)) {
                    return ItemStack.EMPTY;
                }
            }
        }
        return getResult(list);
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }
}
