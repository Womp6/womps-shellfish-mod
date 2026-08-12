package womp.shellfishmod.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import womp.shellfishmod.ShellfishMod;
import womp.shellfishmod.recipes.ShellfishTrapRecipe;

public class ShellfishRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ShellfishMod.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, ShellfishMod.MOD_ID);
    public static final DeferredRegister<RecipeBookCategory> RECIPE_CATEGORIES = DeferredRegister.create(Registries.RECIPE_BOOK_CATEGORY, ShellfishMod.MOD_ID);

    public static final RegistryObject<RecipeSerializer<ShellfishTrapRecipe>> TRAP_SERIALIZER = RECIPE_SERIALIZERS.register("shellfish_trap", () -> ShellfishTrapRecipe.SERIALIZER);
    public static final RegistryObject<RecipeType<ShellfishTrapRecipe>> TRAP_TYPE = RECIPE_TYPES.register(ShellfishTrapRecipe.Type.ID, () -> ShellfishTrapRecipe.Type.INSTANCE);
    public static final RegistryObject<RecipeBookCategory> TRAP_CATEGORY = RECIPE_CATEGORIES.register("trap_category", RecipeBookCategory::new);

    public static void register(BusGroup bus) {
        RECIPE_SERIALIZERS.register(bus);
        RECIPE_TYPES.register(bus);
        RECIPE_CATEGORIES.register(bus);
    }
}
