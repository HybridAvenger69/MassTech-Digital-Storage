package com.hybridavenger69.mtstorage.container;

import com.hybridavenger69.mtstorage.MSContainerMenus;
import com.hybridavenger69.mtstorage.container.slot.filter.FilterSlot;
import com.hybridavenger69.mtstorage.container.slot.filter.FluidFilterSlot;
import com.hybridavenger69.mtstorage.item.UpgradeItem;
import com.hybridavenger69.mtstorage.blockentity.ExporterBlockEntity;
import com.hybridavenger69.mtstorage.blockentity.config.IType;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.items.SlotItemHandler;

public abstract class ExporterContainerMenu extends BaseContainerMenu {
    private final ExporterBlockEntity exporter;
    private boolean hasRegulatorMode;

    public ExporterContainerMenu(ExporterBlockEntity exporter, Player player, int windowId) {
        super(MSContainerMenus.EXPORTER.get(), exporter, player, windowId);

        this.exporter = exporter;
        this.hasRegulatorMode = hasRegulatorMode();

        initSlots();
    }

    private boolean hasRegulatorMode() {
        return exporter.getNode().getUpgrades().hasUpgrade(UpgradeItem.Type.REGULATOR);
    }

    @Override
    public void broadcastChanges() {
        super.broadcastChanges();

        boolean updatedHasRegulatorMode = hasRegulatorMode();
        if (hasRegulatorMode != updatedHasRegulatorMode) {
            hasRegulatorMode = updatedHasRegulatorMode;

            initSlots();
        }
    }

    public void initSlots() {
        this.slots.clear();
        this.slots.clear();

        this.transferManager.clearTransfers();

        for (int i = 0; i < 4; ++i) {
            addSlot(new SlotItemHandler(exporter.getNode().getUpgrades(), i, 187, 6 + (i * 18)));
        }

        for (int i = 0; i < 9; ++i) {
            addSlot(new FilterSlot(
                exporter.getNode().getItemFilters(),
                i,
                8 + (18 * i),
                20,
                exporter.getNode().getUpgrades().hasUpgrade(UpgradeItem.Type.REGULATOR) ? FilterSlot.FILTER_ALLOW_SIZE : 0
            ).setEnableHandler(() -> exporter.getNode().getType() == IType.ITEMS));
        }

        for (int i = 0; i < 9; ++i) {
            addSlot(new FluidFilterSlot(
                exporter.getNode().getFluidFilters(),
                i,
                8 + (18 * i),
                20,
                exporter.getNode().getUpgrades().hasUpgrade(UpgradeItem.Type.REGULATOR) ? FluidFilterSlot.FILTER_ALLOW_SIZE : 0
            ).setEnableHandler(() -> exporter.getNode().getType() == IType.FLUIDS));
        }

        addPlayerInventory(8, 55);

        transferManager.addBiTransfer(getPlayer().getInventory(), exporter.getNode().getUpgrades());
        transferManager.addFilterTransfer(getPlayer().getInventory(), exporter.getNode().getItemFilters(), exporter.getNode().getFluidFilters(), exporter.getNode()::getType);
    }
}
