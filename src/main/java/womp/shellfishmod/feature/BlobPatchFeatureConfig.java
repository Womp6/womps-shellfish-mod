package womp.shellfishmod.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;

public record BlobPatchFeatureConfig(int tries, int radius, int ySpread, float centerBias, RegistryEntry<PlacedFeature> feature) implements FeatureConfig {
    
    public static final Codec<BlobPatchFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group((Codecs.POSITIVE_INT.fieldOf("tries")).orElse(128).forGetter(BlobPatchFeatureConfig::tries), (Codecs.NON_NEGATIVE_INT.fieldOf("radius")).orElse(7).forGetter(BlobPatchFeatureConfig::radius), (Codecs.NON_NEGATIVE_INT.fieldOf("y_spread")).orElse(3).forGetter(BlobPatchFeatureConfig::ySpread), (Codec.floatRange(0.0f, 1.0f).fieldOf("center_bias")).orElse(0.5f).forGetter(BlobPatchFeatureConfig::centerBias), (PlacedFeature.REGISTRY_CODEC.fieldOf("feature")).forGetter(BlobPatchFeatureConfig::feature)).apply(instance, BlobPatchFeatureConfig::new));
}
