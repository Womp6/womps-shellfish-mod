package womp.shellfishmod.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.levelgen.Heightmap.Types;
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
    
    public static final EntityType<CrayfishEntity> CRAYFISH = register("crayfish", MobCategory.WATER_AMBIENT, CrayfishEntity::new, SpawnPlacementTypes.IN_WATER, CrayfishEntity::canSpawn, 0.75f, 0.25f, CrayfishEntity.createCrayfishAttributes());
    public static final EntityType<LobsterEntity> LOBSTER = register("lobster", MobCategory.WATER_CREATURE, LobsterEntity::new, SpawnPlacementTypes.IN_WATER, LobsterEntity::canSpawn, 0.85f, 0.25f, LobsterEntity.createLobsterAttributes());
    public static final EntityType<CrabEntity> CRAB = register("crab", MobCategory.WATER_AMBIENT, CrabEntity::new, SpawnPlacementTypes.NO_RESTRICTIONS, CrabEntity::canSpawn, 0.4f, 0.25f, CrabEntity.createCrabAttributes());
    public static final EntityType<ShrimpEntity> SHRIMP = register("shrimp", MobCategory.WATER_AMBIENT, ShrimpEntity::new, SpawnPlacementTypes.IN_WATER, ShrimpEntity::canSpawn, 0.75f, 0.25f, ShrimpEntity.createShrimpAttributes());
    public static final EntityType<SeaSnailEntity> SEA_SNAIL = register("sea_snail", MobCategory.WATER_AMBIENT, SeaSnailEntity::new, SpawnPlacementTypes.NO_RESTRICTIONS, SeaSnailEntity::canSpawn, 0.45f, 0.45f, SeaSnailEntity.createSeaSnailAttributes());
    public static final EntityType<SeaUrchinEntity> SEA_URCHIN = register("sea_urchin", MobCategory.WATER_AMBIENT, SeaUrchinEntity::new, SpawnPlacementTypes.IN_WATER, SeaUrchinEntity::canSpawn, 0.25f, 0.2f, SeaUrchinEntity.createSeaUrchinAttributes());
    public static final EntityType<ClamEntity> CLAM = register("clam", MobCategory.AMBIENT, ClamEntity::new, SpawnPlacementTypes.IN_WATER, ClamEntity::canSpawn, 0.4f, 0.2f, ClamEntity.createClamAttributes());
    public static final EntityType<OysterEntity> OYSTER = register("oyster", MobCategory.AMBIENT, OysterEntity::new, SpawnPlacementTypes.IN_WATER, OysterEntity::canSpawn, 0.4f, 0.2f, OysterEntity.createOysterAttributes());
    public static final EntityType<MusselEntity> MUSSEL = register("mussel", MobCategory.AMBIENT, MusselEntity::new, SpawnPlacementTypes.IN_WATER, MusselEntity::canSpawn, 0.4f, 0.2f, MusselEntity.createMusselAttributes());
    public static final EntityType<MossBallEntity> MOSS_BALL = register("moss_ball", MobCategory.AMBIENT, MossBallEntity::new, SpawnPlacementTypes.IN_WATER, MossBallEntity::canSpawn, 0.32f, 0.32f, MossBallEntity.createMossBallAttributes());


    private static <T extends Mob> EntityType<T> register(String name, MobCategory group, EntityType.EntityFactory<T> factory,
            SpawnPlacementType location, SpawnPlacements.SpawnPredicate<T> predicate, float width, float height, AttributeSupplier.Builder attributes) {
        EntityType<T> mob = Registry.register(BuiltInRegistries.ENTITY_TYPE, Identifier.fromNamespaceAndPath("shellfish", name), EntityType.Builder.of(factory, group).sized(width, height).build(ShellfishUtil.createKey(name, Registries.ENTITY_TYPE)));
        FabricDefaultAttributeRegistry.register(mob, attributes);
        SpawnPlacements.register(mob, location, Types.MOTION_BLOCKING_NO_LEAVES, predicate);
        return mob;
    }

    public static void register() {}
}