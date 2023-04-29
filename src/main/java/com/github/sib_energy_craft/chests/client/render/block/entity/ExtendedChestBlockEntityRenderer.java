package com.github.sib_energy_craft.chests.client.render.block.entity;

import com.github.sib_energy_craft.chests.block.AbstractExtendedChestBlock;
import com.github.sib_energy_craft.chests.block.entity.AbstractExtendedChestBlockEntity;
import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.sec_utils.common.Identified;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.DoubleBlockProperties;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LidOpenable;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.LightmapCoordinatesRetriever;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.0.1
 * @author sibmaks
 */
@Environment(EnvType.CLIENT)
public class ExtendedChestBlockEntityRenderer<T extends BlockEntity & LidOpenable> implements BlockEntityRenderer<T> {

    private final Identified<? extends AbstractExtendedChestBlock<?>> block;
    private final ModelPart singleChestLid;
    private final ModelPart singleChestBase;
    private final ModelPart singleChestLatch;

    public ExtendedChestBlockEntityRenderer(@NotNull BlockEntityRendererFactory.Context ctx,
                                            @NotNull Identified<? extends AbstractExtendedChestBlock<?>> block) {
        this.block = block;
        var modelPart = ctx.getLayerModelPart(EntityModelLayers.CHEST);
        this.singleChestBase = modelPart.getChild("bottom");
        this.singleChestLid = modelPart.getChild("lid");
        this.singleChestLatch = modelPart.getChild("lock");
    }

    @Override
    public void render(@NotNull T entity,
                       float tickDelta,
                       @NotNull MatrixStack matrices,
                       @NotNull VertexConsumerProvider vertexConsumers,
                       int light,
                       int overlay) {
        var world = entity.getWorld();
        var worldIsNull = world != null;
        var blockState = worldIsNull ? entity.getCachedState() : block.entity().getDefaultState()
                .with(AbstractExtendedChestBlock.FACING, Direction.SOUTH);
        var block = blockState.getBlock();
        if (!(block instanceof AbstractExtendedChestBlock<?> abstractChestBlock)) {
            return;
        }
        matrices.push();
        float f = blockState.get(AbstractExtendedChestBlock.FACING).asRotation();
        matrices.translate(0.5F, 0.5F, 0.5F);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-f));
        matrices.translate(-0.5F, -0.5F, -0.5F);
        DoubleBlockProperties.PropertySource<? extends AbstractExtendedChestBlockEntity> propertySource;
        if (worldIsNull) {
            propertySource = abstractChestBlock.getBlockEntitySource(blockState, world, entity.getPos(), true);
        } else {
            propertySource = DoubleBlockProperties.PropertyRetriever::getFallback;
        }

        float g = propertySource.apply(AbstractExtendedChestBlock.getAnimationProgressRetriever(entity)).get(tickDelta);
        g = 1.0F - g;
        g = 1.0F - g * g * g;
        int i = propertySource.apply(new LightmapCoordinatesRetriever<>()).applyAsInt(light);
        var vertexConsumer = getVertexConsumer(vertexConsumers, this.block.identifier());
        this.render(matrices, vertexConsumer, this.singleChestLid, this.singleChestLatch, this.singleChestBase, g, i, overlay);
        matrices.pop();
    }

    @NotNull
    private static VertexConsumer getVertexConsumer(@NotNull VertexConsumerProvider vertexConsumers,
                                                    @NotNull Identifier identifier) {
        identifier = Identifiers.of("textures/entity/extended_chest/%s.png".formatted(identifier.getPath()));
        return vertexConsumers.getBuffer(RenderLayer.getEntityCutout(identifier));
    }

    private void render(@NotNull MatrixStack matrices,
                        @NotNull VertexConsumer vertices,
                        @NotNull ModelPart lid,
                        @NotNull ModelPart latch,
                        @NotNull ModelPart base,
                        float openFactor,
                        int light,
                        int overlay) {
        lid.pitch = -(openFactor * 1.5707964F);
        latch.pitch = lid.pitch;
        lid.render(matrices, vertices, light, overlay);
        latch.render(matrices, vertices, light, overlay);
        base.render(matrices, vertices, light, overlay);
    }

}
