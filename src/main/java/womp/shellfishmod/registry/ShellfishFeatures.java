package womp.shellfishmod.registry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import womp.shellfishmod.ShellfishMod;
import womp.shellfishmod.feature.*;

import java.util.function.Supplier;

public class ShellfishFeatures {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, ShellfishMod.MOD_ID);

    public static final RegistryObject<Feature<ProbabilityFeatureConfiguration>> DEAD_CLAM_BLOCK_FEATURE = register("dead_clam_block_feature", ShellfishBlocks.DEAD_CLAM_BLOCK);
    public static final RegistryObject<Feature<ProbabilityFeatureConfiguration>> DEAD_OYSTER_BLOCK_FEATURE = register("dead_oyster_block_feature", ShellfishBlocks.DEAD_OYSTER_BLOCK);
    public static final RegistryObject<Feature<ProbabilityFeatureConfiguration>> DEAD_MUSSEL_BLOCK_FEATURE = register("dead_mussel_block_feature", ShellfishBlocks.DEAD_MUSSEL_BLOCK);
    public static final RegistryObject<Feature<ProbabilityFeatureConfiguration>> ROCKWEED_FEATURE = register("rockweed", ShellfishBlocks.ROCKWEED, ShellfishBlocks.TALL_ROCKWEED);
    public static final RegistryObject<Feature<ProbabilityFeatureConfiguration>> PADDLEWEED_FEATURE = register("paddleweed", ShellfishBlocks.PADDLEWEED);
    public static final RegistryObject<Feature<ProbabilityFeatureConfiguration>> EELGRASS_FEATURE = register("eelgrass", ShellfishBlocks.EELGRASS);
    public static final RegistryObject<Feature<ProbabilityFeatureConfiguration>> SEA_LETTUCE_FEATURE = register("sea_lettuce", ShellfishBlocks.SEA_LETTUCE);


    private static RegistryObject<Feature<ProbabilityFeatureConfiguration>> register(String name, Supplier<Block> place) {
        return register(name, place, null);
    }

    private static RegistryObject<Feature<ProbabilityFeatureConfiguration>> register(String name, Supplier<Block> place, Supplier<Block> tall) {
        return FEATURES.register(name, () -> new UnderwaterFeature(place, tall));
    }

    public static void register(IEventBus bus) {
        FEATURES.register(bus);
    }
}
