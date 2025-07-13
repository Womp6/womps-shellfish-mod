package womp.shellfishmod.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import womp.shellfishmod.ShellfishMod;
import womp.shellfishmod.blocks.*;
import womp.shellfishmod.blocks.parents.*;
import womp.shellfishmod.blocks.traps.ReinforcedTrapBlock;
import womp.shellfishmod.blocks.traps.ReinforcedTrapBlockEntity;
import womp.shellfishmod.blocks.traps.ShellfishTrapBlock;
import womp.shellfishmod.blocks.traps.ShellfishTrapBlockEntity;
import womp.shellfishmod.entity.parents.ShellfishEntity;
import womp.shellfishmod.util.ShellfishTags;

import java.util.Set;
import java.util.function.Supplier;

public class ShellfishBlocks {

    //REGISTRY
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ShellfishMod.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, ShellfishMod.MOD_ID);

    //EGGS BLOCKS
    public static final DeferredBlock<Block> CRAYFISH_EGGS_BLOCK = registerEggsBlock("crayfish", ShellfishEntities.CRAYFISH, ShellfishSounds.CRAYFISHES_HATCH);
    public static final DeferredBlock<Block> LOBSTER_EGGS_BLOCK = registerEggsBlock("lobster", ShellfishEntities.LOBSTER, ShellfishSounds.LOBSTERS_HATCH);
    public static final DeferredBlock<Block> CRAB_EGGS_BLOCK = registerEggsBlock("crab", ShellfishEntities.CRAB, ShellfishSounds.CRABS_HATCH);
    public static final DeferredBlock<Block> SHRIMP_EGGS_BLOCK = registerEggsBlock("shrimp", ShellfishEntities.SHRIMP, ShellfishSounds.SHRIMPS_HATCH);
    public static final DeferredBlock<Block> SEA_SNAIL_EGGS_BLOCK = registerEggsBlock("sea_snail", ShellfishEntities.SEA_SNAIL, ShellfishSounds.SEA_SNAILS_HATCH);

    //SHELLS
    public static final DeferredBlock<Block> SEA_SNAIL_SHELL_BLOCK = registerShell("sea_snail_shell", Block.box(5, 0, 4, 11, 6, 12));
    public static final DeferredBlock<Block> CLAM_SHELL = registerShell("clam_shell", Block.box(5.5, 0, 5.75, 10.5, 2, 10.75));
    public static final DeferredBlock<Block> OYSTER_SHELL = registerShell("oyster_shell", Block.box(4.5, 0, 4.5, 11.5, 2, 11.5));
    public static final DeferredBlock<Block> MUSSEL_SHELL = registerShell("mussel_shell", Block.box(4.5, 0, 4.5, 11.5, 2, 11.5));

    //CLAY, SAND, AND MUD BLOCKS
    public static final DeferredBlock<Block> CLAM_CLAY = registerCSM("clam_clay", 1);
    public static final DeferredBlock<Block> CLAM_SAND = registerCSM("clam_sand", 2);
    public static final DeferredBlock<Block> CLAM_MUD = registerCSM("clam_mud", 3);
    public static final DeferredBlock<Block> OYSTER_CLAY = registerCSM("oyster_clay", 1);
    public static final DeferredBlock<Block> OYSTER_SAND = registerCSM("oyster_sand", 2);
    public static final DeferredBlock<Block> OYSTER_MUD = registerCSM("oyster_mud", 3);
    public static final DeferredBlock<Block> MUSSEL_CLAY = registerCSM("mussel_clay", 1);
    public static final DeferredBlock<Block> MUSSEL_SAND = registerCSM("mussel_sand", 2);
    public static final DeferredBlock<Block> MUSSEL_MUD = registerCSM("mussel_mud", 3);

    //DEAD BLOCKS
    public static final DeferredBlock<Block> DEAD_CLAM_BLOCK = registerDeadBlock("dead_clam_block", Block.box(5.5, 0, 5.75, 10.5, 3.5, 10.75), true);
    public static final DeferredBlock<Block> DEAD_OYSTER_BLOCK = registerDeadBlock("dead_oyster_block", Block.box(2.0, 0.0, 2.0, 14.0, 13.0, 14.0), false);
    public static final DeferredBlock<Block> DEAD_MUSSEL_BLOCK = registerDeadBlock("dead_mussel_block", Block.box(2.0, 0.0, 2.0, 14.0, 13.0, 14.0), false);

    //PLANTS
    public static final DeferredBlock<Block> ROCKWEED = BLOCKS.register("rockweed", () -> new RockWeedBlock(settingsPlant(Blocks.SEAGRASS, false, "rockweed")));
    public static final DeferredBlock<Block> TALL_ROCKWEED = BLOCKS.register("tall_rockweed", () -> new TallRockWeedBlock(settingsPlant(Blocks.TALL_SEAGRASS, true, "tall_rockweed")));
    public static final DeferredBlock<Block> WATER_LETTUCE = BLOCKS.register("water_lettuce", () -> new WaterLettuceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LILY_PAD).instabreak().pushReaction(PushReaction.DESTROY).sound(SoundType.BIG_DRIPLEAF).setId(ShellfishTags.createKey("water_lettuce", Registries.BLOCK))));
    public static final DeferredBlock<Block> PADDLEWEED = registerPlant("paddleweed", Block.box(1.0, 0.0, 1.0, 15.0, 5.0, 15.0));
    public static final DeferredBlock<Block> EELGRASS = registerPlant("eelgrass", Block.box(4.0, 0.0, 4.0, 12.0, 11.0, 12.0));
    public static final DeferredBlock<Block> SEA_LETTUCE = BLOCKS.register("sea_lettuce", () -> new SeaLettuceBlock(settingsPlant(ROCKWEED.get(), false, "sea_lettuce")));
    public static final DeferredBlock<Block> TALL_CATTAIL = registerTallPlant("tall_cattail", false, false);
    public static final DeferredBlock<Block> CATTAIL = registerLandWaterPlant("cattail", false, TALL_CATTAIL, false);
    public static final DeferredBlock<Block> TALL_PICKERELWEED = registerTallPlant("tall_pickerelweed", false, false);
    public static final DeferredBlock<Block> PICKERELWEED = registerLandWaterPlant("pickerelweed", false, TALL_PICKERELWEED, false);
    public static final DeferredBlock<Block> TALL_WHEATGRASS = registerTallPlant("tall_wheatgrass", false, true);
    public static final DeferredBlock<Block> WHEATGRASS = registerLandWaterPlant("wheatgrass", false, TALL_WHEATGRASS, true);
    public static final DeferredBlock<Block> TALL_WATER_GRASS = registerTallPlant("tall_water_grass", true, true);
    public static final DeferredBlock<Block> WATER_GRASS = registerLandWaterPlant("water_grass", true, TALL_WATER_GRASS, true);
    public static final DeferredBlock<Block> DRIFTWOOD = BLOCKS.register("driftwood", () -> new DriftwoodBlock(BlockBehaviour.Properties.of().strength(0.2f).sound(SoundType.WOOD).ignitedByLava().pushReaction(PushReaction.DESTROY).setId(ShellfishTags.createKey("driftwood", Registries.BLOCK))));

    //SHELLFISH TRAPS
    public static final DeferredBlock<Block> SHELLFISH_TRAP_BLOCK = BLOCKS.register("shellfish_trap_block", () -> new ShellfishTrapBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(5.0f, 6.0f).sound(SoundType.CHAIN).noOcclusion().setId(ShellfishTags.createKey("shellfish_trap_block", Registries.BLOCK))));
    public static final DeferredBlock<Block> REINFORCED_TRAP = BLOCKS.register("reinforced_trap", () -> new ReinforcedTrapBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK).strength(5.5f, 6.5f).sound(SoundType.CHAIN).noOcclusion().setId(ShellfishTags.createKey("reinforced_trap", Registries.BLOCK))));

    //BLOCK ENTITIES
    public static final Supplier<BlockEntityType<WaterLettuceBlockEntity>> WATER_LETTUCE_BLOCK_ENTITY = registerBE("water_lettuce_block_entity", WaterLettuceBlockEntity::new, WATER_LETTUCE);
    public static final Supplier<BlockEntityType<SeaLettuceBlockEntity>> SEA_LETTUCE_BLOCK_ENTITY = registerBE("sea_lettuce_block_entity", SeaLettuceBlockEntity::new, SEA_LETTUCE);
    public static final Supplier<BlockEntityType<ShellfishTrapBlockEntity>> SHELLFISH_TRAP_BLOCK_ENTITY = registerBE("shellfish_trap_block_entity", ShellfishTrapBlockEntity::new, SHELLFISH_TRAP_BLOCK);
    public static final Supplier<BlockEntityType<ReinforcedTrapBlockEntity>> REINFORCED_TRAP_BLOCK_ENTITY = registerBE("reinforced_trap_block_entity", ReinforcedTrapBlockEntity::new, REINFORCED_TRAP);

    //BARREL FOR TRAPPER HUT
    public static final DeferredBlock<Block> BARREL_NO_POI = BLOCKS.register("barrel", () -> new ShellfishBarrelBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BARREL).setId(ShellfishTags.createKey("barrel", Registries.BLOCK))));
    public static final Supplier<BlockEntityType<ShellfishBarrelBlockEntity>> BARREL_BE_NO_POI = registerBE("barrel_block_entity", ShellfishBarrelBlockEntity::new, BARREL_NO_POI);

    private static DeferredBlock<Block> registerLandWaterPlant(String name, boolean waterOnly, Supplier<? extends Block> tallPlantBlock, boolean grass) {
        return BLOCKS.register(name, () -> grass ? new ShellfishLandPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).setId(ShellfishTags.createKey(name, Registries.BLOCK)), waterOnly, tallPlantBlock) : new WaterFlowerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DANDELION).setId(ShellfishTags.createKey(name, Registries.BLOCK)), tallPlantBlock));
    }

    private static DeferredBlock<Block> registerTallPlant(String name, boolean waterOnly, boolean grass) {
        return BLOCKS.register(name, () -> new ShellfishLandTallBlock(BlockBehaviour.Properties.ofFullCopy(grass ? Blocks.SHORT_GRASS : Blocks.DANDELION).setId(ShellfishTags.createKey(name, Registries.BLOCK)), waterOnly));
    }

    private static DeferredBlock<Block> registerPlant(String name, VoxelShape shape) {
        return BLOCKS.register(name, () -> new ShellfishPlantBlock(settingsPlant(Blocks.SEAGRASS, true, name), shape, ShellfishPlantBlock.PlaceType.MUD_SAND));
    }

    private static BlockBehaviour.Properties settingsPlant(Block block, boolean offset, String name) {
        BlockBehaviour.Properties settings = BlockBehaviour.Properties.ofFullCopy(block).instabreak().noCollission().replaceable().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY).setId(ShellfishTags.createKey(name, Registries.BLOCK));
        return offset ? settings.offsetType(BlockBehaviour.OffsetType.XZ) : settings;
    }

    private static DeferredBlock<Block> registerDeadBlock(String name, VoxelShape shape, boolean solid) {
        BlockBehaviour.Properties sets = BlockBehaviour.Properties.ofFullCopy(Blocks.TURTLE_EGG).instabreak().sound(SoundType.CALCITE).pushReaction(PushReaction.DESTROY).setId(ShellfishTags.createKey(name, Registries.BLOCK));
        if (!solid) return BLOCKS.register(name, () -> new DeadBlock(sets.noCollission(), shape, solid));
        return BLOCKS.register(name, () -> new DeadBlock(sets, shape, solid));
    }

    private static DeferredBlock<Block> registerCSM(String name, int type) {
        return BLOCKS.register(name, () -> type == 1 ? new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.CLAY).instrument(NoteBlockInstrument.FLUTE).strength(0.6F).sound(SoundType.GRAVEL).setId(ShellfishTags.createKey(name, Registries.BLOCK)))
                : type == 2 ? new ColoredFallingBlock(new ColorRGBA(14406560), BlockBehaviour.Properties.ofFullCopy(Blocks.SAND).instrument(NoteBlockInstrument.SNARE).strength(0.5F).sound(SoundType.SAND).setId(ShellfishTags.createKey(name, Registries.BLOCK)))
                : new MudBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MUD).setId(ShellfishTags.createKey(name, Registries.BLOCK))));
    }

    private static DeferredBlock<Block> registerShell(String name, VoxelShape shape) {
        return BLOCKS.register(name, () -> new ShellBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TURTLE_EGG).instabreak().sound(SoundType.CALCITE).pushReaction(PushReaction.DESTROY).setId(ShellfishTags.createKey(name, Registries.BLOCK)), shape));
    }

    private static DeferredBlock<Block> registerEggsBlock(String name, Supplier<? extends EntityType<? extends ShellfishEntity<?>>> entity, Supplier<SoundEvent> hatchSound) {
        return BLOCKS.register(name + "_eggs_block", () -> new EggsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WATER).instabreak().noCollission().sound(SoundType.FROGSPAWN).pushReaction(PushReaction.DESTROY).noOcclusion().setId(ShellfishTags.createKey(name + "_eggs_block", Registries.BLOCK)), entity, hatchSound));
    }

    private static <T extends BlockEntity> Supplier<BlockEntityType<T>> registerBE(String name, BlockEntityType.BlockEntitySupplier<T> factory, Supplier<Block> block) {
        return BLOCK_ENTITIES.register(name, () -> new BlockEntityType<T>(factory, Set.of(block.get())));
    }

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
        BLOCK_ENTITIES.register(bus);
    }

    public static Block[] getCutouts() {
        return new Block[] {
            LOBSTER_EGGS_BLOCK.get(),
            CRAYFISH_EGGS_BLOCK.get(),
            CRAB_EGGS_BLOCK.get(),
            SHRIMP_EGGS_BLOCK.get(),
            DEAD_OYSTER_BLOCK.get(),
            DEAD_MUSSEL_BLOCK.get(),
            ROCKWEED.get(),
            TALL_ROCKWEED.get(),
            WATER_LETTUCE.get(),
            PADDLEWEED.get(),
            EELGRASS.get(),
            SEA_LETTUCE.get(),
            WATER_GRASS.get(),
            TALL_WATER_GRASS.get(),
            CATTAIL.get(),
            TALL_CATTAIL.get(),
            PICKERELWEED.get(),
            TALL_PICKERELWEED.get(),
            WHEATGRASS.get(),
            TALL_WHEATGRASS.get(),
            SHELLFISH_TRAP_BLOCK.get(),
            REINFORCED_TRAP.get()
        };
    }
}
