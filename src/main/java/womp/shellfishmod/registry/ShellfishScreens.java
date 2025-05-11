package womp.shellfishmod.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import womp.shellfishmod.ShellfishMod;
import womp.shellfishmod.screens.ShellfishTrapScreenHandler;

public class ShellfishScreens {

    public static final DeferredRegister<MenuType<?>> SCREEN_HANDLERS = DeferredRegister.create(Registries.MENU, ShellfishMod.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<ShellfishTrapScreenHandler>> SHELLFISH_TRAP_SCREEN_HANDLER = SCREEN_HANDLERS.register("shellfish_trap_screen_handler", () -> IMenuTypeExtension.create(ShellfishTrapScreenHandler::new));

    public static void register(IEventBus bus) {
        SCREEN_HANDLERS.register(bus);
    }
}
