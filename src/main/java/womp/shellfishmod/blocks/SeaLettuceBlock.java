package womp.shellfishmod.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import womp.shellfishmod.blocks.parents.ShellfishPlantBlock;
import womp.shellfishmod.util.config.ShellfishConfig;

public class SeaLettuceBlock extends ShellfishPlantBlock implements EntityBlock, BonemealableBlock {

    protected static final VoxelShape SHAPE = Block.box(5.0, 0.0, 5.0, 11.0, 3.0, 11.0);
    protected static final VoxelShape SHAPE_2D = Block.box(3.0, 0.0, 3.0, 13.0, 12.0, 13.0);
    protected static final VoxelShape LARGE_SHAPE = Block.box(3.0, 0, 3.0, 13.0, 5.0, 13.0);
    protected static final VoxelShape LARGE_SHAPE_2D = Block.box(2.0, 0.0, 2.0, 14.0, 15.0, 14.0);

    public static final BooleanProperty LARGE = BooleanProperty.create("large");
    public static final BooleanProperty SHOW_3D = BooleanProperty.create("is3d");

    public SeaLettuceBlock(BlockBehaviour.Properties settings) {
        super(settings, SHAPE, PlaceType.SOLID_SIDE, simpleCodec(SeaLettuceBlock::new));
        this.registerDefaultState(this.stateDefinition.any().setValue(LARGE, false).setValue(SHOW_3D, true));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LARGE);
        builder.add(SHOW_3D);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return ShellfishConfig.getShellfishGraphics() == 2 ? state.getValue(LARGE) ? LARGE_SHAPE : SHAPE : state.getValue(LARGE) ? LARGE_SHAPE_2D : SHAPE_2D;
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
        if (fluidState.is(FluidTags.WATER) && fluidState.getAmount() == 8) {
            return super.getStateForPlacement(ctx).setValue(LARGE, false);
        }
        return null;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos var1, BlockState var2) {
        return new SeaLettuceBlockEntity(var1, var2);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state) {
        return !state.getValue(LARGE);
    }

    @Override
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return !state.getValue(LARGE);
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        world.setBlock(pos, state.setValue(LARGE, true), 2);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof SeaLettuceBlockEntity) {
            ((SeaLettuceBlockEntity) blockEntity).setLarge(true);
        }
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!world.isClientSide()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof SeaLettuceBlockEntity) {
                ((SeaLettuceBlockEntity) blockEntity).setLarge(state.getValue(LARGE));
            }
        }
    }
}
