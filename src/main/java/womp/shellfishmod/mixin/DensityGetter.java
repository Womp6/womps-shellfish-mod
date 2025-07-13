package womp.shellfishmod.mixin;

import java.util.List;
import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.SpawnDensityCapper;

@Mixin(SpawnDensityCapper.class)
public interface DensityGetter {
    
    @Accessor("playersToDensityCap")
    Map<ServerPlayerEntity, Object> getPlayersToDensityCap();

    @Invoker("getMobSpawnablePlayers")
    List<ServerPlayerEntity> callGetMobSpawnablePlayers(ChunkPos chunkPos);
}
