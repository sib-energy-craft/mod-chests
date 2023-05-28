package com.github.sib_energy_craft.chests.block;

import com.github.sib_energy_craft.chests.block.entity.AbstractExtendedChestBlockEntity;
import com.github.sib_energy_craft.chests.block.entity.SilverExtendedChestBlockEntity;
import com.github.sib_energy_craft.chests.load.Entities;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public class SilverExtendedChestBlock extends AbstractExtendedChestBlock {
     public SilverExtendedChestBlock(@NotNull AbstractBlock.Settings settings) {
        super(settings, 54);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SilverExtendedChestBlockEntity(pos, state, this);
    }
    @Override
    protected BlockEntityType<? extends AbstractExtendedChestBlockEntity<?>> getBlockEntityType() {
        return Entities.SILVER_CHEST;
    }
}
