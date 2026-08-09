package womp.shellfishmod.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelValueEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PlaceOnWaterBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ColoredFallingBlock;
import net.minecraft.world.level.block.MudBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.VoxelShape;
import womp.shellfishmod.blocks.DriftwoodBlock;
import womp.shellfishmod.blocks.RockWeedBlock;
import womp.shellfishmod.blocks.SeaLettuceBlock;
import womp.shellfishmod.blocks.SeaLettuceBlockEntity;
import womp.shellfishmod.blocks.ShellfishBarrelBlock;
import womp.shellfishmod.blocks.ShellfishBarrelBlockEntity;
import womp.shellfishmod.blocks.TallRockWeedBlock;
import womp.shellfishmod.blocks.WaterFlowerBlock;
import womp.shellfishmod.blocks.WaterLettuceBlock;
import womp.shellfishmod.blocks.WaterLettuceBlockEntity;
import womp.shellfishmod.blocks.parents.DeadBlock;
import womp.shellfishmod.blocks.parents.EggsBlock;
import womp.shellfishmod.blocks.parents.ShellBlock;
import womp.shellfishmod.blocks.parents.ShellfishLandPlantBlock;
import womp.shellfishmod.blocks.parents.ShellfishLandTallBlock;
import womp.shellfishmod.blocks.parents.ShellfishPlantBlock;
import womp.shellfishmod.blocks.parents.ShellfishPlantBlock.PlaceType;
import womp.shellfishmod.blocks.traps.ReinforcedTrapBlock;
import womp.shellfishmod.blocks.traps.ReinforcedTrapBlockEntity;
import womp.shellfishmod.blocks.traps.ShellfishTrapBlock;
import womp.shellfishmod.blocks.traps.ShellfishTrapBlockEntity;
import womp.shellfishmod.entity.parents.ShellfishEntity;
import womp.shellfishmod.item.TrapBlockItem;

public class ShellfishBlocks {

    //EGGS BLOCKS
    public static final Block CRAYFISH_EGGS_BLOCK = registerEggsBlock("crayfish", ShellfishEntities.CRAYFISH, ShellfishSounds.CRAYFISHES_HATCH);
    public static final Block LOBSTER_EGGS_BLOCK = registerEggsBlock("lobster", ShellfishEntities.LOBSTER, ShellfishSounds.LOBSTERS_HATCH);
	public static final Block CRAB_EGGS_BLOCK = registerEggsBlock("crab", ShellfishEntities.CRAB, ShellfishSounds.CRABS_HATCH);
	public static final Block SHRIMP_EGGS_BLOCK = registerEggsBlock("shrimp", ShellfishEntities.SHRIMP, ShellfishSounds.SHRIMPS_HATCH);
	public static final Block SEA_SNAIL_EGGS_BLOCK = registerEggsBlock("sea_snail", ShellfishEntities.SEA_SNAIL, ShellfishSounds.SEA_SNAILS_HATCH);

    //SHELLS
    public static final Block SEA_SNAIL_SHELL_BLOCK = registerShell("sea_snail_shell", Block.box(5, 0, 4, 11, 6, 12));
	public static final Block CLAM_SHELL = registerShell("clam_shell", Block.box(5.5, 0, 5.75, 10.5, 2, 10.75));
    public static final Block OYSTER_SHELL = registerShell("oyster_shell", Block.box(4.5, 0, 4.5, 11.5, 2, 11.5));
    public static final Block MUSSEL_SHELL = registerShell("mussel_shell", Block.box(4.5, 0, 4.5, 11.5, 2, 11.5));

    //CLAY, SAND, AND MUD BLOCKS
    public static final Block CLAM_CLAY = registerCSM("clam_clay", 1);
    public static final Block CLAM_SAND = registerCSM("clam_sand", 2);
    public static final Block CLAM_MUD = registerCSM("clam_mud", 3);
    public static final Block OYSTER_CLAY = registerCSM("oyster_clay", 1);
    public static final Block OYSTER_SAND = registerCSM("oyster_sand", 2);
    public static final Block OYSTER_MUD = registerCSM("oyster_mud", 3);
    public static final Block MUSSEL_CLAY = registerCSM("mussel_clay", 1);
    public static final Block MUSSEL_SAND = registerCSM("mussel_sand", 2);
    public static final Block MUSSEL_MUD = registerCSM("mussel_mud", 3);

