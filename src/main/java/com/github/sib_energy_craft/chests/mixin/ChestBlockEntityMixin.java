package com.github.sib_energy_craft.chests.mixin;

import com.github.sib_energy_craft.chests.ChestTier;
import com.github.sib_energy_craft.chests.UpgradeableChest;
import net.minecraft.block.entity.ChestBlockEntity;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @since 0.0.8
 * @author sibmaks
 */
@Mixin(ChestBlockEntity.class)
public abstract class ChestBlockEntityMixin implements UpgradeableChest {

    @Override
    public ChestTier getTier() {
        return ChestTier.T0;
    }

}
