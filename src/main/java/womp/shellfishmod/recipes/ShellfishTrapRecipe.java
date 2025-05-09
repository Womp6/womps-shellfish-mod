package womp.shellfishmod.recipes;

import java.util.ArrayList;
import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.IngredientPlacement;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import womp.shellfishmod.registry.ShellfishRecipes;

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
    public boolean matches(ShellfishTrapRecipeInput input, World world) {
        if (world.isClient()) {
            return false;
        }

        for (String biomeEntry : this.biome) {
            if (biomeEntry.equals(input.getBiome()) && bait.test(input.getStackInSlot(0))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ItemStack craft(ShellfishTrapRecipeInput input, WrapperLookup registryManager) {
        return value.copy();
    }

    public ItemStack getResult() {
        Random random = Random.create();
        int length = outputs.size();
        int select = random.nextBetween(0, length - 1);
        value = outputs.get(select);
        return value.copy();
    }

    @Override
    public IngredientPlacement getIngredientPlacement() {
        return IngredientPlacement.forSingleSlot(bait);
    }

    @Override
    public RecipeBookCategory getRecipeBookCategory() {
        return ShellfishRecipes.TRAP_CATEGORY;
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
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

        public static <T> PacketCodec<RegistryByteBuf, List<T>> list(PacketCodec<RegistryByteBuf, T> elementCodec) {
            return new PacketCodec<>() {
                @Override
                public void encode(RegistryByteBuf buf, List<T> list) {
                    buf.writeVarInt(list.size());
                    for (T element : list) {
                        elementCodec.encode(buf, element);
                    }
                }
        
                @Override
                public List<T> decode(RegistryByteBuf buf) {
                    int size = buf.readVarInt();
                    List<T> list = new ArrayList<>(size);
                    for (int i = 0; i < size; i++) {
                        list.add(elementCodec.decode(buf));
                    }
                    return list;
                }
            };
        }

        public static <T> PacketCodec<RegistryByteBuf, List<T>> sList(PacketCodec<ByteBuf, T> elementCodec) {
            return new PacketCodec<>() {
                @Override
                public void encode(RegistryByteBuf buf, List<T> list) {
                    buf.writeVarInt(list.size());
                    for (T element : list) {
                        elementCodec.encode(buf, element);
                    }
                }
        
                @Override
                public List<T> decode(RegistryByteBuf buf) {
                    int size = buf.readVarInt();
                    List<T> list = new ArrayList<>(size);
                    for (int i = 0; i < size; i++) {
                        list.add(elementCodec.decode(buf));
                    }
                    return list;
                }
            };
        }

        public static final PacketCodec<RegistryByteBuf, ShellfishTrapRecipe> STREAM_CODEC = PacketCodec.tuple(
            Ingredient.PACKET_CODEC, r -> r.bait,
            list(ItemStack.PACKET_CODEC), r -> r.outputs,
            sList(PacketCodecs.STRING), r -> r.biome,  ShellfishTrapRecipe::new
        );

        @Override
        public PacketCodec<RegistryByteBuf, ShellfishTrapRecipe> packetCodec() {
            return STREAM_CODEC;
        }
    }
}
