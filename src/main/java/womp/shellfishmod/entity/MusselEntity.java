package womp.shellfishmod.entity;

import java.util.function.IntFunction;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.Codec;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.Util;
import net.minecraft.util.function.ValueLists;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import womp.shellfishmod.entity.goals.SitAroundGoal;
import womp.shellfishmod.entity.goals.WanderInWaterGoal;
import womp.shellfishmod.entity.parents.ShellfishEntity;
import womp.shellfishmod.entity.parents.ShellfishEntity.ShellfishVariant;
import womp.shellfishmod.entity.MusselEntity.Variant;
import womp.shellfishmod.registry.ShellfishEntities;
import womp.shellfishmod.registry.ShellfishItems;
import womp.shellfishmod.registry.ShellfishSounds;

public class MusselEntity extends ShellfishEntity<Variant> {
    
    public MusselEntity(EntityType<? extends MusselEntity> entityType, World world) {
        super(entityType, world);
        waterIdle = true;
        isIdleEntity = true;
        airBreathing = false;
        slow = false;
        waterWalking = true;
        brokenAnim = true;
    }

    public static DefaultAttributeContainer.Builder createMusselAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.MAX_HEALTH, 3.0d).add(EntityAttributes.MOVEMENT_SPEED, 0.05);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SitAroundGoal(this));
        this.goalSelector.add(1, new WanderInWaterGoal(this, 1));
    }
    
    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        MusselEntity child;
        if((child = ShellfishEntities.MUSSEL.create(world, SpawnReason.BREEDING)) != null && entity instanceof MusselEntity mate) {
            child.setVariant((random.nextBoolean() ? this : mate).getVariant());
            child.setPersistent();
            return child;
        }
        return null;
    }

    @Override
    public ItemStack getBucketItem() {
        return new ItemStack(ShellfishItems.MUSSEL_BUCKET);
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        playSound(ShellfishSounds.MUSSEL_DEATH, 1.0f, 1.4f);
        return null;
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(DamageSource source) {
        playSound(ShellfishSounds.MUSSEL_HURT, 1.0f, 1.4f);
        return null;
    }

    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        Variant variant;
        Random random = world.getRandom();
        if (entityData instanceof MusselData) {
            variant = ((MusselData)entityData).variant;
        } else {
            variant = Util.getRandom(Variant.values(), random);
            entityData = new MusselData(variant);
        }
        this.setVariant(variant);
        this.setNewborn(true);
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    public static enum Variant implements ShellfishVariant {
        MUSSEL0(0, "mussel0"),
        MUSSEL1(1, "mussel1"),
        MUSSEL2(2, "mussel2"),
        MUSSEL3(3, "mussel3"),
        MUSSEL4(4, "mussel4");

        public static final Codec<Variant> CODEC;
        private static final IntFunction<Variant> BY_ID;
        final int id;
        private final String name;

        private Variant(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getIndex() {
            return this.id;
        }

        public static Variant byId(int id) {
            return BY_ID.apply(id);
        }

        @Override
        public String asString() {
            return this.name;
        }

        static {
            CODEC = StringIdentifiable.createCodec(Variant::values);
            BY_ID = ValueLists.createIdToValueFunction(Variant::getIndex, Variant.values(), ValueLists.OutOfBoundsHandling.CLAMP);
        }
    }

    static class MusselData extends PassiveEntity.PassiveData {
        public final Variant variant;

        MusselData(Variant variant) {
            super(true);
            this.variant = variant;
        }
    }

    @Override
    protected Variant byId(int id) {
        return Variant.byId(id);
    }
}
