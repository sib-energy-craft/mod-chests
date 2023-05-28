package com.github.sib_energy_craft.chests.block.entity;

import com.github.sib_energy_craft.chests.block.AbstractExtendedChestBlock;
import com.github.sib_energy_craft.pipes.api.ItemConsumer;
import com.github.sib_energy_craft.pipes.api.ItemSupplier;
import com.github.sib_energy_craft.pipes.utils.PipeUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public abstract class AbstractExtendedChestBlockEntity<T extends AbstractExtendedChestBlock>
        extends LootableContainerBlockEntity
        implements LidOpenable,
        ItemSupplier, ItemConsumer {
    private DefaultedList<ItemStack> inventory;
    private final ViewerCountManager stateManager;
    private final ChestLidAnimator lidAnimator;
    private final String containerName;

    protected AbstractExtendedChestBlockEntity(@NotNull BlockEntityType<?> blockEntityType,
                                               @NotNull BlockPos blockPos,
                                               @NotNull BlockState blockState,
                                               @NotNull String containerName,
                                               @NotNull T block) {
        super(blockEntityType, blockPos, blockState);
        this.inventory = DefaultedList.ofSize(block.getSize(), ItemStack.EMPTY);
        this.stateManager = new ChestViewerCountManager<>(this);
        this.lidAnimator = new ChestLidAnimator();
        this.containerName = containerName;
    }

    @Override
    public int size() {
        return inventory.size();
    }

    @NotNull
    @Override
    protected Text getContainerName() {
        return Text.translatable(containerName);
    }

    @Override
    public void readNbt(@NotNull NbtCompound nbt) {
        super.readNbt(nbt);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        if (!this.deserializeLootTable(nbt)) {
            Inventories.readNbt(nbt, this.inventory);
        }
    }

    @Override
    protected void writeNbt(@NotNull NbtCompound nbt) {
        super.writeNbt(nbt);
        if (!this.serializeLootTable(nbt)) {
            Inventories.writeNbt(nbt, this.inventory);
        }

    }

    public static void clientTick(@NotNull World world,
                                  @NotNull BlockPos pos,
                                  @NotNull BlockState state,
                                  @NotNull AbstractExtendedChestBlockEntity<?> blockEntity) {
        blockEntity.lidAnimator.step();
    }

    static void playSound(@NotNull World world,
                          @NotNull BlockPos pos,
                          @NotNull BlockState state,
                          @NotNull SoundEvent soundEvent) {
        double d = pos.getX() + 0.5;
        double e = pos.getY() + 0.5;
        double f = pos.getZ() + 0.5;

        float pitch = world.random.nextFloat() * 0.1F + 0.9F;
        world.playSound(null, d, e, f, soundEvent, SoundCategory.BLOCKS, 0.5F, pitch);
    }

    @Override
    public boolean onSyncedBlockEvent(int type, int data) {
        if (type == 1) {
            this.lidAnimator.setOpen(data > 0);
            return true;
        } else {
            return super.onSyncedBlockEvent(type, data);
        }
    }

    @Override
    public void onOpen(@NotNull PlayerEntity player) {
        if (!this.removed && !player.isSpectator()) {
            this.stateManager.openContainer(player, world, pos, getCachedState());
        }
    }

    @Override
    public void onClose(@NotNull PlayerEntity player) {
        if (!this.removed && !player.isSpectator()) {
            this.stateManager.closeContainer(player, world, pos, getCachedState());
        }
    }

    @NotNull
    @Override
    protected DefaultedList<ItemStack> getInvStackList() {
        return this.inventory;
    }

    @Override
    protected void setInvStackList(@NotNull DefaultedList<ItemStack> list) {
        this.inventory = list;
    }

    @Override
    public float getAnimationProgress(float tickDelta) {
        return this.lidAnimator.getProgress(tickDelta);
    }

    public void onScheduledTick() {
        if (!this.removed) {
            this.stateManager.updateViewerCount(world, pos, getCachedState());
        }
    }

    protected void onViewerCountUpdate(@NotNull World world,
                                       @NotNull BlockPos pos,
                                       @NotNull BlockState state,
                                       int oldViewerCount,
                                       int newViewerCount) {
        var block = state.getBlock();
        world.addSyncedBlockEvent(pos, block, 1, newViewerCount);
    }

    @Override
    public boolean canConsume(@NotNull ItemStack itemStack, @NotNull Direction direction) {
        return PipeUtils.hasSpaceFor(this, itemStack);
    }

    @Override
    public @NotNull ItemStack consume(@NotNull ItemStack itemStack, @NotNull Direction direction) {
        return PipeUtils.consume(this, itemStack);
    }

    @Override
    public @NotNull List<ItemStack> canSupply(@NotNull Direction direction) {
        return inventory.stream()
                .filter(it -> !it.isEmpty())
                .map(ItemStack::copy)
                .collect(Collectors.toList());
    }

    @Override
    public boolean supply(@NotNull ItemStack requested, @NotNull Direction direction) {
        return PipeUtils.supply(this, requested);
    }

    @Override
    public void returnStack(@NotNull ItemStack requested, @NotNull Direction direction) {
        PipeUtils.consume(this, requested);
    }
}
