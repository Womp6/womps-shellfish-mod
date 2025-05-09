package womp.shellfishmod.item;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import womp.shellfishmod.registry.ShellfishCrayfish;

import javax.annotation.Nullable;

public class CrayfishItem extends Item {

    public CrayfishItem(Item.Properties settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel world, Entity entity, EquipmentSlot slot) {
        if (stack.has(DataComponents.CUSTOM_NAME)) {
            int check = 0;
            String name = stack.get(DataComponents.CUSTOM_NAME).getString();
            for (RegistryObject<DataComponentType<Boolean>> value : ShellfishCrayfish.map.keySet()) {
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
        super.inventoryTick(stack, world, entity, slot);
    }

    private void removeCompsExc(ItemStack stack, @Nullable RegistryObject<DataComponentType<Boolean>> except) {
        if (except != null && stack.has(except.get())) return; // This makes less work for the system, that way it's not constantly occupied clearing components
        for (RegistryObject<DataComponentType<Boolean>> type : ShellfishCrayfish.map.keySet()) {
            if (!type.equals(except)) {
                stack.remove(type.get());
            }
        }
    }
}
