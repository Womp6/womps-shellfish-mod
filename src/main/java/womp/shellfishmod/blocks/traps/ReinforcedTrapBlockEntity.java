package womp.shellfishmod.blocks.traps;

import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import womp.shellfishmod.blocks.parents.AbstractTrapBlockEntity;
import womp.shellfishmod.registry.ShellfishBlocks;
import womp.shellfishmod.registry.ShellfishWorldgen;

public class ReinforcedTrapBlockEntity extends AbstractTrapBlockEntity {

    public ReinforcedTrapBlockEntity(BlockPos pos, BlockState state) {
        super(ShellfishBlocks.REINFORCED_TRAP_BLOCK_ENTITY, pos, state);
    }

    @Override
    protected Item selectJunk(int i) {
        if (i == 1) {
            return Items.STICK;
        } else if (i == 2) {
            return Items.STRING;
        } else {
            RegistryEntry<Biome> biome = this.getWorld().getBiome(this.getPos());
            if (!biome.getKey().isPresent()) return Items.GLASS_BOTTLE;
            if (biome.isIn(BiomeTags.IS_RIVER) || biome.getKey().get().equals(BiomeKeys.SWAMP) || biome.getKey().get().equals(BiomeKeys.MANGROVE_SWAMP) || biome.getKey().get().equals(ShellfishWorldgen.MARSH)) {
                return Items.LILY_PAD;
            } else return Items.GLASS_BOTTLE;
        }
    }

    @Override
    protected Item selectTreasure(int i) {
        Random random = Random.create();
        if (i == 1) {
            count = random.nextBetween(5, 10);
            return Items.PRISMARINE_SHARD;
        } else if (i == 2) {
            count = 4;
            return Items.IRON_NUGGET;
        } else if (i == 3 || i == 4) {
            RegistryEntry<Biome> biome = this.getWorld().getBiome(this.getPos());
            if (!biome.getKey().isPresent()) return selectTreasure(1);
            if (biome.isIn(BiomeTags.IS_OCEAN) || biome.isIn(BiomeTags.IS_DEEP_OCEAN)) {
                return Items.COPPER_INGOT;
            } else if (biome.isIn(BiomeTags.IS_RIVER)) {
                count = i == 3 ? count : random.nextBetween(4, 8);
                return i == 3 ? Items.COPPER_INGOT : Items.GOLD_NUGGET;
            } else if (biome.getKey().get().equals(BiomeKeys.SWAMP) || biome.getKey().get().equals(BiomeKeys.MANGROVE_SWAMP) || biome.getKey().get().equals(ShellfishWorldgen.MARSH)) {
                count = random.nextBetween(2, 4);
                return Items.SLIME_BALL;
            } else return selectTreasure(1);
        } else {
            if (random.nextBetween(1, 7) == 6) {
                count = 1;
                return Items.HEART_OF_THE_SEA;  
            }
            count = random.nextBetween(2, 5);
            return Items.EMERALD;
        }
    }

    @Override
    protected int getMaxDurability() {
        return 300;
    }

    @Override
    protected int getMaxProgress() {
        return 700;
    }

    @Override
    protected int getMaxOutCount() {
        return 2;
    }

    @Override
    public String getRepairKey() {
        return "block.trap.r_needs_repair";
    }

    @Override
    protected int[] getOutputInts() {
        return new int[]{20, 20, 3, 3, 5, 6, 5, 3};
    }

    @Override
    protected Text getContainerName() {
        return Text.translatable("container.reinforced_trap");
    }
}
