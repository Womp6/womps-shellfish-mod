package womp.shellfishmod.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import womp.shellfishmod.util.config.ShellfishConfig;

public class WaterLettuceBlock extends BushBlock implements EntityBlock {

    protected static final VoxelShape SHAPE = Block.box(2.5, -1.0, 2.5, 13.5, 0.5, 13.5);
    protected static final VoxelShape SHAPE_2D = Block.box(1.0, 0.0, 1.0, 15.0, 1.5, 15.0);

    public static final BooleanProperty SHOW_3D = BooleanProperty.create("is3d");
    public static final BooleanProperty IS_SWAMP = BooleanProperty.create("swamp");
    public static final BooleanProperty IS_MARSH = BooleanProperty.create("marsh");

    public WaterLettuceBlock(BlockBehaviour.Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(SHOW_3D, true).setValue(IS_SWAMP, false).setValue(IS_MARSH, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(SHOW_3D).add(IS_SWAMP).add(IS_MARSH);
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        super.entityInside(pState, pLevel, pPos, pEntity);
        if (pLevel instanceof ServerLevel && pEntity instanceof Boat) {
            pLevel.destroyBlock(new BlockPos(pPos), true, pEntity);
        }
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return ShellfishConfig.getShellfishGraphics() >= 1 ? SHAPE : SHAPE_2D;
    }

    @Override
    protected boolean mayPlaceOn(BlockState floor, BlockGetter world, BlockPos pos) {
        FluidState fluidState = world.getFluidState(pos.below());
        FluidState fluidState2 = world.getFluidState(pos);
        return fluidState.getType() == Fluids.WATER && fluidState2.getType() == Fluids.EMPTY;
    }

    @Override
    public boolean canSurvive(BlockState floor, LevelReader world, BlockPos pos) {
        FluidState fluidState = world.getFluidState(pos.below());
        FluidState fluidState2 = world.getFluidState(pos);
        return fluidState.getType() == Fluids.WATER && fluidState2.getType() == Fluids.EMPTY;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos var1, BlockState var2) {
        return new WaterLettuceBlockEntity(var1, var2);
    }
}
