package womp.shellfishmod.entity;

import java.util.function.IntFunction;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.Util;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.animal.fish.AbstractFish;
import net.minecraft.world.entity.animal.squid.Squid;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import womp.shellfishmod.entity.goals.HungryActiveTargetGoal;
import womp.shellfishmod.entity.goals.HungryRevengeGoal;
import womp.shellfishmod.entity.goals.ShellfishLayEggGoal;
import womp.shellfishmod.entity.goals.ShellfishMateGoal;
import womp.shellfishmod.entity.goals.WanderInWaterGoal;
import womp.shellfishmod.entity.goals.WanderToWaterGoal;
import womp.shellfishmod.entity.parents.EggLaying;
import womp.shellfishmod.entity.parents.Hungry;
import womp.shellfishmod.entity.parents.ShellfishEntity;
import womp.shellfishmod.entity.parents.ShellfishEntity.ShellfishVariant;
import womp.shellfishmod.entity.CrabEntity.Variant;
import womp.shellfishmod.registry.ShellfishBlocks;
import womp.shellfishmod.registry.ShellfishEntities;
import womp.shellfishmod.registry.ShellfishItems;
import womp.shellfishmod.registry.ShellfishSounds;
import womp.shellfishmod.registry.ShellfishWorldgen;
import womp.shellfishmod.util.ShellfishTags;

public class CrabEntity extends ShellfishEntity<Variant> implements Hungry, EggLaying {

    public CrabEntity(EntityType<? extends CrabEntity> entityType, Level world) {
        super(entityType, world);
        hostileSound = ShellfishSounds.CRAB_ATTACK;
    }

    public static AttributeSupplier.Builder createCrabAttributes() {
        return Monster.createMobAttributes().add(Attributes.MAX_HEALTH, 5.0d).add(Attributes.ATTACK_DAMAGE, 1.0d).add(Attributes.MOVEMENT_SPEED, 0.15);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new ShellfishLayEggGoal(this, 1, ShellfishSounds.CRAB_LAYS_EGGS, ShellfishBlocks.CRAB_EGGS_BLOCK));
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
        if((child = ShellfishEntities.CRAB.create(world, EntitySpawnReason.BREEDING)) != null && entity instanceof CrabEntity mate) {
            child.setVariant((random.nextBoolean() ? this : mate).getVariant());
            child.setPersistenceRequired();
            return child;
        }
        return null;
    }

    public static boolean canSpawn(EntityType<CrabEntity> type, ServerLevelAccessor world, EntitySpawnReason reason, BlockPos pos, RandomSource random) {
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
        } else if (stack.is(ShellfishItems.SHRIMP_BUCKET)) {
            player.setItemInHand(hand, new ItemStack(Items.WATER_BUCKET));
        } else if (stack.is(ShellfishItems.SEA_SNAIL_BUCKET)) {
            player.setItemInHand(hand, new ItemStack(Items.WATER_BUCKET));
        } else if (stack.is(ShellfishItems.SEA_URCHIN_BUCKET)) {
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
        return new ItemStack(ShellfishItems.CRAB_BUCKET);
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return ShellfishSounds.CRAB_DEATH;
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(DamageSource source) {
        return ShellfishSounds.CRAB_HURT;
    }

    @Override
    protected float getSoundVolume() {
        return 0.4f;
    }

    @Override
    public ShellfishEntity<Variant> getEntity() {
        return this;
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @Nullable SpawnGroupData entityData) {
        Variant variant;
        RandomSource random = world.getRandom();
        if (entityData instanceof CrabData) {
            variant = ((CrabData)entityData).variant;
        } else {
            variant = Util.getRandom(Variant.values(), random);
            entityData = new CrabData(variant);
        }
        this.setVariant(variant);
        this.setNewborn(true);
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
    }

    public static enum Variant implements ShellfishVariant {
        CRAB0(0, "crab0"),
        CRAB1(1, "crab1"),
        CRAB2(2, "crab2"),
        CRAB3(3, "crab3"),
        CRAB4(4, "crab4");

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
        public String getSerializedName() {
            return this.name;
        }

        static {
            CODEC = StringRepresentable.fromEnum(Variant::values);
            BY_ID = ByIdMap.continuous(Variant::getIndex, Variant.values(), ByIdMap.OutOfBoundsStrategy.CLAMP);
        }
    }

    static class CrabData extends AgeableMob.AgeableMobGroupData {
        public final Variant variant;

        CrabData(Variant variant) {
            super(true);
            this.variant = variant;
        }
    }

    @Override
    protected Variant byId(int id) {
        return Variant.byId(id);
    }
}
