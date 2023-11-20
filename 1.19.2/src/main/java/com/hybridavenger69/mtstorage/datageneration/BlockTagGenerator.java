package com.hybridavenger69.mtstorage.datageneration;

import com.hybridavenger69.mtstorage.MSBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class BlockTagGenerator extends BlockTagsProvider {
    public BlockTagGenerator(DataGenerator dataGenerator, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, modId, existingFileHelper);
    }

    @Override
    protected void addTags() {
        TagAppender<Block> noRelocationTag = tag(BlockTags.create(new ResourceLocation("forge:relocation_not_supported")));
        MSBlocks.COLORED_BLOCK_TAGS.forEach((tag, map) -> {
            map.values().forEach(block -> tag(tag).add(block.get()));
            noRelocationTag.addTags(tag);
        });
        MSBlocks.STORAGE_BLOCKS.forEach((tag, block) -> noRelocationTag.add(block.get()));
        MSBlocks.FLUID_STORAGE_BLOCKS.forEach((tag, block) -> noRelocationTag.add(block.get()));

        noRelocationTag.add(
                MSBlocks.IMPORTER.get(),
                MSBlocks.EXPORTER.get(),
                MSBlocks.EXTERNAL_STORAGE.get(),
                MSBlocks.DISK_DRIVE.get(),
                MSBlocks.INTERFACE.get(),
                MSBlocks.FLUID_INTERFACE.get(),
                MSBlocks.STORAGE_MONITOR.get(),
                MSBlocks.CONSTRUCTOR.get(),
                MSBlocks.DESTRUCTOR.get(),
                MSBlocks.PORTABLE_GRID.get(),
                MSBlocks.CREATIVE_PORTABLE_GRID.get()
        );
    }
}
