package com.hybridavenger69.mtstorage.screen.widget.sidebutton;

import com.mojang.blaze3d.vertex.PoseStack;
import com.hybridavenger69.mtstorage.apiimpl.network.node.DetectorNetworkNode;
import com.hybridavenger69.mtstorage.container.DetectorContainerMenu;
import com.hybridavenger69.mtstorage.screen.BaseScreen;
import com.hybridavenger69.mtstorage.blockentity.DetectorBlockEntity;
import com.hybridavenger69.mtstorage.blockentity.data.BlockEntitySynchronizationManager;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;

public class DetectorModeSideButton extends SideButton {
    public DetectorModeSideButton(BaseScreen<DetectorContainerMenu> screen) {
        super(screen);
    }

    @Override
    protected String getTooltip() {
        return I18n.get("sidebutton.mtstorage.detector.mode") + "\n" + ChatFormatting.GRAY + I18n.get("sidebutton.mtstorage.detector.mode." + DetectorBlockEntity.MODE.getValue());
    }

    @Override
    protected void renderButtonIcon(PoseStack poseStack, int x, int y) {
        screen.blit(poseStack, x, y, DetectorBlockEntity.MODE.getValue() * 16, 176, 16, 16);
    }

    @Override
    public void onPress() {
        int mode = DetectorBlockEntity.MODE.getValue();

        if (mode == DetectorNetworkNode.MODE_EQUAL) {
            mode = DetectorNetworkNode.MODE_ABOVE;
        } else if (mode == DetectorNetworkNode.MODE_ABOVE) {
            mode = DetectorNetworkNode.MODE_UNDER;
        } else if (mode == DetectorNetworkNode.MODE_UNDER) {
            mode = DetectorNetworkNode.MODE_EQUAL;
        }

        BlockEntitySynchronizationManager.setParameter(DetectorBlockEntity.MODE, mode);
    }
}
