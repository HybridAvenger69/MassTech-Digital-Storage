package com.hybridavenger69.mtstorage.screen.widget.sidebutton;

import com.mojang.blaze3d.vertex.PoseStack;
import com.hybridavenger69.mtstorage.apiimpl.network.node.diskmanipulator.DiskManipulatorNetworkNode;
import com.hybridavenger69.mtstorage.container.DiskManipulatorContainerMenu;
import com.hybridavenger69.mtstorage.screen.BaseScreen;
import com.hybridavenger69.mtstorage.blockentity.DiskManipulatorBlockEntity;
import com.hybridavenger69.mtstorage.blockentity.data.BlockEntitySynchronizationManager;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;

public class IoModeSideButton extends SideButton {
    public IoModeSideButton(BaseScreen<DiskManipulatorContainerMenu> screen) {
        super(screen);
    }

    @Override
    protected String getTooltip() {
        return I18n.get("sidebutton.mtstorage.iomode") + "\n" + ChatFormatting.GRAY + I18n.get("sidebutton.mtstorage.iomode." + (DiskManipulatorBlockEntity.IO_MODE.getValue() == DiskManipulatorNetworkNode.IO_MODE_INSERT ? "insert" : "extract"));
    }

    @Override
    protected void renderButtonIcon(PoseStack poseStack, int x, int y) {
        screen.blit(poseStack, x, y, DiskManipulatorBlockEntity.IO_MODE.getValue() == DiskManipulatorNetworkNode.IO_MODE_EXTRACT ? 0 : 16, 160, 16, 16);
    }

    @Override
    public void onPress() {
        BlockEntitySynchronizationManager.setParameter(DiskManipulatorBlockEntity.IO_MODE, DiskManipulatorBlockEntity.IO_MODE.getValue() == DiskManipulatorNetworkNode.IO_MODE_INSERT ? DiskManipulatorNetworkNode.IO_MODE_EXTRACT : DiskManipulatorNetworkNode.IO_MODE_INSERT);
    }
}
