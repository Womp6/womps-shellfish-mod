package womp.shellfishmod.recipes;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import io.netty.buffer.ByteBuf;
import womp.shellfishmod.registry.ShellfishRecipes;

// This file was creating using help from Kaupenjoe
public class ShellfishTrapRecipe implements Recipe<ShellfishTrapRecipeInput> {

    private final Ingredient bait;
    private final List<ItemStackTemplate> outputs;
    private final List<String> biome;
    private ItemStack value;

    public static final MapCodec<ShellfishTrapRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(in -> in.group(
        Ingredient.CODEC.fieldOf("bait").forGetter(r -> r.bait),
        ItemStackTemplate.CODEC.listOf().fieldOf("outputs").forGetter(r -> r.outputs),
        Codec.STRING.listOf().fieldOf("biomes").forGetter(r -> r.biome)
    ).apply(in, ShellfishTrapRecipe::new)); 
    public static final StreamCodec<RegistryFriendlyByteBuf, ShellfishTrapRecipe> STREAM_CODEC = StreamCodec.composite(
        Ingredient.CONTENTS_STREAM_CODEC, r -> r.bait,
        list(ItemStackTemplate.STREAM_CODEC), r -> r.outputs,
        sList(ByteBufCodecs.STRING_UTF8), r -> r.biome,  ShellfishTrapRecipe::new
    );
    public static final RecipeSerializer<ShellfishTrapRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    public ShellfishTrapRecipe(Ingredient bait, List<ItemStackTemplate> outputs, List<String> biome) {
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
    public ItemStack assemble(ShellfishTrapRecipeInput input) {
        return value.copy();
    }

    public ItemStack getResult() {
        RandomSource random = RandomSource.create();
        int length = outputs.size();
        int select = random.nextIntBetweenInclusive(0, length - 1);
        value = outputs.get(select).create();
        return value.copy();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.create(bait);
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return ShellfishRecipes.TRAP_CATEGORY;
    }

    @Override
    public boolean isSpecial() {
		return true;
	}

    @Override
    public RecipeSerializer<ShellfishTrapRecipe> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public RecipeType<ShellfishTrapRecipe> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<ShellfishTrapRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "shellfish_trap";
        
        @Override
        public String toString() {
            return ID;
        }
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

    @Override
    public boolean showNotification() {
        return false;
    }

    @Override
    public String group() {
        return "";
    }
}
