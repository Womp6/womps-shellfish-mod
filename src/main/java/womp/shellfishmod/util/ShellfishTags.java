package womp.shellfishmod.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import womp.shellfishmod.ShellfishMod;

public class ShellfishTags {

    public static class Items {

        public static final TagKey<Item> LOBSTER_FOOD = createTag("lobster_food");
        public static final TagKey<Item> CRAYFISH_FOOD = createTag("crayfish_food");
        public static final TagKey<Item> CRAB_FOOD = createTag("crab_food");
        public static final TagKey<Item> SHRIMP_FOOD = createTag("shrimp_food");
        public static final TagKey<Item> SEA_SNAIL_FOOD = createTag("sea_snail_food");

        private static TagKey<Item> createTag(String name) {
            return TagKey.create(Registries.ITEM, new ResourceLocation(ShellfishMod.MOD_ID, name));
        }
    }

    public static class Blocks {

        public static final TagKey<Block> SHELLFISH_SPAWNABLE_ON = createTag("shellfish_spawnable_on");

        private static TagKey<Block> createTag(String name) {
            return TagKey.create(Registries.BLOCK, new ResourceLocation(ShellfishMod.MOD_ID, name));
        }
    }
}
