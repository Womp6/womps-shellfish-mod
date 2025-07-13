package womp.shellfishmod.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import womp.shellfishmod.registry.ShellfishBlocks;

import javax.annotation.Nullable;

// This is solely for a no POI barrel in the trapper hut
public class ShellfishBarrelBlockEntity extends BarrelBlockEntity {

    public ShellfishBarrelBlockEntity(BlockPos pos, BlockState blockState) {
        super(pos, blockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return ShellfishBlocks.BARREL_BE_NO_POI.get();
    }

    @Override
    public boolean isValidBlockState(BlockState p_345570_) {
        return p_345570_.is(ShellfishBlocks.BARREL_NO_POI.get()) || super.isValidBlockState(p_345570_);
    }
}
