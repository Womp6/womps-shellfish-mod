package womp.shellfishmod.feature;

import com.mojang.serialization.Codec;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class BlobPatchFeature extends Feature<BlobPatchFeatureConfig> {

    public BlobPatchFeature(Codec<BlobPatchFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<BlobPatchFeatureConfig> context) {
        BlobPatchFeatureConfig blobConfig = context.getConfig();
        Random random = context.getRandom();
        BlockPos blockPos = context.getOrigin();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        int i = 0;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        int permRadius = blobConfig.radius();
        int height = blobConfig.ySpread() + 1;
        for (int l = 0; l < blobConfig.tries(); ++l) {
            float bias = 1 - blobConfig.centerBias();
            int radius = 1 + Math.round((float)(permRadius * Math.pow(bias, ((double)random.nextBetween(0, permRadius * 1000)) / 1000)));
            int x = random.nextInt(radius) - random.nextInt(radius);
            int y = random.nextInt(height) - random.nextInt(height);
            int maxZ = 1 + (int) Math.round(Math.abs(Math.sqrt((radius * radius) - (x * x))));
            int z = random.nextInt(maxZ) - random.nextInt(maxZ);
            mutable.set(blockPos, x, y, z);
            if (!blobConfig.feature().value().generateUnregistered(structureWorldAccess, context.getGenerator(), random, mutable)) continue;
            ++i;
        }
        return i > 0;
    }
    
}
