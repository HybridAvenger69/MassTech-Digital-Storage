package com.hybridavenger69.mtstorage.screen.grid.stack;

import com.mojang.blaze3d.vertex.PoseStack;
import com.hybridavenger69.mtstorage.api.storage.tracker.StorageTrackerEntry;
import com.hybridavenger69.mtstorage.screen.BaseScreen;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface IGridStack {
    UUID getId();

    @Nullable
    UUID getOtherId();

    void updateOtherId(@Nullable UUID otherId);

    String getName();

    String getModId();

    String getModName();

    Set<String> getTags();

    List<Component> getTooltip(boolean bypassCache);

    int getQuantity();

    void setQuantity(int amount);

    String getFormattedFullQuantity();

    void draw(PoseStack poseStack, BaseScreen<?> screen, int x, int y);

    Object getIngredient();

    @Nullable
    StorageTrackerEntry getTrackerEntry();

    void setTrackerEntry(@Nullable StorageTrackerEntry entry);

    boolean isCraftable();
}
