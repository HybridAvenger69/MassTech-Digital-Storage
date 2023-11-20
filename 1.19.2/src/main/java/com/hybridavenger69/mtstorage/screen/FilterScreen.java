package com.hybridavenger69.mtstorage.screen;

import com.hybridavenger69.hybridlib.HybridIDS;
import com.mojang.blaze3d.vertex.PoseStack;
import com.hybridavenger69.mtstorage.MS;
import com.hybridavenger69.mtstorage.api.util.IComparer;
import com.hybridavenger69.mtstorage.api.util.IFilter;
import com.hybridavenger69.mtstorage.container.FilterContainerMenu;
import com.hybridavenger69.mtstorage.item.FilterItem;
import com.hybridavenger69.mtstorage.network.FilterUpdateMessage;
import com.hybridavenger69.mtstorage.render.RenderSettings;
import com.hybridavenger69.mtstorage.screen.widget.CheckboxWidget;
import com.hybridavenger69.mtstorage.screen.widget.sidebutton.FilterTypeSideButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.lwjgl.glfw.GLFW;

public class FilterScreen extends BaseScreen<FilterContainerMenu> {
    private final ItemStack stack;
    private final String name;
    private int compare;
    private int mode;
    private boolean modFilter;
    private int type;

    private CheckboxWidget modFilterCheckBox;
    private Button modeButton;
    private EditBox nameField;

    public FilterScreen(FilterContainerMenu containerMenu, Inventory inventory, Component title) {
        super(containerMenu, 176, 231, inventory, title);

        this.stack = containerMenu.getFilterItem();

        this.compare = FilterItem.getCompare(containerMenu.getFilterItem());
        this.mode = FilterItem.getMode(containerMenu.getFilterItem());
        this.modFilter = FilterItem.isModFilter(containerMenu.getFilterItem());
        this.name = FilterItem.getFilterName(containerMenu.getFilterItem());
        this.type = FilterItem.getType(containerMenu.getFilterItem());
    }

    @Override
    public void onPostInit(int x, int y) {
        addCheckBox(x + 7, y + 77, Component.translatable("gui.mtstorage.filter.compare_nbt"), (compare & IComparer.COMPARE_NBT) == IComparer.COMPARE_NBT, btn -> {
            compare ^= IComparer.COMPARE_NBT;

            sendUpdate();
        });

        modFilterCheckBox = addCheckBox(0, y + 71 + 25, Component.translatable("gui.mtstorage.filter.mod_filter"), modFilter, btn -> {
            modFilter = !modFilter;

            sendUpdate();
        });

        modeButton = addButton(x + 7, y + 71 + 21, 0, 20, Component.literal(""), true, true, btn -> {
            mode = mode == IFilter.MODE_WHITELIST ? IFilter.MODE_BLACKLIST : IFilter.MODE_WHITELIST;

            updateModeButton(mode);

            sendUpdate();
        });

        updateModeButton(mode);

        nameField = new EditBox(font, x + 34, y + 121, 137 - 6, font.lineHeight, Component.literal(""));
        nameField.setValue(name);
        nameField.setBordered(false);
        nameField.setVisible(true);
        nameField.setCanLoseFocus(true);
        nameField.setFocus(false);
        nameField.setTextColor(RenderSettings.INSTANCE.getSecondaryColor());
        nameField.setResponder(content -> sendUpdate());

        addRenderableWidget(nameField);

        addSideButton(new FilterTypeSideButton(this));
    }

    private void updateModeButton(int mode) {
        Component text = mode == IFilter.MODE_WHITELIST
            ? Component.translatable("sidebutton.mtstorage.mode.whitelist")
            : Component.translatable("sidebutton.mtstorage.mode.blacklist");

        modeButton.setWidth(font.width(text.getString()) + 12);
        modeButton.setMessage(text);
        modFilterCheckBox.x = modeButton.x + modeButton.getWidth() + 4;
    }

    @Override
    public boolean keyPressed(int key, int scanCode, int modifiers) {
        if (key == GLFW.GLFW_KEY_ESCAPE) {
            minecraft.player.closeContainer();

            return true;
        }

        if (nameField.keyPressed(key, scanCode, modifiers) || nameField.canConsumeInput()) {
            return true;
        }

        return super.keyPressed(key, scanCode, modifiers);
    }

    @Override
    public void tick(int x, int y) {
        // NO OP
    }

    @Override
    public void renderBackground(PoseStack poseStack, int x, int y, int mouseX, int mouseY) {
        bindTexture(HybridIDS.MTStorage_MODID, "gui/filter.png");

        blit(poseStack, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void renderForeground(PoseStack poseStack, int mouseX, int mouseY) {
        renderString(poseStack, 7, 7, title.getString());
        renderString(poseStack, 7, 137, I18n.get("container.inventory"));
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;

        FilterItem.setType(stack, type);
    }

    public void sendUpdate() {
        MS.NETWORK_HANDLER.sendToServer(new FilterUpdateMessage(compare, mode, modFilter, nameField.getValue(), type));
    }
}
