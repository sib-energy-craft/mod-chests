package com.github.sib_energy_craft.chests.item;

import com.github.sib_energy_craft.chests.block.AbstractExtendedChestBlock;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;

/**
 * @author sibmaks
 * @since 0.0.8
 */
public class ChestItem<T extends AbstractExtendedChestBlock> extends BlockItem {
    private final T block;

    public ChestItem(@NotNull T block, @NotNull Settings settings) {
        super(block, settings);
        this.block = block;
    }

    @Override
    public void appendTooltip(@NotNull ItemStack stack,
                              @Nullable World world,
                              @NotNull List<Text> tooltip,
                              @NotNull TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("attribute.name.sib_energy_craft.size", block.getSize())
                .setStyle(Style.EMPTY.withColor(Color.GRAY.getRGB())));
    }
}
