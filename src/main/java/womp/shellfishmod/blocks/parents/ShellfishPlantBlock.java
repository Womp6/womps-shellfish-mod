package womp.shellfishmod.blocks.parents;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ShellfishPlantBlock extends BushBlock implements LiquidBlockContainer {

    protected final VoxelShape shape;
    protected final PlaceType placeType;

    public ShellfishPlantBlock(Properties settings, VoxelShape shape, PlaceType placeType) {
        super(settings);
        this.shape = shape;
        this.placeType = placeType;
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return shape;
    }

    @Override
    protected boolean mayPlaceOn(@NotNull BlockState floor, BlockGetter world, BlockPos pos) {
        BlockState block = world.getBlockState(pos.below());
        FluidState fluid = world.getFluidState(pos);
        if (placeType.equals(PlaceType.MUD_SAND)) return (block.getBlock().equals(Blocks.SAND) | block.getBlock().equals(Blocks.MUD)) && fluid.getType().equals(Fluids.WATER);
        else if (placeType.equals(PlaceType.SOLID_SIDE)) return block.isFaceSturdy(world, pos, Direction.UP) && !block.is(Blocks.MAGMA_BLOCK);
        return super.mayPlaceOn(floor, world, pos);
    }

    @Override
    public boolean canSurvive(@NotNull BlockState floor, LevelReader world, BlockPos pos) {
        BlockState block = world.getBlockState(pos.below());
        FluidState fluid = world.getFluidState(pos);
        if (placeType.equals(PlaceType.MUD_SAND)) return (block.getBlock().equals(Blocks.SAND) | block.getBlock().equals(Blocks.MUD)) && fluid.getType() == Fluids.WATER;
        else if (placeType.equals(PlaceType.SOLID_SIDE)) return block.isFaceSturdy(world, pos, Direction.UP) && !block.is(Blocks.MAGMA_BLOCK);
        return super.canSurvive(floor, world, pos);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
        if (fluidState.is(FluidTags.WATER) && fluidState.getAmount() == 8) {
            return super.getStateForPlacement(ctx);
        }
        return null;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        BlockState blockState = super.updateShape(state, direction, neighborState, world, pos, neighborPos);
        if (!blockState.isAir()) {
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }
        return blockState;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return Fluids.WATER.getSource(false);
    }

    @Override
    public boolean canPlaceLiquid(BlockGetter world, BlockPos pos, BlockState state, Fluid fluid) {
        return false;
    }

    @Override
    public boolean placeLiquid(LevelAccessor world, BlockPos pos, BlockState state, FluidState fluidState) {
        return false;
    }

    public static enum PlaceType {
        MUD_SAND,
        SOLID_SIDE
    }
}