    //DEAD BLOCKS
    public static final Block DEAD_CLAM_BLOCK = registerDeadBlock("dead_clam_block", Block.box(5.5, 0, 5.75, 10.5, 3.5, 10.75), true);
    public static final Block DEAD_OYSTER_BLOCK = registerDeadBlock("dead_oyster_block", Block.box(2.0, 0.0, 2.0, 14.0, 13.0, 14.0), false);
    public static final Block DEAD_MUSSEL_BLOCK = registerDeadBlock("dead_mussel_block", Block.box(2.0, 0.0, 2.0, 14.0, 13.0, 14.0), false);

    //PLANTS
    public static final Block ROCKWEED = register("rockweed", new RockWeedBlock(settingsPlant(Blocks.SEAGRASS, false, "rockweed")), "rockweed");
	public static final Block TALL_ROCKWEED = registerNoBI("tall_rockweed", new TallRockWeedBlock(settingsPlant(Blocks.TALL_SEAGRASS, true, "tall_rockweed")));
    private static final Block water_lettuce_data = new WaterLettuceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LILY_PAD).instabreak().pushReaction(PushReaction.DESTROY).sound((SoundType.BIG_DRIPLEAF)).setId(ShellfishUtil.createKey("water_lettuce", Registries.BLOCK)));
    public static final Block WATER_LETTUCE = registerCBI("water_lettuce", water_lettuce_data, "water_lettuce", new PlaceOnWaterBlockItem(water_lettuce_data, new Item.Properties().setId(ShellfishUtil.createKey("water_lettuce", Registries.ITEM))));
    public static final Block PADDLEWEED = registerPlant("paddleweed", Block.box(1.0, 0.0, 1.0, 15.0, 5.0, 15.0));
    public static final Block EELGRASS = registerPlant("eelgrass", Block.box(4.0, 0.0, 4.0, 12.0, 11.0, 12.0));
    private static final Block sea_lettuce_data = new SeaLettuceBlock(settingsPlant(ROCKWEED, false, "sea_lettuce"));
    public static final Block SEA_LETTUCE = registerCBI("sea_lettuce", sea_lettuce_data, "sea_lettuce", new BlockItem(sea_lettuce_data, new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationModifier((float) 0.1).build()).setId(ShellfishUtil.createKey("sea_lettuce", Registries.ITEM))));
    public static final Block TALL_CATTAIL = registerTallPlant("tall_cattail", false, false);
    public static final Block CATTAIL = registerLandWaterPlant("cattail", false, TALL_CATTAIL, false);
    public static final Block TALL_PICKERELWEED = registerTallPlant("tall_pickerelweed", false, false);
    public static final Block PICKERELWEED = registerLandWaterPlant("pickerelweed", false, TALL_PICKERELWEED, false);
    public static final Block TALL_WHEATGRASS = registerTallPlant("tall_wheatgrass", false, true);
    public static final Block WHEATGRASS = registerLandWaterPlant("wheatgrass", false, TALL_WHEATGRASS, true);
    public static final Block TALL_WATER_GRASS = registerTallPlant("tall_water_grass", true, true); 
    public static final Block WATER_GRASS = registerLandWaterPlant("water_grass", true, TALL_WATER_GRASS, true);
    public static final Block driftwood_data = new DriftwoodBlock(BlockBehaviour.Properties.of().strength(0.2f).sound(SoundType.WOOD).ignitedByLava().pushReaction(PushReaction.DESTROY).setId(ShellfishUtil.createKey("driftwood", Registries.BLOCK)));
    public static final Block DRIFTWOOD = registerCBI("driftwood", driftwood_data, "driftwood", new PlaceOnWaterBlockItem(driftwood_data, new Item.Properties().setId(ShellfishUtil.createKey("driftwood", Registries.ITEM))));   

    //SHELLFISH TRAPS
    public static final Block SHELLFISH_TRAP_BLOCK = registerDurability("shellfish_trap_block", new ShellfishTrapBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(5.0f, 6.0f).sound(SoundType.CHAIN).noOcclusion().setId(ShellfishUtil.createKey("shellfish_trap_block", Registries.BLOCK))), "shellfish_trap", 150);
    public static final Block REINFORCED_TRAP = registerDurability("reinforced_trap", new ReinforcedTrapBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK).strength(5.5f, 6.5f).sound(SoundType.CHAIN).noOcclusion().setId(ShellfishUtil.createKey("reinforced_trap", Registries.BLOCK))), "reinforced_trap", 300);

    //BLOCK ENTITIES
    public static final BlockEntityType<WaterLettuceBlockEntity> WATER_LETTUCE_BLOCK_ENTITY = registerBE("water_lettuce_block_entity", WaterLettuceBlockEntity::new, WATER_LETTUCE);
	public static final BlockEntityType<SeaLettuceBlockEntity> SEA_LETTUCE_BLOCK_ENTITY = registerBE("sea_lettuce_block_entity", SeaLettuceBlockEntity::new, SEA_LETTUCE);
	public static final BlockEntityType<ShellfishTrapBlockEntity> SHELLFISH_TRAP_BLOCK_ENTITY = registerBE("shellfish_trap_block_entity", ShellfishTrapBlockEntity::new, SHELLFISH_TRAP_BLOCK);
    public static final BlockEntityType<ReinforcedTrapBlockEntity> REINFORCED_TRAP_BLOCK_ENTITY = registerBE("reinforced_trap_block_entity", ReinforcedTrapBlockEntity::new, REINFORCED_TRAP);

    //BARREL FOR TRAPPER HUT
    public static final Block BARREL_NO_POI = registerNoBI("barrel", new ShellfishBarrelBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BARREL).setId(ShellfishUtil.createKey("barrel", Registries.BLOCK))));
    public static final BlockEntityType<ShellfishBarrelBlockEntity> BARREL_BE_NO_POI = registerBE("barrel_block_entity", ShellfishBarrelBlockEntity::new, BARREL_NO_POI);

    private static Block registerLandWaterPlant(String name, boolean waterOnly, Block tallPlantBlock, boolean grass) {
        return register(name, grass ? new ShellfishLandPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).setId(ShellfishUtil.createKey(name, Registries.BLOCK)), waterOnly, tallPlantBlock) : new WaterFlowerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DANDELION).setId(ShellfishUtil.createKey(name, Registries.BLOCK)), tallPlantBlock), name);
    }

    private static Block registerTallPlant(String name, boolean waterOnly, boolean grass) {
        Block block = new ShellfishLandTallBlock(BlockBehaviour.Properties.ofFullCopy(grass ? Blocks.SHORT_GRASS : Blocks.DANDELION).setId(ShellfishUtil.createKey(name, Registries.BLOCK)), waterOnly);
        return registerCBI(name, block, name, new DoubleHighBlockItem(block, new Item.Properties().setId(ShellfishUtil.createKey(name, Registries.ITEM))));
    }

    private static Block registerPlant(String name, VoxelShape shape) {
        return register(name, new ShellfishPlantBlock(settingsPlant(Blocks.SEAGRASS, true, name).setId(ShellfishUtil.createKey(name, Registries.BLOCK)), shape, PlaceType.MUD_SAND), name);
    }

    private static BlockBehaviour.Properties settingsPlant(Block block, boolean offset, String name) {
        BlockBehaviour.Properties settings = BlockBehaviour.Properties.ofFullCopy(block).instabreak().noCollision().replaceable().sound((SoundType.WET_GRASS)).pushReaction(PushReaction.DESTROY).setId(ShellfishUtil.createKey(name, Registries.BLOCK));
        return offset ? settings.offsetType(BlockBehaviour.OffsetType.XZ) : settings;
    }

    private static Block registerDeadBlock(String name, VoxelShape shape, boolean solid) {
        BlockBehaviour.Properties sets = BlockBehaviour.Properties.ofFullCopy(Blocks.TURTLE_EGG).instabreak().sound((SoundType.CALCITE)).pushReaction(PushReaction.DESTROY).setId(ShellfishUtil.createKey(name, Registries.BLOCK));
        if (!solid) return register(name, new DeadBlock(sets.noCollision(), shape, solid), name);
        return register(name, new DeadBlock(sets, shape, solid), name);
    }

   /**
     * For type: 1 is clay, 2 is sand, 3 is mud.
     */
    private static Block registerCSM(String name, int type) {
        return register(name, type == 1 ? new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.CLAY).instrument(NoteBlockInstrument.FLUTE).strength(0.6F).sound(SoundType.GRAVEL).setId(ShellfishUtil.createKey(name, Registries.BLOCK))) 
        : type == 2 ? new ColoredFallingBlock(new ColorRGBA(14406560), BlockBehaviour.Properties.ofFullCopy(Blocks.SAND).instrument(NoteBlockInstrument.SNARE).strength(0.5F).sound(SoundType.SAND).setId(ShellfishUtil.createKey(name, Registries.BLOCK))) 
        : new MudBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MUD).setId(ShellfishUtil.createKey(name, Registries.BLOCK))), name);
    }

    private static Block registerShell(String name, VoxelShape shape) {
        return register(name, new ShellBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TURTLE_EGG).instabreak().sound(SoundType.CALCITE).pushReaction(PushReaction.DESTROY).setId(ShellfishUtil.createKey(name, Registries.BLOCK)), shape), name);
    }

    private static Block registerEggsBlock(String name, EntityType<? extends ShellfishEntity<?>> entity, SoundEvent hatchSound) {
        return register(name + "_eggs_block", new EggsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WATER).instabreak().noCollision().sound(SoundType.FROGSPAWN).pushReaction(PushReaction.DESTROY).noOcclusion().setId(ShellfishUtil.createKey(name + "_eggs_block", Registries.BLOCK)), entity, hatchSound), name + "_eggs");
    }

    private static Block registerCBI(String name, Block factory, String itemName, Item factory2) {
        Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath("shellfish", itemName), factory2);
        return Registry.register(BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath("shellfish", name), factory);
    }

    private static Block register(String name, Block factory, String itemName) {
        return registerCBI(name, factory, itemName, new BlockItem(factory, new Item.Properties().setId(ShellfishUtil.createKey(itemName, Registries.ITEM))));
    }

    private static Block registerDurability(String name, Block factory, String itemName, int max) {
        return registerCBI(name, factory, itemName, new TrapBlockItem(factory, new Item.Properties().setId(ShellfishUtil.createKey(itemName, Registries.ITEM)), max));
    }

    private static Block registerNoBI(String name, Block factory) {
        return Registry.register(BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath("shellfish", name), factory);
    }

    private static <T extends BlockEntity> BlockEntityType<T> registerBE(String name, FabricBlockEntityTypeBuilder.Factory<T> factory, Block block) {
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath("shellfish", name), FabricBlockEntityTypeBuilder.create(factory, block).build());
    }
    
    public static void register() {
        FuelValueEvents.BUILD.register((builder, ctx) -> {
            builder.add(DRIFTWOOD, 200);
        });
        FlammableBlockRegistry.getDefaultInstance().add(DRIFTWOOD, 5, 5);
    }

    public static Block[] getCutouts() {
        return new Block[] {
            LOBSTER_EGGS_BLOCK,
            CRAYFISH_EGGS_BLOCK,
            CRAB_EGGS_BLOCK,
            SHRIMP_EGGS_BLOCK,
            DEAD_OYSTER_BLOCK,
            DEAD_MUSSEL_BLOCK,
            ROCKWEED,
            TALL_ROCKWEED,
            WATER_LETTUCE,
            PADDLEWEED,
            EELGRASS,
            SEA_LETTUCE,
            WATER_GRASS,
            TALL_WATER_GRASS,
            CATTAIL,
            TALL_CATTAIL,
            PICKERELWEED,
            TALL_PICKERELWEED,
            WHEATGRASS,
            TALL_WHEATGRASS,
            SHELLFISH_TRAP_BLOCK,
            REINFORCED_TRAP
        };
    }
}
