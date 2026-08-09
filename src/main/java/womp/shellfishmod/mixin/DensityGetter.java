package womp.shellfishmod.mixin;

import java.util.List;
import java.util.Map;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LocalMobCapCalculator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LocalMobCapCalculator.class)
public interface DensityGetter {
    
    @Accessor("playerMobCounts")
    Map<ServerPlayer, Object> getPlayersToDensityCap();

    @Invoker("getPlayersNear")
    List<ServerPlayer> callGetMobSpawnablePlayers(ChunkPos chunkPos);
}
