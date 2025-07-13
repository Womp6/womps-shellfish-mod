package womp.shellfishmod.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.block.BlockState;
import net.minecraft.util.collection.DataPool;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;

public record ConditionedStatePool(BlockPredicate condition, DataPool<BlockState> pool) {
    
    public static final Codec<ConditionedStatePool> CONDITIONED_STATE_POOL_CODEC =
    RecordCodecBuilder.create(instance -> instance.group(
        BlockPredicate.BASE_CODEC.fieldOf("condition").forGetter(c -> c.condition()),
        DataPool.createCodec(BlockState.CODEC).fieldOf("states").forGetter(c -> c.pool())
    ).apply(instance, ConditionedStatePool::new));
}
