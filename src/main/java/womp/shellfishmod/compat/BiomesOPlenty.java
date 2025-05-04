package womp.shellfishmod.compat;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import womp.shellfishmod.registry.ShellfishEntities;

//This file allows spawning in the biomes of Biomes O' Plenty (if it is in use)
//Permission to do this has been received by Forstride
public class BiomesOPlenty {

    public static void registerExtraSpawns() {
        if (FabricLoader.getInstance().isModLoaded("biomesoplenty")) {

            //biomes
            RegistryKey<Biome> bayou = makeKey("bayou");
            RegistryKey<Biome> dune_beach = makeKey("dune_beach");
            RegistryKey<Biome> floodplain = makeKey("floodplain");
            RegistryKey<Biome> gravel_beach = makeKey("gravel_beach");
            RegistryKey<Biome> marsh = makeKey("marsh");
            RegistryKey<Biome> wetland = makeKey("wetland");

            //bayou
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(bayou), SpawnGroup.WATER_AMBIENT, ShellfishEntities.CRAYFISH, 10, 1, 3);
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(bayou), SpawnGroup.WATER_AMBIENT, ShellfishEntities.CRAB, 3, 1, 2);
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(bayou), SpawnGroup.WATER_AMBIENT, ShellfishEntities.SHRIMP, 6, 1, 4);
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(bayou), SpawnGroup.WATER_AMBIENT, ShellfishEntities.SEA_SNAIL, 9, 1, 3);
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(bayou), SpawnGroup.AMBIENT, ShellfishEntities.MOSS_BALL, 7, 1, 5);

            //dune beach and gravel beach
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(dune_beach, gravel_beach), SpawnGroup.WATER_AMBIENT, ShellfishEntities.CRAB, 5, 1, 2);
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(dune_beach, gravel_beach), SpawnGroup.WATER_AMBIENT, ShellfishEntities.SEA_SNAIL, 4, 1, 2);

            //floodplain
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(floodplain), SpawnGroup.WATER_AMBIENT, ShellfishEntities.CRAYFISH, 9, 1, 3);
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(floodplain), SpawnGroup.WATER_AMBIENT, ShellfishEntities.CRAB, 4, 1, 2);
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(floodplain), SpawnGroup.WATER_AMBIENT, ShellfishEntities.SHRIMP, 9, 2, 5);
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(floodplain), SpawnGroup.WATER_AMBIENT, ShellfishEntities.SEA_SNAIL, 7, 1, 3);
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(floodplain), SpawnGroup.AMBIENT, ShellfishEntities.CLAM, 7, 1, 4);
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(floodplain), SpawnGroup.AMBIENT, ShellfishEntities.MUSSEL, 9, 1, 5);
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(floodplain), SpawnGroup.AMBIENT, ShellfishEntities.MOSS_BALL, 8, 1, 6);

            //marsh (Biomes O' Plenty version) and wetland
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(marsh, wetland), SpawnGroup.WATER_AMBIENT, ShellfishEntities.CRAYFISH, 10, 1, 4);
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(marsh, wetland), SpawnGroup.WATER_AMBIENT, ShellfishEntities.CRAB, 7, 1, 3);
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(marsh, wetland), SpawnGroup.WATER_AMBIENT, ShellfishEntities.SHRIMP, 7, 2, 5);
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(marsh, wetland), SpawnGroup.WATER_AMBIENT, ShellfishEntities.SEA_SNAIL, 9, 1, 3);
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(marsh, wetland), SpawnGroup.AMBIENT, ShellfishEntities.CLAM, 8, 1, 4);
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(marsh, wetland), SpawnGroup.AMBIENT, ShellfishEntities.MUSSEL, 8, 1, 5);
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(marsh, wetland), SpawnGroup.AMBIENT, ShellfishEntities.MOSS_BALL, 9, 1, 6);
        }
    }

    private static RegistryKey<Biome> makeKey(String path) {
        return RegistryKey.of(RegistryKeys.BIOME, new Identifier("biomesoplenty", path));
    }
}
