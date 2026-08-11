package womp.shellfishmod.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import womp.shellfishmod.ShellfishMod;
import womp.shellfishmod.util.ShellfishTags;
import womp.shellfishmod.worldgen.MarshFoliagePlacer;

public class ShellfishWorldgen {

    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACER_TYPES = DeferredRegister.create(Registries.FOLIAGE_PLACER_TYPE, ShellfishMod.MOD_ID);

    public static final ResourceKey<Biome> MARSH = ShellfishTags.createKey("marsh", Registries.BIOME);
    public static final RegistryObject<FoliagePlacerType<?>> MARSH_FOLIAGE_PLACER = FOLIAGE_PLACER_TYPES.register("marsh_foliage_placer", () -> new FoliagePlacerType<>(MarshFoliagePlacer.CODEC));

    public static void register(BusGroup bus) {
        FOLIAGE_PLACER_TYPES.register(bus);
    }
}
