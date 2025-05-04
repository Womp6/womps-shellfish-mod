package womp.shellfishmod.registry;

import com.mojang.serialization.Codec;

import net.minecraft.component.DataComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ShellfishComponents {

    public static final DataComponentType<Integer> DURABILITY_COMPONENT = register("durability", new DataComponentType.Builder<Integer>().codec(Codec.INT).packetCodec(PacketCodecs.INTEGER).build());

    public static <T> DataComponentType<T> register(String name, DataComponentType<T> factory) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, new Identifier("shellfish", name), factory);
    }

    public static void register() {}
    
}
