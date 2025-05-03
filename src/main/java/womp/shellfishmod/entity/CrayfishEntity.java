package womp.shellfishmod.entity;


import org.jetbrains.annotations.Nullable;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TadpoleEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import womp.shellfishmod.entity.goals.HungryActiveTargetGoal;
import womp.shellfishmod.entity.goals.ShellfishLayEggGoal;
import womp.shellfishmod.entity.goals.ShellfishMateGoal;
import womp.shellfishmod.entity.goals.WanderInWaterGoal;
import womp.shellfishmod.entity.goals.WanderToWaterGoal;
import womp.shellfishmod.entity.parents.EggLaying;
import womp.shellfishmod.entity.parents.Hungry;
import womp.shellfishmod.entity.parents.ShellfishEntity;
import womp.shellfishmod.registry.ShellfishBlocks;
import womp.shellfishmod.registry.ShellfishEntities;
import womp.shellfishmod.registry.ShellfishItems;
import womp.shellfishmod.registry.ShellfishSounds;
import womp.shellfishmod.util.ShellfishTags;

public class CrayfishEntity extends ShellfishEntity implements Hungry, EggLaying {

    public CrayfishEntity(EntityType<? extends CrayfishEntity> entityType, World world) {
        super(entityType, world);
        this.hostileSound = ShellfishSounds.CRAYFISH_ATTACK;
    }

    public static DefaultAttributeContainer.Builder createCrayfishAttributes() {
        return HostileEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0d).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0d).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.14);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new ShellfishLayEggGoal(this, 1, ShellfishSounds.CRAYFISH_LAYS_EGGS, ShellfishBlocks.CRAYFISH_EGGS_BLOCK));
        this.goalSelector.add(1, new ShellfishMateGoal(this, 1));
        this.goalSelector.add(1, new FollowParentGoal(this, 1.1));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1d, true));
        this.targetSelector.add(2, new RevengeGoal(this, new Class[0]));
        this.targetSelector.add(2, new HungryActiveTargetGoal<>(this, TadpoleEntity.class, false));
        this.targetSelector.add(2, new HungryActiveTargetGoal<>(this, FishEntity.class, false));
        this.targetSelector.add(2, new HungryActiveTargetGoal<>(this, TurtleEntity.class, 10, true, true, (entity) -> {
            return entity.isBaby();
        }));
        this.targetSelector.add(2, new HungryActiveTargetGoal<>(this, ShrimpEntity.class, false));
        this.targetSelector.add(2, new HungryActiveTargetGoal<>(this, SeaSnailEntity.class, false));
        this.targetSelector.add(2, new HungryActiveTargetGoal<>(this, ClamEntity.class, false));
        this.targetSelector.add(2, new HungryActiveTargetGoal<>(this, MusselEntity.class, false));
        this.targetSelector.add(2, new HungryActiveTargetGoal<>(this, MossBallEntity.class, false));
        this.goalSelector.add(3, new WanderInWaterGoal(this, 1));
        this.goalSelector.add(5, new WanderToWaterGoal(this, 1));
        this.goalSelector.add(7, new WanderAroundGoal(this, 1));
        this.goalSelector.add(9, new LookAroundGoal(this));
    }

    @Override
    public boolean isBreedingItem(ItemStack item) {
        return item.isIn(ShellfishTags.Items.CRAYFISH_FOOD);
    }
    
    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        CrayfishEntity child;
        if((child = ShellfishEntities.CRAYFISH.create(world)) != null && entity instanceof CrayfishEntity mate) {
            child.setVariant((random.nextBoolean() ? this : mate).getVariant());
            child.setPersistent();
            return child;
        }
        return null;
    }

    @Override
    protected void eat(PlayerEntity player, Hand hand, ItemStack stack) {
        if (stack.isOf(ShellfishItems.MOSS_BALL_BUCKET)) {
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
        return new ItemStack(ShellfishItems.CRAYFISH_BUCKET);
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return ShellfishSounds.CRAYFISH_DEATH;
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(DamageSource source) {
        return ShellfishSounds.CRAYFISH_HURT;
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
