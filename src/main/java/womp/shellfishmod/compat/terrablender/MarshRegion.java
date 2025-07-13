package womp.shellfishmod.compat.terrablender;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.Region;
import terrablender.api.RegionType;
import womp.shellfishmod.registry.ShellfishWorldgen;

import java.util.function.Consumer;

public class MarshRegion extends Region {

    public MarshRegion() {
        super(ResourceLocation.fromNamespaceAndPath("shellfish", "marsh"), RegionType.OVERWORLD, 3);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        this.addModifiedVanillaOverworldBiomes(mapper, mVOB -> {
            mVOB.replaceBiome(Biomes.SWAMP, ShellfishWorldgen.MARSH);
            mVOB.replaceBiome(Biomes.MANGROVE_SWAMP, ShellfishWorldgen.MARSH);
        });
    }
}
