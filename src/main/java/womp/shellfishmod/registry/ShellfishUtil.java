package womp.shellfishmod.registry;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import womp.shellfishmod.command.PassiveShellfishCommand;
import womp.shellfishmod.entity.SeaUrchinEntity;

public class ShellfishUtil {

    public static <T> ResourceKey<T> createKey(String name, ResourceKey<? extends Registry<T>> type) {
        return ResourceKey.create(type, Identifier.fromNamespaceAndPath("shellfish", name));
    }
    
    public static void register() {
        CommandRegistrationCallback.EVENT.register(PassiveShellfishCommand::register);

        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (entity instanceof SeaUrchinEntity) {
				if (world instanceof ServerLevel serverWorld) {
                	if (hand == InteractionHand.MAIN_HAND) {
						if (player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
                    		player.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 0));
							player.hurtServer(serverWorld, world.damageSources().magic(), 2.0f);
						}
                	}
				}
            }
            return InteractionResult.PASS;
        });

        ShellfishCrayfish.register();
    }
}
