package com.github.sib_energy_craft.chests.client.load;

import com.github.sib_energy_craft.chests.block.AbstractExtendedChestBlock;
import com.github.sib_energy_craft.chests.block.entity.AbstractExtendedChestBlockEntity;
import com.github.sib_energy_craft.sec_utils.common.Identified;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;

/**
 * @author sibmaks
 * @since 2023-04-29
 */
@Getter
@AllArgsConstructor
public class ChestGroup {
    private Item blockItem;
    private Identified< ? extends AbstractExtendedChestBlock> block;
    private BlockEntityType<? extends AbstractExtendedChestBlockEntity<?>> entity;
}
