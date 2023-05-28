package com.github.sib_energy_craft.chests.load;

import com.github.sib_energy_craft.chests.item.ChestItem;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;

import static com.github.sib_energy_craft.sec_utils.utils.ItemUtils.register;


/**
 * @since 0.0.1
 * @author sibmaks
 */
public final class Items implements DefaultModInitializer {
    public static final BlockItem COPPER_CHEST;
    public static final BlockItem IRON_CHEST;
    public static final BlockItem GOLD_CHEST;
    public static final BlockItem DIAMOND_CHEST;

    static {
        var chestSettings = new Item.Settings();

        var copperChestItem = new ChestItem<>(Blocks.COPPER_CHEST.entity(), chestSettings);
        COPPER_CHEST = register(ItemGroups.FUNCTIONAL, Blocks.COPPER_CHEST.identifier(), copperChestItem);

        var ironChestItem = new ChestItem<>(Blocks.IRON_CHEST.entity(), chestSettings);
        IRON_CHEST = register(ItemGroups.FUNCTIONAL, Blocks.IRON_CHEST.identifier(), ironChestItem);

        var goldChestItem = new ChestItem<>(Blocks.GOLD_CHEST.entity(), chestSettings);
        GOLD_CHEST = register(ItemGroups.FUNCTIONAL, Blocks.GOLD_CHEST.identifier(), goldChestItem);

        var diamondChestItem = new ChestItem<>(Blocks.DIAMOND_CHEST.entity(), chestSettings);
        DIAMOND_CHEST = register(ItemGroups.FUNCTIONAL, Blocks.DIAMOND_CHEST.identifier(), diamondChestItem);
    }
}
