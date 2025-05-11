package womp.shellfishmod.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

// This file was creating using help from Kaupenjoe
public class ShellfishTrapRecipe implements Recipe<ShellfishTrapRecipeInput> {

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
    public boolean matches(ShellfishTrapRecipeInput inventory, Level world) {
        if (world.isClientSide()) {
            return false;
        }

        return bait.get(0).test(inventory.getItem(0));
    }

    @Override
    public ItemStack assemble(ShellfishTrapRecipeInput inventory, HolderLookup.Provider provider) {
        return value.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        RandomSource random = RandomSource.create();
        int length = outputs.size();
        int select = random.nextIntBetweenInclusive(0, length - 1);
        ItemStack[] item = outputs.get(select).getItems();
        ItemStack value = item[0];
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

        public static final MapCodec<ShellfishTrapRecipe> CODEC = RecordCodecBuilder.mapCodec(in -> in.group(
                Ingredient.CODEC_NONEMPTY.listOf().fieldOf("bait").forGetter(r -> r.bait),
                Ingredient.CODEC_NONEMPTY.listOf().fieldOf("outputs").forGetter(r -> r.outputs),
                Codec.STRING.listOf().fieldOf("biomes").forGetter(r -> r.biome)
        ).apply(in, ShellfishTrapRecipe::new));

        @Override
        public MapCodec<ShellfishTrapRecipe> codec() {
            return CODEC;
        }

        public static <T> StreamCodec<RegistryFriendlyByteBuf, List<T>> list(StreamCodec<RegistryFriendlyByteBuf, T> elementCodec) {
            return new StreamCodec<>() {
                @Override
                public void encode(RegistryFriendlyByteBuf buf, List<T> list) {
                    buf.writeVarInt(list.size());
                    for (T element : list) {
                        elementCodec.encode(buf, element);
                    }
                }

                @Override
                public List<T> decode(RegistryFriendlyByteBuf buf) {
                    int size = buf.readVarInt();
                    List<T> list = new ArrayList<>(size);
                    for (int i = 0; i < size; i++) {
                        list.add(elementCodec.decode(buf));
                    }
                    return list;
                }
            };
        }

        public static <T> StreamCodec<RegistryFriendlyByteBuf, List<T>> sList(StreamCodec<ByteBuf, T> elementCodec) {
            return new StreamCodec<>() {
                @Override
                public void encode(RegistryFriendlyByteBuf buf, List<T> list) {
                    buf.writeVarInt(list.size());
                    for (T element : list) {
                        elementCodec.encode(buf, element);
                    }
                }

                @Override
                public List<T> decode(RegistryFriendlyByteBuf buf) {
                    int size = buf.readVarInt();
                    List<T> list = new ArrayList<>(size);
                    for (int i = 0; i < size; i++) {
                        list.add(elementCodec.decode(buf));
                    }
                    return list;
                }
            };
        }

        public static final StreamCodec<RegistryFriendlyByteBuf, ShellfishTrapRecipe> STREAM_CODEC = StreamCodec.composite(
                list(Ingredient.CONTENTS_STREAM_CODEC), r -> r.bait,
                list(Ingredient.CONTENTS_STREAM_CODEC), r -> r.outputs,
                sList(ByteBufCodecs.STRING_UTF8), r -> r.biome,  ShellfishTrapRecipe::new
        );

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ShellfishTrapRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
