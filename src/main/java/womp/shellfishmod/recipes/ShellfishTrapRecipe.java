package womp.shellfishmod.recipes;

import java.util.ArrayList;
import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import io.netty.buffer.ByteBuf;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.util.collection.DefaultedList;
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
    public ItemStack craft(SimpleInventory inventory, WrapperLookup registryManager) {
        return value.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResult(WrapperLookup registryManager) {
        Random random = Random.create();
        int length = outputs.size();
        int select = random.nextBetween(0, length - 1);
        ItemStack[] item = outputs.get(select).getMatchingStacks();
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

        public static final MapCodec<ShellfishTrapRecipe> CODEC = RecordCodecBuilder.mapCodec(in -> in.group(
            Ingredient.DISALLOW_EMPTY_CODEC.listOf().fieldOf("bait").forGetter(r -> r.bait),
            Ingredient.DISALLOW_EMPTY_CODEC.listOf().fieldOf("outputs").forGetter(r -> r.outputs),
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
            list(Ingredient.PACKET_CODEC), r -> r.bait,
            list(Ingredient.PACKET_CODEC), r -> r.outputs,
            sList(PacketCodecs.STRING), r -> r.biome,  ShellfishTrapRecipe::new
        );

        @Override
        public PacketCodec<RegistryByteBuf, ShellfishTrapRecipe> packetCodec() {
            return STREAM_CODEC;
        }
    }
}
