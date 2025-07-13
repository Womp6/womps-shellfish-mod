package womp.shellfishmod.registry;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ShellfishGroup {
    
    public static final RegistryKey<ItemGroup> SHELLFISH_GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier("shellfish", "shellfish_group"));

    public static void register() {

        Registry.register(Registries.ITEM_GROUP, SHELLFISH_GROUP, FabricItemGroup.builder().icon(() -> new ItemStack(ShellfishItems.CRAYFISH)).displayName(Text.translatable("shellfish.group")).build());

        ItemGroupEvents.modifyEntriesEvent(SHELLFISH_GROUP).register(content -> {

			//FOOD SHELLFISH
			content.add(ShellfishItems.RAW_CRAYFISH);
			content.add(ShellfishItems.COOKED_CRAYFISH);
			content.add(ShellfishItems.RAW_LOBSTER);
			content.add(ShellfishItems.COOKED_LOBSTER);
			content.add(ShellfishItems.RAW_CRAB);
			content.add(ShellfishItems.COOKED_CRAB);
			content.add(ShellfishItems.RAW_SHRIMP);
			content.add(ShellfishItems.COOKED_SHRIMP);
			content.add(ShellfishItems.RAW_SEA_SNAIL);
			content.add(ShellfishItems.COOKED_SEA_SNAIL);
			content.add(ShellfishItems.RAW_UNI);
			content.add(ShellfishItems.COOKED_UNI);
			content.add(ShellfishItems.RAW_CLAM);
			content.add(ShellfishItems.COOKED_CLAM);
			content.add(ShellfishItems.RAW_OYSTER);
			content.add(ShellfishItems.COOKED_OYSTER);
			content.add(ShellfishItems.RAW_MUSSEL);
			content.add(ShellfishItems.COOKED_MUSSEL);

			//SHELLS
			content.add(ShellfishBlocks.SEA_SNAIL_SHELL_BLOCK);
			content.add(ShellfishBlocks.CLAM_SHELL);
			content.add(ShellfishBlocks.OYSTER_SHELL);
			content.add(ShellfishBlocks.MUSSEL_SHELL);


			//NOT FOOD SHELLFISH
			content.add(ShellfishItems.CRAYFISH);
			content.add(ShellfishItems.LOBSTER);
			content.add(ShellfishItems.CRAB);
			content.add(ShellfishItems.SHRIMP);
			content.add(ShellfishItems.SEA_SNAIL);
			content.add(ShellfishItems.SEA_URCHIN);
			content.add(ShellfishItems.CLAM);
			content.add(ShellfishItems.OYSTER);
			content.add(ShellfishItems.MUSSEL);

			//FANCY FOOD

				//GENERAL
			content.add(ShellfishItems.SHELLFISH_STEW);
			content.add(ShellfishItems.LIGHT_SHELLFISH_STEW);

				//CRAYFISH
			content.add(ShellfishItems.CRAYFISH_BOIL);
			content.add(ShellfishItems.GUMBO);
			content.add(ShellfishItems.CRAYFISH_BISQUE);

				//LOBSTER
			content.add(ShellfishItems.BOILED_LOBSTER);
			content.add(ShellfishItems.LOBSTER_ROLL);
			content.add(ShellfishItems.LOBSTER_SALAD);

				//CRAB
			content.add(ShellfishItems.CRAB_CAKE);
			content.add(ShellfishItems.CRAB_DIP);
			content.add(ShellfishItems.CRAB_LOUIE);

				//SHRIMP
			content.add(ShellfishItems.RAW_SHRIMP_SKEWER);
			content.add(ShellfishItems.COOKED_SHRIMP_SKEWER);
			content.add(ShellfishItems.SHRIMP_SCAMPI);
			content.add(ShellfishItems.SHRIMP_TACO);

				//SEA_SNAIL
			content.add(ShellfishItems.ESCARGOT);
			content.add(ShellfishItems.SNAIL_GRATIN);
			content.add(ShellfishItems.SNAIL_SOUP);

				//SEA_URCHIN
			content.add(ShellfishItems.UNI_SUSHI);
			content.add(ShellfishItems.UNI_CHAWANMUSHI);
			content.add(ShellfishItems.UNI_SALAD);

				//CLAM
			content.add(ShellfishItems.BAKED_CLAM);
			content.add(ShellfishItems.CLAM_STRIP);
			content.add(ShellfishItems.CLAM_CHOWDER);
			
				//OYSTER
			content.add(ShellfishItems.OYSTER_ROCKEFELLER);
			content.add(ShellfishItems.FRIED_OYSTER);
			content.add(ShellfishItems.OYSTER_CHOWDER);

				//MUSSEL
			content.add(ShellfishItems.STUFFED_MUSSEL);
			content.add(ShellfishItems.MUSSEL_SOUP);
			content.add(ShellfishItems.MOULES_MARINIERES);

				//CAVIAR
			content.add(ShellfishItems.CAVIAR_BUCKET);

				//BURNT
			content.add(ShellfishItems.BURNT_CRAYFISH);
			content.add(ShellfishItems.BURNT_LOBSTER);
			content.add(ShellfishItems.BURNT_CRAB);
			content.add(ShellfishItems.BURNT_SHRIMP);
			content.add(ShellfishItems.BURNT_SEA_SNAIL);
			content.add(ShellfishItems.BURNT_UNI);
			content.add(ShellfishItems.BURNT_CLAM);
			content.add(ShellfishItems.BURNT_OYSTER);
			content.add(ShellfishItems.BURNT_MUSSEL);

			//BUCKET ITEMS
			content.add(ShellfishItems.CRAYFISH_BUCKET);
			content.add(ShellfishItems.LOBSTER_BUCKET);
			content.add(ShellfishItems.CRAB_BUCKET);
			content.add(ShellfishItems.SHRIMP_BUCKET);
			content.add(ShellfishItems.SEA_SNAIL_BUCKET);
			content.add(ShellfishItems.SEA_URCHIN_BUCKET);
			content.add(ShellfishItems.CLAM_BUCKET);
			content.add(ShellfishItems.OYSTER_BUCKET);
			content.add(ShellfishItems.MUSSEL_BUCKET);
			content.add(ShellfishItems.MOSS_BALL_BUCKET);

			//SPAWN EGGS
			content.add(ShellfishItems.CRAYFISH_SPAWN_EGG);
			content.add(ShellfishItems.LOBSTER_SPAWN_EGG);
			content.add(ShellfishItems.CRAB_SPAWN_EGG);
			content.add(ShellfishItems.SHRIMP_SPAWN_EGG);
			content.add(ShellfishItems.SEA_SNAIL_SPAWN_EGG);
			content.add(ShellfishItems.SEA_URCHIN_SPAWN_EGG);
			content.add(ShellfishItems.CLAM_SPAWN_EGG);
			content.add(ShellfishItems.OYSTER_SPAWN_EGG);
			content.add(ShellfishItems.MUSSEL_SPAWN_EGG);
			content.add(ShellfishItems.MOSS_BALL_SPAWN_EGG);

			//PLANTS
			content.add(ShellfishBlocks.ROCKWEED);
			content.add(ShellfishBlocks.WATER_LETTUCE);
			content.add(ShellfishBlocks.PADDLEWEED);
			content.add(ShellfishBlocks.EELGRASS);
			content.add(ShellfishBlocks.SEA_LETTUCE);
			content.add(ShellfishBlocks.CATTAIL);
			content.add(ShellfishBlocks.TALL_CATTAIL);
			content.add(ShellfishBlocks.PICKERELWEED);
			content.add(ShellfishBlocks.TALL_PICKERELWEED);
			content.add(ShellfishBlocks.WHEATGRASS);
			content.add(ShellfishBlocks.TALL_WHEATGRASS);
			content.add(ShellfishBlocks.WATER_GRASS);
			content.add(ShellfishBlocks.TALL_WATER_GRASS);
			content.add(ShellfishBlocks.DRIFTWOOD);
			content.add(ShellfishItems.SHELLFISH_BAIT);
			content.add(ShellfishItems.DRIED_SHELLFISH_BAIT);

			//FANCY STUFF (NOT FOOD)
			content.add(ShellfishItems.PEARL);
			content.add(ShellfishBlocks.SHELLFISH_TRAP_BLOCK);
			content.add(ShellfishBlocks.REINFORCED_TRAP);

			//EGG BLOCKS
			content.add(ShellfishBlocks.CRAYFISH_EGGS_BLOCK);
			content.add(ShellfishBlocks.LOBSTER_EGGS_BLOCK);
			content.add(ShellfishBlocks.CRAB_EGGS_BLOCK);
			content.add(ShellfishBlocks.SHRIMP_EGGS_BLOCK);
			content.add(ShellfishBlocks.SEA_SNAIL_EGGS_BLOCK);

			//MISC
			content.add(ShellfishBlocks.CLAM_CLAY);
			content.add(ShellfishBlocks.CLAM_SAND);
			content.add(ShellfishBlocks.CLAM_MUD);
			content.add(ShellfishBlocks.OYSTER_CLAY);
			content.add(ShellfishBlocks.OYSTER_SAND);
			content.add(ShellfishBlocks.OYSTER_MUD);
			content.add(ShellfishBlocks.MUSSEL_CLAY);
			content.add(ShellfishBlocks.MUSSEL_SAND);
			content.add(ShellfishBlocks.MUSSEL_MUD);
			content.add(ShellfishBlocks.DEAD_CLAM_BLOCK);
			content.add(ShellfishBlocks.DEAD_OYSTER_BLOCK);
			content.add(ShellfishBlocks.DEAD_MUSSEL_BLOCK);
		});
    }
}
