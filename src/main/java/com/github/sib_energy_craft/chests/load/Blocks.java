package com.github.sib_energy_craft.chests.load;

import com.github.sib_energy_craft.chests.block.*;
import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.sec_utils.common.Identified;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

import static com.github.sib_energy_craft.sec_utils.utils.BlockUtils.register;


/**
 * @since 0.0.1
 * @author sibmaks
 */
public final class Blocks implements DefaultModInitializer {
    public static final Identified<CopperExtendedChestBlock> COPPER_CHEST;
    public static final Identified<IronExtendedChestBlock> IRON_CHEST;
    public static final Identified<GoldExtendedChestBlock> GOLD_CHEST;
    public static final Identified<SilverExtendedChestBlock> SILVER_CHEST;
    public static final Identified<DiamondExtendedChestBlock> DIAMOND_CHEST;

    static {
        var chestSettings = AbstractBlock.Settings
                .of(Material.METAL)
                .strength(2.5F)
                .requiresTool()
                .sounds(BlockSoundGroup.METAL);

        var ironExtendedChestBlock = new IronExtendedChestBlock(chestSettings);
        IRON_CHEST = register(Identifiers.of("iron_chest"), ironExtendedChestBlock);

        var copperExtendedChestBlock = new CopperExtendedChestBlock(chestSettings);
        COPPER_CHEST = register(Identifiers.of("copper_chest"), copperExtendedChestBlock);

        var goldExtendedChestBlock = new GoldExtendedChestBlock(chestSettings);
        GOLD_CHEST = register(Identifiers.of("gold_chest"), goldExtendedChestBlock);

        var silverExtendedChestBlock = new SilverExtendedChestBlock(chestSettings);
        SILVER_CHEST = register(Identifiers.of("silver_chest"), silverExtendedChestBlock);

        var diamondExtendedChestBlock = new DiamondExtendedChestBlock(chestSettings);
        DIAMOND_CHEST = register(Identifiers.of("diamond_chest"), diamondExtendedChestBlock);
    }
}
