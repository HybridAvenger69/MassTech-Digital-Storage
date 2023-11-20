package com.hybridavenger69.mtstorage.item.property;

import com.hybridavenger69.mtstorage.apiimpl.network.Network;
import com.hybridavenger69.mtstorage.block.ControllerBlock;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;

public class ControllerItemPropertyGetter implements ItemPropertyFunction {
    @Override
    public float call(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int p) {
        IEnergyStorage storage = stack.getCapability(ForgeCapabilities.ENERGY).orElse(null);
        if (storage != null) {
            return Network.getEnergyType(storage.getEnergyStored(), storage.getMaxEnergyStored()).ordinal();
        }
        return ControllerBlock.EnergyType.OFF.ordinal();
    }
}
