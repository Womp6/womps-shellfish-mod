package womp.shellfishmod.entity.parents;

import org.jetbrains.annotations.Nullable;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.Bucketable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.BiomeKeys;
import womp.shellfishmod.registry.ShellfishWorldgen;

public abstract class ShellfishEntity extends AnimalEntity implements Bucketable {
    
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
    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(ShellfishEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> FROM_BUCKET = DataTracker.registerData(ShellfishEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> NEWBORN = DataTracker.registerData(ShellfishEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public int partnerVariantStorage = -1;
    
    public ShellfishEntity(EntityType<? extends ShellfishEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.WATER, 0.0f);
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
            return this.prevX == this.getX() && this.prevZ == this.getZ() && this.isTouchingWater();
        }
        return this.prevX == this.getX() && this.prevZ == this.getZ();
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
        if (this.prevX != this.getX()) {
            return waterWalking ? this.isTouchingWater() : true;
        }
        if (this.prevZ != this.getZ()) {
            return waterWalking ? this.isTouchingWater() : true;
        }
        return false;
    }

    // Default animation conditions
    protected void setupAnimationStates() {
        
        if (brokenAnim ? isIdle() && slowCounter >= 3 : isIdle()) {
            this.idleAnimationState.startIfNotRunning(this.age);
            this.moveAnimationState.stop();
        } else {
            this.idleAnimationState.stop();
            slowCounter++;
        }

        if (this.isWalking()) {
            slowCounter = 0;
            this.moveAnimationState.startIfNotRunning(this.age);
        }

        if (isIdleEntity && !isTouchingWater()) {
            this.moveAnimationState.stop();
            slowCounter = 0;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.getWorld().isClient && this.isBaby()) this.setNewborn(this.getBreedingAge() <= -12000);

        // ANIMATION
        if (this.getWorld().isClient()) {
            this.setupAnimationStates();
        }

        // HUNGRY
        if (this instanceof Hungry hungry) {
            tickCounter = hungry.tick(tickCounter);
        }
    }

    @Override
    public boolean canSpawn(WorldView world) {
        return world.doesNotIntersectEntities(this);
    }

    public static boolean canSpawn(EntityType<? extends ShellfishEntity> type, WorldAccess world, SpawnReason reason, BlockPos pos, Random random) {
        int i = 63;
        int j = i - depth;
        return pos.getY() >= j && pos.getY() < i && (world.getFluidState(pos.up()).isIn(FluidTags.WATER) || world.getFluidState(pos.down()).isIn(FluidTags.WATER)) && world.getFluidState(pos).isIn(FluidTags.WATER) && ((world.getBiome(pos).matchesKey(ShellfishWorldgen.MARSH) || world.getBiome(pos).matchesKey(BiomeKeys.SWAMP) || world.getBiome(pos).matchesKey(BiomeKeys.MANGROVE_SWAMP)) ? isLightLevelValidForNaturalSpawn(world, pos) : true);
    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.AQUATIC;
    }

    @Override
    public boolean isFromBucket() {
        return this.dataTracker.get(FROM_BUCKET);
    }

    @Override
    public boolean isPushedByFluids() {
        return false;
    }

    // Default travel method for in water movement (to make it look natural)
    @Override
    public void travel(Vec3d movementInput) {
        if (this.isTouchingWater() && slow && this.canMoveVoluntarily()) {
            this.updateVelocity(tSpeed, movementInput);
            this.move(MovementType.SELF, this.getVelocity());
            if (this.isAttacking()) this.setVelocity(this.getVelocity().multiply(.8, .85, .8).add(0, -0.01, 0));
            else this.setVelocity(this.getVelocity().multiply(.8, .825, .8).add(0, -0.01d, 0));
        } else {
            super.travel(movementInput);
        }
    }

    @Override
    public void setFromBucket(boolean fromBucket) {
        this.dataTracker.set(FROM_BUCKET, fromBucket);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(VARIANT, 0);
        this.dataTracker.startTracking(FROM_BUCKET, false);
        this.dataTracker.startTracking(NEWBORN, true);
        if (this instanceof EggLaying egg) {
            this.dataTracker.startTracking(egg.getEggTracker(), false);
        }
        if (this instanceof Hungry hungry) {
            this.dataTracker.startTracking(hungry.getHungryTracker(), false);
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            Entity entity = source.getAttacker();
            if (entity != null && !(entity instanceof PlayerEntity) && !(entity instanceof ArrowEntity)) {
                amount = (amount + 1.0f) / 2.0f;
            }
            return super.damage(source, amount);
        }
    }

    @Override
    public boolean tryAttack(Entity target) {
        if (hostileSound == null) {return false;}
        boolean bl = target.damage(this.getDamageSources().mobAttack(this), (float)(this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE)));
        if (bl) {
           this.applyDamageEffects(this, target);
           this.playSound(hostileSound, 1.0f, 1.0f);
        }
  
        return bl;
     }

