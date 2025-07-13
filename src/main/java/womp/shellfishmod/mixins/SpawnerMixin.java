package womp.shellfishmod.mixins;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import womp.shellfishmod.ShellfishMod;
import womp.shellfishmod.registry.ShellfishWorldgen;

@Mixin(NaturalSpawner.class)
public class SpawnerMixin {

    private static final int MARSH_WA_CAP = 30;

    @Accessor("SPAWNING_CATEGORIES")
    private static MobCategory[] getSpawnGroups() {
        throw new AssertionError();
    }

    @Inject(method = "spawnForChunk", at = @At("HEAD"), cancellable = true)
    private static void spawnWaterAmbient(ServerLevel world, LevelChunk chunk, NaturalSpawner.SpawnState info, boolean spawnAnimals, boolean spawnMonsters, boolean rareSpawn, CallbackInfo cb) {
        if (ShellfishMod.loaded) {
            if (world.getBiome(chunk.getPos().getMiddleBlockPosition(63)).is(ShellfishWorldgen.MARSH)) {
                world.getProfiler().push("spawner");
                for (MobCategory spawnGroup : getSpawnGroups()) {
                    if (!spawnAnimals && spawnGroup.isFriendly() || !spawnMonsters && !spawnGroup.isFriendly() || !rareSpawn && spawnGroup.isPersistent() || (spawnGroup == MobCategory.WATER_AMBIENT ? !canWaterAmbientSpawn(info, chunk.getPos()) : !((InfoInvoker) info).callIsBelowCap(spawnGroup, chunk.getPos())))
                        continue;
                    BlockPos chunkCenter = chunk.getPos().getMiddleBlockPosition(57);
                    int nearbyCount = world.getEntitiesOfClass(LivingEntity.class, new AABB(chunkCenter).inflate(20), e -> e.getType().getCategory() == MobCategory.WATER_AMBIENT).size();
                    if (spawnGroup == MobCategory.WATER_AMBIENT && nearbyCount > 1) {
                        continue;
                    }
                    NaturalSpawner.spawnCategoryForChunk(spawnGroup, world, chunk, ((InfoInvoker) info)::callTest, ((InfoInvoker) info)::callRun);
                }
                world.getProfiler().pop();
                cb.cancel();
            }
        }
    }

    @Accessor("MAGIC_NUMBER")
    private static int getChunkArea() {
        throw new AssertionError();
    }

    private static boolean canWaterAmbientSpawn(NaturalSpawner.SpawnState info, ChunkPos pos) {
        int i = Math.max(1, MARSH_WA_CAP * ((InfoInvoker)info).getSpawningChunkCount() / getChunkArea());
        if (info.getMobCategoryCounts().getInt(MobCategory.WATER_AMBIENT) >= i) return false;
        return canSpawn(MobCategory.WATER_AMBIENT, pos, info);
    }

    private static boolean canSpawn(MobCategory spawnGroup, ChunkPos chunkPos, NaturalSpawner.SpawnState info) {
        DensityGetter getter = (DensityGetter)((InfoInvoker)info).getDensityCapper();
        for (ServerPlayer serverPlayerEntity : getter.callGetMobSpawnablePlayers(chunkPos)) {
            DensityCapGetter densityCap = (DensityCapGetter) getter.getPlayersToDensityCap().get(serverPlayerEntity);
            if (densityCap != null && !canSpawn(spawnGroup, densityCap.getSpawnGroupsToDensity())) continue;
            return true;
        }
        return false;
    }

    private static boolean canSpawn(MobCategory spawnGroup, Object2IntMap<MobCategory> spawnGroupsToDensity) {
        if (spawnGroup == MobCategory.WATER_AMBIENT) {
            return spawnGroupsToDensity.getOrDefault((Object)spawnGroup, 0) < MARSH_WA_CAP;
        }
        return spawnGroupsToDensity.getOrDefault((Object)spawnGroup, 0) < spawnGroup.getMaxInstancesPerChunk();
    }
}
