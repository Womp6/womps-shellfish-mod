package womp.shellfishmod.entity;


import java.util.function.IntFunction;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.Codec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.Util;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
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

    public SeaUrchinEntity(EntityType<? extends SeaUrchinEntity> entityType, Level world) {
        super(entityType, world);
        this.waterWalking = false;
        airBreathing = false;
        brokenAnim = true;
    }

    public static AttributeSupplier.Builder createSeaUrchinAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 2.0d).add(Attributes.MOVEMENT_SPEED, 0.07);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(3, new WanderInWaterGoal(this, 1));
        this.goalSelector.addGoal(4, new WanderToWaterGoal(this, 1));
        this.goalSelector.addGoal(5, new SitAroundGoal(this));
        this.goalSelector.addGoal(6, new RandomStrollGoal(this, 1));
    }
    
    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob entity) {
        SeaUrchinEntity child;
        if((child = ShellfishEntities.SEA_URCHIN.create(world, EntitySpawnReason.BREEDING)) != null && entity instanceof SeaUrchinEntity mate) {
            child.setVariant((random.nextBoolean() ? this : mate).getVariant());
            child.setPersistenceRequired();
            return child;
        }
        return null;
    }

    public void travel(Vec3 movementInput) {
        if (this.isInWater() && this.canSimulateMovement()) {
            this.moveRelative(0.045f, movementInput);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().multiply(.1, .825, .1).add(0, -0.01d, 0));
        } else {
            super.travel(movementInput);
        }

    }

    @Override
    public ItemStack getBucketItemStack() {
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
    public int getMaxAirSupply() {
        return 4000;
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @Nullable SpawnGroupData entityData) {
        Variant variant;
        RandomSource random = world.getRandom();
        if (entityData instanceof SeaUrchinData) {
            variant = ((SeaUrchinData)entityData).variant;
        } else {
            variant = Util.getRandom(Variant.values(), random);
            entityData = new SeaUrchinData(variant);
        }
        this.setVariant(variant);
        this.setNewborn(true);
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
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
        public String getSerializedName() {
            return this.name;
        }

        static {
            CODEC = StringRepresentable.fromEnum(Variant::values);
            BY_ID = ByIdMap.continuous(Variant::getIndex, Variant.values(), ByIdMap.OutOfBoundsStrategy.CLAMP);
        }
    }

    static class SeaUrchinData extends AgeableMob.AgeableMobGroupData {
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
