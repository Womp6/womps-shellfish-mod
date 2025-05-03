package womp.shellfishmod.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import womp.shellfishmod.blocks.parents.ShellfishPlantBlock;
import womp.shellfishmod.registry.ShellfishBlocks;

public class RockWeedBlock extends ShellfishPlantBlock implements Fertilizable {
    
    protected static final VoxelShape SHAPE = Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 15.0, 15.0);

    public RockWeedBlock(AbstractBlock.Settings settings) {
        super(settings, SHAPE, ShellfishPlantBlock.PlaceType.SOLID_SIDE);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        BlockState blockState = ShellfishBlocks.TALL_ROCKWEED.getDefaultState();
        BlockState blockState2 = (BlockState)blockState.with(TallRockWeedBlock.HALF, DoubleBlockHalf.UPPER);
        BlockPos blockPos = pos.up();
        if (world.getBlockState(blockPos).isOf(Blocks.WATER)) {
            world.setBlockState(pos, blockState, Block.NOTIFY_LISTENERS);
            world.setBlockState(blockPos, blockState2, Block.NOTIFY_LISTENERS);
        }
    }
}
