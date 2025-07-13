package womp.shellfishmod.entity.parents;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.*;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import womp.shellfishmod.registry.ShellfishWorldgen;
import womp.shellfishmod.util.ShellfishTags;

public abstract class ShellfishEntity<T extends Enum<T> & ShellfishEntity.ShellfishVariant> extends Animal implements Bucketable {

    protected @Nullable SoundEvent hostileSound;
    protected static int depth;
    protected boolean isIdleEntity;
    protected boolean waterWalking;
    protected boolean waterIdle;
    protected boolean airBreathing;
    protected boolean slow;
    protected boolean brokenAnim;
    protected float tSpeed;

    protected abstract T byId(int id);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState moveAnimationState = new AnimationState();
    private int tickCounter = 0;    // For hungry feature
    private int slowCounter = 0;    // For mobs with broken animations
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(ShellfishEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(ShellfishEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> NEWBORN = SynchedEntityData.defineId(ShellfishEntity.class, EntityDataSerializers.BOOLEAN);
    public int partnerVariantStorage = -1;

    public ShellfishEntity(EntityType<? extends ShellfishEntity<?>> entityType, Level world) {
        super(entityType, world);
        this.setPathfindingMalus(PathType.WATER, 0.0f);
        hostileSound = null;    // Gets the hostile sound, if any
        depth = 26;             // Indicates the spawn depth of the mob; 26 blocks is default
        isIdleEntity = false;   // For mobs that do mostly nothing, such as the clam
        waterWalking = true;    // Uses manually programmed animations in water only, and vanilla ones on land
        waterIdle = false;      // For mobs such as the clam that need to be in water to run idle animations
        airBreathing = true;    // True: entity can breathe air | False: entity can drown
        slow = true;            // For mobs that don't use custom travel method
        brokenAnim = false;     // A fix for mobs with broken walking animations that glitch and lag
        tSpeed = 0.06f;         // The speed for the travel method to use
    }

    public boolean isIdle() {
        if (waterIdle) {
            return this.xOld == this.getX() && this.zOld == this.getZ() && this.isInWater();
        }
        return this.xOld == this.getX() && this.zOld == this.getZ();
    }

    /**
     * This is a list of the conditions of this method per each mob:
     * <ul>
     * <p> Crayfish: moving and in water
     * <p> Lobster: moving and in water
     * <p> Crab: moving and in water
     * <p> Shrimp: moving and in water
     * <p> Sea Snail: moving water or not
     * <p> Sea Urchin: moving water or not
     * <p> Clam: moving and in water
     * <p> Oyster: moving and in water
     * <p> Mussel: moving and in water
     */
    public boolean isWalking() {
        if (this.xOld != this.getX()) {
            return waterWalking ? this.isInWater() : true;
        }
        if (this.zOld != this.getZ()) {
            return waterWalking ? this.isInWater() : true;
        }
        return false;
    }

    // Default animation conditions
    protected void setupAnimationStates() {

        if (brokenAnim ? isIdle() && slowCounter >= 3 : isIdle()) {
            this.idleAnimationState.startIfStopped(this.age);
            this.moveAnimationState.stop();
        } else {
            this.idleAnimationState.stop();
            slowCounter++;
        }

        if (this.isWalking()) {
            slowCounter = 0;
            this.moveAnimationState.startIfStopped(this.age);
        }

        if (isIdleEntity && !isInWater()) {
            this.moveAnimationState.stop();
            slowCounter = 0;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide && this.isBaby()) this.setNewborn(this.getAge() <= -12000);

        // ANIMATION
        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }

        // HUNGRY
        if (this instanceof Hungry hungry) {
            tickCounter = hungry.tick(tickCounter);
        }
    }

    @Override
    public boolean checkSpawnObstruction(LevelReader world) {
        return world.isUnobstructed(this);
    }

    public static boolean canSpawn(EntityType<? extends ShellfishEntity<?>> type, LevelReader world, EntitySpawnReason reason, BlockPos pos, RandomSource random) {
        int i = world.getSeaLevel();
        int j = i - depth;
        return pos.getY() >= j && pos.getY() < i && (world.getFluidState(pos.above()).is(FluidTags.WATER) || world.getFluidState(pos.below()).is(FluidTags.WATER)) && world.getFluidState(pos).is(FluidTags.WATER) && ((world.getBiome(pos).is(ShellfishWorldgen.MARSH) || world.getBiome(pos).is(Biomes.SWAMP) || world.getBiome(pos).is(Biomes.MANGROVE_SWAMP)) ? isBrightEnoughToSpawn(world, pos) : true);
    }

    @Override
    public boolean fromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    // Default travel method for in water movement (to make it look natural)
    public void travel(Vec3 movementInput) {
        if (this.isInWater() && slow && this.isEffectiveAi()) {
            this.moveRelative(tSpeed, movementInput);
            this.move(MoverType.SELF, this.getDeltaMovement());
            if (this.isAggressive()) this.setDeltaMovement(this.getDeltaMovement().multiply(.8, .85, .8).add(0, -0.01, 0));
            else this.setDeltaMovement(this.getDeltaMovement().multiply(.8, .825, .8).add(0, -0.01d, 0));
        } else {
            super.travel(movementInput);
        }
    }

    @Override
    public void setFromBucket(boolean fromBucket) {
        this.entityData.set(FROM_BUCKET, fromBucket);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);
        pBuilder.define(VARIANT, 0);
        pBuilder.define(FROM_BUCKET, false);
        pBuilder.define(NEWBORN, true);
        pBuilder.define(EggLaying.HAS_EGG, false);
        pBuilder.define(Hungry.IS_HUNGRY, false);
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
        if (this.isInvulnerableTo(level, source)) {
            return false;
        } else {
            Entity entity = source.getEntity();
            if (entity != null && !(entity instanceof Player) && !(entity instanceof Arrow)) {
                amount = (amount + 1.0f) / 2.0f;
            }
            return super.hurtServer(level, source, amount);
        }
    }

