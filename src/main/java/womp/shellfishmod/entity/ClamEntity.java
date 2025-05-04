package womp.shellfishmod.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import womp.shellfishmod.entity.goals.SitAroundGoal;
import womp.shellfishmod.entity.goals.WanderInWaterGoal;
import womp.shellfishmod.entity.parents.ShellfishEntity;
import womp.shellfishmod.registry.ShellfishItems;
import womp.shellfishmod.registry.ShellfishEntities;
import womp.shellfishmod.registry.ShellfishSounds;

public class ClamEntity extends ShellfishEntity {

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
}