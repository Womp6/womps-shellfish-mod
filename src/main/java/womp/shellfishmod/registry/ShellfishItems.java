package womp.shellfishmod.registry;

import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.ConsumableComponent.Builder;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.item.consume.UseAction;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import womp.shellfishmod.item.CrayfishItem;
import womp.shellfishmod.item.ShellfishBucketItem;

public class ShellfishItems {

    //RAW, COOKED, REGULAR
    public static final Item RAW_CRAYFISH = registerFood("raw_crayfish", 1, 0.2, StatusEffects.HUNGER, 400, 0, 0.5F);
    public static final Item COOKED_CRAYFISH = registerFood("cooked_crayfish", 4, 0.7);
    public static final Item CRAYFISH = registerItem("crayfish", new CrayfishItem(new Item.Settings().registryKey(ShellfishUtil.createKey("crayfish", RegistryKeys.ITEM))));
    public static final Item RAW_LOBSTER = registerFood("raw_lobster", 2, 0.3, StatusEffects.HUNGER, 400, 0, 0.5F);
    public static final Item COOKED_LOBSTER = registerFood("cooked_lobster", 6, 0.8);
    public static final Item LOBSTER = registerBasic("lobster");
    public static final Item RAW_CRAB = registerFood("raw_crab", 1, 0.1, StatusEffects.HUNGER, 400, 0, 0.5F);
    public static final Item COOKED_CRAB = registerFood("cooked_crab", 3, 0.6);
    public static final Item CRAB = registerBasic("crab");
    public static final Item RAW_SHRIMP = registerFood("raw_shrimp", 1, 0.2, StatusEffects.HUNGER, 400, 0, 0.5F);
    public static final Item COOKED_SHRIMP = registerFood("cooked_shrimp", 3, 0.7);
    public static final Item SHRIMP = registerBasic("shrimp");
    public static final Item RAW_SEA_SNAIL = registerFood("raw_sea_snail", 1, 0.2, StatusEffects.HUNGER, 400, 0, 0.5F, ShellfishBlocks.SEA_SNAIL_SHELL_BLOCK.asItem());
    public static final Item COOKED_SEA_SNAIL = registerFood("cooked_sea_snail", 3, 0.6, ShellfishBlocks.SEA_SNAIL_SHELL_BLOCK.asItem());
    public static final Item SEA_SNAIL = registerBasic("sea_snail");
    public static final Item RAW_UNI = registerFood("raw_uni", 2, 0.3);
    public static final Item COOKED_UNI = registerFood("cooked_uni", 3, 0.5);
    public static final Item SEA_URCHIN = registerFood("sea_urchin", 1, 0.1, StatusEffects.HUNGER, 400, 0, 1F, StatusEffects.POISON, 100, 1, 1F);
    public static final Item RAW_CLAM = registerFood("raw_clam", 1, 0.2, StatusEffects.HUNGER, 400, 0, 0.5F, ShellfishBlocks.CLAM_SHELL.asItem());
    public static final Item COOKED_CLAM = registerFood("cooked_clam", 3, 0.5, ShellfishBlocks.CLAM_SHELL.asItem());
    public static final Item CLAM = registerBasic("clam");
    public static final Item RAW_OYSTER = registerFood("raw_oyster", 1, 0.2, ShellfishBlocks.OYSTER_SHELL.asItem());
    public static final Item COOKED_OYSTER = registerFood("cooked_oyster", 3, 0.5, ShellfishBlocks.OYSTER_SHELL.asItem());
    public static final Item OYSTER = registerBasic("oyster");
    public static final Item RAW_MUSSEL = registerFood("raw_mussel", 1, 0.1, StatusEffects.HUNGER, 400, 0, 0.5F, ShellfishBlocks.MUSSEL_SHELL.asItem());
    public static final Item COOKED_MUSSEL = registerFood("cooked_mussel", 2, 0.4, ShellfishBlocks.MUSSEL_SHELL.asItem());
    public static final Item MUSSEL = registerBasic("mussel");

