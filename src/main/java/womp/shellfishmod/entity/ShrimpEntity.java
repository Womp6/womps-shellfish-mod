package womp.shellfishmod.entity;

import java.util.function.IntFunction;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.Codec;

import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.ai.pathing.AmphibiousSwimNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.Util;
import net.minecraft.util.function.ValueLists;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import womp.shellfishmod.entity.goals.FollowParentInWaterGoal;
import womp.shellfishmod.entity.goals.ShellfishLayEggGoal;
import womp.shellfishmod.entity.goals.ShellfishMateGoal;
import womp.shellfishmod.entity.goals.SwimToRandomPlaceGoal;
import womp.shellfishmod.entity.goals.WanderToWaterGoal;
import womp.shellfishmod.entity.parents.EggLaying;
import womp.shellfishmod.entity.parents.ShellfishEntity;
import womp.shellfishmod.entity.parents.ShellfishEntity.ShellfishVariant;
import womp.shellfishmod.entity.ShrimpEntity.Variant;
import womp.shellfishmod.registry.ShellfishBlocks;
import womp.shellfishmod.registry.ShellfishEntities;
import womp.shellfishmod.registry.ShellfishItems;
import womp.shellfishmod.registry.ShellfishSounds;
import womp.shellfishmod.util.ShellfishTags;

public class ShrimpEntity extends ShellfishEntity<Variant> implements EggLaying {

    public final AnimationState swimAnimationState = new AnimationState();

    public ShrimpEntity(EntityType<? extends ShrimpEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new ShrimpMoveControl(this);
        depth = 20;
        airBreathing = false;
    }

