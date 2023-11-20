package com.hybridavenger69.mtstorage.item;

import com.hybridavenger69.mtstorage.MS;
import com.hybridavenger69.mtstorage.api.network.grid.IGrid;
import com.hybridavenger69.mtstorage.api.network.item.INetworkItem;
import com.hybridavenger69.mtstorage.api.network.item.INetworkItemManager;
import com.hybridavenger69.mtstorage.apiimpl.network.item.WirelessFluidGridNetworkItem;
import com.hybridavenger69.mtstorage.apiimpl.network.node.GridNetworkNode;
import com.hybridavenger69.mtstorage.inventory.player.PlayerSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class WirelessFluidGridItem extends NetworkItem {
    private final Type type;

    public WirelessFluidGridItem(Type type) {
        super(new Item.Properties().tab(MS.CREATIVE_MODE_TAB).stacksTo(1), type == Type.CREATIVE, () -> MS.SERVER_CONFIG.getWirelessFluidGrid().getCapacity());

        this.type = type;
    }

    public static int getSortingType(ItemStack stack) {
        return (stack.hasTag() && stack.getTag().contains(GridNetworkNode.NBT_SORTING_TYPE)) ? stack.getTag().getInt(GridNetworkNode.NBT_SORTING_TYPE) : IGrid.SORTING_TYPE_QUANTITY;
    }

    public static int getSortingDirection(ItemStack stack) {
        return (stack.hasTag() && stack.getTag().contains(GridNetworkNode.NBT_SORTING_DIRECTION)) ? stack.getTag().getInt(GridNetworkNode.NBT_SORTING_DIRECTION) : IGrid.SORTING_DIRECTION_DESCENDING;
    }

    public static int getSearchBoxMode(ItemStack stack) {
        return (stack.hasTag() && stack.getTag().contains(GridNetworkNode.NBT_SEARCH_BOX_MODE)) ? stack.getTag().getInt(GridNetworkNode.NBT_SEARCH_BOX_MODE) : IGrid.SEARCH_BOX_MODE_NORMAL;
    }

    public static int getTabSelected(ItemStack stack) {
        return (stack.hasTag() && stack.getTag().contains(GridNetworkNode.NBT_TAB_SELECTED)) ? stack.getTag().getInt(GridNetworkNode.NBT_TAB_SELECTED) : -1;
    }

    public static int getTabPage(ItemStack stack) {
        return (stack.hasTag() && stack.getTag().contains(GridNetworkNode.NBT_TAB_PAGE)) ? stack.getTag().getInt(GridNetworkNode.NBT_TAB_PAGE) : 0;
    }

    public static int getSize(ItemStack stack) {
        return (stack.hasTag() && stack.getTag().contains(GridNetworkNode.NBT_SIZE)) ? stack.getTag().getInt(GridNetworkNode.NBT_SIZE) : IGrid.SIZE_STRETCH;
    }

    public Type getType() {
        return type;
    }

    @Nonnull
    @Override
    public INetworkItem provide(INetworkItemManager handler, Player player, ItemStack stack, PlayerSlot slot) {
        return new WirelessFluidGridNetworkItem(handler, player, stack, slot);
    }

    public enum Type {
        NORMAL,
        CREATIVE
    }
}
