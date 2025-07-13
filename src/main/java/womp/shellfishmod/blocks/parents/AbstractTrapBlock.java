package womp.shellfishmod.blocks.parents;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import womp.shellfishmod.registry.ShellfishComponents;
import womp.shellfishmod.registry.ShellfishSounds;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;

// Block entity code made using help from Kaupenjoe
public abstract class AbstractTrapBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {

    //REQUIRED
    protected abstract HashMap<Item, Integer> getRepairItems();
    protected abstract BlockEntityType<? extends AbstractTrapBlockEntity> getBE();
    protected abstract int getMaxDurability();


    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty BROKEN = BooleanProperty.create("broken");

    public AbstractTrapBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false).setValue(BROKEN, false));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER).setValue(BROKEN, false);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(WATERLOGGED, FACING, BROKEN);
    }

    @Override
    public @NotNull FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess view, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        BlockState blockState = super.updateShape(state, world, view, pos, direction, neighborPos, neighborState, random);
        if (!blockState.isAir()) {
            view.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }
        return blockState;
    }

    @Override
    public boolean skipRendering(BlockState pState, BlockState pAdjacentBlockState, Direction pSide) {
        return pAdjacentBlockState.is(this) ? true : super.skipRendering(pState, pAdjacentBlockState, pSide);
    }

    @Override
    public @NotNull VoxelShape getVisualShape(BlockState pState, BlockGetter pReader, BlockPos pPos, CollisionContext pContext) {
        return Shapes.empty();
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof AbstractTrapBlockEntity) {
                Containers.dropContents(world, pos, (AbstractTrapBlockEntity)blockEntity);
                world.updateNeighbourForOutputSignal(pos,this);
            }
            super.onRemove(state, world, pos, newState, moved);
        }
    }

    @Override
    public InteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!world.isClientSide) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof AbstractTrapBlockEntity trap) {
                if (hand == InteractionHand.MAIN_HAND && getRepairItems().containsKey(stack.getItem()) && trap.getDurability() < trap.getMaxDurability()) {
                    if (!player.isCreative()) {
                        player.getMainHandItem().shrink(1);
                    }
                    trap.repair(getRepairItems().get(stack.getItem()));
                    world.playSound(null, pos, ShellfishSounds.TRAP_REPAIR.get(), SoundSource.BLOCKS, 0.6f, 1.5f);
                } else {
                    MenuProvider screen = ((AbstractTrapBlockEntity) world.getBlockEntity(pos));
                    ((ServerPlayer)player).openMenu(screen, pos);
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return (BlockState)state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }
    
    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }


    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        if(world.isClientSide()) {
            return null;
        }

        return createTickerHelper(type, getBE(), (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag options) {
        super.appendHoverText(stack, context, tooltip, options);
        Integer durability = stack.get(ShellfishComponents.DURABILITY_COMPONENT.get());
        if (durability != null && durability < getMaxDurability()) {
            tooltip.add(Component.literal(Component.translatable("shellfish_trap.durability").getString() + durability + " / " + getMaxDurability()).withStyle(ChatFormatting.ITALIC, durability > getMaxDurability() / 2 ? ChatFormatting.DARK_GREEN : durability > getMaxDurability() / 5 ? ChatFormatting.YELLOW : ChatFormatting.DARK_RED));
        }
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state) {
        return true;
    }
}
