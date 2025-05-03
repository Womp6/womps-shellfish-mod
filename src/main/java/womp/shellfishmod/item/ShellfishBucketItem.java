package womp.shellfishmod.item;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Bucketable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import java.util.List;

import org.jetbrains.annotations.Nullable;

public class ShellfishBucketItem extends BucketItem {
    private final EntityType<?> entityType;
    private final boolean hasTooltip;
    public ShellfishBucketItem(EntityType<?> entityType, Fluid fluid, Item item, boolean hasTooltip, Settings settings) {
        super(fluid, settings);
        this.entityType = entityType;
        this.hasTooltip = hasTooltip;
    }
    
    public void onEmptied(@Nullable PlayerEntity player, World world, ItemStack stack, BlockPos pos) {
        if (world instanceof ServerWorld) {
            this.spawnEntity((ServerWorld)world, stack, pos);
            world.emitGameEvent((Entity)player, GameEvent.ENTITY_PLACE, pos);
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        if (hasTooltip && stack.hasNbt()) {
            assert stack.getNbt() != null;
            tooltip.add((Text.translatable(entityType.getTranslationKey() + "." + stack.getNbt().getInt("Variant"))).formatted(Formatting.GRAY, Formatting.ITALIC));
        }
    }

    private void spawnEntity(ServerWorld world, ItemStack stack, BlockPos pos) {
        Entity entity = this.entityType.spawnFromItemStack(world, stack, null, pos, SpawnReason.BUCKET, true, false);
        if (entity != null) {
            if (entity instanceof Entity) {
                Bucketable bucketable = (Bucketable)entity;
                bucketable.copyDataFromNbt(stack.getOrCreateNbt());
                bucketable.setFromBucket(true);
            }
        }
    }

    public static ItemStack getEmptiedStack(ItemStack stack, PlayerEntity player) {
        if (!player.getAbilities().creativeMode) {
            return new ItemStack(Items.BUCKET);
        }
        return stack;
    }
}