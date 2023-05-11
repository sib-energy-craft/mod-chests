package com.github.sib_energy_craft.chests.load;

import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroups;

import static com.github.sib_energy_craft.sec_utils.utils.ItemUtils.registerBlockItem;


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
        COPPER_CHEST = registerBlockItem(ItemGroups.FUNCTIONAL, Blocks.COPPER_CHEST);
        IRON_CHEST = registerBlockItem(ItemGroups.FUNCTIONAL, Blocks.IRON_CHEST);
        GOLD_CHEST = registerBlockItem(ItemGroups.FUNCTIONAL, Blocks.GOLD_CHEST);
        DIAMOND_CHEST = registerBlockItem(ItemGroups.FUNCTIONAL, Blocks.DIAMOND_CHEST);
    }
}
