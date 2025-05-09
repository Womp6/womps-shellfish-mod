package womp.shellfishmod.registry;

import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.feature.Feature;
import womp.shellfishmod.feature.UnderwaterFeature;

public class ShellfishFeatures {
    
    public static final Feature<ProbabilityConfig> DEAD_CLAM_BLOCK_FEATURE = register("dead_clam_block_feature", ShellfishBlocks.DEAD_CLAM_BLOCK);
	public static final Feature<ProbabilityConfig> DEAD_OYSTER_BLOCK_FEATURE = register("dead_oyster_block_feature", ShellfishBlocks.DEAD_OYSTER_BLOCK);
	public static final Feature<ProbabilityConfig> DEAD_MUSSEL_BLOCK_FEATURE = register("dead_mussel_block_feature", ShellfishBlocks.DEAD_MUSSEL_BLOCK);
    public static final Feature<ProbabilityConfig> ROCKWEED_FEATURE = register("rockweed", ShellfishBlocks.ROCKWEED, ShellfishBlocks.TALL_ROCKWEED);
    public static final Feature<ProbabilityConfig> PADDLEWEED_FEATURE = register("paddleweed", ShellfishBlocks.PADDLEWEED);
	public static final Feature<ProbabilityConfig> EELGRASS_FEATURE = register("eelgrass", ShellfishBlocks.EELGRASS);
    public static final Feature<ProbabilityConfig> SEA_LETTUCE_FEATURE = register("sea_lettuce", ShellfishBlocks.SEA_LETTUCE);

    private static Feature<ProbabilityConfig> register(String name, Block place) {
        return register(name, place, null);
    }

    private static Feature<ProbabilityConfig> register(String name, Block place, Block tall) {
        return Registry.register(Registries.FEATURE, Identifier.of("shellfish", name), new UnderwaterFeature(place, tall));
    }

    public static void register() {}
}
