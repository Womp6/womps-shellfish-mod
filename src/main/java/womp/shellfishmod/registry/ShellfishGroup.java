package womp.shellfishmod.registry;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ShellfishGroup {
    
    public static final ResourceKey<CreativeModeTab> SHELLFISH_GROUP = ShellfishUtil.createKey("shellfish_group", Registries.CREATIVE_MODE_TAB);

    public static void register() {

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, SHELLFISH_GROUP, FabricCreativeModeTab.builder().icon(() -> new ItemStack(ShellfishItems.CRAYFISH)).title(Component.translatable("shellfish.group")).build());

        CreativeModeTabEvents.modifyOutputEvent(SHELLFISH_GROUP).register(content -> {

			//FOOD SHELLFISH
			content.accept(ShellfishItems.RAW_CRAYFISH);
			content.accept(ShellfishItems.COOKED_CRAYFISH);
			content.accept(ShellfishItems.RAW_LOBSTER);
			content.accept(ShellfishItems.COOKED_LOBSTER);
			content.accept(ShellfishItems.RAW_CRAB);
			content.accept(ShellfishItems.COOKED_CRAB);
			content.accept(ShellfishItems.RAW_SHRIMP);
			content.accept(ShellfishItems.COOKED_SHRIMP);
			content.accept(ShellfishItems.RAW_SEA_SNAIL);
			content.accept(ShellfishItems.COOKED_SEA_SNAIL);
			content.accept(ShellfishItems.RAW_UNI);
			content.accept(ShellfishItems.COOKED_UNI);
			content.accept(ShellfishItems.RAW_CLAM);
			content.accept(ShellfishItems.COOKED_CLAM);
			content.accept(ShellfishItems.RAW_OYSTER);
			content.accept(ShellfishItems.COOKED_OYSTER);
			content.accept(ShellfishItems.RAW_MUSSEL);
			content.accept(ShellfishItems.COOKED_MUSSEL);

			//SHELLS
			content.accept(ShellfishBlocks.SEA_SNAIL_SHELL_BLOCK);
			content.accept(ShellfishBlocks.CLAM_SHELL);
			content.accept(ShellfishBlocks.OYSTER_SHELL);
			content.accept(ShellfishBlocks.MUSSEL_SHELL);


			//NOT FOOD SHELLFISH
			content.accept(ShellfishItems.CRAYFISH);
			content.accept(ShellfishItems.LOBSTER);
			content.accept(ShellfishItems.CRAB);
			content.accept(ShellfishItems.SHRIMP);
			content.accept(ShellfishItems.SEA_SNAIL);
			content.accept(ShellfishItems.SEA_URCHIN);
			content.accept(ShellfishItems.CLAM);
			content.accept(ShellfishItems.OYSTER);
			content.accept(ShellfishItems.MUSSEL);

			//FANCY FOOD

				//GENERAL
			content.accept(ShellfishItems.SHELLFISH_STEW);
			content.accept(ShellfishItems.LIGHT_SHELLFISH_STEW);

				//CRAYFISH
			content.accept(ShellfishItems.CRAYFISH_BOIL);
			content.accept(ShellfishItems.GUMBO);
			content.accept(ShellfishItems.CRAYFISH_BISQUE);

				//LOBSTER
			content.accept(ShellfishItems.BOILED_LOBSTER);
			content.accept(ShellfishItems.LOBSTER_ROLL);
			content.accept(ShellfishItems.LOBSTER_SALAD);

				//CRAB
			content.accept(ShellfishItems.CRAB_CAKE);
			content.accept(ShellfishItems.CRAB_DIP);
			content.accept(ShellfishItems.CRAB_LOUIE);

				//SHRIMP
			content.accept(ShellfishItems.RAW_SHRIMP_SKEWER);
			content.accept(ShellfishItems.COOKED_SHRIMP_SKEWER);
			content.accept(ShellfishItems.SHRIMP_SCAMPI);
			content.accept(ShellfishItems.SHRIMP_TACO);

				//SEA_SNAIL
			content.accept(ShellfishItems.ESCARGOT);
			content.accept(ShellfishItems.SNAIL_GRATIN);
			content.accept(ShellfishItems.SNAIL_SOUP);

				//SEA_URCHIN
			content.accept(ShellfishItems.UNI_SUSHI);
			content.accept(ShellfishItems.UNI_CHAWANMUSHI);
			content.accept(ShellfishItems.UNI_SALAD);

				//CLAM
			content.accept(ShellfishItems.BAKED_CLAM);
			content.accept(ShellfishItems.CLAM_STRIP);
			content.accept(ShellfishItems.CLAM_CHOWDER);
			
				//OYSTER
			content.accept(ShellfishItems.OYSTER_ROCKEFELLER);
			content.accept(ShellfishItems.FRIED_OYSTER);
			content.accept(ShellfishItems.OYSTER_CHOWDER);

				//MUSSEL
			content.accept(ShellfishItems.STUFFED_MUSSEL);
			content.accept(ShellfishItems.MUSSEL_SOUP);
			content.accept(ShellfishItems.MOULES_MARINIERES);

				//CAVIAR
			content.accept(ShellfishItems.CAVIAR_BUCKET);

				//BURNT
			content.accept(ShellfishItems.BURNT_CRAYFISH);
			content.accept(ShellfishItems.BURNT_LOBSTER);
			content.accept(ShellfishItems.BURNT_CRAB);
			content.accept(ShellfishItems.BURNT_SHRIMP);
			content.accept(ShellfishItems.BURNT_SEA_SNAIL);
			content.accept(ShellfishItems.BURNT_UNI);
			content.accept(ShellfishItems.BURNT_CLAM);
			content.accept(ShellfishItems.BURNT_OYSTER);
			content.accept(ShellfishItems.BURNT_MUSSEL);

			//BUCKET ITEMS
			content.accept(ShellfishItems.CRAYFISH_BUCKET);
			content.accept(ShellfishItems.LOBSTER_BUCKET);
			content.accept(ShellfishItems.CRAB_BUCKET);
			content.accept(ShellfishItems.SHRIMP_BUCKET);
			content.accept(ShellfishItems.SEA_SNAIL_BUCKET);
			content.accept(ShellfishItems.SEA_URCHIN_BUCKET);
			content.accept(ShellfishItems.CLAM_BUCKET);
			content.accept(ShellfishItems.OYSTER_BUCKET);
			content.accept(ShellfishItems.MUSSEL_BUCKET);
			content.accept(ShellfishItems.MOSS_BALL_BUCKET);

			//SPAWN EGGS
			content.accept(ShellfishItems.CRAYFISH_SPAWN_EGG);
			content.accept(ShellfishItems.LOBSTER_SPAWN_EGG);
			content.accept(ShellfishItems.CRAB_SPAWN_EGG);
			content.accept(ShellfishItems.SHRIMP_SPAWN_EGG);
			content.accept(ShellfishItems.SEA_SNAIL_SPAWN_EGG);
			content.accept(ShellfishItems.SEA_URCHIN_SPAWN_EGG);
			content.accept(ShellfishItems.CLAM_SPAWN_EGG);
			content.accept(ShellfishItems.OYSTER_SPAWN_EGG);
			content.accept(ShellfishItems.MUSSEL_SPAWN_EGG);
			content.accept(ShellfishItems.MOSS_BALL_SPAWN_EGG);

			//PLANTS
			content.accept(ShellfishBlocks.ROCKWEED);
			content.accept(ShellfishBlocks.WATER_LETTUCE);
			content.accept(ShellfishBlocks.PADDLEWEED);
			content.accept(ShellfishBlocks.EELGRASS);
			content.accept(ShellfishBlocks.SEA_LETTUCE);
			content.accept(ShellfishBlocks.CATTAIL);
			content.accept(ShellfishBlocks.TALL_CATTAIL);
			content.accept(ShellfishBlocks.PICKERELWEED);
			content.accept(ShellfishBlocks.TALL_PICKERELWEED);
			content.accept(ShellfishBlocks.WHEATGRASS);
			content.accept(ShellfishBlocks.TALL_WHEATGRASS);
			content.accept(ShellfishBlocks.WATER_GRASS);
			content.accept(ShellfishBlocks.TALL_WATER_GRASS);
			content.accept(ShellfishBlocks.DRIFTWOOD);
			content.accept(ShellfishItems.SHELLFISH_BAIT);
			content.accept(ShellfishItems.DRIED_SHELLFISH_BAIT);

			//FANCY STUFF (NOT FOOD)
			content.accept(ShellfishItems.PEARL);
			content.accept(ShellfishBlocks.SHELLFISH_TRAP_BLOCK);
			content.accept(ShellfishBlocks.REINFORCED_TRAP);

			//EGG BLOCKS
			content.accept(ShellfishBlocks.CRAYFISH_EGGS_BLOCK);
			content.accept(ShellfishBlocks.LOBSTER_EGGS_BLOCK);
			content.accept(ShellfishBlocks.CRAB_EGGS_BLOCK);
			content.accept(ShellfishBlocks.SHRIMP_EGGS_BLOCK);
			content.accept(ShellfishBlocks.SEA_SNAIL_EGGS_BLOCK);

			//MISC
			content.accept(ShellfishBlocks.CLAM_CLAY);
			content.accept(ShellfishBlocks.CLAM_SAND);
			content.accept(ShellfishBlocks.CLAM_MUD);
			content.accept(ShellfishBlocks.OYSTER_CLAY);
			content.accept(ShellfishBlocks.OYSTER_SAND);
			content.accept(ShellfishBlocks.OYSTER_MUD);
			content.accept(ShellfishBlocks.MUSSEL_CLAY);
			content.accept(ShellfishBlocks.MUSSEL_SAND);
			content.accept(ShellfishBlocks.MUSSEL_MUD);
			content.accept(ShellfishBlocks.DEAD_CLAM_BLOCK);
			content.accept(ShellfishBlocks.DEAD_OYSTER_BLOCK);
			content.accept(ShellfishBlocks.DEAD_MUSSEL_BLOCK);
		});
    }
}
