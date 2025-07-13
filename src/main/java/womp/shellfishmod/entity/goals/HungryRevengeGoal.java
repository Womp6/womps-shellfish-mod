package womp.shellfishmod.entity.goals;

import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import womp.shellfishmod.command.ShellfishStateUtil;

public class HungryRevengeGoal extends RevengeGoal {

    public HungryRevengeGoal(PathAwareEntity mob, Class<?>[] noRevengeTypes) {
        super(mob, noRevengeTypes);
    }

    @Override
    public boolean canStart() {
        if (ShellfishStateUtil.isShellfishPassive((ServerWorld)this.mob.getWorld()) && !(target instanceof PlayerEntity)) return false;
        return super.canStart();
    }
    
    @Override
    public boolean shouldContinue() {
        if (ShellfishStateUtil.isShellfishPassive((ServerWorld)this.mob.getWorld()) && !(target instanceof PlayerEntity)) return false;
        return super.shouldContinue();
    }
}
