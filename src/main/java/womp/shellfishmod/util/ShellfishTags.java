package womp.shellfishmod.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ShellfishTags {
    
    public static class Items {

        public static final TagKey<Item> LOBSTER_FOOD = createTag("lobster_food");
        public static final TagKey<Item> CRAYFISH_FOOD = createTag("crayfish_food");
        public static final TagKey<Item> CRAB_FOOD = createTag("crab_food");
        public static final TagKey<Item> SHRIMP_FOOD = createTag("shrimp_food");
        public static final TagKey<Item> SEA_SNAIL_FOOD = createTag("sea_snail_food");
        public static final TagKey<Item> EMPTY_TAG = createTag("empty_tag");

        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, new Identifier("shellfish", name));
        }
    }

    public static class Blocks {

        public static final TagKey<Block> SHELLFISH_SPAWNABLE_ON = createTag("shellfish_spawnable_on");

        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, new Identifier("shellfish", name));
        } 
    }
}
