package womp.shellfishmod.client.registry;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;
import womp.shellfishmod.networking.ItemStackSC2Packet;

public class ShellfishMessages {
    
    public static final Identifier ITEM_SYNC = new Identifier("shellfish", "item_sync");

    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(ITEM_SYNC, ItemStackSC2Packet::receive);
    }
}
