package womp.shellfishmod.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import womp.shellfishmod.ShellfishMod;
import womp.shellfishmod.entity.*;

import java.util.function.Supplier;

public class ShellfishEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, ShellfishMod.MOD_ID);

    public static final Supplier<EntityType<CrayfishEntity>> CRAYFISH = register("crayfish", CrayfishEntity::new, MobCategory.WATER_AMBIENT, 0.75f, 0.25f);
    public static final Supplier<EntityType<LobsterEntity>> LOBSTER = register("lobster", LobsterEntity::new, MobCategory.WATER_CREATURE, 0.75f, 0.25f);
    public static final Supplier<EntityType<CrabEntity>> CRAB = register("crab", CrabEntity::new, MobCategory.WATER_AMBIENT, 0.4f, 0.25f);
    public static final Supplier<EntityType<ShrimpEntity>> SHRIMP = register("shrimp", ShrimpEntity::new, MobCategory.WATER_AMBIENT, 0.75f, 0.25f);
    public static final Supplier<EntityType<SeaSnailEntity>> SEA_SNAIL = register("sea_snail", SeaSnailEntity::new, MobCategory.WATER_AMBIENT, 0.45f, 0.45f);
    public static final Supplier<EntityType<SeaUrchinEntity>> SEA_URCHIN = register("sea_urchin", SeaUrchinEntity::new, MobCategory.WATER_AMBIENT, 0.25f, 0.2f);
    public static final Supplier<EntityType<ClamEntity>> CLAM = register("clam", ClamEntity::new, MobCategory.AMBIENT, 0.4f, 0.2f);
    public static final Supplier<EntityType<OysterEntity>> OYSTER = register("oyster", OysterEntity::new, MobCategory.AMBIENT, 0.4f, 0.2f);
    public static final Supplier<EntityType<MusselEntity>> MUSSEL = register("mussel", MusselEntity::new, MobCategory.AMBIENT, 0.4f, 0.2f);
    public static final Supplier<EntityType<MossBallEntity>> MOSS_BALL = register("moss_ball", MossBallEntity::new, MobCategory.AMBIENT, 0.32f, 0.32f);


    private static <T extends Mob> Supplier<EntityType<T>> register(String name, EntityType.EntityFactory<T> factory, MobCategory group, float width, float height) {
        return ENTITIES.register(name, () -> EntityType.Builder.of(factory, group).sized(width, height).build(name));
    }

    public static void register(IEventBus bus) {
        ENTITIES.register(bus);
    }
}
