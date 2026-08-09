package womp.shellfishmod.entity.parents;

import org.jetbrains.annotations.Nullable;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.Bucketable;
import net.minecraft.entity.Entity;
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
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.BiomeKeys;
import womp.shellfishmod.entity.parents.ShellfishEntity.ShellfishVariant;
import womp.shellfishmod.registry.ShellfishWorldgen;
import womp.shellfishmod.util.ShellfishTags;

public abstract class ShellfishEntity<T extends Enum<T> & ShellfishVariant> extends AnimalEntity implements Bucketable {
    
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
    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(ShellfishEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> FROM_BUCKET = DataTracker.registerData(ShellfishEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> NEWBORN = DataTracker.registerData(ShellfishEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public int partnerVariantStorage = -1;
    
    public ShellfishEntity(EntityType<? extends ShellfishEntity<?>> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.WATER, 0.0f);
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
            return this.lastX == this.getX() && this.lastZ == this.getZ() && this.isTouchingWater();
        }
        return this.lastX == this.getX() && this.lastZ == this.getZ();
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
        if (this.lastX != this.getX()) {
            return waterWalking ? this.isTouchingWater() : true;
        }
        if (this.lastZ != this.getZ()) {
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
        if (!this.getEntityWorld().isClient() && this.isBaby()) this.setNewborn(this.getBreedingAge() <= -12000);

        // ANIMATION
        if (this.getEntityWorld().isClient()) {
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

    public static boolean canSpawn(EntityType<? extends ShellfishEntity<?>> type, WorldAccess world, SpawnReason reason, BlockPos pos, Random random) {
        int i = world.getSeaLevel();
        int j = i - depth;
        return pos.getY() >= j && pos.getY() < i && (world.getFluidState(pos.up()).isIn(FluidTags.WATER) || world.getFluidState(pos.down()).isIn(FluidTags.WATER)) && world.getFluidState(pos).isIn(FluidTags.WATER) && ((world.getBiome(pos).matchesKey(ShellfishWorldgen.MARSH) || world.getBiome(pos).matchesKey(BiomeKeys.SWAMP) || world.getBiome(pos).matchesKey(BiomeKeys.MANGROVE_SWAMP)) ? isLightLevelValidForNaturalSpawn(world, pos) : true);
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
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(VARIANT, 0);
        builder.add(FROM_BUCKET, false);
        builder.add(NEWBORN, true);
        builder.add(EggLaying.HAS_EGG, false);
        builder.add(Hungry.IS_HUNGRY, false);
    }

    @Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        if (this.isInvulnerableTo(world, source)) {
            return false;
        } else {
            Entity entity = source.getAttacker();
            if (entity != null && !(entity instanceof PlayerEntity) && !(entity instanceof ArrowEntity)) {
                amount = (amount + 1.0f) / 2.0f;
            }
            return super.damage(world, source, amount);
        }
    }

    @Override
    public boolean tryAttack(ServerWorld world, Entity target) {
        if (hostileSound == null) {return false;}
        boolean bl = target.damage(world, this.getDamageSources().mobAttack(this), (float)(this.getAttributeValue(EntityAttributes.ATTACK_DAMAGE)));
        if (bl) {
           this.onAttacking(target);
           this.playSound(hostileSound, 1.0f, 1.0f);
        }
  
        return bl;
    }

    public T getVariant() {
        return byId(this.dataTracker.get(VARIANT));
    }

    public void setVariant(T variant) {
        this.dataTracker.set(VARIANT, variant.getIndex());
    }

    @Override
    public void writeCustomData(WriteView nbt) {
        super.writeCustomData(nbt);
        nbt.putInt("Variant", this.getVariant().getIndex());
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
    public void readCustomData(ReadView nbt) {
        super.readCustomData(nbt);
        this.setVariant(byId(nbt.getInt("Variant", 0)));
        this.setFromBucket(nbt.getBoolean("FromBucket", false));
        this.setNewborn(nbt.getBoolean("newborn", true));
        if (this instanceof EggLaying egg) {
            egg.setHasEgg(nbt.getBoolean("HasEgg", false));
            partnerVariantStorage = nbt.getInt("partnerVariantStorage", -1);
        }
        if (this instanceof Hungry hungry) {
            hungry.setHungry(nbt.getBoolean("IsHungry", false));
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void copyDataToStack(ItemStack bucket) {
        Bucketable.copyDataToStack(this, bucket);
        NbtComponent.set(DataComponentTypes.BUCKET_ENTITY_DATA, bucket, nbtCompound -> {
            nbtCompound.putInt("Variant", this.getVariant().getIndex());
            nbtCompound.putFloat("Health", this.getHealth());
            nbtCompound.putInt("Age", this.getBreedingAge());
            nbtCompound.putBoolean("newborn", this.isNewborn());
        });
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
        this.setVariant(byId(nbt.getInt("Variant", 0)));
        if (nbt.contains("Age")) {
            this.setBreedingAge(nbt.getInt("Age", 2000));
        }
    }

    @Override
    public SoundEvent getBucketFillSound() {
        return SoundEvents.ITEM_BUCKET_FILL_FISH;
    }

    // Default max air (for clams, oysters, mussles)
    @Override
    public int getMaxAir() {
        return 64000;
    }

    @SuppressWarnings("deprecation")
    protected void tickAir(int air) {
        if (airBreathing) return;
        if (this.isAlive() && !this.isTouchingWaterOrRain()) {
            this.setAir(air - 1);
            if (this.getAir() == -20) {
                this.setAir(0);
                this.serverDamage(getDamageSources().dryOut(), 2.0f);
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

    // Default
    @Override
    public boolean isBreedingItem(ItemStack item) {
        return item.isIn(ShellfishTags.Items.EMPTY_TAG);
    }

    // Default
    public int getMaxVariants() {
        return 5;
    }

    public void setVariantNumerical(int variant) {
        setVariant(byId(variant));
    }

    public interface ShellfishVariant extends StringIdentifiable {
        public int getIndex();
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
