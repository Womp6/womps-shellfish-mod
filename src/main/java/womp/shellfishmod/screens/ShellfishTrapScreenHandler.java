package womp.shellfishmod.screens;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import womp.shellfishmod.blocks.parents.AbstractTrapBlockEntity;
import womp.shellfishmod.registry.ShellfishScreens;

// This file was creating using help from Kaupenjoe
public class ShellfishTrapScreenHandler extends ScreenHandler {

    private final Inventory inventory;
    private final PropertyDelegate delegate;
    public final AbstractTrapBlockEntity blockEntity;
    
    public ShellfishTrapScreenHandler(int syncId, PlayerInventory inventory, TrapData pos) {
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(pos.pos()), new ArrayPropertyDelegate(4));
    }

    public ShellfishTrapScreenHandler(int syncId, PlayerInventory inventory2, BlockEntity blockEntity, PropertyDelegate arrayPropertyDelegate) {
        super(ShellfishScreens.SHELLFISH_TRAP_SCREEN_HANDLER, syncId);
        
        checkSize(((Inventory)blockEntity), 19);
        this.inventory = (Inventory)blockEntity;
        this.delegate = arrayPropertyDelegate;
        this.blockEntity = (AbstractTrapBlockEntity)blockEntity;

        this.addSlot(new Slot(inventory, 0, 12, 35));
        for (int i = 0; i < 6; i++) {
            this.addSlot(new TrapOutputSlot(inventory2.player, inventory, (i + 1), (58 + (i * 18)), 17));
        }
        for (int ii = 0; ii < 6; ii++) {
            this.addSlot(new TrapOutputSlot(inventory2.player, inventory, (ii + 7), (58 + (ii * 18)), 35));
        }
        for (int iii = 0; iii < 6; iii++) {
            this.addSlot(new TrapOutputSlot(inventory2.player, inventory, (iii + 13), (58 + (iii * 18)), 53));
        }
        addPlayerInventory(inventory2);
        addPlayerHotbar(inventory2);

        addProperties(arrayPropertyDelegate);
    }

    public boolean isTrapping() {
        return delegate.get(0) > 0;
    }

    public int getScaledProgress() {
        int progress = this.delegate.get(0);
        int maxProgress = this.delegate.get(1);
        int progressArrowSize = 22;

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    public int getScaledDurability() {
        int durability = this.delegate.get(2);
        int maxDurability = this.delegate.get(3);
        int durabilityBarSize = 40;

        int width = maxDurability != 0 && durability != 0 ? durability * durabilityBarSize / maxDurability : 0;
        if (this.getDurability() > 0 && width == 0) {
            return 1;
        }
        return width;
    }

    public int getDurability() {
        return this.delegate.get(2);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = (Slot)this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot > 0 && invSlot < 19) {
                if (!this.insertItem(originalStack, 19, 55, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickTransfer(originalStack, newStack);
            } else if (invSlot == 0 ? !this.insertItem(originalStack, 19, 55, false) : !this.insertItem(originalStack, 0, 1, false)) {
                return ItemStack.EMPTY;
            }
            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
            if (originalStack.getCount() == newStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTakeItem(player, originalStack);
        }
        
        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity var1) {
        return this.inventory.canPlayerUse(var1);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        if (blockEntity instanceof AbstractTrapBlockEntity trap) {
            if (trap.getDurability() == 0) {
                player.sendMessage(Text.translatable(trap.getRepairKey()), true);
            }
        }
    }

    public int getMaxDurability() {
        return this.delegate.get(3);
    }
}
