package womp.shellfishmod.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;
import womp.shellfishmod.entity.goals.SitAroundGoal;
import womp.shellfishmod.entity.goals.WanderInWaterGoal;
import womp.shellfishmod.registry.ShellfishItems;

public class MossBallEntity extends WaterAnimal implements Bucketable {

    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(MossBallEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SMALL = SynchedEntityData.defineId(MossBallEntity.class, EntityDataSerializers.BOOLEAN);

    public MossBallEntity(EntityType<? extends MossBallEntity> entityType, Level world) {
        super(entityType, world);
    }

    public static AttributeSupplier.Builder createMossBallAttributes() {
        return WaterAnimal.createMobAttributes().add(Attributes.MAX_HEALTH, 1.0d).add(Attributes.MOVEMENT_SPEED, 0.01);
    }

    public boolean isSmall() {
        return this.entityData.get(SMALL);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SitAroundGoal(this));
        this.goalSelector.addGoal(2, new WanderInWaterGoal(this, 1));
    }

    @SuppressWarnings("deprecation")
    public static boolean canSpawn(EntityType<MossBallEntity> type, ServerLevelAccessor world, MobSpawnType reason, BlockPos pos, RandomSource random) {
        int i = world.getSeaLevel();
        int j = i - 26;
        return pos.getY() >= j && pos.getY() <= i && world.getFluidState(pos.below()).is(FluidTags.WATER) && world.getBlockState(pos.above()).is(Blocks.WATER);
    }

    @Override
    public boolean fromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 8;
    }

    @Override
    public void setFromBucket(boolean fromBucket) {
        this.entityData.set(FROM_BUCKET, fromBucket);
    }

    public void setSmall(boolean small) {
        this.entityData.set(SMALL, small);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FROM_BUCKET, false);
        this.entityData.define(SMALL, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("FromBucket", this.fromBucket());
        nbt.putBoolean("IsSmall", this.isSmall());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.setFromBucket(nbt.getBoolean("FromBucket"));
        setSmall(nbt.getBoolean("IsSmall"));
    }


    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, SpawnGroupData entityData, CompoundTag entityNbt) {
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
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void saveToBucketTag(ItemStack bucket) {
        Bucketable.saveDefaultDataToBucketTag(this, bucket);
        CompoundTag nbtCompound = bucket.getOrCreateTag();
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
    public ItemStack getBucketItemStack() {
        return new ItemStack(ShellfishItems.MOSS_BALL_BUCKET.get());
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        return Bucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
    }

    @Override
    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.fromBucket();
    }

    @Override
    public int getMaxAirSupply() {
        return 32767;
    }

    protected void tickAir(int air) {
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

    @Override
    public void baseTick() {
        int i = this.getAirSupply();
        super.baseTick();
        if (!this.isNoAi()) {
            this.tickAir(i);
        }
    }

    @Override
    public SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_FILL_FISH;
    }

    @Override
    public void loadFromBucketTag(CompoundTag var1) {
        Bucketable.loadDefaultDataFromBucketTag(this, var1);
    }

    @Override
    public int getExperienceReward() {
        return 0;
    }
}
