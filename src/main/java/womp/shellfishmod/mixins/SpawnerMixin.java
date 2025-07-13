package womp.shellfishmod.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import womp.shellfishmod.registry.ShellfishWorldgen;

import java.util.Iterator;
import java.util.List;

@Mixin(NaturalSpawner.class)
public class SpawnerMixin {

    private static final int MARSH_WA_CAP = 30;

    @Inject(method = "spawnForChunk", at = @At("HEAD"), cancellable = true)
    private static void spawnWaterAmbient(ServerLevel world, LevelChunk chunk, NaturalSpawner.SpawnState info, List<MobCategory> spawnableGroups, CallbackInfo cb) {
        if (world.getBiome(chunk.getPos().getMiddleBlockPosition(63)).is(ShellfishWorldgen.MARSH)) {
            if (waterAmbientBelowCap(info) && !spawnableGroups.contains(MobCategory.WATER_AMBIENT)) spawnableGroups.add(MobCategory.WATER_AMBIENT);
            ProfilerFiller profiler = Profiler.get();
            profiler.push("spawner");
            Iterator<MobCategory> groups = spawnableGroups.iterator();

            while (groups.hasNext()) {
                MobCategory group = groups.next();
                if (group == MobCategory.WATER_AMBIENT ? canWaterAmbientSpawn(info, chunk.getPos()) : ((InfoInvoker)info).callCanSpawn(group, chunk.getPos())) {
                    BlockPos chunkCenter = chunk.getPos().getMiddleBlockPosition(57);
                    int nearbyCount = world.getEntitiesOfClass(LivingEntity.class, new AABB(chunkCenter).inflate(20), e -> e.getType().getCategory() == MobCategory.WATER_AMBIENT).size();
                    if (group == MobCategory.WATER_AMBIENT && nearbyCount > 1) {
                        continue;
                    }
                    NaturalSpawner.spawnCategoryForChunk(group, world, chunk, ((InfoInvoker)info)::callTest, ((InfoInvoker)info)::callRun);
                }
            }
            profiler.pop();
            cb.cancel();
        }
    }

    @Accessor("MAGIC_NUMBER")
    private static int getChunkArea() {
        throw new AssertionError();
    }

    private static boolean canWaterAmbientSpawn(NaturalSpawner.SpawnState info, ChunkPos chunkPos) {
        DensityGetter capper = (DensityGetter)((InfoInvoker)info).getDensityCapper();
        Iterator<ServerPlayer> var3 = ((DensityGetter)capper).callGetMobSpawnablePlayers(chunkPos).iterator();

        DensityCapGetter densityCap;
        do {
            if (!var3.hasNext()) {
                return false;
            }

            ServerPlayer serverPlayerEntity = (ServerPlayer) var3.next();
            densityCap = (DensityCapGetter)capper.getPlayersToDensityCap().get(serverPlayerEntity);
        } while(densityCap != null && !capCanSpawn(densityCap));

        return true;
    }

    private static boolean capCanSpawn(DensityCapGetter cap) {
        return cap.getSpawnGroupsToDensity().getOrDefault(MobCategory.WATER_AMBIENT, 0) < MARSH_WA_CAP;
    }

    private static boolean waterAmbientBelowCap(NaturalSpawner.SpawnState info) {
        int i = MARSH_WA_CAP * ((InfoInvoker)info).getSpawningChunkCount() / getChunkArea();
        return info.getMobCategoryCounts().getInt(MobCategory.WATER_AMBIENT) < i;
    }
}
