package womp.shellfishmod.feature;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import com.mojang.serialization.Codec;

public class ConditionalBlockFeature extends Feature<SimpleBlockConfiguration> {

    public ConditionalBlockFeature(Codec<SimpleBlockConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> context) {
        SimpleBlockConfiguration config = context.config();
        WorldGenLevel world = context.level();
        BlockPos pos = context.origin();
        RandomSource random = context.random();

        BlockStateProvider provider = config.toPlace();
        BlockState state;

        if (provider instanceof ConditionalBlockStateProvider conditional) {
            Optional<BlockState> isState = conditional.get(world, pos, random);
            if (isState.isPresent()) state = isState.get();
            else return false;
        } else {
            state = provider.getState(world, random, pos);
        }

        if (!state.canSurvive(world, pos)) return false;

        if (state.getBlock() instanceof DoublePlantBlock) {
            if (!world.isEmptyBlock(pos.above())) return false;
            DoublePlantBlock.placeAt(world, state, pos, Block.UPDATE_CLIENTS);
        } else {
            world.setBlock(pos, state, Block.UPDATE_CLIENTS);
        }

        return true;
    }
}
