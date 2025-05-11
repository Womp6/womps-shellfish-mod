package womp.shellfishmod.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import womp.shellfishmod.ShellfishMod;

public class ShellfishGroup {

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ShellfishMod.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GROUP = TABS.register("shellfish_group", () -> CreativeModeTab.builder().icon(() -> new ItemStack(ShellfishItems.CRAYFISH.get())).title(Component.translatable("shellfish.group"))
            .displayItems((displayParameters, content) -> {

                //FOOD SHELLFISH
                content.accept(ShellfishItems.RAW_CRAYFISH.get());
                content.accept(ShellfishItems.COOKED_CRAYFISH.get());
                content.accept(ShellfishItems.RAW_LOBSTER.get());
                content.accept(ShellfishItems.COOKED_LOBSTER.get());
                content.accept(ShellfishItems.RAW_CRAB.get());
                content.accept(ShellfishItems.COOKED_CRAB.get());
                content.accept(ShellfishItems.RAW_SHRIMP.get());
                content.accept(ShellfishItems.COOKED_SHRIMP.get());
                content.accept(ShellfishItems.RAW_SEA_SNAIL.get());
                content.accept(ShellfishItems.COOKED_SEA_SNAIL.get());
                content.accept(ShellfishItems.RAW_UNI.get());
                content.accept(ShellfishItems.COOKED_UNI.get());
                content.accept(ShellfishItems.RAW_CLAM.get());
                content.accept(ShellfishItems.COOKED_CLAM.get());
                content.accept(ShellfishItems.RAW_OYSTER.get());
                content.accept(ShellfishItems.COOKED_OYSTER.get());
                content.accept(ShellfishItems.RAW_MUSSEL.get());
                content.accept(ShellfishItems.COOKED_MUSSEL.get());

                //SHELLS
                content.accept(ShellfishItems.SEA_SNAIL_SHELL.get());
                content.accept(ShellfishItems.CLAM_SHELL.get());
                content.accept(ShellfishItems.OYSTER_SHELL.get());
                content.accept(ShellfishItems.MUSSEL_SHELL.get());


                //NOT FOOD SHELLFISH
                content.accept(ShellfishItems.CRAYFISH.get());
                content.accept(ShellfishItems.LOBSTER.get());
                content.accept(ShellfishItems.CRAB.get());
                content.accept(ShellfishItems.SHRIMP.get());
                content.accept(ShellfishItems.SEA_SNAIL.get());
                content.accept(ShellfishItems.SEA_URCHIN.get());
                content.accept(ShellfishItems.CLAM.get());
                content.accept(ShellfishItems.OYSTER.get());
                content.accept(ShellfishItems.MUSSEL.get());

                //FANCY FOOD

                //GENERAL
                content.accept(ShellfishItems.SHELLFISH_STEW.get());
                content.accept(ShellfishItems.LIGHT_SHELLFISH_STEW.get());

                //CRAYFISH
                content.accept(ShellfishItems.CRAYFISH_BOIL.get());
                content.accept(ShellfishItems.GUMBO.get());
                content.accept(ShellfishItems.CRAYFISH_BISQUE.get());

                //LOBSTER
                content.accept(ShellfishItems.BOILED_LOBSTER.get());
                content.accept(ShellfishItems.LOBSTER_ROLL.get());
                content.accept(ShellfishItems.LOBSTER_SALAD.get());

                //CRAB
                content.accept(ShellfishItems.CRAB_CAKE.get());
                content.accept(ShellfishItems.CRAB_DIP.get());
                content.accept(ShellfishItems.CRAB_LOUIE.get());

                //SHRIMP
                content.accept(ShellfishItems.RAW_SHRIMP_SKEWER.get());
                content.accept(ShellfishItems.COOKED_SHRIMP_SKEWER.get());
                content.accept(ShellfishItems.SHRIMP_SCAMPI.get());
                content.accept(ShellfishItems.SHRIMP_TACO.get());

                //SEA_SNAIL
                content.accept(ShellfishItems.ESCARGOT.get());
                content.accept(ShellfishItems.SNAIL_GRATIN.get());
                content.accept(ShellfishItems.SNAIL_SOUP.get());

                //SEA_URCHIN
                content.accept(ShellfishItems.UNI_SUSHI.get());
                content.accept(ShellfishItems.UNI_CHAWANMUSHI.get());
                content.accept(ShellfishItems.UNI_SALAD.get());

                //CLAM
                content.accept(ShellfishItems.BAKED_CLAM.get());
                content.accept(ShellfishItems.CLAM_STRIP.get());
                content.accept(ShellfishItems.CLAM_CHOWDER.get());

                //OYSTER
                content.accept(ShellfishItems.OYSTER_ROCKEFELLER.get());
                content.accept(ShellfishItems.FRIED_OYSTER.get());
                content.accept(ShellfishItems.OYSTER_CHOWDER.get());

                //MUSSEL
                content.accept(ShellfishItems.STUFFED_MUSSEL.get());
                content.accept(ShellfishItems.MUSSEL_SOUP.get());
                content.accept(ShellfishItems.MOULES_MARINIERES.get());

                //CAVIAR
                content.accept(ShellfishItems.CAVIAR_BUCKET.get());

                //BURNT
                content.accept(ShellfishItems.BURNT_CRAYFISH.get());
                content.accept(ShellfishItems.BURNT_LOBSTER.get());
                content.accept(ShellfishItems.BURNT_CRAB.get());
                content.accept(ShellfishItems.BURNT_SHRIMP.get());
                content.accept(ShellfishItems.BURNT_SEA_SNAIL.get());
                content.accept(ShellfishItems.BURNT_UNI.get());
                content.accept(ShellfishItems.BURNT_CLAM.get());
                content.accept(ShellfishItems.BURNT_OYSTER.get());
                content.accept(ShellfishItems.BURNT_MUSSEL.get());

                //BUCKET ITEMS
                content.accept(ShellfishItems.CRAYFISH_BUCKET.get());
                content.accept(ShellfishItems.LOBSTER_BUCKET.get());
                content.accept(ShellfishItems.CRAB_BUCKET.get());
                content.accept(ShellfishItems.SHRIMP_BUCKET.get());
                content.accept(ShellfishItems.SEA_SNAIL_BUCKET.get());
                content.accept(ShellfishItems.SEA_URCHIN_BUCKET.get());
                content.accept(ShellfishItems.CLAM_BUCKET.get());
                content.accept(ShellfishItems.OYSTER_BUCKET.get());
                content.accept(ShellfishItems.MUSSEL_BUCKET.get());
                content.accept(ShellfishItems.MOSS_BALL_BUCKET.get());

                //SPAWN EGGS
                content.accept(ShellfishItems.CRAYFISH_SPAWN_EGG.get());
                content.accept(ShellfishItems.LOBSTER_SPAWN_EGG.get());
                content.accept(ShellfishItems.CRAB_SPAWN_EGG.get());
                content.accept(ShellfishItems.SHRIMP_SPAWN_EGG.get());
                content.accept(ShellfishItems.SEA_SNAIL_SPAWN_EGG.get());
                content.accept(ShellfishItems.SEA_URCHIN_SPAWN_EGG.get());
                content.accept(ShellfishItems.CLAM_SPAWN_EGG.get());
                content.accept(ShellfishItems.OYSTER_SPAWN_EGG.get());
                content.accept(ShellfishItems.MUSSEL_SPAWN_EGG.get());
                content.accept(ShellfishItems.MOSS_BALL_SPAWN_EGG.get());

                //PLANTS
                content.accept(ShellfishItems.ROCKWEED.get());
                content.accept(ShellfishItems.WATER_LETTUCE.get());
                content.accept(ShellfishItems.PADDLEWEED.get());
                content.accept(ShellfishItems.EELGRASS.get());
                content.accept(ShellfishItems.SEA_LETTUCE.get());
                content.accept(ShellfishItems.SHELLFISH_BAIT.get());
                content.accept(ShellfishItems.DRIED_SHELLFISH_BAIT.get());

                //FANCY STUFF (NOT FOOD)
                content.accept(ShellfishItems.PEARL.get());
                content.accept(ShellfishItems.SHELLFISH_TRAP.get());
                content.accept(ShellfishItems.REINFORCED_TRAP.get());

                //EGG BLOCKS
                content.accept(ShellfishItems.CRAYFISH_EGGS.get());
                content.accept(ShellfishItems.LOBSTER_EGGS.get());
                content.accept(ShellfishItems.CRAB_EGGS.get());
                content.accept(ShellfishItems.SHRIMP_EGGS.get());
                content.accept(ShellfishItems.SEA_SNAIL_EGGS.get());

                //MISC
                content.accept(ShellfishItems.CLAM_CLAY.get());
                content.accept(ShellfishItems.CLAM_SAND.get());
                content.accept(ShellfishItems.OYSTER_CLAY.get());
                content.accept(ShellfishItems.OYSTER_SAND.get());
                content.accept(ShellfishItems.MUSSEL_CLAY.get());
                content.accept(ShellfishItems.MUSSEL_SAND.get());
                content.accept(ShellfishItems.DEAD_CLAM.get());
                content.accept(ShellfishItems.DEAD_OYSTER.get());
                content.accept(ShellfishItems.DEAD_MUSSEL.get());
    }).build());


    public static void register(IEventBus bus) {
        TABS.register(bus);
    }
}