    //SPAWN EGGS AND BUCKETS
    public static final Item CRAYFISH_SPAWN_EGG = registerEgg("crayfish_spawn_egg", ShellfishEntities.CRAYFISH);
    public static final Item CRAYFISH_BUCKET = registerBucket("crayfish_bucket", ShellfishEntities.CRAYFISH);
    public static final Item LOBSTER_SPAWN_EGG = registerEgg("lobster_spawn_egg", ShellfishEntities.LOBSTER);
    public static final Item LOBSTER_BUCKET = registerBucket("lobster_bucket", ShellfishEntities.LOBSTER);
    public static final Item CRAB_SPAWN_EGG = registerEgg("crab_spawn_egg", ShellfishEntities.CRAB);
    public static final Item CRAB_BUCKET = registerBucket("crab_bucket", ShellfishEntities.CRAB);
    public static final Item SHRIMP_SPAWN_EGG = registerEgg("shrimp_spawn_egg", ShellfishEntities.SHRIMP);
    public static final Item SHRIMP_BUCKET = registerBucket("shrimp_bucket", ShellfishEntities.SHRIMP);
    public static final Item SEA_SNAIL_SPAWN_EGG = registerEgg("sea_snail_spawn_egg", ShellfishEntities.SEA_SNAIL);
    public static final Item SEA_SNAIL_BUCKET = registerBucket("sea_snail_bucket", ShellfishEntities.SEA_SNAIL);
    public static final Item SEA_URCHIN_SPAWN_EGG = registerEgg("sea_urchin_spawn_egg", ShellfishEntities.SEA_URCHIN);
    public static final Item SEA_URCHIN_BUCKET = registerBucket("sea_urchin_bucket", ShellfishEntities.SEA_URCHIN);
    public static final Item CLAM_SPAWN_EGG = registerEgg("clam_spawn_egg", ShellfishEntities.CLAM);
    public static final Item CLAM_BUCKET = registerBucket("clam_bucket", ShellfishEntities.CLAM);
    public static final Item OYSTER_SPAWN_EGG = registerEgg("oyster_spawn_egg", ShellfishEntities.OYSTER);
    public static final Item OYSTER_BUCKET = registerBucket("oyster_bucket", ShellfishEntities.OYSTER);
    public static final Item MUSSEL_SPAWN_EGG = registerEgg("mussel_spawn_egg", ShellfishEntities.MUSSEL);
    public static final Item MUSSEL_BUCKET = registerBucket("mussel_bucket", ShellfishEntities.MUSSEL);
    public static final Item MOSS_BALL_SPAWN_EGG = registerEgg("moss_ball_spawn_egg", ShellfishEntities.MOSS_BALL);
    public static final Item MOSS_BALL_BUCKET = registerBucket("moss_ball_bucket", ShellfishEntities.MOSS_BALL);
    
    //BURNT
    public static final Item BURNT_CRAYFISH = registerBurnt("burnt_crayfish");
    public static final Item BURNT_LOBSTER = registerBurnt("burnt_lobster");
    public static final Item BURNT_CRAB = registerBurnt("burnt_crab");
    public static final Item BURNT_SHRIMP = registerBurnt("burnt_shrimp");
    public static final Item BURNT_SEA_SNAIL = registerBurnt("burnt_sea_snail", ShellfishBlocks.SEA_SNAIL_SHELL_BLOCK.asItem());
    public static final Item BURNT_UNI = registerBurnt("burnt_uni");
    public static final Item BURNT_CLAM = registerBurnt("burnt_clam", ShellfishBlocks.CLAM_SHELL.asItem());
    public static final Item BURNT_OYSTER = registerBurnt("burnt_oyster", ShellfishBlocks.OYSTER_SHELL.asItem());
    public static final Item BURNT_MUSSEL = registerBurnt("burnt_mussel", ShellfishBlocks.MUSSEL_SHELL.asItem());
    
