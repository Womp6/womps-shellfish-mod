package womp.shellfishmod.feature;

import java.util.List;
import java.util.Optional;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.BlockStateProviderType;
import womp.shellfishmod.registry.ShellfishFeatures;

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
    protected BlockStateProviderType<?> getType() {
        return ShellfishFeatures.CONDITIONAL_STATE_PROVIDER;
    }

    public Optional<BlockState> get(StructureWorldAccess world, BlockPos pos, Random random) {
        for (ConditionedStatePool entry : entries) {
            if (entry.condition().test(world, pos)) {
                return Optional.of(entry.pool().getDataOrEmpty(random)
                    .orElseThrow(() -> new IllegalStateException("Empty state pool for condition")));
            }
        }
        return Optional.empty();
    }

    @Override
    public BlockState get(Random random, BlockPos pos) {
        throw new UnsupportedOperationException("Use get(world, pos, random) instead");
    }
}