    @Override
    public boolean doHurtTarget(ServerLevel level, Entity target) {
        if (hostileSound == null) {return false;}
        boolean bl = target.hurtServer(level, this.damageSources().mobAttack(this), (float)(this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if (bl) {
            this.setLastHurtMob(target);
            this.playSound(hostileSound, 1.0f, 1.0f);
        }

        return bl;
    }

    public T getVariant() {
        return byId(this.entityData.get(VARIANT));
    }

    public void setVariant(T variant) {
        this.entityData.set(VARIANT, variant.getIndex());
    }

    @Override
    public void addAdditionalSaveData(ValueOutput nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("Variant", this.getVariant().getIndex());
        nbt.putBoolean("FromBucket", this.fromBucket());
        nbt.putBoolean("newborn", this.isNewborn());
        if (this instanceof EggLaying egg) {
            nbt.putBoolean("HasEgg", egg.hasEgg());
            nbt.putInt("partnerVariantStorage", partnerVariantStorage);
        }
        if (this instanceof Hungry hungry) {
            nbt.putBoolean("IsHungry", hungry.isHungry());
        }
    }

    @Override
    public void readAdditionalSaveData(ValueInput nbt) {
        super.readAdditionalSaveData(nbt);
        setVariant(byId(nbt.getIntOr("Variant", 0)));
        this.setFromBucket(nbt.getBooleanOr("FromBucket", false));
        this.setNewborn(nbt.getBooleanOr("newborn", true));
        if (this instanceof EggLaying egg) {
            egg.setHasEgg(nbt.getBooleanOr("HasEgg", false));
            partnerVariantStorage = nbt.getIntOr("partnerVariantStorage", -1);
        }
        if (this instanceof Hungry hungry) {
            hungry.setHungry(nbt.getBooleanOr("IsHungry", false));
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void saveToBucketTag(ItemStack bucket) {
        Bucketable.saveDefaultDataToBucketTag(this, bucket);
        CustomData.update(DataComponents.BUCKET_ENTITY_DATA, bucket, nbtCompound -> {
            nbtCompound.putInt("Variant", this.getVariant().getIndex());
            nbtCompound.putFloat("Health", this.getHealth());
            nbtCompound.putInt("Age", this.getAge());
            nbtCompound.putBoolean("newborn", this.isNewborn());
        });
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        return Bucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
    }

    @Override
    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.fromBucket();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void loadFromBucketTag(CompoundTag nbt) {
        Bucketable.loadDefaultDataFromBucketTag(this, nbt);
        this.setVariant(byId(nbt.getIntOr("Variant", 0)));
        if (nbt.contains("Age")) {
            this.setAge(nbt.getIntOr("Age", 2000));
        }
    }

    @Override
    public SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_FILL_FISH;
    }

    // Default max variant count
    public int getMaxVariants() {
        return 5;
    }

    // Default max air (for clams, oysters, mussles)
    @Override
    public int getMaxAirSupply() {
        return 32767;
    }

    protected void tickAir(int air) {
        if (airBreathing) return;
        if (this.isAlive() && !this.isInWaterOrRain()) {
            this.setAirSupply(air - 1);
            if (this.getAirSupply() == -20) {
                this.setAirSupply(0);
                this.hurt(level().damageSources().dryOut(), 2.0f);
            }
        } else {
            this.setAirSupply(this.getMaxAirSupply());
        }

    }

    // Used for suffocation on land when needed
    @Override
    public void baseTick() {
        int i = this.getAirSupply();
        super.baseTick();
        if (airBreathing) return;
        if (!this.isNoAi()) {
            this.tickAir(i);
        }
    }

    // Default chunk limit
    @Override
    public int getMaxSpawnClusterSize() {
        return 6;
    }

    @Override
    public float getWalkTargetValue(BlockPos pPos, LevelReader pLevel) {
        return 0.0F;
    }

    // Default
    @Override
    public boolean isFood(ItemStack item) {
        return item.is(ShellfishTags.Items.EMPTY_TAG);
    }

    public void setVariantNumerical(int variant) {
        setVariant(byId(variant));
    }

    public interface ShellfishVariant extends StringRepresentable {
        int getIndex();
    }

    public void setNewborn(boolean value) {
        this.entityData.set(NEWBORN, value);
    }

    public boolean isNewborn() {
        return this.entityData.get(NEWBORN);
    }


    // Used in renderers
    public void scale(PoseStack poseStack, float scale, float babyScale) {
        scale(poseStack, scale, babyScale, babyScale);
    }

    public void scale(PoseStack poseStack, float scale, float babyScale, float smallBabyScale) {
        if (this.isBaby() && this.isNewborn()) poseStack.scale(smallBabyScale, smallBabyScale, smallBabyScale);
        else if (this.isBaby() && !this.isNewborn()) poseStack.scale(babyScale, babyScale, babyScale);
        else poseStack.scale(scale, scale, scale);
    }
}
