package womp.shellfishmod.blocks.parents;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import womp.shellfishmod.blocks.SeaLettuceBlock;
import womp.shellfishmod.blocks.WaterLettuceBlock;

public class ShellfishPlantBlockEntity3d extends BlockEntity {

    public boolean is3d;

    public ShellfishPlantBlockEntity3d(BlockEntityType<? extends ShellfishPlantBlockEntity3d> entity, BlockPos pos, BlockState state) {
        super(entity, pos, state);
    }

    public boolean is3d() {
        return is3d;
    }

    public void set3d(boolean is3d) {
        this.is3d = is3d;

        if (level != null) {
            BlockState state = level.getBlockState(worldPosition);
            if (state.getBlock() instanceof SeaLettuceBlock) {
                level.setBlockAndUpdate(worldPosition, state.setValue(SeaLettuceBlock.SHOW_3D, this.is3d));
            } else if (state.getBlock() instanceof WaterLettuceBlock) {
                level.setBlockAndUpdate(worldPosition, state.setValue(WaterLettuceBlock.SHOW_3D, this.is3d));
            }
        }
    }
}
