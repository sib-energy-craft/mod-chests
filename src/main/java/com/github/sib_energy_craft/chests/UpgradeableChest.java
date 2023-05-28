package com.github.sib_energy_craft.chests;

import net.minecraft.inventory.Inventory;

/**
 * @author sibmaks
 * @since 0.0.8
 */
public interface UpgradeableChest extends Inventory {

    /**
     * Get chest tier
     * @return chest tier
     */
    ChestTier getTier();

}
