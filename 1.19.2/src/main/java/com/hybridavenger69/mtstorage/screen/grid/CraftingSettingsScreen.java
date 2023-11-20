package com.hybridavenger69.mtstorage.screen.grid;

import com.hybridavenger69.mtstorage.MS;
import com.hybridavenger69.mtstorage.container.CraftingSettingsContainerMenu;
import com.hybridavenger69.mtstorage.network.grid.GridCraftingPreviewRequestMessage;
import com.hybridavenger69.mtstorage.screen.AmountSpecifyingScreen;
import com.hybridavenger69.mtstorage.screen.BaseScreen;
import com.hybridavenger69.mtstorage.screen.grid.stack.FluidGridStack;
import com.hybridavenger69.mtstorage.screen.grid.stack.IGridStack;
import net.minecraft.network.chat.Component;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fluids.FluidType;

public class CraftingSettingsScreen extends AmountSpecifyingScreen<CraftingSettingsContainerMenu> {
    private final IGridStack stack;

    public CraftingSettingsScreen(BaseScreen parent, Player player, IGridStack stack) {
        super(parent, new CraftingSettingsContainerMenu(player, stack) {
            @Override
            public void updatePatternSlotPositions(int patternScrollOffset) {

            }
        }, 172, 99, player.getInventory(), Component.translatable("container.crafting"));

        this.stack = stack;
    }

    @Override
    protected Component getOkButtonText() {
        return Component.translatable("misc.mtstorage.start");
    }

    @Override
    protected String getTexture() {
        return "gui/amount_specifying.png";
    }

    @Override
    protected int[] getIncrements() {
        if (stack instanceof FluidGridStack) {
            return new int[]{
                100, 500, 1000,
                -100, -500, -1000
            };
        } else {
            return new int[]{
                1, 10, 64,
                -1, -10, -64
            };
        }
    }

    @Override
    protected int getDefaultAmount() {
        return stack instanceof FluidGridStack ? FluidType.BUCKET_VOLUME : 1;
    }

    @Override
    protected boolean canAmountGoNegative() {
        return false;
    }

    @Override
    protected int getMaxAmount() {
        return Integer.MAX_VALUE;
    }

    @Override
    protected void onOkButtonPressed(boolean shiftDown) {
        try {
            int quantity = Integer.parseInt(amountField.getValue());

            MS.NETWORK_HANDLER.sendToServer(new GridCraftingPreviewRequestMessage(stack.getId(), quantity, shiftDown, stack instanceof FluidGridStack));

            okButton.active = false;
        } catch (NumberFormatException e) {
            // NO OP
        }
    }
}
