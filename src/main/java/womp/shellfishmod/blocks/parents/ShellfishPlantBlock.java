package womp.shellfishmod.blocks.parents;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.MapCodec;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidFillable;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;

public class ShellfishPlantBlock extends PlantBlock implements FluidFillable {
    
    protected final VoxelShape shape;
    protected final PlaceType placeType;
    protected final MapCodec<? extends ShellfishPlantBlock> codec;

    public ShellfishPlantBlock(AbstractBlock.Settings settings, VoxelShape shape, PlaceType placeType) {
        super(settings);
        this.shape = shape;
        this.placeType = placeType;
        this.codec = createCodec((setting) -> new ShellfishPlantBlock(setting, shape, placeType));
    }

    public ShellfishPlantBlock(AbstractBlock.Settings settings, VoxelShape shape, PlaceType placeType, MapCodec<? extends ShellfishPlantBlock> codec) {
        super(settings);
        this.shape = shape;
        this.placeType = placeType;
        this.codec = codec;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return shape;
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        BlockState block = world.getBlockState(pos);
        FluidState fluid = world.getFluidState(pos.up());
        if (placeType.equals(PlaceType.MUD_SAND)) {
            return (block.getBlock().equals(Blocks.SAND) || block.getBlock().equals(Blocks.MUD)) && fluid.getFluid().equals(Fluids.WATER);
        } else if (placeType.equals(PlaceType.SOLID_SIDE)) {
            return floor.isSideSolidFullSquare(world, pos, Direction.UP) && !floor.isOf(Blocks.MAGMA_BLOCK);
        }
        return super.canPlantOnTop(floor, world, pos);
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        if (fluidState.isIn(FluidTags.WATER) && fluidState.getLevel() == 8) {
            return super.getPlacementState(ctx);
        }
        return null;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView view, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        BlockState blockState = super.getStateForNeighborUpdate(state, world, view, pos, direction, neighborPos, neighborState, random);
        if (!blockState.isAir()) {
            view.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return blockState;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return Fluids.WATER.getStill(false);
    }

    @Override
    public boolean canFillWithFluid(LivingEntity arg0, BlockView arg1, BlockPos arg2, BlockState arg3, Fluid arg4) {
        return false;
    }

    @Override
    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        return false;
    }

    public static enum PlaceType {
        MUD_SAND,
        SOLID_SIDE;
    }

    @Override
    protected MapCodec<? extends PlantBlock> getCodec() {
        return codec;
    }
}