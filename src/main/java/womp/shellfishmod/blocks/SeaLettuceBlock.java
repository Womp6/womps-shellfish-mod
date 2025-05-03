package womp.shellfishmod.blocks;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import womp.shellfishmod.blocks.parents.ShellfishPlantBlock;
import womp.shellfishmod.util.config.ShellfishConfig;

public class SeaLettuceBlock extends ShellfishPlantBlock implements BlockEntityProvider, Fertilizable {

    protected static final VoxelShape SHAPE = Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 3.0, 11.0);

    protected static final VoxelShape SHAPE_2D = Block.createCuboidShape(3.0, 0.0, 3.0, 13.0, 12.0, 13.0);

    protected static final VoxelShape LARGE_SHAPE = Block.createCuboidShape(3.0, 0, 3.0, 13.0, 5.0, 13.0);

    protected static final VoxelShape LARGE_SHAPE_2D = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 15.0, 14.0);

    public static final BooleanProperty LARGE = BooleanProperty.of("large");

    public static final BooleanProperty SHOW_3D = BooleanProperty.of("is3d");

    public SeaLettuceBlock(AbstractBlock.Settings settings) {
        super(settings, SHAPE, PlaceType.SOLID_SIDE);
        this.setDefaultState(this.stateManager.getDefaultState().with(LARGE, false).with(SHOW_3D, true));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(LARGE);
        builder.add(SHOW_3D);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return ShellfishConfig.getShellfishGraphics() == 2 ? state.get(LARGE) ? LARGE_SHAPE : SHAPE : state.get(LARGE) ? LARGE_SHAPE_2D : SHAPE_2D;
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        if (fluidState.isIn(FluidTags.WATER) && fluidState.getLevel() == 8) {
            return super.getPlacementState(ctx).with(LARGE, false);
        }
        return null;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos var1, BlockState var2) {
        return new SeaLettuceBlockEntity(var1, var2);
    } 

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return !state.get(LARGE);
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return !state.get(LARGE);
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state.with(LARGE, true), Block.NOTIFY_LISTENERS);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof SeaLettuceBlockEntity) {
            ((SeaLettuceBlockEntity) blockEntity).setLarge(true);
        }
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!world.isClient) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof SeaLettuceBlockEntity) {
                ((SeaLettuceBlockEntity) blockEntity).setLarge(state.get(LARGE));
            }
        }
    }
}