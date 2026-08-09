package womp.shellfishmod.item;

import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;
import womp.shellfishmod.registry.ShellfishComponents;

public class TrapBlockItem extends BlockItem {

    private final int maxDurability;

    public TrapBlockItem(Block block, Properties settings, int maxDurability) {
        super(block, settings);
        this.maxDurability = maxDurability;
    }
    
    @SuppressWarnings("deprecation")
    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay displayComponent, Consumer<Component> textConsumer, TooltipFlag type) {
        super.appendHoverText(stack, context, displayComponent, textConsumer, type);
        if (stack.has(ShellfishComponents.DURABILITY_COMPONENT)) {
            Integer durability =  stack.get(ShellfishComponents.DURABILITY_COMPONENT);
            if (durability != null && durability < maxDurability) {
                textConsumer.accept(Component.literal(Component.translatable("shellfish_trap.durability").getString() + durability + " / " + maxDurability).withStyle(ChatFormatting.ITALIC, durability > maxDurability / 2 ? ChatFormatting.DARK_GREEN : durability > maxDurability / 5 ? ChatFormatting.YELLOW : ChatFormatting.DARK_RED));
            }
        }
    }
}
 