package womp.shellfishmod.blocks.parents;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class ShellfishLandPlantBlock extends TallGrassBlock {

    private final boolean waterOnly;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private final Supplier<? extends Block> tallPlantBlock;

    public ShellfishLandPlantBlock(Properties settings, boolean waterOnly, Supplier<? extends Block> tallPlantBlock) {
        super(settings);
        this.waterOnly = waterOnly;
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, waterOnly));
        this.tallPlantBlock = tallPlantBlock;
    }

    @Override
    protected boolean mayPlaceOn(BlockState floor, BlockGetter world, BlockPos pos) {
        return (floor.is(BlockTags.SAND) || super.mayPlaceOn(floor, world, pos) || floor.is(Blocks.CLAY) || floor.is(Blocks.MUD))
                && (waterOnly || world.getFluidState(pos.above()).is(FluidTags.WATER) ? world.getBlockState(pos.above(2)).propagatesSkylightDown() && world.getFluidState(pos.above(2)).isEmpty() : true);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos down = pPos.below();
        return mayPlaceOn(pLevel.getBlockState(down), pLevel, down);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.getValue(WATERLOGGED) || waterOnly) {
            return Fluids.WATER.getSource(false);
        }
        return Fluids.EMPTY.defaultFluidState();
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState state = super.getStateForPlacement(ctx);
        if (state == null) return null;
        FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
        if (fluidState.is(FluidTags.WATER) && fluidState.getAmount() == 8) {
            return state.setValue(WATERLOGGED, ctx.getLevel().getFluidState(ctx.getClickedPos()).getType() == Fluids.WATER);
        }
        return waterOnly ? null : state.setValue(WATERLOGGED, ctx.getLevel().getFluidState(ctx.getClickedPos()).getType() == Fluids.WATER);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Override
    public BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess view, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if (!state.isAir() || state.getValue(WATERLOGGED)) {
            view.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }
        return super.updateShape(state, world, view, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState) {
        return tallPlantBlock.get().defaultBlockState().canSurvive(pLevel, pPos) && pLevel.isEmptyBlock(pPos.above());
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        DoublePlantBlock tallPlantBlock = (DoublePlantBlock) (this.tallPlantBlock.get());
        if (tallPlantBlock.defaultBlockState().canSurvive(world, pos) && world.isEmptyBlock(pos.above())) {
            DoublePlantBlock.placeAt(world, tallPlantBlock.defaultBlockState(), pos, 2);
        }
    }
}
