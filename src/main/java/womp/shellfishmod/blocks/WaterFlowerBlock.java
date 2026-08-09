package womp.shellfishmod.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;
import womp.shellfishmod.blocks.parents.ShellfishLandPlantBlock;

public class WaterFlowerBlock extends ShellfishLandPlantBlock {

    public static final BooleanProperty BLOOMED = BooleanProperty.create("bloomed");

    public WaterFlowerBlock(Properties settings, Block tallPlantBlock) {
        super(settings, false, tallPlantBlock);
        this.registerDefaultState(defaultBlockState().setValue(BLOOMED, false));
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BLOOMED);
    }
    
    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState state = super.getStateForPlacement(ctx);
        return state == null ? null : state.setValue(BLOOMED, false);
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        if (!state.getValue(BLOOMED)) {
            world.setBlockAndUpdate(pos, state.setValue(BLOOMED, true));
        } else {
            super.performBonemeal(world, random, pos, state);
        }
    }
}
