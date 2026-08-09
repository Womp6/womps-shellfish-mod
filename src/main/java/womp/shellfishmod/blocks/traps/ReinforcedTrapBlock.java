package womp.shellfishmod.blocks.traps;

import java.util.HashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import com.mojang.serialization.MapCodec;
import womp.shellfishmod.blocks.parents.AbstractTrapBlock;
import womp.shellfishmod.blocks.parents.AbstractTrapBlockEntity;
import womp.shellfishmod.registry.ShellfishBlocks;
import womp.shellfishmod.registry.ShellfishItems;

public class ReinforcedTrapBlock extends AbstractTrapBlock {

    public static final MapCodec<ReinforcedTrapBlock> CODEC = simpleCodec(ReinforcedTrapBlock::new);

    public ReinforcedTrapBlock(Properties settings) {
        super(settings);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos var1, BlockState var2) {
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

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
}
