package womp.shellfishmod.registry;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluids;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import womp.shellfishmod.ShellfishMod;
import womp.shellfishmod.item.ShellfishBucketItem;

import java.util.function.Supplier;

public class ShellfishItems {

    //REGISTRY
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ShellfishMod.MOD_ID);

    //BLOCK ITEMS
    public static final DeferredItem<Item> CRAYFISH_EGGS = registerBI("crayfish_eggs", ShellfishBlocks.CRAYFISH_EGGS_BLOCK);
    public static final DeferredItem<Item> LOBSTER_EGGS = registerBI("lobster_eggs", ShellfishBlocks.LOBSTER_EGGS_BLOCK);
    public static final DeferredItem<Item> CRAB_EGGS = registerBI("crab_eggs", ShellfishBlocks.CRAB_EGGS_BLOCK);
    public static final DeferredItem<Item> SHRIMP_EGGS = registerBI("shrimp_eggs", ShellfishBlocks.SHRIMP_EGGS_BLOCK);
    public static final DeferredItem<Item> SEA_SNAIL_EGGS = registerBI("sea_snail_eggs", ShellfishBlocks.SEA_SNAIL_EGGS_BLOCK);

    public static final DeferredItem<Item> SEA_SNAIL_SHELL = registerBI("sea_snail_shell", ShellfishBlocks.SEA_SNAIL_SHELL_BLOCK);
    public static final DeferredItem<Item> CLAM_SHELL = registerBI("clam_shell", ShellfishBlocks.CLAM_SHELL);
    public static final DeferredItem<Item> OYSTER_SHELL = registerBI("oyster_shell", ShellfishBlocks.OYSTER_SHELL);
    public static final DeferredItem<Item> MUSSEL_SHELL = registerBI("mussel_shell", ShellfishBlocks.MUSSEL_SHELL);

    public static final DeferredItem<Item> CLAM_CLAY = registerBI("clam_clay", ShellfishBlocks.CLAM_CLAY);
    public static final DeferredItem<Item> CLAM_SAND = registerBI("clam_sand", ShellfishBlocks.CLAM_SAND);
    public static final DeferredItem<Item> OYSTER_CLAY = registerBI("oyster_clay", ShellfishBlocks.OYSTER_CLAY);
    public static final DeferredItem<Item> OYSTER_SAND = registerBI("oyster_sand", ShellfishBlocks.OYSTER_SAND);
    public static final DeferredItem<Item> MUSSEL_CLAY = registerBI("mussel_clay", ShellfishBlocks.MUSSEL_CLAY);
    public static final DeferredItem<Item> MUSSEL_SAND = registerBI("mussel_sand", ShellfishBlocks.MUSSEL_SAND);

    public static final DeferredItem<Item> DEAD_CLAM = registerBI("dead_clam_block", ShellfishBlocks.DEAD_CLAM_BLOCK);
    public static final DeferredItem<Item> DEAD_OYSTER = registerBI("dead_oyster_block", ShellfishBlocks.DEAD_OYSTER_BLOCK);
    public static final DeferredItem<Item> DEAD_MUSSEL = registerBI("dead_mussel_block", ShellfishBlocks.DEAD_MUSSEL_BLOCK);

    public static final DeferredItem<Item> ROCKWEED = registerBI("rockweed", ShellfishBlocks.ROCKWEED);
    public static final DeferredItem<Item> WATER_LETTUCE = ITEMS.register("water_lettuce", () -> new PlaceOnWaterBlockItem(ShellfishBlocks.WATER_LETTUCE.get(), new Item.Properties()));
    public static final DeferredItem<Item> PADDLEWEED = registerBI("paddleweed", ShellfishBlocks.PADDLEWEED);
    public static final DeferredItem<Item> EELGRASS = registerBI("eelgrass", ShellfishBlocks.EELGRASS);
    public static final DeferredItem<Item> SEA_LETTUCE = ITEMS.register("sea_lettuce", () -> new BlockItem(ShellfishBlocks.SEA_LETTUCE.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationModifier((float) 0.1).build())));

    public static final DeferredItem<Item> SHELLFISH_TRAP = registerBI("shellfish_trap", ShellfishBlocks.SHELLFISH_TRAP_BLOCK);
    public static final DeferredItem<Item> REINFORCED_TRAP = registerBI("reinforced_trap", ShellfishBlocks.REINFORCED_TRAP);

    //RAW, COOKED, REGULAR
    public static final DeferredItem<Item> RAW_CRAYFISH = registerFood("raw_crayfish", 1, 0.2, MobEffects.HUNGER, 400, 0, 0.5F);
    public static final DeferredItem<Item> COOKED_CRAYFISH = registerFood("cooked_crayfish", 4, 0.7);
    public static final DeferredItem<Item> CRAYFISH = registerBasic("crayfish");
    public static final DeferredItem<Item> RAW_LOBSTER = registerFood("raw_lobster", 2, 0.3, MobEffects.HUNGER, 400, 0, 0.5F);
    public static final DeferredItem<Item> COOKED_LOBSTER = registerFood("cooked_lobster", 6, 0.8);
    public static final DeferredItem<Item> LOBSTER = registerBasic("lobster");
    public static final DeferredItem<Item> RAW_CRAB = registerFood("raw_crab", 1, 0.1, MobEffects.HUNGER, 400, 0, 0.5F);
    public static final DeferredItem<Item> COOKED_CRAB = registerFood("cooked_crab", 3, 0.6);
    public static final DeferredItem<Item> CRAB = registerBasic("crab");
    public static final DeferredItem<Item> RAW_SHRIMP = registerFood("raw_shrimp", 1, 0.2, MobEffects.HUNGER, 400, 0, 0.5F);
    public static final DeferredItem<Item> COOKED_SHRIMP = registerFood("cooked_shrimp", 3, 0.7);
    public static final DeferredItem<Item> SHRIMP = registerBasic("shrimp");
    public static final DeferredItem<Item> RAW_SEA_SNAIL = registerFood("raw_sea_snail", 1, 0.2, MobEffects.HUNGER, 400, 0, 0.5F, SEA_SNAIL_SHELL);
    public static final DeferredItem<Item> COOKED_SEA_SNAIL = registerFood("cooked_sea_snail", 3, 0.6, SEA_SNAIL_SHELL);
    public static final DeferredItem<Item> SEA_SNAIL = registerBasic("sea_snail");
    public static final DeferredItem<Item> RAW_UNI = registerFood("raw_uni", 2, 0.3);
    public static final DeferredItem<Item> COOKED_UNI = registerFood("cooked_uni", 3, 0.5);
    public static final DeferredItem<Item> SEA_URCHIN = registerFood("sea_urchin", 1, 0.1, MobEffects.HUNGER, 400, 0, 1F, MobEffects.POISON, 100, 1, 1F);
    public static final DeferredItem<Item> RAW_CLAM = registerFood("raw_clam", 1, 0.2, MobEffects.HUNGER, 400, 0, 0.5F, CLAM_SHELL);
    public static final DeferredItem<Item> COOKED_CLAM = registerFood("cooked_clam", 3, 0.5, CLAM_SHELL);
    public static final DeferredItem<Item> CLAM = registerBasic("clam");
    public static final DeferredItem<Item> RAW_OYSTER = registerFood("raw_oyster", 1, 0.2, OYSTER_SHELL);
    public static final DeferredItem<Item> COOKED_OYSTER = registerFood("cooked_oyster", 3, 0.5, OYSTER_SHELL);
    public static final DeferredItem<Item> OYSTER = registerBasic("oyster");
    public static final DeferredItem<Item> RAW_MUSSEL = registerFood("raw_mussel", 1, 0.1, MobEffects.HUNGER, 400, 0, 0.5F, MUSSEL_SHELL);
    public static final DeferredItem<Item> COOKED_MUSSEL = registerFood("cooked_mussel", 2, 0.4, MUSSEL_SHELL);
    public static final DeferredItem<Item> MUSSEL = registerBasic("mussel");

    //SPAWN EGGS AND BUCKETS
    public static final DeferredItem<Item> CRAYFISH_SPAWN_EGG = registerEgg("crayfish_spawn_egg", ShellfishEntities.CRAYFISH, 11499264, 8871950);
    public static final DeferredItem<Item> CRAYFISH_BUCKET = registerBucket("crayfish_bucket", ShellfishEntities.CRAYFISH);
    public static final DeferredItem<Item> LOBSTER_SPAWN_EGG = registerEgg("lobster_spawn_egg", ShellfishEntities.LOBSTER, 0x741617, 0x5A1213);
    public static final DeferredItem<Item> LOBSTER_BUCKET = registerBucket("lobster_bucket", ShellfishEntities.LOBSTER);
    public static final DeferredItem<Item> CRAB_SPAWN_EGG = registerEgg("crab_spawn_egg", ShellfishEntities.CRAB, 0x961b1b, 0x771313);
    public static final DeferredItem<Item> CRAB_BUCKET = registerBucket("crab_bucket", ShellfishEntities.CRAB);
    public static final DeferredItem<Item> SHRIMP_SPAWN_EGG = registerEgg("shrimp_spawn_egg", ShellfishEntities.SHRIMP, 0xcc580a, 0xb9440f);
    public static final DeferredItem<Item> SHRIMP_BUCKET = registerBucket("shrimp_bucket", ShellfishEntities.SHRIMP);
    public static final DeferredItem<Item> SEA_SNAIL_SPAWN_EGG = registerEgg("sea_snail_spawn_egg", ShellfishEntities.SEA_SNAIL, 0xb17b45, 0x553e27);
    public static final DeferredItem<Item> SEA_SNAIL_BUCKET = registerBucket("sea_snail_bucket", ShellfishEntities.SEA_SNAIL);
    public static final DeferredItem<Item> SEA_URCHIN_SPAWN_EGG = registerEgg("sea_urchin_spawn_egg", ShellfishEntities.SEA_URCHIN, 0x2e0e61, 0x200847);
    public static final DeferredItem<Item> SEA_URCHIN_BUCKET = registerBucket("sea_urchin_bucket", ShellfishEntities.SEA_URCHIN);
    public static final DeferredItem<Item> CLAM_SPAWN_EGG = registerEgg("clam_spawn_egg", ShellfishEntities.CLAM, 0x978a7c, 0x72675b);
    public static final DeferredItem<Item> CLAM_BUCKET = registerBucket("clam_bucket", ShellfishEntities.CLAM);
    public static final DeferredItem<Item> OYSTER_SPAWN_EGG = registerEgg("oyster_spawn_egg", ShellfishEntities.OYSTER, 0xbb9a69, 0xaa8e5f);
    public static final DeferredItem<Item> OYSTER_BUCKET = registerBucket("oyster_bucket", ShellfishEntities.OYSTER);
    public static final DeferredItem<Item> MUSSEL_SPAWN_EGG = registerEgg("mussel_spawn_egg", ShellfishEntities.MUSSEL, 0x42608a, 0x072049);
    public static final DeferredItem<Item> MUSSEL_BUCKET = registerBucket("mussel_bucket", ShellfishEntities.MUSSEL);
    public static final DeferredItem<Item> MOSS_BALL_SPAWN_EGG = registerEgg("moss_ball_spawn_egg", ShellfishEntities.MOSS_BALL, 0x44720c, 0x375f05);
    public static final DeferredItem<Item> MOSS_BALL_BUCKET = registerBucket("moss_ball_bucket", ShellfishEntities.MOSS_BALL);

    //BURNT
    public static final DeferredItem<Item> BURNT_CRAYFISH = registerBurnt("burnt_crayfish");
    public static final DeferredItem<Item> BURNT_LOBSTER = registerBurnt("burnt_lobster");
    public static final DeferredItem<Item> BURNT_CRAB = registerBurnt("burnt_crab");
    public static final DeferredItem<Item> BURNT_SHRIMP = registerBurnt("burnt_shrimp");
    public static final DeferredItem<Item> BURNT_SEA_SNAIL = registerBurnt("burnt_sea_snail", SEA_SNAIL_SHELL);
    public static final DeferredItem<Item> BURNT_UNI = registerBurnt("burnt_uni");
    public static final DeferredItem<Item> BURNT_CLAM = registerBurnt("burnt_clam", CLAM_SHELL);
    public static final DeferredItem<Item> BURNT_OYSTER = registerBurnt("burnt_oyster", OYSTER_SHELL);
    public static final DeferredItem<Item> BURNT_MUSSEL = registerBurnt("burnt_mussel", MUSSEL_SHELL);

    //FANCY FOOD
    public static final DeferredItem<Item> SHELLFISH_STEW = registerFoodMaxOne("shellfish_stew", 10, 0.9, Items.BOWL);
    public static final DeferredItem<Item> LIGHT_SHELLFISH_STEW = registerFoodMaxOne("light_shellfish_stew", 8, 0.9, Items.BOWL);
    public static final DeferredItem<Item> CAVIAR_BUCKET = registerFoodMaxOne("caviar_bucket", 6, 0.9, Items.BUCKET);
    public static final DeferredItem<Item> CRAYFISH_BOIL = registerFoodMaxOne("crayfish_boil", 20, 1.0, Items.BOWL);
    public static final DeferredItem<Item> GUMBO = registerFoodMaxOne("gumbo", 10, 0.9, Items.BOWL);
    public static final DeferredItem<Item> CRAYFISH_BISQUE = registerFoodMaxOne("crayfish_bisque", 11, 0.9, Items.BOWL);
    public static final DeferredItem<Item> BOILED_LOBSTER = registerFoodMaxOne("boiled_lobster", 7, 0.8, Items.BUCKET);
    public static final DeferredItem<Item> LOBSTER_ROLL = registerFood("lobster_roll", 13, 0.9);
    public static final DeferredItem<Item> LOBSTER_SALAD = registerFoodMaxOne("lobster_salad", 9, 0.8, Items.BOWL);
    public static final DeferredItem<Item> CRAB_CAKE = registerFood("crab_cake", 8, 0.7);
    public static final DeferredItem<Item> CRAB_DIP = registerFoodMaxOne("crab_dip", 6, 0.6, Items.BOWL);
    public static final DeferredItem<Item> CRAB_LOUIE = registerFoodMaxOne("crab_louie", 9, 0.8, Items.BOWL);
    public static final DeferredItem<Item> RAW_SHRIMP_SKEWER = registerFood("raw_shrimp_skewer", 3, 0.3, MobEffects.HUNGER, 400, 0, 0.5F, Items.STICK);
    public static final DeferredItem<Item> COOKED_SHRIMP_SKEWER = registerFood("cooked_shrimp_skewer", 11, 0.8, Items.STICK);
    public static final DeferredItem<Item> SHRIMP_SCAMPI = registerFoodMaxOne("shrimp_scampi", 16, 0.9, Items.BOWL);
    public static final DeferredItem<Item> SHRIMP_TACO = registerFood("shrimp_taco", 10, 0.8);
    public static final DeferredItem<Item> ESCARGOT = registerFood("escargot", 8, 0.5, SEA_SNAIL_SHELL);
    public static final DeferredItem<Item> SNAIL_GRATIN = registerFoodMaxOne("snail_gratin", 10, 0.7, Items.BOWL);
    public static final DeferredItem<Item> SNAIL_SOUP = registerFoodMaxOne("snail_soup", 5, 0.5, Items.BOWL);
    public static final DeferredItem<Item> UNI_SUSHI = registerFood("uni_sushi", 4, 0.5);
    public static final DeferredItem<Item> UNI_CHAWANMUSHI = registerFoodMaxOne("uni_chawanmushi", 7, 0.7, Items.BOWL);
    public static final DeferredItem<Item> UNI_SALAD = registerFoodMaxOne("uni_salad", 5, 0.6, Items.BOWL);
    public static final DeferredItem<Item> BAKED_CLAM = registerFood("baked_clam", 8, 0.5, CLAM_SHELL);
    public static final DeferredItem<Item> CLAM_STRIP = registerFood("clam_strip", 10, 0.6);
    public static final DeferredItem<Item> CLAM_CHOWDER = registerFoodMaxOne("clam_chowder", 10, 0.8, Items.BOWL);
    public static final DeferredItem<Item> OYSTER_ROCKEFELLER = registerFood("oyster_rockefeller", 8, 0.5, OYSTER_SHELL);
    public static final DeferredItem<Item> FRIED_OYSTER = registerFood("fried_oyster", 10, 0.5);
    public static final DeferredItem<Item> OYSTER_CHOWDER = registerFoodMaxOne("oyster_chowder", 10, 0.8, Items.BOWL);
    public static final DeferredItem<Item> STUFFED_MUSSEL = registerFood("stuffed_mussel", 7, 0.5, MUSSEL_SHELL);
    public static final DeferredItem<Item> MUSSEL_SOUP = registerFoodMaxOne("mussel_soup", 5, 0.5, Items.BOWL);
    public static final DeferredItem<Item> MOULES_MARINIERES = registerFoodMaxOne("moules_marinieres", 10, 0.8, Items.BOWL);

    //MISC
    public static final DeferredItem<Item> PEARL = registerBasic("pearl");
    public static final DeferredItem<Item> SHELLFISH_BAIT = registerBasic("shellfish_bait");
    public static final DeferredItem<Item> DRIED_SHELLFISH_BAIT = registerBasic("dried_shellfish_bait");


    private static DeferredItem<Item> registerBasic(String name) {
        return ITEMS.register(name, () -> new Item(new Item.Properties()));
    }

    private static DeferredItem<Item> registerFood(String name, int hunger, double saturationModifier) {
        return ITEMS.register(name, () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(hunger).saturationModifier((float) saturationModifier).build())));
    }

    private static DeferredItem<Item> registerFood(String name, int hunger, double saturationModifier, ItemLike item) {
        return ITEMS.register(name, () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(hunger).saturationModifier((float) saturationModifier).usingConvertsTo(item).build())));
    }

    private static DeferredItem<Item> registerFoodMaxOne(String name, int hunger, double saturationModifier, ItemLike item) {
        return ITEMS.register(name, () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(hunger).saturationModifier((float) saturationModifier).usingConvertsTo(item).build()).stacksTo(1)));
    }

    private static DeferredItem<Item> registerFood(String name, int hunger, double saturationModifier, Holder<MobEffect> effect, int duration, int amplifier, float chance) {
        return ITEMS.register(name, () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(hunger).saturationModifier((float) saturationModifier).effect(new MobEffectInstance(effect, duration, amplifier), chance).build())));
    }

    private static DeferredItem<Item> registerFood(String name, int hunger, double saturationModifier, Holder<MobEffect> effect, int duration, int amplifier, float chance, ItemLike item) {
        return ITEMS.register(name, () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(hunger).saturationModifier((float) saturationModifier).effect(new MobEffectInstance(effect, duration, amplifier), chance).usingConvertsTo(item).build())));
    }

    private static DeferredItem<Item> registerFood(String name, int hunger, double saturationModifier, Holder<MobEffect> effect, int duration, int amplifier, float chance, Holder<MobEffect> effect2, int duration2, int amplifier2, float chance2) {
        return ITEMS.register(name, () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(hunger).saturationModifier((float) saturationModifier).effect(new MobEffectInstance(effect, duration, amplifier), chance).effect(new MobEffectInstance(effect2, duration2, amplifier2), chance2).build())));
    }

    private static DeferredItem<Item> registerBurnt(String name) {
        return ITEMS.register(name, () -> new Item(new Item.Properties().food((new FoodProperties.Builder()).nutrition(1).saturationModifier((float) 0.1).effect(new MobEffectInstance(MobEffects.POISON, 400, 1), 1F).effect(new MobEffectInstance(MobEffects.CONFUSION, 400, 0), 1F).effect(new MobEffectInstance(MobEffects.HUNGER, 400, 1), 1F).build())));
    }

    private static DeferredItem<Item> registerBurnt(String name, Supplier<? extends ItemLike> item) {
        return ITEMS.register(name, () -> new Item(new Item.Properties().food((new FoodProperties.Builder()).nutrition(1).saturationModifier((float) 0.1).effect(new MobEffectInstance(MobEffects.POISON, 400, 1), 1F).effect(new MobEffectInstance(MobEffects.CONFUSION, 400, 0), 1F).effect(new MobEffectInstance(MobEffects.HUNGER, 400, 1), 1F).usingConvertsTo(item.get()).build())));
    }

    private static DeferredItem<Item> registerBucket(String name, Supplier<? extends EntityType<? extends Mob>> type) {
        return ITEMS.register(name, () -> new ShellfishBucketItem(type, Fluids.WATER, new Item.Properties().stacksTo(1).craftRemainder(Items.BUCKET)));
    }

    private static DeferredItem<Item> registerEgg(String name, Supplier<? extends EntityType<? extends Mob>> type, int primaryColor, int secondaryColor) {
        return ITEMS.register(name, () -> new DeferredSpawnEggItem(type, primaryColor, secondaryColor, new Item.Properties()));
    }

    private static DeferredItem<Item> registerBI(String name, Supplier<? extends Block> block) {
        return ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
