package com.github.sib_energy_craft.chests.block.entity;

import lombok.AllArgsConstructor;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.ViewerCountManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.DoubleInventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

/**
 * @author sibmaks
 * @since 0.0.8
 */
@AllArgsConstructor
public class ChestViewerCountManager<T extends AbstractExtendedChestBlockEntity<?>> extends ViewerCountManager  {
    private final T blockEntity;

    @Override
    protected void onContainerOpen(@NotNull World world,
                                   @NotNull BlockPos pos,
                                   @NotNull BlockState state) {
        AbstractExtendedChestBlockEntity.playSound(world, pos, state, SoundEvents.BLOCK_CHEST_OPEN);
    }

    @Override
    protected void onContainerClose(World world, BlockPos pos, BlockState state) {
        AbstractExtendedChestBlockEntity.playSound(world, pos, state, SoundEvents.BLOCK_CHEST_CLOSE);
    }

    @Override
    protected void onViewerCountUpdate(@NotNull World world,
                                       @NotNull BlockPos pos,
                                       @NotNull BlockState state,
                                       int oldViewerCount,
                                       int newViewerCount) {
        blockEntity.onViewerCountUpdate(world, pos, state, oldViewerCount, newViewerCount);
    }

    @Override
    protected boolean isPlayerViewing(@NotNull PlayerEntity player) {
        if (!(player.currentScreenHandler instanceof GenericContainerScreenHandler genericContainerScreenHandler)) {
            return false;
        } else {
            var inventory = genericContainerScreenHandler.getInventory();
            return inventory == blockEntity ||
                    inventory instanceof DoubleInventory doubleInventory &&
                            doubleInventory.isPart(blockEntity);
        }
    }
}