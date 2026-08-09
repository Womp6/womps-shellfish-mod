package womp.shellfishmod.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LocalMobCapCalculator;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(NaturalSpawner.SpawnState.class)
public interface InfoInvoker {

    @Accessor("spawnableChunkCount")
    int getSpawningChunkCount();

    @Accessor("localMobCapCalculator")
    LocalMobCapCalculator getDensityCapper();
    
    @Invoker("canSpawn")
    boolean callTest(EntityType<?> type, BlockPos pos, ChunkAccess chunk);

    @Invoker("afterSpawn")
    void callRun(Mob entity, ChunkAccess chunk);

    @Invoker("canSpawnForCategoryLocal")
    boolean callCanSpawn(MobCategory group, ChunkPos chunkPos);
}
