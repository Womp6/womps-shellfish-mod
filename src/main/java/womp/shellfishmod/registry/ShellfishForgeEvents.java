package womp.shellfishmod.registry;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;
import womp.shellfishmod.ShellfishMod;
import womp.shellfishmod.command.PassiveShellfishCommand;
import womp.shellfishmod.entity.SeaUrchinEntity;
import womp.shellfishmod.villagers.ShellfishTrapper;

import java.util.List;
import java.util.Optional;

@Mod.EventBusSubscriber(modid = ShellfishMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ShellfishForgeEvents {

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent register) {
        new PassiveShellfishCommand(register.getDispatcher());
        ConfigCommand.register(register.getDispatcher());
    }

    @SubscribeEvent
    public static void attackEntity(AttackEntityEvent event) {
        if (event.getTarget() instanceof SeaUrchinEntity) {
            Player player = event.getEntity();
            if (player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
                player.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 0));
                player.hurt(player.damageSources().magic(), 2.0f);
            }
        }
    }

    @SubscribeEvent
    public static void customTrades(VillagerTradesEvent event) {
        if (event.getType() == ShellfishTrapper.SHELLFISH_TRAPPER_KEY) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> factories = event.getTrades();

            // level 1
            factories.get(1).add((entity, random) -> new MerchantOffer(
                    new ItemCost(ShellfishItems.RAW_CRAYFISH.get(), 12),
                    new ItemStack(Items.EMERALD, 1),
                    16, 2, .02f
            ));
            factories.get(1).add((entity, random) -> new MerchantOffer(
                    new ItemCost(ShellfishItems.RAW_LOBSTER.get(), 12),
                    new ItemStack(Items.EMERALD, 1),
                    16, 2, .03f
            ));
            factories.get(1).add((entity, random) -> new MerchantOffer(
                    new ItemCost(ShellfishItems.RAW_CRAB.get(), 12),
                    new ItemStack(Items.EMERALD, 1),
                    16, 2, .02f
            ));
            factories.get(1).add((entity, random) -> new MerchantOffer(
                    new ItemCost(ShellfishItems.RAW_SHRIMP.get(), 12),
                    new ItemStack(Items.EMERALD, 1),
                    16, 2, .02f
            ));
            factories.get(1).add((entity, random) -> new MerchantOffer(
                    new ItemCost(ShellfishItems.RAW_SEA_SNAIL.get(), 8),
                    new ItemStack(Items.EMERALD, 1),
                    16, 2, .02f
            ));
            factories.get(1).add((entity, random) -> new MerchantOffer(
                    new ItemCost(ShellfishItems.RAW_UNI.get(), 16),
                    new ItemStack(Items.EMERALD, 1),
                    16, 2, .02f
            ));
            factories.get(1).add((entity, random) -> new MerchantOffer(
                    new ItemCost(ShellfishItems.RAW_CLAM.get(), 16),
                    new ItemStack(Items.EMERALD, 1),
                    16, 2, .02f
            ));
            factories.get(1).add((entity, random) -> new MerchantOffer(
                    new ItemCost(ShellfishItems.RAW_OYSTER.get(), 16),
                    new ItemStack(Items.EMERALD, 1),
                    16, 2, .02f
            ));
            factories.get(1).add((entity, random) -> new MerchantOffer(
                    new ItemCost(ShellfishItems.RAW_MUSSEL.get(), 16),
                    new ItemStack(Items.EMERALD, 1),
                    16, 2, .02f
            ));
            factories.get(1).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ShellfishItems.SHELLFISH_BAIT.get(), 4),
                    16, 2, .02f
            ));

            // level 2
            factories.get(2).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    Optional.of(new ItemCost(ShellfishItems.RAW_CRAYFISH.get(), 6)),
                    new ItemStack(ShellfishItems.COOKED_CRAYFISH.get(), 6),
                    8, 10, .03f
            ));
            factories.get(2).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    Optional.of(new ItemCost(ShellfishItems.RAW_LOBSTER.get(), 6)),
                    new ItemStack(ShellfishItems.COOKED_LOBSTER.get(), 6),
                    8, 10, .03f
            ));
            factories.get(2).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    Optional.of(new ItemCost(ShellfishItems.RAW_CRAB.get(), 6)),
                    new ItemStack(ShellfishItems.COOKED_CRAB.get(), 6),
                    8, 10, .03f
            ));
            factories.get(2).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    Optional.of(new ItemCost(ShellfishItems.RAW_SHRIMP.get(), 6)),
                    new ItemStack(ShellfishItems.COOKED_SHRIMP.get(), 6),
                    8, 10, .03f
            ));
            factories.get(2).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    Optional.of(new ItemCost(ShellfishItems.RAW_SEA_SNAIL.get(), 4)),
                    new ItemStack(ShellfishItems.COOKED_SEA_SNAIL.get(), 4),
                    8, 10, .03f
            ));
            factories.get(2).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    Optional.of(new ItemCost(ShellfishItems.RAW_UNI.get(), 8)),
                    new ItemStack(ShellfishItems.COOKED_UNI.get(), 8),
                    8, 5, .03f
            ));
            factories.get(2).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    Optional.of(new ItemCost(ShellfishItems.RAW_CLAM.get(), 8)),
                    new ItemStack(ShellfishItems.COOKED_CLAM.get(), 8),
                    8, 5, .03f
            ));
            factories.get(2).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    Optional.of(new ItemCost(ShellfishItems.RAW_OYSTER.get(), 8)),
                    new ItemStack(ShellfishItems.COOKED_OYSTER.get(), 8),
                    8, 5, .03f
            ));
            factories.get(2).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    Optional.of(new ItemCost(ShellfishItems.RAW_MUSSEL.get(), 8)),
                    new ItemStack(ShellfishItems.COOKED_MUSSEL.get(), 8),
                    8, 5, .03f
            ));

            // level 3
            factories.get(3).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 3),
                    new ItemStack(ShellfishItems.CRAYFISH_BUCKET.get(), 1),
                    5, 8, .05f
            ));
            factories.get(3).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 4),
                    new ItemStack(ShellfishItems.LOBSTER_BUCKET.get(), 1),
                    5, 15, .05f
            ));
            factories.get(3).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 3),
                    new ItemStack(ShellfishItems.CRAB_BUCKET.get(), 1),
                    5, 8, .05f
            ));
            factories.get(3).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 3),
                    new ItemStack(ShellfishItems.SHRIMP_BUCKET.get(), 1),
                    5, 8, .05f
            ));
            factories.get(3).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 4),
                    new ItemStack(ShellfishItems.SEA_SNAIL_BUCKET.get(), 1),
                    5, 10, .05f
            ));
            factories.get(3).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 5),
                    new ItemStack(ShellfishItems.SEA_URCHIN_BUCKET.get(), 1),
                    5, 15, .05f
            ));
            factories.get(3).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 3),
                    new ItemStack(ShellfishItems.CLAM_BUCKET.get(), 1),
                    5, 8, .05f
            ));
            factories.get(3).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 3),
                    new ItemStack(ShellfishItems.OYSTER_BUCKET.get(), 1),
                    5, 8, .05f
            ));
            factories.get(3).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 3),
                    new ItemStack(ShellfishItems.MUSSEL_BUCKET.get(), 1),
                    5, 8, .05f
            ));
            factories.get(3).add((entity, random) -> new MerchantOffer(
                    new ItemCost(ShellfishItems.ROCKWEED.get(), 32),
                    new ItemStack(Items.EMERALD, 1),
                    16, 5, .02f
            ));
            factories.get(3).add((entity, random) -> new MerchantOffer(
                    new ItemCost(ShellfishItems.WATER_LETTUCE.get(), 32),
                    new ItemStack(Items.EMERALD, 1),
                    16, 5, .02f
            ));
            factories.get(3).add((entity, random) -> new MerchantOffer(
                    new ItemCost(ShellfishItems.PADDLEWEED.get(), 16),
                    new ItemStack(Items.EMERALD, 1),
                    16, 5, .02f
            ));
            factories.get(3).add((entity, random) -> new MerchantOffer(
                    new ItemCost(ShellfishItems.EELGRASS.get(), 16),
                    new ItemStack(Items.EMERALD, 1),
                    16, 5, .02f
            ));
            factories.get(3).add((entity, random) -> new MerchantOffer(
                    new ItemCost(ShellfishItems.SEA_LETTUCE.get(), 16),
                    new ItemStack(Items.EMERALD, 1),
                    16, 5, .02f
            ));

            // level 4
            factories.get(4).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 2),
                    new ItemStack(ShellfishItems.SHELLFISH_STEW.get(), 1),
                    8, 25, .05f
            ));
            factories.get(4).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ShellfishItems.LIGHT_SHELLFISH_STEW.get(), 1),
                    8, 15, .05f
            ));
            factories.get(4).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 4),
                    new ItemStack(ShellfishItems.CRAYFISH_BOIL.get(), 1),
                    8, 35, .05f
            ));
            factories.get(4).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 2),
                    new ItemStack(ShellfishItems.GUMBO.get(), 1),
                    8, 25, .05f
            ));
            factories.get(4).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 2),
                    new ItemStack(ShellfishItems.CRAYFISH_BISQUE.get(), 1),
                    8, 25, .05f
            ));
            factories.get(4).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ShellfishItems.BOILED_LOBSTER.get(), 1),
                    8, 15, .05f
            ));
            factories.get(4).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 3),
                    new ItemStack(ShellfishItems.LOBSTER_ROLL.get(), 2),
                    8, 30, .05f
            ));
            factories.get(4).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ShellfishItems.LOBSTER_SALAD.get(), 1),
                    8, 15, .05f
            ));
            factories.get(4).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ShellfishItems.CRAB_CAKE.get(), 4),
                    8, 15, .05f
            ));
            factories.get(4).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ShellfishItems.CRAB_DIP.get(), 1),
                    8, 15, .05f
            ));
            factories.get(4).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ShellfishItems.CRAB_LOUIE.get(), 1),
                    8, 15, .05f
            ));
            factories.get(4).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 3),
                    new ItemStack(ShellfishItems.COOKED_SHRIMP_SKEWER.get(), 2),
                    8, 30, .05f
            ));
            factories.get(4).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 3),
                    new ItemStack(ShellfishItems.SHRIMP_SCAMPI.get(), 1),
                    8, 30, .05f
            ));
            factories.get(4).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 2),
                    new ItemStack(ShellfishItems.SHRIMP_TACO.get(), 2),
                    8, 25, .05f
            ));
            factories.get(4).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ShellfishItems.ESCARGOT.get(), 4),
                    8, 15, .05f
            ));
            factories.get(4).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 2),
                    new ItemStack(ShellfishItems.SNAIL_GRATIN.get(), 1),
                    8, 25, .05f
            ));
            factories.get(4).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ShellfishItems.SNAIL_SOUP.get(), 1),
                    8, 15, .05f
            ));
            factories.get(4).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ShellfishItems.UNI_SUSHI.get(), 5),
                    8, 15, .05f
            ));
            factories.get(4).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ShellfishItems.UNI_CHAWANMUSHI.get(), 1),
                    8, 15, .05f
            ));
            factories.get(4).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ShellfishItems.UNI_SALAD.get(), 1),
                    8, 15, .05f
            ));
            factories.get(4).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ShellfishItems.BAKED_CLAM.get(), 4),
                    8, 15, .05f
            ));
            factories.get(4).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 2),
                    new ItemStack(ShellfishItems.CLAM_STRIP.get(), 3),
                    8, 25, .05f
            ));
            factories.get(4).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 2),
                    new ItemStack(ShellfishItems.CLAM_CHOWDER.get(), 1),
                    8, 25, .05f
            ));
            factories.get(4).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ShellfishItems.OYSTER_ROCKEFELLER.get(), 4),
                    8, 15, .05f
            ));
            factories.get(4).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 2),
                    new ItemStack(ShellfishItems.FRIED_OYSTER.get(), 3),
                    8, 25, .05f
            ));
            factories.get(4).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 2),
                    new ItemStack(ShellfishItems.OYSTER_CHOWDER.get(), 1),
                    8, 25, .05f
            ));
            factories.get(4).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ShellfishItems.STUFFED_MUSSEL.get(), 4),
                    8, 15, .05f
            ));
            factories.get(4).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ShellfishItems.MUSSEL_SOUP.get(), 1),
                    8, 15, .05f
            ));
            factories.get(4).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 2),
                    new ItemStack(ShellfishItems.MOULES_MARINIERES.get(), 1),
                    8, 25, .05f
            ));

            // level 5
            factories.get(5).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 5),
                    new ItemStack(ShellfishItems.SHELLFISH_TRAP.get(), 1),
                    8, 30, .08f
            ));
            factories.get(5).add((entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 5),
                    new ItemStack(ShellfishItems.MOSS_BALL_BUCKET.get(), 1),
                    5, 30, .08f
            ));
            factories.get(5).add((entity, random) -> new MerchantOffer(
                    new ItemCost(ShellfishItems.PEARL.get(), 1),
                    new ItemStack(Items.EMERALD, 1),
                    16, 2, .08f
            ));
        }
    }
}
