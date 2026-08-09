package womp.shellfishmod.registry;

import net.fabricmc.fabric.api.menu.v1.ExtendedMenuType;
import net.fabricmc.fabric.api.menu.v1.ExtendedMenuType.ExtendedFactory;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import womp.shellfishmod.screens.ShellfishTrapScreenHandler;
import womp.shellfishmod.screens.TrapData;

public class ShellfishScreens {
    
    public static final MenuType<ShellfishTrapScreenHandler> SHELLFISH_TRAP_SCREEN_HANDLER = register("shellfish_trap_screen_handler", ShellfishTrapScreenHandler::new, TrapData.PACKET_CODEC);


    private static <T extends AbstractContainerMenu, D> MenuType<T> register(String name, ExtendedFactory<T, D> factory, StreamCodec<RegistryFriendlyByteBuf, D> codec) {
        return Registry.register(BuiltInRegistries.MENU, Identifier.fromNamespaceAndPath("shellfish", name), new ExtendedMenuType<>(factory, codec));
    }

    public static void register() {}
}
