package com.hybridavenger69.mtstorage.screen.widget.sidebutton;

import com.mojang.blaze3d.vertex.PoseStack;
import com.hybridavenger69.mtstorage.screen.FilterScreen;
import com.hybridavenger69.mtstorage.blockentity.config.IType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;

public class FilterTypeSideButton extends SideButton {
    private final FilterScreen filterScreen;

    public FilterTypeSideButton(FilterScreen filterScreen) {
        super(filterScreen);

        this.filterScreen = filterScreen;
    }

    @Override
    protected String getTooltip() {
        return I18n.get("sidebutton.mtstorage.type") + "\n" + ChatFormatting.GRAY + I18n.get("sidebutton.mtstorage.type." + filterScreen.getType());
    }

    @Override
    protected void renderButtonIcon(PoseStack poseStack, int x, int y) {
        filterScreen.blit(poseStack, x, y, 16 * filterScreen.getType(), 128, 16, 16);
    }

    @Override
    public void onPress() {
        filterScreen.setType(filterScreen.getType() == IType.ITEMS ? IType.FLUIDS : IType.ITEMS);
        filterScreen.sendUpdate();
    }
}