    //FANCY FOOD
    public static final Item SHELLFISH_STEW = registerFoodMaxOne("shellfish_stew", 10, 0.9, Items.BOWL);
    public static final Item LIGHT_SHELLFISH_STEW = registerFoodMaxOne("light_shellfish_stew", 8, 0.9, Items.BOWL);
    public static final Item CAVIAR_BUCKET = registerFoodMaxOne("caviar_bucket", 6, 0.9, Items.BUCKET);
    public static final Item CRAYFISH_BOIL = registerFoodMaxOne("crayfish_boil", 20, 1.0, Items.BOWL);
    public static final Item GUMBO = registerFoodMaxOne("gumbo", 10, 0.9, Items.BOWL);
    public static final Item CRAYFISH_BISQUE = registerFoodMaxOne("crayfish_bisque", 11, 0.9, Items.BOWL);
    public static final Item BOILED_LOBSTER = registerFoodMaxOne("boiled_lobster", 7, 0.8, Items.BUCKET);
    public static final Item LOBSTER_ROLL = registerFood("lobster_roll", 13, 0.9);
    public static final Item LOBSTER_SALAD = registerFoodMaxOne("lobster_salad", 9, 0.8, Items.BOWL);
    public static final Item CRAB_CAKE = registerFood("crab_cake", 8, 0.7);
    public static final Item CRAB_DIP = registerFoodMaxOne("crab_dip", 6, 0.6, Items.BOWL);
    public static final Item CRAB_LOUIE = registerFoodMaxOne("crab_louie", 9, 0.8, Items.BOWL);
    public static final Item RAW_SHRIMP_SKEWER = registerFood("raw_shrimp_skewer", 3, 0.3, StatusEffects.HUNGER, 400, 0, 0.5F, Items.STICK);
    public static final Item COOKED_SHRIMP_SKEWER = registerFood("cooked_shrimp_skewer", 11, 0.8, Items.STICK);
    public static final Item SHRIMP_SCAMPI = registerFoodMaxOne("shrimp_scampi", 16, 0.9, Items.BOWL);
    public static final Item SHRIMP_TACO = registerFood("shrimp_taco", 10, 0.8);
    public static final Item ESCARGOT = registerFood("escargot", 8, 0.5, ShellfishBlocks.SEA_SNAIL_SHELL_BLOCK.asItem());
    public static final Item SNAIL_GRATIN = registerFoodMaxOne("snail_gratin", 10, 0.7, Items.BOWL);
    public static final Item SNAIL_SOUP = registerFoodMaxOne("snail_soup", 5, 0.5, Items.BOWL);
    public static final Item UNI_SUSHI = registerFood("uni_sushi", 4, 0.5);
    public static final Item UNI_CHAWANMUSHI = registerFoodMaxOne("uni_chawanmushi", 7, 0.7, Items.BOWL);
    public static final Item UNI_SALAD = registerFoodMaxOne("uni_salad", 5, 0.6, Items.BOWL);
    public static final Item BAKED_CLAM = registerFood("baked_clam", 8, 0.5, ShellfishBlocks.CLAM_SHELL.asItem());
    public static final Item CLAM_STRIP = registerFood("clam_strip", 10, 0.6);
    public static final Item CLAM_CHOWDER = registerFoodMaxOne("clam_chowder", 10, 0.8, Items.BOWL);
    public static final Item OYSTER_ROCKEFELLER = registerFood("oyster_rockefeller", 8, 0.5, ShellfishBlocks.OYSTER_SHELL.asItem());
    public static final Item FRIED_OYSTER = registerFood("fried_oyster", 10, 0.5);
    public static final Item OYSTER_CHOWDER = registerFoodMaxOne("oyster_chowder", 10, 0.8, Items.BOWL);
    public static final Item STUFFED_MUSSEL = registerFood("stuffed_mussel", 7, 0.5, ShellfishBlocks.MUSSEL_SHELL.asItem());
    public static final Item MUSSEL_SOUP = registerFoodMaxOne("mussel_soup", 5, 0.5, Items.BOWL);
    public static final Item MOULES_MARINIERES = registerFoodMaxOne("moules_marinieres", 10, 0.8, Items.BOWL);
    
    //MISC
    public static final Item PEARL = registerItem("pearl", new Item(new Item.Settings().trimMaterial(ShellfishUtil.createKey("pearl", RegistryKeys.TRIM_MATERIAL)).registryKey(ShellfishUtil.createKey("pearl", RegistryKeys.ITEM))));
    public static final Item SHELLFISH_BAIT = registerBasic("shellfish_bait");
    public static final Item DRIED_SHELLFISH_BAIT = registerBasic("dried_shellfish_bait");
    

