package com.hybridavenger69.mtstorage.blockentity;

import com.hybridavenger69.mtstorage.MSBlockEntities;
import com.hybridavenger69.mtstorage.apiimpl.network.node.ImporterNetworkNode;
import com.hybridavenger69.mtstorage.apiimpl.network.node.cover.CoverManager;
import com.hybridavenger69.mtstorage.blockentity.config.IComparable;
import com.hybridavenger69.mtstorage.blockentity.config.IType;
import com.hybridavenger69.mtstorage.blockentity.config.IWhitelistBlacklist;
import com.hybridavenger69.mtstorage.blockentity.data.BlockEntitySynchronizationParameter;
import com.hybridavenger69.mtstorage.blockentity.data.BlockEntitySynchronizationSpec;
import com.hybridavenger69.mtstorage.util.LevelUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;

import javax.annotation.Nonnull;

public class ImporterBlockEntity extends NetworkNodeBlockEntity<ImporterNetworkNode> {
    public static final BlockEntitySynchronizationParameter<Integer, ImporterBlockEntity> COMPARE = IComparable.createParameter();
    public static final BlockEntitySynchronizationParameter<Integer, ImporterBlockEntity> WHITELIST_BLACKLIST = IWhitelistBlacklist.createParameter();
    public static final BlockEntitySynchronizationParameter<Integer, ImporterBlockEntity> TYPE = IType.createParameter();
    public static final BlockEntitySynchronizationParameter<CompoundTag, ImporterBlockEntity> COVER_MANAGER = new BlockEntitySynchronizationParameter<>(EntityDataSerializers.COMPOUND_TAG, new CompoundTag(), t -> t.getNode().getCoverManager().writeToNbt(), (t, v) -> t.getNode().getCoverManager().readFromNbt(v), (initial, p) -> {
    });

    public static BlockEntitySynchronizationSpec SPEC = BlockEntitySynchronizationSpec.builder()
        .addWatchedParameter(REDSTONE_MODE)
        .addWatchedParameter(COMPARE)
        .addWatchedParameter(WHITELIST_BLACKLIST)
        .addWatchedParameter(TYPE)
        .addWatchedParameter(COVER_MANAGER)
        .build();

    public ImporterBlockEntity(BlockPos pos, BlockState state) {
        super(MSBlockEntities.IMPORTER.get(), pos, state, SPEC, ImporterNetworkNode.class);
    }

    @Override
    @Nonnull
    public ImporterNetworkNode createNode(Level level, BlockPos pos) {
        return new ImporterNetworkNode(level, pos);
    }

    @Nonnull
    @Override
    public ModelData getModelData() {
        return ModelData.builder().with(CoverManager.PROPERTY, this.getNode().getCoverManager()).build();
    }

    @Override
    public CompoundTag writeUpdate(CompoundTag tag) {
        super.writeUpdate(tag);

        tag.put(CoverManager.NBT_COVER_MANAGER, this.getNode().getCoverManager().writeToNbt());

        return tag;
    }

    @Override
    public void readUpdate(CompoundTag tag) {
        super.readUpdate(tag);

        this.getNode().getCoverManager().readFromNbt(tag.getCompound(CoverManager.NBT_COVER_MANAGER));

        requestModelDataUpdate();

        LevelUtils.updateBlock(level, worldPosition);
    }
}
