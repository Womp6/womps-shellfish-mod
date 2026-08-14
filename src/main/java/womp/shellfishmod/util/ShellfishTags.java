package womp.shellfishmod.util;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import womp.shellfishmod.ShellfishMod;

public class ShellfishTags {

    public static <T> ResourceKey<T> createKey(String name, ResourceKey<? extends Registry<T>> type) {
        return ResourceKey.create(type, Identifier.fromNamespaceAndPath(ShellfishMod.MOD_ID, name));
    }

    public static class Items {

        public static final TagKey<Item> LOBSTER_FOOD = createTag("lobster_food");
        public static final TagKey<Item> CRAYFISH_FOOD = createTag("crayfish_food");
        public static final TagKey<Item> CRAB_FOOD = createTag("crab_food");
        public static final TagKey<Item> SHRIMP_FOOD = createTag("shrimp_food");
        public static final TagKey<Item> SEA_SNAIL_FOOD = createTag("sea_snail_food");
        public static final TagKey<Item> EMPTY_TAG = createTag("empty_tag");

        private static TagKey<Item> createTag(String name) {
            return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(ShellfishMod.MOD_ID, name));
        }
    }

    public static class Blocks {

        public static final TagKey<Block> SHELLFISH_SPAWNABLE_ON = createTag("shellfish_spawnable_on");

        private static TagKey<Block> createTag(String name) {
            return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(ShellfishMod.MOD_ID, name));
        }
    }
}
