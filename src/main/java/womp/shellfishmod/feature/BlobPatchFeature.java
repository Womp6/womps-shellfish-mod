package womp.shellfishmod.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class BlobPatchFeature extends Feature<BlobPatchFeatureConfig> {

    public BlobPatchFeature(Codec<BlobPatchFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<BlobPatchFeatureConfig> context) {
        BlobPatchFeatureConfig blobConfig = context.config();
        RandomSource random = context.random();
        BlockPos blockPos = context.origin();
        WorldGenLevel structureWorldAccess = context.level();
        int i = 0;
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        int permRadius = blobConfig.radius();
        int height = blobConfig.ySpread() + 1;
        for (int l = 0; l < blobConfig.tries(); ++l) {
            float bias = 1 - blobConfig.centerBias();
            int radius = 1 + Math.round((float)(permRadius * Math.pow(bias, ((double)random.nextIntBetweenInclusive(0, permRadius * 1000)) / 1000)));
            int x = random.nextInt(radius) - random.nextInt(radius);
            int y = random.nextInt(height) - random.nextInt(height);
            int maxZ = 1 + (int) Math.round(Math.abs(Math.sqrt((radius * radius) - (x * x))));
            int z = random.nextInt(maxZ) - random.nextInt(maxZ);
            mutable.setWithOffset(blockPos, x, y, z);
            if (!blobConfig.feature().value().place(structureWorldAccess, context.chunkGenerator(), random, mutable)) continue;
            ++i;
        }
        return i > 0;
    }

}
