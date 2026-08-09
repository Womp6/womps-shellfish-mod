package womp.shellfishmod.entity.goals;

import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import womp.shellfishmod.entity.SeaSnailEntity;

public class WanderInWaterGoal extends Goal {
    
    public static final int DEFAULT_CHANCE = 120;
    protected final PathfinderMob mob;
    protected double targetX;
    protected double targetY;
    protected double targetZ;
    protected final double speed;
    protected int chance;
    protected boolean ignoringChance;
    private final boolean canDespawn;
    private final Level world;

    public WanderInWaterGoal(PathfinderMob mob, double speed) {
        this(mob, speed, 120);
    }

    public WanderInWaterGoal(PathfinderMob mob, double speed, int chance) {
        this(mob, speed, chance, true);
    }

    public WanderInWaterGoal(PathfinderMob entity, double speed, int chance, boolean canDespawn) {
        this.mob = entity;
        this.speed = speed;
        this.chance = chance;
        this.canDespawn = canDespawn;
        this.world = mob.level();
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        Vec3 vec3d;
        if (!this.ignoringChance) {
            if (this.canDespawn && this.mob.getNoActionTime() >= 100) {
                return false;
            }
            if (this.mob.getRandom().nextInt(RandomStrollGoal.reducedTickDelay(this.chance)) != 0) {
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
        return this.mob.isInWater() && this.mob.getFluidHeight(FluidTags.WATER) > this.mob.getFluidJumpThreshold() || this.mob.isInLava();
    }

    @Nullable
    protected Vec3 getWanderTarget() {
        net.minecraft.util.RandomSource random = this.mob.getRandom();
            BlockPos blockPos = this.mob.blockPosition();
            for (int i = 0; i < 10; ++i) {
                BlockPos blockPos2 = blockPos.offset(random.nextInt(6) - 3, 2 - random.nextInt(4), random.nextInt(6) - 3);
                if (!this.world.getBlockState(blockPos2).is(Blocks.WATER)) continue;
                return Vec3.atBottomCenterOf(blockPos2);
            }
            return null;
    }

    @Override
    public boolean canContinueToUse() {
        return !this.mob.getNavigation().isDone();
    }

    @Override
    public void start() {
        if (this.mob instanceof SeaSnailEntity snail) {
            snail.setCanHide(false);
        }
        this.mob.getNavigation().moveTo(this.targetX, this.targetY, this.targetZ, this.speed);
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
