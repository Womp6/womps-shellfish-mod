package womp.shellfishmod.blocks.parents;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ShellfishLandTallBlock extends DoublePlantBlock {

    private final boolean waterOnly;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public ShellfishLandTallBlock(Properties settings, boolean waterOnly) {
        super(settings);
        this.waterOnly = waterOnly;
    }

    @Override
    protected boolean mayPlaceOn(BlockState floor, BlockGetter world, BlockPos pos) {
        BlockPos up = pos.above(2);
        List<BlockPos> sides = List.of(up.east(), up.west(), up.north(), up.south(), up.above());
        return (floor.is(BlockTags.SAND) || super.mayPlaceOn(floor, world, pos) || floor.is(Blocks.CLAY) || floor.is(Blocks.MUD))
                && world.getFluidState(pos.above(2)).isEmpty() && sides.stream().allMatch(side -> world.getFluidState(side).isEmpty());
    }

    @Override
    public BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess view, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        BlockPos up = pos.above();
        List<BlockPos> sides = List.of(up.east(), up.west(), up.north(), up.south(), up.above());
        if (!sides.stream().allMatch(side -> world.getFluidState(side).isEmpty())) return Blocks.AIR.defaultBlockState();
        if (!state.isAir() || state.getValue(WATERLOGGED)) {
            view.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }
        return super.updateShape(state, world, view, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.getValue(HALF) == DoubleBlockHalf.LOWER && (state.getValue(WATERLOGGED) || waterOnly)) {
            return Fluids.WATER.getSource(false);
        }
        return Fluids.EMPTY.defaultFluidState();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState state = super.getStateForPlacement(ctx);
        if (state == null) return null;

        BlockPos pos = ctx.getClickedPos();
        BlockState stateUp = ctx.getLevel().getBlockState(pos.above());
        FluidState fluidUp = ctx.getLevel().getFluidState(pos.above());
        FluidState fluidDown = ctx.getLevel().getFluidState(pos);

        if ((fluidDown.is(FluidTags.WATER) && fluidDown.getAmount() == 8)) {
            if (state.getValue(HALF) == DoubleBlockHalf.LOWER) return state.setValue(WATERLOGGED, true);
            if (fluidUp.isEmpty() && stateUp.canBeReplaced(ctx)) return state.setValue(WATERLOGGED, false);
            return null;
        } else if (waterOnly) return null;
        else {
            if (state.getValue(HALF) == DoubleBlockHalf.LOWER) return state.setValue(WATERLOGGED, false);
            if (fluidUp.isEmpty() && stateUp.canBeReplaced(ctx)) return state.setValue(WATERLOGGED, false);
        }
        return null;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            BlockState blockState = world.getBlockState(pos.below());
            return blockState.is(this) && blockState.getValue(HALF) == DoubleBlockHalf.LOWER;
        }
        FluidState fluidState = world.getFluidState(pos);
        return super.canSurvive(state, world, pos) && (waterOnly ? fluidState.is(FluidTags.WATER) && fluidState.getAmount() == 8 : (fluidState.is(FluidTags.WATER) && fluidState.getAmount() == 8) || fluidState.isEmpty()) && world.getFluidState(pos.above()).isEmpty();
    }
}
