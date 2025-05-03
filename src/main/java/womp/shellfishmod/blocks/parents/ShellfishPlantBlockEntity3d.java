package womp.shellfishmod.blocks.parents;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
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

        if (world != null) {
            BlockState state = world.getBlockState(pos);
            if (state.getBlock() instanceof SeaLettuceBlock) {
                world.setBlockState(pos, state.with(SeaLettuceBlock.SHOW_3D, this.is3d));
            } else if (state.getBlock() instanceof WaterLettuceBlock) {
                world.setBlockState(pos, state.with(WaterLettuceBlock.SHOW_3D, this.is3d));
            }
        }
    }
}
