package womp.shellfishmod.recipes;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record ShellfishTrapRecipeInput(ItemStack item, String biome) implements RecipeInput {

    @Override
	public ItemStack getItem(int slot) {
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
