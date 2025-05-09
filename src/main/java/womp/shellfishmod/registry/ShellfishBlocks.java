package womp.shellfishmod.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ColoredFallingBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.PlaceableOnWaterItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ColorCode;
import net.minecraft.util.Identifier;
import net.minecraft.util.shape.VoxelShape;
import womp.shellfishmod.blocks.RockWeedBlock;
import womp.shellfishmod.blocks.SeaLettuceBlock;
import womp.shellfishmod.blocks.SeaLettuceBlockEntity;
import womp.shellfishmod.blocks.TallRockWeedBlock;
import womp.shellfishmod.blocks.WaterLettuceBlock;
import womp.shellfishmod.blocks.WaterLettuceBlockEntity;
import womp.shellfishmod.blocks.parents.DeadBlock;
import womp.shellfishmod.blocks.parents.EggsBlock;
import womp.shellfishmod.blocks.parents.ShellBlock;
import womp.shellfishmod.blocks.parents.ShellfishPlantBlock;
import womp.shellfishmod.blocks.parents.ShellfishPlantBlock.PlaceType;
import womp.shellfishmod.blocks.traps.ReinforcedTrapBlock;
import womp.shellfishmod.blocks.traps.ReinforcedTrapBlockEntity;
import womp.shellfishmod.blocks.traps.ShellfishTrapBlock;
import womp.shellfishmod.blocks.traps.ShellfishTrapBlockEntity;
import womp.shellfishmod.entity.parents.ShellfishEntity;

public class ShellfishBlocks {

    //EGGS BLOCKS
    public static final Block CRAYFISH_EGGS_BLOCK = registerEggsBlock("crayfish", ShellfishEntities.CRAYFISH, ShellfishSounds.CRAYFISHES_HATCH);
    public static final Block LOBSTER_EGGS_BLOCK = registerEggsBlock("lobster", ShellfishEntities.LOBSTER, ShellfishSounds.LOBSTERS_HATCH);
	public static final Block CRAB_EGGS_BLOCK = registerEggsBlock("crab", ShellfishEntities.CRAB, ShellfishSounds.CRABS_HATCH);
	public static final Block SHRIMP_EGGS_BLOCK = registerEggsBlock("shrimp", ShellfishEntities.SHRIMP, ShellfishSounds.SHRIMPS_HATCH);
	public static final Block SEA_SNAIL_EGGS_BLOCK = registerEggsBlock("sea_snail", ShellfishEntities.SEA_SNAIL, ShellfishSounds.SEA_SNAILS_HATCH);

    //SHELLS
    public static final Block SEA_SNAIL_SHELL_BLOCK = registerShell("sea_snail_shell", Block.createCuboidShape(5, 0, 4, 11, 6, 12));
	public static final Block CLAM_SHELL = registerShell("clam_shell", Block.createCuboidShape(5.5, 0, 5.75, 10.5, 2, 10.75));
    public static final Block OYSTER_SHELL = registerShell("oyster_shell", Block.createCuboidShape(4.5, 0, 4.5, 11.5, 2, 11.5));
    public static final Block MUSSEL_SHELL = registerShell("mussel_shell", Block.createCuboidShape(4.5, 0, 4.5, 11.5, 2, 11.5));

    //CLAY AND SAND BLOCKS
    public static final Block CLAM_CLAY = registerCS("clam_clay", true);
    public static final Block CLAM_SAND = registerCS("clam_sand", false);
    public static final Block OYSTER_CLAY = registerCS("oyster_clay", true);
    public static final Block OYSTER_SAND = registerCS("oyster_sand", false);
    public static final Block MUSSEL_CLAY = registerCS("mussel_clay", true);
    public static final Block MUSSEL_SAND = registerCS("mussel_sand", false);

