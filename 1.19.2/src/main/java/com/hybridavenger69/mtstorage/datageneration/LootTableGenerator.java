package com.hybridavenger69.mtstorage.datageneration;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.hybridavenger69.mtstorage.MSBlocks;
import com.hybridavenger69.mtstorage.loottable.ControllerLootFunction;
import com.hybridavenger69.mtstorage.loottable.CrafterLootFunction;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class LootTableGenerator extends LootTableProvider {
    public LootTableGenerator(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return ImmutableList.of(Pair.of(RSBlockLootTables::new, LootContextParamSets.BLOCK));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
        //NO OP
    }

    @Override
    public String getName() {
        return "Refined Storage Loot Tables";
    }

    private static class RSBlockLootTables extends BlockLoot {
        @Override
        protected void addTables() {
            MSBlocks.CONTROLLER.values().forEach(block -> genBlockItemLootTableWithFunction(block.get(), ControllerLootFunction.builder()));
            MSBlocks.CREATIVE_CONTROLLER.values().forEach(block -> dropSelf(block.get()));
            MSBlocks.CRAFTER.values().forEach(block -> genBlockItemLootTableWithFunction(block.get(), CrafterLootFunction.builder()));
            MSBlocks.GRID.values().forEach(block -> dropSelf(block.get()));
            MSBlocks.CRAFTING_GRID.values().forEach(block -> dropSelf(block.get()));
            MSBlocks.FLUID_GRID.values().forEach(block -> dropSelf(block.get()));
            MSBlocks.PATTERN_GRID.values().forEach(block -> dropSelf(block.get()));
            MSBlocks.SECURITY_MANAGER.values().forEach(block -> dropSelf(block.get()));
            MSBlocks.WIRELESS_TRANSMITTER.values().forEach(block -> dropSelf(block.get()));
            MSBlocks.RELAY.values().forEach(block -> dropSelf(block.get()));
            MSBlocks.NETWORK_TRANSMITTER.values().forEach(block -> dropSelf(block.get()));
            MSBlocks.NETWORK_RECEIVER.values().forEach(block -> dropSelf(block.get()));
            MSBlocks.DISK_MANIPULATOR.values().forEach(block -> dropSelf(block.get()));
            MSBlocks.CRAFTING_MONITOR.values().forEach(block -> dropSelf(block.get()));
            MSBlocks.CRAFTER_MANAGER.values().forEach(block -> dropSelf(block.get()));
            MSBlocks.DETECTOR.values().forEach(block -> dropSelf(block.get()));
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return MSBlocks.COLORED_BLOCKS.stream().map(RegistryObject::get).collect(Collectors.toList());
        }

        private void genBlockItemLootTableWithFunction(Block block, LootItemFunction.Builder builder) {
            add(block, LootTable.lootTable().withPool(
                LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(block)
                        .apply(builder))
                    .when(ExplosionCondition.survivesExplosion())));
        }
    }
}
