package womp.shellfishmod.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.RecipeInput;

public record ShellfishTrapRecipeInput(ItemStack item, String biome) implements RecipeInput {

    @Override
	public ItemStack getStackInSlot(int slot) {
		if (slot != 0) {
			throw new IllegalArgumentException("No item for index " + slot);
		} else {
			return this.item;
		}
	}

    @Override
    public int size() {
        return 1;
    }

    public String getBiome() {
        return this.biome;
    }
}
