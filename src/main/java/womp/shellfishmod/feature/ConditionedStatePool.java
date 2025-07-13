package womp.shellfishmod.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;

public record ConditionedStatePool(BlockPredicate condition, SimpleWeightedRandomList<BlockState> pool) {

    public static final Codec<ConditionedStatePool> CONDITIONED_STATE_POOL_CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    BlockPredicate.CODEC.fieldOf("condition").forGetter(ConditionedStatePool::condition),
                    SimpleWeightedRandomList.wrappedCodec(BlockState.CODEC).fieldOf("states").forGetter(ConditionedStatePool::pool)
            ).apply(instance, ConditionedStatePool::new));
}
