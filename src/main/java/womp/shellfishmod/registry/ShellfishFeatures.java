package womp.shellfishmod.registry;

import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProviderType;
import womp.shellfishmod.feature.BlobPatchFeature;
import womp.shellfishmod.feature.BlobPatchFeatureConfig;
import womp.shellfishmod.feature.ConditionalBlockFeature;
import womp.shellfishmod.feature.ConditionalBlockStateProvider;
import womp.shellfishmod.feature.UnderwaterFeature;

public class ShellfishFeatures {
    
    public static final Feature<ProbabilityConfig> DEAD_CLAM_BLOCK_FEATURE = register("dead_clam_block_feature", ShellfishBlocks.DEAD_CLAM_BLOCK);
	public static final Feature<ProbabilityConfig> DEAD_OYSTER_BLOCK_FEATURE = register("dead_oyster_block_feature", ShellfishBlocks.DEAD_OYSTER_BLOCK);
	public static final Feature<ProbabilityConfig> DEAD_MUSSEL_BLOCK_FEATURE = register("dead_mussel_block_feature", ShellfishBlocks.DEAD_MUSSEL_BLOCK);
    public static final Feature<ProbabilityConfig> ROCKWEED_FEATURE = register("rockweed", ShellfishBlocks.ROCKWEED, ShellfishBlocks.TALL_ROCKWEED);
    public static final Feature<ProbabilityConfig> PADDLEWEED_FEATURE = register("paddleweed", ShellfishBlocks.PADDLEWEED);
	public static final Feature<ProbabilityConfig> EELGRASS_FEATURE = register("eelgrass", ShellfishBlocks.EELGRASS);
    public static final Feature<ProbabilityConfig> SEA_LETTUCE_FEATURE = register("sea_lettuce", ShellfishBlocks.SEA_LETTUCE);

    public static final BlockStateProviderType<ConditionalBlockStateProvider> CONDITIONAL_STATE_PROVIDER = Registry.register(Registries.BLOCK_STATE_PROVIDER_TYPE, new Identifier("shellfish", "conditional_state_provider"), new BlockStateProviderType<ConditionalBlockStateProvider>(ConditionalBlockStateProvider.CODEC));
    public static final Feature<SimpleBlockFeatureConfig> CONDITIONAL_BLOCK_FEATURE = register("conditional_block", new ConditionalBlockFeature(SimpleBlockFeatureConfig.CODEC));

    public static final Feature<BlobPatchFeatureConfig> BLOB_PATCH_FEATURE = register("blob_patch", new BlobPatchFeature(BlobPatchFeatureConfig.CODEC));
    

    private static Feature<ProbabilityConfig> register(String name, Block place) {
        return register(name, place, null);
    }

    private static Feature<ProbabilityConfig> register(String name, Block place, Block tall) {
        return register(name, new UnderwaterFeature(place, tall));
    }

    private static <T extends FeatureConfig> Feature<T> register(String name, Feature<T> feature) {
        return Registry.register(Registries.FEATURE, new Identifier("shellfish", name), feature);
    }

    public static void register() {}
}
