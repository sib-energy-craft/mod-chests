package com.github.sib_energy_craft.chests.block;

import com.github.sib_energy_craft.chests.block.entity.GoldExtendedChestBlockEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public class GoldExtendedChestBlock extends AbstractExtendedChestBlock<GoldExtendedChestBlockEntity> {
     public GoldExtendedChestBlock(@NotNull AbstractBlock.Settings settings,
                                   @NotNull Supplier<BlockEntityType<? extends GoldExtendedChestBlockEntity>> supplier) {
        super(settings, supplier);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GoldExtendedChestBlockEntity(pos, state);
    }
}
