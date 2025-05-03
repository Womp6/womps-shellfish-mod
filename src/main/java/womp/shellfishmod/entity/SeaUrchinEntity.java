package womp.shellfishmod.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import womp.shellfishmod.entity.goals.SitAroundGoal;
import womp.shellfishmod.entity.goals.WanderInWaterGoal;
import womp.shellfishmod.entity.goals.WanderToWaterGoal;
import womp.shellfishmod.entity.parents.ShellfishEntity;
import womp.shellfishmod.registry.ShellfishEntities;
import womp.shellfishmod.registry.ShellfishItems;
import womp.shellfishmod.registry.ShellfishSounds;

public class SeaUrchinEntity extends ShellfishEntity {

    public SeaUrchinEntity(EntityType<? extends SeaUrchinEntity> entityType, Level world) {
        super(entityType, world);
        this.variantChance = .5f;
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
        if((child = ShellfishEntities.SEA_URCHIN.get().create(world)) != null && entity instanceof SeaUrchinEntity mate) {
            child.setVariant((random.nextBoolean() ? this : mate).getVariant());
            child.setPersistenceRequired();
            return child;
        }
        return null;
    }

    public void travel(Vec3 movementInput) {
        if (this.isInWater() && this.isEffectiveAi()) {
            this.moveRelative(0.045f, movementInput);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().multiply(.1, .825, .1).add(0, -0.01d, 0));
        } else {
            super.travel(movementInput);
        }

    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(ShellfishItems.SEA_URCHIN_BUCKET.get());
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return ShellfishSounds.SEA_URCHIN_DEATH.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(DamageSource source) {
        return ShellfishSounds.SEA_URCHIN_HURT.get();
    }

    @Override
    public int getMaxAirSupply() {
        return 4000;
    }
}