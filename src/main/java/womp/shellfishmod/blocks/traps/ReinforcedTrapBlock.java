package womp.shellfishmod.blocks.traps;

import java.util.HashMap;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import womp.shellfishmod.blocks.parents.AbstractTrapBlock;
import womp.shellfishmod.blocks.parents.AbstractTrapBlockEntity;
import womp.shellfishmod.registry.ShellfishBlocks;
import womp.shellfishmod.registry.ShellfishItems;

public class ReinforcedTrapBlock extends AbstractTrapBlock {

    public ReinforcedTrapBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos var1, BlockState var2) {
        return new ReinforcedTrapBlockEntity(var1, var2);
    }

    @Override
    protected HashMap<Item, Integer> getRepairItems() {
        HashMap<Item, Integer> map = new HashMap<>();
        map.put(Items.IRON_INGOT, 50);
        map.put(ShellfishItems.PEARL, 100);
        return map;
    }

    @Override
    protected BlockEntityType<? extends AbstractTrapBlockEntity> getBE() {
        return ShellfishBlocks.REINFORCED_TRAP_BLOCK_ENTITY;
    }

    @Override
    protected int getMaxDurability() {
        return 300;
    }
}
