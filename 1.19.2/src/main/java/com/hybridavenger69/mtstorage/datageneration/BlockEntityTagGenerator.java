package com.hybridavenger69.mtstorage.datageneration;

import com.hybridavenger69.mtstorage.MSBlockEntities;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class BlockEntityTagGenerator extends TagsProvider<BlockEntityType<?>> {
    public BlockEntityTagGenerator(DataGenerator dataGenerator, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, Registry.BLOCK_ENTITY_TYPE, modId, existingFileHelper);
    }

    @Override
    protected void addTags() {
        TagAppender<BlockEntityType<?>> packingTapeBlacklist = tag(TagKey.create(Registry.BLOCK_ENTITY_TYPE_REGISTRY, new ResourceLocation("packingtape:blacklist/problematic")));
        packingTapeBlacklist.add(
                MSBlockEntities.CONTROLLER.get(),
                MSBlockEntities.CREATIVE_CONTROLLER.get(),
                MSBlockEntities.DETECTOR.get(),
                MSBlockEntities.DISK_DRIVE.get(),
                MSBlockEntities.EXPORTER.get(),
                MSBlockEntities.EXTERNAL_STORAGE.get(),
                MSBlockEntities.GRID.get(),
                MSBlockEntities.CRAFTING_GRID.get(),
                MSBlockEntities.PATTERN_GRID.get(),
                MSBlockEntities.FLUID_GRID.get(),
                MSBlockEntities.IMPORTER.get(),
                MSBlockEntities.NETWORK_TRANSMITTER.get(),
                MSBlockEntities.NETWORK_RECEIVER.get(),
                MSBlockEntities.RELAY.get(),
                MSBlockEntities.CABLE.get(),
                MSBlockEntities.ONE_K_STORAGE_BLOCK.get(),
                MSBlockEntities.FOUR_K_STORAGE_BLOCK.get(),
                MSBlockEntities.SIXTEEN_K_STORAGE_BLOCK.get(),
                MSBlockEntities.SIXTY_FOUR_K_STORAGE_BLOCK.get(),
                MSBlockEntities.CREATIVE_STORAGE_BLOCK.get(),
                MSBlockEntities.SIXTY_FOUR_K_FLUID_STORAGE_BLOCK.get(),
                MSBlockEntities.TWO_HUNDRED_FIFTY_SIX_K_FLUID_STORAGE_BLOCK.get(),
                MSBlockEntities.THOUSAND_TWENTY_FOUR_K_FLUID_STORAGE_BLOCK.get(),
                MSBlockEntities.FOUR_THOUSAND_NINETY_SIX_K_FLUID_STORAGE_BLOCK.get(),
                MSBlockEntities.CREATIVE_FLUID_STORAGE_BLOCK.get(),
                MSBlockEntities.SECURITY_MANAGER.get(),
                MSBlockEntities.INTERFACE.get(),
                MSBlockEntities.FLUID_INTERFACE.get(),
                MSBlockEntities.WIRELESS_TRANSMITTER.get(),
                MSBlockEntities.STORAGE_MONITOR.get(),
                MSBlockEntities.CONSTRUCTOR.get(),
                MSBlockEntities.DESTRUCTOR.get(),
                MSBlockEntities.DISK_MANIPULATOR.get(),
                MSBlockEntities.PORTABLE_GRID.get(),
                MSBlockEntities.CREATIVE_PORTABLE_GRID.get(),
                MSBlockEntities.CRAFTER.get(),
                MSBlockEntities.CRAFTER_MANAGER.get(),
                MSBlockEntities.CRAFTING_MONITOR.get()
        );
    }

    @Override
    public String getName() {
        return "Block Entity Type Tags";
    }
}
