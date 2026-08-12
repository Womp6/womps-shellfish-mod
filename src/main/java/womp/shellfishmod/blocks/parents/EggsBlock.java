package womp.shellfishmod.blocks.parents;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.VisibleForTesting;
import womp.shellfishmod.entity.parents.ShellfishEntity;
import womp.shellfishmod.registry.ShellfishItems;

import java.util.function.Supplier;

public class EggsBlock extends Block implements SimpleWaterloggedBlock {

    private final SoundEvent hatchSound;
    private final Supplier<? extends EntityType<? extends ShellfishEntity<?>>> shellfish;

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty VARIANT1 = IntegerProperty.create("variant1", 0, 50);
    public static final IntegerProperty VARIANT2 = IntegerProperty.create("variant2", 0, 50);
    protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 1.5, 16.0);
    private static int minHatchTime = 3600;
    private static int maxHatchTime = 12000;

    public EggsBlock(BlockBehaviour.Properties settings, Supplier<? extends EntityType<? extends ShellfishEntity<?>>> shellfish, Supplier<? extends SoundEvent> hatchSound) {
        super(settings);
        this.hatchSound = hatchSound.get();
        this.shellfish = shellfish;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        stateManager.add(BlockStateProperties.WATERLOGGED).add(VARIANT1).add(VARIANT2);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState()
                .setValue(WATERLOGGED, ctx.getLevel().getFluidState(ctx.getClickedPos()).is(Fluids.WATER))
                .setValue(VARIANT1, 0).setValue(VARIANT2, 0);
    }

    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return EggsBlock.canLayAt(world, pos.below());
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean notify) {
        world.scheduleTick(pos, this, EggsBlock.getHatchTime(world.getRandom()));
    }

    private static int getHatchTime(RandomSource random) {
        return random.nextInt(minHatchTime, maxHatchTime);
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess view, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if (!this.canSurvive(state, world, pos)) {
            return Blocks.WATER.defaultBlockState();
        }
        if (state.getValue(WATERLOGGED)) {
            view.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }
        return super.updateShape(state, world, view, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (!this.canSurvive(state, world, pos)) {
            this.breakWithoutDrop(world, pos);
            return;
        }
        this.hatch(world, pos, random);
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity, InsideBlockEffectApplier applier, boolean bl) {
        if (entity.getType().equals(EntityTypes.FALLING_BLOCK)) {
            this.breakWithoutDrop(world, pos);
        }
    }

    private static boolean canLayAt(BlockGetter world, BlockPos pos) {
        FluidState blockState = world.getFluidState(pos);
        FluidState fluidState2 = world.getFluidState(pos.above());
        return blockState.getType() == Fluids.EMPTY && fluidState2.getType() == Fluids.WATER;
    }

    private void hatch(ServerLevel world, BlockPos pos, RandomSource random) {
        int v1 = -1, v2 = -1;
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof EggsBlock) {
            v1 = state.getValue(VARIANT1) - 1;
            v2 = state.getValue(VARIANT2) - 1;
        }
        this.breakWithoutDrop(world, pos);
        world.playSound(null, pos, hatchSound, SoundSource.BLOCKS, 1.0f, 1.0f);
        this.spawnShellfish(world, pos, random, v1, v2);
    }

    private void breakWithoutDrop(Level world, BlockPos pos) {
        world.destroyBlock(pos, false);
    }

    private void spawnShellfish(ServerLevel world, BlockPos pos, RandomSource random, int v1, int v2) {
        int i = random.nextInt(2, 5);
        for (int j = 1; j <= i; ++j) {
            ShellfishEntity<?> child = shellfish.get().create(world, EntitySpawnReason.BREEDING);
            if (child == null) continue;
            child.setAge(-24000);
            double d = (double)pos.getX() + this.getSpawnOffset(random);
            double e = (double)pos.getZ() + this.getSpawnOffset(random);
            int k = random.nextInt(1, 361);
            child.snapTo(d, (double)pos.getY() - 0, e, k, 0.0f);
            child.setPersistenceRequired();
            if (v1 >= 0 && v2 >= 0 && v1 < child.getMaxVariants() && v2 < child.getMaxVariants()) {
                child.setVariantNumerical(random.nextIntBetweenInclusive(0, 1) == 1 ? v1 : v2);
            } else child.setVariantNumerical(random.nextInt(child.getMaxVariants()));
            world.addFreshEntity(child);
        }
    }

    private double getSpawnOffset(RandomSource random) {
        double d = 0.1875;
        return Mth.clamp(random.nextDouble(), d, 1.0 - d);
    }

    @VisibleForTesting
    public static void setHatchTimeRange(int min, int max) {
        minHatchTime = min;
        maxHatchTime = max;
    }

    @VisibleForTesting
    public static void resetHatchTimeRange() {
        minHatchTime = 3600;
        maxHatchTime = 12000;
    }

    @Override
    public @NotNull InteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if(stack.is(Items.BUCKET)) {
            ItemStack itemStack = new ItemStack(ShellfishItems.CAVIAR_BUCKET.get());
            ItemStack itemStack2 = ItemUtils.createFilledResult(stack, player, itemStack, false);
            player.setItemInHand(hand, itemStack2);
            world.playSound(null, pos.getX() + 0.5d, pos.getY(), pos.getZ() + 0.5d, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1, 1);
            world.setBlock(pos, (state.getValue(WATERLOGGED) ? Blocks.WATER : Blocks.AIR).defaultBlockState(), 3);
            return InteractionResult.SUCCESS;
        }
        return super.useItemOn(stack, state, world, pos, player, hand, hit);
    }
}