    //DEAD BLOCKS
    public static final Block DEAD_CLAM_BLOCK = registerDeadBlock("dead_clam_block", Block.createCuboidShape(5.5, 0, 5.75, 10.5, 3.5, 10.75), true);
    public static final Block DEAD_OYSTER_BLOCK = registerDeadBlock("dead_oyster_block", Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 13.0, 14.0), false);
    public static final Block DEAD_MUSSEL_BLOCK = registerDeadBlock("dead_mussel_block", Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 13.0, 14.0), false);

    //PLANTS
    public static final Block ROCKWEED = register("rockweed", new RockWeedBlock(settingsPlant(Blocks.SEAGRASS, false).registryKey(ShellfishUtil.createKey("rockweed", RegistryKeys.BLOCK))), "rockweed");
	public static final Block TALL_ROCKWEED = registerNoBI("tall_rockweed", new TallRockWeedBlock(settingsPlant(Blocks.TALL_SEAGRASS, true).registryKey(ShellfishUtil.createKey("tall_rockweed", RegistryKeys.BLOCK))));
    private static final Block water_lettuce_data = new WaterLettuceBlock(AbstractBlock.Settings.copy(Blocks.LILY_PAD).breakInstantly().pistonBehavior(PistonBehavior.DESTROY).sounds((BlockSoundGroup.BIG_DRIPLEAF)).registryKey(ShellfishUtil.createKey("water_lettuce", RegistryKeys.BLOCK)));
    public static final Block WATER_LETTUCE = registerCBI("water_lettuce", water_lettuce_data, "water_lettuce", new PlaceableOnWaterItem(water_lettuce_data, new Item.Settings().registryKey(ShellfishUtil.createKey("water_lettuce", RegistryKeys.ITEM))));
    public static final Block PADDLEWEED = registerPlant("paddleweed", Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 5.0, 15.0));
    public static final Block EELGRASS = registerPlant("eelgrass", Block.createCuboidShape(4.0, 0.0, 4.0, 12.0, 11.0, 12.0));
    private static final Block sea_lettuce_data = new SeaLettuceBlock(settingsPlant(ROCKWEED, false).registryKey(ShellfishUtil.createKey("sea_lettuce", RegistryKeys.BLOCK)));
    public static final Block SEA_LETTUCE = registerCBI("sea_lettuce", sea_lettuce_data, "sea_lettuce", new BlockItem(sea_lettuce_data, new Item.Settings().food(new FoodComponent.Builder().nutrition(1).saturationModifier((float) 0.1).build()).registryKey(ShellfishUtil.createKey("sea_lettuce", RegistryKeys.ITEM))));    

    //SHELLFISH TRAPS
    public static final Block SHELLFISH_TRAP_BLOCK = register("shellfish_trap_block", new ShellfishTrapBlock(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).strength(5.0f, 6.0f).sounds(BlockSoundGroup.CHAIN).nonOpaque().registryKey(ShellfishUtil.createKey("shellfish_trap_block", RegistryKeys.BLOCK))), "shellfish_trap");
    public static final Block REINFORCED_TRAP = register("reinforced_trap", new ReinforcedTrapBlock(AbstractBlock.Settings.copy(Blocks.DIAMOND_BLOCK).strength(5.5f, 6.5f).sounds(BlockSoundGroup.CHAIN).nonOpaque().registryKey(ShellfishUtil.createKey("reinforced_trap", RegistryKeys.BLOCK))), "reinforced_trap");

    //BLOCK ENTITIES
    public static final BlockEntityType<WaterLettuceBlockEntity> WATER_LETTUCE_BLOCK_ENTITY = registerBE("water_lettuce_block_entity", WaterLettuceBlockEntity::new, WATER_LETTUCE);
	public static final BlockEntityType<SeaLettuceBlockEntity> SEA_LETTUCE_BLOCK_ENTITY = registerBE("sea_lettuce_block_entity", SeaLettuceBlockEntity::new, SEA_LETTUCE);
	public static final BlockEntityType<ShellfishTrapBlockEntity> SHELLFISH_TRAP_BLOCK_ENTITY = registerBE("shellfish_trap_block_entity", ShellfishTrapBlockEntity::new, SHELLFISH_TRAP_BLOCK);
    public static final BlockEntityType<ReinforcedTrapBlockEntity> REINFORCED_TRAP_BLOCK_ENTITY = registerBE("reinforced_trap_block_entity", ReinforcedTrapBlockEntity::new, REINFORCED_TRAP);


    private static Block registerPlant(String name, VoxelShape shape) {
        return register(name, new ShellfishPlantBlock(settingsPlant(Blocks.SEAGRASS, true).registryKey(ShellfishUtil.createKey(name, RegistryKeys.BLOCK)), shape, PlaceType.MUD_SAND), name);
    }

    private static Block.Settings settingsPlant(Block block, boolean offset) {
        Block.Settings settings = AbstractBlock.Settings.copy(block).breakInstantly().noCollision().replaceable().sounds((BlockSoundGroup.WET_GRASS)).pistonBehavior(PistonBehavior.DESTROY);
        return offset ? settings.offset(AbstractBlock.OffsetType.XZ) : settings;
    }

    private static Block registerDeadBlock(String name, VoxelShape shape, boolean solid) {
        AbstractBlock.Settings sets = AbstractBlock.Settings.copy(Blocks.TURTLE_EGG).breakInstantly().sounds((BlockSoundGroup.CALCITE)).pistonBehavior(PistonBehavior.DESTROY).registryKey(ShellfishUtil.createKey(name, RegistryKeys.BLOCK));
        if (!solid) return register(name, new DeadBlock(sets.noCollision(), shape, solid), name);
        return register(name, new DeadBlock(sets, shape, solid), name);
    }

    private static Block registerCS(String name, boolean clay) {
        return register(name, clay ? new Block(AbstractBlock.Settings.copy(Blocks.CLAY).instrument(NoteBlockInstrument.FLUTE).strength(0.6F).sounds(BlockSoundGroup.GRAVEL).registryKey(ShellfishUtil.createKey(name, RegistryKeys.BLOCK)))
         : new ColoredFallingBlock(new ColorCode(14406560), AbstractBlock.Settings.copy(Blocks.SAND).instrument(NoteBlockInstrument.SNARE).strength(0.5F).sounds(BlockSoundGroup.SAND).registryKey(ShellfishUtil.createKey(name, RegistryKeys.BLOCK))), name);
    }

    private static Block registerShell(String name, VoxelShape shape) {
        return register(name, new ShellBlock(AbstractBlock.Settings.copy(Blocks.TURTLE_EGG).breakInstantly().sounds(BlockSoundGroup.CALCITE).pistonBehavior(PistonBehavior.DESTROY).registryKey(ShellfishUtil.createKey(name, RegistryKeys.BLOCK)), shape), name);
    }

    private static Block registerEggsBlock(String name, EntityType<? extends ShellfishEntity<?>> entity, SoundEvent hatchSound) {
        return register(name + "_eggs_block", new EggsBlock(AbstractBlock.Settings.create().mapColor(MapColor.WATER_BLUE).breakInstantly().noCollision().sounds(BlockSoundGroup.FROGSPAWN).pistonBehavior(PistonBehavior.DESTROY).nonOpaque().registryKey(ShellfishUtil.createKey(name + "_eggs_block", RegistryKeys.BLOCK)), entity, hatchSound), name + "_eggs");
    }

    private static Block registerCBI(String name, Block factory, String itemName, Item factory2) {
        Registry.register(Registries.ITEM, Identifier.of("shellfish", itemName), factory2);
        return Registry.register(Registries.BLOCK, Identifier.of("shellfish", name), factory);
    }

    private static Block register(String name, Block factory, String itemName) {
        return registerCBI(name, factory, itemName, new BlockItem(factory, new Item.Settings().registryKey(ShellfishUtil.createKey(itemName, RegistryKeys.ITEM))));
    }

    private static Block registerNoBI(String name, Block factory) {
        return Registry.register(Registries.BLOCK, Identifier.of("shellfish", name), factory);
    }

    private static <T extends BlockEntity> BlockEntityType<T> registerBE(String name, FabricBlockEntityTypeBuilder.Factory<T> factory, Block block) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of("shellfish", name), FabricBlockEntityTypeBuilder.create(factory, block).build(null));
    }
    
    public static void register() {}

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
            SHELLFISH_TRAP_BLOCK,
            REINFORCED_TRAP
        };
    }
}
