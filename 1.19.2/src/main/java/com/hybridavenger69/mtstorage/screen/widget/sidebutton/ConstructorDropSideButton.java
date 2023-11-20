package com.hybridavenger69.mtstorage.screen.widget.sidebutton;

import com.mojang.blaze3d.vertex.PoseStack;
import com.hybridavenger69.mtstorage.container.ConstructorContainerMenu;
import com.hybridavenger69.mtstorage.screen.BaseScreen;
import com.hybridavenger69.mtstorage.blockentity.ConstructorBlockEntity;
import com.hybridavenger69.mtstorage.blockentity.data.BlockEntitySynchronizationManager;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;

public class ConstructorDropSideButton extends SideButton {
    public ConstructorDropSideButton(BaseScreen<ConstructorContainerMenu> screen) {
        super(screen);
    }

    @Override
    protected void renderButtonIcon(PoseStack poseStack, int x, int y) {
        screen.blit(poseStack, x, y, 64 + (Boolean.TRUE.equals(ConstructorBlockEntity.DROP.getValue()) ? 16 : 0), 16, 16, 16);
    }

    @Override
    protected String getTooltip() {
        return I18n.get("sidebutton.mtstorage.constructor.drop") + "\n" + ChatFormatting.GRAY + I18n.get(Boolean.TRUE.equals(ConstructorBlockEntity.DROP.getValue()) ? "gui.yes" : "gui.no");
    }

    @Override
    public void onPress() {
        BlockEntitySynchronizationManager.setParameter(ConstructorBlockEntity.DROP, !ConstructorBlockEntity.DROP.getValue());
    }
}
