package com.hybridavenger69.mtstorage.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.hybridavenger69.mtstorage.MS;
import com.hybridavenger69.mtstorage.apiimpl.API;
import com.hybridavenger69.mtstorage.apiimpl.network.node.FluidInterfaceNetworkNode;
import com.hybridavenger69.mtstorage.container.FluidInterfaceContainerMenu;
import com.hybridavenger69.mtstorage.render.FluidRenderer;
import com.hybridavenger69.mtstorage.screen.widget.sidebutton.RedstoneModeSideButton;
import com.hybridavenger69.mtstorage.blockentity.FluidInterfaceBlockEntity;
import com.hybridavenger69.mtstorage.blockentity.NetworkNodeBlockEntity;
import com.hybridavenger69.mtstorage.util.RenderUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class FluidInterfaceScreen extends BaseScreen<FluidInterfaceContainerMenu> {
    private static final FluidRenderer TANK_RENDERER = new FluidRenderer(FluidInterfaceNetworkNode.TANK_CAPACITY, 12, 47, 1);

    public FluidInterfaceScreen(FluidInterfaceContainerMenu containerMenu, Inventory inventory, Component title) {
        super(containerMenu, 211, 204, inventory, title);
    }

    @Override
    public void onPostInit(int x, int y) {
        addSideButton(new RedstoneModeSideButton(this, NetworkNodeBlockEntity.REDSTONE_MODE));
    }

    @Override
    public void tick(int x, int y) {
        // NO OP
    }

    @Override
    public void renderBackground(PoseStack poseStack, int x, int y, int mouseX, int mouseY) {
        bindTexture(MS.ID, "gui/fluid_interface.png");

        blit(poseStack, x, y, 0, 0, imageWidth, imageHeight);

        if (!FluidInterfaceBlockEntity.TANK_IN.getValue().isEmpty()) {
            TANK_RENDERER.render(poseStack, x + 46, y + 56, FluidInterfaceBlockEntity.TANK_IN.getValue());
        }

        if (!FluidInterfaceBlockEntity.TANK_OUT.getValue().isEmpty()) {
            TANK_RENDERER.render(poseStack, x + 118, y + 56, FluidInterfaceBlockEntity.TANK_OUT.getValue());
        }
    }

    @Override
    public void renderForeground(PoseStack poseStack, int mouseX, int mouseY) {
        renderString(poseStack, 7, 7, title.getString());
        renderString(poseStack, 43 + 4, 20, I18n.get("gui.mtstorage.fluid_interface.in"));
        renderString(poseStack, 115 + 1, 20, I18n.get("gui.mtstorage.fluid_interface.out"));
        renderString(poseStack, 7, 111, I18n.get("container.inventory"));

        if (RenderUtils.inBounds(46, 56, 12, 47, mouseX, mouseY) && !FluidInterfaceBlockEntity.TANK_IN.getValue().isEmpty()) {
            renderTooltip(poseStack, mouseX, mouseY, FluidInterfaceBlockEntity.TANK_IN.getValue().getDisplayName().getString() + "\n" + ChatFormatting.GRAY + API.instance().getQuantityFormatter().formatInBucketForm(FluidInterfaceBlockEntity.TANK_IN.getValue().getAmount()) + ChatFormatting.RESET);
        }

        if (RenderUtils.inBounds(118, 56, 12, 47, mouseX, mouseY) && !FluidInterfaceBlockEntity.TANK_OUT.getValue().isEmpty()) {
            renderTooltip(poseStack, mouseX, mouseY, FluidInterfaceBlockEntity.TANK_OUT.getValue().getDisplayName().getString() + "\n" + ChatFormatting.GRAY + API.instance().getQuantityFormatter().formatInBucketForm(FluidInterfaceBlockEntity.TANK_OUT.getValue().getAmount()) + ChatFormatting.RESET);
        }
    }
}
