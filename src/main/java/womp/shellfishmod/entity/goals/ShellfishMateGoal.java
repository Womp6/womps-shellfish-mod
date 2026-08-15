package womp.shellfishmod.entity.goals;

import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.level.gamerules.GameRules;
import womp.shellfishmod.entity.parents.EggLaying;
import womp.shellfishmod.entity.parents.ShellfishEntity;

public class ShellfishMateGoal extends BreedGoal {

    protected final EggLaying shellfish;

    public ShellfishMateGoal(EggLaying shellfish, double speed) {
        super(shellfish.getEntity(), speed);
        this.shellfish = shellfish;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && !this.shellfish.hasEgg();
    }

    @Override
    protected void breed() {
        ServerPlayer serverPlayerEntity = this.animal.getLoveCause();
        if (serverPlayerEntity == null && this.partner.getLoveCause() != null) {
            serverPlayerEntity = this.partner.getLoveCause();
        }
        if (serverPlayerEntity != null) {
            serverPlayerEntity.awardStat(Stats.ANIMALS_BRED);
            CriteriaTriggers.BRED_ANIMALS.trigger(serverPlayerEntity, this.animal, this.partner, null);
        }
        this.shellfish.setHasEgg(true);
        this.animal.setAge(6000);
        this.partner.setAge(6000);
        this.animal.resetLove();
        this.partner.resetLove();
        if (partner instanceof ShellfishEntity<?> shellfishPartner) this.shellfish.setPartnerVariant(shellfishPartner.getVariant().getIndex());
        RandomSource random = this.animal.getRandom();
        if (this.level.getGameRules().get(GameRules.MOB_DROPS)) {
            this.level.addFreshEntity(new ExperienceOrb(this.level, this.animal.getX(), this.animal.getY(), this.animal.getZ(), random.nextInt(7) + 1));
        }
    }
}
