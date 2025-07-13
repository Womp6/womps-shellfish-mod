package womp.shellfishmod.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import womp.shellfishmod.ShellfishMod;
import womp.shellfishmod.worldgen.MarshFoliagePlacer;

import java.util.function.Supplier;

public class ShellfishWorldgen {

    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACER_TYPES = DeferredRegister.create(Registries.FOLIAGE_PLACER_TYPE, ShellfishMod.MOD_ID);

    public static final ResourceKey<Biome> MARSH = ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(ShellfishMod.MOD_ID, "marsh"));
    public static final Supplier<FoliagePlacerType<?>> MARSH_FOLIAGE_PLACER = FOLIAGE_PLACER_TYPES.register("marsh_foliage_placer", () -> new FoliagePlacerType<>(MarshFoliagePlacer.CODEC));

    public static void register(IEventBus bus) {
        FOLIAGE_PLACER_TYPES.register(bus);
    }
}
