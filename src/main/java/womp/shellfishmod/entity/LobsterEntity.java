package womp.shellfishmod.entity;

import java.util.function.IntFunction;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.Codec;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.Util;
import net.minecraft.util.function.ValueLists;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
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

    public LobsterEntity(EntityType<? extends LobsterEntity> entityType, World world) {
        super(entityType, world);
        depth = 50;
        hostileSound = ShellfishSounds.LOBSTER_ATTACK;
    }

    public static DefaultAttributeContainer.Builder createLobsterAttributes() {
        return HostileEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0d).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0d).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, .15);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new ShellfishLayEggGoal(this, 1, ShellfishSounds.LOBSTER_LAYS_EGGS, ShellfishBlocks.LOBSTER_EGGS_BLOCK));
        this.goalSelector.add(1, new ShellfishMateGoal(this, 1));
        this.goalSelector.add(1, new FollowParentGoal(this, 1.1));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1d, true));
        this.targetSelector.add(2, new HungryRevengeGoal(this, new Class[0]));
        this.targetSelector.add(2, new HungryActiveTargetGoal<>(this, FishEntity.class, false));
        this.targetSelector.add(2, new HungryActiveTargetGoal<>(this, DrownedEntity.class, false));
        this.targetSelector.add(2, new HungryActiveTargetGoal<>(this, CrabEntity.class, false));
        this.targetSelector.add(2, new HungryActiveTargetGoal<>(this, ShrimpEntity.class, false));
        this.targetSelector.add(2, new HungryActiveTargetGoal<>(this, SeaSnailEntity.class, false));
        this.targetSelector.add(2, new HungryActiveTargetGoal<>(this, SeaUrchinEntity.class, false));
        this.targetSelector.add(2, new HungryActiveTargetGoal<>(this, ClamEntity.class, false));
        this.targetSelector.add(2, new HungryActiveTargetGoal<>(this, OysterEntity.class, false));
        this.targetSelector.add(2, new HungryActiveTargetGoal<>(this, MusselEntity.class, false));
        this.goalSelector.add(3, new WanderInWaterGoal(this, 1));
        this.goalSelector.add(5, new WanderToWaterGoal(this, 1));
        this.goalSelector.add(7, new WanderAroundGoal(this, 1));
        this.goalSelector.add(9, new LookAroundGoal(this));
    }

    @Override
    public boolean isBreedingItem(ItemStack item) {
        return item.isIn(ShellfishTags.Items.LOBSTER_FOOD);
    }
    
    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        LobsterEntity child;
        if((child = ShellfishEntities.LOBSTER.create(world)) != null && entity instanceof LobsterEntity mate) {
            child.setVariant((random.nextBoolean() ? this : mate).getVariant());
            child.setPersistent();
            return child;
        }
        return null;
    }

    @Override
    protected void eat(PlayerEntity player, Hand hand, ItemStack stack) {
        if (stack.isOf(Items.TROPICAL_FISH_BUCKET)) {
            player.setStackInHand(hand, new ItemStack(Items.WATER_BUCKET));
        } else if (stack.isOf(Items.COD_BUCKET)) {
            player.setStackInHand(hand, new ItemStack(Items.WATER_BUCKET));
        } else if (stack.isOf(Items.SALMON_BUCKET)) {
            player.setStackInHand(hand, new ItemStack(Items.WATER_BUCKET));
        } else if (stack.isOf(Items.PUFFERFISH_BUCKET)) {
            player.setStackInHand(hand, new ItemStack(Items.WATER_BUCKET));
        } else if (stack.isOf(ShellfishItems.CRAB_BUCKET)) {
            player.setStackInHand(hand, new ItemStack(Items.WATER_BUCKET));
        } else if (stack.isOf(ShellfishItems.CRAYFISH_BUCKET)) {
            player.setStackInHand(hand, new ItemStack(Items.WATER_BUCKET));
        } else if (stack.isOf(ShellfishItems.SHRIMP_BUCKET)) {
            player.setStackInHand(hand, new ItemStack(Items.WATER_BUCKET));
        } else if (stack.isOf(ShellfishItems.SEA_SNAIL_BUCKET)) {
            player.setStackInHand(hand, new ItemStack(Items.WATER_BUCKET));
        } else if (stack.isOf(ShellfishItems.SEA_URCHIN_BUCKET)) {
            player.setStackInHand(hand, new ItemStack(Items.WATER_BUCKET));
        } else {
            super.eat(player, hand, stack);
        }
    }

    @Override
    public int getLimitPerChunk() {
        return 4;
    }

    @Override
    public ItemStack getBucketItem() {
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
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        Variant variant;
        Random random = world.getRandom();
        if (entityData instanceof LobsterData) {
            variant = ((LobsterData)entityData).variant;
        } else {
            variant = Util.getRandom(Variant.values(), random);
            entityData = new LobsterData(variant);
        }
        this.setVariant(variant);
        this.setNewborn(true);
        return super.initialize(world, difficulty, spawnReason, entityData);
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
        public String asString() {
            return this.name;
        }

        static {
            CODEC = StringIdentifiable.createCodec(Variant::values);
            BY_ID = ValueLists.createIdToValueFunction(Variant::getIndex, Variant.values(), ValueLists.OutOfBoundsHandling.CLAMP);
        }
    }

    static class LobsterData extends PassiveEntity.PassiveData {
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