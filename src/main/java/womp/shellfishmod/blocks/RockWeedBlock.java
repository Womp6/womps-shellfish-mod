package womp.shellfishmod.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.shapes.VoxelShape;
import womp.shellfishmod.blocks.parents.ShellfishPlantBlock;
import womp.shellfishmod.registry.ShellfishBlocks;

public class RockWeedBlock extends ShellfishPlantBlock implements BonemealableBlock {

    protected static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 15.0, 15.0);

    public RockWeedBlock(BlockBehaviour.Properties settings) {
        super(settings, SHAPE, PlaceType.SOLID_SIDE);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        BlockState blockState = ShellfishBlocks.TALL_ROCKWEED.get().defaultBlockState();
        BlockState blockState2 = (BlockState)blockState.setValue(TallRockWeedBlock.HALF, DoubleBlockHalf.UPPER);
        BlockPos blockPos = pos.above();
        if (world.getBlockState(blockPos).is(Blocks.WATER)) {
            world.setBlock(pos, blockState, 2);
            world.setBlock(blockPos, blockState2, 2);
        }
    }
}
