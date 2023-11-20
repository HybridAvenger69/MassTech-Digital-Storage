package com.hybridavenger69.mtstorage.util;

import com.hybridavenger69.hybridlib.HybridIDS;
import com.hybridavenger69.mtstorage.MS;
import com.hybridavenger69.mtstorage.MSBlocks;
import com.hybridavenger69.mtstorage.MSItems;
import com.hybridavenger69.mtstorage.block.BaseBlock;
import com.hybridavenger69.mtstorage.block.BlockDirection;
import com.hybridavenger69.mtstorage.block.NetworkNodeBlock;
import com.hybridavenger69.mtstorage.item.blockitem.ColoredBlockItem;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ColorMap<T> {
    public static final DyeColor DEFAULT_COLOR = DyeColor.LIGHT_BLUE;

    private final Map<DyeColor, RegistryObject<T>> map = new EnumMap<>(DyeColor.class);

    private final Map<T, DyeColor> colorByBlock = new HashMap<>();

    private DeferredRegister<Item> itemRegister;
    private DeferredRegister<Block> blockRegister;
    private List<Runnable> lateRegistration;

    public ColorMap(DeferredRegister<Block> blockRegister) {
        this.blockRegister = blockRegister;
    }

    public ColorMap(DeferredRegister<Item> itemRegister, List<Runnable> lateRegistration) {
        this.itemRegister = itemRegister;
        this.lateRegistration = lateRegistration;
    }

    public RegistryObject<T> get(DyeColor color) {
        return map.get(color);
    }

    public DyeColor getColorFromObject(T object) {
        if (colorByBlock.isEmpty()) {
            map.forEach(((dyeColor, registryObject) -> colorByBlock.put(registryObject.get(), dyeColor)));
        }
        return colorByBlock.get(object);
    }

    public Collection<RegistryObject<T>> values() {
        return map.values();
    }

    public void put(DyeColor color, RegistryObject<T> object) {
        map.put(color, object);
    }

    public void forEach(BiConsumer<DyeColor, RegistryObject<T>> consumer) {
        map.forEach(consumer);
    }

    public Block[] getBlocks() {
        return map.values().stream().map(RegistryObject::get).toArray(Block[]::new);
    }

    public <S extends Block> void registerBlocks(String name, Supplier<S> blockFactory) {
        for (DyeColor color : DyeColor.values()) {
            String prefix = color != DEFAULT_COLOR ? color + "_" : "";
            RegistryObject<S> block = blockRegister.register(prefix + name, blockFactory);
            map.put(color, (RegistryObject<T>) block);
            MSBlocks.COLORED_BLOCKS.add(block);
        }
        MSBlocks.COLORED_BLOCK_TAGS.put(BlockTags.create(new ResourceLocation(HybridIDS.MTStorage_MODID, get(DEFAULT_COLOR).getId().getPath())), (ColorMap<S>) this);
    }

    public <S extends BaseBlock> void registerItemsFromBlocks(ColorMap<S> blockMap) {
        RegistryObject<S> originalBlock = blockMap.get(DEFAULT_COLOR);
        map.put(DEFAULT_COLOR, registerBlockItemFor(originalBlock, DEFAULT_COLOR, originalBlock));
        lateRegistration.add(() -> blockMap.forEach((color, block) -> {
            if (color != DEFAULT_COLOR) {
                map.put(color, registerBlockItemFor(block, color, originalBlock));
            }
        }));
        MSItems.COLORED_ITEM_TAGS.put(ItemTags.create(new ResourceLocation(HybridIDS.MTStorage_MODID, blockMap.get(DEFAULT_COLOR).getId().getPath())), (ColorMap<BlockItem>) this);
    }

    private <S extends BaseBlock> RegistryObject<T> registerBlockItemFor(RegistryObject<S> block, DyeColor color, RegistryObject<S> translationBlock) {
        return (RegistryObject<T>) itemRegister.register(
            block.getId().getPath(),
            () -> new ColoredBlockItem(
                block.get(),
                new Item.Properties().tab(MS.CREATIVE_MODE_TAB),
                color,
                BlockUtils.getBlockTranslation(translationBlock.get())
            )
        );
    }

    public <S extends BaseBlock> InteractionResult changeBlockColor(BlockState state, ItemStack heldItem, Level level, BlockPos pos, Player player) {
        DyeColor color = DyeColor.getColor(heldItem);
        if (color == null || state.getBlock().equals(map.get(color).get())) {
            return InteractionResult.PASS;
        }

        return setBlockState(getNewState((RegistryObject<S>) map.get(color), state), heldItem, level, pos, player);
    }

    private <S extends BaseBlock> BlockState getNewState(RegistryObject<S> block, BlockState state) {
        BlockState newState = block.get().defaultBlockState();

        if (((NetworkNodeBlock) block.get()).hasConnectedState()) {
            newState = newState.setValue(NetworkNodeBlock.CONNECTED, state.getValue(NetworkNodeBlock.CONNECTED));
        }
        if (block.get().getDirection() != BlockDirection.NONE) {
            newState = newState.setValue(block.get().getDirection().getProperty(), state.getValue(block.get().getDirection().getProperty()));
        }

        return newState;
    }

    public InteractionResult setBlockState(BlockState newState, ItemStack heldItem, Level level, BlockPos pos, Player player) {
        if (!level.isClientSide) {
            level.setBlockAndUpdate(pos, newState);
            if (((ServerPlayer) player).gameMode.getGameModeForPlayer() != GameType.CREATIVE) {
                heldItem.shrink(1);
            }
        }

        return InteractionResult.SUCCESS;
    }
}
