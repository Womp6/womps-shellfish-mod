package womp.shellfishmod.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import womp.shellfishmod.registry.ShellfishFeatures;

import java.util.List;
import java.util.Optional;

public class ConditionalBlockStateProvider extends BlockStateProvider {

    public static final MapCodec<ConditionalBlockStateProvider> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        ConditionedStatePool.CONDITIONED_STATE_POOL_CODEC.listOf().fieldOf("entries")
            .forGetter(p -> p.entries)
        ).apply(instance, ConditionalBlockStateProvider::new));

    private final List<ConditionedStatePool> entries;

    public ConditionalBlockStateProvider(List<ConditionedStatePool> entries) {
        this.entries = entries;
    }

    @Override
    protected BlockStateProviderType<?> type() {
        return ShellfishFeatures.CONDITIONAL_STATE_PROVIDER.get();
    }

    public Optional<BlockState> get(WorldGenLevel world, BlockPos pos, RandomSource random) {
        for (ConditionedStatePool entry : entries) {
            if (entry.condition().test(world, pos)) {
                return Optional.of(entry.pool().getRandom(random)
                        .orElseThrow(() -> new IllegalStateException("Empty state pool for condition")));
            }
        }
        return Optional.empty();
    }

    @Override
    public BlockState getState(RandomSource random, BlockPos pos) {
        throw new UnsupportedOperationException("Use get(world, pos, random) instead");
    }
}
