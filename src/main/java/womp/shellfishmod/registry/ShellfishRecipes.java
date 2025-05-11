package womp.shellfishmod.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import womp.shellfishmod.ShellfishMod;
import womp.shellfishmod.recipes.ShellfishTrapRecipe;

public class ShellfishRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, ShellfishMod.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, ShellfishMod.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<ShellfishTrapRecipe>> TRAP_SERIALIZER = RECIPE_SERIALIZERS.register(ShellfishTrapRecipe.Serializer.ID, () -> ShellfishTrapRecipe.Serializer.INSTANCE);
    public static final DeferredHolder<RecipeType<?>, RecipeType<ShellfishTrapRecipe>> TRAP_TYPE = RECIPE_TYPES.register(ShellfishTrapRecipe.Type.ID, () -> ShellfishTrapRecipe.Type.INSTANCE);

    public static void register(IEventBus bus) {
        RECIPE_SERIALIZERS.register(bus);
        RECIPE_TYPES.register(bus);
    }
}
