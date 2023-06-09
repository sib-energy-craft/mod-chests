package com.github.sib_energy_craft.chests.block.entity;

import com.github.sib_energy_craft.chests.ChestTier;
import com.github.sib_energy_craft.chests.block.IronExtendedChestBlock;
import com.github.sib_energy_craft.chests.load.Entities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public class IronExtendedChestBlockEntity extends AbstractExtendedChestBlockEntity<IronExtendedChestBlock> {

    public IronExtendedChestBlockEntity(@NotNull BlockPos blockPos,
                                        @NotNull BlockState blockState,
                                        @NotNull IronExtendedChestBlock block) {
        super(Entities.IRON_CHEST, blockPos, blockState, "container.iron_chest", block);
    }

    public IronExtendedChestBlockEntity(@NotNull BlockPos blockPos,
                                        @NotNull BlockState blockState,
                                        @NotNull IronExtendedChestBlock block,
                                        @NotNull Inventory inventory) {
        super(Entities.IRON_CHEST, blockPos, blockState, "container.iron_chest", block, inventory);
    }

    @NotNull
    @Override
    protected ScreenHandler createScreenHandler(int syncId,
                                                @NotNull PlayerInventory playerInventory) {
        return new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X5, syncId, playerInventory, this, 5);
    }

    @Override
    public ChestTier getTier() {
        return ChestTier.T2;
    }

}
