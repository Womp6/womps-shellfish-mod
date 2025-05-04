package womp.shellfishmod.registry;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import womp.shellfishmod.command.PassiveShellfishCommand;
import womp.shellfishmod.entity.SeaUrchinEntity;

public class ShellfishUtil {
    
    public static void register() {
        CommandRegistrationCallback.EVENT.register(PassiveShellfishCommand::register);

        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (entity instanceof SeaUrchinEntity) {
                if (hand == Hand.MAIN_HAND) {
					if (player.getStackInHand(Hand.MAIN_HAND).isEmpty()) {
                    	player.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 0));
						player.damage(world.getDamageSources().magic(), 2.0f);
					}
                }
            }
            return ActionResult.PASS;
        });
    }
}
