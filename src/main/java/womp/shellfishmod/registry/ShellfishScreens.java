package womp.shellfishmod.registry;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType.ExtendedFactory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import womp.shellfishmod.screens.ShellfishTrapScreenHandler;

public class ShellfishScreens {
    
    public static final ScreenHandlerType<ShellfishTrapScreenHandler> SHELLFISH_TRAP_SCREEN_HANDLER = register("shellfish_trap_screen_handler", ShellfishTrapScreenHandler::new);


    private static <T extends ScreenHandler> ScreenHandlerType<T> register(String name, ExtendedFactory<T> factory) {
        return Registry.register(Registries.SCREEN_HANDLER, new Identifier("shellfish", name), new ExtendedScreenHandlerType<>(factory));
    }

    public static void register() {}
}
