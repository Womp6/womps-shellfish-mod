package womp.shellfishmod.entity;

import com.mojang.serialization.Codec;
import net.minecraft.util.Util;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.fish.AbstractFish;
import net.minecraft.world.entity.animal.turtle.Turtle;
import net.minecraft.world.entity.animal.frog.Tadpole;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.*;
import org.jetbrains.annotations.Nullable;
import womp.shellfishmod.entity.goals.*;
import womp.shellfishmod.entity.parents.EggLaying;
import womp.shellfishmod.entity.parents.Hungry;
import womp.shellfishmod.entity.parents.ShellfishEntity;
import womp.shellfishmod.registry.ShellfishBlocks;
import womp.shellfishmod.registry.ShellfishItems;
import womp.shellfishmod.util.ShellfishTags;
import womp.shellfishmod.registry.ShellfishEntities;
import womp.shellfishmod.registry.ShellfishSounds;

import java.util.function.IntFunction;

public class CrayfishEntity extends ShellfishEntity<CrayfishEntity.Variant> implements Hungry, EggLaying {

    public static final EntityDataAccessor<Boolean> IS_HUNGRY = SynchedEntityData.defineId(CrayfishEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> HAS_EGG = SynchedEntityData.defineId(CrayfishEntity.class, EntityDataSerializers.BOOLEAN);

    public CrayfishEntity(EntityType<? extends CrayfishEntity> entityType, Level world) {
        super(entityType, world);
        this.hostileSound = ShellfishSounds.CRAYFISH_ATTACK.get();
    }

    public static AttributeSupplier.Builder createCrayfishAttributes() {
        return Monster.createMobAttributes().add(Attributes.MAX_HEALTH, 8.0d).add(Attributes.ATTACK_DAMAGE, 1.0d).add(Attributes.MOVEMENT_SPEED, 0.14);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new ShellfishLayEggGoal(this, 1, ShellfishSounds.CRAYFISH_LAYS_EGGS.get(), ShellfishBlocks.CRAYFISH_EGGS_BLOCK.get()));
        this.goalSelector.addGoal(1, new ShellfishMateGoal(this, 1));
        this.goalSelector.addGoal(1, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1d, true));
        this.targetSelector.addGoal(2, new HungryRevengeGoal(this, new Class[0]));
        this.targetSelector.addGoal(2, new HungryActiveTargetGoal<>(this, Tadpole.class, false));
        this.targetSelector.addGoal(2, new HungryActiveTargetGoal<>(this, AbstractFish.class, false));
        this.targetSelector.addGoal(2, new HungryActiveTargetGoal<>(this, Turtle.class, 10, true, true, (entity, baby) -> entity.isBaby()));
        this.targetSelector.addGoal(2, new HungryActiveTargetGoal<>(this, ShrimpEntity.class, false));
        this.targetSelector.addGoal(2, new HungryActiveTargetGoal<>(this, SeaSnailEntity.class, false));
        this.targetSelector.addGoal(2, new HungryActiveTargetGoal<>(this, ClamEntity.class, false));
        this.targetSelector.addGoal(2, new HungryActiveTargetGoal<>(this, MusselEntity.class, false));
        this.targetSelector.addGoal(2, new HungryActiveTargetGoal<>(this, MossBallEntity.class, false));
        this.goalSelector.addGoal(3, new WanderInWaterGoal(this, 1));
        this.goalSelector.addGoal(5, new WanderToWaterGoal(this, 1));
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
    }

    @Override
    public boolean isFood(ItemStack item) {
        return item.is(ShellfishTags.Items.CRAYFISH_FOOD);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob entity) {
        CrayfishEntity child;
        if((child = ShellfishEntities.CRAYFISH.get().create(world, EntitySpawnReason.BREEDING)) != null && entity instanceof CrayfishEntity mate) {
            child.setVariant((random.nextBoolean() ? this : mate).getVariant());
            child.setPersistenceRequired();
            return child;
        }
        return null;
    }

    @Override
    protected void usePlayerItem(Player player, InteractionHand hand, ItemStack stack) {
        if (stack.is(ShellfishItems.MOSS_BALL_BUCKET.get())) {
            player.setItemInHand(hand, new ItemStack(Items.WATER_BUCKET));
        } else {
            super.usePlayerItem(player, hand, stack);
        }
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 4;
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(ShellfishItems.CRAYFISH_BUCKET.get());
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return ShellfishSounds.CRAYFISH_DEATH.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(DamageSource source) {
        return ShellfishSounds.CRAYFISH_HURT.get();
    }

    @Override
    public ShellfishEntity<Variant> getEntity() {
        return this;
    }

    @Override
    public EntityDataAccessor<Boolean> getHasEgg() {
        return HAS_EGG;
    }

    @Override
    public EntityDataAccessor<Boolean> getHungry() {
        return IS_HUNGRY;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);
        pBuilder.define(IS_HUNGRY, false);
        pBuilder.define(HAS_EGG, false);
    }

    @Override
    protected Variant byId(int id) {
        return Variant.byId(id);
    }

    @Override
    public int getMaxVariants() {
        return 28;
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, EntitySpawnReason spawnReason, SpawnGroupData entityData) {
        Variant variant;
        RandomSource random = world.getRandom();
        if (entityData instanceof CrayfishData) {
            variant = ((CrayfishData)entityData).variant;
        } else {
            variant = Util.getRandom(Variant.values(), random);
            entityData = new CrayfishData(variant);
        }
        this.setVariant(variant);
        this.setNewborn(true);
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
    }

    public enum Variant implements ShellfishVariant {
        CRAY0(0, "cray0"),
        CRAY1(1, "cray1"),
        CRAY2(2, "cray2"),
        CRAY3(3, "cray3"),
        CRAY4(4, "cray4"),
        CRAY5(5, "cray5"),
        CRAY6(6, "cray6"),
        CRAY7(7, "cray7"),
        CRAY8(8, "cray8"),
        CRAY9(9, "cray9"),
        CRAY10(10, "cray10"),
        CRAY11(11, "cray11"),
        CRAY12(12, "cray12"),
        CRAY13(13, "cray13"),
        CRAY14(14, "cray14"),
        CRAY15(15, "cray15"),
        CRAY16(16, "cray16"),
        CRAY17(17, "cray17"),
        CRAY18(18, "cray18"),
        CRAY19(19, "cray19"),
        CRAY20(20, "cray20"),
        CRAY21(21, "cray21"),
        CRAY22(22, "cray22"),
        CRAY23(23, "cray23"),
        CRAY24(24, "cray24"),
        CRAY25(25, "cray25"),
        CRAY26(26, "cray26"),
        CRAY27(27, "cray27");

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

    static class CrayfishData extends AgeableMob.AgeableMobGroupData {
        public final Variant variant;

        CrayfishData(Variant variant) {
            super(true);
            this.variant = variant;
        }
    }
}