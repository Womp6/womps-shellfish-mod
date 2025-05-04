package womp.shellfishmod.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

// This file was creating using help from Kaupenjoe
public class ShellfishTrapRecipe implements Recipe<SimpleContainer> {

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
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.withSize(this.bait.size(), Ingredient.EMPTY);
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

        public static final Codec<ShellfishTrapRecipe> CODEC = RecordCodecBuilder.create(in -> in.group(
                validateAmount(Ingredient.CODEC_NONEMPTY).fieldOf("bait").forGetter(r -> r.bait),
                validateAmount(Ingredient.CODEC_NONEMPTY).fieldOf("outputs").forGetter(r -> r.outputs),
                Codec.STRING.listOf().fieldOf("biomes").forGetter(r -> r.biome)
        ).apply(in, ShellfishTrapRecipe::new));

        private static Codec<List<Ingredient>> validateAmount(Codec<Ingredient> delegate) {
            return ExtraCodecs.validate(ExtraCodecs.validate(
                    delegate.listOf(), DataResult::success
            ), list -> list.isEmpty() ? DataResult.error(() -> "Recipe has missing fields!") : DataResult.success(list));
        }

        @Override
        public Codec<ShellfishTrapRecipe> codec() {
            return CODEC;
        }

        @Override
        public @Nullable ShellfishTrapRecipe fromNetwork(FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }

            NonNullList<Ingredient> out = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);
            for (int i = 0; i < out.size(); i++) {
                out.set(i, Ingredient.fromNetwork(buf));
            }

            NonNullList<String> biomes = NonNullList.withSize(buf.readInt(), "");
            for (int i = 0; i < biomes.size(); i++) {
                biomes.set(i, buf.readUtf());
            }

            return new ShellfishTrapRecipe(inputs, out, biomes);
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
