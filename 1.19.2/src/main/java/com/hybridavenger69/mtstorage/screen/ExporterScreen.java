package com.hybridavenger69.mtstorage.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.hybridavenger69.mtstorage.MS;
import com.hybridavenger69.mtstorage.container.ExporterContainerMenu;
import com.hybridavenger69.mtstorage.item.UpgradeItem;
import com.hybridavenger69.mtstorage.screen.widget.sidebutton.ExactModeSideButton;
import com.hybridavenger69.mtstorage.screen.widget.sidebutton.RedstoneModeSideButton;
import com.hybridavenger69.mtstorage.screen.widget.sidebutton.TypeSideButton;
import com.hybridavenger69.mtstorage.blockentity.ExporterBlockEntity;
import com.hybridavenger69.mtstorage.blockentity.NetworkNodeBlockEntity;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ExporterScreen extends BaseScreen<ExporterContainerMenu> {
    private boolean hasRegulatorMode;

    public ExporterScreen(ExporterContainerMenu containerMenu, Inventory playerInventory, Component title) {
        super(containerMenu, 211, 137, playerInventory, title);

        this.hasRegulatorMode = hasRegulatorMode();
    }

    private boolean hasRegulatorMode() {
        return ((ExporterBlockEntity) menu.getBlockEntity()).getNode().getUpgrades().hasUpgrade(UpgradeItem.Type.REGULATOR);
    }

    @Override
    public void onPostInit(int x, int y) {
        addSideButton(new RedstoneModeSideButton(this, NetworkNodeBlockEntity.REDSTONE_MODE));

        addSideButton(new TypeSideButton(this, ExporterBlockEntity.TYPE));

        addSideButton(new ExactModeSideButton(this, ExporterBlockEntity.COMPARE));
    }

    @Override
    public void tick(int x, int y) {
        boolean updatedHasRegulatorMode = hasRegulatorMode();
        if (hasRegulatorMode != updatedHasRegulatorMode) {
            hasRegulatorMode = updatedHasRegulatorMode;

            menu.initSlots();
        }
    }

    @Override
    public void renderBackground(PoseStack poseStack, int x, int y, int mouseX, int mouseY) {
        bindTexture(MS.ID, "gui/exporter.png");

        blit(poseStack, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void renderForeground(PoseStack poseStack, int mouseX, int mouseY) {
        renderString(poseStack, 7, 7, title.getString());
        renderString(poseStack, 7, 43, I18n.get("container.inventory"));
    }
}
