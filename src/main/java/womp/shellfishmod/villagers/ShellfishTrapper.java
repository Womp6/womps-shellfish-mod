package womp.shellfishmod.villagers;

import com.google.common.collect.ImmutableSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import womp.shellfishmod.ShellfishMod;
import womp.shellfishmod.registry.ShellfishBlocks;
import womp.shellfishmod.registry.ShellfishSounds;
import womp.shellfishmod.util.ShellfishTags;

import java.util.function.Supplier;

// See ShellfishForgeEvents for trades
public class ShellfishTrapper {

    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, ShellfishMod.MOD_ID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS = DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, ShellfishMod.MOD_ID);

    public static final RegistryObject<PoiType> TRAP_POI = registerPOI("trap_poi", ShellfishBlocks.SHELLFISH_TRAP_BLOCK);
    public static final RegistryObject<PoiType> REINFORCED_POI = registerPOI("reinforced_poi", ShellfishBlocks.REINFORCED_TRAP);

    public static final ResourceKey<PoiType> TRAP_KEY = registerPOIKey("trap_poi"), REINFORCED_KEY = registerPOIKey("reinforced_poi");

    public static final ResourceKey<VillagerProfession> SHELLFISH_TRAPPER_KEY = ShellfishTags.createKey("shellfish_trapper", Registries.VILLAGER_PROFESSION);
    public static final RegistryObject<VillagerProfession> SHELLFISH_TRAPPER = VILLAGER_PROFESSIONS.register("shellfish_trapper", () -> new VillagerProfession(Component.translatable("entity.minecraft.villager.shellfish.shellfish_trapper"), x -> (x.is(TRAP_KEY) || x.is(REINFORCED_KEY)), x -> (x.is(TRAP_KEY) || x.is(REINFORCED_KEY)), ImmutableSet.of(), ImmutableSet.of(), ShellfishSounds.WORK_TRAPPER.get()));


    private static RegistryObject<PoiType> registerPOI(String name, Supplier<Block> block) {
        return POI_TYPES.register(name, () -> new PoiType(ImmutableSet.copyOf(block.get().getStateDefinition().getPossibleStates()), 1, 1));
    }

    private static ResourceKey<PoiType> registerPOIKey(String name) {
        return ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, ResourceLocation.fromNamespaceAndPath("shellfish", name));
    }

    public static void register(BusGroup bus) {
        POI_TYPES.register(bus);
        VILLAGER_PROFESSIONS.register(bus);
    }
}
