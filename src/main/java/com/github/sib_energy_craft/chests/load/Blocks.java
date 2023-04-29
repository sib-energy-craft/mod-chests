package com.github.sib_energy_craft.chests.load;

import com.github.sib_energy_craft.chests.block.CopperExtendedChestBlock;
import com.github.sib_energy_craft.chests.block.DiamondExtendedChestBlock;
import com.github.sib_energy_craft.chests.block.GoldExtendedChestBlock;
import com.github.sib_energy_craft.chests.block.IronExtendedChestBlock;
import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.sec_utils.common.Identified;
import com.github.sib_energy_craft.sec_utils.load.ModRegistrar;
import com.github.sib_energy_craft.sec_utils.utils.BlockUtils;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;


/**
 * @since 0.0.1
 * @author sibmaks
 */
public final class Blocks implements ModRegistrar {
    public static final Identified<CopperExtendedChestBlock> COPPER_CHEST;
    public static final Identified<IronExtendedChestBlock> IRON_CHEST;
    public static final Identified<GoldExtendedChestBlock> GOLD_CHEST;
    public static final Identified<DiamondExtendedChestBlock> DIAMOND_CHEST;

    static {
        var chestSettings = AbstractBlock.Settings
                .of(Material.METAL)
                .strength(2.5F)
                .requiresTool()
                .sounds(BlockSoundGroup.METAL);

        var ironExtendedChestBlock = new IronExtendedChestBlock(chestSettings, () -> Entities.IRON_CHEST);
        IRON_CHEST = BlockUtils.register(Identifiers.of("iron_chest"), ironExtendedChestBlock);

        var copperExtendedChestBlock = new CopperExtendedChestBlock(chestSettings, () -> Entities.COPPER_CHEST);
        COPPER_CHEST = BlockUtils.register(Identifiers.of("copper_chest"), copperExtendedChestBlock);

        var goldExtendedChestBlock = new GoldExtendedChestBlock(chestSettings, () -> Entities.GOLD_CHEST);
        GOLD_CHEST = BlockUtils.register(Identifiers.of("gold_chest"), goldExtendedChestBlock);

        var diamondExtendedChestBlock = new DiamondExtendedChestBlock(chestSettings, () -> Entities.DIAMOND_CHEST);
        DIAMOND_CHEST = BlockUtils.register(Identifiers.of("diamond_chest"), diamondExtendedChestBlock);
    }
}
