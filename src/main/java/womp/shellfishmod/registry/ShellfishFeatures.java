package womp.shellfishmod.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import womp.shellfishmod.ShellfishMod;
import womp.shellfishmod.feature.*;

import java.util.function.Supplier;

public class ShellfishFeatures {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(BuiltInRegistries.FEATURE, ShellfishMod.MOD_ID);

    public static final Supplier<Feature<ProbabilityFeatureConfiguration>> DEAD_CLAM_BLOCK_FEATURE = register("dead_clam_block_feature", ShellfishBlocks.DEAD_CLAM_BLOCK);
    public static final Supplier<Feature<ProbabilityFeatureConfiguration>> DEAD_OYSTER_BLOCK_FEATURE = register("dead_oyster_block_feature", ShellfishBlocks.DEAD_OYSTER_BLOCK);
    public static final Supplier<Feature<ProbabilityFeatureConfiguration>> DEAD_MUSSEL_BLOCK_FEATURE = register("dead_mussel_block_feature", ShellfishBlocks.DEAD_MUSSEL_BLOCK);
    public static final Supplier<Feature<ProbabilityFeatureConfiguration>> ROCKWEED_FEATURE = register("rockweed", ShellfishBlocks.ROCKWEED, ShellfishBlocks.TALL_ROCKWEED);
    public static final Supplier<Feature<ProbabilityFeatureConfiguration>> PADDLEWEED_FEATURE = register("paddleweed", ShellfishBlocks.PADDLEWEED);
    public static final Supplier<Feature<ProbabilityFeatureConfiguration>> EELGRASS_FEATURE = register("eelgrass", ShellfishBlocks.EELGRASS);
    public static final Supplier<Feature<ProbabilityFeatureConfiguration>> SEA_LETTUCE_FEATURE = register("sea_lettuce", ShellfishBlocks.SEA_LETTUCE);


    private static Supplier<Feature<ProbabilityFeatureConfiguration>> register(String name, Supplier<Block> place) {
        return register(name, place, null);
    }

    private static Supplier<Feature<ProbabilityFeatureConfiguration>> register(String name, Supplier<Block> place, Supplier<Block> tall) {
        return FEATURES.register(name, () -> new UnderwaterFeature(place, tall));
    }

    public static void register(IEventBus bus) {
        FEATURES.register(bus);
    }
}