    public int getVariant() {
        return this.dataTracker.get(VARIANT);
    }

    public void setVariant(int variant) {
        this.dataTracker.set(VARIANT, variant);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Variant", this.getVariant());
        nbt.putBoolean("FromBucket", this.isFromBucket());
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
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        setVariant(MathHelper.clamp(nbt.getInt("Variant"), 0, getMaxVariants() - 1));
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
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, NbtCompound entityNbt) {
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
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void copyDataToStack(ItemStack bucket) {
        Bucketable.copyDataToStack(this, bucket);
        NbtCompound nbtCompound = bucket.getOrCreateNbt();
        nbtCompound.putInt("Variant", this.getVariant());
        nbtCompound.putFloat("Health", this.getHealth());
        nbtCompound.putInt("Age", this.getBreedingAge());
        nbtCompound.putBoolean("newborn", this.isNewborn());
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return false;
    }
    
    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        return Bucketable.tryBucket(player, hand, this).orElse(super.interactMob(player, hand));
    }

    @Override
    public boolean cannotDespawn() {
        return super.cannotDespawn() || this.isFromBucket();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void copyDataFromNbt(NbtCompound nbt) {
        Bucketable.copyDataFromNbt(this, nbt);
        if (nbt.contains("Age")) {
            this.setBreedingAge(nbt.getInt("Age"));
        }
    }

    @Override
    public SoundEvent getBucketFillSound() {
        return SoundEvents.ITEM_BUCKET_FILL_FISH;
    }

    // Default max variant count
    public int getMaxVariants() {
        return 5;
    }

    // Default max air (for clams, oysters, mussles)
    @Override
    public int getMaxAir() {
        return 64000;
    }

    protected void tickAir(int air) {
        if (airBreathing) return;
        if (this.isAlive() && !this.isWet()) {
            this.setAir(air - 1);
            if (this.getAir() == -20) {
                this.setAir(0);
                this.damage(getWorld().getDamageSources().dryOut(), 2.0f);
            }
        } else {
            this.setAir(this.getMaxAir());
        }
        
    }

    // Used for suffocation on land when needed
    @Override
    public void baseTick() {
        int i = this.getAir();
        super.baseTick();
        if (airBreathing) return;
        if (!this.isAiDisabled()) {
            this.tickAir(i);
        }
    }

    // Default chunk limit
    @Override
    public int getLimitPerChunk() {
        return 6;
    }

    @Override
    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        return 0.0f;
    }

    public void setNewborn(boolean value) {
        this.dataTracker.set(NEWBORN, value);
    }

    public boolean isNewborn() {
        return this.dataTracker.get(NEWBORN);
    }


    // Used in renderers
    public void scale(MatrixStack poseStack, float scale, float babyScale) {
        scale(poseStack, scale, babyScale, babyScale);
    }

    public void scale(MatrixStack poseStack, float scale, float babyScale, float smallBabyScale) {
        if (this.isBaby() && this.isNewborn()) poseStack.scale(smallBabyScale, smallBabyScale, smallBabyScale);
        else if (this.isBaby() && !this.isNewborn()) poseStack.scale(babyScale, babyScale, babyScale);
        else poseStack.scale(scale, scale, scale);
    }
}
