package womp.shellfishmod.registry;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import womp.shellfishmod.ShellfishMod;
import womp.shellfishmod.screens.ShellfishTrapScreenHandler;

public class ShellfishScreens {

    public static final DeferredRegister<MenuType<?>> SCREEN_HANDLERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, ShellfishMod.MOD_ID);

    public static final RegistryObject<MenuType<ShellfishTrapScreenHandler>> SHELLFISH_TRAP_SCREEN_HANDLER = SCREEN_HANDLERS.register("shellfish_trap_screen_handler", () -> IForgeMenuType.create(ShellfishTrapScreenHandler::new));

    public static void register(BusGroup bus) {
        SCREEN_HANDLERS.register(bus);
    }
}
