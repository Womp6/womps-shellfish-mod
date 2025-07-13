package womp.shellfishmod.blocks.parents;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;

public class ShellfishLandTallBlock extends TallPlantBlock {

    private final boolean waterOnly;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public ShellfishLandTallBlock(Settings settings, boolean waterOnly) {
        super(settings);
        this.waterOnly = waterOnly;
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        BlockPos up = pos.up(2);
        List<BlockPos> sides = List.of(up.east(), up.west(), up.north(), up.south(), up.up());
        return (floor.isIn(BlockTags.SAND) || super.canPlantOnTop(floor, world, pos) || floor.isOf(Blocks.CLAY) || floor.isOf(Blocks.MUD))
            && world.getFluidState(pos.up(2)).isEmpty() && sides.stream().allMatch(side -> world.getFluidState(side).isEmpty());
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView view, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        BlockPos up = pos.up();
        List<BlockPos> sides = List.of(up.east(), up.west(), up.north(), up.south(), up.up());
        if (!sides.stream().allMatch(side -> world.getFluidState(side).isEmpty())) return Blocks.AIR.getDefaultState();
        if (!state.isAir() || state.get(WATERLOGGED)) {
            view.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, world, view, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(HALF) == DoubleBlockHalf.LOWER && (state.get(WATERLOGGED).booleanValue() || waterOnly)) {
            return Fluids.WATER.getStill(false);
        }
        return Fluids.EMPTY.getDefaultState();
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
        super.appendProperties(builder);
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = super.getPlacementState(ctx);
        if (state == null) return null;

        BlockPos pos = ctx.getBlockPos();
        BlockState stateUp = ctx.getWorld().getBlockState(pos.up());
        FluidState fluidUp = ctx.getWorld().getFluidState(pos.up());
        FluidState fluidDown = ctx.getWorld().getFluidState(pos);

        if ((fluidDown.isIn(FluidTags.WATER) && fluidDown.getLevel() == 8)) {
            if (state.get(HALF) == DoubleBlockHalf.LOWER) return state.with(WATERLOGGED, true);
            if (fluidUp.isEmpty() && stateUp.canReplace(ctx)) return state.with(WATERLOGGED, false);
            return null;
        } else if (waterOnly) return null;
        else {
            if (state.get(HALF) == DoubleBlockHalf.LOWER) return state.with(WATERLOGGED, false);
            if (fluidUp.isEmpty() && stateUp.canReplace(ctx)) return state.with(WATERLOGGED, false);
        }
        return null;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if (state.get(HALF) == DoubleBlockHalf.UPPER) {
            BlockState blockState = world.getBlockState(pos.down());
            return blockState.isOf(this) && blockState.get(HALF) == DoubleBlockHalf.LOWER;
        }
        FluidState fluidState = world.getFluidState(pos);
        return super.canPlaceAt(state, world, pos) && (waterOnly ? fluidState.isIn(FluidTags.WATER) && fluidState.getLevel() == 8 : (fluidState.isIn(FluidTags.WATER) && fluidState.getLevel() == 8) || fluidState.isEmpty()) && world.getFluidState(pos.up()).isEmpty();
    }
}
