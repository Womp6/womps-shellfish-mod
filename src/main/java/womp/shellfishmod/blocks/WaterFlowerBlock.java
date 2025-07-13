package womp.shellfishmod.blocks;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import womp.shellfishmod.blocks.parents.ShellfishLandPlantBlock;

public class WaterFlowerBlock extends ShellfishLandPlantBlock {

    public static final BooleanProperty BLOOMED = BooleanProperty.of("bloomed");

    public WaterFlowerBlock(Settings settings, Block tallPlantBlock) {
        super(settings, false, tallPlantBlock);
        this.setDefaultState(getDefaultState().with(BLOOMED, false));
    }

    @Override
    protected void appendProperties(Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(BLOOMED);
    }
    
    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = super.getPlacementState(ctx);
        return state == null ? null : state.with(BLOOMED, false);
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        if (!state.get(BLOOMED)) {
            world.setBlockState(pos, state.with(BLOOMED, true));
        } else {
            super.grow(world, random, pos, state);
        }
    }
}
