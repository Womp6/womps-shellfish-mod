package womp.shellfishmod.registry;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;

public class ShellfishComponents {

    public static final DataComponentType<Integer> DURABILITY_COMPONENT = register("durability", new DataComponentType.Builder<Integer>().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build());

    public static <T> DataComponentType<T> register(String name, DataComponentType<T> factory) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, Identifier.fromNamespaceAndPath("shellfish", name), factory);
    }

    public static void register() {}
    
}
