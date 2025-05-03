package womp.shellfishmod.blocks.traps;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import womp.shellfishmod.blocks.parents.AbstractTrapBlockEntity;
import womp.shellfishmod.blocks.parents.AbstractTrapBlock;
import womp.shellfishmod.registry.ShellfishBlocks;

import java.util.HashMap;

public class ShellfishTrapBlock extends AbstractTrapBlock {

    public ShellfishTrapBlock(Properties settings) {
        super(settings);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos var1, BlockState var2) {
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
        return ShellfishBlocks.SHELLFISH_TRAP_BLOCK_ENTITY.get();
    }

    @Override
    protected int getMaxDurability() {
        return 150;
    }
}
