package womp.shellfishmod.entity;

import java.util.function.IntFunction;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.Codec;

import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.Bucketable;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.VariantHolder;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.Util;
import net.minecraft.util.function.ValueLists;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeKeys;
import womp.shellfishmod.entity.goals.SitAroundGoal;
import womp.shellfishmod.entity.goals.WanderInWaterGoal;
import womp.shellfishmod.entity.MossBallEntity.Variant;
import womp.shellfishmod.registry.ShellfishItems;
import womp.shellfishmod.registry.ShellfishWorldgen;

public class MossBallEntity extends WaterCreatureEntity implements Bucketable, VariantHolder<Variant> {
    
    private static final TrackedData<Boolean> FROM_BUCKET = DataTracker.registerData(MossBallEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(MossBallEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public MossBallEntity(EntityType<? extends MossBallEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createMossBallAttributes() {
        return WaterCreatureEntity.createMobAttributes().add(EntityAttributes.MAX_HEALTH, 1.0d).add(EntityAttributes.MOVEMENT_SPEED, 0.01);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SitAroundGoal(this));
        this.goalSelector.add(2, new WanderInWaterGoal(this, 1));
    }

    public static boolean canSpawn(EntityType<MossBallEntity> type, ServerWorldAccess world, SpawnReason reason, BlockPos pos, Random random) {
        int i = world.getSeaLevel();
        int j = i - 26;
        return pos.getY() >= j && pos.getY() <= i && world.getFluidState(pos.down()).isIn(FluidTags.WATER) && world.getBlockState(pos.up()).isOf(Blocks.WATER) && ((world.getBiome(pos).matchesKey(ShellfishWorldgen.MARSH) || world.getBiome(pos).matchesKey(BiomeKeys.SWAMP) || world.getBiome(pos).matchesKey(BiomeKeys.MANGROVE_SWAMP)) ? world.getLightLevel(pos, 0) > 8 : true);
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

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(FROM_BUCKET, false);
        builder.add(VARIANT, 0);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("FromBucket", this.isFromBucket());
        nbt.putInt("Variant", this.getVariant().id);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setFromBucket(nbt.getBoolean("FromBucket"));
        this.setVariant(Variant.byId(nbt.getInt("Variant")));
    }
    
    @Override
    public Variant getVariant() {
        return Variant.byId(this.dataTracker.get(VARIANT));
    }

    @Override
    public void setVariant(Variant variant) {
        this.dataTracker.set(VARIANT, variant.id);
    }

    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        Variant variant;
        Random random = world.getRandom();
        if (entityData instanceof MossBallData) {
            variant = ((MossBallData)entityData).variant;
        } else {
            variant = Util.getRandom(Variant.values(), random);
            entityData = new MossBallData(variant);
        }
        this.setVariant(variant);
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void copyDataToStack(ItemStack bucket) {
        Bucketable.copyDataToStack(this, bucket);
        NbtComponent.set(DataComponentTypes.BUCKET_ENTITY_DATA, bucket, nbtCompound -> {
        nbtCompound.putFloat("Health", this.getHealth());
        nbtCompound.putInt("Variant", this.getVariant().id);
        });
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

    @SuppressWarnings("deprecation")
    protected void tickAir(int air) {
        if (this.isAlive() && !this.isWet()) {
            this.setAir(air - 1);
            if (this.getAir() == -20) {
                this.setAir(0);
                this.serverDamage(getDamageSources().dryOut(), 2.0f);
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

    @SuppressWarnings("deprecation")
    @Override
    public void copyDataFromNbt(NbtCompound nbt) {
        Bucketable.copyDataFromNbt(this, nbt);
        this.setVariant(Variant.byId(nbt.getInt("Variant")));
    }

    @Override
    public int getExperienceToDrop(ServerWorld world) {
        return 0;
    }

    public static enum Variant implements StringIdentifiable {
        LARGE(0, "large"),
        SMALL(1, "small");

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

    static class MossBallData extends PassiveEntity.PassiveData {
        public final Variant variant;

        MossBallData(Variant variant) {
            super(true);
            this.variant = variant;
        }
    }
}
