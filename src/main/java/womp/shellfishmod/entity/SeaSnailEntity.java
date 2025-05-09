package womp.shellfishmod.entity;


import java.util.function.IntFunction;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.Codec;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.Util;
import net.minecraft.util.function.ValueLists;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import womp.shellfishmod.entity.goals.ShellfishLayEggGoal;
import womp.shellfishmod.entity.goals.ShellfishMateGoal;
import womp.shellfishmod.entity.goals.WanderInWaterGoal;
import womp.shellfishmod.entity.goals.WanderToWaterGoal;
import womp.shellfishmod.entity.parents.EggLaying;
import womp.shellfishmod.entity.parents.ShellfishEntity;
import womp.shellfishmod.entity.parents.ShellfishEntity.ShellfishVariant;
import womp.shellfishmod.entity.SeaSnailEntity.Variant;
import womp.shellfishmod.registry.ShellfishBlocks;
import womp.shellfishmod.registry.ShellfishEntities;
import womp.shellfishmod.registry.ShellfishItems;
import womp.shellfishmod.registry.ShellfishSounds;
import womp.shellfishmod.util.ShellfishTags;

public class SeaSnailEntity extends ShellfishEntity<Variant> implements EggLaying {

    private boolean canHide = true;
    private int counter = 5;

    public SeaSnailEntity(EntityType<? extends SeaSnailEntity> entityType, World world) {
        super(entityType, world);
        waterWalking = false;
        tSpeed = 0.05f;
    }

    public static DefaultAttributeContainer.Builder createSeaSnailAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.MAX_HEALTH, 5.0d).add(EntityAttributes.MOVEMENT_SPEED, 0.1);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new ShellfishLayEggGoal(this, 1, ShellfishSounds.SEA_SNAIL_LAYS_EGGS, ShellfishBlocks.SEA_SNAIL_EGGS_BLOCK));
        this.goalSelector.add(0, new EscapeDangerGoal(this, 1.2));
        this.goalSelector.add(1, new ShellfishMateGoal(this, 1));
        this.goalSelector.add(1, new FollowParentGoal(this, 1.1));
        this.goalSelector.add(3, new WanderInWaterGoal(this, 1));
        this.goalSelector.add(5, new WanderToWaterGoal(this, 1));
        this.goalSelector.add(7, new NoHideWanderOnLandGoal(this, 0.9));
    }

    @Override
    public boolean isBreedingItem(ItemStack item) {
        return item.isIn(ShellfishTags.Items.SEA_SNAIL_FOOD);
    }
    
    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        SeaSnailEntity child;
        if((child = ShellfishEntities.SEA_SNAIL.create(world, SpawnReason.BREEDING)) != null && entity instanceof SeaSnailEntity mate) {
            child.setVariant((random.nextBoolean() ? this : mate).getVariant());
            child.setPersistent();
            return child;
        }
        return null;
    }

    public boolean isSnailIdle() {
        return this.prevX == this.getX() && this.prevY == this.getY() && this.prevZ == this.getZ();
    }

    @Override
    protected void eat(PlayerEntity player, Hand hand, ItemStack stack) {
        if (stack.isOf(ShellfishItems.MOSS_BALL_BUCKET)) {
            player.setStackInHand(hand, new ItemStack(Items.WATER_BUCKET));
        } else {
            super.eat(player, hand, stack);
        }
    }

    public static boolean canSpawn(EntityType<SeaSnailEntity> type, ServerWorldAccess world, SpawnReason reason, BlockPos pos, Random random) {
        int i = world.getSeaLevel();
        int j = i - 26;
        if(pos.getY() >= j && pos.getY() <= i && world.getFluidState(pos.down()).isIn(FluidTags.WATER) && world.getBlockState(pos.up()).isOf(Blocks.WATER)) {
            return true;
        } else if(pos.getY() >= i-6 && SeaSnailEntity.isLightLevelValidForNaturalSpawn(world, pos)) {
            return world.getBlockState(pos.down()).isIn(ShellfishTags.Blocks.SHELLFISH_SPAWNABLE_ON);
        }
        return false;
    }

    @Override
    public int getLimitPerChunk() {
        return 4;
    }

    @Override
    public ItemStack getBucketItem() {
        return new ItemStack(ShellfishItems.SEA_SNAIL_BUCKET);
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return ShellfishSounds.SEA_SNAIL_DEATH;
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(DamageSource source) {
        return ShellfishSounds.SEA_SNAIL_HURT;
    }

    @Override
    public ShellfishEntity<Variant> getEntity() {
        return this;
    }

    public boolean canHide() {
        return canHide;
    }

    public void setCanHide(boolean canHide) {
        this.canHide = canHide;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("canHide", canHide);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        canHide = nbt.contains("canHide") ? nbt.getBoolean("canHide") : true;
    }

    @Override
    public void setupAnimationStates() {
        if (canHide && isSnailIdle() && counter >= 3) {
            this.idleAnimationState.startIfNotRunning(this.age);
        } else this.idleAnimationState.stop();

        if (this.isWalking()) {
            this.moveAnimationState.startIfNotRunning(this.age);
            counter = 0;
        } else if (counter >= 2) {
            this.moveAnimationState.stop();
            counter++;
        } else counter++;
    }

    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        Variant variant;
        Random random = world.getRandom();
        if (entityData instanceof SeaSnailData) {
            variant = ((SeaSnailData)entityData).variant;
        } else {
            variant = Util.getRandom(Variant.values(), random);
            entityData = new SeaSnailData(variant);
        }
        this.setVariant(variant);
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    static class NoHideWanderOnLandGoal extends WanderAroundGoal {
        
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

    public static enum Variant implements ShellfishVariant {
        SNAIL0(0, "snail0"),
        SNAIL1(1, "snail1"),
        SNAIL2(2, "snail2"),
        SNAIL3(3, "snail3"),
        SNAIL4(4, "snail4");

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

    static class SeaSnailData extends PassiveEntity.PassiveData {
        public final Variant variant;

        SeaSnailData(Variant variant) {
            super(true);
            this.variant = variant;
        }
    }

    @Override
    protected Variant byId(int id) {
        return Variant.byId(id);
    }
}
