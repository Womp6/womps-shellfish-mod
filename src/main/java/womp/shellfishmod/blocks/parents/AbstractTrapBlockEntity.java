package womp.shellfishmod.blocks.parents;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import womp.shellfishmod.recipes.ShellfishTrapRecipe;
import womp.shellfishmod.recipes.ShellfishTrapRecipeInput;
import womp.shellfishmod.registry.ShellfishComponents;
import womp.shellfishmod.registry.ShellfishItems;
import womp.shellfishmod.screens.ShellfishTrapScreenHandler;

import java.util.Optional;

// The foundation of this file was created using help from Kaupenjoe
public abstract class AbstractTrapBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer {

    //REQUIRED
    protected abstract Item selectJunk(int i);
    protected abstract Item selectTreasure(int i);
    protected abstract int getMaxDurability();
    protected abstract int getMaxProgress();
    protected abstract int getMaxOutCount();
    public abstract String getRepairKey();
    /**
     * Return a list of 8 integers:
     * <ul>
     * <p>Integer 1 is the upper bound for the random generator with shellfish bait.
     * <p>Integer 2 is the upper bound for the random generator without shellfish bait.
     * <p>Integer 3 is the chance value for treasure with shellfish bait.
     * <p>Integer 4 is the chance value for treasure without shellfish bait.
     * <p>Integer 5 is the chance value for junk with shellfish bait.
     * <p>Integer 6 is the chance value for junk without shellfish bait
     * <p>Integer 7 is the upper bound for the {@link #selectTreasure(int)} method.
     * <p>Integer 8 is the upper bound for the {@link #selectJunk(int)} method.
     */
    protected abstract int[] getOutputInts();


