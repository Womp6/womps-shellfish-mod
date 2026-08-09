package womp.shellfishmod.registry;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import womp.shellfishmod.mixin.FoliagePlacerHelper;
import womp.shellfishmod.worldgen.MarshFoliagePlacer;

public class ShellfishWorldgen {

    // MARSH
    public static final ResourceKey<Biome> MARSH = ShellfishUtil.createKey("marsh", Registries.BIOME);
    public static final FoliagePlacerType<?> MARSH_FOLIAGE_PLACER = FoliagePlacerHelper.callRegister("marsh_foliage_placer", MarshFoliagePlacer.CODEC);

    // BLOCK GENERATION
    public static final ResourceKey<PlacedFeature> CLAM_CLAY = of("clam_clay");
    public static final ResourceKey<PlacedFeature> DEAD_CLAM_BLOCK = of("dead_clam_block");
    public static final ResourceKey<PlacedFeature> SHELL_PLACE_KEY = of("shell_placed");
    public static final ResourceKey<PlacedFeature> CLAM_SHELL_PLACE_KEY = of("clam_shell_placed");
    public static final ResourceKey<PlacedFeature> OYSTER_ORE = of("oyster_ore");    
    public static final ResourceKey<PlacedFeature> CLAM_ORE_OCEAN = of("clam_ore_ocean");    
    public static final ResourceKey<PlacedFeature> OYSTER_SHELL = of("oyster_shell");    
    public static final ResourceKey<PlacedFeature> DEAD_OYSTER_BLOCK = of("dead_oyster_block");
    public static final ResourceKey<PlacedFeature> MUSSEL_ORE = of("mussel_ore");
    public static final ResourceKey<PlacedFeature> MUSSEL_SHELL = of("mussel_shell");
    public static final ResourceKey<PlacedFeature> DEAD_MUSSEL_BLOCK = of("dead_mussel_block");
    public static final ResourceKey<PlacedFeature> ROCKWEED = of("rockweed");
    public static final ResourceKey<PlacedFeature> DEEP_ROCKWEED = of("deep_rockweed");
    public static final ResourceKey<PlacedFeature> WATER_LETTUCE = of("water_lettuce");
    public static final ResourceKey<PlacedFeature> PADDLEWEED = of("paddleweed");
    public static final ResourceKey<PlacedFeature> EELGRASS = of("eelgrass");
    public static final ResourceKey<PlacedFeature> SEA_LETTUCE = of("sea_lettuce");
    public static final ResourceKey<PlacedFeature> COLD_SEA_LETTUCE = of("cold_sea_lettuce");
    public static final ResourceKey<PlacedFeature> BEACHED_DRIFTWOOD = of("beached_driftwood");

