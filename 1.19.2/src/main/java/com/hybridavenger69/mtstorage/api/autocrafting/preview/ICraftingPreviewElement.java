package com.hybridavenger69.mtstorage.api.autocrafting.preview;

import com.mojang.blaze3d.vertex.PoseStack;
import com.hybridavenger69.mtstorage.api.render.IElementDrawers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Represents a crafting preview element.
 */
public interface ICraftingPreviewElement {
    /**
     * @param poseStack the pose stack
     * @param x         position on the x axis to render
     * @param y         position on the y axis to render
     * @param drawers   the drawers this element can use
     */
    @OnlyIn(Dist.CLIENT)
    void draw(PoseStack poseStack, int x, int y, IElementDrawers drawers);

    /**
     * @return true when this crafting preview elements signifies an error that disables starting a task
     */
    boolean doesDisableTaskStarting();

    /**
     * @param buf buffer to write to
     */
    void write(FriendlyByteBuf buf);

    /**
     * Returns the id of this element, used for serialization and deserialization over the network.
     *
     * @return the id
     */
    ResourceLocation getId();
}
