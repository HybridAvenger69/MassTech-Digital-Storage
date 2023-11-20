package com.hybridavenger69.mtstorage.apiimpl.render;

import com.hybridavenger69.mtstorage.api.render.IElementDrawer;
import com.hybridavenger69.mtstorage.api.render.IElementDrawers;
import com.hybridavenger69.mtstorage.render.FluidRenderer;
import com.hybridavenger69.mtstorage.screen.BaseScreen;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class ElementDrawers<T extends AbstractContainerMenu> implements IElementDrawers {
    protected final BaseScreen<T> screen;

    public ElementDrawers(BaseScreen<T> screen) {
        this.screen = screen;
    }

    @Override
    public IElementDrawer<ItemStack> getItemDrawer() {
        return screen::renderItem;
    }

    @Override
    public IElementDrawer<FluidStack> getFluidDrawer() {
        return FluidRenderer.INSTANCE::render;
    }

    @Override
    public IElementDrawer<String> getStringDrawer() {
        return screen::renderString;
    }
}
