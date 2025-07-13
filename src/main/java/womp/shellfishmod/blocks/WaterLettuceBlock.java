package womp.shellfishmod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import womp.shellfishmod.util.config.ShellfishConfig;

public class WaterLettuceBlock extends PlantBlock implements BlockEntityProvider {

    protected static final VoxelShape SHAPE = Block.createCuboidShape(2.5, -1.0, 2.5, 13.5, 0.5, 13.5);
    protected static final VoxelShape SHAPE_2D = Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 1.5, 15.0);

    public static final BooleanProperty SHOW_3D = BooleanProperty.of("is3d");
    public static final BooleanProperty IS_SWAMP = BooleanProperty.of("swamp");
    public static final BooleanProperty IS_MARSH = BooleanProperty.of("marsh");

    public WaterLettuceBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(SHOW_3D, true).with(IS_SWAMP, false).with(IS_MARSH, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(SHOW_3D).add(IS_SWAMP).add(IS_MARSH);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (world instanceof ServerWorld && entity instanceof BoatEntity) {
            world.breakBlock(new BlockPos(pos), true, entity);
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return ShellfishConfig.getShellfishGraphics() >= 1 ? SHAPE : SHAPE_2D;
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        FluidState fluidState = world.getFluidState(pos);
        FluidState fluidState2 = world.getFluidState(pos.up());
        return fluidState.getFluid() == Fluids.WATER && fluidState2.getFluid() == Fluids.EMPTY;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos var1, BlockState var2) {
        return new WaterLettuceBlockEntity(var1, var2);
    }
}
