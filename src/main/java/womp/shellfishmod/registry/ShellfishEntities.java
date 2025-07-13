package womp.shellfishmod.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import womp.shellfishmod.ShellfishMod;
import womp.shellfishmod.entity.*;
import womp.shellfishmod.util.ShellfishTags;

public class ShellfishEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ShellfishMod.MOD_ID);

    public static final RegistryObject<EntityType<CrayfishEntity>> CRAYFISH = register("crayfish", CrayfishEntity::new, MobCategory.WATER_AMBIENT, 0.75f, 0.25f);
    public static final RegistryObject<EntityType<LobsterEntity>> LOBSTER = register("lobster", LobsterEntity::new, MobCategory.WATER_CREATURE, 0.75f, 0.25f);
    public static final RegistryObject<EntityType<CrabEntity>> CRAB = register("crab", CrabEntity::new, MobCategory.WATER_AMBIENT, 0.4f, 0.25f);
    public static final RegistryObject<EntityType<ShrimpEntity>> SHRIMP = register("shrimp", ShrimpEntity::new, MobCategory.WATER_AMBIENT, 0.75f, 0.25f);
    public static final RegistryObject<EntityType<SeaSnailEntity>> SEA_SNAIL = register("sea_snail", SeaSnailEntity::new, MobCategory.WATER_AMBIENT, 0.45f, 0.45f);
    public static final RegistryObject<EntityType<SeaUrchinEntity>> SEA_URCHIN = register("sea_urchin", SeaUrchinEntity::new, MobCategory.WATER_AMBIENT, 0.25f, 0.2f);
    public static final RegistryObject<EntityType<ClamEntity>> CLAM = register("clam", ClamEntity::new, MobCategory.AMBIENT, 0.4f, 0.2f);
    public static final RegistryObject<EntityType<OysterEntity>> OYSTER = register("oyster", OysterEntity::new, MobCategory.AMBIENT, 0.4f, 0.2f);
    public static final RegistryObject<EntityType<MusselEntity>> MUSSEL = register("mussel", MusselEntity::new, MobCategory.AMBIENT, 0.4f, 0.2f);
    public static final RegistryObject<EntityType<MossBallEntity>> MOSS_BALL = register("moss_ball", MossBallEntity::new, MobCategory.AMBIENT, 0.32f, 0.32f);


    private static <T extends Mob> RegistryObject<EntityType<T>> register(String name, EntityType.EntityFactory<T> factory, MobCategory group, float width, float height) {
        return ENTITIES.register(name, () -> EntityType.Builder.of(factory, group).sized(width, height).build(ShellfishTags.createKey(name, Registries.ENTITY_TYPE)));
    }

    public static void register(BusGroup bus) {
        ENTITIES.register(bus);
    }
}
