package womp.shellfishmod.entity.goals;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import womp.shellfishmod.command.ShellfishStateUtil;
import womp.shellfishmod.entity.parents.Hungry;

import java.util.EnumSet;

public class HungryActiveTargetGoal<T extends LivingEntity> extends TargetGoal {
    protected final Class<T> targetClass;

    protected final int reciprocalChance;
    protected @Nullable LivingEntity targetEntity;
    protected TargetingConditions targetPredicate;

    public HungryActiveTargetGoal(Mob mob, Class<T> targetClass, boolean checkVisibility) {
        this(mob, targetClass, 10, checkVisibility, false, null);
    }

    public HungryActiveTargetGoal(Mob mob, Class<T> targetClass, boolean checkVisibility, TargetingConditions.Selector targetPredicate) {
        this(mob, targetClass, 10, checkVisibility, false, targetPredicate);
    }

    public HungryActiveTargetGoal(Mob mob, Class<T> targetClass, boolean checkVisibility, boolean checkCanNavigate) {
        this(mob, targetClass, 10, checkVisibility, checkCanNavigate, null);
    }

    public HungryActiveTargetGoal(Mob mob, Class<T> targetClass, int reciprocalChance, boolean checkVisibility, boolean checkCanNavigate, @Nullable TargetingConditions.Selector targetPredicate) {
        super(mob, checkVisibility, checkCanNavigate);
        this.targetClass = targetClass;
        this.reciprocalChance = HungryActiveTargetGoal.reducedTickDelay(reciprocalChance);
        this.setFlags(EnumSet.of(Goal.Flag.TARGET));
        this.targetPredicate = TargetingConditions.forCombat().range(this.getFollowDistance()).selector(targetPredicate);
    }

    @Override
    public boolean canUse() {
        if (this.reciprocalChance > 0 && this.mob.getRandom().nextInt(this.reciprocalChance) != 0) {
            return false;
        }

        if (this.mob instanceof Hungry hungry && !hungry.isHungry()) {
            return false;
        }

        ServerLevel world = (ServerLevel) this.mob.level();

        if (ShellfishStateUtil.isShellfishPassive(world)) {
            return false;
        }

        this.findClosestTarget();
        return this.targetEntity != null;
    }

    protected AABB getSearchBox(double distance) {
        return this.mob.getBoundingBox().inflate(distance, 4.0, distance);
    }

    protected void findClosestTarget() {
        ServerLevel server = (ServerLevel) this.mob.level();
        this.targetEntity = this.targetClass == Player.class || this.targetClass == ServerPlayer.class ? server.getNearestPlayer(this.targetPredicate, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ()) : server.getNearestEntity(this.mob.level().getEntitiesOfClass(this.targetClass, this.getSearchBox(this.getFollowDistance()), livingEntity -> true), this.targetPredicate, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
    }

    @Override
    public void start() {
        this.mob.setTarget(this.targetEntity);
        super.start();
    }

    public void setTarget(@Nullable LivingEntity targetEntity) {
        this.targetEntity = targetEntity;
    }
}
