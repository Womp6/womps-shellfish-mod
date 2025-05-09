package womp.shellfishmod.entity.goals;

import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import womp.shellfishmod.entity.parents.ShellfishEntity;

public class FollowParentInWaterGoal extends FollowParentGoal {
    private final ShellfishEntity<?> shellfish;
    private final World world;
    private final java.util.Random random;

    public FollowParentInWaterGoal(ShellfishEntity<?> shellfish, double speed) {
        super(shellfish, speed);
        this.shellfish = shellfish;
        this.world = this.shellfish.getWorld();
        this.random = new java.util.Random();
    }

    @Override
    public boolean canStart() {
        if (!this.shellfish.isSubmergedIn(FluidTags.WATER)) {
            return false;
        }
        
        BlockPos targetPos = this.shellfish.getBlockPos().add(
            this.random.nextInt(10) - 5,
            this.random.nextInt(3) - 1,
            this.random.nextInt(10) - 5
        );

        if (!this.world.getBlockState(targetPos).getFluidState().isIn(FluidTags.WATER)) {
            return false;
        }
    
        Path path = this.shellfish.getNavigation().findPathTo(targetPos, 1);
        if (path == null) {
            return false;
        }
    
        for (int i = 0; i < path.getLength(); i++) {
            BlockPos pathPos = path.getNodePos(i);
            if (!this.world.getBlockState(pathPos).getFluidState().isIn(FluidTags.WATER)) {
                return false;
            }
        }
        
        return super.canStart();
    }
}
