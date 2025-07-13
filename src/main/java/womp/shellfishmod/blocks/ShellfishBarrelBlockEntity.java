package womp.shellfishmod.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import womp.shellfishmod.registry.ShellfishBlocks;

public class ShellfishBarrelBlockEntity extends BarrelBlockEntity {

    public ShellfishBarrelBlockEntity(BlockPos pos, BlockState blockState) {
        super(pos, blockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return ShellfishBlocks.BARREL_BE_NO_POI.get();
    }
}
