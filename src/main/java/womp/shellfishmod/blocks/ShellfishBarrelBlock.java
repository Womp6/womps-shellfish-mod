package womp.shellfishmod.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

public class ShellfishBarrelBlock extends BarrelBlock {

    public ShellfishBarrelBlock(Properties p_49046_) {
        super(p_49046_);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ShellfishBarrelBlockEntity(pPos, pState);
    }

    @Override
    protected ItemStack getCloneItemStack(LevelReader pLevel, BlockPos pPos, BlockState pState, boolean pIncludeData) {
        return new ItemStack(Blocks.BARREL);
    }
}
