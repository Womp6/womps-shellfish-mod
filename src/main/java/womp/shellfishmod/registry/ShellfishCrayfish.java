package womp.shellfishmod.registry;

import java.util.HashMap;

import com.mojang.serialization.Codec;

import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ShellfishCrayfish {

	public static final HashMap<ComponentType<Boolean>, String> map = new HashMap<>();

	public static final ComponentType<Boolean> SUPERCRAYFISH = registerBoolComp("supercrayfish");
	public static final ComponentType<Boolean> CLAYFISH = registerBoolComp("clayfish");
	public static final ComponentType<Boolean> SAPPHIRE = registerBoolComp("sapphire");
	public static final ComponentType<Boolean> LOUISIANA = registerBoolComp("louisiana");
	public static final ComponentType<Boolean> WHITE_TUBERCLED = registerBoolComp("white_tubercled");
	public static final ComponentType<Boolean> ZEBRA = registerBoolComp("zebra");
	public static final ComponentType<Boolean> VIRILE = registerBoolComp("virile");
	public static final ComponentType<Boolean> RUSTY = registerBoolComp("rusty");
	public static final ComponentType<Boolean> SUPERNOVA = registerBoolComp("supernova");
	public static final ComponentType<Boolean> SIGNAL = registerBoolComp("signal");
	public static final ComponentType<Boolean> SWAMP_DWARF = registerBoolComp("swamp_dwarf");
	public static final ComponentType<Boolean> AUSTRALIAN_REDCLAW = registerBoolComp("australian_redclaw");
	public static final ComponentType<Boolean> BIG_SANDY = registerBoolComp("big_sandy");
	public static final ComponentType<Boolean> SPINYCHEEK = registerBoolComp("spinycheek");
	public static final ComponentType<Boolean> PARKHILL_PRAIRIE = registerBoolComp("parkhill_prairie");
	public static final ComponentType<Boolean> COMMON_YABBY = registerBoolComp("common_yabby");
	public static final ComponentType<Boolean> NASHVILLE = registerBoolComp("nashville");
	public static final ComponentType<Boolean> MURRAY = registerBoolComp("murray");
	public static final ComponentType<Boolean> TASMANIAN_GIANT = registerBoolComp("tasmanian_giant");
	public static final ComponentType<Boolean> JAPANESE = registerBoolComp("japanese");
	public static final ComponentType<Boolean> MARBLED = registerBoolComp("marbled");
	public static final ComponentType<Boolean> ORANGE_DWARF = registerBoolComp("orange_dwarf");
	public static final ComponentType<Boolean> SLOUGH = registerBoolComp("slough");
	public static final ComponentType<Boolean> CAVE = registerBoolComp("cave");
	public static final ComponentType<Boolean> NOBLE = registerBoolComp("noble");
	public static final ComponentType<Boolean> HAIRY_MARRON = registerBoolComp("hairy_marron");
	public static final ComponentType<Boolean> THUNDERBOLT = registerBoolComp("thunderbolt");
	public static final ComponentType<Boolean> BLUE_KONG = registerBoolComp("blue_kong");
	public static final ComponentType<Boolean> DIAMOND = registerBoolComp("diamond");
	public static final ComponentType<Boolean> GOLDEN = registerBoolComp("golden");

	private static ComponentType<Boolean> registerBoolComp(String name) {
		return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of("shellfish", !name.equals("supercrayfish") && !name.equals("clayfish") ? name + "_crayfish" : name), new ComponentType.Builder<Boolean>().codec(Codec.BOOL).packetCodec(PacketCodecs.BOOLEAN).build());
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
