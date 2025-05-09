package womp.shellfishmod.item;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.Bucketable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

public class ShellfishBucketItem extends BucketItem {
    private final Supplier<EntityType<?>> entityType;
    private final boolean hasTooltip;
    public ShellfishBucketItem(Supplier<EntityType<?>> entityType, Fluid fluid, Item item, boolean hasTooltip, Settings settings) {
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

    @SuppressWarnings("deprecation")
    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
        super.appendTooltip(stack, context, displayComponent, textConsumer, type);
        NbtComponent nbtComponent = stack.getOrDefault(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT);
        if (hasTooltip && nbtComponent.isEmpty()) {
            assert nbtComponent.copyNbt() != null;
            textConsumer.accept(Text.translatable(getEntityType().getTranslationKey() + "." + nbtComponent.copyNbt().getInt("Variant")).formatted(Formatting.GRAY, Formatting.ITALIC));
        }
    }

    private void spawnEntity(ServerWorld world, ItemStack stack, BlockPos pos) {
        Entity entity = this.entityType.get().spawnFromItemStack(world, stack, null, pos, SpawnReason.BUCKET, true, false);
        if (entity != null) {
            if (entity instanceof Entity) {
                Bucketable bucketable = (Bucketable)entity;
                NbtComponent nbtComponent = stack.getOrDefault(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT);
                bucketable.copyDataFromNbt(nbtComponent.copyNbt());
                bucketable.setFromBucket(true);
            }
        }
    }

    private EntityType<?> getEntityType() {
        return entityType.get();
    }

    public static ItemStack getEmptiedStack(ItemStack stack, PlayerEntity player) {
        if (!player.getAbilities().creativeMode) {
            return new ItemStack(Items.BUCKET);
        }
        return stack;
    }
}