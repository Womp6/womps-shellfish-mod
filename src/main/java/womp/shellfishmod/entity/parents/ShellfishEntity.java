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
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Bucketable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import womp.shellfishmod.entity.parents.ShellfishEntity.ShellfishVariant;
import womp.shellfishmod.registry.ShellfishWorldgen;
import womp.shellfishmod.util.ShellfishTags;

public abstract class ShellfishEntity<T extends Enum<T> & ShellfishVariant> extends Animal implements Bucketable {
    
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
            return this.xo == this.getX() && this.zo == this.getZ() && this.isInWater();
        }
        return this.xo == this.getX() && this.zo == this.getZ();
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
        if (this.xo != this.getX()) {
            return waterWalking ? this.isInWater() : true;
        }
        if (this.zo != this.getZ()) {
            return waterWalking ? this.isInWater() : true;
        }
        return false;
    }

    // Default animation conditions
    protected void setupAnimationStates() {
        
        if (brokenAnim ? isIdle() && slowCounter >= 3 : isIdle()) {
            this.idleAnimationState.startIfStopped(this.tickCount);
            this.moveAnimationState.stop();
        } else {
            this.idleAnimationState.stop();
            slowCounter++;
        }

        if (this.isWalking()) {
            slowCounter = 0;
            this.moveAnimationState.startIfStopped(this.tickCount);
        }

        if (isIdleEntity && !isInWater()) {
            this.moveAnimationState.stop();
            slowCounter = 0;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide() && this.isBaby()) this.setNewborn(this.getAge() <= -12000);

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

    public static boolean canSpawn(EntityType<? extends ShellfishEntity<?>> type, LevelAccessor world, EntitySpawnReason reason, BlockPos pos, RandomSource random) {
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
    @Override
    public void travel(Vec3 movementInput) {
        if (this.isInWater() && slow && this.canSimulateMovement()) {
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
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, 0);
        builder.define(FROM_BUCKET, false);
        builder.define(NEWBORN, true);
        builder.define(EggLaying.HAS_EGG, false);
        builder.define(Hungry.IS_HUNGRY, false);
    }

    @Override
    public boolean hurtServer(ServerLevel world, DamageSource source, float amount) {
        if (this.isInvulnerableTo(world, source)) {
            return false;
        } else {
            Entity entity = source.getEntity();
            if (entity != null && !(entity instanceof Player) && !(entity instanceof Arrow)) {
                amount = (amount + 1.0f) / 2.0f;
            }
            return super.hurtServer(world, source, amount);
        }
    }

    @Override
    public boolean doHurtTarget(ServerLevel world, Entity target) {
        if (hostileSound == null) {return false;}
        boolean bl = target.hurtServer(world, this.damageSources().mobAttack(this), (float)(this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
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
        this.setVariant(byId(nbt.getIntOr("Variant", 0)));
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
        this.setVariant(byId(nbt.getIntOr("Variant", random.nextInt(0, getMaxVariants()))));
        if (nbt.contains("Age")) {
            this.setAge(nbt.getIntOr("Age", 2000));
        }
    }

    @Override
    public SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_FILL_FISH;
    }

    // Default max air (for clams, oysters, mussles)
    @Override
    public int getMaxAirSupply() {
        return 64000;
    }

    @SuppressWarnings("deprecation")
    protected void tickAir(int air) {
        if (airBreathing) return;
        if (this.isAlive() && !this.isInWaterOrRain()) {
            this.setAirSupply(air - 1);
            if (this.getAirSupply() == -20) {
                this.setAirSupply(0);
                this.hurt(damageSources().dryOut(), 2.0f);
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
    public float getWalkTargetValue(BlockPos pos, LevelReader world) {
        return 0.0f;
    }

    // Default
    @Override
    public boolean isFood(ItemStack item) {
        return item.is(ShellfishTags.Items.EMPTY_TAG);
    }

    // Default
    public int getMaxVariants() {
        return 5;
    }

    public void setVariantNumerical(int variant) {
        setVariant(byId(variant));
    }

    public interface ShellfishVariant extends StringRepresentable {
        public int getIndex();
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
