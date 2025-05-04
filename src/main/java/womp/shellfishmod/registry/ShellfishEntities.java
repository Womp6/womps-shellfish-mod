package womp.shellfishmod.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnLocation;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.SpawnRestriction;
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
    
    public static final EntityType<CrayfishEntity> CRAYFISH = register("crayfish", SpawnGroup.WATER_AMBIENT, CrayfishEntity::new, SpawnLocationTypes.IN_WATER, CrayfishEntity::canSpawn, 0.75f, 0.25f, CrayfishEntity.createCrayfishAttributes());
    public static final EntityType<LobsterEntity> LOBSTER = register("lobster", SpawnGroup.WATER_CREATURE, LobsterEntity::new, SpawnLocationTypes.IN_WATER, LobsterEntity::canSpawn, 0.85f, 0.25f, LobsterEntity.createLobsterAttributes());
    public static final EntityType<CrabEntity> CRAB = register("crab", SpawnGroup.WATER_AMBIENT, CrabEntity::new, SpawnLocationTypes.UNRESTRICTED, CrabEntity::canSpawn, 0.4f, 0.25f, CrabEntity.createCrabAttributes());
    public static final EntityType<ShrimpEntity> SHRIMP = register("shrimp", SpawnGroup.WATER_AMBIENT, ShrimpEntity::new, SpawnLocationTypes.IN_WATER, ShrimpEntity::canSpawn, 0.75f, 0.25f, ShrimpEntity.createShrimpAttributes());
    public static final EntityType<SeaSnailEntity> SEA_SNAIL = register("sea_snail", SpawnGroup.WATER_AMBIENT, SeaSnailEntity::new, SpawnLocationTypes.UNRESTRICTED, SeaSnailEntity::canSpawn, 0.45f, 0.45f, SeaSnailEntity.createSeaSnailAttributes());
    public static final EntityType<SeaUrchinEntity> SEA_URCHIN = register("sea_urchin", SpawnGroup.WATER_AMBIENT, SeaUrchinEntity::new, SpawnLocationTypes.IN_WATER, SeaUrchinEntity::canSpawn, 0.25f, 0.2f, SeaUrchinEntity.createSeaUrchinAttributes());
    public static final EntityType<ClamEntity> CLAM = register("clam", SpawnGroup.AMBIENT, ClamEntity::new, SpawnLocationTypes.IN_WATER, ClamEntity::canSpawn, 0.4f, 0.2f, ClamEntity.createClamAttributes());
    public static final EntityType<OysterEntity> OYSTER = register("oyster", SpawnGroup.AMBIENT, OysterEntity::new, SpawnLocationTypes.IN_WATER, OysterEntity::canSpawn, 0.4f, 0.2f, OysterEntity.createOysterAttributes());
    public static final EntityType<MusselEntity> MUSSEL = register("mussel", SpawnGroup.AMBIENT, MusselEntity::new, SpawnLocationTypes.IN_WATER, MusselEntity::canSpawn, 0.4f, 0.2f, MusselEntity.createMusselAttributes());
    public static final EntityType<MossBallEntity> MOSS_BALL = register("moss_ball", SpawnGroup.AMBIENT, MossBallEntity::new, SpawnLocationTypes.IN_WATER, MossBallEntity::canSpawn, 0.32f, 0.32f, MossBallEntity.createMossBallAttributes());


    private static <T extends MobEntity> EntityType<T> register(String name, SpawnGroup group, EntityType.EntityFactory<T> factory,
            SpawnLocation location, SpawnRestriction.SpawnPredicate<T> predicate, float width, float height, DefaultAttributeContainer.Builder attributes) {
        EntityType<T> mob = Registry.register(Registries.ENTITY_TYPE, new Identifier("shellfish", name), EntityType.Builder.create(factory, group).dimensions(width, height).build());
        FabricDefaultAttributeRegistry.register(mob, attributes);
        SpawnRestriction.register(mob, location, Type.MOTION_BLOCKING_NO_LEAVES, predicate);
        return mob;
    }

    public static void register() {}
}