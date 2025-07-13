package womp.shellfishmod.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import womp.shellfishmod.registry.ShellfishWorldgen;

public class MarshFoliagePlacer extends FoliagePlacer {

    public static final MapCodec<MarshFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> fillFoliagePlacerFields(instance)
            .and(Codec.intRange(0, 4).fieldOf("height").forGetter((placer) -> placer.height)).apply(instance, MarshFoliagePlacer::new));

    private final int height;

    public MarshFoliagePlacer(IntProvider radius, IntProvider offset, int height) {
        super(radius, offset);
        this.height = height;
    }

    @Override
    protected FoliagePlacerType<?> getType() {
        return ShellfishWorldgen.MARSH_FOLIAGE_PLACER;
    }

    @Override
    protected void generate(TestableWorld world, FoliagePlacer.BlockPlacer placer, Random random, TreeFeatureConfig config, int trunkHeight, FoliagePlacer.TreeNode treeNode, int foliageHeight, int radius, int offset) {
        this.generateSquare(world, placer, random, config, treeNode.getCenter(), 0, 1, false);
        placeFoliageBlock(world, placer, random, config, treeNode.getCenter());
        placeFoliageBlock(world, placer, random, config, treeNode.getCenter().north());
        placeFoliageBlock(world, placer, random, config, treeNode.getCenter().east());
        placeFoliageBlock(world, placer, random, config, treeNode.getCenter().south());
        placeFoliageBlock(world, placer, random, config, treeNode.getCenter().west());
        this.generateSquare(world, placer, random, config, treeNode.getCenter(), 1, -1, false);
    }

    @Override
    public int getRandomHeight(Random var1, int var2, TreeFeatureConfig var3) {
        return height;
    }

    @Override
    protected boolean isInvalidForLeaves(Random var1, int var2, int var3, int var4, int var5, boolean var6) {
        return false;
    }
}
