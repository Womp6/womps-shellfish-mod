package womp.shellfishmod.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

// This file was creating using help from Kaupenjoe
public class ShellfishTrapRecipe implements Recipe<SimpleContainer> {

    private final ResourceLocation id;
    private final NonNullList<Ingredient> bait;
    private final NonNullList<Ingredient> outputs;
    private final String[] biome;
    private ItemStack value;

    public ShellfishTrapRecipe(ResourceLocation id, NonNullList<Ingredient> outputs, NonNullList<Ingredient> bait, String[] biome) {
        this.id = id;
        this.bait = bait;
        this.outputs = outputs;
        this.biome = biome;
    }

    @Override
    public boolean matches(SimpleContainer inventory, Level world) {
        if (world.isClientSide()) {
            return false;
        }

        return bait.get(0).test(inventory.getItem(0));
    }

    @Override
    public ItemStack assemble(SimpleContainer inventory, RegistryAccess registryManager) {
        return value.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryManager) {
        RandomSource random = RandomSource.create();
        int length = outputs.size();
        int select = random.nextIntBetweenInclusive(0, length - 1);
        ItemStack[] item = outputs.get(select).getItems();
        value = item[0];
        return value.copy();
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.bait;
    }

    public String[] getBiomes() {
        return this.biome;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public static class Type implements RecipeType<ShellfishTrapRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "shellfish_trap";
    }

    public static class Serializer implements RecipeSerializer<ShellfishTrapRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "shellfish_trap";

        @Override
        public ShellfishTrapRecipe fromJson(ResourceLocation id, JsonObject json) {
            JsonArray bait = GsonHelper.getAsJsonArray(json, "bait");
            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);

            JsonArray outputs = GsonHelper.getAsJsonArray(json, "outputs");
            NonNullList<Ingredient> out = NonNullList.withSize(outputs.size(), Ingredient.EMPTY);
            
            JsonArray biome = GsonHelper.getAsJsonArray(json, "biomes");
            String[] biomes = new String[biome.size()];

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(bait.get(i)));
            }
            for (int i = 0; i < out.size(); i++) {
                out.set(i, Ingredient.fromJson(outputs.get(i)));
            }
            for (int i = 0; i < biomes.length; i++) {
                biomes[i] = biome.get(i).getAsString();
            }

            return new ShellfishTrapRecipe(id, out, inputs, biomes);
        }

        @Override
        public ShellfishTrapRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }
            
            NonNullList<Ingredient> out = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);
            for (int i = 0; i < out.size(); i++) {
                out.set(i, Ingredient.fromNetwork(buf));
            }
            
            String[] biomes = new String[buf.readInt()];
            for (int i = 0; i < biomes.length; i++) {
                biomes[i] = buf.readUtf();
            }

            return new ShellfishTrapRecipe(id, out, inputs, biomes);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, ShellfishTrapRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }
            buf.writeInt(recipe.outputs.size());
            for (Ingredient ing : recipe.outputs) {
                ing.toNetwork(buf);
            }
            buf.writeInt(recipe.getBiomes().length);
            for (String id : recipe.getBiomes()) {
                buf.writeUtf(id);
            }
        }
    }
}
