package womp.shellfishmod.entity.goals;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class WanderToWaterGoal extends Goal {

    protected final PathfinderMob mob;
    protected double targetX;
    protected double targetY;
    protected double targetZ;
    protected final double speed;
    private final Level world;

    public WanderToWaterGoal(PathfinderMob entity, double speed) {
        this.mob = entity;
        this.speed = speed;
        this.world = mob.level();
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        Vec3 vec3 = this.getWanderTarget();
        if (this.mob.isInWater()) {
            return false;
        }
        if (vec3 == null) {
            return false;
        }
        this.targetX = vec3.x;
        this.targetY = vec3.y;
        this.targetZ = vec3.z;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return !this.mob.getNavigation().isDone();
    }

    @Override
    public void start() {
        this.mob.getNavigation().moveTo(this.targetX, this.targetY, this.targetZ, this.speed);
    }

    @Nullable
    protected Vec3 getWanderTarget() {
        RandomSource random = this.mob.getRandom();
        BlockPos blockPos = this.mob.blockPosition();
        for (int i = 0; i < 10; ++i) {
            BlockPos blockPos2 = blockPos.offset(random.nextInt(6) - 3, 1 - random.nextInt(4), random.nextInt(6) - 3);
            if (!this.world.getBlockState(blockPos2).is(Blocks.WATER)) continue;
            return Vec3.atBottomCenterOf(blockPos2);
        }
        return null;
    }
}
