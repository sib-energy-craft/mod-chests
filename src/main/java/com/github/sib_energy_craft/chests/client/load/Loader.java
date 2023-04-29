package com.github.sib_energy_craft.chests.client.load;

import com.github.sib_energy_craft.chests.block.entity.AbstractExtendedChestBlockEntity;
import com.github.sib_energy_craft.chests.client.render.block.entity.ExtendedChestBlockEntityRenderer;
import com.github.sib_energy_craft.chests.load.Blocks;
import com.github.sib_energy_craft.chests.load.Entities;
import com.github.sib_energy_craft.chests.load.Items;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.minecraft.client.render.block.entity.BlockEntityRendererFactories.register;


/**
 * @author sibmaks
 * @since 0.0.1
 */
public class Loader implements ClientModInitializer {
    private static final List<ChestGroup> CHEST_GROUPS = List.of(
            new ChestGroup(Items.COPPER_CHEST, Blocks.COPPER_CHEST, Entities.COPPER_CHEST),
            new ChestGroup(Items.IRON_CHEST, Blocks.IRON_CHEST, Entities.IRON_CHEST),
            new ChestGroup(Items.GOLD_CHEST, Blocks.GOLD_CHEST, Entities.GOLD_CHEST),
            new ChestGroup(Items.DIAMOND_CHEST, Blocks.DIAMOND_CHEST, Entities.DIAMOND_CHEST)
    );

    @Override
    public void onInitializeClient() {
        for (var chestGroup : CHEST_GROUPS) {
            var blockItem = chestGroup.getBlockItem();
            var entity = chestGroup.getEntity();
            var block = chestGroup.getBlock();
            register(entity, ctx -> new ExtendedChestBlockEntityRenderer<>(ctx, block));

            var renderEntity = entity.instantiate(BlockPos.ORIGIN, block.entity().getDefaultState());
            BuiltinItemRendererRegistry.INSTANCE.register(blockItem, getDynamicItemRenderer(renderEntity));
        }
    }

    @NotNull
    private static BuiltinItemRendererRegistry.DynamicItemRenderer getDynamicItemRenderer(
            @Nullable AbstractExtendedChestBlockEntity renderEntity) {
        return (itemStack, transform, stack, source, light, overlay) -> {

            var instance = MinecraftClient.getInstance();
            var blockEntityRenderDispatcher = instance.getBlockEntityRenderDispatcher();
            blockEntityRenderDispatcher.renderEntity(renderEntity, stack, source, light, overlay);
        };
    }
}
