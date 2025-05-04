package womp.shellfishmod.entity;

import org.jetbrains.annotations.Nullable;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import womp.shellfishmod.entity.goals.SitAroundGoal;
import womp.shellfishmod.entity.goals.WanderInWaterGoal;
import womp.shellfishmod.entity.parents.ShellfishEntity;
import womp.shellfishmod.registry.ShellfishEntities;
import womp.shellfishmod.registry.ShellfishItems;
import womp.shellfishmod.registry.ShellfishSounds;

public class MusselEntity extends ShellfishEntity {
    
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
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 3.0d).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.05);
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
        if((child = ShellfishEntities.MUSSEL.create(world)) != null && entity instanceof MusselEntity mate) {
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
}
