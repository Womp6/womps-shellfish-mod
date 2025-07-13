package womp.shellfishmod.entity.parents;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
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
import net.minecraft.world.level.*;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import womp.shellfishmod.registry.ShellfishWorldgen;

public abstract class ShellfishEntity extends Animal implements Bucketable {

    protected @Nullable SoundEvent hostileSound;
    protected static int depth;
    protected @Nullable Float variantChance;
    protected boolean isIdleEntity;
    protected boolean waterWalking;
    protected boolean waterIdle;
    protected boolean airBreathing;
    protected boolean slow;
    protected boolean brokenAnim;
    protected float tSpeed;

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState moveAnimationState = new AnimationState();
    private int tickCounter = 0;    // For hungry feature
    private int slowCounter = 0;    // For mobs with broken animations
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(ShellfishEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(ShellfishEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> NEWBORN = SynchedEntityData.defineId(ShellfishEntity.class, EntityDataSerializers.BOOLEAN);
    public int partnerVariantStorage = -1;

    public ShellfishEntity(EntityType<? extends ShellfishEntity> entityType, Level world) {
        super(entityType, world);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0f);
        hostileSound = null;    // Gets the hostile sound, if any
        depth = 26;             // Indicates the spawn depth of the mob; 26 blocks is default
        variantChance = null;   // For mobs with a predetermined most common variant chance
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

    public static boolean canSpawn(EntityType<? extends ShellfishEntity> type, LevelReader world, MobSpawnType reason, BlockPos pos, RandomSource random) {
        int i = 63;
        int j = i - depth;
        return pos.getY() >= j && pos.getY() < i && (world.getFluidState(pos.above()).is(FluidTags.WATER) || world.getFluidState(pos.below()).is(FluidTags.WATER)) && world.getFluidState(pos).is(FluidTags.WATER) && ((world.getBiome(pos).is(ShellfishWorldgen.MARSH) || world.getBiome(pos).is(Biomes.SWAMP) || world.getBiome(pos).is(Biomes.MANGROVE_SWAMP)) ? isBrightEnoughToSpawn(world, pos) : true);
    }

    @Override
    public MobType getMobType() {
        return MobType.WATER;
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
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
        this.entityData.define(FROM_BUCKET, false);
        this.entityData.define(NEWBORN, true);
        if (this instanceof EggLaying egg) {
            this.entityData.define(egg.getEggTracker(), false);
        }
        if (this instanceof Hungry hungry) {
            this.entityData.define(hungry.getHungryTracker(), false);
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            Entity entity = source.getEntity();
            if (entity != null && !(entity instanceof Player) && !(entity instanceof Arrow)) {
                amount = (amount + 1.0f) / 2.0f;
            }
            return super.hurt(source, amount);
        }
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (hostileSound == null) {return false;}
        boolean bl = target.hurt(this.damageSources().mobAttack(this), (float)(this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if (bl) {
            this.doEnchantDamageEffects(this, target);
            this.playSound(hostileSound, 1.0f, 1.0f);
        }

        return bl;
    }

    public int getVariant() {
        return this.entityData.get(VARIANT);
    }

    public void setVariant(int variant) {
        this.entityData.set(VARIANT, variant);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("Variant", this.getVariant());
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
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        setVariant(Mth.clamp(nbt.getInt("Variant"), 0, getMaxVariants() - 1));
        this.setFromBucket(nbt.getBoolean("FromBucket"));
        this.setNewborn(nbt.getBoolean("newborn"));
        if (this instanceof EggLaying egg) {
            egg.setHasEgg(nbt.getBoolean("HasEgg"));
            partnerVariantStorage = nbt.getInt("partnerVariantStorage");
        }
        if (this instanceof Hungry hungry) {
            hungry.setHungry(nbt.getBoolean("IsHungry"));
        }
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, SpawnGroupData entityData, CompoundTag entityNbt) {
        if (entityNbt == null) {
            double chance = getRandom().nextDouble();
            if (variantChance == null ? chance <= (double)(getMaxVariants() - 1) / getMaxVariants() : chance <=  variantChance) setVariant(getRandom().nextInt(getMaxVariants()));
        } else {
            if (entityNbt.contains("Variant", 3)) {
                this.setVariant(entityNbt.getInt("Variant"));
            }
            if (entityNbt.contains("Health", 99)) {
                this.setHealth(entityNbt.getFloat("Health"));
            }
        }
        this.setNewborn(true);
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void saveToBucketTag(ItemStack bucket) {
        Bucketable.saveDefaultDataToBucketTag(this, bucket);
        CompoundTag nbtCompound = bucket.getOrCreateTag();
        nbtCompound.putInt("Variant", this.getVariant());
        nbtCompound.putFloat("Health", this.getHealth());
        nbtCompound.putInt("Age", this.getAge());
        nbtCompound.putBoolean("newborn", this.isNewborn());
    }

    @Override
    public boolean canBeLeashed(Player player) {
        return false;
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
        if (nbt.contains("Age")) {
            this.setAge(nbt.getInt("Age"));
        }
    }

    @Override
    public SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_FILL_FISH;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
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
        if (this.isAlive() && !this.isInWaterRainOrBubble()) {
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
