package womp.shellfishmod.entity.goals;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import womp.shellfishmod.entity.parents.ShellfishEntity;

public class FollowParentInWaterGoal extends FollowParentGoal {

    private final ShellfishEntity<?> shellfish;
    private final Level world;
    private final java.util.Random random;

    public FollowParentInWaterGoal(ShellfishEntity<?> shellfish, double speed) {
        super(shellfish, speed);
        this.shellfish = shellfish;
        this.world = this.shellfish.level();
        this.random = new java.util.Random();
    }

    @Override
    public boolean canUse() {
        if (!this.shellfish.isEyeInFluid(FluidTags.WATER)) {
            return false;
        }

        BlockPos targetPos = this.shellfish.blockPosition().offset(
                this.random.nextInt(10) - 5,
                this.random.nextInt(3) - 1,
                this.random.nextInt(10) - 5
        );

        if (!this.world.getBlockState(targetPos).getFluidState().is(FluidTags.WATER)) {
            return false;
        }

        Path path = this.shellfish.getNavigation().createPath(targetPos, 1);
        if (path == null) {
            return false;
        }

        for (int i = 0; i < path.getNodeCount(); i++) {
            BlockPos pathPos = path.getNodePos(i);
            if (!this.world.getBlockState(pathPos).getFluidState().is(FluidTags.WATER)) {
                return false;
            }
        }

        return super.canUse();
    }
}