    public static DefaultAttributeContainer.Builder createShrimpAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.MAX_HEALTH, 4.0d);
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new AmphibiousSwimNavigation(this, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new ShrimpWaterLayEggGoal(this, 1));
        this.goalSelector.add(0, new ShellfishLayEggGoal(this, 0.2, ShellfishSounds.SHRIMP_LAYS_EGGS, ShellfishBlocks.SHRIMP_EGGS_BLOCK));
        this.goalSelector.add(1, new ShrimpWaterMateGoal(this, 1));
        this.goalSelector.add(1, new FollowParentInWaterGoal(this, 1.1));
        this.goalSelector.add(2, new ShellfishMateGoal(this, 0.2));
        this.goalSelector.add(2, new FollowParentGoal(this, 0.3));
        this.goalSelector.add(3, new SwimToRandomPlaceGoal(this));
        this.goalSelector.add(5, new WanderToWaterGoal(this, 0.2));
        this.goalSelector.add(7, new WanderAroundGoal(this, 0.2));
        this.goalSelector.add(9, new LookAroundGoal(this));
    }

    @Override
    public boolean isBreedingItem(ItemStack item) {
        return item.isIn(ShellfishTags.Items.SHRIMP_FOOD);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        ShrimpEntity child;
        if((child = ShellfishEntities.SHRIMP.create(world, SpawnReason.BREEDING)) != null && entity instanceof ShrimpEntity mate) {
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
    protected void setupAnimationStates() {
       
        if (isIdle()) {
            this.swimAnimationState.stop();
            this.idleAnimationState.startIfNotRunning(this.age);
        } else this.idleAnimationState.stop();

        if (isWalking()) {
            this.swimAnimationState.startIfNotRunning(this.age);
        } else this.swimAnimationState.stop();
    }

    @Override
    public void travel(Vec3d movementInput) {
        if (this.canMoveVoluntarily() && this.isTouchingWater()) {
            this.updateVelocity(0.01f, movementInput);
            this.move(MovementType.SELF, this.getVelocity());
            this.setVelocity(this.getVelocity().multiply(0.9));
            if (this.getTarget() == null) {
                this.setVelocity(this.getVelocity().add(0.0, -0.005, 0.0));
            }
        } else {
            super.travel(movementInput);
        }
    }

    @Override
    public int getLimitPerChunk() {
        return 7;
    }

    @Override
    public ItemStack getBucketItem() {
        return new ItemStack(ShellfishItems.SHRIMP_BUCKET);
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return ShellfishSounds.SHRIMP_DEATH;
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(DamageSource source) {
        return ShellfishSounds.SHRIMP_HURT;
    }

    @Override
    public int getMaxAir() {
        return 4000;
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
        if (entityData instanceof ShrimpData) {
            variant = ((ShrimpData)entityData).variant;
        } else {
            variant = Util.getRandom(Variant.values(), random);
            entityData = new ShrimpData(variant);
        }
        this.setVariant(variant);
        this.setNewborn(true);
        return super.initialize(world, difficulty, spawnReason, entityData);
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
            if (this.shrimp.isTouchingWater()) {
                if (this.shrimp.isSubmergedIn(FluidTags.WATER)) {
                    this.shrimp.setVelocity(this.shrimp.getVelocity().add(0.0, 0.001, 0.0));
                }
                if (this.state != MoveControl.State.MOVE_TO || this.shrimp.getNavigation().isIdle()) {
                    this.shrimp.setMovementSpeed(0.0f);
                    return;
                }
                float f = (float)(this.speed * this.shrimp.getAttributeValue(EntityAttributes.MOVEMENT_SPEED));
                this.shrimp.setMovementSpeed(MathHelper.lerp(0.125f, this.shrimp.getMovementSpeed(), f));
                double d = this.targetX - this.shrimp.getX();
                double e = this.targetY - this.shrimp.getY();
                double g = this.targetZ - this.shrimp.getZ();
                if (e != 0.0) {
                    double h = Math.sqrt(d * d + e * e + g * g);
                    this.shrimp.setVelocity(this.shrimp.getVelocity().add(0.0, (double)this.shrimp.getMovementSpeed() * (e / h) * 0.1, 0.0));
                }
                if (d != 0.0 || g != 0.0) {
                    float i = (float)(MathHelper.atan2(g, d) * 57.2957763671875) - 90.0f;
                    this.shrimp.setYaw(this.wrapDegrees(this.shrimp.getYaw(), i, 90.0f));
                    this.shrimp.bodyYaw = this.shrimp.getYaw();
                }
            } else {
                super.tick();
            }
        }
    }

    static class ShrimpWaterMateGoal extends ShellfishMateGoal {
        private final World world;
        private final java.util.Random random;

        ShrimpWaterMateGoal(EggLaying shrimp, double speed) {
            super(shrimp, speed);
            this.world = shellfish.getEntity().getWorld();
            this.random = new java.util.Random();
        }
        
        @Override
        public boolean canStart() {
            if (!this.shellfish.getEntity().isSubmergedIn(FluidTags.WATER)) {
                return false;
            }
            
            BlockPos targetPos = this.shellfish.getEntity().getBlockPos().add(
                this.random.nextInt(10) - 5,
                this.random.nextInt(3) - 1,
                this.random.nextInt(10) - 5
            );

            if (!this.world.getBlockState(targetPos).getFluidState().isIn(FluidTags.WATER)) {
                return false;
            }
        
            Path path = this.shellfish.getEntity().getNavigation().findPathTo(targetPos, 1);
            if (path == null) {
                return false;
            }
        
            for (int i = 0; i < path.getLength(); i++) {
                BlockPos pathPos = path.getNodePos(i);
                if (!this.world.getBlockState(pathPos).getFluidState().isIn(FluidTags.WATER)) {
                    return false;
                }
            }
            
            return super.canStart();
        }
    }

    static class ShrimpWaterLayEggGoal extends ShellfishLayEggGoal {
        private final World world;
        private final java.util.Random random;

        ShrimpWaterLayEggGoal(EggLaying shrimp, double speed) {
            super(shrimp, speed, ShellfishSounds.SHRIMP_LAYS_EGGS, ShellfishBlocks.SHRIMP_EGGS_BLOCK);
            this.world = this.shellfish.getEntity().getWorld();
            this.random = new java.util.Random();
        }
        
        @Override
        public boolean canStart() {
            if (!this.shellfish.getEntity().isSubmergedIn(FluidTags.WATER)) {
                return false;
            }
            
            BlockPos targetPos = this.shellfish.getEntity().getBlockPos().add(
                this.random.nextInt(10) - 5,
                this.random.nextInt(3) - 1,
                this.random.nextInt(10) - 5
            );

            if (!this.world.getBlockState(targetPos).getFluidState().isIn(FluidTags.WATER)) {
                return false;
            }
        
            Path path = this.shellfish.getEntity().getNavigation().findPathTo(targetPos, 1);
            if (path == null) {
                return false;
            }
        
            for (int i = 0; i < path.getLength(); i++) {
                BlockPos pathPos = path.getNodePos(i);
                if (!this.world.getBlockState(pathPos).getFluidState().isIn(FluidTags.WATER)) {
                    return false;
                }
            }
            
            return super.canStart();
        }
    }

    public static enum Variant implements ShellfishVariant {
        SHRIMP0(0, "shrimp0"),
        SHRIMP1(1, "shrimp1"),
        SHRIMP2(2, "shrimp2"),
        SHRIMP3(3, "shrimp3"),
        SHRIMP4(4, "shrimp4");

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

    static class ShrimpData extends PassiveEntity.PassiveData {
        public final Variant variant;

        ShrimpData(Variant variant) {
            super(true);
            this.variant = variant;
        }
    }

    @Override
    protected Variant byId(int id) {
        return Variant.byId(id);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
    }
}
