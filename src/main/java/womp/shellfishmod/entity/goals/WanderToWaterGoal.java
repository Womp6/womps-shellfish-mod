package womp.shellfishmod.entity.goals;

import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import womp.shellfishmod.entity.SeaSnailEntity;

public class WanderToWaterGoal extends Goal {
    private final PathfinderMob mob;
    private double x;
    private double y;
    private double z;
    private final double speed;
    private final Level world;

    public WanderToWaterGoal(PathfinderMob mob, double speed) {
        this.mob = mob;
        this.speed = speed;
        this.world = mob.level();
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (this.mob.isInWater()) {
            return false;
        };
        Vec3 vec3d = this.getWanderTarget();
        if (vec3d == null) {
            return false;
        }
        this.x = vec3d.x;
        this.y = vec3d.y;
        this.z = vec3d.z;
        return true;
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
        this.mob.getNavigation().moveTo(this.x, this.y, this.z, this.speed);
    }

    @Override
    public void stop() {
        if (this.mob instanceof SeaSnailEntity snail) {
            snail.setCanHide(true);
        }
    }

    @Nullable
    private Vec3 getWanderTarget() {
        net.minecraft.util.RandomSource random = this.mob.getRandom();
        BlockPos blockPos = this.mob.blockPosition();
        for (int i = 0; i < 10; ++i) {
            BlockPos blockPos2 = blockPos.offset(random.nextInt(6) - 3, 1 - random.nextInt(4), random.nextInt(6) - 3);
            if (!this.world.getBlockState(blockPos2).is(Blocks.WATER)) continue;
            return Vec3.atBottomCenterOf(blockPos2);
        }
        return null;
    }
}