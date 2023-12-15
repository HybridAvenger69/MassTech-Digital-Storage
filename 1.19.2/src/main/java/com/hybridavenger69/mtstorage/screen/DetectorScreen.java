package com.hybridavenger69.mtstorage.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.hybridavenger69.mtstorage.MS;
import com.hybridavenger69.mtstorage.container.DetectorContainerMenu;
import com.hybridavenger69.mtstorage.render.RenderSettings;
import com.hybridavenger69.mtstorage.screen.widget.sidebutton.DetectorModeSideButton;
import com.hybridavenger69.mtstorage.screen.widget.sidebutton.ExactModeSideButton;
import com.hybridavenger69.mtstorage.screen.widget.sidebutton.TypeSideButton;
import com.hybridavenger69.mtstorage.blockentity.DetectorBlockEntity;
import com.hybridavenger69.mtstorage.blockentity.data.BlockEntitySynchronizationManager;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.lwjgl.glfw.GLFW;

public class DetectorScreen extends BaseScreen<DetectorContainerMenu> {
    private EditBox amountField;

    public DetectorScreen(DetectorContainerMenu containerMenu, Inventory inventory, Component title) {
        super(containerMenu, 176, 137, inventory, title);
    }

    @Override
    public void onPostInit(int x, int y) {
        addSideButton(new TypeSideButton(this, DetectorBlockEntity.TYPE));

        addSideButton(new DetectorModeSideButton(this));

        addSideButton(new ExactModeSideButton(this, DetectorBlockEntity.COMPARE));

        amountField = new EditBox(font, x + 41 + 1, y + 23 + 1, 50, font.lineHeight, Component.literal(""));
        amountField.setValue(String.valueOf(DetectorBlockEntity.AMOUNT.getValue()));
        amountField.setBordered(false);
        amountField.setVisible(true);
        amountField.setCanLoseFocus(true);
        amountField.setFocus(false);
        amountField.setTextColor(RenderSettings.INSTANCE.getSecondaryColor());
        amountField.setResponder(value -> {
            try {
                int result = Integer.parseInt(value);

                BlockEntitySynchronizationManager.setParameter(DetectorBlockEntity.AMOUNT, result);
            } catch (NumberFormatException e) {
                // NO OP
            }
        });

        addRenderableWidget(amountField);
    }

    public void updateAmountField(int amount) {
        amountField.setValue(String.valueOf(amount));
    }

    @Override
    public void tick(int x, int y) {
        // NO OP
    }

    @Override
    public void renderBackground(PoseStack poseStack, int x, int y, int mouseX, int mouseY) {
        bindTexture(MS.ID, "gui/detector.png");

        blit(poseStack, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void renderForeground(PoseStack poseStack, int mouseX, int mouseY) {
        renderString(poseStack, 7, 7, title.getString());
        renderString(poseStack, 7, 43, I18n.get("container.inventory"));
    }

    @Override
    public boolean keyPressed(int key, int scanCode, int modifiers) {
        if (key == GLFW.GLFW_KEY_ESCAPE) {
            minecraft.player.closeContainer();

            return true;
        }

        if (amountField.keyPressed(key, scanCode, modifiers) || amountField.canConsumeInput()) {
            return true;
        }

        return super.keyPressed(key, scanCode, modifiers);
    }
}
