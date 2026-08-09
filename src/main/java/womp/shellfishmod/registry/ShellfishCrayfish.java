package womp.shellfishmod.registry;

import java.util.HashMap;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;
import com.mojang.serialization.Codec;

public class ShellfishCrayfish {

	public static final HashMap<DataComponentType<Boolean>, String> map = new HashMap<>();

	public static final DataComponentType<Boolean> SUPERCRAYFISH = registerBoolComp("supercrayfish");
	public static final DataComponentType<Boolean> CLAYFISH = registerBoolComp("clayfish");
	public static final DataComponentType<Boolean> SAPPHIRE = registerBoolComp("sapphire");
	public static final DataComponentType<Boolean> LOUISIANA = registerBoolComp("louisiana");
	public static final DataComponentType<Boolean> WHITE_TUBERCLED = registerBoolComp("white_tubercled");
	public static final DataComponentType<Boolean> ZEBRA = registerBoolComp("zebra");
	public static final DataComponentType<Boolean> VIRILE = registerBoolComp("virile");
	public static final DataComponentType<Boolean> RUSTY = registerBoolComp("rusty");
	public static final DataComponentType<Boolean> SUPERNOVA = registerBoolComp("supernova");
	public static final DataComponentType<Boolean> SIGNAL = registerBoolComp("signal");
	public static final DataComponentType<Boolean> SWAMP_DWARF = registerBoolComp("swamp_dwarf");
	public static final DataComponentType<Boolean> AUSTRALIAN_REDCLAW = registerBoolComp("australian_redclaw");
	public static final DataComponentType<Boolean> BIG_SANDY = registerBoolComp("big_sandy");
	public static final DataComponentType<Boolean> SPINYCHEEK = registerBoolComp("spinycheek");
	public static final DataComponentType<Boolean> PARKHILL_PRAIRIE = registerBoolComp("parkhill_prairie");
	public static final DataComponentType<Boolean> COMMON_YABBY = registerBoolComp("common_yabby");
	public static final DataComponentType<Boolean> NASHVILLE = registerBoolComp("nashville");
	public static final DataComponentType<Boolean> MURRAY = registerBoolComp("murray");
	public static final DataComponentType<Boolean> TASMANIAN_GIANT = registerBoolComp("tasmanian_giant");
	public static final DataComponentType<Boolean> JAPANESE = registerBoolComp("japanese");
	public static final DataComponentType<Boolean> MARBLED = registerBoolComp("marbled");
	public static final DataComponentType<Boolean> ORANGE_DWARF = registerBoolComp("orange_dwarf");
	public static final DataComponentType<Boolean> SLOUGH = registerBoolComp("slough");
	public static final DataComponentType<Boolean> CAVE = registerBoolComp("cave");
	public static final DataComponentType<Boolean> NOBLE = registerBoolComp("noble");
	public static final DataComponentType<Boolean> HAIRY_MARRON = registerBoolComp("hairy_marron");
	public static final DataComponentType<Boolean> THUNDERBOLT = registerBoolComp("thunderbolt");
	public static final DataComponentType<Boolean> BLUE_KONG = registerBoolComp("blue_kong");
	public static final DataComponentType<Boolean> DIAMOND = registerBoolComp("diamond");
	public static final DataComponentType<Boolean> GOLDEN = registerBoolComp("golden");

	private static DataComponentType<Boolean> registerBoolComp(String name) {
		return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, Identifier.fromNamespaceAndPath("shellfish", !name.equals("supercrayfish") && !name.equals("clayfish") ? name + "_crayfish" : name), new DataComponentType.Builder<Boolean>().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build());
	}
    
    public static void register() {
		map.put(SUPERCRAYFISH, "Supercrayfish");
        map.put(CLAYFISH, "Clayfish");
        map.put(SAPPHIRE, "Sapphire Crayfish");
        map.put(LOUISIANA, "Louisiana Crayfish");
        map.put(WHITE_TUBERCLED, "White Tubercled Crayfish");
        map.put(ZEBRA, "Zebra Crayfish");
        map.put(VIRILE, "Virile Crayfish");
        map.put(RUSTY, "Rusty Crayfish");
        map.put(SUPERNOVA, "Supernova Crayfish");
        map.put(SIGNAL, "Signal Crayfish");
        map.put(SWAMP_DWARF, "Swamp Dwarf Crayfish");
        map.put(AUSTRALIAN_REDCLAW, "Australian Redclaw Crayfish");
        map.put(BIG_SANDY, "Big Sandy Crayfish");
        map.put(SPINYCHEEK, "Spiny-Cheek Crayfish");
        map.put(PARKHILL_PRAIRIE, "Parkhill Prairie Crayfish");
        map.put(COMMON_YABBY, "Common Yabby Crayfish");
        map.put(NASHVILLE, "Nashville Crayfish");
        map.put(MURRAY, "Murray Crayfish");
        map.put(TASMANIAN_GIANT, "Tasmanian Giant Crayfish");
        map.put(JAPANESE, "Japanese Crayfish");
        map.put(MARBLED, "Marbled Crayfish");
        map.put(ORANGE_DWARF, "Orange Dwarf Crayfish");
        map.put(SLOUGH, "Slough Crayfish");
        map.put(CAVE, "Cave Crayfish");
        map.put(NOBLE, "Noble Crayfish");
        map.put(HAIRY_MARRON, "Hairy Marron Crayfish");
        map.put(THUNDERBOLT, "Thunderbolt Crayfish");
        map.put(BLUE_KONG, "Blue Kong Crayfish");
        map.put(DIAMOND, "Diamond Crayfish");
        map.put(GOLDEN, "Golden Crayfish");
	}
}
