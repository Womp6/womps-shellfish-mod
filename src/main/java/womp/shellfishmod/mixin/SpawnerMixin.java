package womp.shellfishmod.mixin;

import java.util.Iterator;
import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.profiler.Profilers;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.SpawnHelper.Info;
import net.minecraft.world.chunk.WorldChunk;
import womp.shellfishmod.registry.ShellfishWorldgen;

@Mixin(SpawnHelper.class)
public class SpawnerMixin {

    private static final int MARSH_WA_CAP = 30;

    @Inject(method = "spawn", at = @At("HEAD"), cancellable = true)
    private static void spawnWaterAmbient(ServerWorld world, WorldChunk chunk, Info info, List<SpawnGroup> spawnableGroups, CallbackInfo cb) {
        if (world.getBiome(chunk.getPos().getCenterAtY(63)).matchesKey(ShellfishWorldgen.MARSH)) {
            if (waterAmbientBelowCap(info) && !spawnableGroups.contains(SpawnGroup.WATER_AMBIENT)) spawnableGroups.add(SpawnGroup.WATER_AMBIENT);
            Profiler profiler = Profilers.get();
            profiler.push("spawner");
            Iterator<SpawnGroup> groups = spawnableGroups.iterator();
            
            while (groups.hasNext()) {
                SpawnGroup group = groups.next();
                if (group == SpawnGroup.WATER_AMBIENT ? canWaterAmbientSpawn(info, chunk.getPos()) : ((InfoInvoker)info).callCanSpawn(group, chunk.getPos())) {
                    BlockPos chunkCenter = chunk.getPos().getCenterAtY(57);
                    int nearbyCount = world.getEntitiesByClass(LivingEntity.class, new Box(chunkCenter).expand(20), e -> e.getType().getSpawnGroup() == SpawnGroup.WATER_AMBIENT).size();
                    if (group == SpawnGroup.WATER_AMBIENT && nearbyCount > 1) {
                        continue;
                    }
                    SpawnHelper.spawnEntitiesInChunk(group, world, chunk, ((InfoInvoker)info)::callTest, ((InfoInvoker)info)::callRun);
                }
            }
            profiler.pop();
            cb.cancel();
        }
    }

    @Accessor("CHUNK_AREA")
    private static int getChunkArea() {
        throw new AssertionError();
    }

    private static boolean canWaterAmbientSpawn(Info info, ChunkPos chunkPos) {
        DensityGetter capper = (DensityGetter)((InfoInvoker)info).getDensityCapper();
        Iterator<ServerPlayerEntity> var3 = ((DensityGetter)capper).callGetMobSpawnablePlayers(chunkPos).iterator();

        DensityCapGetter densityCap;
        do {
            if (!var3.hasNext()) {
                return false;
            }

            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)var3.next();
            densityCap = (DensityCapGetter)capper.getPlayersToDensityCap().get(serverPlayerEntity);
        } while(densityCap != null && !capCanSpawn(densityCap));

        return true;
    }

    private static boolean capCanSpawn(DensityCapGetter cap) {
        return cap.getSpawnGroupsToDensity().getOrDefault(SpawnGroup.WATER_AMBIENT, 0) < MARSH_WA_CAP;
    }

    private static boolean waterAmbientBelowCap(Info info) {
        int i = MARSH_WA_CAP * ((InfoInvoker)info).getSpawningChunkCount() / getChunkArea();
		return info.getGroupToCount().getInt(SpawnGroup.WATER_AMBIENT) < i;
    }
}
