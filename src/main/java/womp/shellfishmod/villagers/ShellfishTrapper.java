package womp.shellfishmod.villagers;

import java.util.Optional;

import com.google.common.collect.ImmutableSet;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;
import womp.shellfishmod.registry.ShellfishBlocks;
import womp.shellfishmod.registry.ShellfishItems;
import womp.shellfishmod.registry.ShellfishSounds;

public class ShellfishTrapper {
    
    public static final PointOfInterestType TRAP_POI = registerPOI("trap_poi", ShellfishBlocks.SHELLFISH_TRAP_BLOCK);
    public static final PointOfInterestType REINFORCED_POI = registerPOI("reinforced_poi", ShellfishBlocks.REINFORCED_TRAP);

    public static final RegistryKey<PointOfInterestType> TRAP_KEY = registerPOIKey("trap_poi"), REINFORCED_KEY = registerPOIKey("reinforced_poi");

    public static final VillagerProfession SHELLFISH_TRAPPER = Registry.register(Registries.VILLAGER_PROFESSION, new Identifier("shellfish", "shellfish_trapper"),
                new VillagerProfession("shellfish_trapper", entry -> true, entry -> entry.matchesKey(TRAP_KEY) || entry.matchesKey(REINFORCED_KEY),
                        ImmutableSet.of(), ImmutableSet.of(), ShellfishSounds.WORK_TRAPPER));

    private static PointOfInterestType registerPOI(String name, Block block) {
        return PointOfInterestHelper.register(new Identifier("shellfish", name), 1, 1, ImmutableSet.copyOf(block.getStateManager().getStates())); 
    }

