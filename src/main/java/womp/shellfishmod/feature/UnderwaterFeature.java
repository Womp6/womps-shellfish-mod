package womp.shellfishmod.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import womp.shellfishmod.blocks.SeaLettuceBlock;
import womp.shellfishmod.blocks.SeaLettuceBlockEntity;
import womp.shellfishmod.blocks.TallRockWeedBlock;
import womp.shellfishmod.registry.ShellfishBlocks;

import java.util.function.Supplier;

public class UnderwaterFeature extends Feature<ProbabilityFeatureConfiguration> {

    private final Block place, tallBlock;

    public UnderwaterFeature(Supplier<Block> block, Supplier<Block> tallBlock) {
        super(ProbabilityFeatureConfiguration.CODEC);
        this.place = block.get();
        this.tallBlock = tallBlock == null ? null : tallBlock.get();
    }

    @Override
    public boolean place(FeaturePlaceContext<ProbabilityFeatureConfiguration> context) {
        boolean bl = false;
        RandomSource random = context.random();
        WorldGenLevel structureWorldAccess = context.level();
        BlockPos blockPos = context.origin();
        ProbabilityFeatureConfiguration probabilityConfig = context.config();
        int i = random.nextInt(8) - random.nextInt(8);
        int j = random.nextInt(8) - random.nextInt(8);
        int k = structureWorldAccess.getHeight(Heightmap.Types.OCEAN_FLOOR, blockPos.getX() + i, blockPos.getZ() + j);
        BlockPos blockPos2 = new BlockPos(blockPos.getX() + i, k, blockPos.getZ() + j);
        if (structureWorldAccess.getBlockState(blockPos2).is(Blocks.WATER)) {
            BlockState blockState = place.defaultBlockState();
            if (place.equals(ShellfishBlocks.SEA_LETTUCE.get())) {
                boolean isLarge = random.nextBoolean();
                blockState = blockState.setValue(SeaLettuceBlock.LARGE, isLarge);
                if (blockState.canSurvive(structureWorldAccess, blockPos2)) {
                    structureWorldAccess.setBlock(blockPos2, blockState, 2);
                    BlockEntity blockEntity = structureWorldAccess.getBlockEntity(blockPos2);
                    if (blockEntity instanceof SeaLettuceBlockEntity) {
                        ((SeaLettuceBlockEntity) blockEntity).setLarge(isLarge);
                    }
                }
            } else if (tallBlock == null) {
                if (blockState.canSurvive(structureWorldAccess, blockPos2)) {
                    structureWorldAccess.setBlock(blockPos2, blockState, 2);
                    bl = true;
                }
            } else {
                boolean bl2 = random.nextDouble() < (double)probabilityConfig.probability;
                blockState = bl2 ? tallBlock.defaultBlockState() : place.defaultBlockState();
                if (blockState.canSurvive(structureWorldAccess, blockPos2)) {
                    if (bl2 && tallBlock instanceof TallRockWeedBlock) {
                        BlockState blockState22 = blockState.setValue(TallRockWeedBlock.HALF, DoubleBlockHalf.UPPER);
                        BlockPos blockPos3 = blockPos2.above();
                        if (structureWorldAccess.getBlockState(blockPos3).is(Blocks.WATER)) {
                            structureWorldAccess.setBlock(blockPos2, blockState, 2);
                            structureWorldAccess.setBlock(blockPos3, blockState22, 2);
                        }
                    } else {
                        structureWorldAccess.setBlock(blockPos2, blockState, 2);
                    }
                    bl = true;
                }
            }
        }
        return bl;
    }
}
