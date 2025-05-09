package womp.shellfishmod.item;

import org.jetbrains.annotations.Nullable;

import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import womp.shellfishmod.registry.ShellfishCrayfish;

public class CrayfishItem extends Item {

    public CrayfishItem(Item.Settings settings) {
        super(settings);
    }
    
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (stack.contains(DataComponentTypes.CUSTOM_NAME)) {
            int check = 0;
            String name = stack.get(DataComponentTypes.CUSTOM_NAME).getString();
            for (ComponentType<Boolean> value : ShellfishCrayfish.map.keySet()) {
                if (name.equals(ShellfishCrayfish.map.get(value))) {
                    removeCompsExc(stack, value);
                    stack.set(value, true);
                    check++;
                    break;
                }
            }
            if (check != 1) {
                removeCompsExc(stack, null);
            }
             
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    private void removeCompsExc(ItemStack stack, @Nullable ComponentType<?> except) {
        if (stack.contains(except)) return; // This makes less work for the system, that way it's not constantly occupied clearing components
        for (ComponentType<?> type : ShellfishCrayfish.map.keySet()) {
            if (!type.equals(except)) {
                stack.remove(type);
            }
        }
    }
}
