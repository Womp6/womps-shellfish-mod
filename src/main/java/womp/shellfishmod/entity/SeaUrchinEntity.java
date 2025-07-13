package womp.shellfishmod.entity;


import java.util.function.IntFunction;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.Codec;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import womp.shellfishmod.entity.goals.SitAroundGoal;
import womp.shellfishmod.entity.goals.WanderInWaterGoal;
import womp.shellfishmod.entity.goals.WanderToWaterGoal;
import womp.shellfishmod.entity.parents.ShellfishEntity;
import womp.shellfishmod.entity.parents.ShellfishEntity.ShellfishVariant;
import womp.shellfishmod.entity.SeaUrchinEntity.Variant;
import womp.shellfishmod.registry.ShellfishEntities;
import womp.shellfishmod.registry.ShellfishItems;
import womp.shellfishmod.registry.ShellfishSounds;

public class SeaUrchinEntity extends ShellfishEntity<Variant> {

    public SeaUrchinEntity(EntityType<? extends SeaUrchinEntity> entityType, World world) {
        super(entityType, world);
        this.waterWalking = false;
        airBreathing = false;
        brokenAnim = true;
    }

    public static DefaultAttributeContainer.Builder createSeaUrchinAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 2.0d).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.07);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(3, new WanderInWaterGoal(this, 1));
        this.goalSelector.add(4, new WanderToWaterGoal(this, 1));
        this.goalSelector.add(5, new SitAroundGoal(this));
        this.goalSelector.add(6, new WanderAroundGoal(this, 1));
    }
    
    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        SeaUrchinEntity child;
        if((child = ShellfishEntities.SEA_URCHIN.create(world)) != null && entity instanceof SeaUrchinEntity mate) {
            child.setVariant((random.nextBoolean() ? this : mate).getVariant());
            child.setPersistent();
            return child;
        }
        return null;
    }

    public void travel(Vec3d movementInput) {
        if (this.isTouchingWater() && this.canMoveVoluntarily()) {
            this.updateVelocity(0.045f, movementInput);
            this.move(MovementType.SELF, this.getVelocity());
            this.setVelocity(this.getVelocity().multiply(.1, .825, .1).add(0, -0.01d, 0));
        } else {
            super.travel(movementInput);
        }

    }

    @Override
    public ItemStack getBucketItem() {
        return new ItemStack(ShellfishItems.SEA_URCHIN_BUCKET);
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return ShellfishSounds.SEA_URCHIN_DEATH;
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(DamageSource source) {
        return ShellfishSounds.SEA_URCHIN_HURT;
    }

    @Override
    public int getMaxAir() {
        return 4000;
    }

    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        Variant variant;
        Random random = world.getRandom();
        if (entityData instanceof SeaUrchinData) {
            variant = ((SeaUrchinData)entityData).variant;
        } else {
            variant = Util.getRandom(Variant.values(), random);
            entityData = new SeaUrchinData(variant);
        }
        this.setVariant(variant);
        this.setNewborn(true);
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    public static enum Variant implements ShellfishVariant {
        URCHIN0(0, "urchin0"),
        URCHIN1(1, "urchin1"),
        URCHIN2(2, "urchin2"),
        URCHIN3(3, "urchin3"),
        URCHIN4(4, "urchin4");

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

    static class SeaUrchinData extends PassiveEntity.PassiveData {
        public final Variant variant;

        SeaUrchinData(Variant variant) {
            super(true);
            this.variant = variant;
        }
    }

    @Override
    protected Variant byId(int id) {
        return Variant.byId(id);
    }
}
