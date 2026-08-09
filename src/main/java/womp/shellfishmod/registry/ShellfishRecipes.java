package womp.shellfishmod.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import womp.shellfishmod.recipes.ShellfishTrapRecipe;

public class ShellfishRecipes {
    
    public static final RecipeSerializer<ShellfishTrapRecipe> TRAP_SERIALIZER = registerSerializer("shellfish_trap", ShellfishTrapRecipe.SERIALIZER);
    public static final RecipeType<ShellfishTrapRecipe> TRAP_TYPE = registerType(ShellfishTrapRecipe.Type.ID, ShellfishTrapRecipe.Type.INSTANCE);
    public static final RecipeBookCategory TRAP_CATEGORY = registerCategory("trap_category");


    private static <T extends Recipe<?>> RecipeSerializer<T> registerSerializer(String name, RecipeSerializer<T> serializer) {
        return Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Identifier.fromNamespaceAndPath("shellfish", name), serializer);
    }

    private static <T extends Recipe<?>> RecipeType<T> registerType(String name, RecipeType<T> type) {
        return Registry.register(BuiltInRegistries.RECIPE_TYPE, Identifier.fromNamespaceAndPath("shellfish",  name), type);
    }

    private static RecipeBookCategory registerCategory(String name) {
        return Registry.register(BuiltInRegistries.RECIPE_BOOK_CATEGORY, Identifier.fromNamespaceAndPath("shellfish", name), new RecipeBookCategory());
    }

    public static void register() {}
}
