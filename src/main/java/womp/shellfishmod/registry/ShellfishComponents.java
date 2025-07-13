package womp.shellfishmod.registry;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import womp.shellfishmod.ShellfishMod;

public class ShellfishComponents {

    public static final DeferredRegister<DataComponentType<?>> COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, ShellfishMod.MOD_ID);

    public static final RegistryObject<DataComponentType<Integer>> DURABILITY_COMPONENT = COMPONENTS.register("durability", () -> new DataComponentType.Builder<Integer>().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build());

    public static void register(BusGroup bus) {
        COMPONENTS.register(bus);
        ShellfishCrayfish.register(bus);
    }
}
