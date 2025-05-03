package womp.shellfishmod.entity.goals;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.GameRules;
import womp.shellfishmod.entity.parents.EggLaying;
import womp.shellfishmod.entity.parents.ShellfishEntity;

public class ShellfishMateGoal extends AnimalMateGoal {

    protected final EggLaying shellfish;

    public ShellfishMateGoal(EggLaying shellfish, double speed) {
        super(shellfish.getEntity(), speed);
        this.shellfish = shellfish;
    }

    @Override
    public boolean canStart() {
        return super.canStart() && !this.shellfish.hasEgg();
    }

    @Override
    protected void breed() {
        ServerPlayerEntity serverPlayerEntity = this.animal.getLovingPlayer();
        if (serverPlayerEntity == null && this.mate.getLovingPlayer() != null) {
            serverPlayerEntity = this.mate.getLovingPlayer();
        }
        if (serverPlayerEntity != null) {
            serverPlayerEntity.incrementStat(Stats.ANIMALS_BRED);
            Criteria.BRED_ANIMALS.trigger(serverPlayerEntity, this.animal, this.mate, null);
        }
        this.shellfish.setHasEgg(true);
        this.animal.setBreedingAge(6000);
        this.mate.setBreedingAge(6000);
        this.animal.resetLoveTicks();
        this.mate.resetLoveTicks();
        if (mate instanceof ShellfishEntity shellfishPartner) this.shellfish.setPartnerVariant(shellfishPartner.getVariant());
        Random random = this.animal.getRandom();
        if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
            this.world.spawnEntity(new ExperienceOrbEntity(this.world, this.animal.getX(), this.animal.getY(), this.animal.getZ(), random.nextInt(7) + 1));
        }
    }
}
