package womp.shellfishmod.feature;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import womp.shellfishmod.blocks.SeaLettuceBlock;
import womp.shellfishmod.blocks.SeaLettuceBlockEntity;
import womp.shellfishmod.blocks.TallRockWeedBlock;
import womp.shellfishmod.registry.ShellfishBlocks;

public class UnderwaterFeature extends Feature<ProbabilityConfig> {

    private final Block place, tallBlock;

    public UnderwaterFeature(Block block, Block tallBlock) {
        super(ProbabilityConfig.CODEC);
        this.place = block;
        this.tallBlock = tallBlock;
    }

    @Override
    public boolean generate(FeatureContext<ProbabilityConfig> context) {
        boolean bl = false;
        Random random = context.getRandom();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos blockPos = context.getOrigin();
        ProbabilityConfig probabilityConfig = context.getConfig();
        int i = random.nextInt(8) - random.nextInt(8);
        int j = random.nextInt(8) - random.nextInt(8);
        int k = structureWorldAccess.getTopY(Heightmap.Type.OCEAN_FLOOR, blockPos.getX() + i, blockPos.getZ() + j);
        BlockPos blockPos2 = new BlockPos(blockPos.getX() + i, k, blockPos.getZ() + j);
        if (structureWorldAccess.getBlockState(blockPos2).isOf(Blocks.WATER)) {
            BlockState blockState = place.getDefaultState();
            if (place.equals(ShellfishBlocks.SEA_LETTUCE)) {
                Boolean isLarge = random.nextBoolean();
                blockState = blockState.with(SeaLettuceBlock.LARGE, isLarge);
                structureWorldAccess.setBlockState(blockPos2, blockState, Block.NOTIFY_LISTENERS);
                BlockEntity blockEntity = structureWorldAccess.getBlockEntity(blockPos2);
                if (blockEntity instanceof SeaLettuceBlockEntity) {
                    ((SeaLettuceBlockEntity) blockEntity).setLarge(isLarge);
                }
            } else if (tallBlock == null) {
                if (blockState.canPlaceAt(structureWorldAccess, blockPos2)) {
                    structureWorldAccess.setBlockState(blockPos2, blockState, Block.NOTIFY_LISTENERS);
                    bl = true;
                }
            } else {
                boolean bl2 = random.nextDouble() < (double)probabilityConfig.probability;
                blockState = bl2 ? tallBlock.getDefaultState() : place.getDefaultState();
                if (blockState.canPlaceAt(structureWorldAccess, blockPos2)) {
                    if (bl2 && tallBlock instanceof TallRockWeedBlock) {
                        BlockState blockState22 = (BlockState)blockState.with(TallRockWeedBlock.HALF, DoubleBlockHalf.UPPER);
                        BlockPos blockPos3 = blockPos2.up();
                        if (structureWorldAccess.getBlockState(blockPos3).isOf(Blocks.WATER)) {
                            structureWorldAccess.setBlockState(blockPos2, blockState, Block.NOTIFY_LISTENERS);
                            structureWorldAccess.setBlockState(blockPos3, blockState22, Block.NOTIFY_LISTENERS);
                        }
                    } else {
                        structureWorldAccess.setBlockState(blockPos2, blockState, Block.NOTIFY_LISTENERS);
                    }
                    bl = true;
                }
            }
        }
        return bl;
    }
}
