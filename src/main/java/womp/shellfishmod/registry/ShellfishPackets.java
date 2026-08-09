package womp.shellfishmod.registry;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import womp.shellfishmod.networking.BaitPacket;

public class ShellfishPackets {
    
    public static void registerClient() {
        ClientPlayNetworking.registerGlobalReceiver(BaitPacket.ID, BaitPacket::receive);
    }

    public static void register() {
        PayloadTypeRegistry.clientboundPlay().register(BaitPacket.ID, BaitPacket.PACKET);
    }
}
