package womp.shellfishmod.recipes;

import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

// This file was creating using help from Kaupenjoe
public class ShellfishTrapRecipe implements Recipe<SimpleInventory> {

    private final List<Ingredient> bait;
    private final List<Ingredient> outputs;
    private final List<String> biome;
    private ItemStack value;

    public ShellfishTrapRecipe(List<Ingredient> bait, List<Ingredient> outputs, List<String> biome) {
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
    public ItemStack getResult(DynamicRegistryManager registryManager) {
        Random random = Random.create();
        int length = outputs.size();
        int select = random.nextBetween(0, length - 1);
        ItemStack[] item = outputs.get(select).getMatchingStacks();
        value = item[0];
        return value.copy();
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
        DefaultedList<Ingredient> list = DefaultedList.ofSize(this.bait.size(), Ingredient.EMPTY);
        for (int i = 0; i < list.size(); i++) {
            list.set(i, this.bait.get(i));
        }
        return list;
    }

    public String[] getBiomes() {
        String[] list = new String[this.biome.size()];
        for (int i = 0; i < list.length; i++) {
            list[i] = this.biome.get(i);
        }
        return list;
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

        public static final Codec<ShellfishTrapRecipe> CODEC = RecordCodecBuilder.create(in -> in.group(
            validateAmount(Ingredient.DISALLOW_EMPTY_CODEC).fieldOf("bait").forGetter(r -> r.bait),
            validateAmount(Ingredient.DISALLOW_EMPTY_CODEC).fieldOf("outputs").forGetter(r -> r.outputs),
            Codec.STRING.listOf().fieldOf("biomes").forGetter(r -> r.biome)
        ).apply(in, ShellfishTrapRecipe::new));

        private static Codec<List<Ingredient>> validateAmount(Codec<Ingredient> delegate) {
            return Codecs.validate(Codecs.validate(
                    delegate.listOf(), list -> DataResult.success(list)
            ), list -> list.isEmpty() ? DataResult.error(() -> "Recipe has missing fields!") : DataResult.success(list));
        }

        @Override
        public Codec<ShellfishTrapRecipe> codec() {
            return CODEC;
        }

        @Override
        public ShellfishTrapRecipe read(PacketByteBuf buf) {
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(), Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromPacket(buf));
            }
            
            DefaultedList<Ingredient> out = DefaultedList.ofSize(buf.readInt(), Ingredient.EMPTY);
            for (int i = 0; i < out.size(); i++) {
                out.set(i, Ingredient.fromPacket(buf));
            }
            
            DefaultedList<String> biomes = DefaultedList.ofSize(buf.readInt(), "");
            for (int i = 0; i < biomes.size(); i++) {
                biomes.set(i, buf.readString());
            }

            return new ShellfishTrapRecipe(inputs, out, biomes);
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
