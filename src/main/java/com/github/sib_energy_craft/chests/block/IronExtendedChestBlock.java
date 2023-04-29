package com.github.sib_energy_craft.chests.block;

import com.github.sib_energy_craft.chests.block.entity.IronExtendedChestBlockEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public class IronExtendedChestBlock extends AbstractExtendedChestBlock<IronExtendedChestBlockEntity> implements Waterloggable {
     public IronExtendedChestBlock(@NotNull AbstractBlock.Settings settings,
                                   @NotNull Supplier<BlockEntityType<? extends IronExtendedChestBlockEntity>> supplier) {
        super(settings, supplier);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new IronExtendedChestBlockEntity(pos, state);
    }
}
