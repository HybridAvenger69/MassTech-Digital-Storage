package com.hybridavenger69.mtstorage.item;

import com.hybridavenger69.mtstorage.MS;
import com.hybridavenger69.mtstorage.render.Styles;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class UpgradeItem extends Item {
    private final Type type;

    public UpgradeItem(Type type) {
        super(new Item.Properties().tab(MS.CREATIVE_MODE_TAB));

        this.type = type;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        if (type.getFortuneLevel() > 0) {
            tooltip.add(
                Component.translatable("enchantment.minecraft.fortune")
                    .append(" ")
                    .append(Component.translatable("enchantment.level." + type.getFortuneLevel()))
                    .setStyle(Styles.GRAY)
            );
        }
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return type == Type.SILK_TOUCH ||
            type == Type.FORTUNE_1 ||
            type == Type.FORTUNE_2 ||
            type == Type.FORTUNE_3;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        NORMAL("normal"),
        SPEED("speed"),
        RANGE("range"),
        CRAFTING("crafting"),
        STACK("stack"),
        SILK_TOUCH("silk_touch"),
        FORTUNE_1("fortune_1"),
        FORTUNE_2("fortune_2"),
        FORTUNE_3("fortune_3"),
        REGULATOR("regulator");

        private final String name;

        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public int getEnergyUsage() {
            switch (this) {
                case NORMAL:
                    return 0;
                case RANGE:
                    return MS.SERVER_CONFIG.getUpgrades().getRangeUpgradeUsage();
                case SPEED:
                    return MS.SERVER_CONFIG.getUpgrades().getSpeedUpgradeUsage();
                case CRAFTING:
                    return MS.SERVER_CONFIG.getUpgrades().getCraftingUpgradeUsage();
                case STACK:
                    return MS.SERVER_CONFIG.getUpgrades().getStackUpgradeUsage();
                case SILK_TOUCH:
                    return MS.SERVER_CONFIG.getUpgrades().getSilkTouchUpgradeUsage();
                case FORTUNE_1:
                    return MS.SERVER_CONFIG.getUpgrades().getFortune1UpgradeUsage();
                case FORTUNE_2:
                    return MS.SERVER_CONFIG.getUpgrades().getFortune2UpgradeUsage();
                case FORTUNE_3:
                    return MS.SERVER_CONFIG.getUpgrades().getFortune3UpgradeUsage();
                case REGULATOR:
                    return MS.SERVER_CONFIG.getUpgrades().getRegulatorUpgradeUsage();
                default:
                    throw new IllegalStateException("What even am I?");
            }
        }

        public int getFortuneLevel() {
            switch (this) {
                case FORTUNE_1:
                    return 1;
                case FORTUNE_2:
                    return 2;
                case FORTUNE_3:
                    return 3;
                default:
                    return 0;
            }
        }
    }
}
