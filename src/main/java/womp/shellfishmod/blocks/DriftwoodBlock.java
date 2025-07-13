package womp.shellfishmod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class DriftwoodBlock extends Block {

    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final BooleanProperty ON_WATER = BooleanProperty.of("on_water");

    public static final VoxelShape SHAPE_NS_LAND = Block.createCuboidShape(2, 0, 1.5, 13, 6, 14.5);
    public static final VoxelShape SHAPE_EW_LAND = Block.createCuboidShape(1.5, 0, 2, 14.5, 6, 14);
    public static final VoxelShape SHAPE_NS_WATER = Block.createCuboidShape(2, -4, 1.5, 13, 2, 14.5);
    public static final VoxelShape SHAPE_EW_WATER = Block.createCuboidShape(1.5, -4, 2, 14.5, 2, 14);
    
    public DriftwoodBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState())
        .with(FACING, Direction.NORTH).with(ON_WATER, false));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (!state.contains(FACING) || !state.contains(ON_WATER)) return SHAPE_NS_LAND;
        Direction dir = state.get(FACING);
        boolean onWater = state.get(ON_WATER);
        switch(dir) {
            case NORTH, SOUTH: return onWater ? SHAPE_NS_WATER : SHAPE_NS_LAND;
            case EAST, WEST: return onWater ? SHAPE_EW_WATER : SHAPE_EW_LAND;
            default: return onWater ? SHAPE_NS_WATER : SHAPE_NS_LAND;
        }
    }
    
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState)this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite()).with(ON_WATER, ctx.getWorld().isWater(ctx.getBlockPos().down()) && !ctx.getWorld().getBlockState(ctx.getBlockPos().down()).isSideSolidFullSquare(ctx.getWorld(), ctx.getBlockPos().down(), Direction.UP));
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (!state.canPlaceAt(world, pos)) return Blocks.AIR.getDefaultState();
        if (world.isWater(pos.down()) && !world.getBlockState(pos.down()).isSideSolidFullSquare(world, pos.down(), Direction.UP)) return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos).with(ON_WATER, true);
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos).with(ON_WATER, false);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return (BlockState)state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, ON_WATER);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        BlockState blockState = world.getBlockState(blockPos);
        return blockState.isSideSolidFullSquare(world, blockPos, Direction.UP) || world.isWater(blockPos);
    }
}
