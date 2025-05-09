package womp.shellfishmod.registry;

import com.mojang.serialization.Codec;

import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ShellfishComponents {

    public static final ComponentType<Integer> DURABILITY_COMPONENT = register("durability", new ComponentType.Builder<Integer>().codec(Codec.INT).packetCodec(PacketCodecs.INTEGER).build());

    public static <T> ComponentType<T> register(String name, ComponentType<T> factory) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of("shellfish", name), factory);
    }

    public static void register() {}
    
}
