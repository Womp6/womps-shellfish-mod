package womp.shellfishmod.item;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import womp.shellfishmod.entity.MossBallEntity;
import womp.shellfishmod.entity.parents.ShellfishEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Bucketable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import java.util.List;
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

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        if (hasTooltip && stack.contains(DataComponentTypes.BUCKET_ENTITY_DATA)) {
            assert stack.get(DataComponentTypes.BUCKET_ENTITY_DATA) != null;
            tooltip.add((Text.translatable(entityType.get().getTranslationKey() + "." + stack.get(DataComponentTypes.BUCKET_ENTITY_DATA).copyNbt().getInt("Variant"))).formatted(Formatting.GRAY, Formatting.ITALIC));
        }
    }

    private void spawnEntity(ServerWorld world, ItemStack stack, BlockPos pos) {
        Entity entity = this.entityType.get().spawnFromItemStack(world, stack, null, pos, SpawnReason.BUCKET, true, false);
        if (entity != null) {
            if (entity instanceof Entity) {
                Bucketable bucketable = (Bucketable)entity;
                NbtCompound nbt;
                if (stack.contains(DataComponentTypes.BUCKET_ENTITY_DATA)) nbt = stack.get(DataComponentTypes.BUCKET_ENTITY_DATA).copyNbt();
                else nbt = new NbtCompound();
                bucketable.copyDataFromNbt(nbt);
                bucketable.setFromBucket(true);
                if (!nbt.contains("Variant")) {
                    Random random = Random.create();
                    if (bucketable instanceof ShellfishEntity shellfish) shellfish.setVariantNumerical(random.nextBetween(0, shellfish.getMaxVariants() - 1));
                    if (bucketable instanceof MossBallEntity mossBall) mossBall.setVariant(MossBallEntity.Variant.byId(random.nextBetween(0, 1)));
                }
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