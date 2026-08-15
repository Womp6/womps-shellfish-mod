package womp.shellfishmod.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import womp.shellfishmod.registry.ShellfishWorldgen;

public class MarshFoliagePlacer extends FoliagePlacer {

    public static final MapCodec<MarshFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> foliagePlacerParts(instance)
            .and(Codec.intRange(0, 4).fieldOf("height").forGetter((placer) -> placer.height)).apply(instance, MarshFoliagePlacer::new));

    private final int height;

    public MarshFoliagePlacer(IntProvider radius, IntProvider offset, int height) {
        super(radius, offset);
        this.height = height;
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return ShellfishWorldgen.MARSH_FOLIAGE_PLACER.get();
    }

    @Override
    protected void createFoliage(WorldGenLevel world, FoliagePlacer.FoliageSetter placer, RandomSource random, TreeConfiguration config, int trunkHeight, FoliagePlacer.FoliageAttachment treeNode, int foliageHeight, int radius, int offset) {
        this.placeLeavesRow(world, placer, random, config, treeNode.pos(), 0, 1, false);
        tryPlaceLeaf(world, placer, random, config, treeNode.pos());
        tryPlaceLeaf(world, placer, random, config, treeNode.pos().north());
        tryPlaceLeaf(world, placer, random, config, treeNode.pos().east());
        tryPlaceLeaf(world, placer, random, config, treeNode.pos().south());
        tryPlaceLeaf(world, placer, random, config, treeNode.pos().west());
        this.placeLeavesRow(world, placer, random, config, treeNode.pos(), 1, -1, false);
    }

    @Override
    public int foliageHeight(RandomSource var1, int var2, TreeConfiguration var3) {
        return height;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource var1, int var2, int var3, int var4, int var5, boolean var6) {
        return false;
    }
}
