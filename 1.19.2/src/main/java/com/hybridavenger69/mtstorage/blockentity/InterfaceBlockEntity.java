package com.hybridavenger69.mtstorage.blockentity;

import com.hybridavenger69.mtstorage.MSBlockEntities;
import com.hybridavenger69.mtstorage.apiimpl.network.node.InterfaceNetworkNode;
import com.hybridavenger69.mtstorage.blockentity.config.IComparable;
import com.hybridavenger69.mtstorage.blockentity.data.BlockEntitySynchronizationParameter;
import com.hybridavenger69.mtstorage.blockentity.data.BlockEntitySynchronizationSpec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class InterfaceBlockEntity extends NetworkNodeBlockEntity<InterfaceNetworkNode> {
    public static final BlockEntitySynchronizationParameter<Integer, InterfaceBlockEntity> COMPARE = IComparable.createParameter();

    public static BlockEntitySynchronizationSpec SPEC = BlockEntitySynchronizationSpec.builder()
        .addWatchedParameter(REDSTONE_MODE)
        .addWatchedParameter(COMPARE)
        .build();

    private final LazyOptional<IItemHandler> itemsCapability = LazyOptional.of(() -> getNode().getItems());

    public InterfaceBlockEntity(BlockPos pos, BlockState state) {
        super(MSBlockEntities.INTERFACE.get(), pos, state, SPEC, InterfaceNetworkNode.class);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction direction) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return itemsCapability.cast();
        }

        return super.getCapability(cap, direction);
    }

    @Override
    @Nonnull
    public InterfaceNetworkNode createNode(Level level, BlockPos pos) {
        return new InterfaceNetworkNode(level, pos);
    }
}
