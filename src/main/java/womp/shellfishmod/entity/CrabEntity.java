package womp.shellfishmod.entity;

import net.minecraft.core.BlockPos;
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
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.*;
import net.minecraft.world.level.biome.Biomes;
import org.jetbrains.annotations.Nullable;
import womp.shellfishmod.entity.goals.*;
import womp.shellfishmod.entity.parents.EggLaying;
import womp.shellfishmod.entity.parents.Hungry;
import womp.shellfishmod.entity.parents.ShellfishEntity;
import womp.shellfishmod.registry.*;
import womp.shellfishmod.util.ShellfishTags;

public class CrabEntity extends ShellfishEntity implements Hungry, EggLaying {

    public CrabEntity(EntityType<? extends CrabEntity> entityType, Level world) {
        super(entityType, world);
        hostileSound = ShellfishSounds.CRAB_ATTACK.get();
    }

    public static AttributeSupplier.Builder createCrabAttributes() {
        return Monster.createMobAttributes().add(Attributes.MAX_HEALTH, 5.0d).add(Attributes.ATTACK_DAMAGE, 1.0d).add(Attributes.MOVEMENT_SPEED, 0.15);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new ShellfishLayEggGoal(this, 1, ShellfishSounds.CRAB_LAYS_EGGS.get(), ShellfishBlocks.CRAB_EGGS_BLOCK.get()));
        this.goalSelector.addGoal(1, new ShellfishMateGoal(this, 1));
        this.goalSelector.addGoal(1, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1d, true));
        this.targetSelector.addGoal(2, new HungryRevengeGoal(this, new Class[0]));
        this.targetSelector.addGoal(2, new HungryActiveTargetGoal<>(this, AbstractFish.class, false));
        this.targetSelector.addGoal(2, new HungryActiveTargetGoal<>(this, Squid.class, false));
        this.targetSelector.addGoal(2, new HungryActiveTargetGoal<>(this, ShrimpEntity.class, false));
        this.targetSelector.addGoal(2, new HungryActiveTargetGoal<>(this, SeaSnailEntity.class, false));
        this.targetSelector.addGoal(2, new HungryActiveTargetGoal<>(this, SeaUrchinEntity.class, false));
        this.targetSelector.addGoal(2, new HungryActiveTargetGoal<>(this, ClamEntity.class, false));
        this.targetSelector.addGoal(2, new HungryActiveTargetGoal<>(this, OysterEntity.class, false));
        this.targetSelector.addGoal(2, new HungryActiveTargetGoal<>(this, MusselEntity.class, false));
        this.goalSelector.addGoal(3, new WanderInWaterGoal(this, 1));
        this.goalSelector.addGoal(5, new WanderToWaterGoal(this, 1));
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    @Override
    public boolean isFood(ItemStack item) {
        return item.is(ShellfishTags.Items.CRAB_FOOD);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob entity) {
        CrabEntity child;
        if((child = ShellfishEntities.CRAB.get().create(world)) != null && entity instanceof CrabEntity mate) {
            child.setVariant((random.nextBoolean() ? this : mate).getVariant());
            child.setPersistenceRequired();
            return child;
        }
        return null;
    }

    @SuppressWarnings("deprecation")
    public static boolean canSpawn(EntityType<CrabEntity> type, ServerLevelAccessor world, MobSpawnType reason, BlockPos pos, RandomSource random) {
        int i = world.getSeaLevel();
        int j = i - 26;
        if (pos.getY() >= j && pos.getY() <= i && world.getFluidState(pos.below()).is(FluidTags.WATER) && world.getFluidState(pos).is(FluidTags.WATER) && ((world.getBiome(pos).is(ShellfishWorldgen.MARSH) || world.getBiome(pos).is(Biomes.SWAMP) || world.getBiome(pos).is(Biomes.MANGROVE_SWAMP)) ? isBrightEnoughToSpawn(world, pos) : true)) {
            return true;
        } else if (pos.getY() >= i-6 && CrabEntity.isBrightEnoughToSpawn(world, pos)) {
            return world.getBlockState(pos.below()).is(ShellfishTags.Blocks.SHELLFISH_SPAWNABLE_ON);
        }
        return false;
    }

    @Override
    protected void usePlayerItem(Player player, InteractionHand hand, ItemStack stack) {
        if (stack.is(Items.TROPICAL_FISH_BUCKET)) {
            player.setItemInHand(hand, new ItemStack(Items.WATER_BUCKET));
        } else if (stack.is(Items.COD_BUCKET)) {
            player.setItemInHand(hand, new ItemStack(Items.WATER_BUCKET));
        } else if (stack.is(Items.SALMON_BUCKET)) {
            player.setItemInHand(hand, new ItemStack(Items.WATER_BUCKET));
        } else if (stack.is(ShellfishItems.SHRIMP_BUCKET.get())) {
            player.setItemInHand(hand, new ItemStack(Items.WATER_BUCKET));
        } else if (stack.is(ShellfishItems.SEA_SNAIL_BUCKET.get())) {
            player.setItemInHand(hand, new ItemStack(Items.WATER_BUCKET));
        } else if (stack.is(ShellfishItems.SEA_URCHIN_BUCKET.get())) {
            player.setItemInHand(hand, new ItemStack(Items.WATER_BUCKET));
        } else {
            super.usePlayerItem(player, hand, stack);
        }
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 4;
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(ShellfishItems.CRAB_BUCKET.get());
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return ShellfishSounds.CRAB_DEATH.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(DamageSource source) {
        return ShellfishSounds.CRAB_HURT.get();
    }

    @Override
    protected float getSoundVolume() {
        return 0.4f;
    }

    @Override
    public ShellfishEntity getEntity() {
        return this;
    }
}
