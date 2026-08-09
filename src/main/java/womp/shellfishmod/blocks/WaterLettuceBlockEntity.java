package womp.shellfishmod.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import womp.shellfishmod.blocks.parents.ShellfishPlantBlockEntity3d;
import womp.shellfishmod.registry.ShellfishBlocks;

public class WaterLettuceBlockEntity extends ShellfishPlantBlockEntity3d {

    public boolean swamp, marsh;

    public WaterLettuceBlockEntity(BlockPos pos, BlockState state) {
        super(ShellfishBlocks.WATER_LETTUCE_BLOCK_ENTITY, pos, state);
    }

    public boolean isSwamp() {
        return swamp;
    }

    public boolean isMarsh() {
        return marsh;
    }

    public void setSwamp(boolean swamp) {
        this.swamp = swamp;

        if (level != null) {
            BlockState state = level.getBlockState(worldPosition);
            if (state.getBlock() instanceof WaterLettuceBlock) {
                level.setBlockAndUpdate(worldPosition, state.setValue(WaterLettuceBlock.IS_SWAMP, this.swamp));
            }
        }
    }

    public void setMarsh(boolean marsh) {
        this.marsh = marsh;

        if (level != null) {
            BlockState state = level.getBlockState(worldPosition);
            if (state.getBlock() instanceof WaterLettuceBlock) {
                level.setBlockAndUpdate(worldPosition, state.setValue(WaterLettuceBlock.IS_MARSH, this.marsh));
            }
        }
    }
}
