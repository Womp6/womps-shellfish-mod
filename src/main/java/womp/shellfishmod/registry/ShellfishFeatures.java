package womp.shellfishmod.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import womp.shellfishmod.feature.BlobPatchFeature;
import womp.shellfishmod.feature.BlobPatchFeatureConfig;
import womp.shellfishmod.feature.ConditionalBlockFeature;
import womp.shellfishmod.feature.ConditionalBlockStateProvider;
import womp.shellfishmod.feature.UnderwaterFeature;

public class ShellfishFeatures {
    
    public static final Feature<ProbabilityFeatureConfiguration> DEAD_CLAM_BLOCK_FEATURE = register("dead_clam_block_feature", ShellfishBlocks.DEAD_CLAM_BLOCK);
	public static final Feature<ProbabilityFeatureConfiguration> DEAD_OYSTER_BLOCK_FEATURE = register("dead_oyster_block_feature", ShellfishBlocks.DEAD_OYSTER_BLOCK);
	public static final Feature<ProbabilityFeatureConfiguration> DEAD_MUSSEL_BLOCK_FEATURE = register("dead_mussel_block_feature", ShellfishBlocks.DEAD_MUSSEL_BLOCK);
    public static final Feature<ProbabilityFeatureConfiguration> ROCKWEED_FEATURE = register("rockweed", ShellfishBlocks.ROCKWEED, ShellfishBlocks.TALL_ROCKWEED);
    public static final Feature<ProbabilityFeatureConfiguration> PADDLEWEED_FEATURE = register("paddleweed", ShellfishBlocks.PADDLEWEED);
	public static final Feature<ProbabilityFeatureConfiguration> EELGRASS_FEATURE = register("eelgrass", ShellfishBlocks.EELGRASS);
    public static final Feature<ProbabilityFeatureConfiguration> SEA_LETTUCE_FEATURE = register("sea_lettuce", ShellfishBlocks.SEA_LETTUCE);

    public static final BlockStateProviderType<ConditionalBlockStateProvider> CONDITIONAL_STATE_PROVIDER = Registry.register(BuiltInRegistries.BLOCKSTATE_PROVIDER_TYPE, Identifier.fromNamespaceAndPath("shellfish", "conditional_state_provider"), new BlockStateProviderType<ConditionalBlockStateProvider>(ConditionalBlockStateProvider.CODEC));
    public static final Feature<SimpleBlockConfiguration> CONDITIONAL_BLOCK_FEATURE = register("conditional_block", new ConditionalBlockFeature(SimpleBlockConfiguration.CODEC));

    public static final Feature<BlobPatchFeatureConfig> BLOB_PATCH_FEATURE = register("blob_patch", new BlobPatchFeature(BlobPatchFeatureConfig.CODEC));

    private static Feature<ProbabilityFeatureConfiguration> register(String name, Block place) {
        return register(name, place, null);
    }

    private static Feature<ProbabilityFeatureConfiguration> register(String name, Block place, Block tall) {
        return register(name, new UnderwaterFeature(place, tall));
    }

    private static <T extends FeatureConfiguration> Feature<T> register(String name, Feature<T> feature) {
        return Registry.register(BuiltInRegistries.FEATURE, Identifier.fromNamespaceAndPath("shellfish", name), feature);
    }

    public static void register() {}
}
