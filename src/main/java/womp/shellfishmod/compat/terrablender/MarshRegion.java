package womp.shellfishmod.compat.terrablender;

import java.util.function.Consumer;

import com.mojang.datafixers.util.Pair;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.util.MultiNoiseUtil.NoiseHypercube;
import terrablender.api.Region;
import terrablender.api.RegionType;
import womp.shellfishmod.registry.ShellfishWorldgen;

public class MarshRegion extends Region {

    public MarshRegion() {
        super(Identifier.of("shellfish", "marsh"), RegionType.OVERWORLD, 3);
    }
    
    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<NoiseHypercube, RegistryKey<Biome>>> mapper) {
        this.addModifiedVanillaOverworldBiomes(mapper, mVOB -> {
            mVOB.replaceBiome(BiomeKeys.SWAMP, ShellfishWorldgen.MARSH);
            mVOB.replaceBiome(BiomeKeys.MANGROVE_SWAMP, ShellfishWorldgen.MARSH);
        });
    }
}
