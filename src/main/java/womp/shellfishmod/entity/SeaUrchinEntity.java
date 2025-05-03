package womp.shellfishmod.entity;


import org.jetbrains.annotations.Nullable;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import womp.shellfishmod.entity.goals.SitAroundGoal;
import womp.shellfishmod.entity.goals.WanderInWaterGoal;
import womp.shellfishmod.entity.goals.WanderToWaterGoal;
import womp.shellfishmod.entity.parents.ShellfishEntity;
import womp.shellfishmod.registry.ShellfishEntities;
import womp.shellfishmod.registry.ShellfishItems;
import womp.shellfishmod.registry.ShellfishSounds;

public class SeaUrchinEntity extends ShellfishEntity {

    public SeaUrchinEntity(EntityType<? extends SeaUrchinEntity> entityType, World world) {
        super(entityType, world);
        this.variantChance = .5f;
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
}
