package womp.shellfishmod.blocks.parents;

import java.util.HashMap;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import womp.shellfishmod.registry.ShellfishSounds;

// Block entity code made using help from Kaupenjoe
public abstract class AbstractTrapBlock extends BlockWithEntity implements Waterloggable {

    //REQUIRED
    protected abstract HashMap<Item, Integer> getRepairItems();
    protected abstract BlockEntityType<? extends AbstractTrapBlockEntity> getBE();
    protected abstract int getMaxDurability();


    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final BooleanProperty BROKEN = BooleanProperty.of("broken");

    public AbstractTrapBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState())
        .with(FACING, Direction.NORTH)).with(WATERLOGGED, false).with(BROKEN, false));
    }
    
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState)this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite())
        .with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER).with(BROKEN, false);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return (BlockState)state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, BROKEN);
    }

    @SuppressWarnings("deprecation")
    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(WATERLOGGED).booleanValue()) {
            return Fluids.WATER.getStill(false);
        }
        return super.getFluidState(state);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return state;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof AbstractTrapBlockEntity) {
                ItemScatterer.spawn(world, pos, (AbstractTrapBlockEntity)blockEntity);
                world.updateComparators(pos,this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            NamedScreenHandlerFactory screen = ((AbstractTrapBlockEntity) world.getBlockEntity(pos));
            BlockEntity blockEntity = world.getBlockEntity(pos);
            Item handItem = player.getStackInHand(hand).getItem();
            if (blockEntity instanceof AbstractTrapBlockEntity trap && hand == Hand.MAIN_HAND && getRepairItems().containsKey(handItem) && trap.getDurability() < trap.getMaxDurability()) {
                if (!player.isCreative()) {
                    player.getMainHandStack().decrement(1);
                }
                trap.repair(getRepairItems().get(handItem));
                world.playSound(null, pos, ShellfishSounds.TRAP_REPAIR, SoundCategory.BLOCKS, 0.6f, 1.5f);
            } else if (screen != null) {
                player.openHandledScreen(screen);
            }
        }
        return ActionResult.SUCCESS;
    }
    

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, getBE(), (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        BlockEntity blockEntity;
        if (itemStack.hasCustomName() && (blockEntity = world.getBlockEntity(pos)) instanceof AbstractTrapBlockEntity) {
            ((AbstractTrapBlockEntity)blockEntity).setCustomName(itemStack.getName());
        }
        if (itemStack.hasNbt() && itemStack.getNbt().getCompound("BlockEntityTag").contains("durability") && (blockEntity = world.getBlockEntity(pos)) instanceof AbstractTrapBlockEntity) {
            ((AbstractTrapBlockEntity)blockEntity).setDurability(itemStack.getNbt().getCompound("BlockEntityTag").getInt("durability"));
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        super.appendTooltip(stack, world, tooltip, options);
        if (stack.hasNbt() && stack.getNbt().getCompound("BlockEntityTag").contains("durability") && stack.getNbt().getCompound("BlockEntityTag").getInt("durability") < getMaxDurability()) {
            int durability = stack.getNbt().getCompound("BlockEntityTag").getInt("durability");
            tooltip.add(Text.literal(Text.translatable("shellfish_trap.durability").getString() + durability + " / " + getMaxDurability()).formatted(Formatting.ITALIC, durability > getMaxDurability() / 2 ? Formatting.DARK_GREEN : durability > getMaxDurability() / 5 ? Formatting.YELLOW : Formatting.DARK_RED));
        }
    }

    @Override
    public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }
}