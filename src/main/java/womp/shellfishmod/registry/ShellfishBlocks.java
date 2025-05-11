package womp.shellfishmod.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
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
import womp.shellfishmod.blocks.parents.DeadBlock;
import womp.shellfishmod.blocks.parents.EggsBlock;
import womp.shellfishmod.blocks.parents.ShellBlock;
import womp.shellfishmod.blocks.parents.ShellfishPlantBlock;
import womp.shellfishmod.blocks.traps.ReinforcedTrapBlock;
import womp.shellfishmod.blocks.traps.ReinforcedTrapBlockEntity;
import womp.shellfishmod.blocks.traps.ShellfishTrapBlock;
import womp.shellfishmod.blocks.traps.ShellfishTrapBlockEntity;
import womp.shellfishmod.entity.parents.ShellfishEntity;

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

    //CLAY AND SAND BLOCKS
    public static final DeferredBlock<Block> CLAM_CLAY = registerCS("clam_clay", true);
    public static final DeferredBlock<Block> CLAM_SAND = registerCS("clam_sand", false);
    public static final DeferredBlock<Block> OYSTER_CLAY = registerCS("oyster_clay", true);
    public static final DeferredBlock<Block> OYSTER_SAND = registerCS("oyster_sand", false);
    public static final DeferredBlock<Block> MUSSEL_CLAY = registerCS("mussel_clay", true);
    public static final DeferredBlock<Block> MUSSEL_SAND = registerCS("mussel_sand", false);

    //DEAD BLOCKS
    public static final DeferredBlock<Block> DEAD_CLAM_BLOCK = registerDeadBlock("dead_clam_block", Block.box(5.5, 0, 5.75, 10.5, 3.5, 10.75), true);
    public static final DeferredBlock<Block> DEAD_OYSTER_BLOCK = registerDeadBlock("dead_oyster_block", Block.box(2.0, 0.0, 2.0, 14.0, 13.0, 14.0), false);
    public static final DeferredBlock<Block> DEAD_MUSSEL_BLOCK = registerDeadBlock("dead_mussel_block", Block.box(2.0, 0.0, 2.0, 14.0, 13.0, 14.0), false);

    //PLANTS
    public static final DeferredBlock<Block> ROCKWEED = BLOCKS.register("rockweed", () -> new RockWeedBlock(settingsPlant(Blocks.SEAGRASS, false)));
    public static final DeferredBlock<Block> TALL_ROCKWEED = BLOCKS.register("tall_rockweed", () -> new TallRockWeedBlock(settingsPlant(Blocks.TALL_SEAGRASS, true)));
    public static final DeferredBlock<Block> WATER_LETTUCE = BLOCKS.register("water_lettuce", () -> new WaterLettuceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LILY_PAD).instabreak().pushReaction(PushReaction.DESTROY).sound(SoundType.BIG_DRIPLEAF)));
    public static final DeferredBlock<Block> PADDLEWEED = registerPlant("paddleweed", Block.box(1.0, 0.0, 1.0, 15.0, 5.0, 15.0));
    public static final DeferredBlock<Block> EELGRASS = registerPlant("eelgrass", Block.box(4.0, 0.0, 4.0, 12.0, 11.0, 12.0));
    public static final DeferredBlock<Block> SEA_LETTUCE = BLOCKS.register("sea_lettuce", () -> new SeaLettuceBlock(settingsPlant(ROCKWEED.get(), false)));

    //SHELLFISH TRAPS
    public static final DeferredBlock<Block> SHELLFISH_TRAP_BLOCK = BLOCKS.register("shellfish_trap_block", () -> new ShellfishTrapBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(5.0f, 6.0f).sound(SoundType.CHAIN).noOcclusion()));
    public static final DeferredBlock<Block> REINFORCED_TRAP = BLOCKS.register("reinforced_trap", () -> new ReinforcedTrapBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK).strength(5.5f, 6.5f).sound(SoundType.CHAIN).noOcclusion()));

    //BLOCK ENTITIES
    public static final Supplier<BlockEntityType<WaterLettuceBlockEntity>> WATER_LETTUCE_BLOCK_ENTITY = registerBE("water_lettuce_block_entity", WaterLettuceBlockEntity::new, WATER_LETTUCE);
    public static final Supplier<BlockEntityType<SeaLettuceBlockEntity>> SEA_LETTUCE_BLOCK_ENTITY = registerBE("sea_lettuce_block_entity", SeaLettuceBlockEntity::new, SEA_LETTUCE);
    public static final Supplier<BlockEntityType<ShellfishTrapBlockEntity>> SHELLFISH_TRAP_BLOCK_ENTITY = registerBE("shellfish_trap_block_entity", ShellfishTrapBlockEntity::new, SHELLFISH_TRAP_BLOCK);
    public static final Supplier<BlockEntityType<ReinforcedTrapBlockEntity>> REINFORCED_TRAP_BLOCK_ENTITY = registerBE("reinforced_trap_block_entity", ReinforcedTrapBlockEntity::new, REINFORCED_TRAP);


    private static DeferredBlock<Block> registerPlant(String name, VoxelShape shape) {
        return BLOCKS.register(name, () -> new ShellfishPlantBlock(settingsPlant(Blocks.SEAGRASS, true), shape, ShellfishPlantBlock.PlaceType.MUD_SAND));
    }

    private static BlockBehaviour.Properties settingsPlant(Block block, boolean offset) {
        BlockBehaviour.Properties settings = BlockBehaviour.Properties.ofFullCopy(block).instabreak().noCollission().replaceable().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY);
        return offset ? settings.offsetType(BlockBehaviour.OffsetType.XZ) : settings;
    }

    private static DeferredBlock<Block> registerDeadBlock(String name, VoxelShape shape, boolean solid) {
        BlockBehaviour.Properties sets = BlockBehaviour.Properties.ofFullCopy(Blocks.TURTLE_EGG).instabreak().sound(SoundType.CALCITE).pushReaction(PushReaction.DESTROY);
        if (!solid) return BLOCKS.register(name, () -> new DeadBlock(sets.noCollission(), shape, solid));
        return BLOCKS.register(name, () -> new DeadBlock(sets, shape, solid));
    }

    private static DeferredBlock<Block> registerCS(String name, boolean clay) {
        return BLOCKS.register(name, () -> clay ? new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.CLAY).instrument(NoteBlockInstrument.FLUTE).strength(0.6F).sound(SoundType.GRAVEL)) : new ColoredFallingBlock(new ColorRGBA(14406560), BlockBehaviour.Properties.ofFullCopy(Blocks.SAND).instrument(NoteBlockInstrument.SNARE).strength(0.5F).sound(SoundType.SAND)));
    }

    private static DeferredBlock<Block> registerShell(String name, VoxelShape shape) {
        return BLOCKS.register(name, () -> new ShellBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TURTLE_EGG).instabreak().sound(SoundType.CALCITE).pushReaction(PushReaction.DESTROY), shape));
    }

    private static DeferredBlock<Block> registerEggsBlock(String name, Supplier<? extends EntityType<? extends ShellfishEntity<?>>> entity, Supplier<SoundEvent> hatchSound) {
        return BLOCKS.register(name + "_eggs_block", () -> new EggsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WATER).instabreak().noCollission().sound(SoundType.FROGSPAWN).pushReaction(PushReaction.DESTROY).noOcclusion(), entity, hatchSound));
    }

    private static <T extends BlockEntity> Supplier<BlockEntityType<T>> registerBE(String name, BlockEntityType.BlockEntitySupplier<T> factory, Supplier<Block> block) {
        return BLOCK_ENTITIES.register(name, () -> BlockEntityType.Builder.of(factory, block.get()).build(null));
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
            SHELLFISH_TRAP_BLOCK.get(),
            REINFORCED_TRAP.get()
        };
    }
}
