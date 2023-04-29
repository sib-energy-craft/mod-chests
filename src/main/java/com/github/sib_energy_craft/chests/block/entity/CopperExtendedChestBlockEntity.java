package com.github.sib_energy_craft.chests.block.entity;

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
public class CopperExtendedChestBlockEntity extends AbstractExtendedChestBlockEntity {

    public CopperExtendedChestBlockEntity(@NotNull BlockPos blockPos,
                                          @NotNull BlockState blockState) {
        super(Entities.COPPER_CHEST, blockPos, blockState, "container.copper_chest", 36);
    }

    @NotNull
    @Override
    protected ScreenHandler createScreenHandler(int syncId,
                                                @NotNull PlayerInventory playerInventory) {
        return new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X4, syncId, playerInventory, this, 4);
    }

}
