package womp.shellfishmod.compat;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import womp.shellfishmod.registry.ShellfishEntities;

//This file allows spawning in the biomes of Biomes O' Plenty (if it is in use)
//Permission to do this has been received by Forstride
public class BiomesOPlenty {

    public static void registerExtraSpawns() {
        if (FabricLoader.getInstance().isModLoaded("biomesoplenty")) {

            //biomes
            ResourceKey<Biome> bayou = makeKey("bayou");
            ResourceKey<Biome> dune_beach = makeKey("dune_beach");
            ResourceKey<Biome> floodplain = makeKey("floodplain");
            ResourceKey<Biome> gravel_beach = makeKey("gravel_beach");
            ResourceKey<Biome> marsh = makeKey("marsh");
            ResourceKey<Biome> wetland = makeKey("wetland");

            //bayou
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(bayou), MobCategory.WATER_AMBIENT, ShellfishEntities.CRAYFISH, 10, 1, 3);
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(bayou), MobCategory.WATER_AMBIENT, ShellfishEntities.CRAB, 3, 1, 2);
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(bayou), MobCategory.WATER_AMBIENT, ShellfishEntities.SHRIMP, 6, 1, 4);
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(bayou), MobCategory.WATER_AMBIENT, ShellfishEntities.SEA_SNAIL, 9, 1, 3);
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(bayou), MobCategory.AMBIENT, ShellfishEntities.MOSS_BALL, 7, 1, 5);

            //dune beach and gravel beach
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(dune_beach, gravel_beach), MobCategory.WATER_AMBIENT, ShellfishEntities.CRAB, 5, 1, 2);
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(dune_beach, gravel_beach), MobCategory.WATER_AMBIENT, ShellfishEntities.SEA_SNAIL, 4, 1, 2);

            //floodplain
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(floodplain), MobCategory.WATER_AMBIENT, ShellfishEntities.CRAYFISH, 9, 1, 3);
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(floodplain), MobCategory.WATER_AMBIENT, ShellfishEntities.CRAB, 4, 1, 2);
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(floodplain), MobCategory.WATER_AMBIENT, ShellfishEntities.SHRIMP, 9, 2, 5);
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(floodplain), MobCategory.WATER_AMBIENT, ShellfishEntities.SEA_SNAIL, 7, 1, 3);
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(floodplain), MobCategory.AMBIENT, ShellfishEntities.CLAM, 7, 1, 4);
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(floodplain), MobCategory.AMBIENT, ShellfishEntities.MUSSEL, 9, 1, 5);
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(floodplain), MobCategory.AMBIENT, ShellfishEntities.MOSS_BALL, 8, 1, 6);

            //marsh (Biomes O' Plenty version) and wetland
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(marsh, wetland), MobCategory.WATER_AMBIENT, ShellfishEntities.CRAYFISH, 10, 1, 4);
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(marsh, wetland), MobCategory.WATER_AMBIENT, ShellfishEntities.CRAB, 7, 1, 3);
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(marsh, wetland), MobCategory.WATER_AMBIENT, ShellfishEntities.SHRIMP, 7, 2, 5);
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(marsh, wetland), MobCategory.WATER_AMBIENT, ShellfishEntities.SEA_SNAIL, 9, 1, 3);
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(marsh, wetland), MobCategory.AMBIENT, ShellfishEntities.CLAM, 8, 1, 4);
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(marsh, wetland), MobCategory.AMBIENT, ShellfishEntities.MUSSEL, 8, 1, 5);
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(marsh, wetland), MobCategory.AMBIENT, ShellfishEntities.MOSS_BALL, 9, 1, 6);
        }
    }

    private static ResourceKey<Biome> makeKey(String path) {
        return ResourceKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath("biomesoplenty", path));
    }
}
