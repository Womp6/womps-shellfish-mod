package womp.shellfishmod.villagers;

import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.trading.TradeSet;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import womp.shellfishmod.ShellfishMod;
import womp.shellfishmod.registry.ShellfishBlocks;
import womp.shellfishmod.registry.ShellfishSounds;
import womp.shellfishmod.util.ShellfishTags;

import java.util.function.Supplier;

// See ShellfishForgeEvents for trades
public class ShellfishTrapper {

    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(BuiltInRegistries.POINT_OF_INTEREST_TYPE, ShellfishMod.MOD_ID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS = DeferredRegister.create(BuiltInRegistries.VILLAGER_PROFESSION, ShellfishMod.MOD_ID);

    public static final Holder<PoiType> TRAP_POI = registerPOI("trap_poi", ShellfishBlocks.SHELLFISH_TRAP_BLOCK);
    public static final Holder<PoiType> REINFORCED_POI = registerPOI("reinforced_poi", ShellfishBlocks.REINFORCED_TRAP);

    public static final ResourceKey<PoiType> TRAP_KEY = registerPOIKey("trap_poi"), REINFORCED_KEY = registerPOIKey("reinforced_poi");

    public static final ResourceKey<VillagerProfession> SHELLFISH_TRAPPER_KEY = ShellfishTags.createKey("shellfish_trapper", Registries.VILLAGER_PROFESSION);

    public static final ResourceKey<TradeSet> TRAPPER_LEVEL_1 = registerTradeSetKey("shellfish_trapper/level_1");
    public static final ResourceKey<TradeSet> TRAPPER_LEVEL_2 = registerTradeSetKey("shellfish_trapper/level_2");
    public static final ResourceKey<TradeSet> TRAPPER_LEVEL_3 = registerTradeSetKey("shellfish_trapper/level_3");
    public static final ResourceKey<TradeSet> TRAPPER_LEVEL_4 = registerTradeSetKey("shellfish_trapper/level_4");
    public static final ResourceKey<TradeSet> TRAPPER_LEVEL_5 = registerTradeSetKey("shellfish_trapper/level_5");

    public static final Holder<VillagerProfession> SHELLFISH_TRAPPER = VILLAGER_PROFESSIONS.register("shellfish_trapper", () -> new VillagerProfession(Component.translatable("entity.minecraft.villager.shellfish.shellfish_trapper"), entry -> entry.is(TRAP_KEY) || entry.is(REINFORCED_KEY), entry -> entry.is(TRAP_KEY) || entry.is(REINFORCED_KEY),
            ImmutableSet.of(), ImmutableSet.of(), ShellfishSounds.WORK_TRAPPER.get(), Int2ObjectMap.ofEntries(
            Int2ObjectMap.entry(1, TRAPPER_LEVEL_1),
            Int2ObjectMap.entry(2, TRAPPER_LEVEL_2),
            Int2ObjectMap.entry(3, TRAPPER_LEVEL_3),
            Int2ObjectMap.entry(4, TRAPPER_LEVEL_4),
            Int2ObjectMap.entry(5, TRAPPER_LEVEL_5)
    )));


    private static Holder<PoiType> registerPOI(String name, Supplier<Block> block) {
        return POI_TYPES.register(name, () -> new PoiType(ImmutableSet.copyOf(block.get().getStateDefinition().getPossibleStates()), 1, 1));
    }

    private static ResourceKey<PoiType> registerPOIKey(String name) {
        return ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, Identifier.fromNamespaceAndPath("shellfish", name));
    }

    private static ResourceKey<TradeSet> registerTradeSetKey(String name) {
        return ResourceKey.create(Registries.TRADE_SET, Identifier.fromNamespaceAndPath("shellfish", name));
    }

    public static void register(IEventBus bus) {
        POI_TYPES.register(bus);
        VILLAGER_PROFESSIONS.register(bus);
    }
}
