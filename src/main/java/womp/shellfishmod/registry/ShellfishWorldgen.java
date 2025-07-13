package womp.shellfishmod.registry;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import womp.shellfishmod.mixin.FoliagePlacerHelper;
import womp.shellfishmod.worldgen.MarshFoliagePlacer;

public class ShellfishWorldgen {

    // MARSH
    public static final RegistryKey<Biome> MARSH = RegistryKey.of(RegistryKeys.BIOME, Identifier.of("shellfish", "marsh"));
    public static final FoliagePlacerType<?> MARSH_FOLIAGE_PLACER = FoliagePlacerHelper.callRegister("marsh_foliage_placer", MarshFoliagePlacer.CODEC);

    // BLOCK GENERATION
    public static final RegistryKey<PlacedFeature> CLAM_CLAY = of("clam_clay");
    public static final RegistryKey<PlacedFeature> DEAD_CLAM_BLOCK = of("dead_clam_block");
    public static final RegistryKey<PlacedFeature> SHELL_PLACE_KEY = of("shell_placed");
    public static final RegistryKey<PlacedFeature> CLAM_SHELL_PLACE_KEY = of("clam_shell_placed");
    public static final RegistryKey<PlacedFeature> OYSTER_ORE = of("oyster_ore");    
    public static final RegistryKey<PlacedFeature> CLAM_ORE_OCEAN = of("clam_ore_ocean");    
    public static final RegistryKey<PlacedFeature> OYSTER_SHELL = of("oyster_shell");    
    public static final RegistryKey<PlacedFeature> DEAD_OYSTER_BLOCK = of("dead_oyster_block");
    public static final RegistryKey<PlacedFeature> MUSSEL_ORE = of("mussel_ore");
    public static final RegistryKey<PlacedFeature> MUSSEL_SHELL = of("mussel_shell");
    public static final RegistryKey<PlacedFeature> DEAD_MUSSEL_BLOCK = of("dead_mussel_block");
    public static final RegistryKey<PlacedFeature> ROCKWEED = of("rockweed");
    public static final RegistryKey<PlacedFeature> DEEP_ROCKWEED = of("deep_rockweed");
    public static final RegistryKey<PlacedFeature> WATER_LETTUCE = of("water_lettuce");
    public static final RegistryKey<PlacedFeature> PADDLEWEED = of("paddleweed");
    public static final RegistryKey<PlacedFeature> EELGRASS = of("eelgrass");
    public static final RegistryKey<PlacedFeature> SEA_LETTUCE = of("sea_lettuce");
    public static final RegistryKey<PlacedFeature> COLD_SEA_LETTUCE = of("cold_sea_lettuce");
    public static final RegistryKey<PlacedFeature> BEACHED_DRIFTWOOD = of("beached_driftwood");

