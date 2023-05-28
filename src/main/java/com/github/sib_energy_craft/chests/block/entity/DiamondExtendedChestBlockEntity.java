package com.github.sib_energy_craft.chests.block.entity;

import com.github.sib_energy_craft.chests.ChestTier;
import com.github.sib_energy_craft.chests.block.DiamondExtendedChestBlock;
import com.github.sib_energy_craft.chests.load.Entities;
import com.github.sib_energy_craft.chests.load.ScreenHandlers;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public class DiamondExtendedChestBlockEntity extends AbstractExtendedChestBlockEntity<DiamondExtendedChestBlock>
        implements ExtendedScreenHandlerFactory {

    public DiamondExtendedChestBlockEntity(@NotNull BlockPos blockPos,
                                           @NotNull BlockState blockState,
                                           @NotNull DiamondExtendedChestBlock block) {
        super(Entities.DIAMOND_CHEST, blockPos, blockState, "container.diamond_chest", block);
    }

    @NotNull
    @Override
    protected ScreenHandler createScreenHandler(int syncId,
                                                @NotNull PlayerInventory playerInventory) {
        return new GenericContainerScreenHandler(ScreenHandlers.DIAMOND_CHEST, syncId, playerInventory, this, 7);
    }

    @Override
    public void writeScreenOpeningData(@NotNull ServerPlayerEntity player,
                                       @NotNull PacketByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    @Override
    public ChestTier getTier() {
        return ChestTier.T4;
    }
}
