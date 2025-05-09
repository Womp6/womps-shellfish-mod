package womp.shellfishmod.recipes;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.RecipeInput;

public record ShellfishTrapRecipeInput(List<ItemStack> input) implements RecipeInput {

    @Override
    public ItemStack getStackInSlot(int slot) {
        return input.get(slot);
    }

    @Override
    public int getSize() {
        return input.size();
    }
    
}