    private static Builder food() {
      return ConsumableComponent.builder().consumeSeconds(1.6F).useAction(UseAction.EAT).sound(SoundEvents.ENTITY_GENERIC_EAT).consumeParticles(true);
	}

    private static Item registerBasic(String name) {
        return registerItem(name, new Item(new Item.Settings().registryKey(ShellfishUtil.createKey(name, RegistryKeys.ITEM))));
    }

    private static Item registerFood(String name, int hunger, double saturationModifier) {
        return registerItem(name, new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(hunger).saturationModifier((float) saturationModifier).build()).registryKey(ShellfishUtil.createKey(name, RegistryKeys.ITEM))));
    }

    private static Item registerFood(String name, int hunger, double saturationModifier, Item item) {
        return registerItem(name, new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(hunger).saturationModifier((float) saturationModifier).build()).useRemainder(item).registryKey(ShellfishUtil.createKey(name, RegistryKeys.ITEM))));
    }

    private static Item registerFoodMaxOne(String name, int hunger, double saturationModifier, Item item) {
        return registerItem(name, new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(hunger).saturationModifier((float) saturationModifier).build()).maxCount(1).useRemainder(item).registryKey(ShellfishUtil.createKey(name, RegistryKeys.ITEM))));
    }

    private static Item registerFood(String name, int hunger, double saturationModifier, RegistryEntry<StatusEffect> effect, int duration, int amplifier, float chance) {
        return registerItem(name, new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(hunger).saturationModifier((float) saturationModifier).build(), food().consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(effect, duration, amplifier), chance)).build()).registryKey(ShellfishUtil.createKey(name, RegistryKeys.ITEM))));
    }

    private static Item registerFood(String name, int hunger, double saturationModifier, RegistryEntry<StatusEffect> effect, int duration, int amplifier, float chance, Item item) {
        return registerItem(name, new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(hunger).saturationModifier((float) saturationModifier).build(), food().consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(effect, duration, amplifier), chance)).build()).useRemainder(item).registryKey(ShellfishUtil.createKey(name, RegistryKeys.ITEM))));
    }

    private static Item registerFood(String name, int hunger, double saturationModifier, RegistryEntry<StatusEffect> effect, int duration, int amplifier, float chance, RegistryEntry<StatusEffect> effect2, int duration2, int amplifier2, float chance2) {
        return registerItem(name, new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(hunger).saturationModifier((float) saturationModifier).build(), food().consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(effect, duration, amplifier), chance)).consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(effect2, duration2, amplifier2), chance2)).build()).registryKey(ShellfishUtil.createKey(name, RegistryKeys.ITEM))));
    }

    private static Item registerBurnt(String name) {
        return registerItem(name, new Item(new Item.Settings().food((new FoodComponent.Builder()).nutrition(1).saturationModifier((float) 0.1).build(), food().consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.POISON, 400, 1), 1F)).consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 400, 0), 1F)).consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.HUNGER, 400, 1), 1F)).build()).registryKey(ShellfishUtil.createKey(name, RegistryKeys.ITEM))));
    }

    private static Item registerBurnt(String name, Item item) {
        return registerItem(name, new Item(new Item.Settings().food((new FoodComponent.Builder()).nutrition(1).saturationModifier((float) 0.1).build(), food().consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.POISON, 400, 1), 1F)).consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 400, 0), 1F)).consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.HUNGER, 400, 1), 1F)).build()).useRemainder(item).registryKey(ShellfishUtil.createKey(name, RegistryKeys.ITEM))));
    }

    private static Item registerBucket(String name, EntityType<? extends MobEntity> type) {
        return registerItem(name, new ShellfishBucketItem(() -> type, Fluids.WATER, Items.BUCKET, false, new Item.Settings().maxCount(1).recipeRemainder(Items.BUCKET).registryKey(ShellfishUtil.createKey(name, RegistryKeys.ITEM))));
    }

    private static Item registerEgg(String name, EntityType<? extends MobEntity> type) {
        return registerItem(name, new SpawnEggItem(type, new Item.Settings().registryKey(ShellfishUtil.createKey(name, RegistryKeys.ITEM))));
    }

    private static Item registerItem(String name, Item factory) {
        return Registry.register(Registries.ITEM, Identifier.of("shellfish", name), factory);
    }

    public static void register() {}
}
