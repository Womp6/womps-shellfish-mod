package womp.shellfishmod.registry;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import womp.shellfishmod.recipes.ShellfishTrapRecipe;

public class ShellfishRecipes {
    
    public static final RecipeSerializer<ShellfishTrapRecipe> TRAP_SERIALIZER = registerSerializer(ShellfishTrapRecipe.Serializer.ID, ShellfishTrapRecipe.Serializer.INSTANCE);
    public static final RecipeType<ShellfishTrapRecipe> TRAP_TYPE = registerType(ShellfishTrapRecipe.Type.ID, ShellfishTrapRecipe.Type.INSTANCE);


    private static <T extends Recipe<?>> RecipeSerializer<T> registerSerializer(String name, RecipeSerializer<T> serializer) {
        return Registry.register(Registries.RECIPE_SERIALIZER, new Identifier("shellfish", name), serializer);
    }

    private static <T extends Recipe<?>> RecipeType<T> registerType(String name, RecipeType<T> type) {
        return Registry.register(Registries.RECIPE_TYPE, new Identifier("shellfish",  name), type);
    }

    public static void register() {}
}
