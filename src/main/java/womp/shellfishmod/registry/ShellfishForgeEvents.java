package womp.shellfishmod.registry;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.npc.villager.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;
import womp.shellfishmod.ShellfishMod;
import womp.shellfishmod.command.PassiveShellfishCommand;
import womp.shellfishmod.entity.*;
import womp.shellfishmod.villagers.ShellfishTrapper;

import java.util.List;
import java.util.Optional;

@Mod.EventBusSubscriber(modid = ShellfishMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.BOTH)
public class ShellfishForgeEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ShellfishEntities.CRAYFISH.get(), CrayfishEntity.createCrayfishAttributes().build());
        event.put(ShellfishEntities.LOBSTER.get(), LobsterEntity.createLobsterAttributes().build());
        event.put(ShellfishEntities.CRAB.get(), CrabEntity.createCrabAttributes().build());
        event.put(ShellfishEntities.SHRIMP.get(), ShrimpEntity.createShrimpAttributes().build());
        event.put(ShellfishEntities.SEA_SNAIL.get(), SeaSnailEntity.createSeaSnailAttributes().build());
        event.put(ShellfishEntities.SEA_URCHIN.get(), SeaUrchinEntity.createSeaUrchinAttributes().build());
        event.put(ShellfishEntities.CLAM.get(), ClamEntity.createClamAttributes().build());
        event.put(ShellfishEntities.OYSTER.get(), OysterEntity.createOysterAttributes().build());
        event.put(ShellfishEntities.MUSSEL.get(), MusselEntity.createMusselAttributes().build());
        event.put(ShellfishEntities.MOSS_BALL.get(), MossBallEntity.createMossBallAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawnPlacement(SpawnPlacementRegisterEvent event) {
        event.register(ShellfishEntities.CRAYFISH.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                CrayfishEntity::canSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ShellfishEntities.LOBSTER.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                LobsterEntity::canSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ShellfishEntities.CRAB.get(), SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                CrabEntity::canSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ShellfishEntities.SHRIMP.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                ShrimpEntity::canSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ShellfishEntities.SEA_SNAIL.get(), SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                SeaSnailEntity::canSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ShellfishEntities.SEA_URCHIN.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                SeaUrchinEntity::canSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ShellfishEntities.CLAM.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                ClamEntity::canSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ShellfishEntities.OYSTER.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                OysterEntity::canSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ShellfishEntities.MUSSEL.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                MusselEntity::canSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ShellfishEntities.MOSS_BALL.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                MossBallEntity::canSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
    }

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
            factories.get(1).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(ShellfishItems.RAW_CRAYFISH.get(), 12),
                    new ItemStack(Items.EMERALD, 1),
                    16, 2, .02f
            ));
            factories.get(1).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(ShellfishItems.RAW_LOBSTER.get(), 12),
                    new ItemStack(Items.EMERALD, 1),
                    16, 2, .03f
            ));
            factories.get(1).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(ShellfishItems.RAW_CRAB.get(), 12),
                    new ItemStack(Items.EMERALD, 1),
                    16, 2, .02f
            ));
            factories.get(1).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(ShellfishItems.RAW_SHRIMP.get(), 12),
                    new ItemStack(Items.EMERALD, 1),
                    16, 2, .02f
            ));
            factories.get(1).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(ShellfishItems.RAW_SEA_SNAIL.get(), 8),
                    new ItemStack(Items.EMERALD, 1),
                    16, 2, .02f
            ));
            factories.get(1).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(ShellfishItems.RAW_UNI.get(), 16),
                    new ItemStack(Items.EMERALD, 1),
                    16, 2, .02f
            ));
            factories.get(1).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(ShellfishItems.RAW_CLAM.get(), 16),
                    new ItemStack(Items.EMERALD, 1),
                    16, 2, .02f
            ));
            factories.get(1).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(ShellfishItems.RAW_OYSTER.get(), 16),
                    new ItemStack(Items.EMERALD, 1),
                    16, 2, .02f
            ));
            factories.get(1).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(ShellfishItems.RAW_MUSSEL.get(), 16),
                    new ItemStack(Items.EMERALD, 1),
                    16, 2, .02f
            ));
            factories.get(1).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ShellfishItems.SHELLFISH_BAIT.get(), 4),
                    16, 2, .02f
            ));

            // level 2
            factories.get(2).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    Optional.of(new ItemCost(ShellfishItems.RAW_CRAYFISH.get(), 6)),
                    new ItemStack(ShellfishItems.COOKED_CRAYFISH.get(), 6),
                    8, 10, .03f
            ));
            factories.get(2).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    Optional.of(new ItemCost(ShellfishItems.RAW_LOBSTER.get(), 6)),
                    new ItemStack(ShellfishItems.COOKED_LOBSTER.get(), 6),
                    8, 10, .03f
            ));
            factories.get(2).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    Optional.of(new ItemCost(ShellfishItems.RAW_CRAB.get(), 6)),
                    new ItemStack(ShellfishItems.COOKED_CRAB.get(), 6),
                    8, 10, .03f
            ));
            factories.get(2).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    Optional.of(new ItemCost(ShellfishItems.RAW_SHRIMP.get(), 6)),
                    new ItemStack(ShellfishItems.COOKED_SHRIMP.get(), 6),
                    8, 10, .03f
            ));
            factories.get(2).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    Optional.of(new ItemCost(ShellfishItems.RAW_SEA_SNAIL.get(), 4)),
                    new ItemStack(ShellfishItems.COOKED_SEA_SNAIL.get(), 4),
                    8, 10, .03f
            ));
            factories.get(2).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    Optional.of(new ItemCost(ShellfishItems.RAW_UNI.get(), 8)),
                    new ItemStack(ShellfishItems.COOKED_UNI.get(), 8),
                    8, 5, .03f
            ));
            factories.get(2).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    Optional.of(new ItemCost(ShellfishItems.RAW_CLAM.get(), 8)),
                    new ItemStack(ShellfishItems.COOKED_CLAM.get(), 8),
                    8, 5, .03f
            ));
            factories.get(2).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    Optional.of(new ItemCost(ShellfishItems.RAW_OYSTER.get(), 8)),
                    new ItemStack(ShellfishItems.COOKED_OYSTER.get(), 8),
                    8, 5, .03f
            ));
            factories.get(2).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    Optional.of(new ItemCost(ShellfishItems.RAW_MUSSEL.get(), 8)),
                    new ItemStack(ShellfishItems.COOKED_MUSSEL.get(), 8),
                    8, 5, .03f
            ));

            // level 3
            factories.get(3).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 3),
                    new ItemStack(ShellfishItems.CRAYFISH_BUCKET.get(), 1),
                    5, 8, .05f
            ));
            factories.get(3).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 4),
                    new ItemStack(ShellfishItems.LOBSTER_BUCKET.get(), 1),
                    5, 15, .05f
            ));
            factories.get(3).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 3),
                    new ItemStack(ShellfishItems.CRAB_BUCKET.get(), 1),
                    5, 8, .05f
            ));
            factories.get(3).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 3),
                    new ItemStack(ShellfishItems.SHRIMP_BUCKET.get(), 1),
                    5, 8, .05f
            ));
            factories.get(3).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 4),
                    new ItemStack(ShellfishItems.SEA_SNAIL_BUCKET.get(), 1),
                    5, 10, .05f
            ));
            factories.get(3).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 5),
                    new ItemStack(ShellfishItems.SEA_URCHIN_BUCKET.get(), 1),
                    5, 15, .05f
            ));
            factories.get(3).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 3),
                    new ItemStack(ShellfishItems.CLAM_BUCKET.get(), 1),
                    5, 8, .05f
            ));
            factories.get(3).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 3),
                    new ItemStack(ShellfishItems.OYSTER_BUCKET.get(), 1),
                    5, 8, .05f
            ));
            factories.get(3).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 3),
                    new ItemStack(ShellfishItems.MUSSEL_BUCKET.get(), 1),
                    5, 8, .05f
            ));
            factories.get(3).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(ShellfishItems.ROCKWEED.get(), 32),
                    new ItemStack(Items.EMERALD, 1),
                    16, 5, .02f
            ));
            factories.get(3).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(ShellfishItems.WATER_LETTUCE.get(), 32),
                    new ItemStack(Items.EMERALD, 1),
                    16, 5, .02f
            ));
            factories.get(3).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(ShellfishItems.PADDLEWEED.get(), 16),
                    new ItemStack(Items.EMERALD, 1),
                    16, 5, .02f
            ));
            factories.get(3).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(ShellfishItems.EELGRASS.get(), 16),
                    new ItemStack(Items.EMERALD, 1),
                    16, 5, .02f
            ));
            factories.get(3).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(ShellfishItems.SEA_LETTUCE.get(), 16),
                    new ItemStack(Items.EMERALD, 1),
                    16, 5, .02f
            ));

            // level 4
            factories.get(4).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 2),
                    new ItemStack(ShellfishItems.SHELLFISH_STEW.get(), 1),
                    8, 25, .05f
            ));
            factories.get(4).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ShellfishItems.LIGHT_SHELLFISH_STEW.get(), 1),
                    8, 15, .05f
            ));
            factories.get(4).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 4),
                    new ItemStack(ShellfishItems.CRAYFISH_BOIL.get(), 1),
                    8, 35, .05f
            ));
            factories.get(4).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 2),
                    new ItemStack(ShellfishItems.GUMBO.get(), 1),
                    8, 25, .05f
            ));
            factories.get(4).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 2),
                    new ItemStack(ShellfishItems.CRAYFISH_BISQUE.get(), 1),
                    8, 25, .05f
            ));
            factories.get(4).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ShellfishItems.BOILED_LOBSTER.get(), 1),
                    8, 15, .05f
            ));
            factories.get(4).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 3),
                    new ItemStack(ShellfishItems.LOBSTER_ROLL.get(), 2),
                    8, 30, .05f
            ));
            factories.get(4).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ShellfishItems.LOBSTER_SALAD.get(), 1),
                    8, 15, .05f
            ));
            factories.get(4).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ShellfishItems.CRAB_CAKE.get(), 4),
                    8, 15, .05f
            ));
            factories.get(4).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ShellfishItems.CRAB_DIP.get(), 1),
                    8, 15, .05f
            ));
            factories.get(4).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ShellfishItems.CRAB_LOUIE.get(), 1),
                    8, 15, .05f
            ));
            factories.get(4).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 3),
                    new ItemStack(ShellfishItems.COOKED_SHRIMP_SKEWER.get(), 2),
                    8, 30, .05f
            ));
            factories.get(4).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 3),
                    new ItemStack(ShellfishItems.SHRIMP_SCAMPI.get(), 1),
                    8, 30, .05f
            ));
            factories.get(4).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 2),
                    new ItemStack(ShellfishItems.SHRIMP_TACO.get(), 2),
                    8, 25, .05f
            ));
            factories.get(4).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ShellfishItems.ESCARGOT.get(), 4),
                    8, 15, .05f
            ));
            factories.get(4).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 2),
                    new ItemStack(ShellfishItems.SNAIL_GRATIN.get(), 1),
                    8, 25, .05f
            ));
            factories.get(4).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ShellfishItems.SNAIL_SOUP.get(), 1),
                    8, 15, .05f
            ));
            factories.get(4).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ShellfishItems.UNI_SUSHI.get(), 5),
                    8, 15, .05f
            ));
            factories.get(4).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ShellfishItems.UNI_CHAWANMUSHI.get(), 1),
                    8, 15, .05f
            ));
            factories.get(4).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ShellfishItems.UNI_SALAD.get(), 1),
                    8, 15, .05f
            ));
            factories.get(4).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ShellfishItems.BAKED_CLAM.get(), 4),
                    8, 15, .05f
            ));
            factories.get(4).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 2),
                    new ItemStack(ShellfishItems.CLAM_STRIP.get(), 3),
                    8, 25, .05f
            ));
            factories.get(4).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 2),
                    new ItemStack(ShellfishItems.CLAM_CHOWDER.get(), 1),
                    8, 25, .05f
            ));
            factories.get(4).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ShellfishItems.OYSTER_ROCKEFELLER.get(), 4),
                    8, 15, .05f
            ));
            factories.get(4).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 2),
                    new ItemStack(ShellfishItems.FRIED_OYSTER.get(), 3),
                    8, 25, .05f
            ));
            factories.get(4).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 2),
                    new ItemStack(ShellfishItems.OYSTER_CHOWDER.get(), 1),
                    8, 25, .05f
            ));
            factories.get(4).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ShellfishItems.STUFFED_MUSSEL.get(), 4),
                    8, 15, .05f
            ));
            factories.get(4).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ShellfishItems.MUSSEL_SOUP.get(), 1),
                    8, 15, .05f
            ));
            factories.get(4).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 2),
                    new ItemStack(ShellfishItems.MOULES_MARINIERES.get(), 1),
                    8, 25, .05f
            ));

            // level 5
            factories.get(5).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 5),
                    new ItemStack(ShellfishItems.SHELLFISH_TRAP.get(), 1),
                    8, 30, .08f
            ));
            factories.get(5).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 5),
                    new ItemStack(ShellfishItems.MOSS_BALL_BUCKET.get(), 1),
                    5, 30, .08f
            ));
            factories.get(5).add((level, entity, random) -> new MerchantOffer(
                    new ItemCost(ShellfishItems.PEARL.get(), 1),
                    new ItemStack(Items.EMERALD, 1),
                    16, 2, .08f
            ));
        }
    }
}
