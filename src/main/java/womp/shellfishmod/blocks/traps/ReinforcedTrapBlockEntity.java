package womp.shellfishmod.blocks.traps;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.state.BlockState;
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
            Holder<Biome> biome = this.getLevel().getBiome(this.getBlockPos());
            if (!biome.unwrapKey().isPresent()) return Items.GLASS_BOTTLE;
            if (biome.is(BiomeTags.IS_RIVER) || biome.unwrapKey().get().equals(Biomes.SWAMP) || biome.unwrapKey().get().equals(Biomes.MANGROVE_SWAMP) || biome.unwrapKey().get().equals(ShellfishWorldgen.MARSH)) {
                return Items.LILY_PAD;
            } else return Items.GLASS_BOTTLE;
        }
    }

    @Override
    protected Item selectTreasure(int i) {
        RandomSource random = RandomSource.create();
        if (i == 1) {
            count = random.nextIntBetweenInclusive(5, 10);
            return Items.PRISMARINE_SHARD;
        } else if (i == 2) {
            count = 4;
            return Items.IRON_NUGGET;
        } else if (i == 3 || i == 4) {
            Holder<Biome> biome = this.getLevel().getBiome(this.getBlockPos());
            if (!biome.unwrapKey().isPresent()) return selectTreasure(1);
            if (biome.is(BiomeTags.IS_OCEAN) || biome.is(BiomeTags.IS_DEEP_OCEAN)) {
                return Items.COPPER_INGOT;
            } else if (biome.is(BiomeTags.IS_RIVER)) {
                count = i == 3 ? count : random.nextIntBetweenInclusive(4, 8);
                return i == 3 ? Items.COPPER_INGOT : Items.GOLD_NUGGET;
            } else if (biome.unwrapKey().get().equals(Biomes.SWAMP) || biome.unwrapKey().get().equals(Biomes.MANGROVE_SWAMP) || biome.unwrapKey().get().equals(ShellfishWorldgen.MARSH)) {
                count = random.nextIntBetweenInclusive(2, 4);
                return Items.SLIME_BALL;
            } else return selectTreasure(1);
        } else {
            if (random.nextIntBetweenInclusive(1, 7) == 6) {
                count = 1;
                return Items.HEART_OF_THE_SEA;  
            }
            count = random.nextIntBetweenInclusive(2, 5);
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
    protected Component getDefaultName() {
        return Component.translatable("container.reinforced_trap");
    }
}
