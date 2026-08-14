package womp.shellfishmod.entity;

import com.mojang.serialization.Codec;
import net.minecraft.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;
import womp.shellfishmod.entity.goals.ShellfishLayEggGoal;
import womp.shellfishmod.entity.goals.ShellfishMateGoal;
import womp.shellfishmod.entity.goals.WanderInWaterGoal;
import womp.shellfishmod.entity.goals.WanderToWaterGoal;
import womp.shellfishmod.entity.parents.EggLaying;
import womp.shellfishmod.entity.parents.ShellfishEntity;
import womp.shellfishmod.registry.*;
import womp.shellfishmod.util.ShellfishTags;

import java.util.function.IntFunction;

public class SeaSnailEntity extends ShellfishEntity<SeaSnailEntity.Variant> implements EggLaying {

    public static final EntityDataAccessor<Boolean> HAS_EGG = SynchedEntityData.defineId(SeaSnailEntity.class, EntityDataSerializers.BOOLEAN);

    private boolean canHide = true;
    private int counter = 5;

    public SeaSnailEntity(EntityType<? extends SeaSnailEntity> entityType, Level world) {
        super(entityType, world);
        waterWalking = false;
        tSpeed = 0.05f;
    }

    public static AttributeSupplier.Builder createSeaSnailAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 5.0d).add(Attributes.MOVEMENT_SPEED, 0.1);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new ShellfishLayEggGoal(this, 1, ShellfishSounds.SEA_SNAIL_LAYS_EGGS.get(), ShellfishBlocks.SEA_SNAIL_EGGS_BLOCK.get()));
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.2));
        this.goalSelector.addGoal(1, new ShellfishMateGoal(this, 1));
        this.goalSelector.addGoal(1, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(3, new WanderInWaterGoal(this, 1));
        this.goalSelector.addGoal(5, new WanderToWaterGoal(this, 1));
        this.goalSelector.addGoal(7, new NoHideWanderOnLandGoal(this, 0.9));
    }

    @Override
    public boolean isFood(ItemStack item) {
        return item.is(ShellfishTags.Items.SEA_SNAIL_FOOD);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob entity) {
        SeaSnailEntity child;
        if((child = ShellfishEntities.SEA_SNAIL.get().create(world, EntitySpawnReason.BREEDING)) != null && entity instanceof SeaSnailEntity mate) {
            child.setVariant((random.nextBoolean() ? this : mate).getVariant());
            child.setPersistenceRequired();
            return child;
        }
        return null;
    }

    public boolean isSnailIdle() {
        return this.xOld == this.getX() && this.yOld == this.getY() && this.zOld == this.getZ();
    }

    @Override
    protected void usePlayerItem(Player player, InteractionHand hand, ItemStack stack) {
        if (stack.is(ShellfishItems.MOSS_BALL_BUCKET.get())) {
            player.setItemInHand(hand, new ItemStack(Items.WATER_BUCKET));
        } else {
            super.usePlayerItem(player, hand, stack);
        }
    }

    public static boolean canSpawn(EntityType<SeaSnailEntity> type, ServerLevelAccessor world, EntitySpawnReason reason, BlockPos pos, RandomSource random) {
        int i = world.getSeaLevel();
        int j = i - 26;
        if(pos.getY() >= j && pos.getY() <= i && world.getFluidState(pos.below()).is(FluidTags.WATER) && world.getBlockState(pos.above()).is(Blocks.WATER) && ((world.getBiome(pos).is(ShellfishWorldgen.MARSH) || world.getBiome(pos).is(Biomes.SWAMP) || world.getBiome(pos).is(Biomes.MANGROVE_SWAMP)) ? isBrightEnoughToSpawn(world, pos) : true)) {
            return true;
        } else if(pos.getY() >= i-6 && SeaSnailEntity.isBrightEnoughToSpawn(world, pos)) {
            return world.getBlockState(pos.below()).is(ShellfishTags.Blocks.SHELLFISH_SPAWNABLE_ON);
        }
        return false;
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 4;
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(ShellfishItems.SEA_SNAIL_BUCKET.get());
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return ShellfishSounds.SEA_SNAIL_DEATH.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(DamageSource source) {
        return ShellfishSounds.SEA_SNAIL_HURT.get();
    }

    @Override
    public ShellfishEntity<Variant> getEntity() {
        return this;
    }

    @Override
    public EntityDataAccessor<Boolean> getHasEgg() {
        return HAS_EGG;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);
        pBuilder.define(HAS_EGG, false);
    }

    public boolean canHide() {
        return canHide;
    }

    public void setCanHide(boolean canHide) {
        this.canHide = canHide;
    }

    @Override
    public void addAdditionalSaveData(ValueOutput nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("canHide", canHide);
    }

    @Override
    public void readAdditionalSaveData(ValueInput nbt) {
        super.readAdditionalSaveData(nbt);
        canHide = nbt.getBooleanOr("canHide", true);
    }

    @Override
    protected Variant byId(int id) {
        return Variant.byId(id);
    }

    @Override
    public void setupAnimationStates() {
        if (canHide && isSnailIdle() && counter >= 3) {
            this.idleAnimationState.startIfStopped(this.age);
        } else this.idleAnimationState.stop();

        if (this.isWalking()) {
            this.moveAnimationState.startIfStopped(this.age);
            counter = 0;
        } else if (counter >= 2) {
            this.moveAnimationState.stop();
            counter++;
        } else counter++;
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, EntitySpawnReason spawnReason, SpawnGroupData entityData) {
        Variant variant;
        RandomSource random = world.getRandom();
        if (entityData instanceof SeaSnailData) {
            variant = ((SeaSnailData)entityData).variant;
        } else {
            variant = Util.getRandom(Variant.values(), random);
            entityData = new SeaSnailData(variant);
        }
        this.setVariant(variant);
        this.setNewborn(true);
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
    }

    static class NoHideWanderOnLandGoal extends RandomStrollGoal {

        public NoHideWanderOnLandGoal(SeaSnailEntity mob, double speed) {
            super(mob, speed);
        }

        @Override
        public void start() {
            if (this.mob instanceof SeaSnailEntity snail) {
                snail.setCanHide(false);
            }
            super.start();
        }

        @Override
        public void stop() {
            if (this.mob instanceof SeaSnailEntity snail) {
                snail.setCanHide(false);
            }
            super.stop();
        }
    }

    public enum Variant implements ShellfishVariant
    {
        SNAIL0(0, "snail0"),
        SNAIL1(1, "snail1"),
        SNAIL2(2, "snail2"),
        SNAIL3(3, "snail3"),
        SNAIL4(4, "snail4");

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

    static class SeaSnailData extends AgeableMob.AgeableMobGroupData {
        public final Variant variant;

        SeaSnailData(Variant variant) {
            super(true);
            this.variant = variant;
        }
    }
}