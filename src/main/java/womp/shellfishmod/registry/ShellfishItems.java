package womp.shellfishmod.registry;

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
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import womp.shellfishmod.ShellfishMod;
import womp.shellfishmod.item.DriftwoodItem;
import womp.shellfishmod.item.ReturnOnConsumptionItem;
import womp.shellfishmod.item.ShellfishBucketItem;

import java.util.function.Supplier;

public class ShellfishItems {

    //REGISTRY
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ShellfishMod.MOD_ID);

    //BLOCK ITEMS
    public static final RegistryObject<Item> CRAYFISH_EGGS = registerBI("crayfish_eggs", ShellfishBlocks.CRAYFISH_EGGS_BLOCK);
    public static final RegistryObject<Item> LOBSTER_EGGS = registerBI("lobster_eggs", ShellfishBlocks.LOBSTER_EGGS_BLOCK);
    public static final RegistryObject<Item> CRAB_EGGS = registerBI("crab_eggs", ShellfishBlocks.CRAB_EGGS_BLOCK);
    public static final RegistryObject<Item> SHRIMP_EGGS = registerBI("shrimp_eggs", ShellfishBlocks.SHRIMP_EGGS_BLOCK);
    public static final RegistryObject<Item> SEA_SNAIL_EGGS = registerBI("sea_snail_eggs", ShellfishBlocks.SEA_SNAIL_EGGS_BLOCK);

    public static final RegistryObject<Item> SEA_SNAIL_SHELL = registerBI("sea_snail_shell", ShellfishBlocks.SEA_SNAIL_SHELL_BLOCK);
    public static final RegistryObject<Item> CLAM_SHELL = registerBI("clam_shell", ShellfishBlocks.CLAM_SHELL);
    public static final RegistryObject<Item> OYSTER_SHELL = registerBI("oyster_shell", ShellfishBlocks.OYSTER_SHELL);
    public static final RegistryObject<Item> MUSSEL_SHELL = registerBI("mussel_shell", ShellfishBlocks.MUSSEL_SHELL);

    public static final RegistryObject<Item> CLAM_CLAY = registerBI("clam_clay", ShellfishBlocks.CLAM_CLAY);
    public static final RegistryObject<Item> CLAM_SAND = registerBI("clam_sand", ShellfishBlocks.CLAM_SAND);
    public static final RegistryObject<Item> CLAM_MUD = registerBI("clam_mud", ShellfishBlocks.CLAM_MUD);
    public static final RegistryObject<Item> OYSTER_CLAY = registerBI("oyster_clay", ShellfishBlocks.OYSTER_CLAY);
    public static final RegistryObject<Item> OYSTER_SAND = registerBI("oyster_sand", ShellfishBlocks.OYSTER_SAND);
    public static final RegistryObject<Item> OYSTER_MUD = registerBI("oyster_mud", ShellfishBlocks.OYSTER_MUD);
    public static final RegistryObject<Item> MUSSEL_CLAY = registerBI("mussel_clay", ShellfishBlocks.MUSSEL_CLAY);
    public static final RegistryObject<Item> MUSSEL_SAND = registerBI("mussel_sand", ShellfishBlocks.MUSSEL_SAND);
    public static final RegistryObject<Item> MUSSEL_MUD = registerBI("mussel_mud", ShellfishBlocks.MUSSEL_MUD);

    public static final RegistryObject<Item> DEAD_CLAM = registerBI("dead_clam_block", ShellfishBlocks.DEAD_CLAM_BLOCK);
    public static final RegistryObject<Item> DEAD_OYSTER = registerBI("dead_oyster_block", ShellfishBlocks.DEAD_OYSTER_BLOCK);
    public static final RegistryObject<Item> DEAD_MUSSEL = registerBI("dead_mussel_block", ShellfishBlocks.DEAD_MUSSEL_BLOCK);

    public static final RegistryObject<Item> ROCKWEED = registerBI("rockweed", ShellfishBlocks.ROCKWEED);
    public static final RegistryObject<Item> WATER_LETTUCE = ITEMS.register("water_lettuce", () -> new PlaceOnWaterBlockItem(ShellfishBlocks.WATER_LETTUCE.get(), new Item.Properties()));
    public static final RegistryObject<Item> PADDLEWEED = registerBI("paddleweed", ShellfishBlocks.PADDLEWEED);
    public static final RegistryObject<Item> EELGRASS = registerBI("eelgrass", ShellfishBlocks.EELGRASS);
    public static final RegistryObject<Item> SEA_LETTUCE = ITEMS.register("sea_lettuce", () -> new BlockItem(ShellfishBlocks.SEA_LETTUCE.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationMod((float) 0.1).build())));
    public static final RegistryObject<Item> TALL_CATTAIL = registerTallBI("tall_cattail", ShellfishBlocks.TALL_CATTAIL);
    public static final RegistryObject<Item> CATTAIL = registerBI("cattail", ShellfishBlocks.CATTAIL);
    public static final RegistryObject<Item> TALL_PICKERELWEED = registerTallBI("tall_pickerelweed", ShellfishBlocks.TALL_PICKERELWEED);
    public static final RegistryObject<Item> PICKERELWEED = registerBI("pickerelweed", ShellfishBlocks.PICKERELWEED);
    public static final RegistryObject<Item> TALL_WHEATGRASS = registerTallBI("tall_wheatgrass", ShellfishBlocks.TALL_WHEATGRASS);
    public static final RegistryObject<Item> WHEATGRASS = registerBI("wheatgrass", ShellfishBlocks.WHEATGRASS);
    public static final RegistryObject<Item> TALL_WATER_GRASS = registerTallBI("tall_water_grass", ShellfishBlocks.TALL_WATER_GRASS);
    public static final RegistryObject<Item> WATER_GRASS = registerBI("water_grass", ShellfishBlocks.WATER_GRASS);
    public static final RegistryObject<Item> DRIFTWOOD = ITEMS.register("driftwood", () -> new DriftwoodItem(ShellfishBlocks.DRIFTWOOD.get(), new Item.Properties(), 200));

    public static final RegistryObject<Item> SHELLFISH_TRAP = registerBI("shellfish_trap", ShellfishBlocks.SHELLFISH_TRAP_BLOCK);
    public static final RegistryObject<Item> REINFORCED_TRAP = registerBI("reinforced_trap", ShellfishBlocks.REINFORCED_TRAP);

    //RAW, COOKED, REGULAR
    public static final RegistryObject<Item> RAW_CRAYFISH = registerFood("raw_crayfish", 1, 0.2, MobEffects.HUNGER, 400, 0, 0.5F);
    public static final RegistryObject<Item> COOKED_CRAYFISH = registerFood("cooked_crayfish", 4, 0.7);
    public static final RegistryObject<Item> CRAYFISH = registerBasic("crayfish");
    public static final RegistryObject<Item> RAW_LOBSTER = registerFood("raw_lobster", 2, 0.3, MobEffects.HUNGER, 400, 0, 0.5F);
    public static final RegistryObject<Item> COOKED_LOBSTER = registerFood("cooked_lobster", 6, 0.8);
    public static final RegistryObject<Item> LOBSTER = registerBasic("lobster");
    public static final RegistryObject<Item> RAW_CRAB = registerFood("raw_crab", 1, 0.1, MobEffects.HUNGER, 400, 0, 0.5F);
    public static final RegistryObject<Item> COOKED_CRAB = registerFood("cooked_crab", 3, 0.6);
    public static final RegistryObject<Item> CRAB = registerBasic("crab");
    public static final RegistryObject<Item> RAW_SHRIMP = registerFood("raw_shrimp", 1, 0.2, MobEffects.HUNGER, 400, 0, 0.5F);
    public static final RegistryObject<Item> COOKED_SHRIMP = registerFood("cooked_shrimp", 3, 0.7);
    public static final RegistryObject<Item> SHRIMP = registerBasic("shrimp");
    public static final RegistryObject<Item> RAW_SEA_SNAIL = registerFood("raw_sea_snail", 1, 0.2, MobEffects.HUNGER, 400, 0, 0.5F, SEA_SNAIL_SHELL);
    public static final RegistryObject<Item> COOKED_SEA_SNAIL = registerFood("cooked_sea_snail", 3, 0.6, SEA_SNAIL_SHELL);
    public static final RegistryObject<Item> SEA_SNAIL = registerBasic("sea_snail");
    public static final RegistryObject<Item> RAW_UNI = registerFood("raw_uni", 2, 0.3);
    public static final RegistryObject<Item> COOKED_UNI = registerFood("cooked_uni", 3, 0.5);
    public static final RegistryObject<Item> SEA_URCHIN = registerFood("sea_urchin", 1, 0.1, MobEffects.HUNGER, 400, 0, 1F, MobEffects.POISON, 100, 1, 1F);
    public static final RegistryObject<Item> RAW_CLAM = registerFood("raw_clam", 1, 0.2, MobEffects.HUNGER, 400, 0, 0.5F, CLAM_SHELL);
    public static final RegistryObject<Item> COOKED_CLAM = registerFood("cooked_clam", 3, 0.5, CLAM_SHELL);
    public static final RegistryObject<Item> CLAM = registerBasic("clam");
    public static final RegistryObject<Item> RAW_OYSTER = registerFood("raw_oyster", 1, 0.2, OYSTER_SHELL);
    public static final RegistryObject<Item> COOKED_OYSTER = registerFood("cooked_oyster", 3, 0.5, OYSTER_SHELL);
    public static final RegistryObject<Item> OYSTER = registerBasic("oyster");
    public static final RegistryObject<Item> RAW_MUSSEL = registerFood("raw_mussel", 1, 0.1, MobEffects.HUNGER, 400, 0, 0.5F, MUSSEL_SHELL);
    public static final RegistryObject<Item> COOKED_MUSSEL = registerFood("cooked_mussel", 2, 0.4, MUSSEL_SHELL);
    public static final RegistryObject<Item> MUSSEL = registerBasic("mussel");

    //SPAWN EGGS AND BUCKETS
    public static final RegistryObject<Item> CRAYFISH_SPAWN_EGG = registerEgg("crayfish_spawn_egg", ShellfishEntities.CRAYFISH, 11499264, 8871950);
    public static final RegistryObject<Item> CRAYFISH_BUCKET = registerBucket("crayfish_bucket", ShellfishEntities.CRAYFISH);
    public static final RegistryObject<Item> LOBSTER_SPAWN_EGG = registerEgg("lobster_spawn_egg", ShellfishEntities.LOBSTER, 0x741617, 0x5A1213);
    public static final RegistryObject<Item> LOBSTER_BUCKET = registerBucket("lobster_bucket", ShellfishEntities.LOBSTER);
    public static final RegistryObject<Item> CRAB_SPAWN_EGG = registerEgg("crab_spawn_egg", ShellfishEntities.CRAB, 0x961b1b, 0x771313);
    public static final RegistryObject<Item> CRAB_BUCKET = registerBucket("crab_bucket", ShellfishEntities.CRAB);
    public static final RegistryObject<Item> SHRIMP_SPAWN_EGG = registerEgg("shrimp_spawn_egg", ShellfishEntities.SHRIMP, 0xcc580a, 0xb9440f);
    public static final RegistryObject<Item> SHRIMP_BUCKET = registerBucket("shrimp_bucket", ShellfishEntities.SHRIMP);
    public static final RegistryObject<Item> SEA_SNAIL_SPAWN_EGG = registerEgg("sea_snail_spawn_egg", ShellfishEntities.SEA_SNAIL, 0xb17b45, 0x553e27);
    public static final RegistryObject<Item> SEA_SNAIL_BUCKET = registerBucket("sea_snail_bucket", ShellfishEntities.SEA_SNAIL);
    public static final RegistryObject<Item> SEA_URCHIN_SPAWN_EGG = registerEgg("sea_urchin_spawn_egg", ShellfishEntities.SEA_URCHIN, 0x2e0e61, 0x200847);
    public static final RegistryObject<Item> SEA_URCHIN_BUCKET = registerBucket("sea_urchin_bucket", ShellfishEntities.SEA_URCHIN);
    public static final RegistryObject<Item> CLAM_SPAWN_EGG = registerEgg("clam_spawn_egg", ShellfishEntities.CLAM, 0x978a7c, 0x72675b);
    public static final RegistryObject<Item> CLAM_BUCKET = registerBucket("clam_bucket", ShellfishEntities.CLAM);
    public static final RegistryObject<Item> OYSTER_SPAWN_EGG = registerEgg("oyster_spawn_egg", ShellfishEntities.OYSTER, 0xbb9a69, 0xaa8e5f);
    public static final RegistryObject<Item> OYSTER_BUCKET = registerBucket("oyster_bucket", ShellfishEntities.OYSTER);
    public static final RegistryObject<Item> MUSSEL_SPAWN_EGG = registerEgg("mussel_spawn_egg", ShellfishEntities.MUSSEL, 0x42608a, 0x072049);
    public static final RegistryObject<Item> MUSSEL_BUCKET = registerBucket("mussel_bucket", ShellfishEntities.MUSSEL);
    public static final RegistryObject<Item> MOSS_BALL_SPAWN_EGG = registerEgg("moss_ball_spawn_egg", ShellfishEntities.MOSS_BALL, 0x44720c, 0x375f05);
    public static final RegistryObject<Item> MOSS_BALL_BUCKET = registerBucket("moss_ball_bucket", ShellfishEntities.MOSS_BALL);

    //BURNT
    public static final RegistryObject<Item> BURNT_CRAYFISH = registerBurnt("burnt_crayfish");
    public static final RegistryObject<Item> BURNT_LOBSTER = registerBurnt("burnt_lobster");
    public static final RegistryObject<Item> BURNT_CRAB = registerBurnt("burnt_crab");
    public static final RegistryObject<Item> BURNT_SHRIMP = registerBurnt("burnt_shrimp");
    public static final RegistryObject<Item> BURNT_SEA_SNAIL = registerBurnt("burnt_sea_snail", SEA_SNAIL_SHELL);
    public static final RegistryObject<Item> BURNT_UNI = registerBurnt("burnt_uni");
    public static final RegistryObject<Item> BURNT_CLAM = registerBurnt("burnt_clam", CLAM_SHELL);
    public static final RegistryObject<Item> BURNT_OYSTER = registerBurnt("burnt_oyster", OYSTER_SHELL);
    public static final RegistryObject<Item> BURNT_MUSSEL = registerBurnt("burnt_mussel", MUSSEL_SHELL);

    //FANCY FOOD
    public static final RegistryObject<Item> SHELLFISH_STEW = registerFoodMaxOne("shellfish_stew", 10, 0.9, Items.BOWL);
    public static final RegistryObject<Item> LIGHT_SHELLFISH_STEW = registerFoodMaxOne("light_shellfish_stew", 8, 0.9, Items.BOWL);
    public static final RegistryObject<Item> CAVIAR_BUCKET = registerFoodMaxOne("caviar_bucket", 6, 0.9, Items.BUCKET);
    public static final RegistryObject<Item> CRAYFISH_BOIL = registerFoodMaxOne("crayfish_boil", 20, 1.0, Items.BOWL);
    public static final RegistryObject<Item> GUMBO = registerFoodMaxOne("gumbo", 10, 0.9, Items.BOWL);
    public static final RegistryObject<Item> CRAYFISH_BISQUE = registerFoodMaxOne("crayfish_bisque", 11, 0.9, Items.BOWL);
    public static final RegistryObject<Item> BOILED_LOBSTER = registerFoodMaxOne("boiled_lobster", 7, 0.8, Items.BUCKET);
    public static final RegistryObject<Item> LOBSTER_ROLL = registerFood("lobster_roll", 13, 0.9);
    public static final RegistryObject<Item> LOBSTER_SALAD = registerFoodMaxOne("lobster_salad", 9, 0.8, Items.BOWL);
    public static final RegistryObject<Item> CRAB_CAKE = registerFood("crab_cake", 8, 0.7);
    public static final RegistryObject<Item> CRAB_DIP = registerFoodMaxOne("crab_dip", 6, 0.6, Items.BOWL);
    public static final RegistryObject<Item> CRAB_LOUIE = registerFoodMaxOne("crab_louie", 9, 0.8, Items.BOWL);
    public static final RegistryObject<Item> RAW_SHRIMP_SKEWER = registerFood("raw_shrimp_skewer", 3, 0.3, MobEffects.HUNGER, 400, 0, 0.5F, Items.STICK);
    public static final RegistryObject<Item> COOKED_SHRIMP_SKEWER = registerFood("cooked_shrimp_skewer", 11, 0.8, Items.STICK);
    public static final RegistryObject<Item> SHRIMP_SCAMPI = registerFoodMaxOne("shrimp_scampi", 16, 0.9, Items.BOWL);
    public static final RegistryObject<Item> SHRIMP_TACO = registerFood("shrimp_taco", 10, 0.8);
    public static final RegistryObject<Item> ESCARGOT = registerFood("escargot", 8, 0.5, SEA_SNAIL_SHELL);
    public static final RegistryObject<Item> SNAIL_GRATIN = registerFoodMaxOne("snail_gratin", 10, 0.7, Items.BOWL);
    public static final RegistryObject<Item> SNAIL_SOUP = registerFoodMaxOne("snail_soup", 5, 0.5, Items.BOWL);
    public static final RegistryObject<Item> UNI_SUSHI = registerFood("uni_sushi", 4, 0.5);
    public static final RegistryObject<Item> UNI_CHAWANMUSHI = registerFoodMaxOne("uni_chawanmushi", 7, 0.7, Items.BOWL);
    public static final RegistryObject<Item> UNI_SALAD = registerFoodMaxOne("uni_salad", 5, 0.6, Items.BOWL);
    public static final RegistryObject<Item> BAKED_CLAM = registerFood("baked_clam", 8, 0.5, CLAM_SHELL);
    public static final RegistryObject<Item> CLAM_STRIP = registerFood("clam_strip", 10, 0.6);
    public static final RegistryObject<Item> CLAM_CHOWDER = registerFoodMaxOne("clam_chowder", 10, 0.8, Items.BOWL);
    public static final RegistryObject<Item> OYSTER_ROCKEFELLER = registerFood("oyster_rockefeller", 8, 0.5, OYSTER_SHELL);
    public static final RegistryObject<Item> FRIED_OYSTER = registerFood("fried_oyster", 10, 0.5);
    public static final RegistryObject<Item> OYSTER_CHOWDER = registerFoodMaxOne("oyster_chowder", 10, 0.8, Items.BOWL);
    public static final RegistryObject<Item> STUFFED_MUSSEL = registerFood("stuffed_mussel", 7, 0.5, MUSSEL_SHELL);
    public static final RegistryObject<Item> MUSSEL_SOUP = registerFoodMaxOne("mussel_soup", 5, 0.5, Items.BOWL);
    public static final RegistryObject<Item> MOULES_MARINIERES = registerFoodMaxOne("moules_marinieres", 10, 0.8, Items.BOWL);

    //MISC
    public static final RegistryObject<Item> PEARL = registerBasic("pearl");
    public static final RegistryObject<Item> SHELLFISH_BAIT = registerBasic("shellfish_bait");
    public static final RegistryObject<Item> DRIED_SHELLFISH_BAIT = registerBasic("dried_shellfish_bait");


    private static RegistryObject<Item> registerBasic(String name) {
        return ITEMS.register(name, () -> new Item(new Item.Properties()));
    }

    private static RegistryObject<Item> registerFood(String name, int hunger, double saturationModifier) {
        return ITEMS.register(name, () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(hunger).saturationMod((float) saturationModifier).build())));
    }

    private static RegistryObject<Item> registerFood(String name, int hunger, double saturationModifier, Supplier<? extends ItemLike> item) {
        return ITEMS.register(name, () -> new ReturnOnConsumptionItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(hunger).saturationMod((float) saturationModifier).build()), item));
    }

    private static RegistryObject<Item> registerFood(String name, int hunger, double saturationModifier, ItemLike item) {
        return ITEMS.register(name, () -> new ReturnOnConsumptionItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(hunger).saturationMod((float) saturationModifier).build()), item));
    }

    private static RegistryObject<Item> registerFoodMaxOne(String name, int hunger, double saturationModifier, ItemLike item) {
        return ITEMS.register(name, () -> new ReturnOnConsumptionItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(hunger).saturationMod((float) saturationModifier).build()).stacksTo(1), item));
    }

    private static RegistryObject<Item> registerFood(String name, int hunger, double saturationModifier, MobEffect effect, int duration, int amplifier, float chance) {
        return ITEMS.register(name, () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(hunger).saturationMod((float) saturationModifier).effect(() -> new MobEffectInstance(effect, duration, amplifier), chance).build())));
    }

    private static RegistryObject<Item> registerFood(String name, int hunger, double saturationModifier, MobEffect effect, int duration, int amplifier, float chance, Supplier<? extends ItemLike> item) {
        return ITEMS.register(name, () -> new ReturnOnConsumptionItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(hunger).saturationMod((float) saturationModifier).effect(() -> new MobEffectInstance(effect, duration, amplifier), chance).build()), item));
    }

    private static RegistryObject<Item> registerFood(String name, int hunger, double saturationModifier, MobEffect effect, int duration, int amplifier, float chance, ItemLike item) {
        return ITEMS.register(name, () -> new ReturnOnConsumptionItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(hunger).saturationMod((float) saturationModifier).effect(() -> new MobEffectInstance(effect, duration, amplifier), chance).build()), item));
    }

    private static RegistryObject<Item> registerFood(String name, int hunger, double saturationModifier, MobEffect effect, int duration, int amplifier, float chance, MobEffect effect2, int duration2, int amplifier2, float chance2) {
        return ITEMS.register(name, () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(hunger).saturationMod((float) saturationModifier).effect(() -> new MobEffectInstance(effect, duration, amplifier), chance).effect(() -> new MobEffectInstance(effect2, duration2, amplifier2), chance2).build())));
    }

    private static RegistryObject<Item> registerBurnt(String name) {
        return ITEMS.register(name, () -> new Item(new Item.Properties().food((new FoodProperties.Builder()).nutrition(1).saturationMod((float) 0.1).effect(() -> new MobEffectInstance(MobEffects.POISON, 400, 1), 1F).effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 400, 0), 1F).effect(() -> new MobEffectInstance(MobEffects.HUNGER, 400, 1), 1F).build())));
    }

    private static RegistryObject<Item> registerBurnt(String name, Supplier<? extends ItemLike> item) {
        return ITEMS.register(name, () -> new ReturnOnConsumptionItem(new Item.Properties().food((new FoodProperties.Builder()).nutrition(1).saturationMod((float) 0.1).effect(() -> new MobEffectInstance(MobEffects.POISON, 400, 1), 1F).effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 400, 0), 1F).effect(() -> new MobEffectInstance(MobEffects.HUNGER, 400, 1), 1F).build()), item));
    }

    private static RegistryObject<Item> registerBucket(String name, Supplier<? extends EntityType<? extends Mob>> type) {
        return ITEMS.register(name, () -> new ShellfishBucketItem(type, Fluids.WATER, Items.BUCKET, true, new Item.Properties().stacksTo(1).craftRemainder(Items.BUCKET)));
    }

    private static RegistryObject<Item> registerEgg(String name, Supplier<? extends EntityType<? extends Mob>> type, int primaryColor, int secondaryColor) {
        return ITEMS.register(name, () -> new ForgeSpawnEggItem(type, primaryColor, secondaryColor, new Item.Properties()));
    }

    private static RegistryObject<Item> registerBI(String name, Supplier<? extends Block> block) {
        return ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private static RegistryObject<Item> registerTallBI(String name, Supplier<? extends Block> block) {
        return ITEMS.register(name, () -> new DoubleHighBlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
