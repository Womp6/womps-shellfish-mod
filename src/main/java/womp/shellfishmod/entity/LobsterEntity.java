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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.animal.fish.AbstractFish;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.zombie.Drowned;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import womp.shellfishmod.entity.goals.HungryActiveTargetGoal;
import womp.shellfishmod.entity.goals.HungryRevengeGoal;
import womp.shellfishmod.entity.goals.ShellfishLayEggGoal;
import womp.shellfishmod.entity.goals.ShellfishMateGoal;
import womp.shellfishmod.entity.goals.WanderInWaterGoal;
import womp.shellfishmod.entity.goals.WanderToWaterGoal;
import womp.shellfishmod.entity.parents.EggLaying;
import womp.shellfishmod.entity.parents.Hungry;
import womp.shellfishmod.entity.parents.ShellfishEntity;
import womp.shellfishmod.entity.parents.ShellfishEntity.ShellfishVariant;
import womp.shellfishmod.entity.LobsterEntity.Variant;
import womp.shellfishmod.registry.ShellfishBlocks;
import womp.shellfishmod.registry.ShellfishEntities;
import womp.shellfishmod.registry.ShellfishItems;
import womp.shellfishmod.registry.ShellfishSounds;
import womp.shellfishmod.util.ShellfishTags;

public class LobsterEntity extends ShellfishEntity<Variant> implements Hungry, EggLaying {

    public LobsterEntity(EntityType<? extends LobsterEntity> entityType, Level world) {
        super(entityType, world);
        depth = 50;
        hostileSound = ShellfishSounds.LOBSTER_ATTACK;
    }

    public static AttributeSupplier.Builder createLobsterAttributes() {
        return Monster.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0d).add(Attributes.ATTACK_DAMAGE, 2.0d).add(Attributes.MOVEMENT_SPEED, .15);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new ShellfishLayEggGoal(this, 1, ShellfishSounds.LOBSTER_LAYS_EGGS, ShellfishBlocks.LOBSTER_EGGS_BLOCK));
        this.goalSelector.addGoal(1, new ShellfishMateGoal(this, 1));
        this.goalSelector.addGoal(1, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1d, true));
        this.targetSelector.addGoal(2, new HungryRevengeGoal(this, new Class[0]));
        this.targetSelector.addGoal(2, new HungryActiveTargetGoal<>(this, AbstractFish.class, false));
        this.targetSelector.addGoal(2, new HungryActiveTargetGoal<>(this, Drowned.class, false));
        this.targetSelector.addGoal(2, new HungryActiveTargetGoal<>(this, CrabEntity.class, false));
        this.targetSelector.addGoal(2, new HungryActiveTargetGoal<>(this, ShrimpEntity.class, false));
        this.targetSelector.addGoal(2, new HungryActiveTargetGoal<>(this, SeaSnailEntity.class, false));
        this.targetSelector.addGoal(2, new HungryActiveTargetGoal<>(this, SeaUrchinEntity.class, false));
        this.targetSelector.addGoal(2, new HungryActiveTargetGoal<>(this, ClamEntity.class, false));
        this.targetSelector.addGoal(2, new HungryActiveTargetGoal<>(this, OysterEntity.class, false));
        this.targetSelector.addGoal(2, new HungryActiveTargetGoal<>(this, MusselEntity.class, false));
        this.goalSelector.addGoal(3, new WanderInWaterGoal(this, 1));
        this.goalSelector.addGoal(5, new WanderToWaterGoal(this, 1));
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
    }

    @Override
    public boolean isFood(ItemStack item) {
        return item.is(ShellfishTags.Items.LOBSTER_FOOD);
    }
    
    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob entity) {
        LobsterEntity child;
        if((child = ShellfishEntities.LOBSTER.create(world, EntitySpawnReason.BREEDING)) != null && entity instanceof LobsterEntity mate) {
            child.setVariant((random.nextBoolean() ? this : mate).getVariant());
            child.setPersistenceRequired();
            return child;
        }
        return null;
    }

    @Override
    protected void usePlayerItem(Player player, InteractionHand hand, ItemStack stack) {
        if (stack.is(Items.TROPICAL_FISH_BUCKET)) {
            player.setItemInHand(hand, new ItemStack(Items.WATER_BUCKET));
        } else if (stack.is(Items.COD_BUCKET)) {
            player.setItemInHand(hand, new ItemStack(Items.WATER_BUCKET));
        } else if (stack.is(Items.SALMON_BUCKET)) {
            player.setItemInHand(hand, new ItemStack(Items.WATER_BUCKET));
        } else if (stack.is(Items.PUFFERFISH_BUCKET)) {
            player.setItemInHand(hand, new ItemStack(Items.WATER_BUCKET));
        } else if (stack.is(ShellfishItems.CRAB_BUCKET)) {
            player.setItemInHand(hand, new ItemStack(Items.WATER_BUCKET));
        } else if (stack.is(ShellfishItems.CRAYFISH_BUCKET)) {
            player.setItemInHand(hand, new ItemStack(Items.WATER_BUCKET));
        } else if (stack.is(ShellfishItems.SHRIMP_BUCKET)) {
            player.setItemInHand(hand, new ItemStack(Items.WATER_BUCKET));
        } else if (stack.is(ShellfishItems.SEA_SNAIL_BUCKET)) {
            player.setItemInHand(hand, new ItemStack(Items.WATER_BUCKET));
        } else if (stack.is(ShellfishItems.SEA_URCHIN_BUCKET)) {
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
        return new ItemStack(ShellfishItems.LOBSTER_BUCKET);
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return ShellfishSounds.LOBSTER_DEATH;
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(DamageSource source) {
        return ShellfishSounds.LOBSTER_HURT;
    }

    @Override
    public ShellfishEntity<Variant> getEntity() {
        return this;
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @Nullable SpawnGroupData entityData) {
        Variant variant;
        RandomSource random = world.getRandom();
        if (entityData instanceof LobsterData) {
            variant = ((LobsterData)entityData).variant;
        } else {
            variant = Util.getRandom(Variant.values(), random);
            entityData = new LobsterData(variant);
        }
        this.setVariant(variant);
        this.setNewborn(true);
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
    }

    public static enum Variant implements ShellfishVariant {
        LOB0(0, "lob0"),
        LOB1(1, "lob1"),
        LOB2(2, "lob2"),
        LOB3(3, "lob3"),
        LOB4(4, "lob4");

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

    static class LobsterData extends AgeableMob.AgeableMobGroupData {
        public final Variant variant;

        LobsterData(Variant variant) {
            super(true);
            this.variant = variant;
        }
    }

    @Override
    protected Variant byId(int id) {
        return Variant.byId(id);
    }
}