package com.hybridavenger69.mtstorage.api.autocrafting;

import com.hybridavenger69.mtstorage.api.IRSAPI;
import net.minecraft.world.item.ItemStack;

/**
 * Defines the behavior of pattern rendering.
 * One can add this interface through {@link IRSAPI#addPatternRenderHandler(ICraftingPatternRenderHandler)}.
 */
public interface ICraftingPatternRenderHandler {
    /**
     * Returns true if the pattern can render its output.
     * As soon as one {@link ICraftingPatternRenderHandler} returns true for this method, it will render the output.
     *
     * @param pattern the pattern
     * @return true if this pattern can render its output, false otherwise
     */
    boolean canRenderOutput(ItemStack pattern);
}
