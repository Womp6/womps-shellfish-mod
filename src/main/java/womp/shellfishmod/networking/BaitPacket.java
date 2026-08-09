package womp.shellfishmod.networking;

import java.util.List;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import womp.shellfishmod.blocks.parents.AbstractTrapBlockEntity;

public record BaitPacket(List<ItemStack> stacks, BlockPos pos) implements CustomPacketPayload {

    public static final Type<BaitPacket> ID = new Type<>(Identifier.fromNamespaceAndPath("shellfish", "bait_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, BaitPacket> PACKET = StreamCodec.composite(ItemStack.OPTIONAL_LIST_STREAM_CODEC, BaitPacket::stacks, BlockPos.STREAM_CODEC, BaitPacket::pos, BaitPacket::new);

    public static void receive(BaitPacket payload, ClientPlayNetworking.Context context) {
        BlockPos position = payload.pos;
        List<ItemStack> old = payload.stacks;
        int size = old.size();
        NonNullList<ItemStack> itemStacks = NonNullList.withSize(size, ItemStack.EMPTY);
        for (int i = 0; i < size; i++) {
            itemStacks.set(i, payload.stacks.get(i));
        }
        if (context.client().level.getBlockEntity(position) instanceof AbstractTrapBlockEntity trap) {
            trap.setInventory(itemStacks);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
