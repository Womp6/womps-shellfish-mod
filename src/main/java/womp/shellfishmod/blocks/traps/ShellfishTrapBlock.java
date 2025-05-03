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

public class ShellfishTrapBlock extends AbstractTrapBlock {

    public ShellfishTrapBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos var1, BlockState var2) {
        return new ShellfishTrapBlockEntity(var1, var2);
    }

    @Override
    protected HashMap<Item, Integer> getRepairItems() {
        HashMap<Item, Integer> hashMap = new HashMap<>();
        hashMap.put(Items.IRON_INGOT, 50);
        return hashMap;
    }

    @Override
    protected BlockEntityType<? extends AbstractTrapBlockEntity> getBE() {
        return ShellfishBlocks.SHELLFISH_TRAP_BLOCK_ENTITY;
    }

    @Override
    protected int getMaxDurability() {
        return 150;
    }
}
