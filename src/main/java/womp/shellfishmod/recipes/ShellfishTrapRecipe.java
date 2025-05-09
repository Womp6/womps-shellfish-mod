package womp.shellfishmod.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import womp.shellfishmod.registry.ShellfishRecipes;

import java.util.ArrayList;
import java.util.List;

// This file was creating using help from Kaupenjoe
public class ShellfishTrapRecipe implements Recipe<ShellfishTrapRecipeInput> {

    private final Ingredient bait;
    private final List<ItemStack> outputs;
    private final List<String> biome;
    private ItemStack value;

    public ShellfishTrapRecipe(Ingredient bait, List<ItemStack> outputs, List<String> biome) {
        this.bait = bait;
        this.outputs = outputs;
        this.biome = biome;
    }

    @Override
    public boolean matches(ShellfishTrapRecipeInput input, Level world) {
        if (world.isClientSide()) {
            return false;
        }

        for (String biomeEntry : this.biome) {
            if (biomeEntry.equals(input.getBiome()) && bait.test(input.getItem(0))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ItemStack assemble(ShellfishTrapRecipeInput inventory, HolderLookup.Provider provider) {
        return value.copy();
    }

    public ItemStack getResultItem() {
        RandomSource random = RandomSource.create();
        int length = outputs.size();
        int select = random.nextIntBetweenInclusive(0, length - 1);
        value = outputs.get(select);
        return value.copy();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.create(bait);
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return ShellfishRecipes.TRAP_CATEGORY.get();
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public RecipeSerializer<ShellfishTrapRecipe> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<ShellfishTrapRecipe> getType() {
        return Type.INSTANCE;
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
                Ingredient.CODEC.fieldOf("bait").forGetter(r -> r.bait),
                ItemStack.CODEC.listOf().fieldOf("outputs").forGetter(r -> r.outputs),
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
                Ingredient.CONTENTS_STREAM_CODEC, r -> r.bait,
                list(ItemStack.STREAM_CODEC), r -> r.outputs,
                sList(ByteBufCodecs.STRING_UTF8), r -> r.biome,  ShellfishTrapRecipe::new
        );

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ShellfishTrapRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
