package womp.shellfishmod.entity;

import com.mojang.serialization.Codec;
import net.minecraft.Util;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;
import womp.shellfishmod.entity.goals.SitAroundGoal;
import womp.shellfishmod.entity.goals.WanderInWaterGoal;
import womp.shellfishmod.entity.parents.ShellfishEntity;
import womp.shellfishmod.entity.ClamEntity.Variant;
import womp.shellfishmod.registry.ShellfishItems;
import womp.shellfishmod.registry.ShellfishEntities;
import womp.shellfishmod.registry.ShellfishSounds;

import java.util.function.IntFunction;

public class ClamEntity extends ShellfishEntity<Variant> {

    public ClamEntity(EntityType<? extends ClamEntity> entityType, Level world) {
        super(entityType, world);
        waterIdle = true;
        isIdleEntity = true;
        airBreathing = false;
        slow = false;
        waterWalking = true;
        brokenAnim = true;
    }

    public static AttributeSupplier.Builder createClamAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 5.0d).add(Attributes.MOVEMENT_SPEED, 0.05);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SitAroundGoal(this));
        this.goalSelector.addGoal(1, new WanderInWaterGoal(this, 1));
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob entity) {
        ClamEntity child;
        if((child = ShellfishEntities.CLAM.get().create(world)) != null && entity instanceof ClamEntity mate) {
            child.setVariant((random.nextBoolean() ? this : mate).getVariant());
            child.setPersistenceRequired();
            return child;
        }
        return null;
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(ShellfishItems.CLAM_BUCKET.get());
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return ShellfishSounds.CLAM_DEATH.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(DamageSource source) {
        return ShellfishSounds.CLAM_HURT.get();
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, SpawnGroupData entityData) {
        Variant variant;
        RandomSource random = world.getRandom();
        if (entityData instanceof ClamData) {
            variant = ((ClamData)entityData).variant;
        } else {
            variant = Util.getRandom(Variant.values(), random);
            entityData = new ClamData(variant);
        }
        this.setVariant(variant);
        this.setNewborn(true);
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
    }

    @Override
    protected Variant byId(int id) {
        return Variant.byId(id);
    }

    public enum Variant implements ShellfishVariant {
        CLAM0(0, "clam0"),
        CLAM1(1, "clam1"),
        CLAM2(2, "clam2"),
        CLAM3(3, "clam3"),
        CLAM4(4, "clam4");

        public static final Codec<Variant> CODEC;
        private static final IntFunction<Variant> BY_ID;
        final int id;
        private final String name;

        Variant(int id, String name) {
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
        public String getSerializedName() {
            return this.name;
        }

        static {
            CODEC = StringRepresentable.fromEnum(Variant::values);
            BY_ID = ByIdMap.continuous(Variant::getIndex, Variant.values(), ByIdMap.OutOfBoundsStrategy.CLAMP);
        }
    }

    static class ClamData extends AgeableMob.AgeableMobGroupData {
        public final Variant variant;

        ClamData(Variant variant) {
            super(true);
            this.variant = variant;
        }
    }
}