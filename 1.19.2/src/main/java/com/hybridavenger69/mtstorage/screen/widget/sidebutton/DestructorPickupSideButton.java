package com.hybridavenger69.mtstorage.screen.widget.sidebutton;

import com.mojang.blaze3d.vertex.PoseStack;
import com.hybridavenger69.mtstorage.container.DestructorContainerMenu;
import com.hybridavenger69.mtstorage.screen.BaseScreen;
import com.hybridavenger69.mtstorage.blockentity.DestructorBlockEntity;
import com.hybridavenger69.mtstorage.blockentity.data.BlockEntitySynchronizationManager;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;

public class DestructorPickupSideButton extends SideButton {
    public DestructorPickupSideButton(BaseScreen<DestructorContainerMenu> screen) {
        super(screen);
    }

    @Override
    protected void renderButtonIcon(PoseStack poseStack, int x, int y) {
        screen.blit(poseStack, x, y, 64 + (Boolean.TRUE.equals(DestructorBlockEntity.PICKUP.getValue()) ? 0 : 16), 0, 16, 16);
    }

    @Override
    protected String getTooltip() {
        return I18n.get("sidebutton.mtstorage.destructor.pickup") + "\n" + ChatFormatting.GRAY + I18n.get(Boolean.TRUE.equals(DestructorBlockEntity.PICKUP.getValue()) ? "gui.yes" : "gui.no");
    }

    @Override
    public void onPress() {
        BlockEntitySynchronizationManager.setParameter(DestructorBlockEntity.PICKUP, !DestructorBlockEntity.PICKUP.getValue());
    }
}
