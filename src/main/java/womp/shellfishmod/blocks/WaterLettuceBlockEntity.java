package womp.shellfishmod.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import womp.shellfishmod.blocks.parents.ShellfishPlantBlockEntity3d;
import womp.shellfishmod.registry.ShellfishBlocks;

public class WaterLettuceBlockEntity extends ShellfishPlantBlockEntity3d {

    public boolean swamp;

    public WaterLettuceBlockEntity(BlockPos pos, BlockState state) {
        super(ShellfishBlocks.WATER_LETTUCE_BLOCK_ENTITY, pos, state);
    }

    public boolean isSwamp() {
        return swamp;
    }

    public void setSwamp(boolean swamp) {
        this.swamp = swamp;

        if (world != null) {
            BlockState state = world.getBlockState(pos);
            if (state.getBlock() instanceof WaterLettuceBlock) {
                world.setBlockState(pos, state.with(WaterLettuceBlock.IS_SWAMP, this.swamp));
            }
        }
    }
}
