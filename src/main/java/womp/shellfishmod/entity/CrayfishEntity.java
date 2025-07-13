package womp.shellfishmod.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.Turtle;
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

public class CrayfishEntity extends ShellfishEntity implements Hungry, EggLaying {

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
        this.targetSelector.addGoal(2, new HungryActiveTargetGoal<>(this, Turtle.class, 10, true, true, LivingEntity::isBaby));
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
        if((child = ShellfishEntities.CRAYFISH.get().create(world)) != null && entity instanceof CrayfishEntity mate) {
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
    public ShellfishEntity getEntity() {
        return this;
    }

    @Override
    public int getMaxVariants() {
        return 28;
    }
}