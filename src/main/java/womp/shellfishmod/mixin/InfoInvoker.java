package womp.shellfishmod.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.SpawnDensityCapper;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.chunk.Chunk;

@Mixin(SpawnHelper.Info.class)
public interface InfoInvoker {

    @Accessor("spawningChunkCount")
    int getSpawningChunkCount();

    @Accessor("densityCapper")
    SpawnDensityCapper getDensityCapper();
    
    @Invoker("test")
    boolean callTest(EntityType<?> type, BlockPos pos, Chunk chunk);

    @Invoker("run")
    void callRun(MobEntity entity, Chunk chunk);

    @Invoker("isBelowCap")
    boolean callIsBelowCap(SpawnGroup group, ChunkPos chunkPos);
}
