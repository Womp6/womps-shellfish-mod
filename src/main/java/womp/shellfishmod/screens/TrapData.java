package womp.shellfishmod.screens;

import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public record TrapData(BlockPos pos) {

    public static final StreamCodec<RegistryFriendlyByteBuf, TrapData> PACKET_CODEC = StreamCodec.composite(
        BlockPos.STREAM_CODEC,
        TrapData::pos,
        TrapData::new
    );
}
