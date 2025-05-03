package womp.shellfishmod.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class ReturnOnConsumptionItem extends Item {

    private final ItemStack returnStack;

    public ReturnOnConsumptionItem(Item.Properties settings, Supplier<? extends ItemLike> returnItem) {
        super(settings);
        returnStack = new ItemStack(returnItem.get());
    }

    public ReturnOnConsumptionItem(Item.Properties settings, ItemLike returnItem) {
        super(settings);
        returnStack = new ItemStack(returnItem);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving) {
        ItemStack itemstack = super.finishUsingItem(pStack, pLevel, pEntityLiving);
        return pEntityLiving instanceof Player && ((Player)pEntityLiving).getAbilities().instabuild ? itemstack : returnStack;
    }
}