    private static RegistryKey<PointOfInterestType> registerPOIKey(String name) {
        return RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, new Identifier("shellfish", name));
    }

    public static void register() {
        TradeOfferHelper.registerVillagerOffers(SHELLFISH_TRAPPER, 1, factories -> {
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(ShellfishItems.RAW_CRAYFISH, 12),
                new ItemStack(Items.EMERALD, 1),
                16, 2, .02f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(ShellfishItems.RAW_LOBSTER, 12),
                new ItemStack(Items.EMERALD, 1),
                16, 2, .03f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(ShellfishItems.RAW_CRAB, 12),
                new ItemStack(Items.EMERALD, 1),
                16, 2, .02f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(ShellfishItems.RAW_SHRIMP, 12),
                new ItemStack(Items.EMERALD, 1),
                16, 2, .02f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(ShellfishItems.RAW_SEA_SNAIL, 8),
                new ItemStack(Items.EMERALD, 1),
                16, 2, .02f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(ShellfishItems.RAW_UNI, 16),
                new ItemStack(Items.EMERALD, 1),
                16, 2, .02f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(ShellfishItems.RAW_CLAM, 16),
                new ItemStack(Items.EMERALD, 1),
                16, 2, .02f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(ShellfishItems.RAW_OYSTER, 16),
                new ItemStack(Items.EMERALD, 1),
                16, 2, .02f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(ShellfishItems.RAW_MUSSEL, 16),
                new ItemStack(Items.EMERALD, 1),
                16, 2, .02f
            )));
            factories.add((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 1),
                new ItemStack(ShellfishItems.SHELLFISH_BAIT, 4),
                16, 2, .02f
            ));
        });
        TradeOfferHelper.registerVillagerOffers(SHELLFISH_TRAPPER, 2, factories -> {
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 1),
                Optional.of(new TradedItem(ShellfishItems.RAW_CRAYFISH, 6)),
                new ItemStack(ShellfishItems.COOKED_CRAYFISH, 6),
                8, 10, .03f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 1),
                Optional.of(new TradedItem(ShellfishItems.RAW_LOBSTER, 6)),
                new ItemStack(ShellfishItems.COOKED_LOBSTER, 6),
                8, 10, .03f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 1),
                Optional.of(new TradedItem(ShellfishItems.RAW_CRAB, 6)),
                new ItemStack(ShellfishItems.COOKED_CRAB, 6),
                8, 10, .03f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 1),
                Optional.of(new TradedItem(ShellfishItems.RAW_SHRIMP, 6)),
                new ItemStack(ShellfishItems.COOKED_SHRIMP, 6),
                8, 10, .03f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 1),
                Optional.of(new TradedItem(ShellfishItems.RAW_SEA_SNAIL, 4)),
                new ItemStack(ShellfishItems.COOKED_SEA_SNAIL, 4),
                8, 10, .03f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 1),
                Optional.of(new TradedItem(ShellfishItems.RAW_UNI, 8)),
                new ItemStack(ShellfishItems.COOKED_UNI, 8),
                8, 5, .03f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 1),
                Optional.of(new TradedItem(ShellfishItems.RAW_CLAM, 8)),
                new ItemStack(ShellfishItems.COOKED_CLAM, 8),
                8, 5, .03f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 1),
                Optional.of(new TradedItem(ShellfishItems.RAW_OYSTER, 8)),
                new ItemStack(ShellfishItems.COOKED_OYSTER, 8),
                8, 5, .03f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 1),
                Optional.of(new TradedItem(ShellfishItems.RAW_MUSSEL, 8)),
                new ItemStack(ShellfishItems.COOKED_MUSSEL, 8),
                8, 5, .03f
            )));
        });
        TradeOfferHelper.registerVillagerOffers(SHELLFISH_TRAPPER, 3, factories -> {
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 3),
                new ItemStack(ShellfishItems.CRAYFISH_BUCKET, 1),
                5, 8, .05f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 4),
                new ItemStack(ShellfishItems.LOBSTER_BUCKET, 1),
                5, 15, .05f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 3),
                new ItemStack(ShellfishItems.CRAB_BUCKET, 1),
                5, 8, .05f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 3),
                new ItemStack(ShellfishItems.SHRIMP_BUCKET, 1),
                5, 8, .05f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 4),
                new ItemStack(ShellfishItems.SEA_SNAIL_BUCKET, 1),
                5, 10, .05f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 5),
                new ItemStack(ShellfishItems.SEA_URCHIN_BUCKET, 1),
                5, 15, .05f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 3),
                new ItemStack(ShellfishItems.CLAM_BUCKET, 1),
                5, 8, .05f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 3),
                new ItemStack(ShellfishItems.OYSTER_BUCKET, 1),
                5, 8, .05f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 3),
                new ItemStack(ShellfishItems.MUSSEL_BUCKET, 1),
                5, 8, .05f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(ShellfishBlocks.ROCKWEED, 32),
                new ItemStack(Items.EMERALD, 1),
                16, 5, .02f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(ShellfishBlocks.WATER_LETTUCE, 32),
                new ItemStack(Items.EMERALD, 1),
                16, 5, .02f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(ShellfishBlocks.PADDLEWEED, 16),
                new ItemStack(Items.EMERALD, 1),
                16, 5, .02f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(ShellfishBlocks.EELGRASS, 16),
                new ItemStack(Items.EMERALD, 1),
                16, 5, .02f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(ShellfishBlocks.SEA_LETTUCE, 16),
                new ItemStack(Items.EMERALD, 1),
                16, 5, .02f
            )));
        });
        TradeOfferHelper.registerVillagerOffers(SHELLFISH_TRAPPER, 4, factories -> {
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 2),
                new ItemStack(ShellfishItems.SHELLFISH_STEW, 1),
                8, 25, .05f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 1),
                new ItemStack(ShellfishItems.LIGHT_SHELLFISH_STEW, 1),
                8, 15, .05f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 4),
                new ItemStack(ShellfishItems.CRAYFISH_BOIL, 1),
                8, 35, .05f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 2),
                new ItemStack(ShellfishItems.GUMBO, 1),
                8, 25, .05f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 2),
                new ItemStack(ShellfishItems.CRAYFISH_BISQUE, 1),
                8, 25, .05f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 1),
                new ItemStack(ShellfishItems.BOILED_LOBSTER, 1),
                8, 15, .05f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 3),
                new ItemStack(ShellfishItems.LOBSTER_ROLL, 2),
                8, 30, .05f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 1),
                new ItemStack(ShellfishItems.LOBSTER_SALAD, 1),
                8, 15, .05f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 1),
                new ItemStack(ShellfishItems.CRAB_CAKE, 4),
                8, 15, .05f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 1),
                new ItemStack(ShellfishItems.CRAB_DIP, 1),
                8, 15, .05f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 1),
                new ItemStack(ShellfishItems.CRAB_LOUIE, 1),
                8, 15, .05f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 3),
                new ItemStack(ShellfishItems.COOKED_SHRIMP_SKEWER, 2),
                8, 30, .05f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 3),
                new ItemStack(ShellfishItems.SHRIMP_SCAMPI, 1),
                8, 30, .05f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 2),
                new ItemStack(ShellfishItems.SHRIMP_TACO, 2),
                8, 25, .05f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 1),
                new ItemStack(ShellfishItems.ESCARGOT, 4),
                8, 15, .05f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 2),
                new ItemStack(ShellfishItems.SNAIL_GRATIN, 1),
                8, 25, .05f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 1),
                new ItemStack(ShellfishItems.SNAIL_SOUP, 1),
                8, 15, .05f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 1),
                new ItemStack(ShellfishItems.UNI_SUSHI, 5),
                8, 15, .05f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 1),
                new ItemStack(ShellfishItems.UNI_CHAWANMUSHI, 1),
                8, 15, .05f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 1),
                new ItemStack(ShellfishItems.UNI_SALAD, 1),
                8, 15, .05f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 1),
                new ItemStack(ShellfishItems.BAKED_CLAM, 4),
                8, 15, .05f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 2),
                new ItemStack(ShellfishItems.CLAM_STRIP, 3),
                8, 25, .05f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 2),
                new ItemStack(ShellfishItems.CLAM_CHOWDER, 1),
                8, 25, .05f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 1),
                new ItemStack(ShellfishItems.OYSTER_ROCKEFELLER, 4),
                8, 15, .05f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 2),
                new ItemStack(ShellfishItems.FRIED_OYSTER, 3),
                8, 25, .05f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 2),
                new ItemStack(ShellfishItems.OYSTER_CHOWDER, 1),
                8, 25, .05f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 1),
                new ItemStack(ShellfishItems.STUFFED_MUSSEL, 4),
                8, 15, .05f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 1),
                new ItemStack(ShellfishItems.MUSSEL_SOUP, 1),
                8, 15, .05f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 2),
                new ItemStack(ShellfishItems.MOULES_MARINIERES, 1),
                8, 25, .05f
            )));
        });
        TradeOfferHelper.registerVillagerOffers(SHELLFISH_TRAPPER, 5, factories -> {
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 5),
                new ItemStack(ShellfishBlocks.SHELLFISH_TRAP_BLOCK, 1),
                8, 30, .08f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(Items.EMERALD, 5),
                new ItemStack(ShellfishItems.MOSS_BALL_BUCKET, 1),
                5, 30, .08f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                new TradedItem(ShellfishItems.PEARL, 1),
                new ItemStack(Items.EMERALD, 1),
                16, 2, .08f
            )));
        });
    }
}