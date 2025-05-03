package womp.shellfishmod.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;
import womp.shellfishmod.entity.goals.ShellfishLayEggGoal;
import womp.shellfishmod.entity.goals.ShellfishMateGoal;
import womp.shellfishmod.entity.goals.WanderInWaterGoal;
import womp.shellfishmod.entity.goals.WanderToWaterGoal;
import womp.shellfishmod.entity.parents.EggLaying;
import womp.shellfishmod.entity.parents.ShellfishEntity;
import womp.shellfishmod.registry.ShellfishBlocks;
import womp.shellfishmod.registry.ShellfishItems;
import womp.shellfishmod.util.ShellfishTags;
import womp.shellfishmod.registry.ShellfishEntities;
import womp.shellfishmod.registry.ShellfishSounds;

public class SeaSnailEntity extends ShellfishEntity implements EggLaying {

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
        if((child = ShellfishEntities.SEA_SNAIL.get().create(world)) != null && entity instanceof SeaSnailEntity mate) {
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

    @SuppressWarnings("deprecation")
    public static boolean canSpawn(EntityType<SeaSnailEntity> type, ServerLevelAccessor world, MobSpawnType reason, BlockPos pos, RandomSource random) {
        int i = world.getSeaLevel();
        int j = i - 26;
        if(pos.getY() >= j && pos.getY() <= i && world.getFluidState(pos.below()).is(FluidTags.WATER) && world.getBlockState(pos.above()).is(Blocks.WATER)) {
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
    public ShellfishEntity getEntity() {
        return this;
    }

    public boolean canHide() {
        return canHide;
    }

    public void setCanHide(boolean canHide) {
        this.canHide = canHide;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("canHide", canHide);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        canHide = nbt.contains("canHide") ? nbt.getBoolean("canHide") : true;
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
}