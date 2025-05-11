package womp.shellfishmod.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import womp.shellfishmod.ShellfishMod;
import womp.shellfishmod.recipes.ShellfishTrapRecipe;

import java.util.function.Supplier;

public class ShellfishRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, ShellfishMod.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, ShellfishMod.MOD_ID);
    public static final DeferredRegister<RecipeBookCategory> RECIPE_CATEGORIES = DeferredRegister.create(Registries.RECIPE_BOOK_CATEGORY, ShellfishMod.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<ShellfishTrapRecipe>> TRAP_SERIALIZER = RECIPE_SERIALIZERS.register(ShellfishTrapRecipe.Serializer.ID, () -> ShellfishTrapRecipe.Serializer.INSTANCE);
    public static final DeferredHolder<RecipeType<?>, RecipeType<ShellfishTrapRecipe>> TRAP_TYPE = RECIPE_TYPES.register(ShellfishTrapRecipe.Type.ID, () -> ShellfishTrapRecipe.Type.INSTANCE);
    public static final Supplier<RecipeBookCategory> TRAP_CATEGORY = RECIPE_CATEGORIES.register("trap_category", RecipeBookCategory::new);

    public static void register(IEventBus bus) {
        RECIPE_SERIALIZERS.register(bus);
        RECIPE_TYPES.register(bus);
    }
}
