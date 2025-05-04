package womp.shellfishmod.entity.goals;

import java.util.EnumSet;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import womp.shellfishmod.entity.SeaSnailEntity;

public class WanderInWaterGoal extends Goal {
    
    public static final int DEFAULT_CHANCE = 120;
    protected final PathAwareEntity mob;
    protected double targetX;
    protected double targetY;
    protected double targetZ;
    protected final double speed;
    protected int chance;
    protected boolean ignoringChance;
    private final boolean canDespawn;
    private final World world;

    public WanderInWaterGoal(PathAwareEntity mob, double speed) {
        this(mob, speed, 120);
    }

    public WanderInWaterGoal(PathAwareEntity mob, double speed, int chance) {
        this(mob, speed, chance, true);
    }

    public WanderInWaterGoal(PathAwareEntity entity, double speed, int chance, boolean canDespawn) {
        this.mob = entity;
        this.speed = speed;
        this.chance = chance;
        this.canDespawn = canDespawn;
        this.world = mob.getWorld();
        this.setControls(EnumSet.of(Goal.Control.MOVE));
    }

    @Override
    public boolean canStart() {
        Vec3d vec3d;
        if (!this.ignoringChance) {
            if (this.canDespawn && this.mob.getDespawnCounter() >= 100) {
                return false;
            }
            if (this.mob.getRandom().nextInt(WanderAroundGoal.toGoalTicks(this.chance)) != 0) {
                return false;
            }
        }
        if ((vec3d = this.getWanderTarget()) == null) {
            return false;
        }
        this.targetX = vec3d.x;
        this.targetY = vec3d.y;
        this.targetZ = vec3d.z;
        this.ignoringChance = false;
        return this.mob.isTouchingWater() && this.mob.getFluidHeight(FluidTags.WATER) > this.mob.getSwimHeight() || this.mob.isInLava();
    }

    @Nullable
    protected Vec3d getWanderTarget() {
        net.minecraft.util.math.random.Random random = this.mob.getRandom();
            BlockPos blockPos = this.mob.getBlockPos();
            for (int i = 0; i < 10; ++i) {
                BlockPos blockPos2 = blockPos.add(random.nextInt(6) - 3, 2 - random.nextInt(4), random.nextInt(6) - 3);
                if (!this.world.getBlockState(blockPos2).isOf(Blocks.WATER)) continue;
                return Vec3d.ofBottomCenter(blockPos2);
            }
            return null;
    }

    @Override
    public boolean shouldContinue() {
        return !this.mob.getNavigation().isIdle();
    }

    @Override
    public void start() {
        if (this.mob instanceof SeaSnailEntity snail) {
            snail.setCanHide(false);
        }
        this.mob.getNavigation().startMovingTo(this.targetX, this.targetY, this.targetZ, this.speed);
    }

    @Override
    public void stop() {
        if (this.mob instanceof SeaSnailEntity snail) {
            snail.setCanHide(true);
        }
        this.mob.getNavigation().stop();
        super.stop();
    }

    public void ignoreChanceOnce() {
        this.ignoringChance = true;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }
}
