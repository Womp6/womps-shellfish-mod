package womp.shellfishmod.registry;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import womp.shellfishmod.ShellfishMod;

import java.util.function.Supplier;

public class ShellfishComponents {

    public static final DeferredRegister.DataComponents COMPONENTS = DeferredRegister.createDataComponents(ShellfishMod.MOD_ID);

    public static final Supplier<DataComponentType<Integer>> DURABILITY_COMPONENT = COMPONENTS.registerComponentType("durability", builder -> builder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT));

    public static void register(IEventBus bus) {
        COMPONENTS.register(bus);
    }
}
