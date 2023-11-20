package com.hybridavenger69.mtstorage.item;

import com.hybridavenger69.mtstorage.MS;
import com.hybridavenger69.mtstorage.api.network.item.INetworkItem;
import com.hybridavenger69.mtstorage.api.network.item.INetworkItemManager;
import com.hybridavenger69.mtstorage.apiimpl.network.item.WirelessCraftingMonitorNetworkItem;
import com.hybridavenger69.mtstorage.inventory.player.PlayerSlot;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.UUID;

public class WirelessCraftingMonitorItem extends NetworkItem {
    public static final String NBT_TAB_SELECTED = "TabSelected";
    public static final String NBT_TAB_PAGE = "TabPage";
    private final Type type;

    public WirelessCraftingMonitorItem(Type type) {
        super(new Item.Properties().tab(MS.CREATIVE_MODE_TAB).stacksTo(1), type == Type.CREATIVE, () -> MS.SERVER_CONFIG.getWirelessCraftingMonitor().getCapacity());

        this.type = type;
    }

    public static Optional<UUID> getTabSelected(ItemStack stack) {
        if (stack.hasTag() && stack.getTag().hasUUID(NBT_TAB_SELECTED)) {
            return Optional.of(stack.getTag().getUUID(NBT_TAB_SELECTED));
        }

        return Optional.empty();
    }

    public static void setTabSelected(ItemStack stack, Optional<UUID> tabSelected) {
        if (!stack.hasTag()) {
            stack.setTag(new CompoundTag());
        }

        if (tabSelected.isPresent()) {
            stack.getTag().putUUID(NBT_TAB_SELECTED, tabSelected.get());
        } else {
            stack.getTag().remove(NBT_TAB_SELECTED + "Least");
            stack.getTag().remove(NBT_TAB_SELECTED + "Most");
        }
    }

    public static int getTabPage(ItemStack stack) {
        if (stack.hasTag() && stack.getTag().contains(NBT_TAB_PAGE)) {
            return stack.getTag().getInt(NBT_TAB_PAGE);
        }

        return 0;
    }

    public static void setTabPage(ItemStack stack, int tabPage) {
        if (!stack.hasTag()) {
            stack.setTag(new CompoundTag());
        }

        stack.getTag().putInt(NBT_TAB_PAGE, tabPage);
    }

    public Type getType() {
        return type;
    }

    @Nonnull
    @Override
    public INetworkItem provide(INetworkItemManager handler, Player player, ItemStack stack, PlayerSlot slot) {
        return new WirelessCraftingMonitorNetworkItem(handler, player, stack, slot);
    }

    public enum Type {
        NORMAL,
        CREATIVE
    }
}
