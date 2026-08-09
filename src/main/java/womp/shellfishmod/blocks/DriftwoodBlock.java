package womp.shellfishmod.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DriftwoodBlock extends Block {

    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty ON_WATER = BooleanProperty.create("on_water");

    public static final VoxelShape SHAPE_NS_LAND = Block.box(2, 0, 1.5, 13, 6, 14.5);
    public static final VoxelShape SHAPE_EW_LAND = Block.box(1.5, 0, 2, 14.5, 6, 14);
    public static final VoxelShape SHAPE_NS_WATER = Block.box(2, -4, 1.5, 13, 2, 14.5);
    public static final VoxelShape SHAPE_EW_WATER = Block.box(1.5, -4, 2, 14.5, 2, 14);
    
    public DriftwoodBlock(Properties settings) {
        super(settings);
        this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any())
        .setValue(FACING, Direction.NORTH).setValue(ON_WATER, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (!state.hasProperty(FACING) || !state.hasProperty(ON_WATER)) return SHAPE_NS_LAND;
        Direction dir = state.getValue(FACING);
        boolean onWater = state.getValue(ON_WATER);
        switch(dir) {
            case NORTH, SOUTH: return onWater ? SHAPE_NS_WATER : SHAPE_NS_LAND;
            case EAST, WEST: return onWater ? SHAPE_EW_WATER : SHAPE_EW_LAND;
            default: return onWater ? SHAPE_NS_WATER : SHAPE_NS_LAND;
        }
    }
    
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return (BlockState)this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite()).setValue(ON_WATER, ctx.getLevel().isWaterAt(ctx.getClickedPos().below()) && !ctx.getLevel().getBlockState(ctx.getClickedPos().below()).isFaceSturdy(ctx.getLevel(), ctx.getClickedPos().below(), Direction.UP));
    }

    @Override
    public BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess view, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if (!state.canSurvive(world, pos)) return Blocks.AIR.defaultBlockState();
        if (world.isWaterAt(pos.below()) && !world.getBlockState(pos.below()).isFaceSturdy(world, pos.below(), Direction.UP)) return super.updateShape(state, world, view, pos, direction, neighborPos, neighborState, random).setValue(ON_WATER, true);
        return super.updateShape(state, world, view, pos, direction, neighborPos, neighborState, random).setValue(ON_WATER, false);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return (BlockState)state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, ON_WATER);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        BlockPos blockPos = pos.below();
        BlockState blockState = world.getBlockState(blockPos);
        return blockState.isFaceSturdy(world, blockPos, Direction.UP) || world.isWaterAt(blockPos);
    }
}
