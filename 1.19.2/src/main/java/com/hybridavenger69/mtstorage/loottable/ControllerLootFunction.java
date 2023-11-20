package com.hybridavenger69.mtstorage.loottable;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.hybridavenger69.mtstorage.MSLootFunctions;
import com.hybridavenger69.mtstorage.api.network.INetwork;
import com.hybridavenger69.mtstorage.blockentity.ControllerBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

public class ControllerLootFunction extends LootItemConditionalFunction {
    protected ControllerLootFunction(LootItemCondition[] conditions) {
        super(conditions);
    }

    public static LootItemConditionalFunction.Builder<?> builder() {
        return simpleBuilder(ControllerLootFunction::new);
    }

    @Override
    protected ItemStack run(ItemStack itemStack, LootContext lootContext) {
        BlockEntity blockEntity = lootContext.getParamOrNull(LootContextParams.BLOCK_ENTITY);

        if (blockEntity instanceof ControllerBlockEntity) {
            INetwork network = ((ControllerBlockEntity) blockEntity).getRemovedNetwork() == null ? ((ControllerBlockEntity) blockEntity).getNetwork() : ((ControllerBlockEntity) blockEntity).getRemovedNetwork();

            itemStack.getCapability(ForgeCapabilities.ENERGY).ifPresent(energy -> energy.receiveEnergy(network.getEnergyStorage().getEnergyStored(), false));
        }

        return itemStack;
    }

    @Override
    public LootItemFunctionType getType() {
        return MSLootFunctions.CONTROLLER.get();
    }

    public static class Serializer extends LootItemConditionalFunction.Serializer<ControllerLootFunction> {
        @Override
        public ControllerLootFunction deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootItemCondition[] conditions) {
            return new ControllerLootFunction(conditions);
        }
    }
}
