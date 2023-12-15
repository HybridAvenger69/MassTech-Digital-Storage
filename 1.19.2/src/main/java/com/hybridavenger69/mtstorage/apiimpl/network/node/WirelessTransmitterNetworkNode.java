package com.hybridavenger69.mtstorage.apiimpl.network.node;

import com.hybridavenger69.mtstorage.MS;
import com.hybridavenger69.mtstorage.api.network.IWirelessTransmitter;
import com.hybridavenger69.mtstorage.inventory.item.BaseItemHandler;
import com.hybridavenger69.mtstorage.inventory.item.UpgradeItemHandler;
import com.hybridavenger69.mtstorage.inventory.listener.NetworkNodeInventoryListener;
import com.hybridavenger69.mtstorage.item.UpgradeItem;
import com.hybridavenger69.mtstorage.util.StackUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;

public class WirelessTransmitterNetworkNode extends NetworkNode implements IWirelessTransmitter {
    public static final ResourceLocation ID = new ResourceLocation(MS.ID, "wireless_transmitter");

    private final UpgradeItemHandler upgrades = (UpgradeItemHandler) new UpgradeItemHandler(4, UpgradeItem.Type.RANGE).addListener(new NetworkNodeInventoryListener(this));

    public WirelessTransmitterNetworkNode(Level level, BlockPos pos) {
        super(level, pos);
    }

    @Override
    public int getEnergyUsage() {
        return MS.SERVER_CONFIG.getWirelessTransmitter().getUsage() + upgrades.getEnergyUsage();
    }

    @Override
    public void read(CompoundTag tag) {
        super.read(tag);

        StackUtils.readItems(upgrades, 0, tag);
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public CompoundTag write(CompoundTag tag) {
        super.write(tag);

        StackUtils.writeItems(upgrades, 0, tag);

        return tag;
    }

    @Override
    public int getRange() {
        return MS.SERVER_CONFIG.getWirelessTransmitter().getBaseRange() + (upgrades.getUpgradeCount(UpgradeItem.Type.RANGE) * MS.SERVER_CONFIG.getWirelessTransmitter().getRangePerUpgrade());
    }

    @Override
    public BlockPos getOrigin() {
        return pos;
    }

    @Override
    public ResourceKey<Level> getDimension() {
        return level.dimension();
    }

    public BaseItemHandler getUpgrades() {
        return upgrades;
    }

    @Override
    public IItemHandler getDrops() {
        return getUpgrades();
    }

    @Override
    public boolean canConduct(Direction direction) {
        return getDirection() == direction;
    }

    @Override
    public void visit(Operator operator) {
        operator.apply(level, pos.relative(Direction.DOWN), Direction.UP);
    }
}
