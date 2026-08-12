package womp.shellfishmod.registry;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;
import womp.shellfishmod.ShellfishMod;
import womp.shellfishmod.command.PassiveShellfishCommand;
import womp.shellfishmod.entity.*;

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
}
