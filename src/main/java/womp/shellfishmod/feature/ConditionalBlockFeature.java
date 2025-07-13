package womp.shellfishmod.feature;

import java.util.Optional;

import com.mojang.serialization.Codec;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class ConditionalBlockFeature extends Feature<SimpleBlockFeatureConfig> {

    public ConditionalBlockFeature(Codec<SimpleBlockFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<SimpleBlockFeatureConfig> context) {
        SimpleBlockFeatureConfig config = context.getConfig();
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();
        Random random = context.getRandom();

        BlockStateProvider provider = config.toPlace();
        BlockState state;

        if (provider instanceof ConditionalBlockStateProvider conditional) {
            Optional<BlockState> isState = conditional.get(world, pos, random);
            if (isState.isPresent()) state = isState.get();
            else return false;
        } else {
            state = provider.get(random, pos);
        }

        if (!state.canPlaceAt(world, pos)) return false;

        if (state.getBlock() instanceof TallPlantBlock) {
            if (!world.isAir(pos.up())) return false;
            TallPlantBlock.placeAt(world, state, pos, Block.NOTIFY_LISTENERS);
        } else {
            world.setBlockState(pos, state, Block.NOTIFY_LISTENERS);
        }

        return true;
    }
}
