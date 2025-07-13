package womp.shellfishmod.entity;

import com.mojang.serialization.Codec;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import womp.shellfishmod.entity.goals.*;
import womp.shellfishmod.entity.parents.EggLaying;
import womp.shellfishmod.entity.parents.ShellfishEntity;
import womp.shellfishmod.registry.ShellfishBlocks;
import womp.shellfishmod.registry.ShellfishItems;
import womp.shellfishmod.util.ShellfishTags;
import womp.shellfishmod.registry.ShellfishEntities;
import womp.shellfishmod.registry.ShellfishSounds;

import java.util.function.IntFunction;

public class ShrimpEntity extends ShellfishEntity<ShrimpEntity.Variant> implements EggLaying {

    public final AnimationState swimAnimationState = new AnimationState();

    public ShrimpEntity(EntityType<? extends ShrimpEntity> entityType, Level world) {
        super(entityType, world);
        this.moveControl = new ShrimpMoveControl(this);
        depth = 20;
        airBreathing = false;
    }

    public static AttributeSupplier.Builder createShrimpAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 4.0d);
    }

    @Override
    protected PathNavigation createNavigation(Level world) {
        return new AmphibiousPathNavigation(this, world);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new ShrimpWaterLayEggGoal(this, 1));
        this.goalSelector.addGoal(0, new ShellfishLayEggGoal(this, 0.2, ShellfishSounds.SHRIMP_LAYS_EGGS.get(), ShellfishBlocks.SHRIMP_EGGS_BLOCK.get()));
        this.goalSelector.addGoal(1, new ShrimpWaterMateGoal(this, 1));
        this.goalSelector.addGoal(1, new FollowParentInWaterGoal(this, 1.1));
        this.goalSelector.addGoal(2, new ShellfishMateGoal(this, 0.2));
        this.goalSelector.addGoal(2, new FollowParentGoal(this, 0.3));
        this.goalSelector.addGoal(3, new SwimToRandomPlaceGoal(this));
        this.goalSelector.addGoal(5, new WanderToWaterGoal(this, 0.2));
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 0.2));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
    }

    @Override
    public boolean isFood(ItemStack item) {
        return item.is(ShellfishTags.Items.SHRIMP_FOOD);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob entity) {
        ShrimpEntity child;
        if((child = ShellfishEntities.SHRIMP.get().create(world)) != null && entity instanceof ShrimpEntity mate) {
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
    protected Variant byId(int id) {
        return Variant.byId(id);
    }

    @Override
    protected void setupAnimationStates() {

        if (isIdle()) {
            this.swimAnimationState.stop();
            this.idleAnimationState.startIfStopped(this.age);
        } else this.idleAnimationState.stop();

        if (isWalking()) {
            this.swimAnimationState.startIfStopped(this.age);
        } else this.swimAnimationState.stop();
    }

    @Override
    public void travel(Vec3 movementInput) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(0.01f, movementInput);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.005, 0.0));
            }
        } else {
            super.travel(movementInput);
        }
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 7;
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(ShellfishItems.SHRIMP_BUCKET.get());
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return ShellfishSounds.SHRIMP_DEATH.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(DamageSource source) {
        return ShellfishSounds.SHRIMP_HURT.get();
    }

    @Override
    public int getMaxAirSupply() {
        return 4000;
    }

    @Override
    public ShellfishEntity<Variant> getEntity() {
        return this;
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, SpawnGroupData entityData) {
        Variant variant;
        RandomSource random = world.getRandom();
        if (entityData instanceof ShrimpData) {
            variant = ((ShrimpData)entityData).variant;
        } else {
            variant = Util.getRandom(Variant.values(), random);
            entityData = new ShrimpData(variant);
        }
        this.setVariant(variant);
        this.setNewborn(true);
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
    }


    static class ShrimpMoveControl
            extends MoveControl {
        private final ShrimpEntity shrimp;

        ShrimpMoveControl(ShrimpEntity owner) {
            super(owner);
            this.shrimp = owner;
        }

        @Override
        public void tick() {
            if (this.shrimp.isInWater()) {
                if (this.shrimp.isEyeInFluid(FluidTags.WATER)) {
                    this.shrimp.setDeltaMovement(this.shrimp.getDeltaMovement().add(0.0, 0.001, 0.0));
                }
                if (this.operation != MoveControl.Operation.MOVE_TO || this.shrimp.getNavigation().isDone()) {
                    this.shrimp.setSpeed(0.0f);
                    return;
                }
                float f = (float)(this.speedModifier * this.shrimp.getAttributeValue(Attributes.MOVEMENT_SPEED));
                this.shrimp.setSpeed(Mth.lerp(0.125f, this.shrimp.getSpeed(), f));
                double d = this.wantedX - this.shrimp.getX();
                double e = this.wantedY - this.shrimp.getY();
                double g = this.wantedZ - this.shrimp.getZ();
                if (e != 0.0) {
                    double h = Math.sqrt(d * d + e * e + g * g);
                    this.shrimp.setDeltaMovement(this.shrimp.getDeltaMovement().add(0.0, (double)this.shrimp.getSpeed() * (e / h) * 0.1, 0.0));
                }
                if (d != 0.0 || g != 0.0) {
                    float i = (float)(Mth.atan2(g, d) * 57.2957763671875) - 90.0f;
                    this.shrimp.setYRot(this.rotlerp(this.shrimp.getYRot(), i, 90.0f));
                    this.shrimp.yBodyRot = this.shrimp.getYRot();
                }
            } else {
                super.tick();
            }
        }
    }

    static class ShrimpWaterMateGoal extends ShellfishMateGoal {
        private final Level world;
        private final java.util.Random random;

        ShrimpWaterMateGoal(EggLaying shrimp, double speed) {
            super(shrimp, speed);
            this.world = shellfish.getEntity().level();
            this.random = new java.util.Random();
        }

        @Override
        public boolean canUse() {
            if (!this.shellfish.getEntity().isEyeInFluid(FluidTags.WATER)) {
                return false;
            }

            BlockPos targetPos = this.shellfish.getEntity().blockPosition().offset(
                    this.random.nextInt(10) - 5,
                    this.random.nextInt(3) - 1,
                    this.random.nextInt(10) - 5
            );

            if (!this.world.getBlockState(targetPos).getFluidState().is(FluidTags.WATER)) {
                return false;
            }

            Path path = this.shellfish.getEntity().getNavigation().createPath(targetPos, 1);
            if (path == null) {
                return false;
            }

            for (int i = 0; i < path.getNodeCount(); i++) {
                BlockPos pathPos = path.getNodePos(i);
                if (!this.world.getBlockState(pathPos).getFluidState().is(FluidTags.WATER)) {
                    return false;
                }
            }

            return super.canUse();
        }
    }

    static class ShrimpWaterLayEggGoal extends ShellfishLayEggGoal {
        private final Level world;
        private final java.util.Random random;

        ShrimpWaterLayEggGoal(EggLaying shrimp, double speed) {
            super(shrimp, speed, ShellfishSounds.SHRIMP_LAYS_EGGS.get(), ShellfishBlocks.SHRIMP_EGGS_BLOCK.get());
            this.world = this.shellfish.getEntity().level();
            this.random = new java.util.Random();
        }

        @Override
        public boolean canUse() {
            if (!this.shellfish.getEntity().isEyeInFluid(FluidTags.WATER)) {
                return false;
            }

            BlockPos targetPos = this.shellfish.getEntity().blockPosition().offset(
                    this.random.nextInt(10) - 5,
                    this.random.nextInt(3) - 1,
                    this.random.nextInt(10) - 5
            );

            if (!this.world.getBlockState(targetPos).getFluidState().is(FluidTags.WATER)) {
                return false;
            }

            Path path = this.shellfish.getEntity().getNavigation().createPath(targetPos, 1);
            if (path == null) {
                return false;
            }

            for (int i = 0; i < path.getNodeCount(); i++) {
                BlockPos pathPos = path.getNodePos(i);
                if (!this.world.getBlockState(pathPos).getFluidState().is(FluidTags.WATER)) {
                    return false;
                }
            }

            return super.canUse();
        }
    }

    public enum Variant implements ShellfishVariant
    {
        SHRIMP0(0, "shrimp0"),
        SHRIMP1(1, "shrimp1"),
        SHRIMP2(2, "shrimp2"),
        SHRIMP3(3, "shrimp3"),
        SHRIMP4(4, "shrimp4");

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

    static class ShrimpData extends AgeableMobGroupData {
        public final Variant variant;

        ShrimpData(Variant variant) {
            super(true);
            this.variant = variant;
        }
    }
}