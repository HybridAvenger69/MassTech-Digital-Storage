package com.hybridavenger69.mtstorage.screen.widget.sidebutton;

import com.mojang.blaze3d.vertex.PoseStack;
import com.hybridavenger69.mtstorage.container.CrafterContainerMenu;
import com.hybridavenger69.mtstorage.screen.BaseScreen;
import com.hybridavenger69.mtstorage.blockentity.CrafterBlockEntity;
import com.hybridavenger69.mtstorage.blockentity.data.BlockEntitySynchronizationManager;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;

public class CrafterModeSideButton extends SideButton {
    public CrafterModeSideButton(BaseScreen<CrafterContainerMenu> screen) {
        super(screen);
    }

    @Override
    protected String getTooltip() {
        return I18n.get("sidebutton.mtstorage.crafter_mode") + "\n" + ChatFormatting.GRAY + I18n.get("sidebutton.mtstorage.crafter_mode." + CrafterBlockEntity.MODE.getValue());
    }

    @Override
    protected void renderButtonIcon(PoseStack poseStack, int x, int y) {
        screen.blit(poseStack, x, y, CrafterBlockEntity.MODE.getValue() * 16, 0, 16, 16);
    }

    @Override
    public void onPress() {
        BlockEntitySynchronizationManager.setParameter(CrafterBlockEntity.MODE, CrafterBlockEntity.MODE.getValue() + 1);
    }
}
