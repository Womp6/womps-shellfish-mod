package womp.shellfishmod.compat.terrablender;

import java.util.function.Consumer;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate.ParameterPoint;
import com.mojang.datafixers.util.Pair;
import terrablender.api.Region;
import terrablender.api.RegionType;
import womp.shellfishmod.registry.ShellfishWorldgen;

public class MarshRegion extends Region {

    public MarshRegion() {
        super(Identifier.fromNamespaceAndPath("shellfish", "marsh"), RegionType.OVERWORLD, 3);
    }
    
    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper) {
        this.addModifiedVanillaOverworldBiomes(mapper, mVOB -> {
            mVOB.replaceBiome(Biomes.SWAMP, ShellfishWorldgen.MARSH);
            mVOB.replaceBiome(Biomes.MANGROVE_SWAMP, ShellfishWorldgen.MARSH);
        });
    }
}