    public static RegistryKey<PlacedFeature> of(String id) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of("shellfish", id));
    }

    
    public static void registerBlockgen(){

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.DEEP_COLD_OCEAN, BiomeKeys.DEEP_OCEAN, BiomeKeys.DEEP_FROZEN_OCEAN, BiomeKeys.DEEP_LUKEWARM_OCEAN, BiomeKeys.RIVER, BiomeKeys.FROZEN_RIVER, BiomeKeys.SWAMP, BiomeKeys.MANGROVE_SWAMP), GenerationStep.Feature.UNDERGROUND_ORES, CLAM_CLAY);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.OCEAN, BiomeKeys.COLD_OCEAN, BiomeKeys.FROZEN_OCEAN, BiomeKeys.LUKEWARM_OCEAN, BiomeKeys.WARM_OCEAN), GenerationStep.Feature.UNDERGROUND_ORES, CLAM_ORE_OCEAN);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.OCEAN, BiomeKeys.COLD_OCEAN, BiomeKeys.FROZEN_OCEAN, BiomeKeys.LUKEWARM_OCEAN, BiomeKeys.WARM_OCEAN, BiomeKeys.DEEP_COLD_OCEAN, BiomeKeys.DEEP_OCEAN, BiomeKeys.DEEP_FROZEN_OCEAN, BiomeKeys.DEEP_LUKEWARM_OCEAN, BiomeKeys.MANGROVE_SWAMP), GenerationStep.Feature.UNDERGROUND_ORES, OYSTER_ORE);        
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.OCEAN, BiomeKeys.COLD_OCEAN, BiomeKeys.FROZEN_OCEAN, BiomeKeys.RIVER, BiomeKeys.FROZEN_RIVER, BiomeKeys.LUKEWARM_OCEAN, BiomeKeys.WARM_OCEAN, BiomeKeys.DEEP_COLD_OCEAN, BiomeKeys.DEEP_OCEAN, BiomeKeys.DEEP_FROZEN_OCEAN, BiomeKeys.DEEP_LUKEWARM_OCEAN, BiomeKeys.SWAMP, BiomeKeys.MANGROVE_SWAMP), GenerationStep.Feature.UNDERGROUND_ORES, MUSSEL_ORE);
        
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.BEACH), GenerationStep.Feature.VEGETAL_DECORATION, SHELL_PLACE_KEY);        
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.BEACH), GenerationStep.Feature.VEGETAL_DECORATION, CLAM_SHELL_PLACE_KEY);        
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.BEACH), GenerationStep.Feature.VEGETAL_DECORATION, OYSTER_SHELL);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.BEACH), GenerationStep.Feature.VEGETAL_DECORATION, MUSSEL_SHELL);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.BEACH), GenerationStep.Feature.VEGETAL_DECORATION, BEACHED_DRIFTWOOD);

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.OCEAN, BiomeKeys.COLD_OCEAN, BiomeKeys.FROZEN_OCEAN, BiomeKeys.LUKEWARM_OCEAN, BiomeKeys.WARM_OCEAN, BiomeKeys.DEEP_COLD_OCEAN, BiomeKeys.DEEP_OCEAN, BiomeKeys.DEEP_FROZEN_OCEAN, BiomeKeys.DEEP_LUKEWARM_OCEAN, BiomeKeys.RIVER, BiomeKeys.FROZEN_RIVER), GenerationStep.Feature.VEGETAL_DECORATION, DEAD_CLAM_BLOCK);        
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.OCEAN, BiomeKeys.COLD_OCEAN, BiomeKeys.FROZEN_OCEAN, BiomeKeys.LUKEWARM_OCEAN, BiomeKeys.WARM_OCEAN, BiomeKeys.DEEP_COLD_OCEAN, BiomeKeys.DEEP_OCEAN, BiomeKeys.DEEP_FROZEN_OCEAN, BiomeKeys.DEEP_LUKEWARM_OCEAN), GenerationStep.Feature.VEGETAL_DECORATION, DEAD_OYSTER_BLOCK);        
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.OCEAN, BiomeKeys.COLD_OCEAN, BiomeKeys.FROZEN_OCEAN, BiomeKeys.RIVER, BiomeKeys.FROZEN_RIVER, BiomeKeys.DEEP_COLD_OCEAN, BiomeKeys.DEEP_OCEAN, BiomeKeys.DEEP_FROZEN_OCEAN, BiomeKeys.DEEP_LUKEWARM_OCEAN, BiomeKeys.LUKEWARM_OCEAN, BiomeKeys.WARM_OCEAN), GenerationStep.Feature.VEGETAL_DECORATION, DEAD_MUSSEL_BLOCK);        
        
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.OCEAN, BiomeKeys.COLD_OCEAN, BiomeKeys.FROZEN_OCEAN), GenerationStep.Feature.VEGETAL_DECORATION, ROCKWEED);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.DEEP_OCEAN, BiomeKeys.DEEP_COLD_OCEAN, BiomeKeys.DEEP_FROZEN_OCEAN), GenerationStep.Feature.VEGETAL_DECORATION, DEEP_ROCKWEED);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.PLAINS, BiomeKeys.FOREST, BiomeKeys.BIRCH_FOREST, BiomeKeys.DARK_FOREST, BiomeKeys.JUNGLE, BiomeKeys.SAVANNA, BiomeKeys.SWAMP), GenerationStep.Feature.VEGETAL_DECORATION, WATER_LETTUCE);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.OCEAN, BiomeKeys.WARM_OCEAN, BiomeKeys.LUKEWARM_OCEAN), GenerationStep.Feature.VEGETAL_DECORATION, PADDLEWEED);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.OCEAN, BiomeKeys.COLD_OCEAN, BiomeKeys.WARM_OCEAN, BiomeKeys.LUKEWARM_OCEAN), GenerationStep.Feature.VEGETAL_DECORATION, EELGRASS);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.OCEAN, BiomeKeys.LUKEWARM_OCEAN, BiomeKeys.WARM_OCEAN), GenerationStep.Feature.VEGETAL_DECORATION, SEA_LETTUCE);        
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.COLD_OCEAN, BiomeKeys.FROZEN_OCEAN), GenerationStep.Feature.VEGETAL_DECORATION, COLD_SEA_LETTUCE);
    }

    
    // MOB SPAWNS
    private static void registerSpawns() {

        //RIVER
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.RIVER), SpawnGroup.WATER_AMBIENT, ShellfishEntities.CRAYFISH, 5, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.RIVER), SpawnGroup.WATER_AMBIENT, ShellfishEntities.CRAB, 3, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.RIVER), SpawnGroup.WATER_AMBIENT, ShellfishEntities.SHRIMP, 4, 1, 4);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.RIVER), SpawnGroup.WATER_AMBIENT, ShellfishEntities.SEA_SNAIL, 3, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.RIVER), SpawnGroup.AMBIENT, ShellfishEntities.CLAM, 4, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.RIVER), SpawnGroup.AMBIENT, ShellfishEntities.MUSSEL, 4, 1, 6);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.RIVER), SpawnGroup.AMBIENT, ShellfishEntities.MOSS_BALL, 2, 1, 6);


        //FROZEN RIVER
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.FROZEN_RIVER), SpawnGroup.WATER_AMBIENT, ShellfishEntities.CRAB, 2, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.FROZEN_RIVER), SpawnGroup.WATER_AMBIENT, ShellfishEntities.SHRIMP, 3, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.FROZEN_RIVER), SpawnGroup.WATER_AMBIENT, ShellfishEntities.SEA_SNAIL, 1, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.FROZEN_RIVER), SpawnGroup.AMBIENT, ShellfishEntities.CLAM, 2, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.FROZEN_RIVER), SpawnGroup.AMBIENT, ShellfishEntities.MUSSEL, 4, 1, 4);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.FROZEN_RIVER), SpawnGroup.AMBIENT, ShellfishEntities.MOSS_BALL, 2, 1, 4);


        //OCEAN
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.LUKEWARM_OCEAN, BiomeKeys.WARM_OCEAN), SpawnGroup.WATER_CREATURE, ShellfishEntities.LOBSTER, 10, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.DEEP_LUKEWARM_OCEAN), SpawnGroup.WATER_CREATURE, ShellfishEntities.LOBSTER, 6, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.COLD_OCEAN), SpawnGroup.WATER_CREATURE, ShellfishEntities.LOBSTER, 3, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.DEEP_COLD_OCEAN), SpawnGroup.WATER_CREATURE, ShellfishEntities.LOBSTER, 2, 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.FROZEN_OCEAN), SpawnGroup.WATER_CREATURE, ShellfishEntities.LOBSTER, 1, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.DEEP_FROZEN_OCEAN), SpawnGroup.WATER_CREATURE, ShellfishEntities.LOBSTER, 1, 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.OCEAN), SpawnGroup.WATER_CREATURE, ShellfishEntities.LOBSTER, 3, 1, 3);
		BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.DEEP_OCEAN), SpawnGroup.WATER_CREATURE, ShellfishEntities.LOBSTER, 1, 1, 2);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.OCEAN, BiomeKeys.DEEP_OCEAN), SpawnGroup.WATER_AMBIENT, ShellfishEntities.CRAB, 10, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.COLD_OCEAN, BiomeKeys.DEEP_COLD_OCEAN), SpawnGroup.WATER_AMBIENT, ShellfishEntities.CRAB, 12, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.FROZEN_OCEAN, BiomeKeys.DEEP_FROZEN_OCEAN), SpawnGroup.WATER_AMBIENT, ShellfishEntities.CRAB, 4, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.LUKEWARM_OCEAN, BiomeKeys.WARM_OCEAN, BiomeKeys.DEEP_LUKEWARM_OCEAN), SpawnGroup.WATER_AMBIENT, ShellfishEntities.CRAB, 15, 1, 3);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.OCEAN, BiomeKeys.DEEP_OCEAN), SpawnGroup.WATER_AMBIENT, ShellfishEntities.SHRIMP, 10, 2, 5);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.COLD_OCEAN, BiomeKeys.DEEP_COLD_OCEAN), SpawnGroup.WATER_AMBIENT, ShellfishEntities.SHRIMP, 15, 2, 5);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.FROZEN_OCEAN, BiomeKeys.DEEP_FROZEN_OCEAN), SpawnGroup.WATER_AMBIENT, ShellfishEntities.SHRIMP, 6, 1, 4);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.LUKEWARM_OCEAN, BiomeKeys.WARM_OCEAN, BiomeKeys.DEEP_LUKEWARM_OCEAN), SpawnGroup.WATER_AMBIENT, ShellfishEntities.SHRIMP, 18, 2, 6);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.OCEAN, BiomeKeys.DEEP_OCEAN), SpawnGroup.WATER_AMBIENT, ShellfishEntities.SEA_SNAIL, 10, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.COLD_OCEAN, BiomeKeys.DEEP_COLD_OCEAN), SpawnGroup.WATER_AMBIENT, ShellfishEntities.SEA_SNAIL, 12, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.FROZEN_OCEAN, BiomeKeys.DEEP_FROZEN_OCEAN), SpawnGroup.WATER_AMBIENT, ShellfishEntities.SEA_SNAIL, 3, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.LUKEWARM_OCEAN, BiomeKeys.WARM_OCEAN, BiomeKeys.DEEP_LUKEWARM_OCEAN), SpawnGroup.WATER_AMBIENT, ShellfishEntities.SEA_SNAIL, 16, 1, 4);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.OCEAN, BiomeKeys.DEEP_OCEAN), SpawnGroup.WATER_AMBIENT, ShellfishEntities.SEA_URCHIN, 10, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.COLD_OCEAN, BiomeKeys.DEEP_COLD_OCEAN), SpawnGroup.WATER_AMBIENT, ShellfishEntities.SEA_URCHIN, 15, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.FROZEN_OCEAN, BiomeKeys.DEEP_FROZEN_OCEAN), SpawnGroup.WATER_AMBIENT, ShellfishEntities.SEA_URCHIN, 7, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.LUKEWARM_OCEAN, BiomeKeys.DEEP_LUKEWARM_OCEAN), SpawnGroup.WATER_AMBIENT, ShellfishEntities.SEA_URCHIN, 17, 1, 4);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.WARM_OCEAN), SpawnGroup.WATER_AMBIENT, ShellfishEntities.SEA_URCHIN, 19, 1, 4);

		BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.OCEAN), SpawnGroup.AMBIENT, ShellfishEntities.CLAM, 10, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.COLD_OCEAN), SpawnGroup.AMBIENT, ShellfishEntities.CLAM, 11, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.FROZEN_OCEAN), SpawnGroup.AMBIENT, ShellfishEntities.CLAM, 4, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.LUKEWARM_OCEAN, BiomeKeys.WARM_OCEAN), SpawnGroup.AMBIENT, ShellfishEntities.CLAM, 16, 1, 4);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.DEEP_OCEAN), SpawnGroup.AMBIENT, ShellfishEntities.CLAM, 6, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.DEEP_COLD_OCEAN), SpawnGroup.AMBIENT, ShellfishEntities.CLAM, 7, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.DEEP_FROZEN_OCEAN), SpawnGroup.AMBIENT, ShellfishEntities.CLAM, 3, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.DEEP_LUKEWARM_OCEAN), SpawnGroup.AMBIENT, ShellfishEntities.CLAM, 12, 1, 3);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.OCEAN), SpawnGroup.AMBIENT, ShellfishEntities.OYSTER, 10, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.COLD_OCEAN), SpawnGroup.AMBIENT, ShellfishEntities.OYSTER, 14, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.FROZEN_OCEAN), SpawnGroup.AMBIENT, ShellfishEntities.OYSTER, 6, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.LUKEWARM_OCEAN, BiomeKeys.WARM_OCEAN), SpawnGroup.AMBIENT, ShellfishEntities.OYSTER, 15, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.DEEP_OCEAN), SpawnGroup.AMBIENT, ShellfishEntities.OYSTER, 4, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.DEEP_COLD_OCEAN), SpawnGroup.AMBIENT, ShellfishEntities.OYSTER, 7, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.DEEP_FROZEN_OCEAN), SpawnGroup.AMBIENT, ShellfishEntities.OYSTER, 3, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.DEEP_LUKEWARM_OCEAN), SpawnGroup.AMBIENT, ShellfishEntities.OYSTER, 9, 1, 2);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.OCEAN), SpawnGroup.AMBIENT, ShellfishEntities.MUSSEL, 10, 1, 6);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.COLD_OCEAN), SpawnGroup.AMBIENT, ShellfishEntities.MUSSEL, 14, 1, 6);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.FROZEN_OCEAN), SpawnGroup.AMBIENT, ShellfishEntities.MUSSEL, 6, 1, 6);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.LUKEWARM_OCEAN, BiomeKeys.WARM_OCEAN), SpawnGroup.AMBIENT, ShellfishEntities.MUSSEL, 18, 1, 6);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.DEEP_OCEAN), SpawnGroup.AMBIENT, ShellfishEntities.MUSSEL, 6, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.DEEP_COLD_OCEAN), SpawnGroup.AMBIENT, ShellfishEntities.MUSSEL, 9, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.DEEP_FROZEN_OCEAN), SpawnGroup.AMBIENT, ShellfishEntities.MUSSEL, 3, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.DEEP_LUKEWARM_OCEAN), SpawnGroup.AMBIENT, ShellfishEntities.MUSSEL, 13, 1, 4);


        //BEACH
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.BEACH, BiomeKeys.STONY_SHORE), SpawnGroup.WATER_AMBIENT, ShellfishEntities.CRAB, 5, 1, 2);
		BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.BEACH, BiomeKeys.STONY_SHORE), SpawnGroup.WATER_AMBIENT, ShellfishEntities.SEA_SNAIL, 4, 1, 2);


        //SWAMP
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.SWAMP), SpawnGroup.WATER_AMBIENT, ShellfishEntities.CRAYFISH, 12, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.SWAMP), SpawnGroup.WATER_AMBIENT, ShellfishEntities.CRAB, 7, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.SWAMP), SpawnGroup.WATER_AMBIENT, ShellfishEntities.SHRIMP, 9, 1, 4);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.SWAMP), SpawnGroup.WATER_AMBIENT, ShellfishEntities.SEA_SNAIL, 6, 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.SWAMP), SpawnGroup.AMBIENT, ShellfishEntities.CLAM, 9, 1, 4);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.SWAMP), SpawnGroup.AMBIENT, ShellfishEntities.MUSSEL, 10, 1, 6);


        //MANGROVE SWAMP
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.MANGROVE_SWAMP), SpawnGroup.WATER_AMBIENT, ShellfishEntities.CRAYFISH, 15, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.MANGROVE_SWAMP), SpawnGroup.WATER_AMBIENT, ShellfishEntities.CRAB, 25, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.MANGROVE_SWAMP), SpawnGroup.WATER_AMBIENT, ShellfishEntities.SHRIMP, 20, 2, 5);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.MANGROVE_SWAMP), SpawnGroup.WATER_AMBIENT, ShellfishEntities.SEA_SNAIL, 20, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.MANGROVE_SWAMP), SpawnGroup.AMBIENT, ShellfishEntities.CLAM, 19, 1, 4);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.MANGROVE_SWAMP), SpawnGroup.AMBIENT, ShellfishEntities.OYSTER, 18, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.MANGROVE_SWAMP), SpawnGroup.AMBIENT, ShellfishEntities.MUSSEL, 21, 1, 6);
    }

    
    
    public static void register() {
        registerSpawns();
        registerBlockgen();
    }
}