    public static final int BAIT_SLOT = 0;
    protected final ContainerData delegate;
    protected int progress = 0;
    protected int maxProgress = getMaxProgress();
    protected boolean canTrap;
    protected int count;
    protected int durability = getMaxDurability();
    protected int maxDurability = getMaxDurability();
    private static final int[] SLOTS_FOR_DOWN = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18};
    private static final int[] SLOTS_FOR_REST = new int[]{0};

    private final ItemStackHandler itemHandler = new ItemStackHandler(19) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (level != null) {
                if (!level.isClientSide()) {
                    level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
                }
            }
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0 -> true;
                case 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18 -> false;
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    LazyOptional<? extends IItemHandler>[] handlers =
            SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

    public AbstractTrapBlockEntity(BlockEntityType<? extends AbstractTrapBlockEntity> be, BlockPos pos, BlockState state) {
        super(be, pos, state);

        this.delegate = new ContainerData() {

            @Override
            public int get(int var1) {
                switch (var1) {
                    case 0: {
                        return AbstractTrapBlockEntity.this.progress;
                    }
                    case 1: {
                        return AbstractTrapBlockEntity.this.maxProgress;
                    }
                    case 2: {
                        return AbstractTrapBlockEntity.this.durability;
                    }
                    case 3: {
                        return AbstractTrapBlockEntity.this.maxDurability;
                    }
                }
                return 0;
            }

            @Override
            public void set(int var1, int var2) {
                switch (var1) {
                    case 0: AbstractTrapBlockEntity.this.progress = var2;
                    case 1: AbstractTrapBlockEntity.this.maxProgress = var2;
                    case 2: AbstractTrapBlockEntity.this.durability = var2;
                    case 3: AbstractTrapBlockEntity.this.maxDurability = var2;
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
    }

    public ItemStack renderBait() {
        return this.getItem(BAIT_SLOT);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        for (int x = 0; x < handlers.length; x++) {
            handlers[x].invalidate();
        }
    }

    @Override
    public void saveAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        NonNullList<ItemStack> bait = NonNullList.withSize(1, ItemStack.EMPTY);
        super.saveAdditional(nbt, provider);
        nbt.put("inventory", itemHandler.serializeNBT(provider));
        nbt.putInt("progress", progress);
        nbt.putInt("durability", durability);
        nbt.putBoolean("canTrap", canTrap);
        bait.set(0, itemHandler.getStackInSlot(BAIT_SLOT));
        ContainerHelper.saveAllItems(nbt, bait, provider);
    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        NonNullList<ItemStack> bait = NonNullList.withSize(1, ItemStack.EMPTY);
        itemHandler.deserializeNBT(provider, nbt.getCompound("inventory"));
        progress = nbt.getInt("progress");
        durability = nbt.getInt("durability");
        canTrap = nbt.getBoolean("canTrap");
        ContainerHelper.loadAllItems(nbt, bait, provider);
        itemHandler.setStackInSlot(BAIT_SLOT, bait.getFirst());
        super.loadAdditional(nbt, provider);
    }

    public void tick(Level world, BlockPos pos, BlockState state) {
        if (durability == 0) {
            setBroken(true);
        } else if (durability > maxDurability) {
            durability = maxDurability;
        } else if (durability < 0) {
            durability = 0;
            setBroken(true);
        }
        if (durability > 0) {
            if (checkProperConditions(world, pos, state)) {
                if (canInsertIntoOutputSlots() && hasRecipe() && this.canTrap) {
                    increaseTrapProgress();
                    setChanged(world, pos, state);

                    if (trappingComplete()) {
                        resetTrapping();
                        outputProduct();
                    }
                } else {
                    resetTrapping();
                    checkCanTrap();
                }
            } else {
                resetTrapping();
            }
        }
    }

    protected void checkCanTrap() {
        for (int i = 1; i <= 18; i++) {
            if (this.getItem(i).isEmpty()) this.canTrap = true;
        }
    }

    protected void outputProduct() {
        Optional<ShellfishTrapRecipe> recipe = getCurrentRecipe();
        Item output1 = recipe.get().getResultItem().getItem();
        RandomSource random = RandomSource.create();
        count = random.nextIntBetweenInclusive(1, getMaxOutCount());
        Item output = selectOutput(output1);
        int stack = checkStack(output);
        int count2 = 0;
        if (stack != -1) count2 = -output.getDefaultMaxStackSize() + (this.getItem(stack).getCount() + count);
        int stack2 = checkSecondStack(output, stack, count2);
        if (stack == -1 || stack2 == -2) {
            this.canTrap = false;
        } else if (stack2 != -1) {
            this.canTrap = this.getItem(stack2).getCount() + count2 < output.getDefaultMaxStackSize();
        }
        if (this.canTrap) {
            if (!this.getItem(stack).isEmpty()) {
                this.removeItem(BAIT_SLOT, 1);
                if (count2 < 1) {
                    this.setItem(stack, new ItemStack(this.getItem(stack).getItem(), this.getItem(stack).getCount() + count));
                } else {
                    this.setItem(stack, new ItemStack(this.getItem(stack).getItem(), this.getItem(stack).getMaxStackSize()));
                    if (!this.getItem(stack2).isEmpty()) {
                        this.setItem(stack2, new ItemStack(this.getItem(stack2).getItem(), this.getItem(stack2).getCount() + count2));
                    } else {
                        this.setItem(stack2, new ItemStack(output, count2));
                    }
                }
                durability--;
            } else {
                this.removeItem(BAIT_SLOT, 1);
                this.setItem(stack, new ItemStack(output, count));
                durability--;
            }
        }
    }

    protected int checkSecondStack(Item item, int current, int count2) {
        if (count2 < 1) return -1;
        for (int i = 1; i < 19; i++) {
            if (((this.getItem(i).getItem() == item && this.getItem(i).getCount() < this.getItem(i).getMaxStackSize()) || this.getItem(i).isEmpty()) && current != i) return i;
        }
        return -2;
    }

    protected int checkStack(Item item) {
        for (int i = 1; i <= 18; i++) {
            if ((this.getItem(i).getItem() == item && this.getItem(i).getCount() < this.getItem(i).getMaxStackSize()) || this.getItem(i).isEmpty()) return i;
        }
        return -1;
    }

    protected Item selectOutput(Item item) {
        RandomSource random = RandomSource.create();
        Item bait = this.getItem(BAIT_SLOT).getItem();
        if (bait.equals(ShellfishItems.SHELLFISH_BAIT.get()) || bait.equals(ShellfishItems.DRIED_SHELLFISH_BAIT.get())) {
            int factor = random.nextIntBetweenInclusive(1, getOutputInts()[0]);
            if (factor <= getOutputInts()[2]) {
                return selectTreasure(random.nextIntBetweenInclusive(1, getOutputInts()[6]));
            } else if (factor <= getOutputInts()[4]) {
                return selectJunk(random.nextIntBetweenInclusive(1, getOutputInts()[7]));
            } else {
                return item;
            }
        } else {
            int factor = random.nextIntBetweenInclusive(1, getOutputInts()[1]);
            if (factor <= getOutputInts()[3]) {
                return selectTreasure(random.nextIntBetweenInclusive(1, getOutputInts()[6]));
            } else if (factor <= getOutputInts()[5]) {
                return selectJunk(random.nextIntBetweenInclusive(1, getOutputInts()[7]));
            } else {
                return item;
            }
        }
    }

    protected void resetTrapping() {
        this.progress = 0;
    }

    protected boolean trappingComplete() {
        return this.progress >= this.maxProgress;
    }

    protected void increaseTrapProgress() {
        this.progress++;
    }

    private boolean hasRecipe() {
        Optional<ShellfishTrapRecipe> recipe = getCurrentRecipe();
        return recipe.isPresent();
    }

    private Optional<ShellfishTrapRecipe> getCurrentRecipe() {
        ShellfishTrapRecipeInput recipe = new ShellfishTrapRecipeInput(itemHandler.getStackInSlot(BAIT_SLOT), this.getLevel().getBiome(worldPosition).getRegisteredName());
        if (!level.isClientSide) {
            Optional<RecipeHolder<ShellfishTrapRecipe>> value = RecipeManager.createCheck(ShellfishTrapRecipe.Type.INSTANCE).getRecipeFor(recipe, (ServerLevel)level);
            if (value.isPresent()) {
                return Optional.of(value.get().value());
            }
        }
        return Optional.empty();
    }

    public boolean biomeMatch(Level world, String biome) {
        String currentBiome = world.getBiome(this.getBlockPos()).unwrapKey().get().location().toString();
        if (currentBiome.equals(biome)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (capability == ForgeCapabilities.ITEM_HANDLER && facing != null && !this.remove) {
            return switch (facing) {
                case UP -> handlers[0].cast();
                case DOWN -> handlers[1].cast();
                default -> handlers[2].cast();
            };
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public int getContainerSize() {
        return itemHandler.getSlots();
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < getContainerSize(); i++) {
            if (!itemHandler.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getItem(int slot) {
        return itemHandler.getStackInSlot(slot);
    }

    @Override
    public ItemStack removeItem(int pSlot, int pAmount) {
        return itemHandler.extractItem(pSlot, pAmount, false);
    }

    @Override
    public ItemStack removeItemNoUpdate(int pSlot) {
        int count = itemHandler.getStackInSlot(pSlot).getCount();
        return itemHandler.extractItem(pSlot, count, false);
    }

    @Override
    public void setItem(int pSlot, ItemStack pStack) {
        ItemStack itemstack = itemHandler.getStackInSlot(pSlot);
        boolean flag = !pStack.isEmpty() && ItemStack.isSameItemSameComponents(itemstack, pStack);
        this.itemHandler.setStackInSlot(pSlot, pStack);
        if (pStack.getCount() > this.getMaxStackSize()) {
            pStack.setCount(this.getMaxStackSize());
        }

        if (pSlot == 0 && !flag) {
            this.progress = 0;
            this.setChanged();
        }
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return Container.stillValidBlockEntity(this, pPlayer);
    }

    @Override
    public int[] getSlotsForFace(Direction pSide) {
        if (pSide == Direction.DOWN) {
            return SLOTS_FOR_DOWN;
        } else {
            return SLOTS_FOR_REST;
        }
    }

    @Override
    public boolean canPlaceItem(int pIndex, ItemStack pStack) {
        return pIndex == BAIT_SLOT;
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction side) {
        return slot == BAIT_SLOT;
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, @Nullable Direction side) {
        return slot != BAIT_SLOT;
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        this.handlers = SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH, Direction.WEST, Direction.EAST, Direction.WEST);
    }

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return new ShellfishTrapScreenHandler(pContainerId, pInventory, this, delegate);
    }

    protected boolean canInsertIntoOutputSlots() {
        for (int i = 1; i <= 18; i++) {
            if (this.getItem(i).isEmpty() || this.getItem(i).getCount() < this.getItem(i).getMaxStackSize()) return true;
        }
        return false;
    }

    protected boolean checkProperConditions(Level world, BlockPos pos, BlockState state) {
        int i = 0;
        if (world.getFluidState(pos.above(1)).getType() == Fluids.WATER) i++;
        if (world.getFluidState(pos.north(1)).getType() == Fluids.WATER) i++;
        if (world.getFluidState(pos.east(1)).getType() == Fluids.WATER) i++;
        if (world.getFluidState(pos.west(1)).getType() == Fluids.WATER) i++;
        if (world.getFluidState(pos.south(1)).getType() == Fluids.WATER) i++;

        if (world.getBlockState(pos.north(1)).getBlock() instanceof AbstractTrapBlock) return false;
        if (world.getBlockState(pos.south(1)).getBlock() instanceof AbstractTrapBlock) return false;
        if (world.getBlockState(pos.east(1)).getBlock() instanceof AbstractTrapBlock) return false;
        if (world.getBlockState(pos.west(1)).getBlock() instanceof AbstractTrapBlock) return false;

        if (!world.getEntities(EntityType.HOPPER_MINECART, new AABB(pos.below(1)), entity -> true).isEmpty()) return false;

        if (this.getLevel() != null && this.getLevel().getBlockState(this.getBlockPos()).getBlock() instanceof AbstractTrapBlock) {
            BlockState state1 = this.getLevel().getBlockState(this.getBlockPos());
            if (state1.hasProperty(AbstractTrapBlock.FACING)) {
                Direction dir = state.getValue(AbstractTrapBlock.FACING);
                switch (dir) {
                    case NORTH, SOUTH -> {
                        if (!checkNotCovered(world, pos.south(1), pos.north(1), pos)) {
                            return false;
                        }
                    }
                    case EAST, WEST -> {
                        if (!checkNotCovered(world, pos.east(1), pos.west(1), pos)) {
                            return false;
                        }
                    }
                    default -> {break;}
                }
            }
        }
        return (i >= 3) && !state.getFluidState().isEmpty() && world.getBlockState(pos.below(1)).isFaceSturdy(world, pos, Direction.UP) && !world.getBlockState(pos.below(1)).propagatesSkylightDown();
    }

    protected boolean checkNotCovered(Level world, BlockPos pos1, BlockPos pos2, BlockPos pos) {
        if (world.getFluidState(pos1).getType() != Fluids.WATER && world.getFluidState(pos2).getType() != Fluids.WATER) return false;
        else if (world.getBlockState(pos1).isCollisionShapeFullBlock(world, pos) && world.getFluidState(pos2).getType() != Fluids.WATER) return false;
        else if (world.getBlockState(pos2).isCollisionShapeFullBlock(world, pos) && world.getFluidState(pos1).getType() != Fluids.WATER) return false;
        else if (world.getBlockState(pos1).isCollisionShapeFullBlock(world, pos) && world.getBlockState(pos2).isCollisionShapeFullBlock(world, pos)) return false;
        else return true;
    }

    public int getDurability() {
        return durability;
    }

    public void repair(int value) {
        setBroken(false);
        durability+=value;
        if (durability > maxDurability) {
            durability = maxDurability;
        }
        setChanged();
    }

    public void setDurability(int value) {
        durability = value;
        if (durability == 0) {
            setBroken(true);
        } else {
            setBroken(false);
        }
        setChanged();
    }

    public void setBroken(boolean value) {
        if (level != null) {
            BlockState state = level.getBlockState(worldPosition);
            if (state.getBlock() instanceof AbstractTrapBlock) {
                level.setBlock(worldPosition, state.setValue(AbstractTrapBlock.BROKEN, value), 2);
            }
        }

        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
        }
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        NonNullList<ItemStack> list = NonNullList.withSize(itemHandler.getSlots(), ItemStack.EMPTY);
        for (int i = 0; i < list.size(); i++) {
            list.set(i, itemHandler.getStackInSlot(i));
        }
        return list;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> pItems) {
        for (int i = 0; i < pItems.size(); i++) {
            itemHandler.extractItem(i, itemHandler.getStackInSlot(i).getCount(), false);
            itemHandler.setStackInSlot(i, pItems.get(i));
        }
        setChanged();
    }

    @Override
    protected void applyImplicitComponents(DataComponentInput components) {
        super.applyImplicitComponents(components);
        this.durability = components.getOrDefault(ShellfishComponents.DURABILITY_COMPONENT.get(), maxDurability);
        setChanged();
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder componentMapBuilder) {
        super.collectImplicitComponents(componentMapBuilder);
        if (durability < maxDurability) {
            componentMapBuilder.set(ShellfishComponents.DURABILITY_COMPONENT.get(), durability);
        }
    }

    @Override
    public void removeComponentsFromTag(CompoundTag nbt) {
        nbt.remove("durability");
    }


    //NETWORKING
    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider wrapper) {
        return saveWithoutMetadata(wrapper);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider wrapper) {
        super.onDataPacket(net, pkt, wrapper);
    }

    @Override
    public void clearContent() {
        for (int i = 0; i < this.itemHandler.getSlots(); i ++) {
            this.itemHandler.extractItem(i, itemHandler.getStackInSlot(i).getCount(), true);
        }
        setChanged();
    }


}
