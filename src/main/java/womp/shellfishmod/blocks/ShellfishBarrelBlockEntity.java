package womp.shellfishmod.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import womp.shellfishmod.registry.ShellfishBlocks;

public class ShellfishBarrelBlockEntity extends BarrelBlockEntity {

    public ShellfishBarrelBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }
    
    @Override
    public BlockEntityType<?> getType() {
        return ShellfishBlocks.BARREL_BE_NO_POI;
    }

    @Override
    public boolean supports(BlockState state) {
        return state.isOf(ShellfishBlocks.BARREL_NO_POI) || super.supports(state);
    }
}
