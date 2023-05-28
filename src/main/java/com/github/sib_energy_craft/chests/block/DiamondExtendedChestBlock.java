package com.github.sib_energy_craft.chests.block;

import com.github.sib_energy_craft.chests.block.entity.AbstractExtendedChestBlockEntity;
import com.github.sib_energy_craft.chests.block.entity.DiamondExtendedChestBlockEntity;
import com.github.sib_energy_craft.chests.load.Entities;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public class DiamondExtendedChestBlock extends AbstractExtendedChestBlock implements Waterloggable {
     public DiamondExtendedChestBlock(@NotNull AbstractBlock.Settings settings) {
        super(settings, 63);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DiamondExtendedChestBlockEntity(pos, state, this);
    }

    @Override
    protected BlockEntityType<? extends AbstractExtendedChestBlockEntity<?>> getBlockEntityType() {
        return Entities.DIAMOND_CHEST;
    }
}
