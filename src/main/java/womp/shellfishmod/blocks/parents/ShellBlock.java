package womp.shellfishmod.blocks.parents;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.shape.VoxelShape;

public class ShellBlock extends DeadBlock {
    
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public ShellBlock(Settings settings, VoxelShape shape) {
        super(settings, shape, true);
        setDefaultState(this.stateManager.getDefaultState()
        .with(WATERLOGGED, false));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState)this.getDefaultState()
            .with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
    }
}
