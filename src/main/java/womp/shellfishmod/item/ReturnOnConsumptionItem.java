package womp.shellfishmod.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.StewItem;
import net.minecraft.world.World;

public class ReturnOnConsumptionItem extends StewItem {

   private final ItemStack returnStack;

   public ReturnOnConsumptionItem(Settings settings, ItemConvertible returnItem) {
      super(settings);
      returnStack = new ItemStack(returnItem);
   }

   @Override
   public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
      ItemStack returnStack = this.returnStack.equals(null) ? new ItemStack(Items.AIR) : this.returnStack;
      super.finishUsing(stack, world, user);
      if (stack.isEmpty()) {
         return returnStack;
      } else {
         if (user instanceof PlayerEntity && !((PlayerEntity)user).getAbilities().creativeMode) {
            PlayerEntity playerEntity = (PlayerEntity)user;
            if (!playerEntity.getInventory().insertStack(returnStack)) {
               playerEntity.dropItem(returnStack, false);
            }
         }
         return stack;
      }
   }
}
