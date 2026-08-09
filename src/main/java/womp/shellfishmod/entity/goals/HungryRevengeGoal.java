package womp.shellfishmod.entity.goals;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import womp.shellfishmod.command.ShellfishStateUtil;

public class HungryRevengeGoal extends HurtByTargetGoal {

    public HungryRevengeGoal(PathfinderMob mob, Class<?>[] noRevengeTypes) {
        super(mob, noRevengeTypes);
    }

    @Override
    public boolean canUse() {
        if (ShellfishStateUtil.isShellfishPassive((ServerLevel)this.mob.level()) && !(targetMob instanceof Player)) return false;
        return super.canUse();
    }
    
    @Override
    public boolean canContinueToUse() {
        if (ShellfishStateUtil.isShellfishPassive((ServerLevel)this.mob.level()) && !(targetMob instanceof Player)) return false;
        return super.canContinueToUse();
    }
}
