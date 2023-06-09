package com.github.sib_energy_craft.chests.block.entity;

import com.github.sib_energy_craft.chests.ChestTier;
import com.github.sib_energy_craft.chests.block.CopperExtendedChestBlock;
import com.github.sib_energy_craft.chests.load.Entities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public class CopperExtendedChestBlockEntity extends AbstractExtendedChestBlockEntity<CopperExtendedChestBlock> {

    public CopperExtendedChestBlockEntity(@NotNull BlockPos blockPos,
                                          @NotNull BlockState blockState,
                                          @NotNull CopperExtendedChestBlock block) {
        super(Entities.COPPER_CHEST, blockPos, blockState, "container.copper_chest", block);
    }

    @NotNull
    @Override
    protected ScreenHandler createScreenHandler(int syncId,
                                                @NotNull PlayerInventory playerInventory) {
        return new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X4, syncId, playerInventory, this, 4);
    }

    @Override
    public ChestTier getTier() {
        return ChestTier.T1;
    }
}
