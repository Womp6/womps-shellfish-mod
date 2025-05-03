package womp.shellfishmod.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

// This file was creating using help from Kaupenjoe
public class ShellfishTrapRecipe implements Recipe<SimpleInventory> {

    private final Identifier id;
    private final DefaultedList<Ingredient> bait;
    private final DefaultedList<Ingredient> outputs;
    private final String[] biome;
    private ItemStack value;

    public ShellfishTrapRecipe(Identifier id, DefaultedList<Ingredient> outputs, DefaultedList<Ingredient> bait, String[] biome) {
        this.id = id;
        this.bait = bait;
        this.outputs = outputs;
        this.biome = biome;
    }

    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        if (world.isClient()) {
            return false;
        }

        return bait.get(0).test(inventory.getStack(0));
    }

    @Override
    public ItemStack craft(SimpleInventory inventory, DynamicRegistryManager registryManager) {
        return value.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        Random random = Random.create();
        int length = outputs.size();
        int select = random.nextBetween(0, length - 1);
        ItemStack[] item = outputs.get(select).getMatchingStacks();
        value = item[0];
        return value.copy();
    }

    @Override
    public Identifier getId() {
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
    public DefaultedList<Ingredient> getIngredients() {
        return this.bait;
    }

    public String[] getBiomes() {
        return this.biome;
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
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
        public ShellfishTrapRecipe read(Identifier id, JsonObject json) {
            JsonArray bait = JsonHelper.getArray(json, "bait");
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(1, Ingredient.EMPTY);

            JsonArray outputs = JsonHelper.getArray(json, "outputs");
            DefaultedList<Ingredient> out = DefaultedList.ofSize(outputs.size(), Ingredient.EMPTY);
            
            JsonArray biome = JsonHelper.getArray(json, "biomes");
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
        public ShellfishTrapRecipe read(Identifier id, PacketByteBuf buf) {
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(), Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromPacket(buf));
            }
            
            DefaultedList<Ingredient> out = DefaultedList.ofSize(buf.readInt(), Ingredient.EMPTY);
            for (int i = 0; i < out.size(); i++) {
                out.set(i, Ingredient.fromPacket(buf));
            }
            
            String[] biomes = new String[buf.readInt()];
            for (int i = 0; i < biomes.length; i++) {
                biomes[i] = buf.readString();
            }

            return new ShellfishTrapRecipe(id, out, inputs, biomes);
        }

        @Override
        public void write(PacketByteBuf buf, ShellfishTrapRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.write(buf);
            }
            buf.writeInt(recipe.outputs.size());
            for (Ingredient ing : recipe.outputs) {
                ing.write(buf);
            }
            buf.writeInt(recipe.getBiomes().length);
            for (String id : recipe.getBiomes()) {
                buf.writeString(id);
            }
        }
    }
}
