package com.github.sib_energy_craft.chests.block;

import com.github.sib_energy_craft.chests.block.entity.AbstractExtendedChestBlockEntity;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import lombok.Getter;
import net.minecraft.block.*;
import net.minecraft.block.DoubleBlockProperties.PropertyRetriever;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LidOpenable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.DoubleInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiPredicate;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public abstract class AbstractExtendedChestBlock extends BlockWithEntity
        implements Waterloggable {
    private static final DoubleBlockProperties.PropertyRetriever<AbstractExtendedChestBlockEntity<?>, Optional<Inventory>>
            INVENTORY_RETRIEVER = new DoubleBlockProperties.PropertyRetriever<>() {
        @NotNull
        @Override
        public Optional<Inventory> getFromBoth(@NotNull AbstractExtendedChestBlockEntity blockEntityA,
                                               @NotNull AbstractExtendedChestBlockEntity blockEntityB) {
            return Optional.of(new DoubleInventory(blockEntityA, blockEntityB));
        }

        @NotNull
        @Override
        public Optional<Inventory> getFrom(@NotNull AbstractExtendedChestBlockEntity chestBlockEntity) {
            return Optional.of(chestBlockEntity);
        }

        @NotNull
        @Override
        public Optional<Inventory> getFallback() {
            return Optional.empty();
        }
    };;


    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    protected static final VoxelShape SINGLE_SHAPE = Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 14.0, 15.0);

    @Getter
    protected final int size;

    protected AbstractExtendedChestBlock(@NotNull AbstractBlock.Settings settings,
                                         int size) {
        super(settings);
        this.size = size;
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(FACING, Direction.NORTH)
                .with(WATERLOGGED, false));
    }

    @NotNull
    @Override
    public BlockRenderType getRenderType(@NotNull BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @NotNull
    @Override
    public BlockState getStateForNeighborUpdate(@NotNull BlockState state,
                                                @NotNull Direction direction,
                                                @NotNull BlockState neighborState,
                                                @NotNull WorldAccess world,
                                                @NotNull BlockPos pos,
                                                @NotNull BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @NotNull
    @Override
    public VoxelShape getOutlineShape(@NotNull BlockState state,
                                      @NotNull BlockView world,
                                      @NotNull BlockPos pos,
                                      @NotNull ShapeContext context) {
        return SINGLE_SHAPE;
    }

    @NotNull
    @Override
    public BlockState getPlacementState(@NotNull ItemPlacementContext ctx) {
        var horizontalPlayerFacing = ctx.getHorizontalPlayerFacing();
        var direction = horizontalPlayerFacing.getOpposite();
        var world = ctx.getWorld();
        var fluidState = world.getFluidState(ctx.getBlockPos());
        boolean cancelInteraction = ctx.shouldCancelInteraction();
        var side = ctx.getSide();
        if (side.getAxis().isHorizontal() && cancelInteraction) {
            var neighborChestDirection = this.getNeighborChestDirection(ctx, side.getOpposite());
            if (neighborChestDirection != null && neighborChestDirection.getAxis() != side.getAxis()) {
                direction = neighborChestDirection;
            }
        }

        return this.getDefaultState()
                .with(FACING, direction)
                .with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    @NotNull
    @Override
    public FluidState getFluidState(@NotNull BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Nullable
    private Direction getNeighborChestDirection(@NotNull ItemPlacementContext ctx,
                                                @NotNull Direction dir) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos().offset(dir));
        return blockState.isOf(this) ? blockState.get(FACING) : null;
    }

    @Override
    public void onPlaced(@NotNull World world,
                         @NotNull BlockPos pos,
                         @NotNull BlockState state,
                         @Nullable LivingEntity placer,
                         @NotNull ItemStack itemStack) {
        if (!itemStack.hasCustomName()) {
            return;
        }
        var blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof AbstractExtendedChestBlockEntity<?> chestBlockEntity) {
            chestBlockEntity.setCustomName(itemStack.getName());
        }
    }

    @Override
    public void onStateReplaced(@NotNull BlockState state,
                                @NotNull World world,
                                @NotNull BlockPos pos,
                                @NotNull BlockState newState,
                                boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            var blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof Inventory inventory) {
                ItemScatterer.spawn(world, pos, inventory);
                world.updateComparators(pos, this);
            }

            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @NotNull
    @Override
    public ActionResult onUse(@NotNull BlockState state,
                              @NotNull World world,
                              @NotNull BlockPos pos,
                              @NotNull PlayerEntity player,
                              @NotNull Hand hand,
                              @NotNull BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        } else {
            var namedScreenHandlerFactory = createScreenHandlerFactory(state, world, pos);
            if (namedScreenHandlerFactory != null) {
                player.openHandledScreen(namedScreenHandlerFactory);
                player.incrementStat(this.getOpenStat());
                PiglinBrain.onGuardedBlockInteracted(player, true);
            }

            return ActionResult.CONSUME;
        }
    }

    @NotNull
    protected Stat<Identifier> getOpenStat() {
        return Stats.CUSTOM.getOrCreateStat(Stats.OPEN_CHEST);
    }

    @Nullable
    public static Inventory getInventory(@NotNull AbstractExtendedChestBlock block,
                                         @NotNull BlockState state,
                                         @NotNull World world,
                                         @NotNull BlockPos pos,
                                         boolean ignoreBlocked) {
        var blockEntitySource = block.getBlockEntitySource(state, world, pos, ignoreBlocked);
        var optionalInventory = blockEntitySource.apply(INVENTORY_RETRIEVER);
        return optionalInventory.orElse(null);
    }

    @Nullable
    @Override
    public NamedScreenHandlerFactory createScreenHandlerFactory(@NotNull BlockState state,
                                                                @NotNull World world,
                                                                @NotNull BlockPos pos) {
        var blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof AbstractExtendedChestBlockEntity<?> chestBlockEntity) {
            return chestBlockEntity;
        }
        return null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull World world,
                                                                  @NotNull BlockState state,
                                                                  @NotNull BlockEntityType<T> type) {
        return world.isClient ? checkType(type, getBlockEntityType(), AbstractExtendedChestBlockEntity::clientTick) : null;
    }

    public static boolean isChestBlocked(@NotNull WorldAccess world,
                                         @NotNull BlockPos pos) {
        return hasBlockOnTop(world, pos) || hasCatOnTop(world, pos);
    }

    private static boolean hasBlockOnTop(@NotNull BlockView world,
                                         @NotNull BlockPos pos) {
        var blockPos = pos.up();
        return world.getBlockState(blockPos).isSolidBlock(world, blockPos);
    }

    private static boolean hasCatOnTop(@NotNull WorldAccess world,
                                       @NotNull BlockPos pos) {
        var box = new Box(pos.getX(), pos.getY() + 1, pos.getZ(), pos.getX() + 1, pos.getY() + 2, pos.getZ() + 1);
        var list = world.getNonSpectatingEntities(CatEntity.class, box);
        if (!list.isEmpty()) {
            for (var catEntity : list) {
                if (catEntity.isInSittingPose()) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean hasComparatorOutput(@NotNull BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(@NotNull BlockState state,
                                   @NotNull World world,
                                   @NotNull BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput(getInventory(this, state, world, pos, false));
    }

    @NotNull
    @Override
    public BlockState rotate(@NotNull BlockState state,
                             @NotNull BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @NotNull
    @Override
    public BlockState mirror(@NotNull BlockState state,
                             @NotNull BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(@NotNull StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public boolean canPathfindThrough(@NotNull BlockState state,
                                      @NotNull BlockView world,
                                      @NotNull BlockPos pos,
                                      @NotNull NavigationType type) {
        return false;
    }

    @Override
    public void scheduledTick(@NotNull BlockState state,
                              @NotNull ServerWorld world,
                              @NotNull BlockPos pos,
                              @NotNull Random random) {
        var blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof AbstractExtendedChestBlockEntity<?> extendedChestBlockEntity) {
            extendedChestBlockEntity.onScheduledTick();
        }
    }

    /**
     * Get block entity type
     *
     * @return block entity type
     */
    abstract protected BlockEntityType<? extends AbstractExtendedChestBlockEntity<?>> getBlockEntityType();

    @NotNull
    public DoubleBlockProperties.PropertySource<? extends AbstractExtendedChestBlockEntity<?>> getBlockEntitySource(
            @NotNull BlockState state,
            @NotNull World world,
            @NotNull BlockPos pos,
            boolean ignoreBlocked) {
        BiPredicate<WorldAccess, BlockPos> biPredicate;
        if (ignoreBlocked) {
            biPredicate = (worldx, posx) -> false;
        } else {
            biPredicate = AbstractExtendedChestBlock::isChestBlocked;
        }
        var blockEntityType = getBlockEntityType();
        return DoubleBlockProperties.toPropertySource(blockEntityType, (it) -> DoubleBlockProperties.Type.SINGLE,
                ChestBlock::getFacing, FACING, state, world, pos,
                biPredicate);
    }

    @NotNull
    public static PropertyRetriever<AbstractExtendedChestBlockEntity<?>, Float2FloatFunction> getAnimationProgressRetriever(
            @NotNull LidOpenable progress) {
        return new DoubleBlockProperties.PropertyRetriever<>() {
            @NotNull
            @Override
            public Float2FloatFunction getFromBoth(@NotNull AbstractExtendedChestBlockEntity entityA,
                                                   @NotNull AbstractExtendedChestBlockEntity entityB) {
                return (tickDelta) -> Math.max(entityA.getAnimationProgress(tickDelta),
                        entityB.getAnimationProgress(tickDelta));
            }

            @NotNull
            @Override
            public Float2FloatFunction getFrom(@NotNull AbstractExtendedChestBlockEntity chestBlockEntity) {
                Objects.requireNonNull(chestBlockEntity);
                return chestBlockEntity::getAnimationProgress;
            }

            @NotNull
            @Override
            public Float2FloatFunction getFallback() {
                Objects.requireNonNull(progress);
                return progress::getAnimationProgress;
            }
        };
    }
}
