package com.hybridavenger69.mtstorage.screen.widget.sidebutton;

import com.mojang.blaze3d.vertex.PoseStack;
import com.hybridavenger69.mtstorage.api.storage.AccessType;
import com.hybridavenger69.mtstorage.screen.BaseScreen;
import com.hybridavenger69.mtstorage.blockentity.data.BlockEntitySynchronizationManager;
import com.hybridavenger69.mtstorage.blockentity.data.BlockEntitySynchronizationParameter;
import com.hybridavenger69.mtstorage.util.AccessTypeUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;

public class AccessTypeSideButton extends SideButton {
    private final BlockEntitySynchronizationParameter<AccessType, ?> parameter;

    public AccessTypeSideButton(BaseScreen<?> screen, BlockEntitySynchronizationParameter<AccessType, ?> parameter) {
        super(screen);

        this.parameter = parameter;
    }

    @Override
    protected void renderButtonIcon(PoseStack poseStack, int x, int y) {
        screen.blit(poseStack, x, y, 16 * parameter.getValue().getId(), 240, 16, 16);
    }

    @Override
    protected String getTooltip() {
        return I18n.get("sidebutton.mtstorage.access_type") + "\n" + ChatFormatting.GRAY + I18n.get("sidebutton.mtstorage.access_type." + parameter.getValue().getId());
    }

    @Override
    public void onPress() {
        BlockEntitySynchronizationManager.setParameter(parameter, AccessTypeUtils.getAccessType(parameter.getValue().getId() + 1));
    }
}
