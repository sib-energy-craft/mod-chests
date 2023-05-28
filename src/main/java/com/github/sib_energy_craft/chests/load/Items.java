package com.github.sib_energy_craft.chests.load;

import com.github.sib_energy_craft.chests.ChestTier;
import com.github.sib_energy_craft.chests.block.*;
import com.github.sib_energy_craft.chests.item.ChestItem;
import com.github.sib_energy_craft.chests.item.ChestUpgradeItem;
import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

import static com.github.sib_energy_craft.sec_utils.utils.ItemUtils.register;


/**
 * @since 0.0.1
 * @author sibmaks
 */
public final class Items implements DefaultModInitializer {
    public static final ChestItem<CopperExtendedChestBlock> COPPER_CHEST;
    public static final ChestItem<IronExtendedChestBlock> IRON_CHEST;
    public static final ChestItem<GoldExtendedChestBlock> GOLD_CHEST;
    public static final ChestItem<SilverExtendedChestBlock> SILVER_CHEST;
    public static final ChestItem<DiamondExtendedChestBlock> DIAMOND_CHEST;

    public static final ChestUpgradeItem COPPER_UPGRADE_CHEST;
    public static final ChestUpgradeItem IRON_UPGRADE_CHEST;
    public static final ChestUpgradeItem SILVER_UPGRADE_CHEST;
    public static final ChestUpgradeItem GOLD_UPGRADE_CHEST;
    public static final ChestUpgradeItem DIAMOND_UPGRADE_CHEST;

    static {
        var chestSettings = new Item.Settings();

        var copperChestItem = new ChestItem<>(Blocks.COPPER_CHEST.entity(), chestSettings);
        COPPER_CHEST = register(ItemGroups.FUNCTIONAL, Blocks.COPPER_CHEST.identifier(), copperChestItem);

        var ironChestItem = new ChestItem<>(Blocks.IRON_CHEST.entity(), chestSettings);
        IRON_CHEST = register(ItemGroups.FUNCTIONAL, Blocks.IRON_CHEST.identifier(), ironChestItem);

        var goldChestItem = new ChestItem<>(Blocks.GOLD_CHEST.entity(), chestSettings);
        GOLD_CHEST = register(ItemGroups.FUNCTIONAL, Blocks.GOLD_CHEST.identifier(), goldChestItem);

        var silverChestItem = new ChestItem<>(Blocks.SILVER_CHEST.entity(), chestSettings);
        SILVER_CHEST = register(ItemGroups.FUNCTIONAL, Blocks.SILVER_CHEST.identifier(), silverChestItem);

        var diamondChestItem = new ChestItem<>(Blocks.DIAMOND_CHEST.entity(), chestSettings);
        DIAMOND_CHEST = register(ItemGroups.FUNCTIONAL, Blocks.DIAMOND_CHEST.identifier(), diamondChestItem);

        var copperUpgradeChestItem = new ChestUpgradeItem(
                chestSettings,
                ChestTier.T1,
                getUpgradeChestStateFactory(Blocks.COPPER_CHEST.entity())
        );
        COPPER_UPGRADE_CHEST = register(ItemGroups.FUNCTIONAL, Identifiers.of("copper_chest_upgrade"), copperUpgradeChestItem);

        var ironUpgradeChestItem = new ChestUpgradeItem(
                chestSettings,
                ChestTier.T2,
                getUpgradeChestStateFactory(Blocks.IRON_CHEST.entity())
        );
        IRON_UPGRADE_CHEST = register(ItemGroups.FUNCTIONAL, Identifiers.of("iron_chest_upgrade"), ironUpgradeChestItem);

        var silverUpgradeChestItem = new ChestUpgradeItem(
                chestSettings,
                ChestTier.T3,
                getUpgradeChestStateFactory(Blocks.SILVER_CHEST.entity())
        );
        SILVER_UPGRADE_CHEST = register(ItemGroups.FUNCTIONAL, Identifiers.of("silver_chest_upgrade"), silverUpgradeChestItem);

        var goldUpgradeChestItem = new ChestUpgradeItem(
                chestSettings,
                ChestTier.T4,
                getUpgradeChestStateFactory(Blocks.GOLD_CHEST.entity())
        );
        GOLD_UPGRADE_CHEST = register(ItemGroups.FUNCTIONAL, Identifiers.of("gold_chest_upgrade"), goldUpgradeChestItem);

        var diamondUpgradeChestItem = new ChestUpgradeItem(
                chestSettings,
                ChestTier.T4,
                getUpgradeChestStateFactory(Blocks.DIAMOND_CHEST.entity())
        );
        DIAMOND_UPGRADE_CHEST = register(ItemGroups.FUNCTIONAL, Identifiers.of("diamond_chest_upgrade"), diamondUpgradeChestItem);
    }

    @NotNull
    private static<T extends AbstractExtendedChestBlock> Function<BlockState, BlockState> getUpgradeChestStateFactory(
            T block
    ) {
        return sourceBlockState -> block.getDefaultState()
                .with(AbstractExtendedChestBlock.FACING, sourceBlockState.get(AbstractExtendedChestBlock.FACING));
    }
}
