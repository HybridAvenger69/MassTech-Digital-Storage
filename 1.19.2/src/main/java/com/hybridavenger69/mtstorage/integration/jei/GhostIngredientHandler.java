package com.hybridavenger69.mtstorage.integration.jei;

import com.hybridavenger69.mtstorage.MS;
import com.hybridavenger69.mtstorage.container.slot.filter.FilterSlot;
import com.hybridavenger69.mtstorage.container.slot.filter.FluidFilterSlot;
import com.hybridavenger69.mtstorage.container.slot.legacy.LegacyFilterSlot;
import com.hybridavenger69.mtstorage.network.SetFilterSlotMessage;
import com.hybridavenger69.mtstorage.network.SetFluidFilterSlotMessage;
import com.hybridavenger69.mtstorage.screen.BaseScreen;
import com.hybridavenger69.mtstorage.util.StackUtils;
import mezz.jei.api.gui.handlers.IGhostIngredientHandler;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class GhostIngredientHandler implements IGhostIngredientHandler<BaseScreen> {
    @Override
    public <I> List<Target<I>> getTargets(BaseScreen gui, I ingredient, boolean doStart) {
        List<Target<I>> targets = new ArrayList<>();

        for (Slot slot : gui.getMenu().slots) {
            if (!slot.isActive()) {
                continue;
            }

            Rect2i bounds = new Rect2i(gui.getGuiLeft() + slot.x, gui.getGuiTop() + slot.y, 17, 17);

            if (ingredient instanceof ItemStack && (slot instanceof LegacyFilterSlot || slot instanceof FilterSlot)) {
                targets.add(new Target<I>() {
                    @Override
                    public Rect2i getArea() {
                        return bounds;
                    }

                    @Override
                    public void accept(I ingredient) {
                        slot.set((ItemStack) ingredient);

                        MS.NETWORK_HANDLER.sendToServer(new SetFilterSlotMessage(slot.index, (ItemStack) ingredient));
                    }
                });
            } else if (ingredient instanceof FluidStack && slot instanceof FluidFilterSlot) {
                targets.add(new Target<I>() {
                    @Override
                    public Rect2i getArea() {
                        return bounds;
                    }

                    @Override
                    public void accept(I ingredient) {
                        MS.NETWORK_HANDLER.sendToServer(new SetFluidFilterSlotMessage(slot.index, StackUtils.copy((FluidStack) ingredient, FluidType.BUCKET_VOLUME)));
                    }
                });
            }
        }

        return targets;
    }

    @Override
    public void onComplete() {
        // NO OP
    }
}