    public static ResourceKey<PlacedFeature> of(String id) {
        return ShellfishUtil.createKey(id, Registries.PLACED_FEATURE);
    }

    
    public static void registerBlockgen(){

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.DEEP_COLD_OCEAN, Biomes.DEEP_OCEAN, Biomes.DEEP_FROZEN_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.RIVER, Biomes.FROZEN_RIVER, Biomes.SWAMP, Biomes.MANGROVE_SWAMP), GenerationStep.Decoration.UNDERGROUND_ORES, CLAM_CLAY);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.OCEAN, Biomes.COLD_OCEAN, Biomes.FROZEN_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.WARM_OCEAN), GenerationStep.Decoration.UNDERGROUND_ORES, CLAM_ORE_OCEAN);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.OCEAN, Biomes.COLD_OCEAN, Biomes.FROZEN_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.WARM_OCEAN, Biomes.DEEP_COLD_OCEAN, Biomes.DEEP_OCEAN, Biomes.DEEP_FROZEN_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.MANGROVE_SWAMP), GenerationStep.Decoration.UNDERGROUND_ORES, OYSTER_ORE);        
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.OCEAN, Biomes.COLD_OCEAN, Biomes.FROZEN_OCEAN, Biomes.RIVER, Biomes.FROZEN_RIVER, Biomes.LUKEWARM_OCEAN, Biomes.WARM_OCEAN, Biomes.DEEP_COLD_OCEAN, Biomes.DEEP_OCEAN, Biomes.DEEP_FROZEN_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.SWAMP, Biomes.MANGROVE_SWAMP), GenerationStep.Decoration.UNDERGROUND_ORES, MUSSEL_ORE);
        
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.BEACH), GenerationStep.Decoration.VEGETAL_DECORATION, SHELL_PLACE_KEY);        
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.BEACH), GenerationStep.Decoration.VEGETAL_DECORATION, CLAM_SHELL_PLACE_KEY);        
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.BEACH), GenerationStep.Decoration.VEGETAL_DECORATION, OYSTER_SHELL);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.BEACH), GenerationStep.Decoration.VEGETAL_DECORATION, MUSSEL_SHELL);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.BEACH), GenerationStep.Decoration.VEGETAL_DECORATION, BEACHED_DRIFTWOOD);

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.OCEAN, Biomes.COLD_OCEAN, Biomes.FROZEN_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.WARM_OCEAN, Biomes.DEEP_COLD_OCEAN, Biomes.DEEP_OCEAN, Biomes.DEEP_FROZEN_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.RIVER, Biomes.FROZEN_RIVER), GenerationStep.Decoration.VEGETAL_DECORATION, DEAD_CLAM_BLOCK);        
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.OCEAN, Biomes.COLD_OCEAN, Biomes.FROZEN_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.WARM_OCEAN, Biomes.DEEP_COLD_OCEAN, Biomes.DEEP_OCEAN, Biomes.DEEP_FROZEN_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN), GenerationStep.Decoration.VEGETAL_DECORATION, DEAD_OYSTER_BLOCK);        
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.OCEAN, Biomes.COLD_OCEAN, Biomes.FROZEN_OCEAN, Biomes.RIVER, Biomes.FROZEN_RIVER, Biomes.DEEP_COLD_OCEAN, Biomes.DEEP_OCEAN, Biomes.DEEP_FROZEN_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.WARM_OCEAN), GenerationStep.Decoration.VEGETAL_DECORATION, DEAD_MUSSEL_BLOCK);        
        
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.OCEAN, Biomes.COLD_OCEAN, Biomes.FROZEN_OCEAN), GenerationStep.Decoration.VEGETAL_DECORATION, ROCKWEED);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.DEEP_OCEAN, Biomes.DEEP_COLD_OCEAN, Biomes.DEEP_FROZEN_OCEAN), GenerationStep.Decoration.VEGETAL_DECORATION, DEEP_ROCKWEED);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.PLAINS, Biomes.FOREST, Biomes.BIRCH_FOREST, Biomes.DARK_FOREST, Biomes.JUNGLE, Biomes.SAVANNA, Biomes.SWAMP), GenerationStep.Decoration.VEGETAL_DECORATION, WATER_LETTUCE);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.OCEAN, Biomes.WARM_OCEAN, Biomes.LUKEWARM_OCEAN), GenerationStep.Decoration.VEGETAL_DECORATION, PADDLEWEED);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.OCEAN, Biomes.COLD_OCEAN, Biomes.WARM_OCEAN, Biomes.LUKEWARM_OCEAN), GenerationStep.Decoration.VEGETAL_DECORATION, EELGRASS);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.WARM_OCEAN), GenerationStep.Decoration.VEGETAL_DECORATION, SEA_LETTUCE);        
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.COLD_OCEAN, Biomes.FROZEN_OCEAN), GenerationStep.Decoration.VEGETAL_DECORATION, COLD_SEA_LETTUCE);
    }

    
    // MOB SPAWNS
    private static void registerSpawns() {

        //RIVER
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.RIVER), MobCategory.WATER_AMBIENT, ShellfishEntities.CRAYFISH, 5, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.RIVER), MobCategory.WATER_AMBIENT, ShellfishEntities.CRAB, 3, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.RIVER), MobCategory.WATER_AMBIENT, ShellfishEntities.SHRIMP, 4, 1, 4);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.RIVER), MobCategory.WATER_AMBIENT, ShellfishEntities.SEA_SNAIL, 3, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.RIVER), MobCategory.AMBIENT, ShellfishEntities.CLAM, 4, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.RIVER), MobCategory.AMBIENT, ShellfishEntities.MUSSEL, 4, 1, 6);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.RIVER), MobCategory.AMBIENT, ShellfishEntities.MOSS_BALL, 2, 1, 6);


        //FROZEN RIVER
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.FROZEN_RIVER), MobCategory.WATER_AMBIENT, ShellfishEntities.CRAB, 2, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.FROZEN_RIVER), MobCategory.WATER_AMBIENT, ShellfishEntities.SHRIMP, 3, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.FROZEN_RIVER), MobCategory.WATER_AMBIENT, ShellfishEntities.SEA_SNAIL, 1, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.FROZEN_RIVER), MobCategory.AMBIENT, ShellfishEntities.CLAM, 2, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.FROZEN_RIVER), MobCategory.AMBIENT, ShellfishEntities.MUSSEL, 4, 1, 4);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.FROZEN_RIVER), MobCategory.AMBIENT, ShellfishEntities.MOSS_BALL, 2, 1, 4);


        //OCEAN
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.LUKEWARM_OCEAN, Biomes.WARM_OCEAN), MobCategory.WATER_CREATURE, ShellfishEntities.LOBSTER, 10, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.DEEP_LUKEWARM_OCEAN), MobCategory.WATER_CREATURE, ShellfishEntities.LOBSTER, 6, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.COLD_OCEAN), MobCategory.WATER_CREATURE, ShellfishEntities.LOBSTER, 3, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.DEEP_COLD_OCEAN), MobCategory.WATER_CREATURE, ShellfishEntities.LOBSTER, 2, 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.FROZEN_OCEAN), MobCategory.WATER_CREATURE, ShellfishEntities.LOBSTER, 1, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.DEEP_FROZEN_OCEAN), MobCategory.WATER_CREATURE, ShellfishEntities.LOBSTER, 1, 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.OCEAN), MobCategory.WATER_CREATURE, ShellfishEntities.LOBSTER, 3, 1, 3);
		BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.DEEP_OCEAN), MobCategory.WATER_CREATURE, ShellfishEntities.LOBSTER, 1, 1, 2);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.OCEAN, Biomes.DEEP_OCEAN), MobCategory.WATER_AMBIENT, ShellfishEntities.CRAB, 10, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.COLD_OCEAN, Biomes.DEEP_COLD_OCEAN), MobCategory.WATER_AMBIENT, ShellfishEntities.CRAB, 12, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.FROZEN_OCEAN, Biomes.DEEP_FROZEN_OCEAN), MobCategory.WATER_AMBIENT, ShellfishEntities.CRAB, 4, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.LUKEWARM_OCEAN, Biomes.WARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN), MobCategory.WATER_AMBIENT, ShellfishEntities.CRAB, 15, 1, 3);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.OCEAN, Biomes.DEEP_OCEAN), MobCategory.WATER_AMBIENT, ShellfishEntities.SHRIMP, 10, 2, 5);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.COLD_OCEAN, Biomes.DEEP_COLD_OCEAN), MobCategory.WATER_AMBIENT, ShellfishEntities.SHRIMP, 15, 2, 5);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.FROZEN_OCEAN, Biomes.DEEP_FROZEN_OCEAN), MobCategory.WATER_AMBIENT, ShellfishEntities.SHRIMP, 6, 1, 4);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.LUKEWARM_OCEAN, Biomes.WARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN), MobCategory.WATER_AMBIENT, ShellfishEntities.SHRIMP, 18, 2, 6);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.OCEAN, Biomes.DEEP_OCEAN), MobCategory.WATER_AMBIENT, ShellfishEntities.SEA_SNAIL, 10, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.COLD_OCEAN, Biomes.DEEP_COLD_OCEAN), MobCategory.WATER_AMBIENT, ShellfishEntities.SEA_SNAIL, 12, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.FROZEN_OCEAN, Biomes.DEEP_FROZEN_OCEAN), MobCategory.WATER_AMBIENT, ShellfishEntities.SEA_SNAIL, 3, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.LUKEWARM_OCEAN, Biomes.WARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN), MobCategory.WATER_AMBIENT, ShellfishEntities.SEA_SNAIL, 16, 1, 4);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.OCEAN, Biomes.DEEP_OCEAN), MobCategory.WATER_AMBIENT, ShellfishEntities.SEA_URCHIN, 10, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.COLD_OCEAN, Biomes.DEEP_COLD_OCEAN), MobCategory.WATER_AMBIENT, ShellfishEntities.SEA_URCHIN, 15, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.FROZEN_OCEAN, Biomes.DEEP_FROZEN_OCEAN), MobCategory.WATER_AMBIENT, ShellfishEntities.SEA_URCHIN, 7, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.LUKEWARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN), MobCategory.WATER_AMBIENT, ShellfishEntities.SEA_URCHIN, 17, 1, 4);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.WARM_OCEAN), MobCategory.WATER_AMBIENT, ShellfishEntities.SEA_URCHIN, 19, 1, 4);

		BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.OCEAN), MobCategory.AMBIENT, ShellfishEntities.CLAM, 10, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.COLD_OCEAN), MobCategory.AMBIENT, ShellfishEntities.CLAM, 11, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.FROZEN_OCEAN), MobCategory.AMBIENT, ShellfishEntities.CLAM, 4, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.LUKEWARM_OCEAN, Biomes.WARM_OCEAN), MobCategory.AMBIENT, ShellfishEntities.CLAM, 16, 1, 4);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.DEEP_OCEAN), MobCategory.AMBIENT, ShellfishEntities.CLAM, 6, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.DEEP_COLD_OCEAN), MobCategory.AMBIENT, ShellfishEntities.CLAM, 7, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.DEEP_FROZEN_OCEAN), MobCategory.AMBIENT, ShellfishEntities.CLAM, 3, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.DEEP_LUKEWARM_OCEAN), MobCategory.AMBIENT, ShellfishEntities.CLAM, 12, 1, 3);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.OCEAN), MobCategory.AMBIENT, ShellfishEntities.OYSTER, 10, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.COLD_OCEAN), MobCategory.AMBIENT, ShellfishEntities.OYSTER, 14, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.FROZEN_OCEAN), MobCategory.AMBIENT, ShellfishEntities.OYSTER, 6, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.LUKEWARM_OCEAN, Biomes.WARM_OCEAN), MobCategory.AMBIENT, ShellfishEntities.OYSTER, 15, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.DEEP_OCEAN), MobCategory.AMBIENT, ShellfishEntities.OYSTER, 4, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.DEEP_COLD_OCEAN), MobCategory.AMBIENT, ShellfishEntities.OYSTER, 7, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.DEEP_FROZEN_OCEAN), MobCategory.AMBIENT, ShellfishEntities.OYSTER, 3, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.DEEP_LUKEWARM_OCEAN), MobCategory.AMBIENT, ShellfishEntities.OYSTER, 9, 1, 2);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.OCEAN), MobCategory.AMBIENT, ShellfishEntities.MUSSEL, 10, 1, 6);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.COLD_OCEAN), MobCategory.AMBIENT, ShellfishEntities.MUSSEL, 14, 1, 6);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.FROZEN_OCEAN), MobCategory.AMBIENT, ShellfishEntities.MUSSEL, 6, 1, 6);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.LUKEWARM_OCEAN, Biomes.WARM_OCEAN), MobCategory.AMBIENT, ShellfishEntities.MUSSEL, 18, 1, 6);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.DEEP_OCEAN), MobCategory.AMBIENT, ShellfishEntities.MUSSEL, 6, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.DEEP_COLD_OCEAN), MobCategory.AMBIENT, ShellfishEntities.MUSSEL, 9, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.DEEP_FROZEN_OCEAN), MobCategory.AMBIENT, ShellfishEntities.MUSSEL, 3, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.DEEP_LUKEWARM_OCEAN), MobCategory.AMBIENT, ShellfishEntities.MUSSEL, 13, 1, 4);


        //BEACH
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.BEACH, Biomes.STONY_SHORE), MobCategory.WATER_AMBIENT, ShellfishEntities.CRAB, 5, 1, 2);
		BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.BEACH, Biomes.STONY_SHORE), MobCategory.WATER_AMBIENT, ShellfishEntities.SEA_SNAIL, 4, 1, 2);


        //SWAMP
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.SWAMP), MobCategory.WATER_AMBIENT, ShellfishEntities.CRAYFISH, 12, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.SWAMP), MobCategory.WATER_AMBIENT, ShellfishEntities.CRAB, 7, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.SWAMP), MobCategory.WATER_AMBIENT, ShellfishEntities.SHRIMP, 9, 1, 4);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.SWAMP), MobCategory.WATER_AMBIENT, ShellfishEntities.SEA_SNAIL, 6, 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.SWAMP), MobCategory.AMBIENT, ShellfishEntities.CLAM, 9, 1, 4);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.SWAMP), MobCategory.AMBIENT, ShellfishEntities.MUSSEL, 10, 1, 6);


        //MANGROVE SWAMP
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.MANGROVE_SWAMP), MobCategory.WATER_AMBIENT, ShellfishEntities.CRAYFISH, 15, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.MANGROVE_SWAMP), MobCategory.WATER_AMBIENT, ShellfishEntities.CRAB, 25, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.MANGROVE_SWAMP), MobCategory.WATER_AMBIENT, ShellfishEntities.SHRIMP, 20, 2, 5);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.MANGROVE_SWAMP), MobCategory.WATER_AMBIENT, ShellfishEntities.SEA_SNAIL, 20, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.MANGROVE_SWAMP), MobCategory.AMBIENT, ShellfishEntities.CLAM, 19, 1, 4);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.MANGROVE_SWAMP), MobCategory.AMBIENT, ShellfishEntities.OYSTER, 18, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.MANGROVE_SWAMP), MobCategory.AMBIENT, ShellfishEntities.MUSSEL, 21, 1, 6);
    }

    
    
    public static void register() {
        registerSpawns();
        registerBlockgen();
    }
}
