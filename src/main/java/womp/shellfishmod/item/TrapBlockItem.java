package womp.shellfishmod.item;

import java.util.function.Consumer;

import net.minecraft.block.Block;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import womp.shellfishmod.registry.ShellfishComponents;

public class TrapBlockItem extends BlockItem {

    private final int maxDurability;

    public TrapBlockItem(Block block, Settings settings, int maxDurability) {
        super(block, settings);
        this.maxDurability = maxDurability;
    }
    
    @SuppressWarnings("deprecation")
    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
        super.appendTooltip(stack, context, displayComponent, textConsumer, type);
        if (stack.contains(ShellfishComponents.DURABILITY_COMPONENT)) {
            Integer durability =  stack.get(ShellfishComponents.DURABILITY_COMPONENT);
            if (durability != null && durability < maxDurability) {
                textConsumer.accept(Text.literal(Text.translatable("shellfish_trap.durability").getString() + durability + " / " + maxDurability).formatted(Formatting.ITALIC, durability > maxDurability / 2 ? Formatting.DARK_GREEN : durability > maxDurability / 5 ? Formatting.YELLOW : Formatting.DARK_RED));
            }
        }
    }
}
 