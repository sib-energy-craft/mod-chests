package com.github.sib_energy_craft.chests.load;

import com.github.sib_energy_craft.chests.block.*;
import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.sec_utils.common.Identified;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.MapColor;
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
        var ironChestSettings = AbstractBlock.Settings.create()
                .mapColor(MapColor.IRON_GRAY)
                .strength(2F)
                .requiresTool()
                .sounds(BlockSoundGroup.METAL);

        var ironExtendedChestBlock = new IronExtendedChestBlock(ironChestSettings);
        IRON_CHEST = register(Identifiers.of("iron_chest"), ironExtendedChestBlock);

        var copperChestSettings = AbstractBlock.Settings.create()
                .mapColor(MapColor.ORANGE)
                .strength(2.5F)
                .requiresTool()
                .sounds(BlockSoundGroup.METAL);

        var copperExtendedChestBlock = new CopperExtendedChestBlock(copperChestSettings);
        COPPER_CHEST = register(Identifiers.of("copper_chest"), copperExtendedChestBlock);

        var goldChestSettings = AbstractBlock.Settings.create()
                .mapColor(MapColor.GOLD)
                .strength(3F)
                .requiresTool()
                .sounds(BlockSoundGroup.METAL);

        var goldExtendedChestBlock = new GoldExtendedChestBlock(goldChestSettings);
        GOLD_CHEST = register(Identifiers.of("gold_chest"), goldExtendedChestBlock);

        var silverChestSettings = AbstractBlock.Settings.create()
                .mapColor(MapColor.LIGHT_GRAY)
                .strength(3.5F)
                .requiresTool()
                .sounds(BlockSoundGroup.METAL);

        var silverExtendedChestBlock = new SilverExtendedChestBlock(silverChestSettings);
        SILVER_CHEST = register(Identifiers.of("silver_chest"), silverExtendedChestBlock);

        var diamondChestSettings = AbstractBlock.Settings.create()
                .mapColor(MapColor.DIAMOND_BLUE)
                .strength(4.5F)
                .requiresTool()
                .sounds(BlockSoundGroup.METAL);

        var diamondExtendedChestBlock = new DiamondExtendedChestBlock(diamondChestSettings);
        DIAMOND_CHEST = register(Identifiers.of("diamond_chest"), diamondExtendedChestBlock);
    }
}
