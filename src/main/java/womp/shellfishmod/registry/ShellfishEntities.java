package womp.shellfishmod.registry;

import java.util.function.Supplier;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.SpawnRestriction.Location;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.Heightmap.Type;
import womp.shellfishmod.entity.ClamEntity;
import womp.shellfishmod.entity.CrabEntity;
import womp.shellfishmod.entity.CrayfishEntity;
import womp.shellfishmod.entity.LobsterEntity;
import womp.shellfishmod.entity.MossBallEntity;
import womp.shellfishmod.entity.MusselEntity;
import womp.shellfishmod.entity.OysterEntity;
import womp.shellfishmod.entity.SeaSnailEntity;
import womp.shellfishmod.entity.SeaUrchinEntity;
import womp.shellfishmod.entity.ShrimpEntity;

public class ShellfishEntities {
    
    public static final EntityType<CrayfishEntity> CRAYFISH = register("crayfish", SpawnGroup.WATER_AMBIENT, CrayfishEntity::new, Location.IN_WATER, CrayfishEntity::canSpawn, 0.75f, 0.25f, CrayfishEntity::createCrayfishAttributes);
    public static final EntityType<LobsterEntity> LOBSTER = register("lobster", SpawnGroup.WATER_CREATURE, LobsterEntity::new, Location.IN_WATER, LobsterEntity::canSpawn, 0.85f, 0.25f, LobsterEntity::createLobsterAttributes);
    public static final EntityType<CrabEntity> CRAB = register("crab", SpawnGroup.WATER_AMBIENT, CrabEntity::new, Location.NO_RESTRICTIONS, CrabEntity::canSpawn, 0.4f, 0.25f, CrabEntity::createCrabAttributes);
    public static final EntityType<ShrimpEntity> SHRIMP = register("shrimp", SpawnGroup.WATER_AMBIENT, ShrimpEntity::new, Location.IN_WATER, ShrimpEntity::canSpawn, 0.75f, 0.25f, ShrimpEntity::createShrimpAttributes);
    public static final EntityType<SeaSnailEntity> SEA_SNAIL = register("sea_snail", SpawnGroup.WATER_AMBIENT, SeaSnailEntity::new, Location.NO_RESTRICTIONS, SeaSnailEntity::canSpawn, 0.45f, 0.45f, SeaSnailEntity::createSeaSnailAttributes);
    public static final EntityType<SeaUrchinEntity> SEA_URCHIN = register("sea_urchin", SpawnGroup.WATER_AMBIENT, SeaUrchinEntity::new, Location.IN_WATER, SeaUrchinEntity::canSpawn, 0.25f, 0.2f, SeaUrchinEntity::createSeaUrchinAttributes);
    public static final EntityType<ClamEntity> CLAM = register("clam", SpawnGroup.AMBIENT, ClamEntity::new, Location.IN_WATER, ClamEntity::canSpawn, 0.4f, 0.2f, ClamEntity::createClamAttributes);
    public static final EntityType<OysterEntity> OYSTER = register("oyster", SpawnGroup.AMBIENT, OysterEntity::new, Location.IN_WATER, OysterEntity::canSpawn, 0.4f, 0.2f, OysterEntity::createOysterAttributes);
    public static final EntityType<MusselEntity> MUSSEL = register("mussel", SpawnGroup.AMBIENT, MusselEntity::new, Location.IN_WATER, MusselEntity::canSpawn, 0.4f, 0.2f, MusselEntity::createMusselAttributes);
    public static final EntityType<MossBallEntity> MOSS_BALL = register("moss_ball", SpawnGroup.AMBIENT, MossBallEntity::new, Location.IN_WATER, MossBallEntity::canSpawn, 0.32f, 0.32f, MossBallEntity::createMossBallAttributes);


    private static <T extends MobEntity> EntityType<T> register(String name, SpawnGroup group, EntityType.EntityFactory<T> factory,
            SpawnRestriction.Location location, SpawnRestriction.SpawnPredicate<T> predicate, float width, float height, Supplier<DefaultAttributeContainer.Builder> attributes) {
        return Registry.register(Registries.ENTITY_TYPE, new Identifier("shellfish", name), FabricEntityTypeBuilder.createMob().spawnGroup(group)
            .entityFactory(factory).spawnRestriction(location, Type.MOTION_BLOCKING_NO_LEAVES, predicate).dimensions(EntityDimensions.fixed(width, height)).defaultAttributes(attributes).build());
    }

    public static void register() {}
}