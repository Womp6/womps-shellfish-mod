package womp.shellfishmod.item;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import womp.shellfishmod.registry.ShellfishCrayfish;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class CrayfishItem extends Item {

    public CrayfishItem(Item.Properties settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        if (stack.has(DataComponents.CUSTOM_NAME)) {
            int check = 0;
            String name = stack.get(DataComponents.CUSTOM_NAME).getString();
            for (Supplier<DataComponentType<Boolean>> value : ShellfishCrayfish.map.keySet()) {
                if (name.equals(ShellfishCrayfish.map.get(value))) {
                    removeCompsExc(stack, value);
                    stack.set(value.get(), true);
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

    private void removeCompsExc(ItemStack stack, @Nullable Supplier<DataComponentType<Boolean>> except) {
        if (except != null && stack.has(except.get())) return; // This makes less work for the system, that way it's not constantly occupied clearing components
        for (Supplier<DataComponentType<Boolean>> type : ShellfishCrayfish.map.keySet()) {
            if (!type.equals(except)) {
                stack.remove(type.get());
            }
        }
    }
}
