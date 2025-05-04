package womp.shellfishmod.networking;

import java.util.List;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import womp.shellfishmod.blocks.parents.AbstractTrapBlockEntity;

public record BaitPacket(List<ItemStack> stacks, BlockPos pos) implements CustomPayload {

    public static final Id<BaitPacket> ID = new Id<>(new Identifier("shellfish", "bait_packet"));
    public static final PacketCodec<RegistryByteBuf, BaitPacket> PACKET = PacketCodec.tuple(ItemStack.OPTIONAL_LIST_PACKET_CODEC, BaitPacket::stacks, BlockPos.PACKET_CODEC, BaitPacket::pos, BaitPacket::new);

    public static void receive(BaitPacket payload, ClientPlayNetworking.Context context) {
        BlockPos position = payload.pos;
        List<ItemStack> old = payload.stacks;
        int size = old.size();
        DefaultedList<ItemStack> itemStacks = DefaultedList.ofSize(size, ItemStack.EMPTY);
        for (int i = 0; i < size; i++) {
            itemStacks.set(i, payload.stacks.get(i));
        }
        if (context.client().world.getBlockEntity(position) instanceof AbstractTrapBlockEntity trap) {
            trap.setInventory(itemStacks);
        }
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
