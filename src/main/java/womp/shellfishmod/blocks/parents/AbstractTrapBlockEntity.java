package womp.shellfishmod.blocks.parents;

import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import womp.shellfishmod.client.registry.ShellfishMessages;
import womp.shellfishmod.recipes.ShellfishTrapRecipe;
import womp.shellfishmod.registry.ShellfishItems;
import womp.shellfishmod.screens.ShellfishTrapScreenHandler;

// The foundation of this file was created using help from Kaupenjoe
public abstract class AbstractTrapBlockEntity extends LockableContainerBlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {

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
     * <p>Integer 7 is the upper bound for the {@link #selectTreasure()} method.
     * <p>Integer 8 is the upper bound for the {@link #selectJunk()} method.
     */
    protected abstract int[] getOutputInts();


    public static final int BAIT_SLOT = 0;
    protected final PropertyDelegate delegate;
    protected int progress = 0;
    protected int maxProgress = getMaxProgress();
    protected boolean canTrap;
    protected int count;
    protected int durability = getMaxDurability();
    protected int maxDurability = getMaxDurability();

    protected final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(19, ItemStack.EMPTY);

    public AbstractTrapBlockEntity(BlockEntityType<? extends AbstractTrapBlockEntity> be, BlockPos pos, BlockState state) {
        super(be, pos, state);
        this.delegate = new PropertyDelegate() {

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
            public int size() {
                return 4;
            }
            
        };
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }

    public ItemStack renderBait() {
        return this.getStack(BAIT_SLOT);
    }

    @Override
    public void markDirty() {
        if (!world.isClient) {
            PacketByteBuf data = PacketByteBufs.create();
            data.writeInt(inventory.size());
            for (int i = 0; i < inventory.size(); i++) {
                data.writeItemStack(inventory.get(i));
            }
            data.writeBlockPos(getPos());
            for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld)world, getPos())) {
                ServerPlayNetworking.send(player, ShellfishMessages.ITEM_SYNC, data);
            }
        }
        super.markDirty();
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    public void setInventory(DefaultedList<ItemStack> list) {
        for (int i = 0; i < list.size(); i++) {
            this.inventory.set(i, list.get(i));
        }
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("shellfish_trap.progress", progress);
        nbt.putInt("durability", durability);
        if (durability < maxDurability) {
            nbt.putInt("durabilityCheck", durability);
        } else {
            nbt.remove("durabilityCheck");
        }
        nbt.putBoolean("canTrap", canTrap);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inventory);
        progress = nbt.getInt("shellfish_trap.progress");
        durability = nbt.getInt("durability");
        canTrap = nbt.getBoolean("canTrap");
        super.readNbt(nbt);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
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
                    markDirty(world, pos, state);

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
            if (this.getStack(i).isEmpty()) this.canTrap = true;
        }
    }

    protected void outputProduct() {
        Optional<ShellfishTrapRecipe> recipe = getCurrentRecipe();
        Item output1 = recipe.get().getResult(null).getItem();
        Random random = Random.create();
        count = random.nextBetween(1, getMaxOutCount());
        Item output = selectOutput(output1);
        int stack = checkStack(output);
        int count2 = 0;
        if (stack != -1) count2 = -this.getStack(stack).getMaxCount() + (this.getStack(stack).getCount() + count);
        int stack2 = checkSecondStack(output, stack, count2);
        if (stack == -1 || stack2 == -2) {
            this.canTrap = false;
        } else if (stack2 != -1) {
            this.canTrap = this.getStack(stack2).getCount() + count2 < this.getStack(stack2).getMaxCount();
        }
        if (this.canTrap) {
            if (!this.getStack(stack).isEmpty()) {
                this.removeStack(BAIT_SLOT, 1);
                if (count2 < 1) {
                    this.setStack(stack, new ItemStack(this.getStack(stack).getItem(), this.getStack(stack).getCount() + count));
                } else {
                    this.setStack(stack, new ItemStack(this.getStack(stack).getItem(), this.getStack(stack).getMaxCount()));
                    if (!this.getStack(stack2).isEmpty()) {
                        this.setStack(stack2, new ItemStack(this.getStack(stack2).getItem(), this.getStack(stack2).getCount() + count2));
                    } else {
                        this.setStack(stack2, new ItemStack(output, count2));
                    }
                }
                durability--;
            } else {
                this.removeStack(BAIT_SLOT, 1);
                this.setStack(stack, new ItemStack(output, count));
                durability--;
            }
        }
    }

    protected int checkSecondStack(Item item, int current, int count2) {
        if (count2 < 1) return -1;
        for (int i = 1; i < 19; i++) {
            if (((this.getStack(i).getItem() == item && this.getStack(i).getCount() < this.getStack(i).getMaxCount()) || this.getStack(i).isEmpty()) && current != i) return i;
        }
        return -2;
    } 

    protected int checkStack(Item item) {
        for (int i = 1; i <= 18; i++) {
            if ((this.getStack(i).getItem() == item && this.getStack(i).getCount() < this.getStack(i).getMaxCount()) || this.getStack(i).isEmpty()) return i;
        }
       return -1;
    }

    protected Item selectOutput(Item item) {
        Random random = Random.create();
        Item bait = this.getStack(BAIT_SLOT).getItem();
        if (bait.equals(ShellfishItems.SHELLFISH_BAIT) || bait.equals(ShellfishItems.DRIED_SHELLFISH_BAIT)) {
            int factor = random.nextBetween(1, getOutputInts()[0]);
            if (factor <= getOutputInts()[2]) {
                return selectTreasure(random.nextBetween(1, getOutputInts()[6]));
            } else if (factor <= getOutputInts()[4]) {
                return selectJunk(random.nextBetween(1, getOutputInts()[7]));
            } else {
                return item;
            }
        } else {
            int factor = random.nextBetween(1, getOutputInts()[1]);
            if (factor <= getOutputInts()[3]) {
                return selectTreasure(random.nextBetween(1, getOutputInts()[6]));
            } else if (factor <= getOutputInts()[5]) {
                return selectJunk(random.nextBetween(1, getOutputInts()[7]));
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
    
    protected boolean hasRecipe() {
        Optional<ShellfishTrapRecipe> recipe = getCurrentRecipe();
        return recipe.isPresent();
    }

    protected Optional<ShellfishTrapRecipe> getCurrentRecipe() {
        SimpleInventory inventory1 = new SimpleInventory((this.size()));
        for (int i = 0; i < this.size(); i++) {
            inventory1.setStack(i, this.getStack(i));
        }

       Optional<List<RecipeEntry<ShellfishTrapRecipe>>> list = Optional.of(this.getWorld().getRecipeManager().getAllMatches(ShellfishTrapRecipe.Type.INSTANCE, inventory1, this.getWorld()));
       if (!list.isEmpty()) {
        for (int i = 0; i < list.get().size(); i++) {
            String[] biomes = list.get().get(i).value().getBiomes();
            for (int p = 0; p < biomes.length; p++) {
                if (biomeMatch(world, biomes[p])) {
                    return Optional.of(list.get().get(i).value());
                }
            }
        }
    }
        return Optional.empty();
    }

    public boolean biomeMatch(World world, String biome) {
        String currentBiome = world.getBiome(this.getPos()).getKey().get().getValue().toString();
        if (currentBiome.equals(biome)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return slot == BAIT_SLOT;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, @Nullable Direction dir) {
        return slot != BAIT_SLOT;
    }

    @Override
    public ScreenHandler createScreenHandler(int var1, PlayerInventory var2) {
        return new ShellfishTrapScreenHandler(var1, var2, this, delegate);
    }
        
    protected boolean canInsertIntoOutputSlots() {
        for (int i = 1; i <= 18; i++) {
            if (this.getStack(i).isEmpty() || this.getStack(i).getCount() < this.getStack(i).getMaxCount()) return true;
        }
        return false;
    }

    protected boolean checkProperConditions(World world, BlockPos pos, BlockState state) {
        int i = 0;
        if (world.getFluidState(pos.up(1)).getFluid() == Fluids.WATER) i++;
        if (world.getFluidState(pos.north(1)).getFluid() == Fluids.WATER) i++;
        if (world.getFluidState(pos.east(1)).getFluid() == Fluids.WATER) i++;
        if (world.getFluidState(pos.west(1)).getFluid() == Fluids.WATER) i++;
        if (world.getFluidState(pos.south(1)).getFluid() == Fluids.WATER) i++;

        if (world.getBlockState(pos.north(1)).getBlock() instanceof AbstractTrapBlock) return false;
        if (world.getBlockState(pos.south(1)).getBlock() instanceof AbstractTrapBlock) return false;
        if (world.getBlockState(pos.east(1)).getBlock() instanceof AbstractTrapBlock) return false;
        if (world.getBlockState(pos.west(1)).getBlock() instanceof AbstractTrapBlock) return false;

        if (world.getEntitiesByType(EntityType.HOPPER_MINECART, new Box(pos.down(1)), entity -> true).size() > 0) return false;
        
        if (this.getWorld() != null && this.getWorld().getBlockState(this.getPos()).getBlock() instanceof AbstractTrapBlock) {
            BlockState state1 = this.getWorld().getBlockState(this.getPos());
            if (state1.contains(AbstractTrapBlock.FACING)) {
                Direction dir = state.get(AbstractTrapBlock.FACING);
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
        return (i >= 3) && !state.getFluidState().isEmpty() && world.getBlockState(pos.down(1)).isSideSolidFullSquare(world, pos, Direction.UP) && !world.getBlockState(pos.down(1)).isTransparent(world, pos);
    }

    protected boolean checkNotCovered(World world, BlockPos pos1, BlockPos pos2, BlockPos pos) {
        if (world.getFluidState(pos1).getFluid() != Fluids.WATER && world.getFluidState(pos2).getFluid() != Fluids.WATER) return false;
        else if (world.getBlockState(pos1).isFullCube(world, pos) && world.getFluidState(pos2).getFluid() != Fluids.WATER) return false;
        else if (world.getBlockState(pos2).isFullCube(world, pos) && world.getFluidState(pos1).getFluid() != Fluids.WATER) return false;
        else if (world.getBlockState(pos1).isFullCube(world, pos) && world.getBlockState(pos2).isFullCube(world, pos)) return false;
        else return true;
    }

    public int getDurability() {
        return durability;
    }

    public void repair(int value) {
        setBroken(false);
        durability += value;
        if (durability > maxDurability) {
            durability = maxDurability;
        }
        markDirty();
    }

    public void setDurability(int value) {
        durability = value;
        if (durability == 0) {
            setBroken(true);
        } else {
            setBroken(false);
        }
        markDirty();
    }

    public void setBroken(boolean value) {
        if (world != null) {
            BlockState state = world.getBlockState(pos);
            if (state.getBlock() instanceof AbstractTrapBlock) {
                world.setBlockState(pos, state.with(AbstractTrapBlock.BROKEN, value), Block.NOTIFY_ALL);
            }
            world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
            markDirty();
        }
    }
}