package com.github.sib_energy_craft.chests.load;

import com.github.sib_energy_craft.sec_utils.load.ModRegistrar;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;

import static com.github.sib_energy_craft.sec_utils.utils.ItemUtils.registerBlockItem;


/**
 * @since 0.0.1
 * @author sibmaks
 */
public final class Items implements ModRegistrar {
    public static final Item COPPER_CHEST;
    public static final Item IRON_CHEST;
    public static final Item GOLD_CHEST;
    public static final Item DIAMOND_CHEST;

    static {
        COPPER_CHEST = registerBlockItem(ItemGroups.FUNCTIONAL, Blocks.COPPER_CHEST);
        IRON_CHEST = registerBlockItem(ItemGroups.FUNCTIONAL, Blocks.IRON_CHEST);
        GOLD_CHEST = registerBlockItem(ItemGroups.FUNCTIONAL, Blocks.GOLD_CHEST);
        DIAMOND_CHEST = registerBlockItem(ItemGroups.FUNCTIONAL, Blocks.DIAMOND_CHEST);
    }
}
