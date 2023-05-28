package com.github.sib_energy_craft.chests.block.entity;

import com.github.sib_energy_craft.chests.block.GoldExtendedChestBlock;
import com.github.sib_energy_craft.chests.load.Entities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public class GoldExtendedChestBlockEntity extends AbstractExtendedChestBlockEntity<GoldExtendedChestBlock> {

    public GoldExtendedChestBlockEntity(@NotNull BlockPos blockPos,
                                        @NotNull BlockState blockState,
                                        @NotNull GoldExtendedChestBlock block) {
        super(Entities.GOLD_CHEST, blockPos, blockState, "container.gold_chest", block);
    }

    @NotNull
    @Override
    protected ScreenHandler createScreenHandler(int syncId,
                                                @NotNull PlayerInventory playerInventory) {
        return GenericContainerScreenHandler.createGeneric9x6(syncId, playerInventory, this);
    }

}
