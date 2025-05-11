package womp.shellfishmod.villagers;

import com.google.common.collect.ImmutableSet;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import womp.shellfishmod.ShellfishMod;
import womp.shellfishmod.registry.ShellfishBlocks;
import womp.shellfishmod.registry.ShellfishSounds;

import java.util.function.Supplier;

// See ShellfishForgeEvents for trades
public class ShellfishTrapper {

    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(BuiltInRegistries.POINT_OF_INTEREST_TYPE, ShellfishMod.MOD_ID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS = DeferredRegister.create(BuiltInRegistries.VILLAGER_PROFESSION, ShellfishMod.MOD_ID);

    public static final Holder<PoiType> TRAP_POI = registerPOI("trap_poi", ShellfishBlocks.SHELLFISH_TRAP_BLOCK);
    public static final Holder<PoiType> REINFORCED_POI = registerPOI("reinforced_poi", ShellfishBlocks.REINFORCED_TRAP);

    public static final Holder<VillagerProfession> SHELLFISH_TRAPPER = VILLAGER_PROFESSIONS.register("shellfish_trapper", () -> new VillagerProfession("shellfish_trapper", x -> (x.value() == TRAP_POI.value() || x.value() == REINFORCED_POI.value()), x -> (x.value() == TRAP_POI.value() || x.value() == REINFORCED_POI.value()), ImmutableSet.of(), ImmutableSet.of(), ShellfishSounds.WORK_TRAPPER.get()));


    private static Holder<PoiType> registerPOI(String name, Supplier<Block> block) {
        return POI_TYPES.register(name, () -> new PoiType(ImmutableSet.copyOf(block.get().getStateDefinition().getPossibleStates()), 1, 1));
    }

    public static void register(IEventBus bus) {
        POI_TYPES.register(bus);
        VILLAGER_PROFESSIONS.register(bus);
    }
}
