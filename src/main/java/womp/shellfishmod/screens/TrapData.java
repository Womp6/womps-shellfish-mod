package womp.shellfishmod.screens;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.util.math.BlockPos;

public record TrapData(BlockPos pos) {

    public static final PacketCodec<RegistryByteBuf, TrapData> PACKET_CODEC = PacketCodec.tuple(
        BlockPos.PACKET_CODEC,
        TrapData::pos,
        TrapData::new
    );
}
