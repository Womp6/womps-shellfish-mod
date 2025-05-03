package womp.shellfishmod.entity;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Bucketable;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import womp.shellfishmod.entity.goals.SitAroundGoal;
import womp.shellfishmod.entity.goals.WanderInWaterGoal;
import womp.shellfishmod.registry.ShellfishItems;

public class MossBallEntity extends WaterCreatureEntity implements Bucketable {
    
    private static final TrackedData<Boolean> FROM_BUCKET = DataTracker.registerData(MossBallEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> SMALL = DataTracker.registerData(MossBallEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public MossBallEntity(EntityType<? extends MossBallEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createMossBallAttributes() {
        return WaterCreatureEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 1.0d).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.01);
    }

    public boolean isSmall() {
        return this.dataTracker.get(SMALL);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SitAroundGoal(this));
        this.goalSelector.add(2, new WanderInWaterGoal(this, 1));
    }

    @SuppressWarnings("deprecation")
    public static boolean canSpawn(EntityType<MossBallEntity> type, ServerWorldAccess world, SpawnReason reason, BlockPos pos, Random random) {
        int i = world.getSeaLevel();
        int j = i - 26;
        return pos.getY() >= j && pos.getY() <= i && world.getFluidState(pos.down()).isIn(FluidTags.WATER) && world.getBlockState(pos.up()).isOf(Blocks.WATER);
    }

    @Override
    public boolean isFromBucket() {
        return this.dataTracker.get(FROM_BUCKET);
    }

    @Override
    public int getLimitPerChunk() {
        return 8;
    }

    @Override
    public void setFromBucket(boolean fromBucket) {
        this.dataTracker.set(FROM_BUCKET, fromBucket);
    }

    public void setSmall(boolean small) {
        this.dataTracker.set(SMALL, small);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(FROM_BUCKET, false);
        this.dataTracker.startTracking(SMALL, false);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("FromBucket", this.isFromBucket());
        nbt.putBoolean("IsSmall", this.isSmall());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setFromBucket(nbt.getBoolean("FromBucket"));
        setSmall(nbt.getBoolean("IsSmall"));
    }
    

    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, NbtCompound entityNbt) {
        if (entityNbt == null) {
            double chance = getRandom().nextDouble();
            if (chance <= 1) setSmall(getRandom().nextBoolean());
        } else {
            if (entityNbt.contains("IsSmall")) {
                this.setSmall(entityNbt.getBoolean("IsSmall"));
            }
            if (entityNbt.contains("Health", 99)) {
                this.setHealth(entityNbt.getFloat("Health"));
            }
        }
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void copyDataToStack(ItemStack bucket) {
        Bucketable.copyDataToStack(this, bucket);
        NbtCompound nbtCompound = bucket.getOrCreateNbt();
        nbtCompound.putFloat("Health", this.getHealth());
        nbtCompound.putBoolean("IsSmall", this.isSmall());
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return null;
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(DamageSource source) {
        return null;
    }

    @Override
    public ItemStack getBucketItem() {
        return new ItemStack(ShellfishItems.MOSS_BALL_BUCKET);
    }
    
    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        return Bucketable.tryBucket(player, hand, this).orElse(super.interactMob(player, hand));
    }

    @Override
    public boolean cannotDespawn() {
        return super.cannotDespawn() || this.isFromBucket();
    }

    @Override
    public int getMaxAir() {
        return 64000;
    }

    protected void tickAir(int air) {
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

    @Override
    public void baseTick() {
        int i = this.getAir();
        super.baseTick();
        if (!this.isAiDisabled()) {
            this.tickAir(i);
        }
    }

    @Override
    public SoundEvent getBucketFillSound() {
        return SoundEvents.ITEM_BUCKET_FILL_FISH;
    }

    @Override
    public void copyDataFromNbt(NbtCompound var1) {
    }

    @Override
    public int getXpToDrop() {
        return 0;
    }
}
