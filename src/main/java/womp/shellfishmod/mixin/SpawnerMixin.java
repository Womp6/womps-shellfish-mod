package womp.shellfishmod.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.SpawnHelper.Checker;
import net.minecraft.world.SpawnHelper.Info;
import net.minecraft.world.SpawnHelper.Runner;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import womp.shellfishmod.registry.ShellfishWorldgen;

@Mixin(SpawnHelper.class)
public class SpawnerMixin {

    private static final int MARSH_WA_CAP = 30;

    @Accessor("SPAWNABLE_GROUPS")
    private static SpawnGroup[] getSpawnGroups() {
        throw new AssertionError();
    }

    @Inject(method = "spawn", at = @At("HEAD"), cancellable = true)
    private static void spawnWaterAmbient(ServerWorld world, WorldChunk chunk, Info info, boolean spawnAnimals, boolean spawnMonsters, boolean rareSpawn, CallbackInfo cb) {
        if (world.getBiome(chunk.getPos().getCenterAtY(63)).matchesKey(ShellfishWorldgen.MARSH)) {
            world.getProfiler().push("spawner");
            for (SpawnGroup spawnGroup : getSpawnGroups()) {
                if (!spawnAnimals && spawnGroup.isPeaceful() || !spawnMonsters && !spawnGroup.isPeaceful() || !rareSpawn && spawnGroup.isRare() || (spawnGroup == SpawnGroup.WATER_AMBIENT ? !canWaterAmbientSpawn(info, chunk.getPos()) : !((InfoInvoker)info).callIsBelowCap(spawnGroup, chunk.getPos()))) continue;
                BlockPos chunkCenter = chunk.getPos().getCenterAtY(57);
                int nearbyCount = world.getEntitiesByClass(LivingEntity.class, new Box(chunkCenter).expand(20), e -> e.getType().getSpawnGroup() == SpawnGroup.WATER_AMBIENT).size();
                if (spawnGroup == SpawnGroup.WATER_AMBIENT && nearbyCount > 1) {
                    continue;
                }
                SpawnHelper.spawnEntitiesInChunk(spawnGroup, world, chunk, ((InfoInvoker)info)::callTest, ((InfoInvoker)info)::callRun);
            }
            world.getProfiler().pop();
            cb.cancel();
        }
    }

    @Accessor("CHUNK_AREA")
    private static int getChunkArea() {
        throw new AssertionError();
    }

    private static boolean canWaterAmbientSpawn(Info info, ChunkPos pos) {
        int i = Math.max(1, MARSH_WA_CAP * ((InfoInvoker)info).getSpawningChunkCount() / getChunkArea());
        if (info.getGroupToCount().getInt(SpawnGroup.WATER_AMBIENT) >= i) return false;
        return canSpawn(SpawnGroup.WATER_AMBIENT, pos, info);
    }

    private static boolean canSpawn(SpawnGroup spawnGroup, ChunkPos chunkPos, Info info) {
        DensityGetter getter = (DensityGetter)((InfoInvoker)info).getDensityCapper();
        for (ServerPlayerEntity serverPlayerEntity : getter.callGetMobSpawnablePlayers(chunkPos)) {
            DensityCapGetter densityCap = (DensityCapGetter)getter.getPlayersToDensityCap().get(serverPlayerEntity);
            if (densityCap != null && !canSpawn(spawnGroup, densityCap.getSpawnGroupsToDensity())) continue;
            return true;
        }
        return false;
    }

    private static boolean canSpawn(SpawnGroup spawnGroup, Object2IntMap<SpawnGroup> spawnGroupsToDensity) {
        if (spawnGroup == SpawnGroup.WATER_AMBIENT) {
            return spawnGroupsToDensity.getOrDefault((Object)spawnGroup, 0) < MARSH_WA_CAP;
        }
        return spawnGroupsToDensity.getOrDefault((Object)spawnGroup, 0) < spawnGroup.getCapacity();
    }
}
