package com.hybridavenger69.mtstorage.apiimpl.network.node;

import com.hybridavenger69.mtstorage.MS;
import com.hybridavenger69.mtstorage.api.network.node.ICoverable;
import com.hybridavenger69.mtstorage.apiimpl.network.node.cover.CoverManager;
import com.hybridavenger69.mtstorage.apiimpl.network.node.cover.CoverType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class CableNetworkNode extends NetworkNode implements ICoverable {
    public static final ResourceLocation ID = new ResourceLocation(MS.ID, "cable");

    private final CoverManager coverManager;

    public CableNetworkNode(Level level, BlockPos pos) {
        super(level, pos);
        this.coverManager = new CoverManager(this);
    }

    @Override
    public boolean canConduct(Direction direction) {
        return !coverManager.hasCover(direction) || coverManager.getCover(direction).getType() == CoverType.HOLLOW;
    }

    @Override
    public int getEnergyUsage() {
        return MS.SERVER_CONFIG.getCable().getUsage();
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public CoverManager getCoverManager() {
        return coverManager;
    }

    @Override
    public void read(CompoundTag tag) {
        if (tag.contains(CoverManager.NBT_COVER_MANAGER)) {
            this.coverManager.readFromNbt(tag.getCompound(CoverManager.NBT_COVER_MANAGER));
        }
        super.read(tag);
    }

    @Override
    public CompoundTag write(CompoundTag tag) {
        tag.put(CoverManager.NBT_COVER_MANAGER, this.coverManager.writeToNbt());
        return super.write(tag);
    }
}
