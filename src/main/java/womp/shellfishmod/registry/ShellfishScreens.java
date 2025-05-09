package womp.shellfishmod.registry;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType.ExtendedFactory;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import womp.shellfishmod.screens.ShellfishTrapScreenHandler;
import womp.shellfishmod.screens.TrapData;

public class ShellfishScreens {
    
    public static final ScreenHandlerType<ShellfishTrapScreenHandler> SHELLFISH_TRAP_SCREEN_HANDLER = register("shellfish_trap_screen_handler", ShellfishTrapScreenHandler::new, TrapData.PACKET_CODEC);


    private static <T extends ScreenHandler, D> ScreenHandlerType<T> register(String name, ExtendedFactory<T, D> factory, PacketCodec<RegistryByteBuf, D> codec) {
        return Registry.register(Registries.SCREEN_HANDLER, Identifier.of("shellfish", name), new ExtendedScreenHandlerType<>(factory, codec));
    }

    public static void register() {}
}
