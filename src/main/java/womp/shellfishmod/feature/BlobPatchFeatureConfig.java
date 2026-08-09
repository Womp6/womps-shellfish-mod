package womp.shellfishmod.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public record BlobPatchFeatureConfig(int tries, int radius, int ySpread, float centerBias, Holder<PlacedFeature> feature) implements FeatureConfiguration {
    
    public static final Codec<BlobPatchFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group((ExtraCodecs.POSITIVE_INT.fieldOf("tries")).orElse(128).forGetter(BlobPatchFeatureConfig::tries), (ExtraCodecs.NON_NEGATIVE_INT.fieldOf("radius")).orElse(7).forGetter(BlobPatchFeatureConfig::radius), (ExtraCodecs.NON_NEGATIVE_INT.fieldOf("y_spread")).orElse(3).forGetter(BlobPatchFeatureConfig::ySpread), (Codec.floatRange(0.0f, 1.0f).fieldOf("center_bias")).orElse(0.5f).forGetter(BlobPatchFeatureConfig::centerBias), (PlacedFeature.CODEC.fieldOf("feature")).forGetter(BlobPatchFeatureConfig::feature)).apply(instance, BlobPatchFeatureConfig::new));
}
