package womp.shellfishmod.blocks.traps;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import womp.shellfishmod.blocks.parents.AbstractTrapBlockEntity;
import womp.shellfishmod.registry.ShellfishBlocks;

public class ShellfishTrapBlockEntity extends AbstractTrapBlockEntity {

    public ShellfishTrapBlockEntity(BlockPos pos, BlockState state) {
        super(ShellfishBlocks.SHELLFISH_TRAP_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    protected Item selectJunk(int i) {
        if (i == 1) {
            return Items.STICK;
        } else if (i == 2 || i == 3) {
            return Items.STRING;
        } else if (i == 4 || i == 5) {
            return Items.LILY_PAD;
        } else if (i == 6 || i == 7) {
            return Items.ROTTEN_FLESH;
        } else {
            return Items.GLASS_BOTTLE;
        }
    }

    @Override
    protected Item selectTreasure(int i) {
        if (i <= 2) {
            count = 5;
            return Items.PRISMARINE_SHARD;
        } else {
            if (this.getLevel().getBiome(this.getBlockPos()).is(BiomeTags.IS_OCEAN) || this.getLevel().getBiome(this.getBlockPos()).is(BiomeTags.IS_DEEP_OCEAN)) {
                count = 1;
                return Items.COPPER_INGOT;
            } else if (this.getLevel().getBiome(this.getBlockPos()).is(BiomeTags.IS_RIVER)) {
                count = 4;
                return Items.GOLD_NUGGET;
            } else {
                count = 2;
                return Items.SLIME_BALL;
            }
        }
    }

    @Override
    public Component getDefaultName() {
        return Component.translatable("container.shellfish_trap");
    }

    @Override
    public int getMaxDurability() {
        return 150;
    }

    @Override
    public int getMaxProgress() {
        return 900;
    }

    @Override
    protected int getMaxOutCount() {
        return 1;
    }

    @Override
    protected int[] getOutputInts() {
        return new int[]{20, 20, 1, 1, 5, 7, 3, 9};
    }

    @Override
    public String getRepairKey() {
        return "block.trap.needs_repair";
    }
}
