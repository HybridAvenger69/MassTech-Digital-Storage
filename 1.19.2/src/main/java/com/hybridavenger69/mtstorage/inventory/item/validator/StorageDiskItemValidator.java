package com.hybridavenger69.mtstorage.inventory.item.validator;

import com.hybridavenger69.mtstorage.api.storage.disk.IStorageDiskProvider;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

public class StorageDiskItemValidator implements Predicate<ItemStack> {
    @Override
    public boolean test(ItemStack stack) {
        return stack.getItem() instanceof IStorageDiskProvider && ((IStorageDiskProvider) stack.getItem()).isValid(stack);
    }
}
