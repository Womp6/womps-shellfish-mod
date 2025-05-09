package womp.shellfishmod.entity.goals;

import java.util.EnumSet;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;

public class SitAroundGoal extends Goal {
    private final MobEntity mob;
    private int sitTime;

    public SitAroundGoal(MobEntity mob) {
        this.mob = mob;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    @Override
    public boolean canStart() {
        return this.mob.getRandom().nextFloat() < 0.02f;
    }

    @Override
    public boolean shouldContinue() {
        return this.sitTime >= 0;
    }

    @Override
    public void start() {
        this.sitTime = 20 + this.mob.getRandom().nextInt(20);
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }
}
