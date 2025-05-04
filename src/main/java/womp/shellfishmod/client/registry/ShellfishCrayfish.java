package womp.shellfishmod.client.registry;

import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.util.Identifier;
import womp.shellfishmod.registry.ShellfishItems;

public class ShellfishCrayfish {

	private static final String[][] crayfish = new String[][] {
		{"Supercrayfish", "supercrayfish"},
		{"Clayfish", "clayfish"},
		{"Sapphire Crayfish", "sapphire_crayfish"},
		{"Louisiana Crayfish", "louisiana_crayfish"},
		{"White Tubercled Crayfish", "white_tubercled_crayfish"},
		{"Zebra Crayfish", "zebra_crayfish"},
		{"Virile Crayfish", "virile_crayfish"},
		{"Rusty Crayfish", "rusty_crayfish"},
		{"Supernova Crayfish", "supernova_crayfish"},
		{"Signal Crayfish", "signal_crayfish"},
		{"Swamp Dwarf Crayfish", "swamp_dwarf_crayfish"},
		{"Australian Redclaw Crayfish", "australian_redclaw_crayfish"},
		{"Big Sandy Crayfish", "big_sandy_crayfish"},
		{"Spiny-Cheek Crayfish", "spinycheek_crayfish"},
		{"Parkhill Prairie Crayfish", "parkhill_prairie_crayfish"},
		{"Common Yabby Crayfish", "common_yabby_crayfish"},
		{"Nashville Crayfish", "nashville_crayfish"},
		{"Murray Crayfish", "murray_crayfish"},
		{"Tasmanian Giant Crayfish", "tasmanian_giant_crayfish"},
		{"Japanese Crayfish", "japanese_crayfish"},
		{"Marbled Crayfish", "marbled_crayfish"},
		{"Orange Dwarf Crayfish", "orange_dwarf_crayfish"},
		{"Slough Crayfish", "slough_crayfish"},
		{"Cave Crayfish", "cave_crayfish"},
		{"Noble Crayfish", "noble_crayfish"},
		{"Hairy Marron Crayfish", "hairy_marron_crayfish"},
		{"Thunderbolt Crayfish", "thunderbolt_crayfish"},
		{"Blue Kong Crayfish", "blue_kong_crayfish"},
		{"Diamond Crayfish", "diamond_crayfish"},
		{"Golden Crayfish", "golden_crayfish"}
	};
    
    public static void register() {

		for (String[] type : crayfish) {
			ModelPredicateProviderRegistry.register(ShellfishItems.CRAYFISH, new Identifier(type[1]), (stack, world, entity, seed) -> {
				if (stack.contains(DataComponentTypes.CUSTOM_NAME) && type[0].equals(stack.get(DataComponentTypes.CUSTOM_NAME).getString())) {
					return 1.0F;
				}
				return 0.0F;
			});
		}
    }
}
