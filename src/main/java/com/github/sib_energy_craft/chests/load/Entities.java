package com.github.sib_energy_craft.chests.load;

import com.github.sib_energy_craft.chests.block.entity.CopperExtendedChestBlockEntity;
import com.github.sib_energy_craft.chests.block.entity.DiamondExtendedChestBlockEntity;
import com.github.sib_energy_craft.chests.block.entity.GoldExtendedChestBlockEntity;
import com.github.sib_energy_craft.chests.block.entity.IronExtendedChestBlockEntity;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import com.github.sib_energy_craft.sec_utils.utils.EntityUtils;
import net.minecraft.block.entity.BlockEntityType;


/**
 * @since 0.0.1
 * @author sibmaks
 */
public final class Entities implements DefaultModInitializer {
    public static final BlockEntityType<IronExtendedChestBlockEntity> IRON_CHEST;
    public static final BlockEntityType<CopperExtendedChestBlockEntity> COPPER_CHEST;
    public static final BlockEntityType<GoldExtendedChestBlockEntity> GOLD_CHEST;
    public static final BlockEntityType<DiamondExtendedChestBlockEntity> DIAMOND_CHEST;

    static {
        IRON_CHEST = EntityUtils.register(Blocks.IRON_CHEST, IronExtendedChestBlockEntity::new);
        COPPER_CHEST = EntityUtils.register(Blocks.COPPER_CHEST, CopperExtendedChestBlockEntity::new);
        GOLD_CHEST = EntityUtils.register(Blocks.GOLD_CHEST, GoldExtendedChestBlockEntity::new);
        DIAMOND_CHEST = EntityUtils.register(Blocks.DIAMOND_CHEST, DiamondExtendedChestBlockEntity::new);
    }
}
