package com.github.sib_energy_craft.chests.item;

import com.github.sib_energy_craft.chests.ChestTier;
import com.github.sib_energy_craft.chests.UpgradeableChest;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * @author sibmaks
 * @since 0.0.8
 */
public class ChestUpgradeItem extends Item {
    private final ChestTier toTier;
    private final Function<BlockState, BlockState> blockStateCreator;

    public ChestUpgradeItem(@NotNull Settings settings,
                            @NotNull ChestTier toTier,
                            @NotNull Function<BlockState, BlockState> blockStateCreator) {
        super(settings);
        this.toTier = toTier;
        this.blockStateCreator = blockStateCreator;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        var world = context.getWorld();
        if(world.isClient || !(world instanceof ServerWorld)) {
            return ActionResult.PASS;
        }

        var blockPos = context.getBlockPos();
        var blockEntity = world.getBlockEntity(blockPos);
        if(!(blockEntity instanceof UpgradeableChest upgradeableChest) || !toTier.isGreater(upgradeableChest.getTier())) {
            return ActionResult.PASS;
        }
        var inventory = copyInventory(upgradeableChest);
        var blockState = world.getBlockState(blockPos);
        var newBlockState = blockStateCreator.apply(blockState);

        upgradeableChest.clear();
        world.setBlockState(blockPos, newBlockState, Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
        var newBlockEntity = world.getBlockEntity(blockPos);
        if(newBlockEntity instanceof Inventory newBlockInventory) {
            for (int i = 0; i < inventory.stacks.size(); i++) {
                newBlockInventory.setStack(i, inventory.getStack(i));
            }
        }
        var stack = context.getStack();
        stack.decrement(1);
        return ActionResult.CONSUME;
    }

    @NotNull
    private static SimpleInventory copyInventory(UpgradeableChest upgradeableChest) {
        var inventory = new SimpleInventory(upgradeableChest.size());
        for (int i = 0; i < upgradeableChest.size(); i++) {
            inventory.setStack(i, upgradeableChest.getStack(i));
        }
        return inventory;
    }
}
