package womp.shellfishmod.screens;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import womp.shellfishmod.blocks.parents.AbstractTrapBlockEntity;
import womp.shellfishmod.registry.ShellfishScreens;

// This file was creating using help from Kaupenjoe
public class ShellfishTrapScreenHandler extends AbstractContainerMenu {

    private final Container inventory;
    private final ContainerData delegate;
    public final AbstractTrapBlockEntity blockEntity;

    public ShellfishTrapScreenHandler(int syncId, Inventory inventory, FriendlyByteBuf buf) {
        this(syncId, inventory, new SimpleContainer(19),
                new SimpleContainerData(4), null);
    }

    public ShellfishTrapScreenHandler(int syncId, Inventory inventory2, Container container,
                                      ContainerData arrayPropertyDelegate, AbstractTrapBlockEntity blockEntity) {
        super(ShellfishScreens.SHELLFISH_TRAP_SCREEN_HANDLER.get(), syncId);

        checkContainerSize(container, 19);
        this.inventory = container;
        this.delegate = arrayPropertyDelegate;
        this.blockEntity = blockEntity;

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

        addDataSlots(arrayPropertyDelegate);
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
    public ItemStack quickMoveStack(Player player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = (Slot)this.slots.get(invSlot);
        if (slot != null && slot.hasItem()) {
            ItemStack originalStack = slot.getItem();
            newStack = originalStack.copy();
            if (invSlot > 0 && invSlot < 19) {
                if (!this.moveItemStackTo(originalStack, 19, 55, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(originalStack, newStack);
            } else if (invSlot == 0 ? !this.moveItemStackTo(originalStack, 19, 55, false) : !this.moveItemStackTo(originalStack, 0, 1, false)) {
                return ItemStack.EMPTY;
            }
            if (originalStack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            if (originalStack.getCount() == newStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, originalStack);
        }

        return newStack;
    }

    @Override
    public boolean stillValid(Player var1) {
        return this.inventory.stillValid(var1);
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        if (blockEntity != null && blockEntity.getDurability() == 0) {
            player.displayClientMessage(Component.translatable(blockEntity.getRepairKey()), true);
        }
    }

    public int getMaxDurability() {
        return this.delegate.get(3);
    }
}
